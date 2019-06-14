/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.AuxIvaAcred;
import Models.Consultas;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author horusblack
 */
public class AuxIvaAcredController {

    private List<AuxIvaAcred> auxIvaAcred;
    private Consultas consultas;
    //Antigua
    private final static String NO_CUENTA = "115100100000000000002";
    //Cuenta de AgroEcologia
    private final static String NO_CUENTA_BANORTE7444="111500700100000000003";
    
    
    private final String baseAgroecologia = "COI80Empre2";
    private final String baseAstixa = "COI80Empre1";

    public List<AuxIvaAcred> solicitudAuxIvaAcred(int periodo, int ejercicio, int numEmpresa) {
        //pendiente la validacion de la empresa
        String dataBase = "";
        if (numEmpresa == 0) {
            dataBase = baseAstixa;
        } else if (numEmpresa == 1) {
            dataBase = baseAgroecologia;
        }
        consultas = new Consultas();
        auxIvaAcred = new ArrayList<>();
        periodo += 1;

        if (periodo > 0 && ejercicio >= 2017) {
            auxIvaAcred = consultas.auxIvaAcredConsulta(periodo, ejercicio, NO_CUENTA, dataBase);
        }
        return auxIvaAcred;
    }
}
