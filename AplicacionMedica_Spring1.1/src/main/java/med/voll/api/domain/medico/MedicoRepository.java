package med.voll.api.domain.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
//UTILIZABLE EN AGENDA CONSULTA SERVICE
@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Page<Medico> findByActivoTrue(Pageable paginacion);

    //Consultas para la BD
    /*Validamos: que
     * -el id de ese medico :sea activo el medico
     * -que la especialidad sea igual a la especialidad que estamos enviando del parametro
     * -limita  aun solo medico
     * - Y el id que estamos seleccionando  sea disponible, los que tienen fecha asignada no cumpliran con esta condicion
     * retornanos una subquerie (una tabla la cual vamos a seleccionar el id que no se encuentren con fechas asignadas) */
    @Query("""  
            select m from Medico m
            where m.activo= 1 
            and
            m.especialidad=:especialidad 
            and
            m.id not in(  
                select c.medico.id from Consulta c
                where
                c.fecha=:fecha
            )
            order by rand()
            limit 1
            """)

    Medico seleccionarMedicoConEspecialidadEnFecha(Especialidad especialidad, LocalDateTime fecha);


    @Query("""
            select m.activo 
            from Medico m
            where m.id=:idMedico
            """)
    Boolean findActivoById(Long idMedico);
}
/*JpaRepository<Medico, Long> El priimer parametro es el tipo de objeto que yo voy aguardar aqui osea el tipo de entidad
* con el que yo voy a trabajar en este repositorio, segundo necesito el tipo de objeto del id osea Long
* ESTA INTERFAZ YA NOS BRINDA METODOS QUE VAN A SER UTILES SIN NECESIDAD DE ESCRIBIRLOS*/
/*Un repositorio se encuentra entre las reglas de negocio y la capa de persistencia:

Proporciona una interfaz para las reglas comerciales donde se accede a los objetos como una colección;
Utiliza la capa de persistencia para escribir y recuperar datos necesarios para persistir y recuperar objetos de negocio.
Por lo tanto, incluso es posible utilizar uno o más DAOs en un repositorio.*/