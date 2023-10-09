package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/*AQUI VERIFICAMOS:
-No permita programar más de una consulta en el mismo día para el mismo paciente;
* El paciente exista dentro del intervalo en caso que no exista vamos  aenviar un mensaje de error */
@Component
public class PacienteSinConsulta implements ValidadorDeConsultas{

    @Autowired
    private ConsultaRepository repository; // COnsulta en el repo de consultas

    public void validar(DatosAgendarConsulta datos)  {
        var primerHorario = datos.fecha().withHour(7); //primer horario en que un paciente pueda agendar una consulta
        var ultimoHorario= datos.fecha().withHour(18);

        //Verificamos si existe una ID para ese paciente con una fecha entre ese intervalo de horario
        var pacienteConConsulta=repository.existsByPacienteIdAndFechaBetween(datos.idPaciente(),primerHorario,ultimoHorario);

        if(pacienteConConsulta){ //Si existe una agenda en repo de consultas enviamos un msn de error
            throw new ValidationException("el paciente ya tiene una consulta para ese dia");
        }

    }
}
