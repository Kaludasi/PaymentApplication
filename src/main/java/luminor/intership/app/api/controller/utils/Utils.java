package luminor.intership.app.api.controller.utils;

import luminor.intership.app.api.dto.PaymentDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Klaudijus Simokaitis
 * */
public class Utils {

    /**
     * Function reads CSV file, parses each line and outputs list of payments
     * */
    public static List<PaymentDTO> parseCSV(MultipartFile file){
        List<PaymentDTO> payments = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            br.readLine();
            String line1;
            while ((line1 = br.readLine()) != null){
                String[] values = line1.split(",");
                payments.add(new PaymentDTO(Double.parseDouble(values[0].replaceAll("\"","")), values[1].replaceAll("\"",""), null));
            }
            br.close();
            return payments;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
