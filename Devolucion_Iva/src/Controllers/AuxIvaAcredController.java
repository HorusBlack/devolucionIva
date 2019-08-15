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

    //
  
    private final String CUENTA_AUXILIAR="115100100000000000002";

    private final String baseAgroecologia = "COI80Empre2";
    private final String baseAstixa = "COI80Empre1";
    
    public List<AuxIvaAcred> new_solicitudAuxIvaAcred(int periodo, int ejercicio, int numEmpresa) {
        consultas = new Consultas();
        auxIvaAcred = new ArrayList<>();
        periodo += 1;
        if (numEmpresa == 0) {
             if (periodo > 0 && ejercicio >= 2016) {
                auxIvaAcred = consultas.new_auxIvaAcredAsctisa(periodo, ejercicio, CUENTA_AUXILIAR, baseAstixa);
            }
        } else if (numEmpresa == 1) {
            if (periodo > 0 && ejercicio >= 2016) {
                auxIvaAcred = consultas.new_auxIvaAcredConsulta(periodo, ejercicio, CUENTA_AUXILIAR, baseAgroecologia);
            }
        }

        return auxIvaAcred;
    }
    
}
