package pl.wat.michal.capala.praca_inz.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.wat.michal.capala.praca_inz.backend.dtos.document.AddDocumentRequest;
import pl.wat.michal.capala.praca_inz.backend.dtos.document.DocumentFileDataResponse;
import pl.wat.michal.capala.praca_inz.backend.dtos.document.DocumentResponse;
import pl.wat.michal.capala.praca_inz.backend.services.DocumentService;

import java.util.List;

@RestController
public class DocumentController {

    private final DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/api/documents/getAllPatientDocuments/{patientPesel}")
    public ResponseEntity<List<DocumentResponse>> getAllPatientDocuments(@PathVariable("patientPesel") String patientPesel){
        return new ResponseEntity<>(documentService.getAllPatientDocuments(patientPesel), HttpStatus.OK);
    }

    @GetMapping("/api/documents/getAllDoctorDocuments/{doctorPesel}")
    public ResponseEntity<List<DocumentResponse>> getAllDoctorDocuments(@PathVariable("doctorPesel") String doctorPesel){
        return new ResponseEntity<>(documentService.getAllDoctorDocuments(doctorPesel), HttpStatus.OK);
    }

    @PostMapping("/api/documents/giveAccess/{documentID}/{doctorPesel}")
    public ResponseEntity giveAccess(@PathVariable String documentID, @PathVariable String doctorPesel){
        documentService.giveAccess(documentID, doctorPesel);

        return new ResponseEntity(HttpStatus.OK);
    }
    @PostMapping("/api/documents/revokeAccess/{documentID}/{doctorPesel}")
    public ResponseEntity revokeAccess(@PathVariable String documentID, @PathVariable String doctorPesel){
        documentService.revokeAccess(documentID, doctorPesel);

        return new ResponseEntity(HttpStatus.OK);
    }


    @PostMapping("/api/documents/addDocument")
    public ResponseEntity addDocument(@RequestParam String patientPesel,
                                      @RequestParam String doctorPesel, @RequestParam String writeDate,
                                      @RequestParam String docType, @RequestPart(required = true) MultipartFile fileData){
        AddDocumentRequest addDocumentRequest = new AddDocumentRequest(patientPesel,doctorPesel,writeDate,docType,fileData);
        documentService.addDocument(addDocumentRequest);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/api/documents/getDocument/{documentID}")
    public ResponseEntity getDocument(@PathVariable String documentID){
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=myDoc.txt");
        DocumentFileDataResponse documentFileDataResponse = documentService.downloadDocument(documentID);
        ByteArrayResource resource = new ByteArrayResource(documentFileDataResponse.getFileData().getData());

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(documentFileDataResponse.getFileData().length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

}
