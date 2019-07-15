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
    private final String BANCOMER_ADS_2678 = "111500200100000000003";
    private final String SANTANDER_ADS_2399 = "111500300100000000003";
    private final String SANTANDER2_ADS_6082 = "111500300200000000003";
    private final String SANTANDER3_ADS_5170 = "203500300100000000003";
    private final String BANORTE_ADS_0212 = "111500700100000000003";
    private final String BANORTE2_ADS_7202 = "111500700200000000003";
    private final String CUENTA_BANCOMER_AGRO = "111500700100000000003";

    private final String baseAgroecologia = "COI80Empre2";
    private final String baseAstixa = "COI80Empre1";

    public List<AuxIvaAcred> solicitudAuxIvaAcred(int periodo, int ejercicio, int numEmpresa) {
        //pendiente la validacion de la empresa
        String dataBase = "";
        consultas = new Consultas();
        auxIvaAcred = new ArrayList<>();
        String[] cuentasBanco = {BANCOMER_ADS_2678, SANTANDER2_ADS_6082, SANTANDER3_ADS_5170, SANTANDER_ADS_2399, BANORTE2_ADS_7202, BANORTE_ADS_0212};
        periodo += 1;
        if (numEmpresa == 0) {
            dataBase = baseAstixa;
             if (periodo > 0 && ejercicio >= 2017) {
                auxIvaAcred = consultas.auxIvaAcredAsctisa(periodo, ejercicio, cuentasBanco, dataBase);
            }
        } else if (numEmpresa == 1) {
            dataBase = baseAgroecologia;
            if (periodo > 0 && ejercicio >= 2017) {
                auxIvaAcred = consultas.auxIvaAcredConsulta(periodo, ejercicio, CUENTA_BANCOMER_AGRO, dataBase);
            }
        }

        return auxIvaAcred;
    }
}
