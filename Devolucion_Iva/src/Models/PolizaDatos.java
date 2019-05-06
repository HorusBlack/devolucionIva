/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import java.util.Date;

/**
 * Clase que se encarga de generar un objeto tipo PolizaDatos
 * de la informacion proporcionada de la BD
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class PolizaDatos {

    /**
     * ORIGEN
     */
    private int numeroPoliza, ejercicio, periodo;
    private Date fechaPoliza;
    private String conceptoPoliza, documentos, tipoPoliza, contabilizada, origen;

    public int getNumeroPoliza() {
        return numeroPoliza;
    }

    public int getPeriodo() {
        return periodo;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    public String getTipoPoliza() {
        return tipoPoliza;
    }

    public void setTipoPoliza(String tipoPoliza) {
        this.tipoPoliza = tipoPoliza;
    }

    public String getContabilizada() {
        return contabilizada;
    }

    public void setContabilizada(String contabilizada) {
        this.contabilizada = contabilizada;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public void setNumeroPoliza(int numeroPoliza) {
        this.numeroPoliza = numeroPoliza;
    }

    public int getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(int ejercicio) {
        this.ejercicio = ejercicio;
    }

    public Date getFechaPoliza() {
        return fechaPoliza;
    }

    public void setFechaPoliza(Date fechaPoliza) {
        this.fechaPoliza = fechaPoliza;
    }

    public String getConceptoPoliza() {
        return conceptoPoliza;
    }

    public void setConceptoPoliza(String conceptoPoliza) {
        this.conceptoPoliza = conceptoPoliza;
    }

    public String getDocumentos() {
        return documentos;
    }

    public void setDocumentos(String documentos) {
        this.documentos = documentos;
    }

}
