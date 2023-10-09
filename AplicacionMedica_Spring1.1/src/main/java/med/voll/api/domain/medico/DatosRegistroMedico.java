package med.voll.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import med.voll.api.domain.direccion.DatosDireccion;
//DTO : Son objetos para transportar datos
public record DatosRegistroMedico(

        @NotBlank //Validaciones , que no llege en blanco a la bd
        String nombre,
        @NotBlank
        @Email // Valida que el formato sea email
        String email,
        @NotBlank
        @Size(min = 0, max = 15) //Valida el rango  de longitud permitido
        String telefono,
        @NotBlank
        @Pattern(regexp = "\\d{4,6}") // Validacion que sea de 4 a 6 digitos, regex por que es exprecion regular
        String documento,
        @NotNull
        Especialidad especialidad,
        @NotNull
        @Valid //Se implementara la validacion en MedicoController con esta anotacion y spring lo mape como validacion
        DatosDireccion direccion) {
}

/*Si quiseramos personalizar mas con mensajes podemos hacer lo siguiente :
*  @NotBlank(message = "Nombre es obligatorio")
    String nombre,

    @NotBlank(message = "Email es obligatorio")
    @Email(message = "Formato de email es inválido")
    String email,

    @NotBlank(message = "Teléfono es obligatorio")
    String telefono,
    *  @NotBlank(message = "CRM es obligatorio")
    @Pattern(regexp = "\\d{4,6}", message = "Formato do CRM es inválido")
    String crm,

    @NotNull(message = "Especialidad es obligatorio")
    Especialidad especialidad,

    @NotNull(message = "Datos de dirección son obligatorios")
    @Valid DatosDireccion direccion) {}
    *
    *
    * Otra forma es aislar los mensajes en un archivo de propiedades, que debe tener el nombre ValidationMessages.properties
    *  y estar creado en el directorio src/main/resources:

nombre.obligatorio=El nombre es obligatorio
email.obligatorio=Correo electrónico requerido
email.invalido=El formato del correo electrónico no es válido
phone.obligatorio=Teléfono requerido
crm.obligatorio=CRM es obligatorio
crm.invalido=El formato CRM no es válido
especialidad.obligatorio=La especialidad es obligatoria
address.obligatorio=Los datos de dirección son obligatorios
*
*
* Y, en las anotaciones, indicar la clave de las propiedades por el propio atributo message, delimitando con los caracteres { e }:

public record DatosRegistroMedico(
    @NotBlank(message = "{nombre.obligatorio}")
    String nombre,

    @NotBlank(message = "{email.obligatorio}")
    @Email(message = "{email.invalido}")
    String email,

    @NotBlank(message = "{telefono.obligatorio}")
    String telefono,

    @NotBlank(message = "{crm.obligatorio}")
    @Pattern(regexp = "\\d{4,6}", message = "{crm.invalido}")
    String crm,

    @NotNull(message = "{especialidad.obligatorio}")
    Especialidad especialidad,

    @NotNull(message = "{direccion.obligatorio}")
    @Valid DatosDireccion direccion) {}*/