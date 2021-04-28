package luminor.intership.app.persistence.repository;

import luminor.intership.app.persistence.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * @author Klaudijus Simokaitis
 * */
public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    /**
     * Function returns payment which iban is equals to param
     * */
    Payment getByDebtorIban(String debtorIban);
}
