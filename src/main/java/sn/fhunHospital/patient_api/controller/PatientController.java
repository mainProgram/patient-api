package sn.fhunHospital.patient_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.fhunHospital.patient_api.dto.requests.ContactRequest;
import sn.fhunHospital.patient_api.dto.requests.PatientRequest;
import sn.fhunHospital.patient_api.dto.responses.PatientResponse;
import sn.fhunHospital.patient_api.dto.responses.PatientWithContactResponse;
import sn.fhunHospital.patient_api.service.PatientService;
import sn.fhunHospital.patient_api.utils.exception.ApiResponse;
import sn.fhunHospital.patient_api.utils.exception.ResponseUtil;

import java.util.List;

@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<PatientResponse>>> getAll(){
        return ResponseEntity.ok(ResponseUtil.success(patientService.getAllPatients(), ""));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PatientResponse>> save(@RequestBody PatientRequest patientRequest){
        return ResponseEntity.ok(ResponseUtil.success(patientService.savePatient(patientRequest), "Patient créé avec succès !"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PatientWithContactResponse>> getOne(@PathVariable("id") String id){
        return ResponseEntity.ok(ResponseUtil.success(patientService.getPatientById(id).get(), ""));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PatientWithContactResponse>> update(@PathVariable("id") String id, @RequestBody PatientRequest patientRequest){
        return ResponseEntity.ok(ResponseUtil.success(patientService.updatePatient(id, patientRequest).get(), "Patient modifié avec succès !"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Boolean>> update(@PathVariable("id") String id){
        return ResponseEntity.ok(ResponseUtil.success(patientService.deletePatient(id), "Patient supprimé avec succès !"));
    }

    @PatchMapping("/{id}/contacts")
    public ResponseEntity<ApiResponse<PatientWithContactResponse>> addOrUpdateContacts(@PathVariable String id, @RequestBody List<ContactRequest> contacts) {
        return ResponseEntity.ok(ResponseUtil.success(patientService.addOrUpdateContacts(id, contacts).get(), "Patient modifié avec succès !"));
    }

    @DeleteMapping("/{idPatient}/contact/{idContact}")
    public ResponseEntity<ApiResponse<PatientWithContactResponse>> deleteContact(@PathVariable("idPatient") String idPatient, @PathVariable String idContact) {
        return ResponseEntity.ok(ResponseUtil.success(patientService.deleteContact(idPatient, idContact).get(), "Contact supprimé avec succès !"));
    }
}
