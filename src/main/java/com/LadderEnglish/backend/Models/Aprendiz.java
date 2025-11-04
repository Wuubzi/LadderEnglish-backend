package com.LadderEnglish.backend.Models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "aprendiz")
public class Aprendiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_aprendiz")
    private Long idAprendiz;
    @Column(name = "tipo_documento")
    private String tipoDocumento;
    @Column(name = "numero_documento")
    private Long numeroDocumento;
    @Column
    private String nombres;
    @Column
    private String apellidos;
    @Column
    private String celular;
    @Column
    private String correo;
    @Column(name = "estado_ingles1")
    private String estadoIngles1;
    @Column(name = "estado_ingles2")
    private String estadoIngles2;
    @Column(name = "estado_ingles3")
    private String estadoIngles3;
    @Column
    private String observacion;
    @Column
    private String estado;
    @ManyToOne
    @JoinColumn(name = "id_ficha", referencedColumnName = "id_ficha")
    private Ficha ficha;
}
