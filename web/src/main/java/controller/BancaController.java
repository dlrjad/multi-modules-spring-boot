package controller;

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
import persistence.BancaRepository;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping(path="/api")
public class BancaController{
  private BancaRepository bancaRepository;

  public BancaController(BancaRepository bancaRepository) {
    this.bancaRepository = bancaRepository;
  }


  @CrossOrigin(origins = "http://localhost:4200")
  @GetMapping(path="/bancos")
  public List<Banca> getHotels() {
    List<Banca> bancas = this.bancaRepository.findAll();
    return bancas;
  }
  
  @CrossOrigin(origins = "http://localhost:4200")
  @GetMapping("/banco/{id}")
  public ResponseEntity<?> getUserById(@PathVariable Integer id, HttpServletResponse response) {
    try {
      Banca result = bancaRepository.findById(id).get();
      return new ResponseEntity<Banca>(result, HttpStatus.OK);
    } catch(Exception e) {
      if(e.getMessage().equals("No value present")) {
        throw new BancaNotFoundException(id);
      } else {
        return new ResponseEntity<ErrorRest>(new ErrorRest(e.getMessage()), HttpStatus.BAD_REQUEST);
      }
    }
  }
  
  @CrossOrigin(origins = "http://localhost:4200")
  @PostMapping("/banco")
  public ResponseEntity<?> createUser(@RequestBody Banca banca, HttpServletResponse response) {
    if (banca.equals(null)) {
      return new ResponseEntity<ErrorRest>(new ErrorRest("Formato de peticion incorrecto. Debe enviar los datos del banco a crear"), HttpStatus.BAD_REQUEST);
    }
    try {
        Banca newBanca = new Banca(
          banca.getClient(),
          banca.getType(),
          banca.getAmount()
        );
        return new ResponseEntity<Banca>(bancaRepository.save(newBanca), HttpStatus.OK);

    } catch(Exception e) {
      return new ResponseEntity<ErrorRest>(new ErrorRest(e.getMessage()), HttpStatus.METHOD_NOT_ALLOWED);
    }
  }
  
  @CrossOrigin(origins = "http://localhost:4200")
  @PutMapping("/banco/{id}")
  public ResponseEntity<?> updateUser(@PathVariable Integer id, RequestEntity<Banca> reqBanca) {
    if (reqBanca.getBody().equals(null)) {
      return new ResponseEntity<ErrorRest>(new ErrorRest("Formato de peticion incorrecto. Debe enviar los datos del banco a modificar"), HttpStatus.METHOD_NOT_ALLOWED);
    } 
    try {
      Optional<Banca> aux = bancaRepository.findById(id);
      if (aux.isPresent()) {
        Banca bancaUpdate = aux.get();
        bancaUpdate.setClient(reqBanca.getBody().getClient());
        bancaUpdate.setType(reqBanca.getBody().getType());
        bancaUpdate.setAmount(reqBanca.getBody().getAmount());
        return new ResponseEntity<Banca>(bancaRepository.save(bancaUpdate), HttpStatus.OK);
      } else {
        throw new BancaNotFoundException(id);
      }
    } catch(Exception e) {
      if(e.getMessage().equals("No value present")) {
        throw new BancaNotFoundException(id);
      }
      return new ResponseEntity<ErrorRest>(new ErrorRest(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }
  
  @CrossOrigin(origins = "http://localhost:4200")
  @DeleteMapping("/banco/{id}")
  public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
    try {
      Banca bancaDelete = bancaRepository.findById(id).get();
      bancaRepository.delete(bancaDelete);
      return new ResponseEntity<Banca>(bancaDelete, HttpStatus.OK);
    } catch(Exception e) {
      if(e.getMessage().equals("No value present")) {
        throw new BancaNotFoundException(id);
      } 
      return new ResponseEntity<ErrorRest>(new ErrorRest(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }

}