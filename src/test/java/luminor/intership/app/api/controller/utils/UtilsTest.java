package luminor.intership.app.api.controller.utils;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

public class UtilsTest {
    @Test
    public void testParseCSV() throws IOException {
        assertTrue(Utils.parseCSV(new MockMultipartFile("Name", "AAAAAAAAAAAAAAAAAAAAAAAA".getBytes("UTF-8"))).isEmpty());
        assertTrue(Utils
                .parseCSV(new MockMultipartFile("Name", new ByteArrayInputStream("AAAAAAAAAAAAAAAAAAAAAAAA".getBytes("UTF-8"))))
                .isEmpty());
    }
}

