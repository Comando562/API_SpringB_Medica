package med.voll.api.infra.errores;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//AQUI SPRING VA A MAPEAR LOS ERRORES Y CON EL RESPONSEENTITY PODEMOS MANEJAR MAS LOS ERRORES

@RestControllerAdvice
public class TratadorDeErrores {

    @ExceptionHandler(EntityNotFoundException.class) //Como parametro el tipo de excepcion que yo quiero tratar (En consola)
    public ResponseEntity tratarError404(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarError400(MethodArgumentNotValidException e){
        var errores = e.getFieldErrors().stream().map(DatosErrorValidacion::new).toList();//Muestra una lista de errores de la excepcion /DTO
        return ResponseEntity.badRequest().body(errores);
    }

    @ExceptionHandler(ValidacionDeIntegridad.class)  // verifica que el id de paciente y medico exista caso contrario lanza exception
    public ResponseEntity errorHandlerValidacionesIntegridad(Exception e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(ValidationException.class) // verifica que tenga horario asignado el doctor para consultas
    public ResponseEntity errorHandlerValidacionesDeNegocio(Exception e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }


    //DTO Para Excepcion de Metodos no Validos
    private record DatosErrorValidacion(String campo, String error){
        public DatosErrorValidacion(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }

}

/*La anotación @RestControllerAdvice en Spring Boot se utiliza para definir una clase que proporciona manejo global de
excepciones en una aplicación Spring que utiliza controladores REST (RestController). Esta anotación se usa comúnmente
en combinación con otras anotaciones y permite centralizar la lógica de manejo de excepciones para todos los controladores REST en una aplicación.

Cuando una excepción se produce en un controlador REST, Spring Boot busca una clase anotada con @RestControllerAdvice
para manejar esa excepción en lugar de manejarla directamente en el controlador. Esto permite un manejo más uniforme y
coherente de las excepciones en toda la aplicación.

Un uso típico de @RestControllerAdvice implica la definición de métodos en esta clase anotada con @ExceptionHandler que
 manejan excepciones específicas o genéricas*/