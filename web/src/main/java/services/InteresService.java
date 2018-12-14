package services;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import interes.InteresRepository;
import model.Interes;

@Service
public class InteresService {

    @Autowired
    InteresRepository repo;

    public List<Interes> getAllInteres() {
        return this.repo.findAll();
    }
}