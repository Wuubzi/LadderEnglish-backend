package com.LadderEnglish.backend.DTO.Request;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AuthRequestDTO {
    @NotNull(message = "El tipo de documento no puede ser nulo.")
    @NotBlank(message = "El tipo de documento no puede estar vacío.")
    @Pattern(
            regexp = "^(CC|TI|PPT)$",
            message = "Tipo de documento inválido. Valores permitidos: CC, TI, PPT."
    )
    private String tipo_documento;

    @NotNull(message = "El número de documento no puede ser nulo.")
    @NotBlank(message = "El número de documento no puede estar vacío.")
    @Pattern(
            regexp = "^[0-9]{6,20}$",
            message = "Número de documento inválido. Debe contener sólo dígitos (6 a 20 caracteres)."
    )
    private String numero_documento;

    @NotNull(message = "La contraseña no puede ser nula.")
    @NotBlank(message = "La contraseña no puede estar vacía.")
    private String contrasena;
}
