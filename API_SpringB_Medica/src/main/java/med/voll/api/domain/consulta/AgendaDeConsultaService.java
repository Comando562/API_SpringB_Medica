package med.voll.api.domain.consulta;

import med.voll.api.domain.consulta.desafio.ValidadorCancelamientoDeConsulta;
import med.voll.api.domain.consulta.validaciones.ValidadorDeConsultas;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/*SOLO TIENE COMO RESPONSABILIDAD RECIBIR PARAMETROS Y VALIDAR*/

@Service
public class AgendaDeConsultaService {

    @Autowired //Para poder usar los distintos metodos de esta interfaz
    private PacienteRepository pacienteRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired //Extencion de validadores
    List<ValidadorDeConsultas> validadores;
    @Autowired //Extencion de validadores
    List<ValidadorCancelamientoDeConsulta> validadoresCancelamiento;
    public DatosDetalleConsulta agendar(DatosAgendarConsulta datos){
        //Validacion de integridad para el paciente
        if(!pacienteRepository.findById(datos.idPaciente()).isPresent()){  //Si no encuentra el  id del paciente enviar error
            throw new ValidacionDeIntegridad("este id para el paciente no fue encontrado");
        }

        if(datos.idMedico()!=null && !medicoRepository.existsById(datos.idMedico())){ //Si no encuentra el id del medico enviar error
            throw new ValidacionDeIntegridad("este id para el medico no fue encontrado");
        }

        validadores.forEach(v-> v.validar(datos));//recorre todos los elementos que se encuentran dentro de esa lista de val, que creamos

        var paciente = pacienteRepository.findById(datos.idPaciente()).get();

        var medico = seleccionarMedico(datos);

        if(medico==null){
            throw new ValidacionDeIntegridad("no existen medicos disponibles para este horario y especialidad");
        }

        var consulta = new Consulta(medico,paciente,datos.fecha());

        consultaRepository.save(consulta);

        return new DatosDetalleConsulta(consulta);

    }

    public void cancelar(DatosCancelamientoConsulta datos){
        if(!consultaRepository.existsById(datos.idConsulta())){
            throw new ValidacionDeIntegridad("Id de la consulta informado no existe!");
        }

        validadoresCancelamiento.forEach(v -> v.validar(datos));

        var consulta= consultaRepository.getReferenceById(datos.idConsulta());
                consulta.cancelar(datos.motivo());
    }


    //Algoritmo para seleccionar un medico aleatorio que se encuentre disponible en la fecha y con las necesidades del paciente
    private Medico seleccionarMedico(DatosAgendarConsulta datos) {
        if(datos.idMedico()!=null){
            return medicoRepository.getReferenceById(datos.idMedico());
        }
        if(datos.especialidad()==null){
            throw new ValidacionDeIntegridad("debe seleccionarse una especialidad para el medico");
        }

        //Si no es nula la especialidad enviamos un medico aleatoria para la especialidad
        return medicoRepository.seleccionarMedicoConEspecialidadEnFecha(datos.especialidad(),datos.fecha());
    }
}
/*VALIDACIONES QUE SE MANEJAN EN EL PAQUETE VALIDACIONES:
*-El horario de atención de la clínica es de lunes a sábado, de 07:00 a 19:00 horas
-Las consultas tienen una duración fija de 1 hora;
-Las consultas deben programarse con al menos 30 minutos de anticipación;
-No permitir agendar citas con pacientes inactivos en el sistema;
-No permitir programar citas con médicos inactivos en el sistema;
-No permita programar más de una consulta en el mismo día para el mismo paciente;
-No permitir programar una cita con un médico que ya tiene otra cita programada en la misma fecha/hora;
-La elección de un médico es opcional, en cuyo caso de que no exista el id el sistema debe elegir aleatoriamente un
*  médico que esté disponible en la fecha/hora ingresada.*/