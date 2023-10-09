package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
/*AQUI VERIFICAMOS:
--Las consultas deben programarse con al menos 30 minutos de anticipación;
* La hora en el momento en que se esta realizando esa consulta y la hora en la que se esta registrando esa consulta
* si laa diferencia es de menos de treinta minutos no se realizara */

@Component
public class HorarioDeAnticipacion implements ValidadorDeConsultas{

    public void validar(DatosAgendarConsulta datos) {
        var ahora = LocalDateTime.now();  //Obteniendo momento actual
        var horaDeConsulta= datos.fecha();

        var diferenciaDe30Min= Duration.between(ahora,horaDeConsulta).toMinutes()<30; //comparacion de menos de 30 min no se realizara
        if(diferenciaDe30Min){
            throw new ValidationException("Las consultas deben programarse con al menos 30 minutos de anticipación");
        }
    }
}
