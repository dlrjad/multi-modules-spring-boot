package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="Banca")
public class Banca {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
	private Integer id;
	private String client;
	private String type;
  private Integer amount;
  

  @OneToMany(mappedBy = "banca", cascade = CascadeType.ALL, orphanRemoval = true) // mapped by va a la variable banca creada en interes
  //@JoinColumn(name = "banca_interes", referencedColumnName = "id", nullable = false)
  //@JoinTable(name ="banca_interes", joinColumns={@JoinColumn( name ="id")}, inverseJoinColumns = {@JoinColumn(name = "id_interes")})
  //@OneToMany(mappedBy = "banca")
  protected List<Interes> banca_interes = new ArrayList<>();


  public Banca() {
    this.id = 0;
    this.client = null;
    this.type = null;
    this.amount = null;
    this.banca_interes = null;
  }

  public Banca(Integer id, String client, String type, Integer amount,Interes j) {
    this.id = id;
    this.client = client;
    this.type = type;
    this.amount = amount;
    this.banca_interes.add(j);
  }
  
  public Banca(String client, String type, Integer amount, Interes j) {
    this.client = client;
    this.type = type;
    this.amount = amount;
    this.banca_interes.add(j);
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
     if(banca_interes == null || banca_interes.size() > 0){
      this.banca_interes = banca_interes;
     }
   }

    public void setBancaInteres(Interes j){
      this.banca_interes.add(j);
    }
  
}
