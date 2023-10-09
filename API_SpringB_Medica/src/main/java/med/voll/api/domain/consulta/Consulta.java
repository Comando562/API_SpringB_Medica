package med.voll.api.domain.consulta;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.paciente.Paciente;

import java.time.LocalDateTime;

@Table(name = "consultas")
@Entity(name = "Consulta")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id")
    private Medico medico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    private LocalDateTime fecha;

    @Column(name = "motivo_cancelamiento")
    @Enumerated(EnumType.STRING)
    private MotivoCancelamiento motivoCancelamiento;

    public Consulta( Medico medico, Paciente paciente, LocalDateTime fecha) {
        this.medico=medico;
        this.paciente=paciente;
        this.fecha=fecha;
    }

    public void cancelar(MotivoCancelamiento motivo) {
        this.motivoCancelamiento = motivo;
    }


}

/*@GeneratedValue: Indica que el valor de la columna se generará automáticamente.

strategy = GenerationType.IDENTITY: Especifica la estrategia de generación de valores que se utilizará. En este caso,
 se utiliza la estrategia de identidad.

La estrategia de identidad (GenerationType.IDENTITY) se asocia comúnmente con bases de datos que admiten la generación
 automática de valores de clave primaria, como MySQL o PostgreSQL. Cuando se utiliza esta estrategia, la base de datos
  se encarga de generar automáticamente valores únicos para la columna de clave primaria, generalmente mediante el uso
   de autoincremento o secuencias.
   En este ejemplo, la columna id se configuraría para que se genere automáticamente por la base de datos cuando se
   inserte una nueva fila en la tabla correspondiente a esta entidad.

   fetch = FetchType.LAZY: Esta parte de la anotación configura cómo se debe cargar la entidad relacionada
    cuando se recupera la entidad principal. En este caso, se utiliza la carga perezosa (lazy fetching), lo que
     significa que la entidad relacionada no se cargará desde la base de datos hasta que sea necesario. Esto puede ser
      beneficioso en términos de rendimiento, ya que evita la recuperación innecesaria de datos relacionados.

@JoinColumn(name = "medico_id"): Esta anotación se utiliza para especificar el nombre de la columna en la tabla de la
 entidad actual que se utilizará como clave externa para la relación Many-to-One. En este ejemplo, se especifica que la
  columna "medico_id" se utilizará como clave externa para la relación con la entidad relacionada.

En resumen, este código se utiliza para establecer una relación de muchos a uno entre la entidad actual y otra entidad
relacionada a través de una clave externa llamada "medico_id". Además, configura la carga perezosa para la entidad
 relacionada, lo que puede ser útil para optimizar el rendimiento de las consultas a la base de datos cuando se trabaja
  con grandes conjuntos de datos.*/