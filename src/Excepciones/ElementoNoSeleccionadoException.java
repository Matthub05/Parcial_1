package Excepciones;

/**
 *
 * @author Mateo
 */
public class ElementoNoSeleccionadoException extends RuntimeException{

    public ElementoNoSeleccionadoException() {
        super("Ingrese un id válido");
    }
    
}
