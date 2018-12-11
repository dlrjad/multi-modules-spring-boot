package persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import model.Banca;

@Repository
public interface BancaRepository extends JpaRepository<Banca, Integer>{

}