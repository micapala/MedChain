package pl.wat.michal.capala.praca_inz.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.Binary;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Data
@AllArgsConstructor
public class AddDocumentRequest {
    private String patientPesel;
    private String doctorPesel;
    private String writeDate;
    private String type;
    private MultipartFile fileData;
}
