package med.voll.api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration //Le decimos a spring esta es una clase de configuracion
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf().disable().sessionManagement() //desabilitamos la proteccion que viene por defecto y el login por defecto
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Le indicamos a Spring el tipo de sesion
                .and().authorizeRequests()
                .requestMatchers(HttpMethod.POST, "/login").permitAll()//Cada reques que haga match con Post y va para login permitir todo
                .requestMatchers("/swagger-ui.html", "/v3/api-docs/**","/swagger-ui/**").permitAll() //Permitimos que podamos acceder a estas extenciones y derivadas de swagger ui
                .anyRequest()
                .authenticated() //Todos los request son autenticados
                .and()//Indicamos que nuestro filtro se ejecute antes que el propio de Spring
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)//Valida que el usuario existe y esta autenticado
                .build();
    }
    /*Para que solo el admin pueda eliminar pacientes y medicos:
    *@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/medicos").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/pacientes").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    * otro caso:
    * @Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http.csrf().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and().authorizeRequests()
        .antMatchers(HttpMethod.POST, "/login").permitAll()
        .antMatchers(HttpMethod.DELETE, "/medicos").hasRole("ADMIN")
        .antMatchers(HttpMethod.DELETE, "/pacientes").hasRole("ADMIN")
        .anyRequest().authenticated()
        .and().addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
}
    * */

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

/*@Configuration:

@Configuration es una anotación de Spring que se utiliza para marcar una clase como una fuente de definiciones de
bean para el contenedor de Spring.
En el contexto de Spring Security, se utiliza para crear una configuración de seguridad personalizada.
Esto significa que puedes definir tu propia configuración de seguridad en una clase marcada con @Configuration en
lugar de depender de la configuración predeterminada.
Dentro de una clase anotada con @Configuration, puedes declarar métodos para configurar aspectos específicos de la
 seguridad, como la autenticación y la autorización.
@EnableWebSecurity:

@EnableWebSecurity es una anotación específica de Spring Security que se utiliza para habilitar la configuración
de seguridad web en una aplicación Spring Boot.
Al agregar @EnableWebSecurity a tu clase de configuración, estás indicando a Spring que esta clase contendrá
configuraciones relacionadas con la seguridad web.
Esta anotación suele ser la primera anotación que se agrega en la clase de configuración de seguridad.
@Bean:

@Bean es una anotación de Spring que se utiliza para definir un método de fábrica de beans en una clase de
configuración. Este método devuelve un objeto que Spring administra como un bean en el contexto de la aplicación.
En el contexto de Spring Security, puedes usar @Bean para definir instancias de clases relacionadas con la seguridad,
como UserDetailsService, PasswordEncoder, filtros personalizados, entre otros.
Los beans definidos con @Bean se pueden inyectar en otros componentes de Spring, como controladores o clases de
configuración de seguridad.
El método securityFilterChain debería haberse anotado con @Bean.
Sin esta anotación de método, el objeto SecurityFilterChain no estará expuesto como un bean para Spring.
En resumen, estas tres anotaciones se combinan comúnmente para personalizar la configuración de seguridad en una
 aplicación Spring Boot. @Configuration marca una clase como una fuente de configuración de seguridad, @EnableWebSecurity
  habilita la configuración de seguridad web, y @Bean se utiliza para definir instancias de componentes relacionados con
  la seguridad que se utilizan en la configuración. Juntos, permiten la personalización completa de la seguridad en una
  aplicación web basada en Spring Security.*/