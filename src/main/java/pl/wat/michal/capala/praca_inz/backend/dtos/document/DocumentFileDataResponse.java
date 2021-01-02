package pl.wat.michal.capala.praca_inz.backend.dtos.document;

import lombok.Data;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "documents")
public class DocumentFileDataResponse {
    @Id
    private String id;
    private Binary fileData;
}
