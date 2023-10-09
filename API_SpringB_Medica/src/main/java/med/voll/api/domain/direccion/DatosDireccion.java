package med.voll.api.domain.direccion;

import jakarta.validation.constraints.NotBlank;
//DTO

public record DatosDireccion(
        @NotBlank //Validacion que no llege vacio el campo a la BD
        String calle,
        @NotBlank
        String distrito,
        @NotBlank
        String ciudad,
        @NotBlank
        String numero,
        @NotBlank
        String complemento) {
}

/*El record basicamente hace es crear una clase  ya que se eecuta el Ccodigo*/