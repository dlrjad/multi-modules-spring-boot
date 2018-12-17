package persistence;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

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

  @Override
  public void run(String... strings) {

    List<Interes> interes = new ArrayList<>();
    interes.add(new Interes(1,2.7f));
    interes.add(new Interes(2,2.1f));
    interes.add(new Interes(3,0.8f));
    interes.add(new Interes(4,1.5f));
    interes.forEach(e-> this.interesRepository.save(e));

    String[][] data = new String[][]{{"Santander","Corriente"},{"BBVA","Corriente"},{"La Caixa","Ahorro"},{"Caja 7","Ahorro"}};
    
    List<Banca> bancas = new ArrayList<>();
    for (int i = 0; i < data.length; i++) {
      bancas.add(new Banca(data[i][0], data[i][1], 1000D+(i*100), new ArrayList<Interes>(Arrays.asList(new Interes[]{interes.get(i)}))));
      
    }
    
    bancas.forEach(e -> {
      List<Interes> actualIntereses = e.getBanca_interes();
      actualIntereses.forEach(y -> y.setBanca(e));
      e.setBanca_interes(actualIntereses);
      this.bancaRepository.save(e);
    });
  }
}