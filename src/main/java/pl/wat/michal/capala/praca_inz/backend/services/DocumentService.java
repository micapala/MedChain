package pl.wat.michal.capala.praca_inz.backend.services;

import pl.wat.michal.capala.praca_inz.backend.dtos.document.AddDocumentRequest;
import pl.wat.michal.capala.praca_inz.backend.dtos.document.DocumentFileDataResponse;
import pl.wat.michal.capala.praca_inz.backend.dtos.document.DocumentResponse;

import java.util.List;

public interface DocumentService {
    List<DocumentResponse> getAllPatientDocuments(String patientPesel);
    List<DocumentResponse> getAllDoctorDocuments(String doctorPesel);
    DocumentFileDataResponse downloadDocument(String documentId);
    void giveAccess(String document_id, String doctorPesel);
    void revokeAccess(String document_id, String doctorPesel);
    void addDocument(AddDocumentRequest addDocumentRequest);

}
