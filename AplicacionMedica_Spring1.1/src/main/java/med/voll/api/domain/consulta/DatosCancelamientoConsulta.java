package med.voll.api.domain.consulta;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.medico.Especialidad;

import java.time.LocalDateTime;

public record DatosCancelamientoConsulta(
        @NotNull
        Long idConsulta,

        @Enumerated(EnumType.STRING)
        MotivoCancelamiento motivo
        ) {
        // Constructor para crear una nueva instancia con el valor actualizado de idConsulta
        public DatosCancelamientoConsulta withIdConsulta(Long idConsulta) {
                return new DatosCancelamientoConsulta(idConsulta, this.motivo);
        }

        // Método estático para actualizar idConsulta en una instancia existente
        public static DatosCancelamientoConsulta setIdConsulta(
                DatosCancelamientoConsulta instanciaExistente,
                Long nuevoIdConsulta
        ) {
                return new DatosCancelamientoConsulta(nuevoIdConsulta, instanciaExistente.motivo());
        }
}
