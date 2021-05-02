package luminor.intership.app.api.controller.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import luminor.intership.app.api.dto.PaymentDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

public class PaymentValidatorTest {
    @Test
    public void testSupports() {
        PaymentValidator paymentValidator = new PaymentValidator();
        assertFalse(paymentValidator.supports(Object.class));
    }

    @Test
    public void testSupports2() {
        PaymentValidator paymentValidator = new PaymentValidator();
        assertTrue(paymentValidator.supports(PaymentDTO.class));
    }

    @Test
    public void testValidate() {
        PaymentValidator paymentValidator = new PaymentValidator();

        PaymentDTO paymentDTO = new PaymentDTO(10.0, "Debtor Iban", "Location");
        paymentDTO.setAmount(0.0);
        BindException bindException = new BindException(paymentDTO, "luminor.intership.app.api.dto.PaymentDTO");
        paymentValidator.validate(paymentDTO, bindException);
        BindingResult bindingResult = bindException.getBindingResult();
        assertEquals(2, bindingResult.getErrorCount());
        assertEquals("org.springframework.validation.BeanPropertyBindingResult: 2 errors\n"
                + "Field error in object 'luminor.intership.app.api.dto.PaymentDTO' on field 'amount': rejected value"
                + " [0.0]; codes [validation.amount.greaterThanZero.luminor.intership.app.api.dto.PaymentDTO.amount"
                + ",validation.amount.greaterThanZero.amount,validation.amount.greaterThanZero.java.lang.Double,validation"
                + ".amount.greaterThanZero]; arguments []; default message [null]\n"
                + "Field error in object 'luminor.intership.app.api.dto.PaymentDTO' on field 'debtorIban': rejected value"
                + " [Debtor Iban]; codes [validation.iban.invalid.luminor.intership.app.api.dto.PaymentDTO.debtorIban"
                + ",validation.iban.invalid.debtorIban,validation.iban.invalid.java.lang.String,validation.iban.invalid];"
                + " arguments []; default message [null]", bindingResult.toString());
        assertSame(bindException.getPropertyEditorRegistry(),
                ((BeanPropertyBindingResult) bindingResult).getPropertyAccessor());
    }

    @Test
    public void testValidate2() {
        PaymentValidator paymentValidator = new PaymentValidator();

        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setDebtorIban("UU99999999999999999999");
        paymentDTO.setAmount(0.0);
        BindException bindException = new BindException(paymentDTO, "luminor.intership.app.api.dto.PaymentDTO");
        paymentValidator.validate(paymentDTO, bindException);
        BindingResult bindingResult = bindException.getBindingResult();
        assertEquals(2, bindingResult.getErrorCount());
        assertEquals("org.springframework.validation.BeanPropertyBindingResult: 2 errors\n"
                + "Field error in object 'luminor.intership.app.api.dto.PaymentDTO' on field 'amount': rejected value"
                + " [0.0]; codes [validation.amount.greaterThanZero.luminor.intership.app.api.dto.PaymentDTO.amount"
                + ",validation.amount.greaterThanZero.amount,validation.amount.greaterThanZero.java.lang.Double,validation"
                + ".amount.greaterThanZero]; arguments []; default message [null]\n"
                + "Field error in object 'luminor.intership.app.api.dto.PaymentDTO' on field 'debtorIban': rejected value"
                + " [UU99999999999999999999]; codes [validation.iban.WeDoNotSupportYourCountry.luminor.intership.app.api"
                + ".dto.PaymentDTO.debtorIban,validation.iban.WeDoNotSupportYourCountry.debtorIban,validation.iban"
                + ".WeDoNotSupportYourCountry.java.lang.String,validation.iban.WeDoNotSupportYourCountry]; arguments [];"
                + " default message [null]", bindingResult.toString());
        assertSame(bindException.getPropertyEditorRegistry(),
                ((BeanPropertyBindingResult) bindingResult).getPropertyAccessor());
    }
}

