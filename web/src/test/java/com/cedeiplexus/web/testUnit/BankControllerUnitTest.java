package com.cedeiplexus.web.testUnit;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import controller.BancaController;
import exceptions.BancaNotFoundException;
import model.Banca;
import services.BankService;

public class BankControllerUnitTest {
  
  private BancaController bankController;
  private BankService  bankServices;
  
  private List<Banca> banks = new ArrayList<>();

  @Before
  public void setUp() {
	  banks.add( new Banca(1, "Santander", "Corriente", 9200.5));
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
    Banca bank = new Banca(id, "Santander", "Corriente", 1200.5);
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
    Banca bank = new Banca(id, "Santander", "Corriente", 9200.5);
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
    Banca bank = new Banca(id, "Santander", "Corriente", 9200.5);
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
  public void itShoulDeleteWithdBadRequestStatusCode() {
    Integer id = null;
    ResponseEntity<?> httpResponse = bankController.deleteBank(id);
    Assert.assertEquals(HttpStatus.BAD_REQUEST, httpResponse.getStatusCode());
  }

}