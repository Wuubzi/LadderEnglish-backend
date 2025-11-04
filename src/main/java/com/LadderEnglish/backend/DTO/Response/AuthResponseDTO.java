package com.LadderEnglish.backend.DTO.Response;

import lombok.Data;

@Data
public class AuthResponseDTO {
    private int status;
    private String message;
    private String url;
    private String token;
    private String rol;
    private String timestamp;
}
