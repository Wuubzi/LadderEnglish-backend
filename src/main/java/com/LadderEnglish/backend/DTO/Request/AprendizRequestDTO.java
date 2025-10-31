package com.LadderEnglish.backend.DTO.Request;

import lombok.Data;

@Data
public class AprendizRequestDTO {
    private String tipoDocumento;
    private Long numeroDocumento;
    private String nombres;
    private String apellidos;
    private String celular;
    private String estado;
    private String correo;
    private String estadoIngles1;
    private String estadoIngles2;
    private String estadoIngles3;
    private Long idFicha;
}
