package Excepciones;

/**
 *
 * @author Mateo
 */
public class IdEnUsoException extends RuntimeException{

    public IdEnUsoException() {
        super("La Id ya está en uso");
    }
    
}
