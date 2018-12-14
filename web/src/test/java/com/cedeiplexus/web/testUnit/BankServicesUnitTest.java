package com.cedeiplexus.web.testUnit;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import exceptions.BancaNotFoundException;
import persistence.BancaRepository;
import services.BankService;

import model.Banca;


public class BankServicesUnitTest {

  private BancaRepository bankRepository;
  private BankService  bankServices;
  
  private List<Banca> banks = new ArrayList<>();

  @Before
  public void setUp() {
	banks.add( new Banca(1, "Santander", "Corriente", 9200.5));
    bankRepository = Mockito.mock(BancaRepository.class);
    when(bankRepository.findAll()).thenReturn(banks);
    when(bankRepository.findById(1)).thenReturn(Optional.of(this.banks.get(0)));
    bankServices = new BankService(bankRepository);
  }

  @Test
  public void itShoulGetAllBanks() {
    List<Banca> banks = bankServices.getAllBanks();
    Assert.assertEquals(1, banks.size());
  }

  @Test
  public void itShoulGetBank() {
    Integer id = 1;
    Banca bank = bankServices.getAccountById(id);
    Assert.assertEquals(this.banks.get(0), bank);
  }

  @Test
  public void itNotShoulGetBank() {
    Integer id = 9999;
    Banca bank = bankServices.getAccountById(id);
    Assert.assertNull(bank);
  }

  @Test
  public void itShoulCreateBank() throws Exception {
    Integer id = 9998;
    Banca newBank = new Banca(id, "Santander", "Corriente", 1200.5);
    when(this.bankRepository.save(newBank)).thenReturn(newBank);
    Banca bank = bankServices.createAccount(newBank);
    Assert.assertEquals(newBank, bank);
  }

  @Test
  public void itNotShoulCreateBank() throws Exception {
    Integer id = 1;
    Banca newBank = new Banca(id, "Santander", "Corriente", 1200.5);
    try {
      bankServices.createAccount(newBank);
      fail("Fail test");
    } catch(Exception e) {
    	Assert.assertEquals(Exception.class, e.getClass());
    } 
  }

  @Test
  public void itShoulUpdateBank() throws Exception {
    Banca updatedBank = this.banks.get(0);
    updatedBank.setAmount(1200.5);
    when(this.bankRepository.save(updatedBank)).thenReturn(updatedBank);
    Banca bank = bankServices.updateAccount(updatedBank);
    Assert.assertEquals(updatedBank, bank);
  }

  @Test
  public void itNotShoulUpdateBank() throws Exception {
    Integer id = 9999;
    Banca updatedBank = new Banca(id, "Santander", "Corriente", 1200.5);
    try {
      bankServices.updateAccount(updatedBank);
      fail("Fail test");
    } catch(Exception e) {
      Assert.assertEquals(BancaNotFoundException.class, e.getClass());
    }
    
  }

  @Test
  public void itShoulDeleteBank() throws Exception {
    Integer id = 1;
    String message = bankServices.removeAccount(id);
    Assert.assertEquals(String.format("La cuenta con id %d ha sido eliminada satisfactoriamente", id), message);
  }

  @Test
  public void itNotShoulDeleteBank() throws Exception {
    Integer id = 9999;
    try {
      bankServices.removeAccount(id);
      fail("Fail test");
    }catch(Exception e) {
      Assert.assertEquals(BancaNotFoundException.class, e.getClass());
    }
  }

}