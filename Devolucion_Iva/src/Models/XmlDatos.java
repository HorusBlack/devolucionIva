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

    private String fechaFactura, subTotal, Total, conceptoXml, folioFiscal, iva;

    public String getIva() {
        return iva;
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
