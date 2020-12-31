package pl.wat.michal.capala.praca_inz.backend.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DBObject;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wat.michal.capala.praca_inz.backend.dtos.AddDocumentRequest;
import pl.wat.michal.capala.praca_inz.backend.dtos.DocumentData;
import pl.wat.michal.capala.praca_inz.backend.dtos.DocumentDataResponse;
import pl.wat.michal.capala.praca_inz.backend.dtos.DocumentResponse;
import pl.wat.michal.capala.praca_inz.backend.repositories.DocumentDataRepository;
import pl.wat.michal.capala.praca_inz.backend.repositories.HyperledgerRepository;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final HyperledgerRepository hyperledgerRepository;
    private final DocumentDataRepository documentDataRepository;

    @Autowired
    public DocumentServiceImpl(HyperledgerRepository hyperledgerRepository, DocumentDataRepository documentDataRepository) {
        this.hyperledgerRepository = hyperledgerRepository;
        this.documentDataRepository = documentDataRepository;
    }


    @Override
    public List<DocumentResponse> getAllPatientDocuments(String patientPesel) {
        return jsonToList(hyperledgerRepository.evaluateChaincodeTransaction("mychannel",
                "dokumenty",
                "QueryDocuments",
                new String("{\"selector\":{\"patientPesel\":\"" + patientPesel + "\"}}")));
    }

    @Override
    public List<DocumentResponse> getAllDoctorDocuments(String doctorPesel) {
        return jsonToList(hyperledgerRepository.evaluateChaincodeTransaction("mychannel",
                "dokumenty",
                "QueryDocuments",
                new String("{\"selector\":{\"doctorPesel\":\"" + doctorPesel + "\"}}")));
    }

    @Override
    public DocumentData downloadDocument(String documentId) {
        return documentDataRepository.findById(documentId).get();
    }

    @Override
    public void giveAccess(String documentID, String doctorPesel) {
        hyperledgerRepository.submitChaincodeTransaction("mychannel",
                "dokumenty",
                "AddPermissioned",
                documentID, doctorPesel);
    }

    @Override
    public void revokeAccess(String documentID, String doctorPesel) {
        hyperledgerRepository.submitChaincodeTransaction("mychannel",
                "dokumenty",
                "RemovePermissioned",
                documentID, doctorPesel);
    }

    @Override
    public void addDocument(AddDocumentRequest addDocumentRequest) {
        DocumentData documentData = new DocumentData();
        try {
            documentData.setFileData(new Binary(BsonBinarySubType.BINARY, addDocumentRequest.getFileData().getBytes()));
            documentData = documentDataRepository.insert(documentData);

        } catch (IOException e) {
            e.printStackTrace();
        }
        hyperledgerRepository.submitChaincodeTransaction("mychannel",
                "dokumenty",
                "CreateDocument",
                documentData.getId(), addDocumentRequest.getPatientPesel(), addDocumentRequest.getDoctorPesel(),
                addDocumentRequest.getWriteDate(), addDocumentRequest.getType(), "[]");

    }

    private List<DocumentResponse> jsonToList (String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<DocumentResponse> responseList = Arrays.asList(mapper.readValue(json, DocumentResponse[].class));
            return responseList;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }

    }
}
