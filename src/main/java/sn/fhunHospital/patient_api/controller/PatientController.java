package sn.fhunHospital.patient_api.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.fhunHospital.patient_api.dto.requests.PatientRequest;
import sn.fhunHospital.patient_api.dto.responses.PatientResponse;
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
}
