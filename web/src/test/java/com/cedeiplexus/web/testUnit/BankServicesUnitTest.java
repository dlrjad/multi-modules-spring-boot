package com.cedeiplexus.web.testUnit;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import exceptions.BancaNotFoundException;
import exceptions.TransferException;
import exceptions.WithoutFoundsException;
import interes.InteresRepository;
import model.Banca;
import model.Interes;
import model.Transfer;
import persistence.BancaRepository;
import services.BankService;
import services.InteresService;

public class BankServicesUnitTest {

  private BancaRepository bankRepository;
  private InteresRepository interesRepository;
  private BankService bankServices;
  private InteresService interesService;

  private List<Banca> banks = new ArrayList<>();
  private List<Interes> intereses = new ArrayList<>();

  @Before
  public void setUp() {
    this.setUpBanks();
    when(bankRepository.findAll()).thenReturn(banks);
    when(bankRepository.findById(1)).thenReturn(Optional.of(this.banks.get(0)));
    when(bankRepository.findById(2)).thenReturn(Optional.of(this.banks.get(1)));
    when(bankRepository.findById(3)).thenReturn(Optional.of(this.banks.get(2)));

    this.setUpInteres();
    when(this.interesRepository.findAll()).thenReturn(this.intereses);
  }
  
  private void setUpInteres(){
    this.intereses.add(new Interes(1,2.3F));
    this.intereses.add(new Interes(2,1.5F));
    this.intereses.add(new Interes(3,0.8F));

    this.interesRepository = Mockito.mock(InteresRepository.class);
    this.interesService = new InteresService(this.interesRepository);
  }
  
  private void setUpBanks(){
    banks.add(new Banca(1, "Santander", "Corriente", 9200.5D,new ArrayList<Interes>(Arrays.asList(new Interes[]{new Interes(1,2.5F)}))));
    banks.add(new Banca(2, "BBVA", "Corriente", 1000D,new ArrayList<Interes>(Arrays.asList(new Interes[]{new Interes(1,2.5F)}))));
    banks.add(new Banca(3, "La Caixa", "Ahorro", 1200D,new ArrayList<Interes>(Arrays.asList(new Interes[]{new Interes(1,2.5F)}))));
    
    this.bankRepository = Mockito.mock(BancaRepository.class);
    this.bankServices = new BankService(bankRepository);
  }

  @Test
  public void itShoulGetAllBanks() {
    List<Banca> banks = bankServices.getAllBanks();
    if (banks.size() < 1) {
      fail("There are accounts");
    }
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
    Banca newBank = new Banca(id, "Santander", "Corriente", 1200.5D,new ArrayList<Interes>(Arrays.asList(new Interes[]{new Interes(1,2.5F)})));
    when(this.bankRepository.save(newBank)).thenReturn(newBank);
    Banca bank = bankServices.createAccount(newBank);
    Assert.assertEquals(newBank, bank);
  }

