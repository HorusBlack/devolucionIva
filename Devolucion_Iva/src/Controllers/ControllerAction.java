/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.Consultas;
import Models.RelacionActividades;
import Models.RetencionIvaMes;
import Models.RetencionIvaPagadaMes;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author horusblack
 */
public class ControllerAction {

    //PENDIENTE PASAR TODO AQUI
    private Consultas consultas;
    private List<RetencionIvaMes> listRetencionIvaMes;
    private List<RetencionIvaPagadaMes> ListRetencionIvaPagadaMeses;
    private RelacionActividades relacionActividades;
    private final static String NO_CUENTA = "115100100000000000002";
    private final String baseAgroecologia = "COI80Empre2";
    private final String baseAstixa = "COI80Empre1";
    private String dataBase;

    /**
     * Funcion que solicita datos referente a las retenciones de iva del mes y
     * retorna una lista de datos
     *
     * @param periodo
     * @param ejercicio
     * @param numEmpresa
     * @return List RetencionIvaMes
     */
    public List<RetencionIvaMes> solicitudRetencionesIvaMes(int periodo, int ejercicio, int numEmpresa) {
        if (numEmpresa == 0) {
            dataBase = baseAstixa;
        } else if (numEmpresa == 1) {
            dataBase = baseAgroecologia;
        }
        consultas = new Consultas();
        listRetencionIvaMes = new ArrayList<>();
        periodo += 1;

        if (periodo > 0 && ejercicio >= 2017) {
            listRetencionIvaMes = consultas.retencionIvaMesConsulta(periodo, ejercicio, NO_CUENTA, dataBase);
        }
        return listRetencionIvaMes;
    }

    /**
     * Funcion que solicita datos referente a las retenciones de iva del mes
     * pagadas y retorna una lista de datos
     *
     * @param periodo
     * @param ejercicio
     * @param numEmpresa
     * @return List RetencionIvaMes
     */
    public List<RetencionIvaPagadaMes> solicitudRetencionesIvaMesPagada(int periodo, int ejercicio, int numEmpresa) {
        if (numEmpresa == 0) {
            dataBase = baseAstixa;
        } else if (numEmpresa == 1) {
            dataBase = baseAgroecologia;
        }
        consultas = new Consultas();
        ListRetencionIvaPagadaMeses = new ArrayList<>();
        periodo += 1;

        if (periodo > 0 && ejercicio >= 2017) {
            ListRetencionIvaPagadaMeses = consultas.retencionesIvaMesPagada(periodo, ejercicio, NO_CUENTA, dataBase);
        }
        return ListRetencionIvaPagadaMeses;
    }

    /**
     * Funcion que envia datos para un nuevo tipo de relacion de actividad y
     * retorna un booleano segun el resultado
     *
     * @param nombreRelacion
     * @param codigo
     * @return boolean
     */
    public boolean solicitarInsertNuevaRelacion(String nombreRelacion, String codigo) {
        consultas = new Consultas();
        boolean resultado = consultas.insertarNuevaRelacion(nombreRelacion, codigo);
        return resultado;
    }

    /**
     * Funcion que solicita una lista de RelacionActividades a la BD y retorna
     * los datos
     *
     * @return List RelacionActividades
     */
    public List<RelacionActividades> procesarListaRelaciones() {
        consultas = new Consultas();
        List<RelacionActividades> resultList = consultas.relaciones();
        return resultList;
    }

    /**
     * Funcion que solicita una lista de RelacionActividades a la BD y retorna
     * los datos
     *
     * @return List RelacionActividades
     */
    public List<RelacionActividades> procesarListaRelacionesActividad() {
        consultas = new Consultas();
        List<RelacionActividades> resultList = consultas.relacionesRFC();
        return resultList;
    }
    
    /**
     * Funcion que obtiene manda y retorna el resultado de una actualizacion entre RFC y Actividad
     * @param clave
     * @param idRfcAsociado
     * @return boolean
     */
    public boolean actualizarRfcAsociado(String clave, int idRfcAsociado) {
     
        boolean exito;
        consultas = new Consultas();
        exito = consultas.actualizarRelacionActividad(clave, idRfcAsociado);
        return exito;
    }

    /**
     * Funcion que obtiene y manda los datos requeridos para una nueva relacion
     * entre un RFC y una actividad Retorna un booleano segun el resultado
     *
     * @param clave
     * @param nuevoRfc
     * @return boolean
     */
    public boolean nuevoRfcAsociado(String clave, String nuevoRfc) {
        //nombre relacion txtRfcNuevo,
        boolean exito;
        consultas = new Consultas();
        exito = consultas.insertNuevoRfcActividad(clave, nuevoRfc);
        return exito;
    }
    

}
