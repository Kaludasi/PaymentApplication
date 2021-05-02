package luminor.intership.app.api.controller;

import luminor.intership.app.api.controller.utils.PaymentValidator;
import luminor.intership.app.api.dto.PaymentDTO;
import luminor.intership.app.persistence.entity.Payment;
import luminor.intership.app.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.ConcurrentModel;
import org.springframework.validation.BindException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {PaymentController.class})
@ExtendWith(MockitoExtension.class)
class PaymentControllerTest {

    @Mock
    private PaymentService paymentService;
    @Mock
    private PaymentValidator paymentValidator;
    @InjectMocks
    private PaymentController paymentController;

    @BeforeEach
    void setUp(){
        paymentController = new PaymentController(paymentService, paymentValidator);
    }

    @Test
    public void testCreate() {
        doNothing().when(this.paymentValidator).validate(any(), any());
        doNothing().when(this.paymentService).createPayment(any());
        PaymentDTO paymentDTO = new PaymentDTO();
        ConcurrentModel model = new ConcurrentModel();
        BindException bindingResult = new BindException("Target", "Object Name");
        assertEquals("redirect:/payments",
                this.paymentController.create(paymentDTO, model, bindingResult, new MockHttpServletRequest()));
        verify(this.paymentService).createPayment(any());
        verify(this.paymentValidator).validate(any(), any());
        assertEquals("", paymentDTO.getLocation());
    }

    @Test
    public void testGetPaymentForm() {
        assertEquals("paymentTemplate", this.paymentController.getPaymentForm(new ConcurrentModel()));
    }

    @Test
    public void testCreateFromCSV() throws IOException {
        MockMultipartFile file = new MockMultipartFile("Name",
                new ByteArrayInputStream("AAAAAAAAAAAAAAAAAAAAAAAA".getBytes("UTF-8")));
        ConcurrentModel model = new ConcurrentModel();
        assertEquals("redirect:/payments", this.paymentController.createFromCSV(file, model, new MockHttpServletRequest()));
    }

    @Test
    public void testGetRequestLocation() {
        assertEquals("", this.paymentController.getRequestLocation(new MockHttpServletRequest()));
        assertEquals("", this.paymentController
                .getRequestLocation(new MockHttpServletRequest("https://example.org/example", "https://example.org/example")));
    }

    @Test
    public void testGetPaymentFilesForm() {
        assertEquals("paymentCsvTemplate", this.paymentController.getPaymentFilesForm(new ConcurrentModel()));
    }

    @Test
    public void testGetPaymentByDebtorIban() {
        Payment payment = new Payment();
        payment.setLocation("Location");
        payment.setId(UUID.randomUUID());
        payment.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        payment.setDebtorIban("Debtor Iban");
        payment.setAmount(10.0);
        when(this.paymentService.getByDebtorIban(anyString())).thenReturn(payment);
        Optional<String> debtorIban = Optional.of("42");
        assertEquals("paymentsTemplate", this.paymentController.getAllPayments(debtorIban, new ConcurrentModel()));
        verify(this.paymentService).getByDebtorIban(anyString());
    }

    @Test
    public void testGetAllPayments() {
        Payment payment = new Payment();
        payment.setLocation("Location");
        payment.setId(UUID.randomUUID());
        payment.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        payment.setDebtorIban("Debtor Iban");
        payment.setAmount(10.0);
        when(this.paymentService.getAllPayments()).thenReturn(new ArrayList<>());
        assertEquals("paymentsTemplate", this.paymentController.getAllPayments(Optional.empty(), new ConcurrentModel()));
        verify(this.paymentService).getAllPayments();
    }


}