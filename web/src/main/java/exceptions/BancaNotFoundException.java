package exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class BancaNotFoundException extends RuntimeException {
  private static final long serialVersionUID = -7295910574475009536L;

  public BancaNotFoundException() {
    super("Cuenta inexistente");
  }
  public BancaNotFoundException(Integer id) {
    super(String.format("No existe ningún usuario con el ID = %d", id));
  }

}
