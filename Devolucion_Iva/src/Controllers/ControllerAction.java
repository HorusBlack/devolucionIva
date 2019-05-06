/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.Consultas;
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
    private final static String NO_CUENTA = "115100100000000000002";

    /**
     * Funcion que solicita datos referente a las retenciones de iva del mes y
     * retorna una lista de datos
     *
     * @param periodo
     * @param ejercicio
     * @return List RetencionIvaMes
     */
    public List<RetencionIvaMes> solicitudRetencionesIvaMes(int periodo, int ejercicio) {

        consultas = new Consultas();
        listRetencionIvaMes = new ArrayList<>();
        periodo += 1;

        if (periodo > 0 && ejercicio >= 2017) {
            listRetencionIvaMes = consultas.retencionIvaMesConsulta(periodo, ejercicio, NO_CUENTA);
        }
        return listRetencionIvaMes;
    }

    /**
     * Funcion que solicita datos referente a las retenciones de iva del mes pagadas y
     * retorna una lista de datos
     *
     * @param periodo
     * @param ejercicio
     * @return List RetencionIvaMes
     */
    public List<RetencionIvaPagadaMes> solicitudRetencionesIvaMesPagada(int periodo, int ejercicio) {

        consultas = new Consultas();
        ListRetencionIvaPagadaMeses = new ArrayList<>();
        periodo += 1;

        if (periodo > 0 && ejercicio >= 2017) {
            ListRetencionIvaPagadaMeses = consultas.retencionesIvaMesPagada(periodo, ejercicio, NO_CUENTA);
        }
        return ListRetencionIvaPagadaMeses;
    }
}
