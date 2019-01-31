package model;

import java.util.ArrayList;
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

import exceptions.WithoutFoundsException;

/**
 * Clase entidad Banca
 */
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
  protected Double amount;

  @Column(name = "banca_interes")
  @OneToMany(mappedBy = "banca", cascade = CascadeType.ALL, fetch = FetchType.EAGER) // mapped by va a la variable banca
  protected List<Interes> banca_interes; // creada en interes

  private Double opsLimit = 1000D;
  /**
   * Constructor por defecto
   */
  public Banca() {
    this.id = 0;
    this.client = null;
    this.type = null;
    this.amount = null;
    this.banca_interes = null;
  }

  public Banca(Integer id, String client, String type, Double amount, ArrayList<Interes> arrayList) {
    this.id = id;
    this.client = client;
    if(type==null) this.type = ""; else this.type = type;
    this.amount = amount;
    this.banca_interes = arrayList;
  }

  public Banca(String client, String type, Double amount, List<Interes> intereses) {
    this.id = 0;
    this.client = client;
    this.type = type;
    this.amount = amount;
    this.banca_interes = intereses;
  }

  /**
   * Obtiene el identificador de cuenta
   * 
   * @return
   */
  public Integer getId() {
    return this.id;
  }

  /**
   * Estavlece un identificador de cuenta
   * 
   * @param id
   */
  public void setId(Integer id) {
    this.id = id;
  }

  /**
   * Obtiene el banco al que pertenece la cuenta
   * 
   * @return
   */
  public String getClient() {
    return this.client;
  }

  /**
   * Establece un nuevo banco a la cuenta
   * 
   * @param client
   */
  public void setClient(String client) {
    if (client != null) {
      this.client = client;
    }
  }

  /**
   * Obtiene el tipo de cuenta
   * 
   * @return
   */
  public String getType() {
    return this.type;
  }

  /**
   * Establece un nuevo tipo de cuenta
   * 
   * @param type
   */
  public void setType(String type) {
    if (type != null) {
      this.type = type;
    }
  }

  /**
   * Obtiene los fondos de la cuenta
   * 
   * @return
   */
  public Double getAmount() {
    return this.amount;
  }

  /**
   * Establece nuevos fondos en la cuenta
   * 
   * @param amount
   */
  public void setAmount(Double amount) {
    if (amount != null) {
      this.amount = amount;
    }
  }

  /**
   * Retira una cantidad de dinero especificada
   * 
   * @param canti
   * @return
   */
  public Double withdrawMoney(Double canti) throws WithoutFoundsException {
    this.amount = this.amount - canti;
    if (this.opsLimit != null && canti > this.opsLimit || this.amount < 0) {
      this.amount += canti;
      throw new WithoutFoundsException();
    }
    return this.amount;
  }

  /**
   * Ingresa una cantidad de dinero especificada
   * 
   * @param canti
   * @return
   */
  public Double depositMoney(Double canti) {
    if(this.type.equals("Ahorro")){
      canti *= this.banca_interes.get(this.banca_interes.size() -1).getInteres();
    }
    this.amount += canti;
    return this.amount;
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
