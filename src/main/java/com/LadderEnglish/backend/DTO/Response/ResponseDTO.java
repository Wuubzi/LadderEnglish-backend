package com.LadderEnglish.backend.DTO.Response;

import lombok.Data;

@Data
public class ResponseDTO {
    private int status;
    private String message;
    private String timestamp;
    private String url;
}
