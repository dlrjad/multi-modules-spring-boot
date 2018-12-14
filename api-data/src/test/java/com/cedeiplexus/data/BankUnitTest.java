package com.cedeiplexus.data;

import org.junit.Assert;
import org.junit.Test;

import model.Banca;

public class BankUnitTest {
  
  private Banca bank = new Banca(3242, "Santander", "Corriente", 1200.5);

  @Test
  public void itShouldNotBeNull() {
    Assert.assertNotNull(bank);
  }

  @Test
  public void itShouldBeEqualID() {
    Assert.assertEquals(3242, bank.getId().intValue());
  }

  @Test
  public void itShouldBeEqualClient() {
    Assert.assertEquals("Santander", bank.getClient());
  }

  @Test
  public void itShouldBeEqualType() {
    Assert.assertEquals("Corriente", bank.getType());
  }

  @Test
  public void itShouldBeEqualAmount() {
    double errorMargin = 0.0000001;
    Assert.assertEquals(1200.5, bank.getAmount(), errorMargin);
  }

}