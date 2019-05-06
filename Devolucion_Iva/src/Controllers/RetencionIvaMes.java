/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

/**
 *
 * @author horusblack
 */
public class RetencionIvaMes {

    private String tipoPoliza, polCombinada, concepto, rfcProveedor, conceptoGasto, conceptosBase, factura, fecha;
    private double subTotal, ivaAcreditable, importe, total;

    public String getTipoPoliza() {
        return tipoPoliza;
    }

    public void setTipoPoliza(String tipoPoliza) {
        this.tipoPoliza = tipoPoliza;
    }

    public String getPolCombinada() {
        return polCombinada;
    }

    public void setPolCombinada(String polCombinada) {
        this.polCombinada = polCombinada;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getRfcProveedor() {
        return rfcProveedor;
    }

    public void setRfcProveedor(String rfcProveedor) {
        this.rfcProveedor = rfcProveedor;
    }

    public String getConceptoGasto() {
        return conceptoGasto;
    }

    public void setConceptoGasto(String conceptoGasto) {
        this.conceptoGasto = conceptoGasto;
    }

    public String getConceptosBase() {
        return conceptosBase;
    }

    public void setConceptosBase(String conceptosBase) {
        this.conceptosBase = conceptosBase;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public double getIvaAcreditable() {
        return ivaAcreditable;
    }

    public void setIvaAcreditable(double ivaAcreditable) {
        this.ivaAcreditable = ivaAcreditable;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

}
