package com.LadderEnglish.backend.Utils;

import com.LadderEnglish.backend.Models.Usuario;
import com.LadderEnglish.backend.Repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String numeroDocumento) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByNumeroDocumento(Long.valueOf(numeroDocumento))
                .orElseThrow(() ->
                        new UsernameNotFoundException("Usuario no encontrado con número de documento: " + numeroDocumento)
                );

        return new CustomUserDetails(usuario);
    }
}
