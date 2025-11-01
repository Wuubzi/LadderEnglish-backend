package com.LadderEnglish.backend.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "ficha")
@Data
public class Ficha {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "id_ficha")
    private Long idFicha;
    @Column(name = "numero_ficha")
    private Long numeroFicha;
    @Column(name = "nombre_ficha")
    private String nombreFicha;
    @OneToMany(mappedBy = "ficha", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Aprendiz> aprendices;
}
