package exceptions;

/**
 * TransferException
 */
public class TransferException extends Exception{

    private static final long serialVersionUID = 6533019801005442927L;

    public TransferException() {
        super("Hubo un error en la transferencia");
    }

    public TransferException(String message){
        super(String.format("La transferencia no pudo completarse porque %s",message));
    }
    
}