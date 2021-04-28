package luminor.intership.app.api;

import luminor.intership.app.api.dto.PaymentDTO;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

/**
 * @author Klaudijus Simokaitis
 * */
public interface PaymentAPI {

    /**
     * Method POST for creating new payment
     * @param paymentDTO payment data transfer object
     * */
    @PostMapping("/payment")
    String create(PaymentDTO paymentDTO, Model model, BindingResult bindingResult, HttpServletRequest request);

    /**
     * Method GET for getting new payments form
     * */
    @GetMapping("/payment")
    String getPaymentForm(Model model);

    /**
     * Method POST for creating new payment(s) from CSV file
     * @param file CSV File
     * */
    @PostMapping(value = "/payment-files", consumes = {"multipart/form-data"})
    String createFromCSV(@Valid @RequestParam("file") MultipartFile file, Model model, HttpServletRequest request);

    /**
     * Method GET for new payment(s) form CSV file form
     * */
    @GetMapping("/payment-files")
    String getPaymentFilesForm(Model model);

    /**
     * Method GET returns all payments but if theres querry param returns one payment which iban is equals to querry param
     * @param debtorIban optional querry param for finding payment which iban is equals to param
     * */
    @GetMapping("/payments")
    String getAllPayments(@RequestParam Optional<String> debtorIban, Model model);
}
