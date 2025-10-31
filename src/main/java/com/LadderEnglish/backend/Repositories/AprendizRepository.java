package com.LadderEnglish.backend.Repositories;

import com.LadderEnglish.backend.Models.Aprendiz;
import com.LadderEnglish.backend.Models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AprendizRepository extends JpaRepository<Aprendiz, Long> {
    List<Aprendiz> findAllByFicha_IdFicha(Long idFicha);
}
