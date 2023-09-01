package Excepciones;

/**
 *
 * @author Mateo
 */
public class CamposVaciosException extends RuntimeException{

    public CamposVaciosException() {
        super("Ingrese todos los campos");
    }
    
}
