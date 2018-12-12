package controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
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
import model.Banca;
import services.BankService;

@RestController
@RequestMapping(path = "/api")
public class BancaController {

  @Autowired
  private BankService service;

  @CrossOrigin(origins = "http://localhost:4200")
  @GetMapping(path = "/bancos")
  public List<Banca> getHotels() {
    return this.service.getAllBanks();
  }

  @CrossOrigin(origins = "http://localhost:4200")
  @GetMapping("/banco/{id}")
  public ResponseEntity<?> getUserById(@PathVariable Integer id, HttpServletResponse response) {
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

  @CrossOrigin(origins = "http://localhost:4200")
  @PostMapping("/banco")
  public ResponseEntity<?> createUser(@RequestBody Banca banca, HttpServletResponse response) {
    ResponseEntity<?> res = null;
    if (banca == null) {
      return new ResponseEntity<ErrorRest>(
          new ErrorRest("Formato de peticion incorrecto. Debe enviar los datos del banco a crear"),
          HttpStatus.BAD_REQUEST);
    }
    try {
      res = new ResponseEntity<Banca>(this.service.createAccount(banca),HttpStatus.OK);
    } catch (Exception e) {
      res = new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return res;
  }

  @CrossOrigin(origins = "http://localhost:4200")
  @PutMapping("/banco/{id}")
  public ResponseEntity<?> updateUser(@PathVariable Integer id, RequestEntity<Banca> reqBanca) {
    ResponseEntity<?> res = null;
    if (reqBanca.getBody() == null) {
      return new ResponseEntity<ErrorRest>(
          new ErrorRest("Formato de peticion incorrecto. Debe enviar los datos del banco a modificar"),
          HttpStatus.BAD_REQUEST);
    }
    try {
      res = new ResponseEntity<Banca>(this.service.updateAccount(reqBanca.getBody()), HttpStatus.OK);
    } catch (BancaNotFoundException e) {
      res = new ResponseEntity<ErrorRest>(new ErrorRest(), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      res = new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return res;
  }

  @CrossOrigin(origins = "http://localhost:4200")
  @DeleteMapping("/banco/{id}")
  public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
    ResponseEntity<?> res = null;
    try {
      res = new ResponseEntity<String>(this.service.removeAccount(id), HttpStatus.OK);
    } catch (BancaNotFoundException e) {
      res = new ResponseEntity<ErrorRest>(new ErrorRest(), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      res = new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return res;
  }

  @CrossOrigin(origins = "http://localhost:4200")
  @GetMapping("/bancos/{type}")
  public ResponseEntity<?> getType(@PathVariable String type, RequestEntity<Banca> reqBanca) {
    ResponseEntity<?> res = null;
    try {
      res = new ResponseEntity<List<Banca>>(this.service.getType(type), HttpStatus.OK);
    } catch (BancaNotFoundException e) {
      res = new ResponseEntity<ErrorRest>(new ErrorRest(), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      res = new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return res;
  }

}