package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;

import med.voll.api.domain.consulta.*;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.paciente.Paciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller //Indicamos a Spring que esto es un controller
@ResponseBody //Retorna tipos de datos Json
@RequestMapping("/consultas") //La ruta http
@SecurityRequirement(name = "bearer-key")  //Reuquerimiento de Seg que va a solicitar el JWT
public class ConsultaController {

    @Autowired // Indicamos a Spring que es la Anotacion que sera usada mas adelante sin necesidad de repetirlo
    private AgendaDeConsultaService service;

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DatosAgendarConsulta datos){

        var response = service.agendar(datos);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{idConsulta}")
    @Transactional
    public ResponseEntity cancelar(
            @PathVariable @Valid Long idConsulta,
            @RequestBody @Valid DatosCancelamientoConsulta datosCancelamiento
    ) {
        // Actualizar el idConsulta en la instancia existente
        datosCancelamiento = DatosCancelamientoConsulta.setIdConsulta(datosCancelamiento, idConsulta);
        service.cancelar(datosCancelamiento);
        return ResponseEntity.noContent().build();
    }
    /*public ResponseEntity cancelar(
            @PathVariable @Valid Long idConsulta,
            @RequestBody @Valid DatosCancelamientoConsulta datosCancelamiento
    ) {
        datosCancelamiento.setIdConsulta(idConsulta);
        service.cancelar(datosCancelamiento);
        return ResponseEntity.noContent().build();
    }
    /*@DeleteMapping("/{idConsulta}")
    @Transactional
    public ResponseEntity cancelar(
            @PathVariable @Valid Long idConsulta,
            @RequestParam(name = "motivo", required = true) @Valid MotivoCancelamiento motivo
    ) {
        var consulta = new DatosCancelamientoConsulta(idConsulta, motivo);
        service.cancelar(consulta);
        return ResponseEntity.noContent().build();
    }*/
    /*Metodo proporcionado por Alura :
    * @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelarConsulta(@PathVariable Long id) {
        consultaService.cancelarConsulta(id);
        return ResponseEntity.noContent().build();
    }*/
}