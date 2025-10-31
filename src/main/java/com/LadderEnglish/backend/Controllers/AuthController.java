package com.LadderEnglish.backend.Controllers;

import com.LadderEnglish.backend.DTO.Request.AuthRequestDTO;
import com.LadderEnglish.backend.DTO.Response.AuthResponseDTO;
import com.LadderEnglish.backend.Services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid  @RequestBody AuthRequestDTO authRequestDTO, HttpServletRequest request) {
        return new ResponseEntity<>(authService.login(authRequestDTO, request), HttpStatus.OK) ;
    }
}
