package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import exceptions.BancaNotFoundException;
import exceptions.ErrorRest;
import exceptions.WithoutFoundsException;
import model.Banca;
import model.Transfer;
import services.BankService;

/**
 * Clase controlador BancaController
 */
@RestController
@RequestMapping(path = "/api")
public class BancaController {

  @Autowired
  private BankService service;
  
  /**
   * Constructor del Controlador que tiene dependencia con el servicio bankService
   * @param bankService
   */
  public BancaController(BankService bankService) {
	  this.service = bankService;
  }

  /**
   * Método GET para obtener todas las cuentas bancarias
   * @return retorna todas las cuentas bancarias en caso de éxito
   */
  @CrossOrigin(origins = "http://localhost:4200")
  @GetMapping(path = "/bancos")
  public ResponseEntity<?> getBanks() {
    ResponseEntity<?> res = null;
    List<Banca> banks = this.service.getAllBanks();
    try {
      return new ResponseEntity<List<Banca>>(banks, HttpStatus.OK);
    }
    catch (Exception e) {
      res = new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return res;
  }

  /**
   * Método GET para obtener una cuenta bancaria filtrando por su ID (número de cuenta)
   * @param id
   * @return retorna una cuenta bancaria en caso de éxito
   */
  @CrossOrigin(origins = "http://localhost:4200")
  @GetMapping("/banco/{id}")
  public ResponseEntity<?> getBankById(@PathVariable Integer id) {
    ResponseEntity<?> res = null;
    try {
      Banca founded = this.service.getAccountById(id);
      if (founded != null) {
        res = new ResponseEntity<Banca>(this.service.getAccountById(id), HttpStatus.OK);
      } else {
        res = new ResponseEntity<ErrorRest>(new ErrorRest(), HttpStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      res = new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return res;
  }

  /**
   * Método POST para crear una cuenta bancaria
   * @param banca
   * @return retorna una cuenta bancaria creado en caso de éxito
   */
  @CrossOrigin(origins = "http://localhost:4200")
  @PostMapping("/banco")
  public ResponseEntity<?> createBank(@RequestBody Banca banca) {
    ResponseEntity<?> res = null;
    if (banca == null) {
      return new ResponseEntity<ErrorRest>(
          new ErrorRest("Formato de peticion incorrecto. Debe enviar los datos del banco a crear"),
          HttpStatus.BAD_REQUEST);
    }
    try {
      res = new ResponseEntity<Banca>(this.service.createAccount(banca), HttpStatus.OK);
    } catch (Exception e) {
      res = new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return res;
  }

  /**
   * Método PUT para actualizar una cuenta bancaria filtrando por su ID (número de cuenta)
   * @param id
   * @param reqBanca
   * @return retorna una cuenta bancaria actualizado en caso de éxito
   */
  @CrossOrigin(origins = "http://localhost:4200")
  @PutMapping("/banco/{id}")
  public ResponseEntity<?> updateBank(@PathVariable Integer id, @RequestBody Banca reqBanca) {
    ResponseEntity<?> res = null;
    if (reqBanca == null) {
      return new ResponseEntity<ErrorRest>(
          new ErrorRest("Formato de peticion incorrecto. Debe enviar los datos del banco a modificar"),
          HttpStatus.BAD_REQUEST);
    }
    try {
      res = new ResponseEntity<Banca>(this.service.updateAccount(reqBanca), HttpStatus.OK);
    } catch (BancaNotFoundException e) {
      res = new ResponseEntity<ErrorRest>(new ErrorRest(e.getMessage()), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      res = new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return res;
  }

  /**
   * Método DELETE que eliminar una cuenta bancaria filtrando por su ID (número de cuenta)
   * @param id
   * @return retorna un mensaje indicando cuenta bancaria eliminada en caso de éxito
   */
  @CrossOrigin(origins = "http://localhost:4200")
  @DeleteMapping("/banco/{id}")
  public ResponseEntity<?> deleteBank(@PathVariable Integer id) {
    ResponseEntity<?> res = null;
    try {
    	if(id == null) {
    		throw new RuntimeException("No se ha especificado un identificador de cuenta");
    	}
      res = new ResponseEntity<String>(this.service.removeAccount(id), HttpStatus.OK);
    } catch (BancaNotFoundException e) {
      res = new ResponseEntity<ErrorRest>(new ErrorRest(e.getMessage()), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      res = new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return res;
  }

  /**
   * Método GET para obtener una lista de cuentas bancarias filtrando por un tipo de cuenta (ahorro/corriente)
   * @param type
   * @return retorna una lista de cuentas bancarias en caso de éxito
   */
  @CrossOrigin(origins = "http://localhost:4200")
  @GetMapping("/bancos/{type}")
  public ResponseEntity<?> getType(@PathVariable String type) {
    ResponseEntity<?> res = null;
    try {
      res = new ResponseEntity<List<Banca>>(this.service.getType(type), HttpStatus.OK);
    } catch (BancaNotFoundException e) {
      res = new ResponseEntity<ErrorRest>(new ErrorRest(), HttpStatus.NOT_FOUND);
    } catch (RuntimeException e) {
    	res = new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      res = new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return res;
  }

  /**
   * Método PUT para actualizar cuenta bancaria al realizar una transferencia
   * @param transferRequest
   * @return retorna un mensaje indicando que la transferencia se ha realizado en caso de éxito
   */
  @CrossOrigin(origins = "http://localhost:4200")
  @PutMapping("/banco/transfers")
  public ResponseEntity<?> transfer(@RequestBody Transfer transferRequest) {
    ResponseEntity<?> res = null;
    try {
      this.service.moneyTransfer(transferRequest);
      res = new ResponseEntity<String>("Transferencia realizada con exito", HttpStatus.OK);
    } catch (BancaNotFoundException e) {
      res = new ResponseEntity<ErrorRest>(new ErrorRest(e.getMessage()), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      res = new ResponseEntity<ErrorRest>(new ErrorRest(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return res;
  }

  /**
   * Método PUT para actualizar una cuenta bancaria al realizar una operación (deposito/extracción)
   * @param account
   * @param canti
   * @return retorna una cuenta bancaria actualizada en caso de éxito
   */
  @CrossOrigin(origins = "http://localhost:4200")
  @PutMapping("/banco/{account}/ops/{canti}")
  public ResponseEntity<?> ops(@PathVariable Integer account, @PathVariable Double canti) {
    ResponseEntity<?> res = null;
    try {
      res = new ResponseEntity<Banca>(this.service.accountOps(account, canti), HttpStatus.OK);
    } catch (WithoutFoundsException e) {
      res = new ResponseEntity<ErrorRest>(new ErrorRest(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    } catch (BancaNotFoundException e) {
      res = new ResponseEntity<ErrorRest>(new ErrorRest(e.getMessage()), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      res = new ResponseEntity<ErrorRest>(new ErrorRest(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return res;
  }

}