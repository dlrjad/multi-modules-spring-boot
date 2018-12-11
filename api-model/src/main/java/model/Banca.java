package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Banca {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String client;
	private String type;
  private Integer amount;


  public Banca() {
  }

  public Banca(Integer id, String client, String type, Integer amount) {
    this.id = id;
    this.client = client;
    this.type = type;
    this.amount = amount;
  }
  
  public Banca(String client, String type, Integer amount) {
    this.client = client;
    this.type = type;
    this.amount = amount;
  }


  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getClient() {
    return this.client;
  }

  public void setClient(String client) {
    this.client = client;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Integer getAmount() {
    return this.amount;
  }

  public void setAmount(Integer amount) {
    this.amount = amount;
  }
  
}
