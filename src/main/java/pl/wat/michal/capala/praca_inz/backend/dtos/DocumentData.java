package pl.wat.michal.capala.praca_inz.backend.dtos;

import lombok.Data;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "documents")
public class DocumentData {
    @Id
    private String id;
    private Binary fileData;
}
