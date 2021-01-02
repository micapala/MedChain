package pl.wat.michal.capala.praca_inz.backend.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wat.michal.capala.praca_inz.backend.dtos.document.AddDocumentRequest;
import pl.wat.michal.capala.praca_inz.backend.dtos.document.ChaincodeDocument;
import pl.wat.michal.capala.praca_inz.backend.dtos.document.DocumentFileDataResponse;
import pl.wat.michal.capala.praca_inz.backend.dtos.document.DocumentResponse;
import pl.wat.michal.capala.praca_inz.backend.repositories.DocumentDataRepository;
import pl.wat.michal.capala.praca_inz.backend.repositories.HyperledgerRepository;

import java.io.IOException;
import java.util.ArrayList;
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
        List <ChaincodeDocument> chaincodeDocuments = jsonToList(hyperledgerRepository.evaluateChaincodeTransaction("mychannel",
                "dokumenty",
                "QueryDocuments",
                new String("{\"selector\":{\"patientPesel\":\"" + patientPesel + "\"}}")));
        return chaincodeToResponse(chaincodeDocuments);
    }

    @Override
    public List<DocumentResponse> getAllDoctorDocuments(String doctorPesel) {
        List <ChaincodeDocument> chaincodeDocuments = jsonToList(hyperledgerRepository.evaluateChaincodeTransaction("mychannel",
                "dokumenty",
                "QueryDocuments",
                new String("{\"selector\":{\"doctorPesel\":\"" + doctorPesel + "\"}}")));
        return chaincodeToResponse(chaincodeDocuments);

    }

    @Override
    public DocumentFileDataResponse downloadDocument(String documentId) {
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
        DocumentFileDataResponse documentFileDataResponse = new DocumentFileDataResponse();
        try {
            documentFileDataResponse.setFileData(new Binary(BsonBinarySubType.BINARY, addDocumentRequest.getFileData().getBytes()));
            documentFileDataResponse = documentDataRepository.insert(documentFileDataResponse);

        } catch (IOException e) {
            e.printStackTrace();
        }
        hyperledgerRepository.submitChaincodeTransaction("mychannel",
                "dokumenty",
                "CreateDocument",
                documentFileDataResponse.getId(), addDocumentRequest.getPatientPesel(), addDocumentRequest.getDoctorPesel(),
                addDocumentRequest.getWriteDate(), addDocumentRequest.getType(), "[]");

    }

    private List<ChaincodeDocument> jsonToList (String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<ChaincodeDocument> responseList = Arrays.asList(mapper.readValue(json, ChaincodeDocument[].class));
            return responseList;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }

    }

    private List<DocumentResponse> chaincodeToResponse (List<ChaincodeDocument> chaincodeDocuments){

        List<DocumentResponse> documentResponseList = new ArrayList<>();
        for (int i =0; i< chaincodeDocuments.size();i++) {
            ChaincodeDocument chaincodeDocument = chaincodeDocuments.get(i);
            DocumentResponse documentResponse = new DocumentResponse(chaincodeDocument.getDocumentID(),chaincodeDocument.getPreparationDate(),chaincodeDocument.getDocType());
            documentResponseList.add(documentResponse);
        }
        return documentResponseList;

    }
}
