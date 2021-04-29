package luminor.intership.app.service.serviceImpl;

import luminor.intership.app.persistence.entity.Payment;
import luminor.intership.app.persistence.repository.PaymentRepository;
import luminor.intership.app.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Klaudijus Simokaitis
 * */
@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public void createPayment(Payment payment) {
        if (payment == null) {
            return;
        }
        payment.setCreatedAt(LocalDateTime.now());
        paymentRepository.save(payment);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public Payment getByDebtorIban(String debtorIban) {
        return paymentRepository.getByDebtorIban(debtorIban);
    }
}