  @Test
  public void itNotShoulCreateBank() throws Exception {
    Integer id = 1;
    Banca newBank = new Banca(id, "Santander", "Corriente", 1200.5D,new ArrayList<Interes>(Arrays.asList(new Interes[]{new Interes(1,2.5F)})));
    try {
      bankServices.createAccount(newBank);
      fail("Fail test");
    } catch (Exception e) {
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
    Banca updatedBank = new Banca(id, "Santander", "Corriente", 1200.5D,new ArrayList<Interes>(Arrays.asList(new Interes[]{new Interes(1,2.5F)})));
    try {
      bankServices.updateAccount(updatedBank);
      fail("Fail test");
    } catch (Exception e) {
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
    } catch (Exception e) {
      Assert.assertEquals(BancaNotFoundException.class, e.getClass());
    }
  }

  /* Filter accounts by type tests */
  @Test
  public void itShouldGetAccountType() {
    String type = "Ahorro";
    List<Banca> results = new ArrayList<>();
    results.add(new Banca(3, "BBVA", "Ahorro", 14000D,new ArrayList<Interes>(Arrays.asList(new Interes[]{new Interes(1,2.3F)}))));
    when(this.bankRepository.findByType(type)).thenReturn(results);
    List<Banca> repositoryAccounts = this.bankServices.getType(type);
    Assert.assertEquals(results, repositoryAccounts);
  }

  /* Money transfer tests */
  @Test
  public void itShouldBeTransferMoneyBetweenAccounts() throws BancaNotFoundException, TransferException, Exception {
    Transfer transferData = new Transfer(1, 2, 200D);
    this.bankServices.moneyTransfer(transferData);
    Assert.assertEquals((Double) 1200D, this.bankRepository.findById(2).get().getAmount());
  }

  @Test
  public void itshouldBeFailFromAccountType() {
    Transfer transferData = new Transfer(3, 2, 200D);
    try {
      this.bankServices.moneyTransfer(transferData);
      fail();
    } catch (TransferException e) {
      Assert.assertEquals(
          "La transferencia no pudo completarse porque las cuentas de tipo ahorro no permiten esta operacion",
          e.getMessage());
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void itshouldBeFailToAccountType() {
    Transfer transferData = new Transfer(2, 3, 200D);
    try {
      this.bankServices.moneyTransfer(transferData);
      fail("This test must to fail");
    } catch (TransferException e) {
      Assert.assertEquals(
          "La transferencia no pudo completarse porque las cuentas de tipo ahorro no permiten esta operacion",
          e.getMessage());
    } catch (Exception e) {
      fail("This isn't correct exception");
    }
  }

  @Test
  public void itShouldFailNonExistFromAccount() {
    Transfer transferData = new Transfer(5, 1, 150D);
    try {
      this.bankServices.moneyTransfer(transferData);
      fail("This test must to fail");
    } catch (BancaNotFoundException e) {
      // test OK
    } catch (Exception e) {
      fail("This isn't correct exception");
    }
  }

  @Test
  public void itShouldFailNonExistToAccount() {
    Transfer transferData = new Transfer(1, 5, 150D);
    try {
      this.bankServices.moneyTransfer(transferData);
      fail("This test must to fail");
    } catch (BancaNotFoundException e) {
      // test OK
    } catch (Exception e) {
      fail("This isn't correct exception");
    }
  }

  @Test
  public void itShouldFailInvalidAmount() {
    Transfer transferData = new Transfer(1, 2, 0D);
    try {
      this.bankServices.moneyTransfer(transferData);
      fail("this test must to fail");
    } catch (TransferException e) {
      Assert.assertEquals("La transferencia no pudo completarse porque la cantidad es inválida", e.getMessage());
    } catch (Exception e) {
      fail("This isn't correct exception");
    }
  }

  @Test
  public void itShouldFailSameAccountTo() {
    Transfer transferData = new Transfer(1, 1, 200D);
    try {
      this.bankServices.moneyTransfer(transferData);
      fail("This test must to fail");
    } catch (TransferException e) {
      Assert.assertEquals("La transferencia no pudo completarse porque no puedes transferir dinero a tu propia cuenta",
          e.getMessage());
    } catch (Exception e) {
      fail("This isn't correct exception");
    }
  }

  /* Accounts operations tests */

  @Test
  public void itShouldOKDepositmoneyCorrienteAccount() throws BancaNotFoundException, WithoutFoundsException, Exception {
    Banca updatedValues = this.bankServices.accountOps(2, 20D);
    Double expected = 1020D;
    Assert.assertEquals(expected, updatedValues.getAmount());
  }

  @Test
  public void itShouldOKDepositMoneyAhorroAccount() throws BancaNotFoundException, WithoutFoundsException, Exception {
    Banca updateValues = this.bankServices.accountOps(3, 20D);
    Double expected = 1250D;
    Assert.assertEquals(expected, updateValues.getAmount());
  }

  @Test
  public void itShouldOKWithdrawMoney() throws BancaNotFoundException, WithoutFoundsException, Exception {
    Banca updatedValues = this.bankServices.accountOps(2, -20D);
    Double expected = 980D;
    Assert.assertEquals(expected, updatedValues.getAmount());
  }

  @Test
  public void itShouldFailNonExistAccount() {
    try {
      this.bankServices.accountOps(5, 50D);
      fail("This test must fail");
    } catch (BancaNotFoundException e) {
      // test OK
    } catch (Exception e) {
      fail("This isn't correct exception");
    }
  }

  @Test
  public void itShouldFailInvalidWithdrawAmountAhorroType() {
    try {
      this.bankServices.accountOps(3, -2000D);
      fail("This test must fail");
    } catch (WithoutFoundsException e) {
      // test OK
    } catch (Exception e) {
      fail("This isn' correct exception");
    }
  }

  @Test
  public void itShouldFailInvalidWithdrawAmountCorrienteType() {
    try {
      this.bankServices.accountOps(2, -2050D);
      fail("This test must fail");
    } catch (WithoutFoundsException e) {
      // test OK
    } catch (Exception e) {
      fail("This isn' correct exception");
    }
  }

  /* Test de tipos de intereses*/
  @Test
  public void itShouldgetAllInteres() {
    List<Interes> interes = this.interesService.getAllInteres();
    if (interes.size() < 1) {
      fail("There are accounts");
    }
  }
}