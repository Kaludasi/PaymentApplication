package luminor.intership.app.api.controller.utils;

import luminor.intership.app.api.dto.PaymentDTO;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author Klaudijus Simokaitis
 */
@Component
public class PaymentValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return PaymentDTO.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Assert.notNull(errors, "Errors object must not be null");
        PaymentDTO payment = (PaymentDTO) o;
        validateAmount(errors, payment);
        validateIban(errors, payment);
    }

    /**
     * Function validates payment amount and if it is invalid adds error to binding result
     */
    private void validateAmount(Errors errors, PaymentDTO payment) {
        if (payment.getAmount() <= 0) {
            errors.rejectValue("amount", "validation.amount.greaterThanZero");
        }
    }

    /**
     * Function validates IBAN pattern, country code and if it is invalid adds error to binding result
     */
    private void validateIban(Errors errors, PaymentDTO payment) {
        if (!payment.getDebtorIban().matches("[A-Z]{2}[0-9]{2}(?:[ ]?[0-9]{4}){4}(?:[ ]?[0-9]{1,2})?")) {
            System.out.println(payment.getDebtorIban());
            errors.rejectValue("debtorIban", "validation.iban.invalid");
        } else if (!(payment.getDebtorIban().startsWith("LT") ||
                payment.getDebtorIban().startsWith("LV") ||
                payment.getDebtorIban().startsWith("EE"))) {
            errors.rejectValue("debtorIban", "validation.iban.WeDoNotSupportYourCountry");
        }
    }
}
