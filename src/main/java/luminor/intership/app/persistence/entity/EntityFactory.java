package luminor.intership.app.persistence.entity;

import luminor.intership.app.api.dto.PaymentDTO;

/**
 * @author Klaudijus Simokaitis
 * */
public class EntityFactory {

    public static PaymentDTO getPaymentDTO(Double amount, String debtorIban, String location) {
        return PaymentDTO.builder()
                .amount(amount)
                .debtorIban(debtorIban)
                .location(location)
                .build();
    }

    public static Payment getPayment(Double amount, String debtorIban, String location) {
        return Payment.builder()
                .amount(amount)
                .debtorIban(debtorIban)
                .location(location)
                .build();
    }
}
