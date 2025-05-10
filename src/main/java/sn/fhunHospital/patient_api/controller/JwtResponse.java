package sn.fhunHospital.patient_api.controller;


import lombok.Data;
import java.util.Set;

@Data
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String id;
    private String username;
    private Set<String> roles;

    public JwtResponse(String accessToken, String id, String username, Set<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.roles = roles;
    }
}
