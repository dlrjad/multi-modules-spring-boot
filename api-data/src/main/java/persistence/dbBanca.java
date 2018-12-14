package persistence;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import interes.InteresRepository;
import model.Banca;
import model.Interes;

@Component
public class dbBanca implements CommandLineRunner {

	@Autowired
	private BancaRepository bancaRepository;
  
    @Autowired
    private InteresRepository interesRepository;
  
  
  /*public dbBanca(BancaRepository bancaRepository) {
    this.bancaRepository = bancaRepository;
  }*/

  @Override
  public void run(String... strings) {
    // Banca santander = new Banca(777777, "Santander", "Corriente", 1200);
    //santander.setBanca_interes(banca_interes);
    // Banca santander2 = new Banca(777778, "Caixa", "Corriente", 1300);
    // Banca santander3 = new Banca(777779, "BBVA", "Ahorro", 1400);

    Interes santa = new Interes(2,2.1f);
    Interes santa1 = new Interes(1,2.7f);

    //  santander.setInteres(santa);
    // santander2.setInteres(santa1);

      Banca santander = new Banca(777777, "Santander", "Corriente", 1200, santa);
    // //santander.setBanca_interes(banca_interes);
      Banca santander2 = new Banca(777778, "Caixa", "Corriente", 1300, santa1);
      Banca santander3 = new Banca(777779, "BBVA", "Ahorro", 1400, santa);
      Banca santander4 = new Banca(777780, "Caja Madrid", "Ahorro", 1500, santa1);
      Banca santander5 = new Banca(777781, "Caja Rural", "Corriente", 2200, santa);

      

    List<Banca> bancas = new ArrayList<>();
    bancas.add(santander);
    bancas.add(santander2);
    bancas.add(santander3);
    bancas.add(santander4);
    bancas.add(santander5);

    List<Interes> interes = new ArrayList<>();
    interes.add(santa);
    interes.add(santa1);
    
    interes.forEach(e-> this.interesRepository.save(e));
    bancas.forEach(e -> this.bancaRepository.save(e));
  }

}