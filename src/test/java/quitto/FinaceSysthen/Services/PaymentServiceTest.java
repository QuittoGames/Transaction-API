package quitto.FinaceSysthen.Services;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import quitto.FinaceSysthen.DTOs.Trasactions.TransactionSenderDTO;
import quitto.FinaceSysthen.Repository.PaymentRepository;
import quitto.FinaceSysthen.Repository.UserRepository;
import java.math.BigDecimal;
import java.util.UUID;
import quitto.FinaceSysthen.Enums.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;    
    
public class PaymentServiceTest {
    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private UserRepository userRepository;

    @Nested
    public class transaction {
        @Test
        @DisplayName("Make The Trasection")
        void MakeTrasaction(){
            // Arrange
            UUID indepotecyKey = UUID.randomUUID();
            BigDecimal value = new BigDecimal(50);
            var data = new TransactionSenderDTO(1L,2L,value,indepotecyKey,Category.FOOD);
            


        }
        
    }

}
    