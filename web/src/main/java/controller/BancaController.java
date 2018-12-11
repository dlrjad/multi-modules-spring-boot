package controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import exceptions.BancaNotFoundException;
import exceptions.ErrorRest;
import model.Banca;
import persistence.BancaRepository;

import java.util.List;

import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping(path="/api")
public class BancaController{
  private BancaRepository bancaRepository;

  public BancaController(BancaRepository bancaRepository) {
    this.bancaRepository = bancaRepository;
  }


  @GetMapping(path="/bancos")
  public List<Banca> getHotels() {
    List<Banca> bancas = this.bancaRepository.findAll();
      return bancas;
  }
  
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

}