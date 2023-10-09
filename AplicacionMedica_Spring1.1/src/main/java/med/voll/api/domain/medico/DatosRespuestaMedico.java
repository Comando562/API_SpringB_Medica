package med.voll.api.domain.medico;

import med.voll.api.domain.direccion.DatosDireccion;
//DTO : Son objetos para transportar datos
//No debemos retornar el objeto de la BD si no con un DTO  solo lo necesario
public record DatosRespuestaMedico(Long id, String nombre, String email, String telefono, String documento,
                                   DatosDireccion direccion) {
}
