package exceptions;

/**
 * WithoutFoundException
 */
public class WithoutFoundsException extends Exception {

    private static final long serialVersionUID = 1L;

    public WithoutFoundsException() {
        super("sin fondos para la operacion");
    }
    
}