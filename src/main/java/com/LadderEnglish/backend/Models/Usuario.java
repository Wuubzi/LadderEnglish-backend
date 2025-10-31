package com.LadderEnglish.backend.Models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_usuario;
    @Column(name = "tipo_documento")
    private String tipoDocumento;
    @Column(name = "numero_documento")
    private Long numeroDocumento;
    @Column
    private String nombres;
    @Column
    private String apellidos;
    @Column
    private String contrasena;
    @Column
    private String rol;
}
