package com.cedeiplexus.web.testUnit;

import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import controller.BancaController;
import exceptions.BancaNotFoundException;
import exceptions.TransferException;
import exceptions.WithoutFoundsException;
import model.Banca;
import model.Interes;
import model.Transfer;
import services.BankService;

public class BankControllerUnitTest {

  private BancaController bankController;
  private BankService bankServices;

  private List<Banca> banks = new ArrayList<>();

  @Before
  public void setUp() {
    banks.add(new Banca(1, "Santander", "Corriente", 9200.5D,
        new ArrayList<Interes>(Arrays.asList(new Interes[] { new Interes(2, 2.3F) }))));
    bankServices = Mockito.mock(BankService.class);
    when(bankServices.getAccountById(1)).thenReturn(this.banks.get(0));
    bankController = new BancaController(bankServices);
  }

  @Test
  public void itShouldGetBanks() {
    ResponseEntity<?> httpResponse = bankController.getBanks();
    Assert.assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
  }

  @Test
  public void itShouldGetBankWith200StatusCode() {
    Integer id = 1;
    ResponseEntity<?> httpResponse = bankController.getBankById(id);
    Assert.assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
  }

  @Test
  public void itShouldGetBankWithNotFoundStatusCode() {
    Integer id = 9999;
    ResponseEntity<?> httpResponse = bankController.getBankById(id);
    Assert.assertEquals(HttpStatus.NOT_FOUND, httpResponse.getStatusCode());
  }

  @Test
  public void itShouldGetBankWithNotFoundErrorStatusCode() {
    ResponseEntity<?> httpResponse = bankController.getBankById(null);
    Assert.assertEquals(HttpStatus.NOT_FOUND, httpResponse.getStatusCode());
  }

  @Test
  public void itShouldPostWith200StatusCode() {
    Integer id = 9998;
    Banca bank = new Banca(id, "Santander", "Corriente", 1200.5D,new ArrayList<Interes>(Arrays.asList(new Interes[]{new Interes(2,2.5F)})));
    ResponseEntity<?> httpResponse = bankController.createBank(bank);
    Assert.assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
  }

  @Test
  public void itShouldPostWithBadRequestStatusCode() {
    Banca bank = null;
    ResponseEntity<?> httpResponse = bankController.createBank(bank);
    Assert.assertEquals(HttpStatus.BAD_REQUEST, httpResponse.getStatusCode());
  }

  @Test
  public void itShouldPostWithInternalServerErrorStatusCode() {
    Banca bank = null;
    ResponseEntity<?> httpResponse = bankController.createBank(bank);
    Assert.assertEquals(HttpStatus.BAD_REQUEST, httpResponse.getStatusCode());
  }

  @Test
  public void itShouldPutWith200StatusCode() {
    Integer id = 1;
    Banca bank = new Banca(id, "Santander", "Corriente", 9200.5D,new ArrayList<Interes>(Arrays.asList(new Interes[]{new Interes(2,2.5F)})));
    ResponseEntity<?> httpResponse = bankController.updateBank(1, bank);
    Assert.assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
  }

  @Test
  public void itShouldPutWithBadRequestStatusCode() {
    Banca bank = null;
    ResponseEntity<?> httpResponse = bankController.updateBank(1, bank);
    Assert.assertEquals(HttpStatus.BAD_REQUEST, httpResponse.getStatusCode());
  }

  @Test
  public void itShouldPutWithNotFoundStatusCodeStatusCode() {
    Integer id = 9999;
    Banca bank = new Banca(id, "Santander", "Corriente", 9200.5D,new ArrayList<Interes>(Arrays.asList(new Interes[]{new Interes(2,2.5F)})));
    when(this.bankServices.updateAccount(bank)).thenThrow(new BancaNotFoundException());
    ResponseEntity<?> httpResponse = bankController.updateBank(id, bank);
    Assert.assertEquals(HttpStatus.NOT_FOUND, httpResponse.getStatusCode());
  }

  @Test
  public void itShouldDeleteWith200StatusCode() {
    Integer id = 1;
    ResponseEntity<?> httpResponse = bankController.deleteBank(id);
    Assert.assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
  }

