package sn.fhunHospital.patient_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "sn.fhunHospital")
public class PatientApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatientApiApplication.class, args);
	}

}
