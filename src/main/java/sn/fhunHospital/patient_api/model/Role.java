package sn.fhunHospital.patient_api.model;

import jakarta.persistence.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "roles")
public class Role {

    @Id
    private String id;

    @Indexed(unique = true)
    private String name;

    // Constructeurs
    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    // Getters et Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}