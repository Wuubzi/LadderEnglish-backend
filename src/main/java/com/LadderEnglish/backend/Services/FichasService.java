package com.LadderEnglish.backend.Services;

import com.LadderEnglish.backend.DTO.Request.FichaRequestDTO;
import com.LadderEnglish.backend.DTO.Response.ResponseDTO;
import com.LadderEnglish.backend.Models.Ficha;
import com.LadderEnglish.backend.Repositories.FichaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class FichasService {
    private final FichaRepository fichaRepository;

    @Autowired
    public FichasService(FichaRepository fichaRepository) {
        this.fichaRepository = fichaRepository;
    }

    public List<Ficha> obtenerFichasActivas() {
       return fichaRepository.findAllByEstado("activo");
    }

    public List<Ficha> obtenerFichas() {
        return fichaRepository.findAll();
    }

    public Ficha obtenerFicha(Long idFicha) {
        return fichaRepository.findById(idFicha).orElse(null);
    }

    public ResponseDTO crearFicha(FichaRequestDTO fichaRequestDTO, String url) {
        Ficha ficha = new Ficha();
        ficha.setNumeroFicha(fichaRequestDTO.getNumeroFicha());
        ficha.setNombreFicha(fichaRequestDTO.getNombreFicha());
        ficha.setEstado(fichaRequestDTO.getEstado());
        fichaRepository.save(ficha);

        ResponseDTO response = new ResponseDTO();
        response.setStatus(201);
        response.setMessage("Ficha creada exitosamente");
        response.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        response.setUrl(url);

        return response;
    }

    public ResponseDTO editarFicha(FichaRequestDTO fichaRequestDTO, Long idFicha, String url) {
        Ficha fichaExistente = fichaRepository.findById(idFicha).orElse(null);

        ResponseDTO response = new ResponseDTO();
        response.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        response.setUrl(url);

        if (fichaExistente == null) {
            response.setStatus(404);
            response.setMessage("Ficha no encontrada");
            return response;
        }

        fichaExistente.setNumeroFicha(fichaRequestDTO.getNumeroFicha());
        fichaExistente.setNombreFicha(fichaRequestDTO.getNombreFicha());
        fichaExistente.setEstado(fichaRequestDTO.getEstado());
        fichaRepository.save(fichaExistente);

        response.setStatus(200);
        response.setMessage("Ficha actualizada exitosamente");
        return response;
    }
}
