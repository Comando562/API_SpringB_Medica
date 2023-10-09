package med.voll.api.controller;

import med.voll.api.domain.consulta.AgendaDeConsultaService;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.consulta.DatosDetalleConsulta;
import med.voll.api.domain.medico.Especialidad;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc //Componentes para realiza una simulacion para el controlador
@AutoConfigureJsonTesters //Configuraciones relacionadas al JacksonTester
@ActiveProfiles("test")
@SuppressWarnings("all")
class ConsultaControllerTest {

    @Autowired
    private MockMvc mvc; //utilizaremos esta anotacion para simular una peticion

    @Autowired
    private JacksonTester<DatosAgendarConsulta> agendarConsultaJacksonTester; //Toma un objeto java y lo convierte a JSON

    @Autowired
    private JacksonTester<DatosDetalleConsulta> detalleConsultaJacksonTester; //

    @MockBean //Indicamos que Vamos a simular esta clase de servicio
    private AgendaDeConsultaService agendaDeConsultaService;


    @Test
    @DisplayName("deberia retornar estado http 400 cuando los datos ingresados sean invalidos")
    @WithMockUser //Simula un usuario autenticado
    void agendarEscenario1() throws Exception {
        //given //when  | Simulando un POST
        var response = mvc.perform(post("/consultas")).andReturn().getResponse();

        //then | Verificams que el estado que estamos recibiendo de la peticion sea 400
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    //Cuando enviamos un JSON y retornar un Json a la aplicacion que esta haciendo la peticion
    @Test
    @DisplayName("deberia retornar estado http 200 cuando los datos ingresados son validos")
    @WithMockUser
    void agendarEscenario2() throws Exception {
        //given
        var fecha = LocalDateTime.now().plusHours(1); //Indicando que va a ser una hora despues del momento actual
        var especialidad = Especialidad.CARDIOLOGIA;
        var datos = new DatosDetalleConsulta(null,2l,5l,fecha);

        // when  | Cuando se intente agendar cualquier JSON  retornar este parametro

        when(agendaDeConsultaService.agendar(any())).thenReturn(datos); //Cuando sea cualquier peticion, retornar datos consulta

        var response = mvc.perform(post("/consultas")
                .contentType(MediaType.APPLICATION_JSON) //espeficicamos el tipo del contenido
                .content(agendarConsultaJacksonTester.write(new DatosAgendarConsulta(2l,5l,fecha, especialidad)).getJson())
        ).andReturn().getResponse(); // objeto Java -> objeto JSON

        //then  | Validar que la respuesta sea igual al estado 200
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var jsonEsperado = detalleConsultaJacksonTester.write(datos).getJson(); //Verificamos el cuerpo del JSON que estamos retornando

        //Indicamos el contenido de la respuesta sea igual al JSON esperado
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);

    }
}

/*@SprinbBootTest; nos permite trabajar con  todos los componentes dentero del contexto de spring.
* Despues verificar los diferentes estads que nosotro podemos retornar cuando realizamos una peticion
* -400; valores invalidos, -404; usuario no ha sido encontrado, 403; autorizacion no ha sido pasada, no pasamos el JWT
* -200; se ha realizado todo exitosamente */

/*Entonces HAY DOS ESTRATEGIAS para nosotros realizar las pruebas para este controlador, una donde nosotros inyectamos
un servidor y realizamos una requisición de verdad dentro de la aplicación, para eso tendríamos que utilizar la anotación
 @RestAndPlay donde vamos a tener un servidor creando una petición en nuestra aplicación.
El controlador va a recibir esa petición, va a realizar la búsqueda en el repositorio, va a realizar las
validaciones con el componente de service y luego va a retornar el estado en caso de que sea encontrada o en caso de
 que los datos sean inválidos. Esa es una primera estrategia.
 --La segunda estrategia es donde nosotros vamos a simular que se realizó la petición y nos vamos a enfocar únicamente
 en este componente, ignorando el resto de los componentes.Entonces nosotros vamos a simular esa petición, no vamos a crear
 un servidor sino que es vamos a simularlo y vamos a ignorar el restante de los componentes que serían los repositorios,
  los servicios o cualquier otro componente,y vamos a ver el Estado que retorna cuando realizamos la petición con esos datos.
  EN ESTE CASO USAREMOS LA SEGUNDA ESTRATEGA; SIMULANDO*/

/*La anotación @WithMockUser en Spring Boot se utiliza para simular un usuario autenticado durante las pruebas unitarias
 y de integración de una aplicación. Esta anotación es especialmente útil cuando se están probando controladores o servicios
 que requieren que el usuario esté autenticado para acceder a ciertos recursos o realizar ciertas acciones.
Cuando se utiliza @WithMockUser, se crea un usuario ficticio con roles y credenciales simuladas para simular la autenticación.
 Esto permite que las pruebas se ejecuten como si un usuario autenticado estuviera interactuando con la aplicación, lo que
  facilita la comprobación del comportamiento de seguridad y acceso de la aplicación en diferentes escenarios.
En este ejemplo, @WithMockUser se utiliza para simular usuarios autenticados con diferentes roles (USER y ADMIN) y se
realizan pruebas en los controladores correspondientes. Esto permite probar cómo responde la aplicación a diferentes roles de
 usuario sin necesidad de autenticar usuarios reales durante las pruebas.
En resumen, @WithMockUser es una anotación útil para probar la seguridad y el acceso a recursos en aplicaciones Spring
Boot sin necesidad de autenticar usuarios reales en las pruebas unitarias e de integración.
*/