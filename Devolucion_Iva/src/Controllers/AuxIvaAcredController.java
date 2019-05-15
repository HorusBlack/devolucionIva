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
    private final static String NO_CUENTA="115100100000000000002";
    
     public List<AuxIvaAcred> solicitudAuxIvaAcred(int periodo, int ejercicio) {

        consultas = new Consultas();
        auxIvaAcred = new ArrayList<>();
        periodo += 1;
        
        if (periodo > 0 && ejercicio >= 2017) {
            auxIvaAcred = consultas.auxIvaAcredConsulta(periodo, ejercicio, NO_CUENTA);
        }
        return auxIvaAcred;
    }
}
