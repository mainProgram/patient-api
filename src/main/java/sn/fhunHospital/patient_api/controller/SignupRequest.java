package sn.fhunHospital.patient_api.controller;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.Set;

@Data
public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    private Set<String> role; // ex: ["admin", "user"]

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
}
