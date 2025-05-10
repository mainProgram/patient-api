package sn.fhunHospital.patient_api;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.Key;
import java.util.Base64;

@SpringBootApplication(scanBasePackages = "sn.fhunHospital")
public class PatientApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatientApiApplication.class, args);
//		Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//		String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());
//		System.out.println("Clé sécurisée pour HS512 : " + encodedKey);
//		System.out.println("Clé sécurisée pour HS512 : " + encodedKey);
//		System.out.println("Clé sécurisée pour HS512 : " + encodedKey);
//		System.out.println("Clé sécurisée pour HS512 : " + encodedKey);
//		System.out.println("Clé sécurisée pour HS512 : " + encodedKey);
//		System.out.println("Clé sécurisée pour HS512 : " + encodedKey);
	}

}
