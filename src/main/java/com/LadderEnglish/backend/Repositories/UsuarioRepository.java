package com.LadderEnglish.backend.Repositories;

import com.LadderEnglish.backend.Models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByNumeroDocumento(Long documento);
    Optional<Usuario> findByTipoDocumentoAndNumeroDocumento(String tipoDocumento, Long documento);
}
