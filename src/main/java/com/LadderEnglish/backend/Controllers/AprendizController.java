package com.LadderEnglish.backend.Controllers;

import com.LadderEnglish.backend.DTO.Request.AprendizRequestDTO;
import com.LadderEnglish.backend.DTO.Response.ResponseDTO;
import com.LadderEnglish.backend.Models.Aprendiz;
import com.LadderEnglish.backend.Services.AprendizService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/aprendiz")
public class AprendizController {

    private final AprendizService aprendizService;

    public AprendizController(AprendizService aprendizService) {
        this.aprendizService = aprendizService;
    }


    @GetMapping("/obtenerAprendices")
    public ResponseEntity<List<Aprendiz>> obtenerAprendices(@RequestParam  Long idFicha) {
        return new ResponseEntity<>(aprendizService.obtenerAprendices(idFicha), HttpStatus.OK);
    }

    @GetMapping("/obtenerAprendiz")
    public ResponseEntity<Aprendiz> obtenerAprendiz(@RequestParam Long idAprendiz) {
        return new ResponseEntity<>(aprendizService.obtenerAprendiz(idAprendiz), HttpStatus.OK);
    }

    @PostMapping("/añadirAprendiz")
    public ResponseEntity<ResponseDTO> añadirAprendiz(@Valid @RequestBody AprendizRequestDTO aprendizRequestDTO, HttpServletRequest request) {
        return new ResponseEntity<>(aprendizService.añadirAprendiz(aprendizRequestDTO, request), HttpStatus.OK);
    }

    @PostMapping("/cargarAprendices")
    public ResponseEntity<ResponseDTO> cargarAprendices(HttpServletRequest request, @RequestParam Long idFicha, MultipartFile file) throws IOException {
        return new ResponseEntity<>(aprendizService.cargarAprendices(request, idFicha,file), HttpStatus.OK);
    }

    @GetMapping("/exportarAprendices")
    public ResponseEntity<byte[]> exportarAprendices(@RequestParam Long idFicha) {
        return aprendizService.exportarAprendices(idFicha);
    }
    
    @PutMapping("/editarAprendiz")
    public ResponseDTO editarAprendiz(
            @RequestParam Long idAprendiz,
            @RequestBody AprendizRequestDTO aprendizRequestDTO,
            HttpServletRequest request) {
        return aprendizService.editarAprendiz(idAprendiz, aprendizRequestDTO, request);
    }

}
