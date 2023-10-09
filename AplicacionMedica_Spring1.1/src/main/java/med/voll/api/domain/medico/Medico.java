package med.voll.api.domain.medico;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.direccion.Direccion;

//MAPEAMIENTO DE ENTIDADES | JPA
@Table(name = "medicos") //Especifica el nombre de la tabla en la db a la que se  va a mapear la entidad "medicos"
@Entity(name = "Medico")  //Se puede mapear  a una tabla en una bd
@Getter
@NoArgsConstructor  //Genera un constructor  sin argumentos
@AllArgsConstructor    //Genera un cons que acepta todos los campos de la clase como argumentos
@EqualsAndHashCode(of = "id")  //Metodos Equals and H.Code para la clase, basados en el campo ID /Comp entre medicos
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private String documento;

    private Boolean activo; //Verifica si esta activo o no el medico
    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;
    @Embedded //Direccion lo usara paciente y medico, para no duplicar codigo hace esto que se duplique
    private Direccion direccion;

    public Medico(DatosRegistroMedico datosRegistroMedico) {
        this.activo = true;
        this.nombre = datosRegistroMedico.nombre();
        this.email = datosRegistroMedico.email();
        this.documento = datosRegistroMedico.documento();
        this.telefono = datosRegistroMedico.telefono();
        this.especialidad = datosRegistroMedico.especialidad();
        this.direccion = new Direccion(datosRegistroMedico.direccion());
    }

    public void actualizarDatos(DatosActualizarMedico datosActualizarMedico) {
        if (datosActualizarMedico.nombre() != null) {
            this.nombre = datosActualizarMedico.nombre();
        }
        if (datosActualizarMedico.documento() != null) {
            this.documento = datosActualizarMedico.documento();
        }
        if (datosActualizarMedico.direccion() != null) {
            this.direccion = direccion.actualizarDireccion(datosActualizarMedico.direccion());
        }
    }
    /*Se pueden actualizar solo estos datos aunque solo sea nombre, documento , El id nunca debe de llegar vacio*/

    public void desactivarMedico() {
        this.activo = false;
    }
}

/*@GeneratedValue se utiliza para especificar cómo se generan los valores de la clave primaria de la entidad.
* strategy = GenerationType.IDENTITY indica que los valores de la clave primaria se generan automáticamente por la
*  base de datos y se utilizan columnas de identidad (como AUTO_INCREMENT en MySQL) para generar los valores de la clave primaria.
* - @Enumerated(EnumType.STRING):
Se utiliza para especificar cómo se debe mapear una enumeración en la base de datos.
EnumType.STRING indica que los valores de la enumeración se almacenan como cadenas en la base de datos en lugar de enteros.
@Embedded:

@Embedded se utiliza para indicar que una propiedad en la entidad debe ser tratada como una entidad incrustada (una entidad dentro de otra entidad).
Esto se usa cuando deseas que un campo de la entidad contenga subcampos que también deben mapearse a la base de datos.*/

