/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 * Clase que se encarga de generar un objeto tipo PolizaDatos de la informacion
 * proporcionada de la BD
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class PolizaDatos {

    private String idDoctodig, numeroPoliza, empresa, rutaXml, nombreXml, tipoPoliza;

    public String getIdDoctodig() {
        return idDoctodig;
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
