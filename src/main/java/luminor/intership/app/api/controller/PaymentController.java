package luminor.intership.app.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import luminor.intership.app.api.PaymentAPI;
import luminor.intership.app.api.controller.utils.PaymentMapper;
import luminor.intership.app.api.controller.utils.PaymentValidator;
import luminor.intership.app.api.controller.utils.Utils;
import luminor.intership.app.api.dto.PaymentDTO;
import luminor.intership.app.persistence.entity.PersonData;
import luminor.intership.app.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Optional;

/**
 * @author Klaudijus Simokaitis
 * */
@Controller
public class PaymentController implements PaymentAPI {

    private final PaymentService paymentService;
    private final PaymentValidator paymentValidator;

    @Autowired
    public PaymentController(PaymentService paymentService, PaymentValidator paymentValidator) {
        this.paymentService = paymentService;
        this.paymentValidator = paymentValidator;
    }

    @Override
    public String create(PaymentDTO paymentDTO, Model model, BindingResult bindingResult, HttpServletRequest request) {
        paymentValidator.validate(paymentDTO, bindingResult);
        model.addAttribute("bindingResult", bindingResult.hasErrors());
        if (bindingResult.hasErrors()) {
            model.addAttribute("payment", new PaymentDTO());
            model.addAttribute("error", bindingResult.getAllErrors().get(0).getCode());
            return "paymentTemplate";
        }
        String location = getRequestLocation(request);
        paymentDTO.setLocation(location);
        paymentService.createPayment(PaymentMapper.map(paymentDTO));
        return "redirect:/payments";
    }

    @Override
    public String getPaymentForm(Model model) {
        model.addAttribute("payment", new PaymentDTO());
        model.addAttribute("error",null);
        return "paymentTemplate";
    }

    @Override
    public String createFromCSV(MultipartFile file, Model model, HttpServletRequest request) {
        String location = getRequestLocation(request);
        List<PaymentDTO> payments = Utils.parseCSV(file);
        if (payments != null) {
            for (PaymentDTO paymentDTO : payments) {
                BindingResult bindingResult = new BeanPropertyBindingResult(paymentDTO, "paymentDto");
                Assert.notNull(bindingResult, "binding result can not be null");
                paymentValidator.validate(paymentDTO, bindingResult);
                if (bindingResult.hasErrors()) {
                    model.addAttribute("iban", paymentDTO.getDebtorIban());
                    model.addAttribute("amount", paymentDTO.getAmount());
                    model.addAttribute("error", bindingResult.getAllErrors().get(0).getCode());
                    return "invalidFileTemplate";
                }
                paymentDTO.setLocation(location);
                paymentService.createPayment(PaymentMapper.map(paymentDTO));
            }
        } else {
            model.addAttribute("error", List.of("Invalid file"));
            return "paymentCsvTemplate";
        }
        return "redirect:/payments";
    }

    /**
     * Function gets user ip from X-Forwarded-For header and sends request to ip-api.com for user location data
     * then parses json data to PersonData object
     * @return usersLocation
     * */
    public String getRequestLocation(HttpServletRequest request) {
        String location = "";
        if (request.getHeader("X-FORWARDED-FOR") != null) {
            try {
                URL url = new URL("http://ip-api.com/json/" + request.getHeader("X-FORWARDED-FOR"));
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("accept", "application/json");
                InputStream responseStream = connection.getInputStream();
                BufferedReader bReader = new BufferedReader(new InputStreamReader(responseStream));
                StringBuilder resopnse = new StringBuilder();
                String line;
                while ((line = bReader.readLine()) != null) {
                    resopnse.append(line);
                }
                ObjectMapper mapper = new ObjectMapper();
                PersonData person = mapper.readValue(String.valueOf(resopnse), PersonData.class);
                location = person.getCountry();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return location;
    }

    @Override
    public String getPaymentFilesForm(Model model) {
        return "paymentCsvTemplate";
    }

    @Override
    public String getAllPayments(Optional<String> debtorIban, Model model) {
        if (debtorIban.isEmpty()) {
            model.addAttribute("payments", paymentService.getAllPayments());
        } else {
            model.addAttribute("payments", paymentService.getByDebtorIban(debtorIban.get()));
        }
        model.addAttribute("debtorIban", "");
        return "paymentsTemplate";
    }
}
