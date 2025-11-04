package com.LadderEnglish.backend.DTO.Request;

import lombok.Data;

@Data
public class FichaRequestDTO {
    private Long numeroFicha;
    private String nombreFicha;
    private String estado;
}
