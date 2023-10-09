package med.voll.api.domain.medico;
//DTO : Son objetos para transportar datos
/*Crea una clase a le ejecutar el programa pero estos datos en este record vamos a utilizarlos para mostrar los datos
que queremos que se muestren*/

//Aqui vamos  a solicitar estos datos por parte del JSON
public record DatosListadoMedico(Long id, String nombre, String especialidad, String documento, String email) {

    public DatosListadoMedico(Medico medico) { //Constructor recibe los datos del medico como entidad, los mapea como STRING
        this(medico.getId(), medico.getNombre(), medico.getEspecialidad().toString(), medico.getDocumento(), medico.getEmail());
    }
}


