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

    public List<PolizaDatos> polizasPeriodoEjercicio(int periodo, int ejercicio) {
        connection = new ConexionDB();
        //String[] parts = string.split("T");
        String subFijoTabla = String.valueOf(ejercicio);
        char v1 = subFijoTabla.charAt(2);
        char v2 = subFijoTabla.charAt(3);
        String tablaPoliza = "POLIZAS" + v1 + v2;
        System.out.println("tabla de poliza: " + tablaPoliza);
        ArrayList<PolizaDatos> polizaDatosList = new ArrayList<>();
        try {
            Connection conexion = connection.Entrar();
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
        }
        return polizaDatosList;
    }

}
