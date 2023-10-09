package med.voll.api.infra.springdoc;



import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfiguration {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                .info(new Info()
                        .title("API Voll.med")
                        .description("API Rest de la aplicación Voll.med, que contiene las funcionalidades de CRUD de médicos y pacientes, así como programación y cancelación de consultas.")
                        .contact(new Contact()
                                .name("Equipo Backend")
                                .email("backend@voll.med"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://voll.med/api/licencia")));
    }

    @Bean
    public void message() {
        System.out.println("bearer is working");
    }
}

/*EXPLICACION: nosotros nos vamos a encontrar que para agregar una autorización en el encabezado de nuestras peticiones, tenemos
que agregar un método del tipo vean, colocar la anotación bean para ejecutar ese método de forma automática dentro del
 contexto de Spring framework.
 Así como colocar la anotación de requerimientos de seguridad dentro de todos los controladores que vayan a tener
 acceso a ese token. Entonces, lo primero sería colocar, copiar ese método. Vamos a ir de vuelta a nuestra API y en la
  parte de infraestructura nosotros vamos a agregar un nuevo paquete que va a ser todo lo relacionado a la documentación.
Y esta clase, al importar el bean nos va a retornar un elemento del tipo OpenAI, con un componente que va a recibir una
 llave de identificación llamado bearer-key, que es el que vamos a pasar a cada uno de nuestros controladores indicándole
  cuál va a ser el esquema de seguridad. El esquema de seguridad es del tipo bearer de Json Web Token
  Ahora en la documentación de Springdoc nos indica que otro de los elementos que tenemos que hacer es agregarle la
   anotación requerimientos de seguridad en cada uno de los controladores que van a solicitar ese token.AuthenticationController
   nosotros no lo necesitamos colocar, ya que nosotros le damos acceso a la autenticación a través de las configuraciones
   de seguridad. Entonces los controladores que necesitan acceso a ese token son las consultas, el controlador de médicos
   y el controlador de pacientes.a
    PARA QUE TENGA UN CAMPO  DE AUTHORIZACION EN LA PATH : /swagger*/