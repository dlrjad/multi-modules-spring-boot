package model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Interes
 */

@Entity
@Table(name ="Interes")
public class Interes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id_interes")
    private Integer id_interes;
    private Float interes;
    private Date fecha;

     //@ManyToOne(cascade = CascadeType.PERSIST, optional = false)
     //@JoinColumn(name="id", nullable = false)

     @ManyToOne(fetch = FetchType.LAZY)
     // @JoinColumn(name = "id")
    private Banca banca;

    /**
     * Constructor por defecto
     */
    public Interes() {
        this.id_interes = 0;
        this.interes = null;
        this.fecha = new Date();
    } 

    /**
     * Constructor con parametros
     * @param interes porcentaje de interes
     * @param date ultima fecha asociada al interes
     */
    public Interes(Integer id,Float interes) {
        this.id_interes = id;
        this.interes = interes;
        this.fecha = new Date();
    }

     /**
     * Obtiene los intereses
     * @return interes
     */
    public Float getInteres() {
        return this.interes;
    }

    /**
     * Asigna el interes
     */
    public void setInteres(Float interes) {
        this.interes = interes;
    }

     /**
     * Obtiene la fecha de la transferencia
     * @return Date
     */
    public Date getFecha() {
        return this.fecha;
    }

    /**
     * Asigna la fecha
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * Obtiene el id_interes
     * @return Date
     */
    public Integer getId_interes() {
        return this.id_interes;
    }

    /*
    * Asigna el id_interes
    */
    public void setId_interes(Integer id_interes) {
        this.id_interes = id_interes;
    }
}