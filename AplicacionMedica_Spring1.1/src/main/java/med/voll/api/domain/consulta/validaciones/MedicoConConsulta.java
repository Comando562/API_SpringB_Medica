package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/*AQUI VERIFICAMOS:
* -No permitir programar una cita con un médico que ya tiene otra cita programada en la misma fecha/hora;
* */

@Component
public class MedicoConConsulta implements ValidadorDeConsultas{

    @Autowired
    private ConsultaRepository repository;

    public void validar(DatosAgendarConsulta datos) {
        if(datos.idMedico()==null) //verifica que el id no sea nulo
            return;

        //Verificamos si existe una ID para ese medico con una fecha entre ese intervalo de horario
        var medicoConConsulta= repository.existsByMedicoIdAndFecha(datos.idMedico(),datos.fecha());

        if(medicoConConsulta){//Si existe una consulta para el medico enviamos un msn de error
            throw new ValidationException("este medico ya tiene una consulta en ese horario");
        }
    }
}
