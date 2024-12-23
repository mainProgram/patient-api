package sn.fhunHospital.patient_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.fhunHospital.patient_api.dto.requests.ContactRequest;
import sn.fhunHospital.patient_api.dto.requests.PatientRequest;
import sn.fhunHospital.patient_api.dto.responses.PatientResponse;
import sn.fhunHospital.patient_api.dto.responses.PatientWithContactResponse;
import sn.fhunHospital.patient_api.service.PatientService;
import java.util.List;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    public ResponseEntity<List<PatientResponse>> getAll(){
        return ResponseEntity.ok().body(patientService.getAllPatients());
    }

    @PostMapping
    public ResponseEntity<PatientResponse> save(@RequestBody PatientRequest patientRequest){
        return ResponseEntity.ok().body(patientService.savePatient(patientRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientWithContactResponse> getOne(@PathVariable("id") String id){
        return ResponseEntity.ok().body(patientService.getPatientById(id).get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientWithContactResponse> update(@PathVariable("id") String id, @RequestBody PatientRequest patientRequest){
        return ResponseEntity.ok(patientService.updatePatient(id, patientRequest).orElseThrow());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> update(@PathVariable("id") String id){
        return ResponseEntity.ok().body(patientService.deletePatient(id));
    }

    @PatchMapping("/{id}/contacts")
    public ResponseEntity<PatientWithContactResponse> addOrUpdateContacts(@PathVariable String id, @RequestBody List<ContactRequest> contacts) {
        return ResponseEntity.ok(patientService.addOrUpdateContacts(id, contacts).orElseThrow());
    }

    @DeleteMapping("/{idPatient}/contact/{idContact}")
    public ResponseEntity<PatientWithContactResponse> deleteContact(@PathVariable("idPatient") String idPatient, @PathVariable String idContact) {
        return ResponseEntity.ok(patientService.deleteContact(idPatient, idContact).orElseThrow());
    }
}
