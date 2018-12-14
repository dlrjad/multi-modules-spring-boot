package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import exceptions.BancaNotFoundException;
import model.Banca;
import persistence.BancaRepository;

import java.util.List;
import java.util.Optional;

/**
 * BankService
 */
@Service
public class BankService {

    @Autowired
    BancaRepository repo;

    public BankService(BancaRepository bankRepository) {
        this.repo = bankRepository;
    }

    public List<Banca> getAllBanks() {
        return this.repo.findAll();
    }

    public Banca getAccountById(Integer id) {
        Optional<Banca> foundedResult = this.repo.findById(id);
        return foundedResult.isPresent() ? foundedResult.get() : null;
    }

    public Banca createAccount(Banca model) throws Exception {
        if (this.getAccountById(model.getId()) != null) {
            throw new Exception();
        }
        return this.repo.save(model);
    }

    public Banca updateAccount(Banca model) throws BancaNotFoundException {
        Banca updated = getAccountById(model.getId());
        if (updated == null) {
            throw new BancaNotFoundException();
        }
        updated.setClient(model.getClient());
        updated.setAmount(model.getAmount());
        updated.setType(model.getType());

        return this.repo.save(updated);
    }

    public String removeAccount(Integer id) throws BancaNotFoundException, Exception {
        if (this.getAccountById(id) == null) {
            throw new BancaNotFoundException();
        }
        String resu = null;
        try {
            this.repo.deleteById(id);
            resu = String.format("La cuenta con id %d ha sido eliminada satisfactoriamente", id);
        } catch (Exception e) {
            throw e;
        }
        return resu;
    }
}