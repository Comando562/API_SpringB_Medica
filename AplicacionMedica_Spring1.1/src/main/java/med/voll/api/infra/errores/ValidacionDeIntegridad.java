package med.voll.api.infra.errores;

public class ValidacionDeIntegridad extends RuntimeException {
    public ValidacionDeIntegridad(String s) {
        super(s);
    }
}

/*la excepcion pudo haber sido Trowable; responde ante erroes y excepciones y RuntimeException; solo responde ante
* excepcion*/
