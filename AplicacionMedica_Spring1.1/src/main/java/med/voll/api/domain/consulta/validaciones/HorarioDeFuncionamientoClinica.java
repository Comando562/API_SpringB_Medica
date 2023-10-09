package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
/*VALIDACION QUE SE MANEJA AQUI:
* -El horario de atención de la clínica es de lunes a sábado, de 07:00 a 19:00 horas*/

@Component
public class HorarioDeFuncionamientoClinica  implements ValidadorDeConsultas{

    public void validar(DatosAgendarConsulta datos) {

        var domingo = DayOfWeek.SUNDAY.equals(datos.fecha().getDayOfWeek()); //verifica si la consulta es el dia domingo

        var antesdDeApertura=datos.fecha().getHour()<7; //verifica el horario de apertura
        var despuesDeCierre=datos.fecha().getHour()>19; //verifica despues de cierre
        if(domingo || antesdDeApertura || despuesDeCierre){
            throw  new ValidationException("El horario de atención de la clínica es de lunes a sábado, de 07:00 a 19:00 horas");
        }
    }
}

/*DayOfWeek.SUNDAY: DayOfWeek es una enumeración (enum) que generalmente se utiliza para representar los días de
 la semana en muchos lenguajes de programación. SUNDAY es una constante de esta enumeración y representa el domingo.*/