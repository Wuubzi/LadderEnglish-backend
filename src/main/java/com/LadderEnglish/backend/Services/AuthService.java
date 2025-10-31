package com.LadderEnglish.backend.Services;

import com.LadderEnglish.backend.DTO.Request.AuthRequestDTO;
import com.LadderEnglish.backend.DTO.Response.AuthResponseDTO;
import com.LadderEnglish.backend.Models.Usuario;
import com.LadderEnglish.backend.Repositories.UsuarioRepository;
import com.LadderEnglish.backend.Utils.CustomUserDetails;
import com.LadderEnglish.backend.Utils.JwtService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UsuarioRepository usuarioRepository,
                       JwtService jwtService,
                       AuthenticationManager authenticationManager,
                       PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponseDTO login(AuthRequestDTO authRequestDTO, HttpServletRequest request) {
        try {
            Optional<Usuario> usuarioOptional = usuarioRepository.findByTipoDocumentoAndNumeroDocumento(authRequestDTO.getTipo_documento(), Long.valueOf(authRequestDTO.getNumero_documento()));
            if (usuarioOptional.isEmpty()) {
               throw new EntityNotFoundException("Usuario no encontrado");
            }
            Usuario usuario = usuarioOptional.get();
            if (!passwordEncoder.matches(authRequestDTO.getContrasena(), usuario.getContrasena())) {
                throw new BadCredentialsException("Contraseña incorrecta");
            }

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequestDTO.getNumero_documento(),
                            authRequestDTO.getContrasena()
                    )
            );

            CustomUserDetails userDetails = new CustomUserDetails(usuarioOptional.get());
            String token = jwtService.generateToken(userDetails);

            AuthResponseDTO authResponseDTO = new AuthResponseDTO();
            authResponseDTO.setStatus(200);
            authResponseDTO.setMessage("Inicio de sesión exitoso");
            authResponseDTO.setUrl(request.getRequestURI().replace(request.getContextPath(), ""));
            authResponseDTO.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
            authResponseDTO.setToken(token);
            return authResponseDTO;

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
