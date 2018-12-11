package persistence;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import model.Banca;

@Component
public class dbBanca implements CommandLineRunner {

	@Autowired
	private BancaRepository bancaRepository;
  
  
  
  /*public dbBanca(BancaRepository bancaRepository) {
    this.bancaRepository = bancaRepository;
  }*/

  @Override
  public void run(String... strings) {
    Banca santander = new Banca(777777, "Santander", "Corriente", 1200);
    Banca santander2 = new Banca(777778, "Caixa", "Corriente", 1300);
    Banca santander3 = new Banca(777779, "BBVA", "Ahorro", 1400);

    List<Banca> bancas = new ArrayList<>();
    bancas.add(santander);
    bancas.add(santander2);
    bancas.add(santander3);
    
    bancas.forEach(e -> this.bancaRepository.save(e));

  }

}