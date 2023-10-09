package med.voll.api.controller;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
//AQUI VAMOS  A HACER EL CRUD


@RestController
@RequestMapping("/medicos")
@SecurityRequirement(name = "bearer-key")  //Reuquerimiento de Seg que va a solicitar el JWT
public class MedicoController {

    @Autowired // Spring busca la interfaz MedicoR. y lo inyecta aqui para no crearlo de nuevo | sería ''un cable automático'
    private MedicoRepository medicoRepository;

    //Con @Valid va a validar que todos los registros medicos sean correctos, de la Clase DatosRMedico
    @PostMapping
    @Transactional   //Se registra medico a travez de un DTO Record con los parametros que se capturan desde Json
    public ResponseEntity<DatosRespuestaMedico> registrarMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico,
                                                                UriComponentsBuilder uriComponentsBuilder) {
        //Aqui devolvemos el cuerpo del medico que hemos registrado en DTO
        Medico medico = medicoRepository.save(new Medico(datosRegistroMedico)); //se instancia para evitar conflictos con Spring
        DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(medico.getId(), medico.getNombre(), medico.getEmail(),
                medico.getTelefono(), medico.getEspecialidad().toString(),
                new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento()));
        //URL dinamica que vamos a devolver los datos del medico a crear / Browser->F12->Headers
        URI url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaMedico);

    }
    /*@RequestBody Sin esta anotación, Spring no leerá el cuerpo de la solicitud ni va mapear sus campos al DTO recibido
     como parámetro.*/


    @GetMapping //Paginado con datos limitados, no se exhiben los datos completos, por el Record se implementa esto
    public ResponseEntity<Page<DatosListadoMedico>> listadoMedicos(@PageableDefault(size = 2) Pageable paginacion) {
//        return medicoRepository.findAll(paginacion).map(DatosListadoMedico::new);
        return ResponseEntity.ok(medicoRepository.findByActivoTrue(paginacion).map(DatosListadoMedico::new)); //Muestra solo medicos activos
    }
    /*-findByActivoTrue: Spring JPA para crear dinamicamente las queries/MedicoRepository | Osea realiza queries personalizadas
    -PageableDefault : QueryParams en la barra http/ tamaño de lista con size etc spring lo hace automaticamente
    aqui por defecto nos va  a devolver en la query 2 medicos si no le agrega el usuario cuantos quiere*/

    @PutMapping
    @Transactional // JPA va  amapear que cuando termine  la transaccion de actualizacion se  va  a liberar y hara un commit en DB
    public ResponseEntity actualizarMedico(@RequestBody @Valid DatosActualizarMedico datosActualizarMedico) {
        Medico medico = medicoRepository.getReferenceById(datosActualizarMedico.id()); // JPA mapea medico de la BD
        medico.actualizarDatos(datosActualizarMedico); //transaccion
        //Por buena practica debemos retornar el elemento que acabamos de actualizar pero de DTO, no de la DB
        return ResponseEntity.ok(new DatosRespuestaMedico(medico.getId(), medico.getNombre(), medico.getEmail(),
                medico.getTelefono(), medico.getEspecialidad().toString(),
                new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento())));
    }

    /*@Transactional lo que hace es abrir una transaccion en la base de datos, JPA lo que  hace es mapear mi medico que estoy
     * trayendo de la base de datos, ya que esta en el contexto de JPA se actualizan los datos del medico, y asi nua vez que
     * el metodo termina la transaccion termina y hace un commit en la BD y son guardados , aquui no estamos usando un Repositorio
     * para hacer un update, si no estamos utilizando JPA */

    // DELETE LOGICO  : No se elimina de la base de datos pero queda desactivado
    @DeleteMapping("/{id}") // Crea la URL para eliminar atravez de esta PathVariable Dinamicamente
    @Transactional //Poner Transactional para que tenga efectos en la BD
    public ResponseEntity eliminarMedico(@PathVariable Long id) { //Spring mapea que el valor de la pathVariable se asignara al parametro
        Medico medico = medicoRepository.getReferenceById(id); //Le decimos a Spring  = agarra el Id que te hemos enviado
        medico.desactivarMedico();
        return ResponseEntity.noContent().build();
    }

    /*DELETE EN BASE DE DATOS: Si los borra de la DB
    * public void eliminarMedico(@PathVariable Long id){
    *   Medico medico = medicoRepository.getReferenceById(id);
    *   medicoRpository.delete(medico);
    * }*/

    //EndPoint para retornar los datos de un medico en especifico / Usado cuando registramos un medico y en el ResponseEntity  lo retorna con DTO
    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaMedico> retornaDatosMedico(@PathVariable Long id) {
        Medico medico = medicoRepository.getReferenceById(id);
        var datosMedico = new DatosRespuestaMedico(medico.getId(), medico.getNombre(), medico.getEmail(),
                medico.getTelefono(), medico.getEspecialidad().toString(),
                new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento()));
        return ResponseEntity.ok(datosMedico);
    }

}
/* ResponseEntity es una clase esencial en Spring Boot que te permite personalizar y controlar completamente las
respuestas HTTP que se envían desde tus controladores. Puedes establecer códigos de estado, encabezados y cuerpos
personalizados para adaptar las respuestas a tus necesidades específicas
ResponseEntity.ok(): Crea una respuesta con el código de estado HTTP 200 (OK).
ResponseEntity.status(HttpStatus.XXX): Crea una respuesta con un código de estado HTTP específico, como 404 para "Not Found".
ResponseEntity<T>(T body): Crea una respuesta con un cuerpo personalizado.
ResponseEntity<T>(T body, HttpHeaders headers): Crea una respuesta con un cuerpo y encabezados personalizados.*/

/*@PageableDefault se utiliza en métodos de controladores para personalizar los valores predeterminados de paginación
 cuando se utiliza un objeto Pageable. Esto es útil en aplicaciones web que requieren paginación de resultados.
 -@RequestBody se utiliza en métodos de controladores para indicar que un parámetro debe ser extraído del cuerpo
 de la solicitud HTTP entrante. Esto es comúnmente utilizado para recibir datos JSON o XML en una solicitud POST o PUT.
 -@Valid se utiliza en conjunción con @RequestBody para habilitar la validación de datos en la entrada. Cuando se
 coloca en un objeto, Spring Boot validará automáticamente los campos del objeto utilizando las anotaciones de
 validación de Bean Validation (como @NotNull, @Size, etc.)
 -@PathVariable se utiliza en métodos de controladores para vincular una parte de la URL como un parámetro de método.
 Esto permite que los valores se pasen en la URL y se utilicen en el controlador.
 -@Transactional se utiliza para indicar que un método debe ejecutarse en una transacción. Cuando se coloca en un
  método de servicio, permite que Spring administre automáticamente la transacción, lo que significa que el método se
   ejecutará en una transacción única..*/


