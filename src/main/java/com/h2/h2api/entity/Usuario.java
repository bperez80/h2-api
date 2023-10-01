package com.h2.h2api.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Usuario {

    @Valid

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private LocalDate created;
    private LocalDate modified;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    @JsonManagedReference
    private List<Telefono> telefonos;

    public Usuario(String id, String name) {
        this.id = id;
        this.name = name;

    }

    public Usuario(ResponseEntity<Object> name) {
        this.name = String.valueOf(name);

    }

    public Usuario() {

    }

}
