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
import java.sql.PreparedStatement;
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
    private ResultSet subResultset;
    private String query, subQuery;
    private PolizaDatos polizaDatos;
    private PolizaProcesada polizaProcesada;
    private AuxIvaAcred auxIvaAcred;
    private RetencionIvaMes retencionIvaMes;
    private RetencionIvaPagadaMes retencionIvaPagadaMes;
    private RelacionActividades relacionActividades;

    /**
     * Función que consulta las polizas de un ejercicio y periodo espefico en la
     * base de datos de AgroEcologia
     *
     * @param periodo int
     * @param ejercicio int
     * @param numeroCuenta String
     * @param numeroEmpresa int
     * @param dataBase String
     * @return List PolizaDatos
     */
    public List<PolizaDatos> polizasPeriodoEjercicio_Agroecologia(int periodo, int ejercicio, String numeroCuenta, int numeroEmpresa,
            String dataBase, boolean tipoSolicitud) {
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
        } else {
            subFijoPeriodo = String.valueOf(periodo);
        }
        String clave = "";
        //Lista de polizas
        List<PolizaDatos> lpd = new ArrayList<>();
        if (tipoSolicitud) {
            lpd = this.consultarPolizasUnicasSinProcesar(dataBase, subBaseCoi, subFijoCuenta, subFijoSaldos, subFijoAuxiliar, String.valueOf(periodo), numeroCuenta);
        } else {
            lpd = this.consultarPolizasUnicas(dataBase, subBaseCoi, subFijoCuenta, subFijoSaldos, subFijoAuxiliar, String.valueOf(periodo), numeroCuenta);
        }
        //String numCuent
        String periodoAnio = subFijoPeriodo + String.valueOf(v1) + String.valueOf(v2);
        ArrayList<PolizaDatos> polizaDatosList = new ArrayList<>();
        Connection conexion = null;
        //" + tableSaldos + "
        try {
            conexion = connection.Entrar(dataBase);
            if (!lpd.isEmpty()) {
                for (int x = 0; x < lpd.size(); x++) {
                    query = "SELECT d.ID_DOCTODIG, d.RUTA, d.ARCHIVO, reg.CVEENTIDAD1 as 'CLAVE_POLISA', reg.CVEENTIDAD2 'TIPO',CONVERT(date,aux.FECHA_POL) 'FECHA POLIZA',"
                            + " aux.MONTOMOV, aux.DEBE_HABER, reg.EMPRESA FROM [DOCUMENTOS_COI].[dbo].[DOCTOSDIG] d INNER JOIN [DOCUMENTOS_COI].[dbo].[RELACION] rel ON"
                            + " d.ID_DOCTODIG = rel.ID_DOCTODIG INNER JOIN [DOCUMENTOS_COI].[dbo].[REGISTROS] reg ON rel.ID_REGISTRO = reg.ID_REGISTRO "
                            + " INNER JOIN [" + subBaseCoi + "].[dbo].[" + subFijoAuxiliar + "] aux ON reg.CVEENTIDAD1 = aux.NUM_POLIZ AND reg.CVEENTIDAD2 = aux.TIPO_POLI "
                            + " WHERE reg.TIPOENTIDAD = 16 AND reg.CVEENTIDAD3 = '" + periodoAnio + "' AND reg.EMPRESA= " + numeroEmpresa + " AND "
                            + "aux.PERIODO = " + periodo + " AND aux.EJERCICIO = " + ejercicio + " AND aux.MONTOMOV=" + lpd.get(x).getMontoMov() + " AND (d.ARCHIVO LIKE '%xml' OR d.ARCHIVO LIKE'%XML') "
                            + "AND reg.CVEENTIDAD1=" + lpd.get(x).getNumeroPoliza() + " AND reg.CVEENTIDAD2='" + lpd.get(x).getTipoPoliza() + "'";
                    stmt = conexion.createStatement();
                    resultSet = stmt.executeQuery(query);
                    if (resultSet.next() == false) {
                        subQuery = "SELECT aux.NUM_CTA,aux.FECHA_POL, aux.TIPO_POLI,aux.NUM_POLIZ ,CONVERT(date,aux.FECHA_POL) 'FECHA POLIZA',"
                                + "aux.MONTOMOV ,aux.DEBE_HABER "
                                + "FROM [" + subBaseCoi + "].[dbo].[" + subFijoAuxiliar + "] aux "
                                + "WHERE aux.PERIODO = " + periodo + " AND aux.EJERCICIO = " + ejercicio + " AND aux.MONTOMOV=" + lpd.get(x).getMontoMov() + " AND aux.NUM_POLIZ=" + lpd.get(x).getNumeroPoliza() + " AND "
                                + "aux.TIPO_POLI='" + lpd.get(x).getTipoPoliza() + "'";
                        stmt = conexion.createStatement();
                        subResultset = stmt.executeQuery(subQuery);
                        if (subResultset.next()) {
                            do {
                                polizaDatos = new PolizaDatos();
                                polizaDatos.setTipoPoliza(subResultset.getString("TIPO_POLI"));
                                polizaDatos.setNumeroPoliza(subResultset.getString("NUM_POLIZ"));
                                polizaDatos.setFechaPago(subResultset.getString("FECHA POLIZA"));
                                polizaDatos.setCuenta(consultaNombreCuenta(dataBase, subBaseCoi, subFijoAuxiliar,
                                        subFijoCuenta, numeroCuenta, String.valueOf(periodo), String.valueOf(ejercicio),
                                        subResultset.getString("TIPO_POLI"), subResultset.getString("NUM_POLIZ")));
                                polizaDatos.setConXml(2);
                                polizaDatos.setNumCuentaCoi(numeroCuenta);
                                polizaDatos.setMontoMov(subResultset.getString("MONTOMOV"));
                                polizaDatos.setEmpresa(String.valueOf(numeroEmpresa));
                                if ("H".equals(subResultset.getString("DEBE_HABER").replace(" ", ""))) {
                                    polizaDatos.setDebe_haber(1);
                                } else {
                                    polizaDatos.setDebe_haber(0);
                                }

                                polizaDatosList.add(polizaDatos);

                            } while (subResultset.next());
                        }
                    } else {
                        do {
                            polizaDatos = new PolizaDatos();
                            polizaDatos.setIdDoctodig(resultSet.getString("ID_DOCTODIG"));
                            polizaDatos.setRutaXml(resultSet.getString("RUTA"));
                            polizaDatos.setNombreXml(resultSet.getString("ARCHIVO"));
                            polizaDatos.setTipoPoliza(resultSet.getString("TIPO"));
                            polizaDatos.setNumeroPoliza(resultSet.getString("CLAVE_POLISA"));
                            polizaDatos.setEmpresa(resultSet.getString("EMPRESA"));
                            polizaDatos.setFechaPago(resultSet.getString("FECHA POLIZA"));
                            polizaDatos.setCuenta(consultaNombreCuenta(dataBase, subBaseCoi, subFijoAuxiliar,
                                    subFijoCuenta, numeroCuenta, String.valueOf(periodo), String.valueOf(ejercicio),
                                    resultSet.getString("TIPO"), resultSet.getString("CLAVE_POLISA")));
                            polizaDatos.setConXml(1);
                            polizaDatos.setNumCuentaCoi(numeroCuenta);
                            polizaDatos.setMontoMov(resultSet.getString("MONTOMOV"));
                            if ("H".equals(resultSet.getString("DEBE_HABER").replace(" ", ""))) {
                                polizaDatos.setDebe_haber(1);
                            } else {
                                polizaDatos.setDebe_haber(0);
                            }
                            polizaDatosList.add(polizaDatos);
                        } while (resultSet.next());
                    }
//                    while (resultSet.next()) {
//                       
//                    }

                }
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionDB.Salir(conexion);
        }
        return polizaDatosList;
    }

    /**
     * Función que consulta las polizas de un ejercicio y periodo espefico en la
     * base de datos de Adsticsa
     *
     * @param periodo int
     * @param ejercicio int
     * @param numeroCuentas String[]
     * @param numeroEmpresa int
     * @param dataBase String
     * @return
     */
    public List<PolizaDatos> polizasPeriodoEjercicio_Adsticsa(int periodo, int ejercicio, String[] numeroCuentas,
            int numeroEmpresa, String dataBase, boolean tipoSolicitud) {

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
        } else {
            subFijoPeriodo = String.valueOf(periodo);
        }
        String periodoAnio = subFijoPeriodo + String.valueOf(v1) + String.valueOf(v2);
        //OK
        ArrayList<PolizaDatos> polizaDatosList = new ArrayList<>();
        Connection conexion = null;
        //Consulto las cuentas
        for (String numeroCuenta : numeroCuentas) {
            List<PolizaDatos> lpd = new ArrayList<>();
            if (tipoSolicitud) {
                lpd = this.consultarPolizasUnicasSinProcesar(dataBase, subBaseCoi, subFijoCuenta, subFijoSaldos, subFijoAuxiliar, String.valueOf(periodo), numeroCuenta);
            } else {
                lpd = this.consultarPolizasUnicas(dataBase, subBaseCoi, subFijoCuenta, subFijoSaldos, subFijoAuxiliar, String.valueOf(periodo), numeroCuenta);
            }
            //String db, String coiDb, String tableCuenta, String tableSaldos, String tableAuxiliar, String numPeriodo, String numCuenta
            try {
                conexion = connection.Entrar(dataBase);
                if (!lpd.isEmpty()) {
                    for (int x = 0; x < lpd.size(); x++) {
                        query = "SELECT d.ID_DOCTODIG, d.RUTA, d.ARCHIVO, reg.CVEENTIDAD1 as 'CLAVE_POLISA', reg.CVEENTIDAD2 'TIPO',"
                                + "CONVERT(date,aux.FECHA_POL) 'FECHA POLIZA', aux.MONTOMOV, aux.DEBE_HABER "
                                + ",reg.EMPRESA FROM "
                                + "[DOCUMENTOS_COI].[dbo].[DOCTOSDIG] d INNER JOIN [DOCUMENTOS_COI].[dbo].[RELACION] rel "
                                + "ON d.ID_DOCTODIG = rel.ID_DOCTODIG INNER JOIN [DOCUMENTOS_COI].[dbo].[REGISTROS] reg "
                                + "ON rel.ID_REGISTRO = reg.ID_REGISTRO INNER JOIN [" + subBaseCoi + "].[dbo].[" + subFijoAuxiliar + "] aux "
                                + "ON reg.CVEENTIDAD1 = aux.NUM_POLIZ AND reg.CVEENTIDAD2 = aux.TIPO_POLI "
                                + " WHERE reg.TIPOENTIDAD = 16 AND reg.CVEENTIDAD3 = '" + periodoAnio + "' AND reg.EMPRESA= " + numeroEmpresa + ""
                                + " AND aux.PERIODO = " + periodo + " AND aux.EJERCICIO = " + ejercicio + " AND aux.MONTOMOV=" + lpd.get(x).getMontoMov() + " AND (d.ARCHIVO LIKE '%xml' OR d.ARCHIVO LIKE'%XML') "
                                + "AND reg.CVEENTIDAD1=" + lpd.get(x).getNumeroPoliza() + " AND reg.CVEENTIDAD2='" + lpd.get(x).getTipoPoliza() + "'";
                        //erro sql aqui
                        stmt = conexion.createStatement();
                        resultSet = stmt.executeQuery(query);
                        //No me devuelve nada por que no estan asociados
                        if (resultSet.next() == false) {
                            subQuery = "SELECT aux.NUM_CTA,aux.FECHA_POL, aux.TIPO_POLI,aux.NUM_POLIZ ,CONVERT(date,aux.FECHA_POL) 'FECHA POLIZA', aux.MONTOMOV, aux.DEBE_HABER "
                                    + "FROM [" + subBaseCoi + "].[dbo].[" + subFijoAuxiliar + "] aux "
                                    + "WHERE aux.PERIODO = " + periodo + " AND aux.EJERCICIO = " + ejercicio + " AND aux.MONTOMOV=" + lpd.get(x).getMontoMov() + " AND aux.NUM_POLIZ=" + lpd.get(x).getNumeroPoliza() + " AND "
                                    + "aux.TIPO_POLI='" + lpd.get(x).getTipoPoliza() + "'";
                            stmt = conexion.createStatement();
                            subResultset = stmt.executeQuery(subQuery);
                            if (subResultset.next()) {
                                do {
                                    polizaDatos = new PolizaDatos();
                                    polizaDatos.setTipoPoliza(subResultset.getString("TIPO_POLI"));
                                    polizaDatos.setNumeroPoliza(subResultset.getString("NUM_POLIZ"));
                                    polizaDatos.setFechaPago(subResultset.getString("FECHA POLIZA"));
                                    polizaDatos.setCuenta(consultaNombreCuenta(dataBase, subBaseCoi, subFijoAuxiliar, subFijoCuenta, numeroCuenta, String.valueOf(periodo), String.valueOf(ejercicio), subResultset.getString("TIPO_POLI"), subResultset.getString("NUM_POLIZ")));
                                    polizaDatos.setConXml(2);
                                    polizaDatos.setNumCuentaCoi(numeroCuenta);
                                    polizaDatos.setMontoMov(subResultset.getString("MONTOMOV"));
                                    if ("H".equals(subResultset.getString("DEBE_HABER").replace(" ", ""))) {
                                        polizaDatos.setDebe_haber(1);
                                    } else {
                                        polizaDatos.setDebe_haber(0);
                                    }
                                    polizaDatos.setEmpresa(String.valueOf(numeroEmpresa));
                                    polizaDatosList.add(polizaDatos);
                                } while (subResultset.next());
                            }
                        } else {
                            do {
                                polizaDatos = new PolizaDatos();
                                polizaDatos.setIdDoctodig(resultSet.getString("ID_DOCTODIG"));
                                polizaDatos.setRutaXml(resultSet.getString("RUTA"));
                                polizaDatos.setNombreXml(resultSet.getString("ARCHIVO"));
                                polizaDatos.setTipoPoliza(resultSet.getString("TIPO"));
                                polizaDatos.setNumeroPoliza(resultSet.getString("CLAVE_POLISA"));
                                polizaDatos.setEmpresa(resultSet.getString("EMPRESA"));
                                polizaDatos.setFechaPago(resultSet.getString("FECHA POLIZA"));
                                polizaDatos.setCuenta(consultaNombreCuenta(dataBase, subBaseCoi, subFijoAuxiliar, subFijoCuenta, numeroCuenta, String.valueOf(periodo), String.valueOf(ejercicio), resultSet.getString("TIPO"), resultSet.getString("CLAVE_POLISA")));
                                polizaDatos.setNumCuentaCoi(numeroCuenta);
                                polizaDatos.setConXml(1);
                                polizaDatos.setMontoMov(resultSet.getString("MONTOMOV"));
                                if ("H".equals(resultSet.getString("DEBE_HABER").replace(" ", ""))) {
                                    polizaDatos.setDebe_haber(1);
                                } else {
                                    polizaDatos.setDebe_haber(0);
                                }
                                polizaDatosList.add(polizaDatos);
                            } while (resultSet.next());
                        }
                    }
                }
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                ConexionDB.Salir(conexion);
            }
        }
        return polizaDatosList;
    }

    /**
     * Funcion que obtiene dos listas de datos. Compara los valores y deja
     * filtrar los diferentes
     *
     * @param listaPolizaDat PolizaDatos
     * @param listaPolizasProcesadas PolizaProcesada
     * @param periodo int
     * @param ejercicio int
     * @return List listaPolizaDat
     */
    public List<PolizaDatos> listaPolizasFiltradas(List<PolizaDatos> listaPolizaDat, List<PolizaProcesada> listaPolizasProcesadas, int periodo, int ejercicio) {
        List<PolizaDatos> listaPolizasFiltradas = new ArrayList<>();

        if (!listaPolizasProcesadas.isEmpty()) {
            if (!listaPolizaDat.isEmpty()) {

                for (int i = 0; i < listaPolizaDat.size(); i++) {
                    int diferente = 8;
                    String debe_haber = (listaPolizaDat.get(i).getDebe_haber() == 1) ? "H" : "D";
                    for (int j = 0; j < listaPolizasProcesadas.size(); j++) {

                        try {

                            if ((listaPolizaDat.get(i).getNumeroPoliza()).replace(" ", "").equals(listaPolizasProcesadas.get(j).getNumeroPoliza().replace(" ", ""))) {
                                diferente--;
                            }
                            if ((listaPolizaDat.get(i).getTipoPoliza().replace(" ", "")).equals(listaPolizasProcesadas.get(j).getTipoPoliza().replace(" ", ""))) {
                                diferente--;
                            }
                            if (Double.parseDouble(listaPolizaDat.get(i).getMontoMov().replace(" ", "")) != Double.parseDouble(listaPolizasProcesadas.get(j).getMontoMov().replace(" ", ""))) {
                                diferente--;
                            }
                            if ((listaPolizaDat.get(i).getEmpresa().replace(" ", "")).equals(listaPolizasProcesadas.get(j).getEmpresa().replace(" ", ""))) {
                                diferente--;
                            }
                            if ((listaPolizaDat.get(i).getNumCuentaCoi().replace(" ", "")).equals(listaPolizasProcesadas.get(j).getNumeroCuenta().replace(" ", ""))) {
                                diferente--;
                            }
                            if ((debe_haber).equals(listaPolizasProcesadas.get(j).getDh().replace(" ", ""))) {
                                diferente--;
                            }
                            if ((String.valueOf(periodo).replace(" ", "")).equals(listaPolizasProcesadas.get(j).getPeriodo().replace(" ", ""))) {
                                diferente--;
                            }
                            if ((String.valueOf(ejercicio).replace(" ", "")).equals(listaPolizasProcesadas.get(j).getEjercicio().replace(" ", ""))) {
                                diferente--;
                            }
                            if (diferente > 0) {

                                PolizaDatos polizaDat = new PolizaDatos();
                                polizaDat.setIdDoctodig(listaPolizaDat.get(i).getIdDoctodig());
                                polizaDat.setRutaXml(listaPolizaDat.get(i).getRutaXml());
                                polizaDat.setNombreXml(listaPolizaDat.get(i).getNombreXml());
                                polizaDat.setTipoPoliza(listaPolizaDat.get(i).getTipoPoliza());
                                polizaDat.setNumeroPoliza(listaPolizaDat.get(i).getNumeroPoliza());
                                polizaDat.setEmpresa(listaPolizaDat.get(i).getEmpresa());
                                polizaDat.setFechaPago(listaPolizaDat.get(i).getFechaPago());
                                polizaDat.setCuenta(listaPolizaDat.get(i).getCuenta());
                                polizaDat.setNumCuentaCoi(listaPolizaDat.get(i).getNumCuentaCoi());
                                polizaDat.setConXml(listaPolizaDat.get(i).getConXml());
                                polizaDat.setMontoMov(listaPolizaDat.get(i).getMontoMov());
                                polizaDat.setDebe_haber(listaPolizaDat.get(i).getDebe_haber());
                                listaPolizasFiltradas.add(polizaDat);
                            }
                        } catch (NullPointerException e) {
                            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, e);
                        }

                    }
                }
            }
        } else {
            listaPolizasFiltradas = listaPolizaDat;
        }

        return listaPolizasFiltradas;
    }

    /**
     * Funcion que obtiene todos los datos de iva acreditable segun un periodo,
     * ejercicio y no. de cuenta
     *
     * @param periodo int
     * @param ejercicio int
     * @param noCuenta String
     * @param dataBase
     * @return List AuxIvaAcred
     */
    public List<AuxIvaAcred> new_auxIvaAcredConsulta(int periodo, int ejercicio, String noCuenta, String dataBase) {
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
            query = "SELECT A.NUM_CTA, NOMBRE, TIPO,B.EJERCICIO,INICIAL AS SALINI, CARGO01 AS CARGO, ABONO01 AS ABONO,INICIAL + ((CARGO01) - (ABONO01))*(1 - 2*NATURALEZA) AS SALDO , NATURALEZA,BANDMULTI, NIVEL, FECHA_POL, TIPO_POLI, NUM_POLIZ, CONCEP_PO, DEBE_HABER, MONTOMOV AS MONTO, ORDEN  FROM (" + tableCuentas + " A JOIN " + tableSaldos + " B ON A.NUM_CTA = B.NUM_CTA   )  LEFT JOIN " + tableAux + " C ON (A.NUM_CTA=C.NUM_CTA AND PERIODO IN (" + periodo + "))  WHERE A.NUM_CTA >= '" + noCuenta + "'   AND  A.NUM_CTA <= '" + noCuenta + "' ORDER BY 1,4,11,12,13,14,18";

            stmt = conexion.createStatement();
            resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                //tipo poliza, numero poliza, fecha, concepto, debe, haber
                auxIvaAcred = new AuxIvaAcred();
                auxIvaAcred.setTipoPoliza(resultSet.getString("TIPO_POLI"));
                auxIvaAcred.setNoPoliza(resultSet.getString("NUM_POLIZ"));
                auxIvaAcred.setFecha(resultSet.getString("FECHA_POL"));
                auxIvaAcred.setConcepto(resultSet.getString("CONCEP_PO"));
                if("D".equals(resultSet.getString("DEBE_HABER"))){
                    auxIvaAcred.setDebe(resultSet.getDouble("MONTO"));
                    auxIvaAcred.setHaber(0);
                }else{
                    auxIvaAcred.setHaber(resultSet.getDouble("MONTO"));
                    auxIvaAcred.setDebe(0);
                }
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

 
    public List<AuxIvaAcred> new_auxIvaAcredAsctisa(int periodo, int ejercicio, String noCuenta, String dataBase) {
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
            query = "SELECT A.NUM_CTA, NOMBRE, TIPO,B.EJERCICIO,INICIAL AS SALINI, CARGO01 AS CARGO, ABONO01 AS ABONO,INICIAL + ((CARGO01) - (ABONO01))*(1 - 2*NATURALEZA) AS SALDO , NATURALEZA,BANDMULTI, NIVEL, FECHA_POL, TIPO_POLI, NUM_POLIZ, CONCEP_PO, DEBE_HABER, MONTOMOV AS MONTO, ORDEN  FROM (" + tableCuentas + " A JOIN " + tableSaldos + " B ON A.NUM_CTA = B.NUM_CTA   )  LEFT JOIN " + tableAux + " C ON (A.NUM_CTA=C.NUM_CTA AND PERIODO IN (" + periodo + "))  WHERE A.NUM_CTA >= '" + noCuenta + "'   AND  A.NUM_CTA <= '" + noCuenta + "' ORDER BY 1,4,11,12,13,14,18";
            stmt = conexion.createStatement();
            resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                //tipo poliza, numero poliza, fecha, concepto, debe, haber
                //SEPARAR SEGUN SI ES DEBE O HABER
                auxIvaAcred = new AuxIvaAcred();
                auxIvaAcred.setTipoPoliza(resultSet.getString("TIPO_POLI"));
                auxIvaAcred.setNoPoliza(resultSet.getString("NUM_POLIZ"));
                auxIvaAcred.setFecha(resultSet.getString("FECHA_POL"));
                auxIvaAcred.setConcepto(resultSet.getString("CONCEP_PO"));
                if("D".equals(resultSet.getString("DEBE_HABER"))){
                    auxIvaAcred.setDebe(resultSet.getDouble("MONTO"));
                    auxIvaAcred.setHaber(0);
                }else{
                    auxIvaAcred.setHaber(resultSet.getDouble("MONTO"));
                    auxIvaAcred.setDebe(0);
                }
                
                
                datosAuxiliarIva.add(auxIvaAcred);
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

    /**
     * Metodo que consulta todos los conceptos Relación de actividades de la
     * Base de datos
     *
     * @param dataBase String
     * @return
     */
    public List<String> consultarConceptosRelacion(String dataBase) {
        connection = new ConexionDB();
        Connection conexion = null;
        String relacion = "";
        List<String> listaRelacion = new ArrayList<>();
        try {
            conexion = connection.Entrar(dataBase);
            query = "SELECT [ID]n"
                    + " ,[CLAVE_CONCEPTO]n"
                    + " ,[DESCRIPCION]n"
                    + " FROM [" + dataBase + "].[dbo].[CONCEPTOS_RELACION]";
            stmt = conexion.createStatement();
            resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                relacion = resultSet.getString("DESCRIPCION");
                listaRelacion.add(relacion);
            }

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionDB.Salir(conexion);
        }
        return listaRelacion;
    }

    /**
     * Funcion qu consulta y obtiene la relacion con actividad de la BD
     *
     * @param dataBase String
     * @param RFC String
     * @return String
     */
    public String consultarRelacionActividad(String dataBase, String RFC) {
        connection = new ConexionDB();
        Connection conexion = null;
        String relacion = "";
        try {
            conexion = connection.Entrar(dataBase);
            query = "SELECT RA.RFC, CR.DESCRIPCION FROM [" + dataBase + "].[dbo].[RELACION_RFC_REL_ACT] RA "
                    + "INNER JOIN [" + dataBase + "].[dbo].[CONCEPTOS_RELACION] CR "
                    + "ON CR.CLAVE_CONCEPTO=RA.ID_RELACION_ACTIVIDAD WHERE RA.RFC='" + RFC + "'";
            stmt = conexion.createStatement();
            resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                relacion = resultSet.getString("DESCRIPCION");
            }

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionDB.Salir(conexion);
        }
        return relacion;
    }

    /**
     * Función para obtener el nombre de la cuenta
     *
     * @param dataBase String
     * @param coiDb String
     * @param auxDb String
     * @param ctaDb String
     * @param noCuenta String
     * @param periodo String
     * @param ejercicio String
     * @param tipoPoli String
     * @param numPoliza String
     * @return
     */
    private String consultaNombreCuenta(String dataBase, String coiDb, String auxDb, String ctaDb,
            String noCuenta, String periodo, String ejercicio, String tipoPoli, String numPoliza) {
        ConexionDB connection2 = new ConexionDB();
        Connection conexion2 = null;
        String nombreConexion = "";
        try {
            conexion2 = connection2.Entrar(dataBase);
            subQuery = "SELECT AUX.[NUM_CTA] ,CTA.[NOMBRE]"
                    + " FROM [" + coiDb + "].[dbo].[" + auxDb + "] AUX"
                    + " INNER JOIN [" + coiDb + "].[dbo].[" + ctaDb + "] CTA ON AUX.[NUM_CTA]=CTA.[NUM_CTA]"
                    + " WHERE AUX.[NUM_CTA]='" + noCuenta + "' AND AUX.[PERIODO]=" + periodo + " "
                    + " AND AUX.[EJERCICIO]=" + ejercicio + " AND AUX.[TIPO_POLI]='" + tipoPoli + "' "
                    + " AND AUX.NUM_POLIZ =" + numPoliza + " "
                    + " GROUP BY AUX.[NUM_CTA], CTA.[NOMBRE]";
            Statement stmt2 = conexion2.createStatement();
            ResultSet resultSet2 = stmt2.executeQuery(subQuery);
            while (resultSet2.next()) {
                nombreConexion = resultSet2.getString("NOMBRE");
            }

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionDB.Salir(conexion2);
        }
        return nombreConexion;
    }

    /**
     * Funcion que consulta un no. de poliza existente segun una tabla, periodo
     * y anio
     *
     * @param db String
     * @param coiDb String
     * @param tableCuenta String
     * @param tableSaldos String
     * @param tableAuxiliar String
     * @param numPeriodo String
     * @param numCuenta String
     * @return
     */
    private List<PolizaDatos> consultarPolizasUnicas(String db, String coiDb, String tableCuenta, String tableSaldos, String tableAuxiliar, String numPeriodo, String numCuenta) {
        connection = new ConexionDB();
        Connection conexion = null;
        String relacion = "";
        List<PolizaDatos> lpd = new ArrayList<>();

        try {
            conexion = connection.Entrar(db);
            query = "SELECT (CASE WHEN NUM_POLIZ IS NULL THEN 'N/D' ELSE NUM_POLIZ END) NUM_POLIZ, C.TIPO_POLI,C.MONTOMOV FROM ([" + coiDb + "].[dbo].[" + tableCuenta + "] A "
                    + " JOIN [" + coiDb + "].[dbo].[" + tableSaldos + "] B ON A.NUM_CTA = B.NUM_CTA)  LEFT JOIN [" + coiDb + "].[dbo].[" + tableAuxiliar + "] C ON (A.NUM_CTA=C.NUM_CTA "
                    + " AND PERIODO IN (" + numPeriodo + "))  WHERE A.NUM_CTA >= '" + numCuenta + "' AND A.NUM_CTA <= '" + numCuenta + "' AND C.MONTOMOV!=0";
            stmt = conexion.createStatement();
            resultSet = stmt.executeQuery(query);

            while (resultSet.next()) {
                polizaDatos = new PolizaDatos();
                polizaDatos.setNumeroPoliza(resultSet.getString("NUM_POLIZ"));
                polizaDatos.setTipoPoliza(resultSet.getString("TIPO_POLI"));
                polizaDatos.setMontoMov(resultSet.getString("MONTOMOV"));
                lpd.add(polizaDatos);
            }

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionDB.Salir(conexion);
        }
        return lpd;
    }

    /**
     *
     * @param db String
     * @param coiDb String
     * @param tableCuenta String
     * @param tableSaldos String
     * @param tableAuxiliar String
     * @param numPeriodo String
     * @param numCuenta String
     * @return List PolizaDatos
     */
    private List<PolizaDatos> consultarPolizasUnicasSinProcesar(String db, String coiDb, String tableCuenta, String tableSaldos, String tableAuxiliar, String numPeriodo, String numCuenta) {
        connection = new ConexionDB();
        Connection conexion = null;
        String relacion = "";
        List<PolizaDatos> lpd = new ArrayList<>();

        try {
            conexion = connection.Entrar(db);
            query = "SELECT (CASE WHEN C.NUM_POLIZ IS NULL THEN 'N/D' ELSE C.NUM_POLIZ END) NUM_POLIZ, C.TIPO_POLI,C.MONTOMOV FROM ([" + coiDb + "].[dbo].[" + tableCuenta + "] A  JOIN [" + coiDb + "].[dbo].[" + tableSaldos + "] B ON A.NUM_CTA = B.NUM_CTA)  LEFT JOIN [" + coiDb + "].[dbo].[" + tableAuxiliar + "] C ON (A.NUM_CTA=C.NUM_CTA  AND PERIODO IN (" + numPeriodo + ")) LEFT JOIN [DOCUMENTOS_COI].[dbo].[PARTIDAS_PROCESADAS] P ON C.NUM_POLIZ = P.NUM_POLIZ AND C.TIPO_POLI = P.TIPO_POLI AND C.MONTOMOV = P.MONTO_MOV WHERE A.NUM_CTA >= '" + numCuenta + "' AND A.NUM_CTA <= '" + numCuenta + "' AND C.MONTOMOV!=0 AND (P.NUM_POLIZ IS NULL AND P.TIPO_POLI IS NULL AND P.MONTO_MOV IS NULL)";
            stmt = conexion.createStatement();
            resultSet = stmt.executeQuery(query);

            while (resultSet.next()) {
                polizaDatos = new PolizaDatos();
                polizaDatos.setNumeroPoliza(resultSet.getString("NUM_POLIZ"));
                polizaDatos.setTipoPoliza(resultSet.getString("TIPO_POLI"));
                polizaDatos.setMontoMov(resultSet.getString("MONTOMOV"));
                lpd.add(polizaDatos);
            }

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionDB.Salir(conexion);
        }
        return lpd;
    }

    /**
     * Funcion que consulta y retorna una lista de todas las polizas procesadas
     * disponibles en la BD
     *
     * @param db String
     * @return List PolizaProcesada
     */
    public List<PolizaProcesada> listaPolizasProcesadas(String db) {
        connection = new ConexionDB();
        Connection conexion = null;

        List<PolizaProcesada> lpp = new ArrayList<>();
        try {
            conexion = connection.Entrar(db);
            query = "SELECT * FROM [dbo].[PARTIDAS_PROCESADAS]";
            stmt = conexion.createStatement();
            resultSet = stmt.executeQuery(query);

            while (resultSet.next()) {
                polizaProcesada = new PolizaProcesada();
                polizaProcesada.setNumeroPoliza(resultSet.getString("NUM_POLIZ"));
                polizaProcesada.setTipoPoliza(resultSet.getString("TIPO_POLI"));
                polizaProcesada.setMontoMov(resultSet.getString("MONTO_MOV"));
                polizaProcesada.setDh(resultSet.getString("DEBE_HABER"));
                polizaProcesada.setEmpresa(resultSet.getString("EMPRESA"));
                polizaProcesada.setPeriodo(resultSet.getString("PERIODO"));
                polizaProcesada.setEjercicio(resultSet.getString("EJERCICIO"));
                polizaProcesada.setNumeroCuenta(resultSet.getString("CUENTA"));
                lpp.add(polizaProcesada);
            }

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionDB.Salir(conexion);
        }
        return lpp;
    }

    /**
     * Funcion que retorna el ultimo registro de los rfc relacionados a las
     * actividades
     *
     * @return int
     */
    private int ultimoRegistroRelacionRfc() {

        ConexionDB connection2 = new ConexionDB();
        Connection conexion2 = null;
        String baseCoi = "COI80Empre1";
        int ultimoID = 0;

        try {
            conexion2 = connection2.Entrar(baseCoi);
            subQuery = "SELECT TOP (1) [ID] FROM [COI80Empre1].[dbo].[RELACION_RFC_REL_ACT] ORDER BY ID DESC";
            Statement stmt2 = conexion2.createStatement();
            ResultSet resultSet2 = stmt2.executeQuery(subQuery);
            while (resultSet2.next()) {
                ultimoID = resultSet2.getInt("ID");
            }

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionDB.Salir(conexion2);
        }
        return ultimoID;

    }

    /*
    *Generar 2 listas una con todas las partidas procesadas y otra con las polizas disponibles (con o sin xml)
    
    *Despues generar una nueva lista <PolizaDatos>, la cual se llanara UNICAMENTE con los registros que pasen de una comparacion
    entre todos los campos de existentes en PolizasProcesadas y Las PolizasDisponibles
    
    *Pasar solo los registros que que sean DIFERENTES
    
    *Crear Modelo para PolizasProcesadas --
     */
    /**
     * Funcion que retorna una lista de RelacionActividadess
     *
     * @return
     */
    public List<RelacionActividades> relaciones() {
        List<RelacionActividades> listaRelaciones = new ArrayList<>();
        connection = new ConexionDB();
        Connection conexion = null;
        String baseCoi = "COI80Empre1";
        String ultimoID = "";

        try {
            conexion = connection.Entrar(baseCoi);
            subQuery = "SELECT [ID] ,[CLAVE_CONCEPTO] ,[DESCRIPCION] FROM [COI80Empre1].[dbo].[CONCEPTOS_RELACION]";
            stmt = conexion.createStatement();
            resultSet = stmt.executeQuery(subQuery);
            while (resultSet.next()) {
                relacionActividades = new RelacionActividades();
                relacionActividades.setIdRelacion(resultSet.getString("ID"));
                relacionActividades.setCodigoRelacion(resultSet.getString("CLAVE_CONCEPTO"));
                relacionActividades.setDescripcionRelacion(resultSet.getString("DESCRIPCION"));
                listaRelaciones.add(relacionActividades);
            }

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionDB.Salir(conexion);
        }
        return listaRelaciones;
    }

    /**
     * Funcion que obtiene los RFC que se encuentras asociados a alguna
     * actividad
     *
     * @return
     */
    public List<RelacionActividades> relacionesRFC() {
        List<RelacionActividades> listaRelaciones = new ArrayList<>();
        connection = new ConexionDB();
        Connection conexion = null;
        String baseCoi = "COI80Empre1";
        String ultimoID = "";

        try {
            conexion = connection.Entrar(baseCoi);
            subQuery = "SELECT [ID] ,[RFC] ,[ID_RELACION_ACTIVIDAD] FROM [COI80Empre1].[dbo].[RELACION_RFC_REL_ACT]";
            stmt = conexion.createStatement();
            resultSet = stmt.executeQuery(subQuery);
            while (resultSet.next()) {
                relacionActividades = new RelacionActividades();
                relacionActividades.setIdRfc(resultSet.getString("ID"));
                relacionActividades.setRfcRelacion(resultSet.getString("RFC"));
                relacionActividades.setIdAsociado(resultSet.getString("ID_RELACION_ACTIVIDAD"));
                listaRelaciones.add(relacionActividades);
            }

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionDB.Salir(conexion);
        }
        return listaRelaciones;
    }

    /**
     * Funcion que recibe una lisa de PolizaProcesada y realiza la inserción a
     * la BD
     *
     * @param datosPolizaProcesada
     * @param numEmpresa
     * @return boolean
     */
    //Cambiar para los que no tienen no de poliza
    public boolean insertarPolizasProcesadas(List<PolizaProcesada> datosPolizaProcesada, int numEmpresa) {
        boolean resultInsert = false;
        String dataBase = "DOCUMENTOS_COI";
        connection = new ConexionDB();
        Connection conexion = null;
        try {
            conexion = connection.Entrar(dataBase);
            PreparedStatement ps;
            for (int i = 0; i < datosPolizaProcesada.size(); i++) {
                String queryPrepare = "INSERT INTO [dbo].[PARTIDAS_PROCESADAS] ([NUM_POLIZ], [TIPO_POLI] ,[MONTO_MOV], [DEBE_HABER] ,[EMPRESA] ,[PERIODO]"
                        + " ,[EJERCICIO], [CUENTA] ,[PROCESADO]) VALUES (? ,? ,? ,? ,? ,? ,? ,?,?)";

                ps = conexion.prepareStatement(queryPrepare);
                ps.setString(1, datosPolizaProcesada.get(i).getNumeroPoliza());
                ps.setString(2, datosPolizaProcesada.get(i).getTipoPoliza());
                ps.setString(3, datosPolizaProcesada.get(i).getMontoMov());
                ps.setString(4, datosPolizaProcesada.get(i).getDh());
                ps.setString(5, String.valueOf(numEmpresa));
                ps.setString(6, datosPolizaProcesada.get(i).getPeriodo());
                ps.setString(7, datosPolizaProcesada.get(i).getEjercicio());
                ps.setString(8, datosPolizaProcesada.get(i).getNumeroCuenta());
                ps.setInt(9, 1);
                ps.executeUpdate();
            }
            //POSIBLE ERROR EN PROGRESO
            String queryDuplicados = "SELECT DISTINCT A.NUM_POLIZ, A.TIPO_POLI,A.MONTO_MOV,A.DEBE_HABER,A.EMPRESA,A.PERIODO,A.EJERCICIO,A.CUENTA, A.PROCESADO INTO  [DOCUMENTOS_COI].[dbo].[PARTIDAS_PROCESADAS_DUPLICADAS]\n"
                    + "      FROM [DOCUMENTOS_COI].[dbo].[PARTIDAS_PROCESADAS] A\n"
                    + " GROUP BY A.NUM_POLIZ, A.TIPO_POLI,A.MONTO_MOV,A.DEBE_HABER,A.EMPRESA,A.PERIODO,A.EJERCICIO,A.CUENTA, A.PROCESADO \n"
                    + " HAVING COUNT(*) > 1";
            ps = conexion.prepareStatement(queryDuplicados);
            ps.executeUpdate();

            String queryEliminarDuplicado = " DELETE [DOCUMENTOS_COI].[dbo].[PARTIDAS_PROCESADAS] WHERE NUM_POLIZ IN (SELECT pp.NUM_POLIZ\n"
                    + "             FROM [DOCUMENTOS_COI].[dbo].[PARTIDAS_PROCESADAS_DUPLICADAS] pp) AND TIPO_POLI IN(SELECT pp.TIPO_POLI\n"
                    + "             FROM [DOCUMENTOS_COI].[dbo].[PARTIDAS_PROCESADAS_DUPLICADAS] pp) AND MONTO_MOV IN (SELECT pp.MONTO_MOV\n"
                    + "             FROM [DOCUMENTOS_COI].[dbo].[PARTIDAS_PROCESADAS_DUPLICADAS] pp) AND DEBE_HABER IN (SELECT pp.DEBE_HABER\n"
                    + "             FROM [DOCUMENTOS_COI].[dbo].[PARTIDAS_PROCESADAS_DUPLICADAS] pp) AND EMPRESA IN (SELECT pp.EMPRESA\n"
                    + "             FROM [DOCUMENTOS_COI].[dbo].[PARTIDAS_PROCESADAS_DUPLICADAS] pp) AND PERIODO IN (SELECT pp.PERIODO\n"
                    + "             FROM [DOCUMENTOS_COI].[dbo].[PARTIDAS_PROCESADAS_DUPLICADAS] pp) AND EJERCICIO IN (SELECT pp.EJERCICIO\n"
                    + "             FROM [DOCUMENTOS_COI].[dbo].[PARTIDAS_PROCESADAS_DUPLICADAS] pp) AND CUENTA IN (SELECT pp.CUENTA\n"
                    + "             FROM [DOCUMENTOS_COI].[dbo].[PARTIDAS_PROCESADAS_DUPLICADAS] pp)";

            ps = conexion.prepareStatement(queryEliminarDuplicado);
            ps.executeUpdate();

            String queryRetornandoUnicos = " INSERT [DOCUMENTOS_COI].[dbo].[PARTIDAS_PROCESADAS] SELECT * FROM [DOCUMENTOS_COI].[dbo].[PARTIDAS_PROCESADAS_DUPLICADAS]";
            ps = conexion.prepareStatement(queryRetornandoUnicos);
            ps.executeUpdate();

            String queryEliminarTablaTemporal = "DROP TABLE  [DOCUMENTOS_COI].[dbo].[PARTIDAS_PROCESADAS_DUPLICADAS]";
            ps = conexion.prepareStatement(queryEliminarTablaTemporal);
            ps.executeUpdate();
            resultInsert = true;
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionDB.Salir(conexion);
        }
        return resultInsert;
    }

    /**
     *
     * @param nombreRelacion
     * @param claveRelacion
     * @return
     */
    public boolean insertarNuevaRelacion(String nombreRelacion, String claveRelacion) {
        connection = new ConexionDB();
        Connection conexion = null;
        String relacion = "";
        String dbAsticsa = "COI80Empre1";
        String dbAgro = "COI80Empre2";
        boolean exito1 = false;
        boolean exito2 = false;
        PreparedStatement ps;
        try {
            conexion = connection.Entrar(dbAsticsa);
            query = "INSERT INTO [" + dbAsticsa + "].[dbo].[CONCEPTOS_RELACION]([CLAVE_CONCEPTO],[DESCRIPCION]) VALUES (?,?)";
            ps = conexion.prepareStatement(query);

            ps.setString(1, claveRelacion);
            ps.setString(2, nombreRelacion);
            ps.executeUpdate();
            exito1 = true;

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionDB.Salir(conexion);
        }

        try {
            conexion = connection.Entrar(dbAgro);
            query = "INSERT INTO [" + dbAgro + "].[dbo].[CONCEPTOS_RELACION]([CLAVE_CONCEPTO],[DESCRIPCION]) VALUES (?,?)";
            ps = conexion.prepareStatement(query);
            ps.setString(1, claveRelacion);
            ps.setString(2, nombreRelacion);
            ps.executeUpdate();
            exito2 = true;

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionDB.Salir(conexion);
        }
        return exito1 && exito2;
    }

    /**
     * Funcion que inserta una nueva relacion entre un RFC y una Actividad en la
     * BD
     *
     * @param clave
     * @param nuevoRfc
     * @return boolean
     */
    public boolean insertNuevoRfcActividad(String clave, String nuevoRfc) {
        connection = new ConexionDB();
        Connection conexion = null;
        String relacion = "";
        String dbAsticsa = "COI80Empre1";
        String dbAgro = "COI80Empre2";
        boolean exito1 = false;
        boolean exito2 = false;
        int ultimoID = this.ultimoRegistroRelacionRfc() + 1;

        PreparedStatement ps;
        try {
            conexion = connection.Entrar(dbAsticsa);
            query = "INSERT INTO [" + dbAsticsa + "].[dbo].[RELACION_RFC_REL_ACT] ([ID] ,[RFC] ,[ID_RELACION_ACTIVIDAD]) VALUES (?,?,?)";
            ps = conexion.prepareStatement(query);

            ps.setInt(1, ultimoID);
            ps.setString(2, nuevoRfc);
            ps.setString(3, clave);
            ps.executeUpdate();
            exito1 = true;

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionDB.Salir(conexion);
        }

        try {
            conexion = connection.Entrar(dbAgro);
            query = "INSERT INTO [" + dbAgro + "].[dbo].[RELACION_RFC_REL_ACT] ([ID] ,[RFC] ,[ID_RELACION_ACTIVIDAD]) VALUES (?,?,?)";
            ps = conexion.prepareStatement(query);
            ps.setInt(1, ultimoID);
            ps.setString(2, nuevoRfc);
            ps.setString(3, clave);
            ps.executeUpdate();
            exito2 = true;

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionDB.Salir(conexion);
        }
        return exito1 && exito2;
    }

    /**
     * Funcion que actualiza la relacion entre un RFC y su Actividad
     *
     * @param clave
     * @param idRfcAsociado
     * @return boolean
     */
    public boolean actualizarRelacionActividad(String clave, int idRfcAsociado) {
        connection = new ConexionDB();
        Connection conexion = null;
        String dbAsticsa = "COI80Empre1";
        String dbAgro = "COI80Empre2";
        boolean exito1 = false;
        boolean exito2 = false;
        PreparedStatement ps;

        try {
            conexion = connection.Entrar(dbAsticsa);
            query = "UPDATE [" + dbAsticsa + "].[dbo].[RELACION_RFC_REL_ACT] SET [ID_RELACION_ACTIVIDAD] = ? "
                    + "WHERE [ID]=?";
            ps = conexion.prepareStatement(query);
            ps.setString(1, clave);
            ps.setInt(2, idRfcAsociado);
            ps.executeUpdate();
            exito1 = true;

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionDB.Salir(conexion);
        }

        try {
            conexion = connection.Entrar(dbAgro);
            query = "UPDATE [" + dbAgro + "].[dbo].[RELACION_RFC_REL_ACT] SET [ID_RELACION_ACTIVIDAD] = ? "
                    + "WHERE [ID]=?";
            ps = conexion.prepareStatement(query);
            ps.setString(1, clave);
            ps.setInt(2, idRfcAsociado);
            ps.executeUpdate();
            exito2 = true;

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionDB.Salir(conexion);
        }
        return exito1 && exito2;
    }

    //String clave, int idRfcAsociado
}
