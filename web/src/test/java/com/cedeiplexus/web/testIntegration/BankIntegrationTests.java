package com.cedeiplexus.web.testIntegration;

import com.cedeiplexus.web.testIntegration.Util.Util;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import model.Banca;
import model.Transfer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import persistence.BancaRepository;
import web.BancaApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(
  classes = BancaApplication.class
)
public class BankIntegrationTests {

  @Autowired
  private WebApplicationContext wac;

  private MockMvc mockMvc;

  Util util = new Util();

  @Autowired
  @Qualifier("bancaRepository")
  BancaRepository bancaRepository;

  @Before
  public void setUp() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
  }

  @Test
	public void testGetBanks() throws Exception {
    this.mockMvc.perform(get("/api/bancos"))
    .andExpect(status().isOk());
  }

  @Test
	public void testGetBank() throws Exception {
    this.mockMvc.perform(get("/api/banco/1"))
    .andExpect(status().isOk());
  }

  @Test
	public void testNotFoundGetBank() throws Exception {
    this.mockMvc.perform(get("/api/banco/9999"))
    .andExpect(status().isNotFound());
  }

  @Test
	public void testMethodNotAllowedGetBank() throws Exception {
    this.mockMvc.perform(get("/api/banco/"))
    .andExpect(status().isMethodNotAllowed());
  }

  @Test
	public void testCreatePostBank() throws Exception {
    Integer id = 9998;
    Banca bank = new Banca(id, "Santander", "Corriente", 1200.5);
    this.mockMvc.perform(post("/api/banco")    
    .content(util.asJsonString(bank))
    .contentType(MediaType.APPLICATION_JSON)
    .accept(MediaType.APPLICATION_JSON))
    .andExpect(status().isOk());
  }

  @Test
	public void testNotCreatePostBank() throws Exception {
    Integer id = 1;
    Banca bank = new Banca(id, "Santander", "Corriente", 1200.5);
    this.mockMvc.perform(post("/api/banco")    
    .content(util.asJsonString(bank))
    .contentType(MediaType.APPLICATION_JSON)
    .accept(MediaType.APPLICATION_JSON))
    .andExpect(status().isInternalServerError());
  }

  @Test
	public void testUpdatePutBank() throws Exception {
    Integer id = 1;
    Banca bank = new Banca(id, "Santander", "Corriente", 9200.5);
    this.mockMvc.perform(put("/api/banco/1")    
    .content(util.asJsonString(bank))
    .contentType(MediaType.APPLICATION_JSON)
    .accept(MediaType.APPLICATION_JSON))
    .andExpect(status().isOk());
  }

  @Test
	public void testNotFoundPutBank() throws Exception {
    Integer id = 9999;
    Banca bank = new Banca(id, "Santander", "Corriente", 1200.5);
    this.mockMvc.perform(put("/api/banco/9999")    
    .content(util.asJsonString(bank))
    .contentType(MediaType.APPLICATION_JSON)
    .accept(MediaType.APPLICATION_JSON))
    .andExpect(status().isNotFound());
  }

  @Test
	public void testMethodNotAllowedPutBank() throws Exception {
    Integer id = 9999;
    Banca bank = new Banca(id, "Santander", "Corriente", 1200.5);
    this.mockMvc.perform(put("/api/banco/")    
    .content(util.asJsonString(bank))
    .contentType(MediaType.APPLICATION_JSON)
    .accept(MediaType.APPLICATION_JSON))
    .andExpect(status().isMethodNotAllowed());
  }

  @Test
	public void testDeleteBank() throws Exception {
    this.mockMvc.perform(delete("/api/banco/1"))
    .andExpect(status().isOk());
  }

  @Test
	public void testNotFoundDeleteBank() throws Exception {
    this.mockMvc.perform(delete("/api/banco/9999"))
    .andExpect(status().isNotFound());
  }

  @Test
	public void testMethodNotAllowedDeleteBank() throws Exception {
    this.mockMvc.perform(delete("/api/banco/"))
    .andExpect(status().isMethodNotAllowed());
  }
  
  @Test
  public void testGetTypeBank() throws Exception {
    this.mockMvc.perform(get("/api/bancos/Ahorro"))
	.andExpect(status().isOk());
  }
  
  @Test
  public void testNotFoundGetTypeBank() throws Exception {
    this.mockMvc.perform(get("/api/bancos/Ahorro404"))
	.andExpect(status().isOk());
  }
  
  @Test
  public void testTransferBank() throws Exception {
    Integer id1 = 1;
	Integer id2 = 2;
	Double amount = 800.5;
	Transfer transfer = new Transfer(id1, id2, amount);
    this.mockMvc.perform(put("/api/banco/transfers")
    .content(util.asJsonString(transfer))
    .contentType(MediaType.APPLICATION_JSON)
    .accept(MediaType.APPLICATION_JSON))
	.andExpect(status().isOk());
  }
  
  @Test
  public void testNotFoundTransferBank() throws Exception {
    Integer id1 = 9999;
	Integer id2 = 2;
	Double amount = 800.5;
	Transfer transfer = new Transfer(id1, id2, amount);
    this.mockMvc.perform(put("/api/banco/transfers")
    .content(util.asJsonString(transfer))
    .contentType(MediaType.APPLICATION_JSON)
    .accept(MediaType.APPLICATION_JSON))
	.andExpect(status().isNotFound());
  }
  
  @Test
  public void testOPSBank() throws Exception {
    Integer id = 2;
	Double amount =  800D;
    this.mockMvc.perform(put(String.format("/api/banco/%d/ops/%s", id,amount.toString()))
    .content(util.asJsonString(id))
    .content(util.asJsonString(amount))
    .contentType(MediaType.APPLICATION_JSON)
    .accept(MediaType.APPLICATION_JSON))
	.andExpect(status().isOk());
  }
  
  @Test
  public void testNotFoundOPSBank() throws Exception {
    Integer id = 9999;
	Double amount =  800D;
    this.mockMvc.perform(put(String.format("/api/banco/%d/ops/%s", id,amount.toString()))
    .content(util.asJsonString(id))
    .content(util.asJsonString(amount))
    .contentType(MediaType.APPLICATION_JSON)
    .accept(MediaType.APPLICATION_JSON))
	.andExpect(status().isNotFound());
  }
  
}