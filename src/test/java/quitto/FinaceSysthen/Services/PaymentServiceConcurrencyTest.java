package quitto.FinaceSysthen.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.naming.OperationNotSupportedException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import quitto.FinaceSysthen.DTOs.Trasactions.TransactionSenderDTO;
import quitto.FinaceSysthen.Enums.Category;
import quitto.FinaceSysthen.Enums.Role;
import quitto.FinaceSysthen.Models.User;
import quitto.FinaceSysthen.Repository.PaymentRepository;
import quitto.FinaceSysthen.Repository.UserRepository;

// Codead with IA for test (JUNIT)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
class PaymentServiceConcurrencyTest {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Test
    void concurrentTransaction_shouldNotAllowOverdraft_orDoubleSuccess() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        try {
            // Repetimos algumas vezes porque “race” pode ser intermitente.
            for (int attempt = 0; attempt < 25; attempt++) {
                paymentRepository.deleteAll();
                userRepository.deleteAll();

                User sender = userService.createUser("sender_" + attempt, new BigDecimal("100.00"), Role.USER);
                User receiver1 = userService.createUser("receiver1_" + attempt, BigDecimal.ZERO, Role.USER);
                User receiver2 = userService.createUser("receiver2_" + attempt, BigDecimal.ZERO, Role.USER);

                BigDecimal txValue = new BigDecimal("80.00");

                TransactionSenderDTO tx1 = new TransactionSenderDTO(
                        sender.getUserId(),
                        receiver1.getUserId(),
                        txValue,
                        UUID.randomUUID(),
                        Category.FOOD);

                TransactionSenderDTO tx2 = new TransactionSenderDTO(
                        sender.getUserId(),
                        receiver2.getUserId(),
                        txValue,
                        UUID.randomUUID(),
                        Category.FOOD);

                CyclicBarrier barrier = new CyclicBarrier(2);

                Callable<TxAttemptResult> task1 = () -> runConcurrentAttempt(tx1, barrier);
                Callable<TxAttemptResult> task2 = () -> runConcurrentAttempt(tx2, barrier);

                List<Future<TxAttemptResult>> futures = executor.invokeAll(List.of(task1, task2));

                TxAttemptResult r1 = futures.get(0).get();
                TxAttemptResult r2 = futures.get(1).get();

                int successCount = (r1.success ? 1 : 0) + (r2.success ? 1 : 0);
                int failureCount = (r1.success ? 0 : 1) + (r2.success ? 0 : 1);

                // Com saldo inicial 100 e duas tentativas de 80, só 1 deve conseguir.
                assertEquals(1, successCount, "Era esperado apenas 1 sucesso (tentativa=" + attempt + ")");
                assertEquals(1, failureCount, "Era esperado apenas 1 falha (tentativa=" + attempt + ")");

                User senderAfter = userRepository.findByUserId(sender.getUserId()).orElseThrow();
                User receiver1After = userRepository.findByUserId(receiver1.getUserId()).orElseThrow();
                User receiver2After = userRepository.findByUserId(receiver2.getUserId()).orElseThrow();

                // Garantia principal: nunca pode ficar negativo.
                assertTrue(senderAfter.getAmount().compareTo(BigDecimal.ZERO) >= 0,
                        "Saldo do sender ficou negativo (tentativa=" + attempt + ") => " + senderAfter.getAmount());

                // Resultado esperado quando só 1 withdraw/deposit passa: sender 20, um receiver 80, outro 0.
                assertTrue(senderAfter.getAmount().compareTo(new BigDecimal("20.00")) == 0,
                    "Saldo final do sender inesperado (tentativa=" + attempt + ") => " + senderAfter.getAmount());

                boolean receiver1GotMoney = receiver1After.getAmount().compareTo(txValue) == 0;
                boolean receiver2GotMoney = receiver2After.getAmount().compareTo(txValue) == 0;

                assertTrue(receiver1GotMoney ^ receiver2GotMoney,
                        "Era esperado exatamente 1 receiver com 80.00 (tentativa=" + attempt + ")");

                // Opcional: se apenas uma transação concluiu, deve existir 1 registro de Payment.
                assertEquals(1L, paymentRepository.count(), "Quantidade de payments inesperada (tentativa=" + attempt + ")");
            }
        } finally {
            executor.shutdownNow();
        }
    }

    private TxAttemptResult runConcurrentAttempt(TransactionSenderDTO dto, CyclicBarrier barrier) {
        try {
            barrier.await();
            paymentService.transaction(dto);
            return TxAttemptResult.success();
        } catch (OperationNotSupportedException ex) {
            return TxAttemptResult.failure(ex.getMessage());
        } catch (Exception ex) {
            return TxAttemptResult.failure(ex.getClass().getSimpleName() + ": " + ex.getMessage());
        }
    }

    private static final class TxAttemptResult {
        final boolean success;
        final String failure;

        private TxAttemptResult(boolean success, String failure) {
            this.success = success;
            this.failure = failure;
        }

        static TxAttemptResult success() {
            return new TxAttemptResult(true, null);
        }

        static TxAttemptResult failure(String message) {
            return new TxAttemptResult(false, message);
        }
    }
}
