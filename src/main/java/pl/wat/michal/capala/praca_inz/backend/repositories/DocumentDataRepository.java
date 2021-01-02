package pl.wat.michal.capala.praca_inz.backend.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.wat.michal.capala.praca_inz.backend.dtos.document.DocumentFileDataResponse;

public interface DocumentDataRepository extends MongoRepository<DocumentFileDataResponse, String> {
}
