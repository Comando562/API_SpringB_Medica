package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/*AQUI VERIFICAMOS:
* -No permitir agendar citas con pacientes inactivos en el sistema;*/

@Component
public class PacienteActivo implements ValidadorDeConsultas{

    @Autowired
    private PacienteRepository repository;

    public void validar(DatosAgendarConsulta datos){ //Verifica si el paciente esta activo
        if(datos.idPaciente()==null){
            return;
        }
        var pacienteActivo=repository.findActivoById(datos.idPaciente()); //En caso el id de paciente no sea nulo se busca en el repositorio

        if(!pacienteActivo){ //si paciente activo es igual a falso
            throw new ValidationException("No se puede permitir agendar citas con pacientes inactivos en el sistema");
        }
    }
}
