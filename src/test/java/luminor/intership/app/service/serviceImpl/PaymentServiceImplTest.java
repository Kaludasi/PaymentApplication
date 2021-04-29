package luminor.intership.app.service.serviceImpl;

import luminor.intership.app.persistence.entity.Payment;
import luminor.intership.app.persistence.repository.PaymentRepository;
import static org.junit.jupiter.api.Assertions.*;

import luminor.intership.app.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private Payment payment;

    @Mock
    private Payment paymentResponse;

    @InjectMocks
    private PaymentServiceImpl paymentService;


    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        paymentService = new PaymentServiceImpl(paymentRepository);
    }

    @Test
    public void testGetAllPaymentsWhenPaymentNotExist(){
        when(paymentRepository.findAll()).thenReturn(Collections.emptyList());
        List<Payment> payments = paymentService.getAllPayments();
        assertNotNull(payments);
        assertTrue(payments.isEmpty());
    }

    @Test
    public void testGetAllPayments(){
        when(paymentRepository.findAll()).thenReturn(List.of(payment));
        List<Payment> payments = paymentService.getAllPayments();
        assertNotNull(payments);
        assertFalse(payments.isEmpty());
        assertEquals(1, payments.size());
    }

    @Test
    public void testCreatePaymentWhenPaymentIsNull(){
        paymentService.createPayment(null);
        verify(paymentRepository, times(0)).save(eq(null));
    }

    @Test
    public void testCreatePayment(){
        when(paymentRepository.save(payment)).thenReturn(paymentResponse);
        paymentService.createPayment(payment);
        verify(paymentRepository, times(1)).save(payment);
    }

    @Test
    public void testGetByDebtorIbanWhenStringIsEmpty(){
        when(paymentRepository.getByDebtorIban("")).thenReturn(paymentResponse);
        paymentService.getByDebtorIban("");
        verify(paymentRepository, times(1)).getByDebtorIban("");
    }

    @Test
    public void testGetByDebtorIbanWhenStringIsNull(){
        when(paymentRepository.getByDebtorIban(eq(null))).thenThrow(IllegalArgumentException.class);
        assertThrows(IllegalArgumentException.class, () -> paymentService.getByDebtorIban(null));
    }

    @Test
    public void testGetByDebtorIban(){
        when(paymentRepository.getByDebtorIban(anyString())).thenReturn(paymentResponse);
        paymentService.getByDebtorIban("");
        verify(paymentRepository, times(1)).getByDebtorIban("");
    }
}