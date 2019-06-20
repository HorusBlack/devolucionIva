/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author horusblack
 */
public class PolizaProcesada {

    /*
    INSERT INTO [dbo].[POLIZA_PROCESADA]
           ([ID_DOCTODIG]
           ,[ARCHIVO]
           ,[CLAVE_POLIZA]
           ,[TIPO]
           ,[EMPRESA]
           ,[PROCESADO])
     
     */
    private String idDoctoDig, nombreArchivo, clavePoliza, tipoPoliza, numeroEmpresa, estatusProcesado;

    public String getIdDoctoDig() {
        return idDoctoDig;
    }

    public void setIdDoctoDig(String idDoctoDig) {
        this.idDoctoDig = idDoctoDig;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getClavePoliza() {
        return clavePoliza;
    }

    public void setClavePoliza(String clavePoliza) {
        this.clavePoliza = clavePoliza;
    }

    public String getTipoPoliza() {
        return tipoPoliza;
    }

    public void setTipoPoliza(String tipoPoliza) {
        this.tipoPoliza = tipoPoliza;
    }

    public String getNumeroEmpresa() {
        return numeroEmpresa;
    }

    public void setNumeroEmpresa(String numeroEmpresa) {
        this.numeroEmpresa = numeroEmpresa;
    }

    public String getEstatusProcesado() {
        return estatusProcesado;
    }

    public void setEstatusProcesado(String estatusProcesado) {
        this.estatusProcesado = estatusProcesado;
    }
    
}
