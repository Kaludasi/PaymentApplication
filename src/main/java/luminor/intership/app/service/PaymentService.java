package luminor.intership.app.service;

import luminor.intership.app.persistence.entity.Payment;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Klaudijus Simokaitis
 * */
@Service
public interface PaymentService {

    /**
     * function which creates new Payment
     * */
    void createPayment(Payment payment);

    /**
     * Function returns all payments
     * */
    List<Payment> getAllPayments();

    /**
     * Function returns payment which iban is equals to param
     * */
    Payment getByDebtorIban(String debtorIban);
}
