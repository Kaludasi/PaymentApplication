package luminor.intership.app.api.controller.utils;

import luminor.intership.app.api.dto.PaymentDTO;
import luminor.intership.app.persistence.entity.EntityFactory;
import luminor.intership.app.persistence.entity.Payment;

/**
 * @author Klaudijus Simokaitis
 * */
public class PaymentMapper {

    /**
     * Function maps Payment to PaymentDTO
     * */
    public static Payment map(PaymentDTO paymentDTO) {
        return EntityFactory.getPayment(
                paymentDTO.getAmount(),
                paymentDTO.getDebtorIban(),
                paymentDTO.getLocation());
    }

    /**
     * Function maps PaymentDTO to Payment
     * */
    public static PaymentDTO map(Payment payment) {
        return EntityFactory.getPaymentDTO(
                payment.getAmount(),
                payment.getDebtorIban(),
                payment.getLocation());
    }
}
