package med.voll.api.domain.medico;

import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.paciente.DatosRegistroPaciente;
import med.voll.api.domain.paciente.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest // Nos permite testear las capas de persistencia de BD
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//Indicamos que la BD que vamos a utilizar es externa y no voy a replazarla
@ActiveProfiles("test") //vollmed_api_test
class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository medicoRepository;

    //Cuando estemos realizando la preuba estara activo , realizando las operaciones en donde estara la instancia para hacer conexion DB
    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("deberia retornar nula cuando el medico se encuentre en consulta con otro paciente en ese horario")
//lo que va a realizar este metodo de prueba
    void seleccionarMedicoConEspecialidadEnFechaEscenario1() {
        //Establecer horario dentro del funcionamiento del horario
        var proximoLunes10H = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY)) //Fijamos el dia de la semana; with para un horario o dia especifico
                .atTime(10,0);

        //Realizamos la consulta nuevamente del paciente con el medico
        var medico = registrarMedico("Jose", "j@mail.com", "123456", Especialidad.CARDIOLOGIA);
        var paciente = registrarPaciente("antonio", "a@mail.com", "654321");
        registrarConsulta(medico, paciente, proximoLunes10H);

        //when | Realizamos la busqueda en la BD para realizar esa consulta
        var medicoLibre = medicoRepository.seleccionarMedicoConEspecialidadEnFecha(Especialidad.CARDIOLOGIA, proximoLunes10H);

        //then | Cuando Medico se encontraba en consulta con otro paciente  se retorna nulo
        assertThat(medicoLibre).isNull(); //Nos permite realizar comparaciones de True or False | medico libre sea nulo
    }

    @Test
    @DisplayName("deberia retornar un medico cuando realice la consulta en la base de datos  en ese horario")
    void seleccionarMedicoConEspecialidadEnFechaEscenario2() {

        //given | dado un conjunto de valores inciales
        var proximoLunes10H = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);

        var medico = registrarMedico("Jose", "j@mail.com", "123456", Especialidad.CARDIOLOGIA);

        //when | Realizamos una busqueda  en la BD
        var medicoLibre = medicoRepository.seleccionarMedicoConEspecialidadEnFecha(Especialidad.CARDIOLOGIA, proximoLunes10H);

        //then | Medico se encuentra disponible para esa especialidad y en ese horario, retornar un medico aleatorio
        assertThat(medicoLibre).isEqualTo(medico);
    }

        /*REGISTRAMOS EL MEDICO MEDIANTE LA CONSUTRUCCION  DE METODOS INTERNOS EN LA CLASE DE PEUBAS*/

    private void registrarConsulta(Medico medico, Paciente paciente, LocalDateTime fecha) {
        em.persist(new Consulta(null, medico, paciente, fecha, null));
    }

    private Medico registrarMedico(String nombre, String email, String documento, Especialidad especialidad) {
        var medico = new Medico(datosMedico(nombre, email, documento, especialidad));
        em.persist(medico);
        return medico;
    }

    private Paciente registrarPaciente(String nombre, String email, String documento) {
        var paciente = new Paciente(datosPaciente(nombre, email, documento));
        em.persist(paciente);
        return paciente;
    }

    private DatosRegistroMedico datosMedico(String nombre, String email, String documento, Especialidad especialidad) {
        return new DatosRegistroMedico(
                nombre,
                email,
                "61999999999",
                documento,
                especialidad,
                datosDireccion()
        );
    }

    private DatosRegistroPaciente datosPaciente(String nombre, String email, String documento) {
        return new DatosRegistroPaciente(
                nombre,
                email,
                "61999999999",
                documento,
                datosDireccion()
        );
    }

    private DatosDireccion datosDireccion() {
        return new DatosDireccion(
                " loca",
                "azul",
                "acapulpo",
                "321",
                "12"
        );
    }

}

/*TESTEO ESCENARIO 1 == Si nosotros vemos los detalles, vemos que él realizó el registro en la base de datos para el médico,
 él realizó un insert de médico, realizó un insert para los pacientes para la consulta y luego que él realizó, él realizó acá una
búsqueda. Entonces si nosotros vemos acá él intentó seleccionar un médico que se encontrase activo con esa especialidad
 y que no se encontrase con consulta y en esa fecha.Entonces seleccionó un médico aleatorio dentro de la especialidad y
 vio cómo nosotros lo habíamos registrado, un médico con este paciente nos va a retornar nulo. */

/*TESTEO ESCENARIO2 == En este caso El medico si se encuentra disponible: no se registrata ni paciente ni consulta, asi que
* al momento de realizar una consulta en la BD nosotros deberiamos encontrar que el medico que estamos buscando, el medico libre
*  sea igual al medico que estamos instanciando */

/*@DataJpaTest : Nos va a permitir usar unos metodos, modificaciones a lo que es la capa de persistencia
 * * NOTA: puede entrar error ya que el busca una base de datos EN MEMORIA y no existe, por que es JPA, estas
 * * BD podrian ser H2, abria que instalar la dependencia  y propperties configurar de nuevo los datos  para la
 * Para decirle que estmos usando una BD externa, ponemos la anotacion @AutoConfigureTestDatabase
 * AL USAR BD DE PRUEBA TENEMOS QUE SEPARAR LA BASE DE DATOS DE PRODUCCION DE LA BD PARA DEBUGS, YA QUE PODEMOS BORRAR O MODIFICAR LA BD
 * Para esto copiamos el application.propperties, y lo pegamos en resources como application-test.propperties
 * y poner en @ActiveProfile indicar el perfil que estamos utilizando, en este caso "test"; Para crear la BD automatica
 *  en el archivo propperties poner el siguiente comando : ?createDatabaseIfNotExist=true&serverTimezone=UTC */