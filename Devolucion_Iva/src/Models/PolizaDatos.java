/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;
//lulu conexion computadora

/**
 * Clase que se encarga de generar un objeto tipo PolizaDatos de la informacion
 * proporcionada de la BD
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class PolizaDatos {

    private String idDoctodig, numeroPoliza, empresa, rutaXml, nombreXml, tipoPoliza, cuenta, fechaPago, montoMov;
    private int conXml;

    public String getMontoMov() {
        return montoMov;
    }

    public int getConXml() {
        return conXml;
    }

    public void setConXml(int conXml) {
        this.conXml = conXml;
    }

    public void setMontoMov(String montoMov) {
        this.montoMov = montoMov;
    }

    public String getIdDoctodig() {
        return idDoctodig;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
    }

    public void setIdDoctodig(String idDoctodig) {
        this.idDoctodig = idDoctodig;
    }

    public String getNumeroPoliza() {
        return numeroPoliza;
    }

    public void setNumeroPoliza(String numeroPoliza) {
        this.numeroPoliza = numeroPoliza;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getRutaXml() {
        return rutaXml;
    }

    public void setRutaXml(String rutaXml) {
        this.rutaXml = rutaXml;
    }

    public String getNombreXml() {
        return nombreXml;
    }

    public void setNombreXml(String nombreXml) {
        this.nombreXml = nombreXml;
    }

    public String getTipoPoliza() {
        return tipoPoliza;
    }

    public void setTipoPoliza(String tipoPoliza) {
        this.tipoPoliza = tipoPoliza;
    }

}
