package com.LadderEnglish.backend.Controllers;

import com.LadderEnglish.backend.DTO.Request.FichaRequestDTO;
import com.LadderEnglish.backend.DTO.Response.AuthResponseDTO;
import com.LadderEnglish.backend.DTO.Response.ResponseDTO;
import com.LadderEnglish.backend.Models.Ficha;
import com.LadderEnglish.backend.Services.FichasService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/fichas")
public class FichasController {

    private final FichasService fichasService;

    @Autowired
    public FichasController(FichasService fichasService) {
        this.fichasService = fichasService;
    }

    @GetMapping("/obtenerFichas")
    public List<Ficha> obtenerFichas() {
        return fichasService.obtenerFichas();
    }

    @GetMapping("/obtenerFicha")
    public Ficha obtenerFicha(@RequestParam Long idFicha) {
        return fichasService.obtenerFicha(idFicha);
    }

    @PostMapping("/añadirFicha")
    public ResponseDTO añadirFicha(@RequestBody FichaRequestDTO fichaRequestDTO, HttpServletRequest request) {
        return fichasService.crearFicha(fichaRequestDTO, request.getRequestURI());
    }

    @PutMapping("/editarFicha")
    public ResponseDTO editarFicha(@RequestBody FichaRequestDTO fichaRequestDTO, @RequestParam Long idFicha, HttpServletRequest request) {
        return fichasService.editarFicha(fichaRequestDTO, idFicha, request.getRequestURI());
    }

}
