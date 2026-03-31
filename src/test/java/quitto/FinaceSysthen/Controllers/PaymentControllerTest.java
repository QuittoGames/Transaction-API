package quitto.FinaceSysthen.Controllers;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import quitto.FinaceSysthen.Services.PaymentService;

public class PaymentControllerTest {

    @Mock   
    private PaymentService paymentService;
    
    public PaymentControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testTrasetion() {
        // teste aqui
    }
}