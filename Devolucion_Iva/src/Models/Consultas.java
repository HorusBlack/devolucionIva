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
    private RetencionIvaMes retencionIvaMes;
    private RetencionIvaPagadaMes retencionIvaPagadaMes;

    /**
     * Función que consulta las polizas de un ejercicio y periodo espefico en la
     * base de datos
     *
     * @param periodo int
     * @param ejercicio int
     * @param numeroCuenta
     * @param numeroEmpresa
     * @param dataBase
     * @return List PolizaDatos
     */
    public List<PolizaDatos> polizasPeriodoEjercicio(int periodo, int ejercicio, String numeroCuenta, int numeroEmpresa,
            String dataBase) {

        System.out.println("Valores recibidos consulta");
        System.out.println("Periodo: " + periodo + " Ejercicio: " + ejercicio + " Cuenta: " + numeroCuenta + " empresa: " + numeroEmpresa + " DB: " + dataBase);

        connection = new ConexionDB();
        //String[] parts = string.split("T");
        String subFijoTabla = String.valueOf(ejercicio);
        char v1 = subFijoTabla.charAt(2);
        char v2 = subFijoTabla.charAt(3);
        String subFijoPeriodo = "";
        String subFijoCuenta = "CUENTAS" + v1 + v2;
        String subFijoAuxiliar = "AUXILIAR" + v1 + v2;
        String subFijoSaldos = "SALDOS" + v1 + v2;
        String subBaseCoi = "COI80Empre" + numeroEmpresa;
        if (periodo < 10) {
            subFijoPeriodo = "0" + periodo;
            System.out.println("periodo string: " + subFijoPeriodo);
        } else {
            subFijoPeriodo = String.valueOf(periodo);
            System.out.println("periodo string: " + subFijoPeriodo);
        }
        String periodoAnio = subFijoPeriodo + String.valueOf(v1) + String.valueOf(v2);
        System.out.println(subFijoCuenta);
        System.out.println(subFijoAuxiliar);
        System.out.println(subFijoSaldos);
        System.out.println(subBaseCoi);

        ArrayList<PolizaDatos> polizaDatosList = new ArrayList<>();
        Connection conexion = null;
        //" + tableSaldos + "
        try {
            conexion = connection.Entrar(dataBase);
            query = "SELECT d.ID_DOCTODIG, d.RUTA, d.ARCHIVO, reg.CVEENTIDAD1 as 'CLAVE_POLISA', reg.CVEENTIDAD2 'TIPO',reg.EMPRESA FROM [DOCUMENTOS_COI].[dbo].[DOCTOSDIG] "
                    + "d INNER JOIN [DOCUMENTOS_COI].[dbo].[RELACION] rel ON d.ID_DOCTODIG = rel.ID_DOCTODIG INNER JOIN [DOCUMENTOS_COI].[dbo].[REGISTROS] reg ON "
                    + "rel.ID_REGISTRO = reg.ID_REGISTRO WHERE reg.TIPOENTIDAD = 16 AND reg.CVEENTIDAD3 = '" + periodoAnio + "' AND "
                    + "reg.EMPRESA = " + numeroEmpresa + " AND (d.ARCHIVO LIKE '%xml' OR d.ARCHIVO LIKE'%XML') AND reg.CVEENTIDAD1 IN "
                    + "(SELECT (CASE WHEN NUM_POLIZ IS NULL THEN 'N/D' ELSE NUM_POLIZ END) NUM_POLIZ FROM ([" + subBaseCoi + "].[dbo].[" + subFijoCuenta + "] A "
                    + "JOIN [" + subBaseCoi + "].[dbo].[" + subFijoSaldos + "] B ON A.NUM_CTA = B.NUM_CTA)  LEFT JOIN [" + subBaseCoi + "].[dbo].[" + subFijoAuxiliar + "] C "
                    + "ON (A.NUM_CTA=C.NUM_CTA AND PERIODO IN (" + periodo + "))  WHERE A.NUM_CTA >= '" + numeroCuenta + "'   AND  A.NUM_CTA <= '" + numeroCuenta + "' ) "
                    + "order by  reg.CVEENTIDAD1";
            //erro sql aqui
            stmt = conexion.createStatement();
            resultSet = stmt.executeQuery(query);
            System.out.println("Registros SQL");
            while (resultSet.next()) {
                System.out.println("Registros SQL DENTRO");
                polizaDatos = new PolizaDatos();
                polizaDatos.setIdDoctodig(resultSet.getString("ID_DOCTODIG"));
                polizaDatos.setRutaXml(resultSet.getString("RUTA"));
                polizaDatos.setNombreXml(resultSet.getString("ARCHIVO"));
                polizaDatos.setTipoPoliza(resultSet.getString("TIPO"));
                polizaDatos.setNumeroPoliza(resultSet.getString("CLAVE_POLISA"));
                polizaDatos.setEmpresa(resultSet.getString("EMPRESA"));
                polizaDatosList.add(polizaDatos);
                for (int i = 0; i < polizaDatosList.size(); i++) {
                    System.out.println("POLIZA DAT: " + polizaDatosList.get(i).getIdDoctodig());
                    System.out.println("POLIZA DAT: " + polizaDatosList.get(i).getEmpresa());
                    System.out.println("POLIZA DAT: " + polizaDatosList.get(i).getNombreXml());
                    System.out.println("POLIZA DAT: " + polizaDatosList.get(i).getNumeroPoliza());
                    System.out.println("POLIZA DAT: " + polizaDatosList.get(i).getRutaXml());
                    System.out.println("POLIZA DAT: " + polizaDatosList.get(i).getTipoPoliza());
                }
            }
            System.out.println("Registros SQL FIN");
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
    public List<AuxIvaAcred> auxIvaAcredConsulta(int periodo, int ejercicio, String noCuenta, String dataBase) {
        connection = new ConexionDB();
        Connection conexion = null;
        String subFijoTabla = String.valueOf(ejercicio);
        char v1 = subFijoTabla.charAt(2);
        char v2 = subFijoTabla.charAt(3);
        String tableAux = "AUXILIAR" + v1 + v2;
        String tableCuentas = "CUENTAS" + v1 + v2;
        String tableSaldos = "SALDOS" + v1 + v2;
        String query;

        List<AuxIvaAcred> datosAuxiliarIva = new ArrayList<>();
        try {
            conexion = connection.Entrar(dataBase);
            if (ejercicio > 2018) {
                //CAMBIAR TOP
                query = "SELECT  A.NUM_CTA, NOMBRE, TIPO,B.EJERCICIO,"
                        + "INICIAL + ((CARGO01+CARGO02+CARGO03) - (ABONO01+ABONO02+ABONO03))*(1 - 2*NATURALEZA) AS SALINI,"
                        + " CARGO04 AS CARGO, ABONO04 AS ABONO,"
                        + "INICIAL + ((CARGO01+CARGO02+CARGO03+CARGO04) - (ABONO01+ABONO02+ABONO03+ABONO04))*(1 - 2*NATURALEZA) "
                        + "AS SALDO , NATURALEZA,BANDMULTI, NIVEL, CONVERT(date,FECHA_POL) FECHA_POL, TIPO_POLI, NUM_POLIZ, CONCEP_PO, DEBE_HABER,"
                        + " MONTOMOV AS MONTO, ORDEN  "
                        + "FROM (" + tableCuentas + " A JOIN " + tableSaldos + " B ON A.NUM_CTA = B.NUM_CTA   )  "
                        + "LEFT JOIN " + tableAux + " C ON (A.NUM_CTA=C.NUM_CTA AND PERIODO IN (" + periodo + "))  "
                        + "WHERE A.NUM_CTA >=" + noCuenta + "   AND  A.NUM_CTA <=" + noCuenta + " ORDER BY NUM_CTA";
            } else {
                query = "SELECT A.NUM_CTA, NOMBRE, TIPO,B.EJERCICIO,INICIAL AS SALINI, CARGO01 AS CARGO, ABONO01 AS ABONO,"
                        + "INICIAL + ((CARGO01) - (ABONO01))*(1 - 2*NATURALEZA) AS SALDO , NATURALEZA,BANDMULTI, NIVEL,"
                        + "CONVERT(date,(CASE WHEN FECHA_POL IS NULL THEN '' ELSE FECHA_POL END)) FECHA_POL,"
                        + " (CASE WHEN TIPO_POLI IS NULL THEN 'N/D' ELSE TIPO_POLI END) TIPO_POLI, (CASE WHEN NUM_POLIZ IS NULL THEN '0' ELSE NUM_POLIZ END) NUM_POLIZ,"
                        + "(CASE WHEN CONCEP_PO IS NULL THEN 'N/D' ELSE CONCEP_PO END) CONCEP_PO,(CASE WHEN DEBE_HABER IS NULL THEN 'N/D' ELSE DEBE_HABER END) DEBE_HABER,"
                        + "(CASE WHEN MONTOMOV IS NULL THEN '0' ELSE MONTOMOV END) MONTO,(CASE WHEN ORDEN IS NULL THEN '0' ELSE ORDEN END) ORDEN "
                        + "FROM (" + tableCuentas + " A JOIN " + tableSaldos + " B ON A.NUM_CTA = B.NUM_CTA)  LEFT JOIN " + tableAux + " C ON (A.NUM_CTA=C.NUM_CTA AND PERIODO IN (" + periodo + "))  "
                        + "WHERE A.NUM_CTA >= " + noCuenta + "   AND  A.NUM_CTA <= " + noCuenta + " ORDER BY NUM_CTA";
            }

            stmt = conexion.createStatement();
            resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                //tipo poliza, numero poliza, fecha, concepto, debe, haber
                auxIvaAcred = new AuxIvaAcred();
                auxIvaAcred.setTipoPoliza(resultSet.getString("TIPO_POLI"));
                auxIvaAcred.setNoPoliza(resultSet.getString("NUM_POLIZ"));
                auxIvaAcred.setFecha(resultSet.getString("FECHA_POL"));
                auxIvaAcred.setConcepto(resultSet.getString("CONCEP_PO"));
                auxIvaAcred.setDebe(resultSet.getDouble("MONTO"));
                auxIvaAcred.setHaber(0);
                datosAuxiliarIva.add(auxIvaAcred);
                //Como se genera el valor haber, o que columna es
                //existen polizas Dr, en Eg. ¿Es normal?
                //(219902.42−208522−11380.7)+0.28
            }

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionDB.Salir(conexion);
        }
        return datosAuxiliarIva;
    }

    /*
    PENDIENTE VER SI SE PUEDE HACER TODO EN UNA SOLA FUNCION
     */
    public List<RetencionIvaMes> retencionIvaMesConsulta(int periodo, int ejercicio, String noCuenta, String dataBase) {
        connection = new ConexionDB();
        Connection conexion = null;
        String subFijoTabla = String.valueOf(ejercicio);
        char v1 = subFijoTabla.charAt(2);
        char v2 = subFijoTabla.charAt(3);
        String tableAux = "AUXILIAR" + v1 + v2;
        String tableCuentas = "CUENTAS" + v1 + v2;
        String tableSaldos = "SALDOS" + v1 + v2;
        String query;

        List<RetencionIvaMes> listRetencion = new ArrayList<>();
        try {
            conexion = connection.Entrar(dataBase);
            if (ejercicio > 2018) {
                //CAMBIAR TOP
                query = "SELECT  A.NUM_CTA, NOMBRE, TIPO,B.EJERCICIO,"
                        + "INICIAL + ((CARGO01+CARGO02+CARGO03) - (ABONO01+ABONO02+ABONO03))*(1 - 2*NATURALEZA) AS SALINI,"
                        + " CARGO04 AS CARGO, ABONO04 AS ABONO,"
                        + "INICIAL + ((CARGO01+CARGO02+CARGO03+CARGO04) - (ABONO01+ABONO02+ABONO03+ABONO04))*(1 - 2*NATURALEZA) "
                        + "AS SALDO , NATURALEZA,BANDMULTI, NIVEL, CONVERT(date,FECHA_POL)FECHA_POL, TIPO_POLI, NUM_POLIZ, CONCEP_PO, DEBE_HABER,"
                        + " MONTOMOV AS MONTO, ORDEN  "
                        + "FROM (" + tableCuentas + " A JOIN " + tableSaldos + " B ON A.NUM_CTA = B.NUM_CTA   )  "
                        + "LEFT JOIN " + tableAux + " C ON (A.NUM_CTA=C.NUM_CTA AND PERIODO IN (" + periodo + "))  "
                        + "WHERE A.NUM_CTA >=" + noCuenta + "   AND  A.NUM_CTA <=" + noCuenta + " ORDER BY NUM_CTA";
            } else {
                query = "SELECT A.NUM_CTA, NOMBRE, TIPO,B.EJERCICIO,INICIAL AS SALINI, CARGO01 AS CARGO, ABONO01 AS ABONO,"
                        + "INICIAL + ((CARGO01) - (ABONO01))*(1 - 2*NATURALEZA) AS SALDO , NATURALEZA,BANDMULTI, NIVEL,"
                        + "CONVERT(date,(CASE WHEN FECHA_POL IS NULL THEN '' ELSE FECHA_POL END)) FECHA_POL,"
                        + " (CASE WHEN TIPO_POLI IS NULL THEN 'N/D' ELSE TIPO_POLI END) TIPO_POLI, (CASE WHEN NUM_POLIZ IS NULL THEN 'N/D' ELSE NUM_POLIZ END) NUM_POLIZ,"
                        + "(CASE WHEN CONCEP_PO IS NULL THEN 'N/D' ELSE CONCEP_PO END) CONCEP_PO,(CASE WHEN DEBE_HABER IS NULL THEN 'N/D' ELSE DEBE_HABER END) DEBE_HABER,"
                        + "(CASE WHEN MONTOMOV IS NULL THEN '0' ELSE MONTOMOV END) MONTO,(CASE WHEN ORDEN IS NULL THEN '0' ELSE ORDEN END) ORDEN "
                        + "FROM (" + tableCuentas + " A JOIN " + tableSaldos + " B ON A.NUM_CTA = B.NUM_CTA)  LEFT JOIN " + tableAux + " C ON (A.NUM_CTA=C.NUM_CTA AND PERIODO IN (" + periodo + "))  "
                        + "WHERE A.NUM_CTA >= " + noCuenta + "   AND  A.NUM_CTA <= " + noCuenta + " ORDER BY NUM_CTA";
            }

            stmt = conexion.createStatement();
            resultSet = stmt.executeQuery(query);
            try {
                while (resultSet.next()) {
                    //tipo poliza, numero poliza, fecha, concepto, debe, haber
                    retencionIvaMes = new RetencionIvaMes();
                    retencionIvaMes.setTipoPoliza(resultSet.getString("TIPO_POLI"));
                    //Algunos no tienen segunda parte
                    if (resultSet.getString("TIPO_POLI").length() > 2) {
                        retencionIvaMes.setPolCombinada(resultSet.getString("TIPO_POLI") + "-" + resultSet.getString("NUM_POLIZ").trim());
                    } else {
                        retencionIvaMes.setPolCombinada(resultSet.getString("TIPO_POLI"));
                    }
                    retencionIvaMes.setFecha(resultSet.getString("FECHA_POL"));
                    retencionIvaMes.setConcepto(resultSet.getString("CONCEP_PO"));
                    retencionIvaMes.setConceptosBase(resultSet.getString("NOMBRE"));
                    listRetencion.add(retencionIvaMes);
                }
            } catch (NullPointerException e) {
                Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, e);
            }

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            ConexionDB.Salir(conexion);
        }
        return listRetencion;
    }

    public List<RetencionIvaPagadaMes> retencionesIvaMesPagada(int periodo, int ejercicio, String noCuenta, String dataBase) {
        connection = new ConexionDB();
        Connection conexion = null;
        String subFijoTabla = String.valueOf(ejercicio);
        char v1 = subFijoTabla.charAt(2);
        char v2 = subFijoTabla.charAt(3);
        String tableAux = "AUXILIAR" + v1 + v2;
        String tableCuentas = "CUENTAS" + v1 + v2;
        String tableSaldos = "SALDOS" + v1 + v2;
        String query;

        List<RetencionIvaPagadaMes> listRetencionPagada = new ArrayList<>();
        try {
            conexion = connection.Entrar(dataBase);
            if (ejercicio > 2018) {
                //CAMBIAR TOP
                query = "SELECT  A.NUM_CTA, NOMBRE, TIPO,B.EJERCICIO,"
                        + "INICIAL + ((CARGO01+CARGO02+CARGO03) - (ABONO01+ABONO02+ABONO03))*(1 - 2*NATURALEZA) AS SALINI,"
                        + " CARGO04 AS CARGO, ABONO04 AS ABONO,"
                        + "INICIAL + ((CARGO01+CARGO02+CARGO03+CARGO04) - (ABONO01+ABONO02+ABONO03+ABONO04))*(1 - 2*NATURALEZA) "
                        + "AS SALDO , NATURALEZA,BANDMULTI, NIVEL, CONVERT(date,FECHA_POL)FECHA_POL, TIPO_POLI, NUM_POLIZ, CONCEP_PO, DEBE_HABER,"
                        + " MONTOMOV AS MONTO, ORDEN  "
                        + "FROM (" + tableCuentas + " A JOIN " + tableSaldos + " B ON A.NUM_CTA = B.NUM_CTA   )  "
                        + "LEFT JOIN " + tableAux + " C ON (A.NUM_CTA=C.NUM_CTA AND PERIODO IN (" + periodo + "))  "
                        + "WHERE A.NUM_CTA >=" + noCuenta + "   AND  A.NUM_CTA <=" + noCuenta + " ORDER BY NUM_CTA";
            } else {
                query = "SELECT A.NUM_CTA, NOMBRE, TIPO,B.EJERCICIO,INICIAL AS SALINI, CARGO01 AS CARGO, ABONO01 AS ABONO,"
                        + "INICIAL + ((CARGO01) - (ABONO01))*(1 - 2*NATURALEZA) AS SALDO , NATURALEZA,BANDMULTI, NIVEL,"
                        + "CONVERT(date,(CASE WHEN FECHA_POL IS NULL THEN '' ELSE FECHA_POL END)) FECHA_POL,"
                        + " (CASE WHEN TIPO_POLI IS NULL THEN 'N/D' ELSE TIPO_POLI END) TIPO_POLI, (CASE WHEN NUM_POLIZ IS NULL THEN 'N/D' ELSE NUM_POLIZ END) NUM_POLIZ,"
                        + "(CASE WHEN CONCEP_PO IS NULL THEN 'N/D' ELSE CONCEP_PO END) CONCEP_PO,(CASE WHEN DEBE_HABER IS NULL THEN 'N/D' ELSE DEBE_HABER END) DEBE_HABER,"
                        + "(CASE WHEN MONTOMOV IS NULL THEN '0' ELSE MONTOMOV END) MONTO,(CASE WHEN ORDEN IS NULL THEN '0' ELSE ORDEN END) ORDEN "
                        + "FROM (" + tableCuentas + " A JOIN " + tableSaldos + " B ON A.NUM_CTA = B.NUM_CTA)  LEFT JOIN " + tableAux + " C ON (A.NUM_CTA=C.NUM_CTA AND PERIODO IN (" + periodo + "))  "
                        + "WHERE A.NUM_CTA >= " + noCuenta + "   AND  A.NUM_CTA <= " + noCuenta + " ORDER BY NUM_CTA";
            }

            stmt = conexion.createStatement();
            resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                //tipo poliza, numero poliza, fecha, concepto, debe, haber
                retencionIvaPagadaMes = new RetencionIvaPagadaMes();
                retencionIvaPagadaMes.setTipoPoliza(resultSet.getString("TIPO_POLI"));
                retencionIvaPagadaMes.setPolCombinada(resultSet.getString("TIPO_POLI") + "-" + resultSet.getString("NUM_POLIZ").trim());
                retencionIvaPagadaMes.setFecha(resultSet.getString("FECHA_POL"));
                retencionIvaPagadaMes.setConcepto(resultSet.getString("CONCEP_PO"));
                retencionIvaPagadaMes.setConceptosBase(resultSet.getString("NOMBRE"));
                listRetencionPagada.add(retencionIvaPagadaMes);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionDB.Salir(conexion);
        }
        return listRetencionPagada;
    }

}
