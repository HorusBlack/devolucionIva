/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class XmlDatos {

    private String fechaFactura, subTotal, Total, conceptoXml, folioFiscal, iva, iva_retenido, isr, folioInterno, rfc, proveedor, formaPago;

    public String getIva() {
        return iva;
    }

    public String getFolioInterno() {
        return folioInterno;
    }

    public void setFolioInterno(String folioInterno) {
        this.folioInterno = folioInterno;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getIva_retenido() {
        return iva_retenido;
    }

    public void setIva_retenido(String iva_retenido) {
        this.iva_retenido = iva_retenido;
    }

    public String getIsr() {
        return isr;
    }

    public void setIsr(String isr) {
        this.isr = isr;
    }

    public void setIva(String iva) {
        this.iva = iva;
    }

    public String getFechaFactura() {
        return fechaFactura;
    }

    public void setFechaFactura(String fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String Total) {
        this.Total = Total;
    }

    public String getConceptoXml() {
        return conceptoXml;
    }

    public void setConceptoXml(String conceptoXml) {
        this.conceptoXml = conceptoXml;
    }

    public String getFolioFiscal() {
        return folioFiscal;
    }

    public void setFolioFiscal(String folioFiscal) {
        this.folioFiscal = folioFiscal;
    }
}
