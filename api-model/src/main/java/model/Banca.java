package model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Banca")
public class Banca {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
  protected Integer id;

  @Column(name = "client")
  protected String client;
  @Column(name = "type")
  protected String type;
  @Column(name = "amount")
  protected Integer amount;

  @Column(name = "banca_interes")
  @OneToMany(mappedBy = "banca", cascade = CascadeType.ALL, fetch = FetchType.EAGER) // mapped by va a la variable banca
  protected List<Interes> banca_interes; // creada en interes

  public Banca() {
    this.id = 0;
    this.client = null;
    this.type = null;
    this.amount = null;
    this.banca_interes = null;
  }

  public Banca(Integer id, String client, String type, Integer amount, List<Interes> intereses) {
    this.id = id;
    this.client = client;
    this.type = type;
    this.amount = amount;
    this.banca_interes = intereses;
  }

  public Banca(String client, String type, Integer amount, List<Interes> intereses) {
    this.id = 0;
    this.client = client;
    this.type = type;
    this.amount = amount;
    this.banca_interes = intereses;
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

  public List<Interes> getBanca_interes() {
    return this.banca_interes;
  }

  public void setBanca_interes(List<Interes> banca_interes) {
    if (banca_interes == null || banca_interes.size() > 0) {
      this.banca_interes = banca_interes;
    }
  }
}
