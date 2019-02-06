package interes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import model.Interes;

/**
 * InteresRepository
 */
@Repository
public interface InteresRepository extends JpaRepository<Interes, Integer> {

    
}