package med.voll.api.domain.consulta;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.medico.Especialidad;

import java.time.LocalDateTime;
//DTO : Son objetos para transportar datos
public record DatosAgendarConsulta(

        @NotNull
        Long idPaciente,
        Long idMedico,
        @NotNull
        @Future
        LocalDateTime fecha,
        Especialidad especialidad) {

}

/*Record es un recurso que le permite representar una clase inmutable, que contiene solo atributos, constructor y
métodos de lectura, de una manera muy simple y ágil.
Este tipo de clase encaja perfectamente para representar clases DTO, ya que su objetivo es únicamente representar datos
 que serán recibidos o devueltos por la API, sin ningún tipo de comportamiento.*/