package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import exceptions.BancaNotFoundException;
import exceptions.TransferException;
import exceptions.WithoutFoundsException;
import model.Banca;
import model.Transfer;
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

    public List<Banca> getType(String type) {
        return this.repo.findByType(type);
    }
    /**
     * Realiza una transferencia entre dos cuentas
     * @param fromAccount
     * @param toAccount
     * @param totalAmount
     */
    public void moneyTransfer(Transfer transferRequest) throws BancaNotFoundException,TransferException, Exception{
        Banca from = this.getAccountById(transferRequest.getFromAccount());
        Banca to = this.getAccountById(transferRequest.getToAccount());
        if(from == null || to == null){
            throw new BancaNotFoundException();
        }
        if(from.equals(to)){
            throw new TransferException("no puedes transferir dinero a tu propia cuenta");
        }
        if(transferRequest.getMoneyToTransfer() == 0D){
            throw new TransferException("la cantidad es inválida");
        }
        
        if(from.getType().equals("Ahorro") || to.getType().equals("Ahorro")){
            throw new TransferException("las cuentas de tipo ahorro no permiten esta operacion");
        }

        from.withdrawMoney(transferRequest.getMoneyToTransfer());
        to.depositMoney(transferRequest.getMoneyToTransfer());
        this.repo.save(from);
        this.repo.save(to);
    }


    /**
     * Operaciones de deposito y extraccion de efectivo para la cuenta especificada
     * @param accountId
     * @param canti
     * @return
     * @throws BancaNotFoundException
     * @throws WithoutFoundsException
     * @throws Exception
     */
    public Banca accountOps(Integer accountId, Double canti) throws BancaNotFoundException, WithoutFoundsException, Exception{
        Banca account = this.getAccountById(accountId);
        if(account == null){
            throw new BancaNotFoundException();
        }

        if(canti > 0){
            account.depositMoney(canti);
        }else{
            account.withdrawMoney(canti * -1D);
        }

        this.repo.save(account);
        return account;
    }
}