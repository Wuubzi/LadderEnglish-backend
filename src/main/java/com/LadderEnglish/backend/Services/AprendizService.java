package com.LadderEnglish.backend.Services;

import com.LadderEnglish.backend.DTO.Request.AprendizRequestDTO;
import com.LadderEnglish.backend.DTO.Response.AuthResponseDTO;
import com.LadderEnglish.backend.DTO.Response.ResponseDTO;
import com.LadderEnglish.backend.Models.Aprendiz;
import com.LadderEnglish.backend.Models.Ficha;
import com.LadderEnglish.backend.Repositories.AprendizRepository;
import com.LadderEnglish.backend.Repositories.FichaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class AprendizService {

    private final AprendizRepository aprendizRepository;
    private final FichaRepository fichaRepository;

    @Autowired
    public AprendizService(AprendizRepository aprendizRepository, FichaRepository fichaRepository) {
        this.aprendizRepository = aprendizRepository;
        this.fichaRepository = fichaRepository;
    }

    public List<Aprendiz> obtenerAprendices(Long idFicha) {
        return aprendizRepository.findAllByFicha_IdFicha(idFicha);
    }

    // Obtener un aprendiz por ID
    public Aprendiz obtenerAprendiz(Long idAprendiz) {
        try {
            Optional<Aprendiz> aprendizOptional = aprendizRepository.findById(idAprendiz);
            if (aprendizOptional.isEmpty()) {
                throw new EntityNotFoundException("Aprendiz no encontrado");
            }

            return aprendizOptional.get();
        } catch (Exception e) {
          throw new RuntimeException(e);
        }
    }

    public ResponseDTO editarAprendiz(Long idAprendiz, AprendizRequestDTO aprendizRequestDTO, HttpServletRequest request) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setTimestamp(java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ISO_DATE_TIME));
        responseDTO.setUrl(request.getRequestURI().replace(request.getContextPath(), ""));

        try {
            // Verificar si el aprendiz existe
            Aprendiz aprendizExistente = aprendizRepository.findById(idAprendiz)
                    .orElseThrow(() -> new EntityNotFoundException("Aprendiz no encontrado"));

            // Verificar si la ficha existe
            Ficha ficha = fichaRepository.findById(aprendizRequestDTO.getIdFicha())
                    .orElseThrow(() -> new EntityNotFoundException("Ficha no encontrada"));

            // Actualizar datos
            aprendizExistente.setTipoDocumento(aprendizRequestDTO.getTipoDocumento());
            aprendizExistente.setNumeroDocumento(aprendizRequestDTO.getNumeroDocumento());
            aprendizExistente.setNombres(aprendizRequestDTO.getNombres());
            aprendizExistente.setApellidos(aprendizRequestDTO.getApellidos());
            aprendizExistente.setCelular(aprendizRequestDTO.getCelular());
            aprendizExistente.setCorreo(aprendizRequestDTO.getCorreo());
            aprendizExistente.setEstado(aprendizRequestDTO.getEstado());
            aprendizExistente.setEstadoIngles1(aprendizRequestDTO.getEstadoIngles1());
            aprendizExistente.setEstadoIngles2(aprendizRequestDTO.getEstadoIngles2());
            aprendizExistente.setEstadoIngles3(aprendizRequestDTO.getEstadoIngles3());
            aprendizExistente.setFicha(ficha);

            aprendizRepository.save(aprendizExistente);

            responseDTO.setStatus(200);
            responseDTO.setMessage("Aprendiz actualizado correctamente.");
            return responseDTO;

        } catch (EntityNotFoundException e) {
            responseDTO.setStatus(404);
            responseDTO.setMessage(e.getMessage());
        } catch (Exception e) {
            responseDTO.setStatus(500);
            responseDTO.setMessage("Error al actualizar aprendiz: " + e.getMessage());
        }

        return responseDTO;
    }

    public ResponseDTO añadirAprendiz(AprendizRequestDTO aprendizRequestDTO, HttpServletRequest request) {

        ResponseDTO responseDTO = new ResponseDTO();

        try {
            Aprendiz aprendiz = getAprendiz(aprendizRequestDTO);
            Ficha ficha = fichaRepository.findById(aprendizRequestDTO.getIdFicha()).orElse(null);
            aprendiz.setFicha(ficha);
            aprendizRepository.save(aprendiz);
            responseDTO.setStatus(200);
            responseDTO.setUrl(request.getRequestURI().replace(request.getContextPath(), ""));
            responseDTO.setTimestamp(java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ISO_DATE_TIME));
            responseDTO.setMessage("Aprendiz registrado correctamente.");
            return responseDTO;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public ResponseDTO cargarAprendices(HttpServletRequest request, @RequestParam Long idFicha, MultipartFile file) throws IOException {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        responseDTO.setUrl(request.getRequestURI().replace(request.getContextPath(), ""));

        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = WorkbookFactory.create(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            Ficha ficha = fichaRepository.findById(idFicha)
                    .orElseThrow(() -> new EntityNotFoundException("Ficha no encontrada con ID: " + idFicha));

            int count = 0;

            // Comienza desde la fila 5 (en tu archivo los datos empiezan ahí)
            for (int i = 5; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                // Leer las celdas con seguridad (permite valores vacíos)
                String tipoDocumento = getCellValueOrEmpty(row.getCell(0));
                String numeroDocumento = getCellValueOrEmpty(row.getCell(1));
                String nombres = getCellValueOrEmpty(row.getCell(2));
                String apellidos = getCellValueOrEmpty(row.getCell(3));
                String celular = getCellValueOrEmpty(row.getCell(4));
                String correo = getCellValueOrEmpty(row.getCell(5));
                String estado = getCellValueOrEmpty(row.getCell(6));
                String ingles1 = getCellValueOrEmpty(row.getCell(7));
                String ingles2 = getCellValueOrEmpty(row.getCell(8));
                String ingles3 = getCellValueOrEmpty(row.getCell(9));

                // Saltar si faltan datos críticos (número de documento mínimo requerido)
                if (numeroDocumento.isEmpty() || nombres.isEmpty() || apellidos.isEmpty())
                    continue;

                Aprendiz aprendiz = new Aprendiz();
                aprendiz.setTipoDocumento(tipoDocumento.isEmpty() ? null : tipoDocumento);

                // Convertir número de documento solo si no está vacío
                try {
                    aprendiz.setNumeroDocumento(Long.valueOf(numeroDocumento));
                } catch (NumberFormatException e) {
                    continue; // Saltar si el número de documento no es válido
                }

                aprendiz.setNombres(nombres);
                aprendiz.setApellidos(apellidos);
                aprendiz.setCelular(celular.isEmpty() ? null : celular);
                aprendiz.setCorreo(correo.isEmpty() ? null : correo);
                aprendiz.setEstado(estado.isEmpty() ? null : estado);
                aprendiz.setEstadoIngles1(ingles1.isEmpty() ? null : ingles1);
                aprendiz.setEstadoIngles2(ingles2.isEmpty() ? null : ingles2);
                aprendiz.setEstadoIngles3(ingles3.isEmpty() ? null : ingles3);
                aprendiz.setFicha(ficha);

                aprendizRepository.save(aprendiz);
                count++;
            }

            responseDTO.setStatus(200);
            responseDTO.setMessage("Se importaron " + count + " aprendices correctamente.");
            return responseDTO;

        } catch (EntityNotFoundException e) {
            responseDTO.setStatus(404);
            responseDTO.setMessage(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO.setStatus(500);
            responseDTO.setMessage("Error al importar el archivo: " + e.getMessage());
        }

        return responseDTO;
    }

    private String getCellValueOrEmpty(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                }
                // Convierte números a string sin notación científica
                return String.valueOf((long) cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return "";
            default:
                return "";
        }
    }

    private static Aprendiz getAprendiz(AprendizRequestDTO aprendizRequestDTO) {
        Aprendiz aprendiz = new Aprendiz();
        aprendiz.setTipoDocumento(aprendizRequestDTO.getTipoDocumento());
        aprendiz.setNumeroDocumento(aprendizRequestDTO.getNumeroDocumento());
        aprendiz.setNombres(aprendizRequestDTO.getNombres());
        aprendiz.setApellidos(aprendizRequestDTO.getApellidos());
        aprendiz.setCelular(aprendizRequestDTO.getCelular());
        aprendiz.setCorreo(aprendizRequestDTO.getCorreo());
        aprendiz.setEstadoIngles1(aprendizRequestDTO.getEstadoIngles1());
        aprendiz.setEstadoIngles2(aprendizRequestDTO.getEstadoIngles2());
        aprendiz.setEstadoIngles3(aprendizRequestDTO.getEstadoIngles3());
        aprendiz.setEstado(aprendizRequestDTO.getEstado());
        return aprendiz;
    }
}
