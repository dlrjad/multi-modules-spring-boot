package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import exceptions.WithoutFoundsException;

/**
 * Clase entidad Banca
 */
@Entity
public class Banca {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String client;
  private String type;
  private Double amount;
  private Double opsLimit;

  /**
   * Constructor por defecto
   */
  public Banca() {
  }

  /**
   * Constructor para modificar cuenta
   * 
   * @param id     identificador de cuenta
   * @param client Banco al que pertenece
   * @param type   Tipo de cuenta
   * @param amount Cantidad de dinero que tiene
   */
  public Banca(Integer id, String client, String type, Double amount) {
	this.id = id;
    this.client = client;
    if(type==null) this.type = ""; else this.type = type;
    this.amount = amount;
    this.opsLimit = this.type.equals("Corriente") ? -1000D : null;
  }

  /**
   * Constructor para añadir cuenta
   * 
   * @param client Banco al que pertenece
   * @param type   Tipo de cuenta
   * @param amount Cantidad de dinero que tiene
   */
  public Banca(String client, String type, Double amount) {
    this.client = client;
    this.type = type;
    this.amount = amount;
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
    this.amount -= canti;
    if (this.opsLimit != null && this.amount < this.opsLimit || this.amount < 0) {
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
    this.amount += canti;
    return this.amount;
  }

}
