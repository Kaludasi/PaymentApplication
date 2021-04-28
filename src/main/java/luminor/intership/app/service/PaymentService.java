package luminor.intership.app.service;

import luminor.intership.app.persistence.entity.Payment;

import java.util.List;

/**
 * @author Klaudijus Simokaitis
 * */
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
