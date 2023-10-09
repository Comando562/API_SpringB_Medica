package med.voll.api.domain.consulta;

import java.time.LocalDateTime;
//DTO : Son objetos para transportar datos

public record DatosDetalleConsulta(Long id, Long idPaciente, Long idMedico, LocalDateTime fecha) {

    public DatosDetalleConsulta(Consulta consulta) {
        this(consulta.getId(),consulta.getPaciente().getId(), consulta.getMedico().getId(), consulta.getFecha());
    }
}
