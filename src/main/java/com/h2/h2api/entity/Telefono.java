package com.h2.h2api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
public class Telefono {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    private Long number;
    private Integer citycode;
    private Integer contrycode;

    @ManyToOne
    @JoinColumn(name = "idUsuario")
    @JsonBackReference
    private Usuario usuario;


}
