package model;

import java.util.Date;

/**
 * Clase entidad Transfer
 */
public class Transfer {

    private Date date;
    private Integer fromAccount;
    private Integer toAccount;
    private Double moneyToTransfer;

    /**
     * Constructor por defecto
     */
    public Transfer() {
        this.date = new Date();
    }

    /**
     * Constructor con datos
     * @param fromAccount cuenta que entrega dinero
     * @param toAccount cuenta que recible dinero
     * @param moneyToTransfer cantidad de dinero transferida
     */
    public Transfer(Integer fromAccount, Integer toAccount, Double moneyToTransfer) {
        this.date = new Date();
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.moneyToTransfer = moneyToTransfer;
    }

    /**
     * Obtiene la fecha de la transferencia
     * @return Date
     */
    public Date getDate() {
        return this.date;
    }

    /**
     * Obtiene la cuenta que gasta el dinero
     * @return
     */
    public Integer getFromAccount() {
        return this.fromAccount;
    }

    /**
     * Obtiene la cuenta que recibe el dinero
     * @return
     */
    public Integer getToAccount() {
        return this.toAccount;
    }

    /**
     * Obtiene el dinero a transferir
     * @return
     */
    public Double getMoneyToTransfer() {
        return this.moneyToTransfer;
    }
    
}