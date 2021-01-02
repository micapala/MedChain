package pl.wat.michal.capala.praca_inz.backend.dtos.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChaincodeDocument {
    private String documentID; // Id dokumentu (obj_id z mongodb)
    private String patientPesel;
    private String doctorPesel; // pesel lekarza
    private String preparationDate;
    private String docType; // typ dokumentu
    private String[] allowedPesels; // data sporządzenia
}
