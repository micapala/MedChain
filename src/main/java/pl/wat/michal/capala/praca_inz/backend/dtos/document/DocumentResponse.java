package pl.wat.michal.capala.praca_inz.backend.dtos.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentResponse {
    private String documentID; // Id dokumentu (obj_id z mongodb)
    private String preparationDate;
    private String docType; // typ dokumentu
}
