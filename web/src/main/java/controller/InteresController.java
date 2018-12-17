package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import model.Interes;
import services.InteresService;

@RestController
@RequestMapping(path = "/api")
public class InteresController {

  @Autowired
  private InteresService service;

  @CrossOrigin(origins = "http://localhost:4200")
  @GetMapping(path = "/interes")
  public List<Interes> getInteres() {
    return this.service.getAllInteres();
  }
}