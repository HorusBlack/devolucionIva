/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

/**
 *Clase que recibe y administra la informacion de un objeto AuxIvaAcred
 * @author horusblack
 */
public class AuxIvaAcred {

    private String tipoPoliza, fecha, concepto;
    private double debe, haber;
    private int noPoliza;

    public String getTipoPoliza() {
        return tipoPoliza;
    }

    public void setTipoPoliza(String tipoPoliza) {
        this.tipoPoliza = tipoPoliza;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public double getDebe() {
        return debe;
    }

    public void setDebe(double debe) {
        this.debe = debe;
    }

    public double getHaber() {
        return haber;
    }

    public void setHaber(double haber) {
        this.haber = haber;
    }

    public int getNoPoliza() {
        return noPoliza;
    }

    public void setNoPoliza(int noPoliza) {
        this.noPoliza = noPoliza;
    }


    
    
}