  @Test
  public void itShouldDeleteWithNotFoundStatusCode() throws BancaNotFoundException, Exception {
    Integer id = 9999;
    when(this.bankServices.removeAccount(id)).thenThrow(new BancaNotFoundException());
    ResponseEntity<?> httpResponse = bankController.deleteBank(id);
    Assert.assertEquals(HttpStatus.NOT_FOUND, httpResponse.getStatusCode());
  }

  @Test
  public void itShoulDeleteWithdInternalServerErrorStatusCode() {
    Integer id = null;
    ResponseEntity<?> httpResponse = bankController.deleteBank(id);
    Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, httpResponse.getStatusCode());
  }

  @Test
  public void itShouldGetTypeWith200StatusCode() {
    String type = "Corriente";
    ResponseEntity<?> httpResponse = bankController.getType(type);
    Assert.assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
  }

  @Test
  public void itShouldGetTypeWithNotFoundStatusCode() {
    String type = "Corriente";
    when(this.bankServices.getType(type)).thenThrow(new BancaNotFoundException());
    ResponseEntity<?> httpResponse = bankController.getType(type);
    Assert.assertEquals(HttpStatus.NOT_FOUND, httpResponse.getStatusCode());
  }

  @Test
  public void itShouldGetTypeWithBadRequestStatusCode() {
    String type = null;
    when(this.bankServices.getType(type)).thenThrow(new NullPointerException());
    ResponseEntity<?> httpResponse = bankController.getType(type);
    Assert.assertEquals(HttpStatus.BAD_REQUEST, httpResponse.getStatusCode());
  }

  @Test
  public void itShouldTransferWith200StatusCode() throws ParseException {
    Integer id1 = 1;
    Integer id2 = 2;
    Double amount = 400.5;
    Transfer transfer = new Transfer(id1, id2, amount);
    ResponseEntity<?> httpResponse = bankController.transfer(transfer);
    Assert.assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
  }

  @Test
  public void itShouldTransferWithNotFoundStatusCode() throws BancaNotFoundException, TransferException, Exception {
    Integer id1 = 9999;
    Integer id2 = 2;
    Double amount = 400.5;
    Transfer transfer = new Transfer(id1, id2, amount);
    when(this.bankServices.moneyTransfer(transfer)).thenThrow(new BancaNotFoundException());
    ResponseEntity<?> httpResponse = bankController.transfer(transfer);
    Assert.assertEquals(HttpStatus.NOT_FOUND, httpResponse.getStatusCode());
  }

  @Test
  public void itShouldTransferWithInternalServerErrorStatusCode()
      throws BancaNotFoundException, TransferException, Exception {
    Integer id1 = null;
    Integer id2 = 2;
    Double amount = 400.5;
    Transfer transfer = new Transfer(id1, id2, amount);
    when(this.bankServices.moneyTransfer(transfer)).thenThrow(new NullPointerException());
    ResponseEntity<?> httpResponse = bankController.transfer(transfer);
    Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, httpResponse.getStatusCode());
  }

  @Test
  public void itShouldOPSWith200StatusCode() {
    Integer id = 1;
    Double amount = 400.5;
    ResponseEntity<?> httpResponse = bankController.ops(id, amount);
    Assert.assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
  }

  @Test
  public void itShouldOPSWithNotFoundStatusCode() throws BancaNotFoundException, WithoutFoundsException, Exception {
    Integer id = 9999;
    Double amount = 400.5;
    when(this.bankServices.accountOps(id, amount)).thenThrow(new BancaNotFoundException());
    ResponseEntity<?> httpResponse = bankController.ops(id, amount);
    Assert.assertEquals(HttpStatus.NOT_FOUND, httpResponse.getStatusCode());
  }

  @Test
  public void itShouldOPSWithInitialServerErrorStatusCode()
      throws BancaNotFoundException, WithoutFoundsException, Exception {
    Integer id = null;
    Double amount = null;
    when(this.bankServices.accountOps(id, amount)).thenThrow(new NullPointerException());
    ResponseEntity<?> httpResponse = bankController.ops(id, amount);
    Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, httpResponse.getStatusCode());
  }

}