package com.LadderEnglish.backend.Repositories;

import com.LadderEnglish.backend.Models.Ficha;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FichaRepository extends JpaRepository<Ficha, Long>  {
    List<Ficha> findAllByEstado(String estado);
}
