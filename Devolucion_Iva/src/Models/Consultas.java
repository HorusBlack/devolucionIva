/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author Macktronica
 */
import Conexion.ConexionDB;
import Controllers.PolizaDatos;
import Controllers.AuxIvaAcred;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Consultas {

    private ConexionDB connection;
    private Statement stmt;
    private ResultSet resultSet;
    private String query;
    private PolizaDatos polizaDatos;
    private AuxIvaAcred auxIvaAcred;

    /**
     * Función que consulta las polizas de un ejercicio y periodo espefico en la
     * base de datos
     *
     * @param periodo int
     * @param ejercicio int
     * @return List PolizaDatos
     */
    public List<PolizaDatos> polizasPeriodoEjercicio(int periodo, int ejercicio) {
        connection = new ConexionDB();
        //String[] parts = string.split("T");
        String subFijoTabla = String.valueOf(ejercicio);
        char v1 = subFijoTabla.charAt(2);
        char v2 = subFijoTabla.charAt(3);
        String tablaPoliza = "POLIZAS" + v1 + v2;
        ArrayList<PolizaDatos> polizaDatosList = new ArrayList<>();
        Connection conexion = null;
        try {
            conexion = connection.Entrar();
            query = "SELECT TOP(10) TIPO_POLI,NUM_POLIZ,PERIODO,EJERCICIO,LOGAUDITA,Convert(date,FECHA_POL) FECHA,ORIGEN,NUMPARCUA,"
                    + "CASE TIENEDOCUMENTOS WHEN  1  THEN 'S' WHEN 0 THEN 'N' END AS TIENEDOCUMENTOS, ESPOLIZAPRIVADA ,CONCEP_PO,CONTABILIZ "
                    + "FROM " + tablaPoliza + " P LEFT JOIN TIPOSPOL TP ON(P.TIPO_POLI=TP.TIPO) "
                    + "WHERE (TIPO_POLI = 'Eg' )  AND (PERIODO = " + periodo + " AND EJERCICIO = " + ejercicio + " )  "
                    + "ORDER BY TIPO_POLI,NUM_POLIZ,PERIODO,EJERCICIO";
            //erro sql aqui
            stmt = conexion.createStatement();
            resultSet = stmt.executeQuery(query);

            while (resultSet.next()) {
                polizaDatos = new PolizaDatos();
                polizaDatos.setNumeroPoliza(resultSet.getInt("NUM_POLIZ"));
                polizaDatos.setEjercicio(resultSet.getInt("EJERCICIO"));
                polizaDatos.setFechaPoliza(resultSet.getDate("FECHA"));
                polizaDatos.setConceptoPoliza(resultSet.getString("CONCEP_PO"));
                polizaDatos.setDocumentos(resultSet.getString("TIENEDOCUMENTOS"));
                polizaDatosList.add(polizaDatos);
            }

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionDB.Salir(conexion);
        }
        return polizaDatosList;
    }

    /**
     * Funcion que obtiene todos los datos de iva acreditable segun un periodo,
     * ejercicio y no. de cuenta
     *
     * @param periodo int
     * @param ejercicio int
     * @param noCuenta String
     * @return List AuxIvaAcred
     */
    public List<AuxIvaAcred> auxIvaAcredConsulta(int periodo, int ejercicio, String noCuenta) {
        connection = new ConexionDB();
        Connection conexion = null;
        String subFijoTabla = String.valueOf(ejercicio);
        char v1 = subFijoTabla.charAt(2);
        char v2 = subFijoTabla.charAt(3);
        String tableAux = "AUXILIAR" + v1 + v2;
        String tableCuentas = "CUENTAS" + v1 + v2;
        String tableSaldos = "SALDOS" + v1 + v2;
        System.out.println("periodo db: "+periodo);
        System.out.println("ejercicio db: "+ejercicio);
        System.out.println("noCuenta db: "+noCuenta);
        String query = "";
        List<AuxIvaAcred> datosAuxiliarIva = new ArrayList<>();
        try {
            conexion = connection.Entrar();
//            if (ejercicio > 2018) {
//                //CAMBIAR TOP
//                query = "SELECT TOP(20)  A.NUM_CTA, NOMBRE, TIPO,B.EJERCICIO,"
//                        + "INICIAL + ((CARGO01+CARGO02+CARGO03) - (ABONO01+ABONO02+ABONO03))*(1 - 2*NATURALEZA) AS SALINI,"
//                        + " CARGO04 AS CARGO, ABONO04 AS ABONO,"
//                        + "INICIAL + ((CARGO01+CARGO02+CARGO03+CARGO04) - (ABONO01+ABONO02+ABONO03+ABONO04))*(1 - 2*NATURALEZA) "
//                        + "AS SALDO , NATURALEZA,BANDMULTI, NIVEL, CONVERT(date,FECHA_POL)FECHA, TIPO_POLI, NUM_POLIZ, CONCEP_PO, DEBE_HABER,"
//                        + " MONTOMOV AS MONTO, ORDEN  "
//                        + "FROM (" + tableCuentas + " A JOIN " + tableSaldos + " B ON A.NUM_CTA = B.NUM_CTA   )  "
//                        + "LEFT JOIN " + tableAux + " C ON (A.NUM_CTA=C.NUM_CTA AND PERIODO IN (" + periodo + "))  "
//                        + "WHERE A.NUM_CTA >=" + noCuenta + "   AND  A.NUM_CTA <=" + noCuenta + " ORDER BY NUM_CTA";
//            } else {
//                query = "SELECT TOP(20) A.NUM_CTA, NOMBRE, TIPO,B.EJERCICIO,INICIAL AS SALINI, CARGO01 AS CARGO, ABONO01 AS ABONO,"
//                        + "INICIAL + ((CARGO01) - (ABONO01))*(1 - 2*NATURALEZA) AS SALDO , NATURALEZA,BANDMULTI, NIVEL, CONVERT(date,FECHA_POL)FECHA,"
//                        + " TIPO_POLI, NUM_POLIZ, CONCEP_PO, DEBE_HABER, MONTOMOV AS MONTO, ORDEN  "
//                        + "FROM (" + tableCuentas + " A JOIN " + tableSaldos + " B ON A.NUM_CTA = B.NUM_CTA   )  "
//                        + "LEFT JOIN " + tableAux + " C ON (A.NUM_CTA=C.NUM_CTA AND PERIODO IN (" + periodo + "))  "
//                        + "WHERE A.NUM_CTA >= " + noCuenta + "  AND  A.NUM_CTA <=" + noCuenta + " ORDER BY NUM_CTA";
//            }
            query="SELECT A.NUM_CTA, NOMBRE, TIPO,B.EJERCICIO,INICIAL AS SALINI, CARGO01 AS CARGO, ABONO01 AS ABONO,INICIAL + ((CARGO01) - (ABONO01))*(1 - 2*NATURALEZA) AS SALDO , NATURALEZA,BANDMULTI, NIVEL, CONVERT(date,FECHA_POL)FECHA, TIPO_POLI, NUM_POLIZ, CONCEP_PO, DEBE_HABER, MONTOMOV AS MONTO, ORDEN  FROM (CUENTAS18 A JOIN SALDOS18 B ON A.NUM_CTA = B.NUM_CTA   )  LEFT JOIN AUXILIAR18 C ON (A.NUM_CTA=C.NUM_CTA AND PERIODO IN (1))  WHERE A.NUM_CTA >= '115100100000000000002'   AND  A.NUM_CTA <= '115100100000000000002' ORDER BY 1,4,11,12,13,14,18";
            stmt = conexion.createStatement();
            resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                //tipo poliza, numero poliza, fecha, concepto, debe, haber
                auxIvaAcred = new AuxIvaAcred();
                auxIvaAcred.setTipoPoliza(resultSet.getString("TIPO_POLI"));
                auxIvaAcred.setNoPoliza(resultSet.getInt("NUM_POLIZ"));
                auxIvaAcred.setFecha(resultSet.getString("FECHA"));
                auxIvaAcred.setConcepto(resultSet.getString("CONCEP_PO"));
                auxIvaAcred.setDebe(resultSet.getDouble("MONTO"));
                auxIvaAcred.setHaber(0);
                datosAuxiliarIva.add(auxIvaAcred);
                //Como se genera el valor haber, o que columna es
                //existen polizas Dr, en Eg. ¿Es normal?
                //(219902.42−208522−11380.7)+0.28
            }
            return datosAuxiliarIva;
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionDB.Salir(conexion);
        }
        return datosAuxiliarIva;
    }
}
