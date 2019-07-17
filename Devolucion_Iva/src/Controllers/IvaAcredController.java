/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.Consultas;
import Models.PolizaDatos;
import Models.PolizaProcesada;
import Models.XmlDatos;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.jespxml.JespXML;
import org.jespxml.excepciones.AtributoNotFoundException;
import org.jespxml.excepciones.TagHijoNotFoundException;
import org.jespxml.modelo.Tag;
import org.xml.sax.SAXException;

/**
 *
 * @author Macktronica
 */
public class IvaAcredController {

    private Consultas consultas;
    private Tag raizXml, et_Concepto;
    private Tag cfdi_Impuestos, cfdi_Complemento, cfdi_Concepto_h, cfdi_ConceptoImpuestos, cfdi_Retenciones, cfdi_retencion_h, tfd_TimbreFiscalDigital, cfdi_Emisor, cfdi_traslados, cfdi_traslado_hijo, cfdi_folioSerie;
    private String fechaFactura, folioFiscal, folioInterno, total, base16, rfc, proveedor, formaPago, iva,
            retencionCuatro, retencionDiez, retencion1016, cuotaC, folioSerie;
    private double baseCeroSuma = 0;
    private double base_16 = 0;
    private String traslado = "";
    private final String COI_AGRO = "COI80Empre2";
    private final String COI_ADSTICSA = "COI80Empre1";
    private final String BANCOMER_ADS_2678 = "111500200100000000003";
    private final String SANTANDER_ADS_2399 = "111500300100000000003";
    private final String SANTANDER2_ADS_6082 = "111500300200000000003";
    private final String SANTANDER3_ADS_5170 = "203500300100000000003";
    private final String BANORTE_ADS_0212 = "111500700100000000003";
    private final String BANORTE2_ADS_7202 = "111500700200000000003";
    private final String CUENTA_BANCOMER_AGRO = "111500700100000000003";
    private final List<XmlDatos> datosXml = new ArrayList<>();
    private List<PolizaDatos> polizaDat = new ArrayList<>();
    private PolizaProcesada polizaProcesada;
    private List<Tag> cfdi_Comprobante;

    /**
     * Funcion que recibe una url en forma de String, para obtener datos de
     * archivos xml segun los conceptos solicitados
     *
     * @param URL
     * @return List XmlDatos
     */
    /**
     * Funcion que obtiene una lista de poliza de datos, para poder obtener la
     * ruta de los archivos xml y terminar de llenar los datos de un objeto
     * XmlDatos
     *
     * @param listFicherosPolizaBase
     * @param db
     * @return
     */
    public List<XmlDatos> listDatosXmlCienAcred_List(List<PolizaDatos> listFicherosPolizaBase, String db) {

        /*
        IMPORTANTE: PARA QUE EL SISTEMA FUNCIONE DE MANERA REMOTA, SE DEBE TENER GUARDARA LA CONTRASEÑA Y USUARIO DEL HOST DONDE SE QUIERE ACCEDER
        Resetear todas las tablas si que estan vacias para no confundir
         */
        consultas = new Consultas();
        String descripcionRelacion = "";
        //url de la carpeta del xml
        for (int p = 0; p < listFicherosPolizaBase.size(); p++) {
            switch (listFicherosPolizaBase.get(p).getConXml()) {
                case 1:
                    //Dos periodos diferentes jalan el mismo xml
                    /*
                    junio 2016 adsticsa
                    */
                    String URL = "\\\\25.62.86.238\\dacaspel\\Documentos digitales\\" + listFicherosPolizaBase.get(p).getRutaXml() + listFicherosPolizaBase.get(p).getNombreXml();
                    System.out.println("Archivo consultado: "+URL);

                    if (!URL.isEmpty() || !URL.equals("")) {
                        try {
                            //Tomando la ruta de la carpeta
                            JespXML xmlCarpeta = new JespXML(URL);

                            //Aqui se podria checar el # de archivos disponibles
                            if (!xmlCarpeta.getAbsolutePath().isEmpty()) {
                                try {
                                    //verificando que sean archivos xml

                                    //Obteniendo la ruta del archivo
                                    JespXML fileXml = xmlCarpeta;

                                    XmlDatos infoXml = new XmlDatos();
                                    StringBuilder valoresConcepto = new StringBuilder();
                                    String formaDePagoDescripcion = "";
                                    //raiz de los archivos
                                    raizXml = fileXml.leerXML();

                                    //Atributos de la raiz
                                    String numCertificado = raizXml.getValorDeAtributo("NoCertificado");
                                    folioSerie = raizXml.getValorDeAtributo("Folio");
                                    infoXml.setNumeroFactura(folioSerie);
                                    fechaFactura = raizXml.getValorDeAtributo("Fecha");
                                    infoXml.setFechaFactura(fechaFactura);
                                    try {
                                        folioInterno = raizXml.getValorDeAtributo("Folio");
                                    } catch (AtributoNotFoundException e) {
                                        folioInterno = "";
                                    }

                                    infoXml.setFolioInterno(folioInterno);

                                    total = raizXml.getValorDeAtributo("Total");
                                    infoXml.setTotal(total);

                                    try {
                                        formaPago = raizXml.getValorDeAtributo("FormaPago");
                                        switch (formaPago) {
                                            case "01":
                                                formaDePagoDescripcion = "Efectivo";
                                                break;
                                            case "02":
                                                formaDePagoDescripcion = "Cheque nominativo";
                                                break;
                                            case "03":
                                                formaDePagoDescripcion = "Transferencia electrónica de fondos";
                                                break;
                                            case "04":
                                                formaDePagoDescripcion = "Tarjeta de crédito";
                                                break;
                                            case "05":
                                                formaDePagoDescripcion = "Monedero electrónico";
                                                break;
                                            case "06":
                                                formaDePagoDescripcion = "Dinero electrónico";
                                                break;
                                            case "08":
                                                formaDePagoDescripcion = "Vales de despensa";
                                                break;
                                            case "12":
                                                formaDePagoDescripcion = "Dación en pago";
                                                break;
                                            case "13":
                                                formaDePagoDescripcion = "Pago por subrogación";
                                                break;
                                            case "14":
                                                formaDePagoDescripcion = "Pago por consignación";
                                                break;
                                            case "15":
                                                formaDePagoDescripcion = "Condonación";
                                                break;
                                            case "17":
                                                formaDePagoDescripcion = "Compensación";
                                                break;
                                            case "23":
                                                formaDePagoDescripcion = "Novación";
                                                break;
                                            case "24":
                                                formaDePagoDescripcion = "Confusión";
                                                break;
                                            case "25":
                                                formaDePagoDescripcion = "Remisión de deuda";
                                                break;
                                            case "26":
                                                formaDePagoDescripcion = "Prescripción o caducidad";
                                                break;
                                            case "27":
                                                formaDePagoDescripcion = "A satisfacción del acreedor";
                                                break;
                                            case "28":
                                                formaDePagoDescripcion = "Tarjeta de débito";
                                                break;
                                            case "29":
                                                formaDePagoDescripcion = "Tarjeta de servicios";
                                                break;
                                            case "30":
                                                formaDePagoDescripcion = "Aplicación de anticipos";
                                                break;
                                            case "31":
                                                formaDePagoDescripcion = "Intermediario pagos";
                                                break;
                                            case "99":
                                                formaDePagoDescripcion = "Por definir";
                                                break;
                                            default:
                                                formaDePagoDescripcion = "";
                                                break;

                                        }
                                    } catch (AtributoNotFoundException e) {
                                        formaDePagoDescripcion = "";
                                    }
                                    infoXml.setFormaPago(formaDePagoDescripcion);

                                    //tomando todos las etiquetas de un xml y guardandolas en una lista
                                    cfdi_Comprobante = raizXml.getTagsHijos();

                                    for (int i = 0; i < cfdi_Comprobante.size(); i++) {
                                        //Obteniendo todas las etiquetas hijo de la raiz
                                        String nombreEtiqueta = cfdi_Comprobante.get(i).toString();
                                        //Buscando el la sub-etiqueta deceada
                                        switch (nombreEtiqueta) {
                                            case "<cfdi:Emisor>":
                                                cfdi_Emisor = raizXml.getTagHijoByName("cfdi:Emisor");
                                                rfc = cfdi_Emisor.getValorDeAtributo("Rfc");
                                                infoXml.setRfc(rfc);
                                                descripcionRelacion = consultas.consultarRelacionActividad(db, rfc);
                                                infoXml.setRelacion(descripcionRelacion);
                                                try {
                                                    proveedor = cfdi_Emisor.getValorDeAtributo("Nombre");
                                                } catch (AtributoNotFoundException e) {
                                                    proveedor = "";
                                                }
                                                infoXml.setProveedor(proveedor);

                                                break;
                                            case "<cfdi:Conceptos>":
                                                /*
                                        Desde aqui para los calculos reales
                                                 */
                                                List<Tag> cfdi_Conceptos;
                                                List<Tag> retenciones;
                                                //Obteniendo hijos de cfdi:Conceptos
                                                cfdi_Conceptos = cfdi_Comprobante.get(i).getTagsHijos();

                                                for (int j = 0; j < cfdi_Conceptos.size(); j++) {
                                                    //obteniendo el la primera sub-etiqueta de <cfdi:Conceptos>
                                                    cfdi_Concepto_h = cfdi_Conceptos.get(j);

                                                    //Cadena del concepto
                                                    if (valoresConcepto.toString().isEmpty()) {

                                                        valoresConcepto.append(cfdi_Concepto_h.getValorDeAtributo("Descripcion"));

                                                    }
                                                    if (!valoresConcepto.toString().isEmpty() && (numCertificado.equals(raizXml.getValorDeAtributo("NoCertificado")))) {
                                                        if (!valoresConcepto.toString().equals(cfdi_Concepto_h.getValorDeAtributo("Descripcion"))) {
                                                            valoresConcepto.append(". ");
                                                            valoresConcepto.append(cfdi_Concepto_h.getValorDeAtributo("Descripcion"));
                                                        }

                                                    }
                                                    try {
                                                        //traslado
                                                        cfdi_ConceptoImpuestos = cfdi_Concepto_h.getTagHijoByName("cfdi:Impuestos");
                                                        //Aqui impuestos traslado
                                                        cfdi_traslados = cfdi_ConceptoImpuestos.getTagHijoByName("cfdi:Traslados");
                                                        cfdi_traslado_hijo = cfdi_traslados.getTagHijoByName("cfdi:Traslado");
                                                        String tipoBase = cfdi_traslado_hijo.getValorDeAtributo("TasaOCuota");
                                                        if ("0.000000".equals(tipoBase)) {
                                                            if ("".equals(traslado)) {
                                                                traslado = cfdi_traslado_hijo.getValorDeAtributo("Base");
                                                                baseCeroSuma = Double.parseDouble(traslado);
                                                            } else {
                                                                traslado = cfdi_traslado_hijo.getValorDeAtributo("Base");
                                                                baseCeroSuma += Double.parseDouble(traslado);
                                                            }
                                                        }
                                                        if (tipoBase.equals("0.160000") || tipoBase.equals("0.16")) {
                                                            if ("".equals(traslado)) {
                                                                traslado = cfdi_traslado_hijo.getValorDeAtributo("Base");
                                                                base_16 = Double.parseDouble(traslado);
                                                            } else {
                                                                traslado = cfdi_traslado_hijo.getValorDeAtributo("Base");
                                                                base_16 += Double.parseDouble(traslado);
                                                            }
                                                        }
                                                    } catch (TagHijoNotFoundException e) {
                                                        baseCeroSuma = 0;
                                                        base_16 = 0;
                                                    }

                                                    //fin traslado
                                                    try {
                                                        //No aesta cargando en ascticsa, verificar el por que
                                                        //Optimizar la velocidad
                                                        cfdi_ConceptoImpuestos = cfdi_Concepto_h.getTagHijoByName("cfdi:Impuestos");

                                                        //retenciones
                                                        //probable problema
                                                        cfdi_Retenciones = cfdi_ConceptoImpuestos.getTagHijoByName("cfdi:Retenciones");
                                                        retenciones = cfdi_Retenciones.getTagsHijos();
                                                        for (int k = 0; k < retenciones.size(); k++) {
                                                            String tipoRetencion = "";
                                                            cfdi_retencion_h = retenciones.get(k);
                                                            tipoRetencion = cfdi_retencion_h.getValorDeAtributo("TasaOCuota");
                                                            switch (tipoRetencion) {
                                                                case "0.040000":
                                                                    if (retencionCuatro.isEmpty()) {
                                                                        retencionCuatro = cfdi_retencion_h.getValorDeAtributo("Importe");
                                                                    }
                                                                    break;
                                                                case "0.100000":
                                                                    if (retencionDiez.isEmpty()) {
                                                                        retencionDiez = cfdi_retencion_h.getValorDeAtributo("Importe");
                                                                    }
                                                                    break;
                                                                case "0.106700":
                                                                    if (retencion1016.isEmpty()) {
                                                                        retencion1016 = cfdi_retencion_h.getValorDeAtributo("Importe");
                                                                    }
                                                                    break;
                                                                default:
                                                                    break;
                                                            }
                                                        }
                                                    } catch (TagHijoNotFoundException | NullPointerException | AtributoNotFoundException e) {
                                                        retencionCuatro = "0";
                                                        retencionDiez = "0";
                                                        retencion1016 = "0";
                                                        Logger.getLogger(IvaAcredController.class.getName()).log(Level.SEVERE, null, e);

                                                    }

                                                }

                                                //Descripcion:Concepto
                                                infoXml.setConceptoXml(valoresConcepto.toString());
                                                //Retenciones
                                                infoXml.setRetencionCuatro(retencionCuatro);
                                                infoXml.setRetencionDiez(retencionDiez);
                                                infoXml.setRetencion1016(retencion1016);

                                                break;
                                            case "<cfdi:Complemento>":
                                                try {
                                                    cfdi_Complemento = raizXml.getTagHijoByName("cfdi:Complemento");
                                                    tfd_TimbreFiscalDigital = cfdi_Complemento.getTagHijoByName("tfd:TimbreFiscalDigital");
                                                    folioFiscal = tfd_TimbreFiscalDigital.getValorDeAtributo("UUID").toUpperCase();
                                                } catch (TagHijoNotFoundException e) {
                                                    folioFiscal = " ";
                                                }

                                                infoXml.setFolioFiscal(folioFiscal);

                                                break;
                                            case "<cfdi:Impuestos>":

                                                double calIva = 0;
                                                String tipoTasa;
                                                cfdi_Impuestos = raizXml.getTagHijoByName("cfdi:Impuestos");

                                                //Tipos de taza
                                                cfdi_traslados = cfdi_Impuestos.getTagHijoByName("cfdi:Traslados");

                                                cfdi_traslado_hijo = cfdi_traslados.getTagHijoByName("cfdi:Traslado");

                                                try {
                                                    tipoTasa = cfdi_traslado_hijo.getValorDeAtributo("TasaOCuota");

                                                    //TasaOCuota puede aparecer tambien como tasa: De momento continuar sin tomarlo en cuenta
                                                    if (tipoTasa.equals("0.160000") || tipoTasa.equals("0.16")) {
                                                        tipoTasa = "0.160000";
                                                    }
                                                    switch (tipoTasa) {
                                                        case "0.000000":
                                                            //verificar los valores
                                                            infoXml.setBaseCero(String.valueOf(baseCeroSuma));
                                                            infoXml.setIva("0");
                                                            infoXml.setBase16("0");
                                                            infoXml.setCuotaCompensatoria("0");
                                                            infoXml.setDato0(1);
                                                            infoXml.setDato16(0);
                                                            baseCeroSuma = 0;
                                                            traslado = "";

                                                            break;
                                                        case "0.160000":
                                                            //Pendiente ajustar esto
                                                            base16 = cfdi_Impuestos.getValorDeAtributo("totalImpuestosTrasladados");
                                                            calIva = Double.parseDouble(base16);
                                                            calIva = calIva * 0.16;
                                                            iva = String.valueOf(calIva);
                                                            infoXml.setIva(iva);
                                                            infoXml.setBase16(base16);
                                                            infoXml.setBaseCero("0");
                                                            infoXml.setCuotaCompensatoria("0");

                                                            calIva = base_16 * 0.16;
                                                            iva = String.valueOf(calIva);
                                                            infoXml.setIva(iva);
                                                            infoXml.setBase16(String.valueOf(base_16));
                                                            infoXml.setBaseCero("0");
                                                            infoXml.setDato0(0);
                                                            infoXml.setDato16(1);
                                                            base_16 = 0;
                                                            traslado = "";
                                                            break;
                                                        case "0.090000":
                                                            cuotaC = cfdi_traslado_hijo.getValorDeAtributo("importe");
                                                            infoXml.setCuotaCompensatoria(cuotaC);
                                                            infoXml.setIva("0");
                                                            infoXml.setBase16("0");
                                                            infoXml.setBaseCero("0");
                                                            infoXml.setDato0(0);
                                                            infoXml.setDato16(0);

                                                            break;
                                                        //las retenciones estan dentro de impuestos, pero no dentro de traslado
                                                        //iva=base16*0.16
                                                    }
                                                } catch (AtributoNotFoundException e) {
                                                    infoXml.setIva("0");
                                                    infoXml.setBase16("0");
                                                    infoXml.setBaseCero("0");
                                                    infoXml.setCuotaCompensatoria("0");
                                                    Logger.getLogger(IvaAcredController.class.getName()).log(Level.SEVERE, null, e);

                                                }

                                                break;
                                            default:
                                                break;
                                        }
                                    }

                                    infoXml.setCodigoEmpresa(listFicherosPolizaBase.get(p).getEmpresa());
                                    infoXml.setNombreArchivoXml(listFicherosPolizaBase.get(p).getNombreXml());
                                    infoXml.setNumeroPoliza(listFicherosPolizaBase.get(p).getNumeroPoliza());
                                    infoXml.setTipoPoliza(listFicherosPolizaBase.get(p).getTipoPoliza());
                                    infoXml.setCuenta(listFicherosPolizaBase.get(p).getCuenta());
                                    infoXml.setFechaPago(listFicherosPolizaBase.get(p).getFechaPago());
                                    infoXml.setIdDoctoDig(listFicherosPolizaBase.get(p).getIdDoctodig());
                                    infoXml.setCuentaCoi(listFicherosPolizaBase.get(p).getNumCuentaCoi());
                                    infoXml.setMontoMov(listFicherosPolizaBase.get(p).getMontoMov());
                                    infoXml.setDebe_haber(listFicherosPolizaBase.get(p).getDebe_haber());
                                    infoXml.setConXml("1");

                                    datosXml.add(infoXml);

                                } catch (SAXException | AtributoNotFoundException | TagHijoNotFoundException e) {
                                    Logger.getLogger(IvaAcredController.class.getName()).log(Level.SEVERE, null, e);
                                }
                            }

                        } catch (NullPointerException | IOException | ParserConfigurationException ex) {
                            Logger.getLogger(IvaAcredController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                case 2:
                    XmlDatos infoXml = new XmlDatos();
                    infoXml.setFechaFactura("");
                    infoXml.setFolioInterno("");
                    infoXml.setTotal("0");
                    infoXml.setFormaPago("");
                    infoXml.setRfc("");
                    infoXml.setRelacion("");
                    infoXml.setProveedor("");
                    infoXml.setConceptoXml("");
                    infoXml.setRetencionCuatro("0");
                    infoXml.setRetencionDiez("0");
                    infoXml.setRetencion1016("0");
                    infoXml.setFolioFiscal("");
                    infoXml.setCodigoEmpresa("");
                    infoXml.setNombreArchivoXml("");
                    infoXml.setCuotaCompensatoria("");
                    infoXml.setIva("0");
                    infoXml.setBase16("0");
                    infoXml.setBaseCero("0");
                    infoXml.setNumeroPoliza(listFicherosPolizaBase.get(p).getNumeroPoliza());
                    infoXml.setTipoPoliza(listFicherosPolizaBase.get(p).getTipoPoliza());
                    infoXml.setCuenta(listFicherosPolizaBase.get(p).getCuenta());
                    infoXml.setFechaPago(listFicherosPolizaBase.get(p).getFechaPago());
                    infoXml.setCuentaCoi(listFicherosPolizaBase.get(p).getNumCuentaCoi());
                    infoXml.setMontoMov(listFicherosPolizaBase.get(p).getMontoMov());
                    infoXml.setDebe_haber(listFicherosPolizaBase.get(p).getDebe_haber());
                    infoXml.setIdDoctoDig("");
                    infoXml.setConXml("0");
                    datosXml.add(infoXml);
                    break;
                default:
                    break;
            }

        }

        return datosXml;
    }

    /**
     * Funcion que obtiene un Periodo [int] y un ejercicio [int] y solicitar los
     * datos a la base.
     *
     * @param periodo int
     * @param ejercicio int
     * @param numEmpresa int
     * @param tipoSolicitud boolean
     * @return List PolizaDatos
     */
    public List<PolizaDatos> solicitudPolizaDatos(int periodo, int ejercicio, int numEmpresa, boolean tipoSolicitud) {
        //CHECAR EL PROCESO POR SECCIONES[PRIMERO BASE DE DATOS]
        consultas = new Consultas();
        polizaDat = new ArrayList<>();
        periodo += 1;
        String base_empresa = "";
        String cuentaBanco = "";
        base_empresa = COI_ADSTICSA;
        String[] cuentasBanco = {BANCOMER_ADS_2678, SANTANDER2_ADS_6082, SANTANDER3_ADS_5170, SANTANDER_ADS_2399, BANORTE2_ADS_7202, BANORTE_ADS_0212};
        //Todas
        if (tipoSolicitud) {
            //SIn procesar
            switch (numEmpresa) {
                case 0:
                    if (periodo > 0 && ejercicio >= 2016) {
                        polizaDat= consultas.polizasPeriodoEjercicio_Adsticsa(periodo, ejercicio, cuentasBanco,
                                (numEmpresa + 1), base_empresa, tipoSolicitud);
                    }
                    break;
                case 1:
                    base_empresa = COI_AGRO;
                    cuentaBanco = CUENTA_BANCOMER_AGRO;
                    if (periodo > 0 && ejercicio >= 2016) {
                        //LLenar con la información de la base de datos
                        polizaDat = consultas.polizasPeriodoEjercicio_Agroecologia(periodo, ejercicio, cuentaBanco, (numEmpresa + 1), base_empresa, tipoSolicitud);
                    }
                    break;
                default:
                    break;
            }

        } else {
            //Todas
            switch (numEmpresa) {
                case 0:
                    if (periodo > 0 && ejercicio >= 2016) {
                        polizaDat = consultas.polizasPeriodoEjercicio_Adsticsa(periodo, ejercicio, cuentasBanco,
                                (numEmpresa + 1), base_empresa, false);
                    }
                    break;
                case 1:
                    base_empresa = COI_AGRO;
                    cuentaBanco = CUENTA_BANCOMER_AGRO;
                    if (periodo > 0 && ejercicio >= 2016) {
                        //LLenar con la información de la base de datos
                        polizaDat = consultas.polizasPeriodoEjercicio_Agroecologia(periodo, ejercicio, cuentaBanco, (numEmpresa + 1), base_empresa, false);
                    }
                    break;
                default:
                    break;
            }

        }
        //Intentar mientras solo con agro, despues con adstisa (lo de las cuentas)
        //A partir de que mes y año

        return polizaDat;
    }

    /**
     * Funcion booleana que verifica que la url de un directorio contenga en su
     * interior al menos un archivo XML
     *
     * @param url
     * @return boolean
     */
    public boolean validarArchivosCarpeta(String url) {
        boolean vacio = false;
        int numeroArchivos = 0;
        JespXML xmlCarpeta = new JespXML(new File(url));
        File[] archivos = xmlCarpeta.listFiles();
        if (archivos != null) {
            if (archivos.length > 0) {
                for (File xmlArchivo : archivos) {
                    if (xmlArchivo.isFile() && (xmlArchivo.getName().endsWith(".xml")
                            || xmlArchivo.getName().endsWith(".XML"))) {
                        numeroArchivos++;
                    }
                }
            }
            if (numeroArchivos > 0) {
                vacio = true;
            }
        }
        return vacio;
    }

    /**
     * Funcion que retorna la lista de conceptos de relacion de la base de datos
     *
     * @param empresa
     * @return
     */
    public List<String> obtenerConceptosRelacion(int empresa) {
        String db = "";
        List<String> listConceptos = new ArrayList<>();
        consultas = new Consultas();
        if (empresa == 0) {
            db = "COI80Empre1";
            listConceptos = consultas.consultarConceptosRelacion(db);
        } else {
            db = "COI80Empre2";
            listConceptos = consultas.consultarConceptosRelacion(db);
        }
        return listConceptos;
    }

    /**
     * Funcion que valida que una lista de PolizaProcesada no este vacia y
     * separa el no de empresa
     *
     * @param datosPolizaProcesada
     * @param numeroEmpresa
     * @return boolean
     */
    public boolean verificadorProcesadoPoliza(List<PolizaProcesada> datosPolizaProcesada, String numeroEmpresa) {
        int ne = Integer.parseInt(numeroEmpresa);
        boolean exito = false;
        consultas = new Consultas();
        if (!datosPolizaProcesada.isEmpty()) {
            exito = consultas.insertarPolizasProcesadas(datosPolizaProcesada, ne);
        }
        return exito;
    }

    //    public List<XmlDatos> listDatosXmlCienAcred(String URL) {
//        String nameArchivo;
//        //Falta checar que los xml esten separados por fecha, y filtrar solo los que se necesitan realmente
//        //DecimalFormat formateador = new DecimalFormat("0.00");
//
//        /*
//        IMPORTANTE: PARA QUE EL SISTEMA FUNCIONE DE MANERA REMOTA, SE DEBE TENER GUARDARA LA CONTRASEÑA Y USUARIO DEL HOST DONDE SE QUIERE ACCEDER
//         */
//        //url de la carpeta del xml
//        if (!URL.isEmpty() || !URL.equals("")) {
//            try {
//                //Tomando la ruta de la carpeta
//                JespXML xmlCarpeta = new JespXML(URL);
//
//                //Aqui se podria checar el # de archivos disponibles
//                if (!xmlCarpeta.getAbsolutePath().isEmpty()) {
//                    try {
//                        //verificando que sean archivos xml
//
//                        //Obteniendo la ruta del archivo
//                        JespXML fileXml = xmlCarpeta;
//
//                        XmlDatos infoXml = new XmlDatos();
//                        StringBuilder valoresConcepto = new StringBuilder();
//                        String formaDePagoDescripcion = "";
//                        //raiz de los archivos
//                        raizXml = fileXml.leerXML();
//
//                        //Atributos de la raiz
//                        String numCertificado = raizXml.getValorDeAtributo("NoCertificado");
//
//                        fechaFactura = raizXml.getValorDeAtributo("Fecha");
//                        infoXml.setFechaFactura(fechaFactura);
//                        try {
//                            folioInterno = raizXml.getValorDeAtributo("Folio");
//                        } catch (AtributoNotFoundException e) {
//                            folioInterno = " ";
//                        }
//
//                        infoXml.setFolioInterno(folioInterno);
//                        //total=(base0+base16)-(retencion4+retencion10+retencion1067)+(CP+IVA)total
//                        total = raizXml.getValorDeAtributo("Total");
//                        infoXml.setTotal(total);
//
//                        try {
//                            formaPago = raizXml.getValorDeAtributo("FormaPago");
//                            switch (formaPago) {
//                                case "01":
//                                    formaDePagoDescripcion = "Efectivo";
//                                    break;
//                                case "02":
//                                    formaDePagoDescripcion = "Cheque nominativo";
//                                    break;
//                                case "03":
//                                    formaDePagoDescripcion = "Transferencia electrónica de fondos";
//                                    break;
//                                case "04":
//                                    formaDePagoDescripcion = "Tarjeta de crédito";
//                                    break;
//                                case "05":
//                                    formaDePagoDescripcion = "Monedero electrónico";
//                                    break;
//                                case "06":
//                                    formaDePagoDescripcion = "Dinero electrónico";
//                                    break;
//                                case "08":
//                                    formaDePagoDescripcion = "Vales de despensa";
//                                    break;
//                                case "12":
//                                    formaDePagoDescripcion = "Dación en pago";
//                                    break;
//                                case "13":
//                                    formaDePagoDescripcion = "Pago por subrogación";
//                                    break;
//                                case "14":
//                                    formaDePagoDescripcion = "Pago por consignación";
//                                    break;
//                                case "15":
//                                    formaDePagoDescripcion = "Condonación";
//                                    break;
//                                case "17":
//                                    formaDePagoDescripcion = "Compensación";
//                                    break;
//                                case "23":
//                                    formaDePagoDescripcion = "Novación";
//                                    break;
//                                case "24":
//                                    formaDePagoDescripcion = "Confusión";
//                                    break;
//                                case "25":
//                                    formaDePagoDescripcion = "Remisión de deuda";
//                                    break;
//                                case "26":
//                                    formaDePagoDescripcion = "Prescripción o caducidad";
//                                    break;
//                                case "27":
//                                    formaDePagoDescripcion = "A satisfacción del acreedor";
//                                    break;
//                                case "28":
//                                    formaDePagoDescripcion = "Tarjeta de débito";
//                                    break;
//                                case "29":
//                                    formaDePagoDescripcion = "Tarjeta de servicios";
//                                    break;
//                                case "30":
//                                    formaDePagoDescripcion = "Aplicación de anticipos";
//                                    break;
//                                case "31":
//                                    formaDePagoDescripcion = "Intermediario pagos";
//                                    break;
//                                case "99":
//                                    formaDePagoDescripcion = "Por definir";
//                                    break;
//                                default:
//                                    break;
//
//                            }
//                        } catch (AtributoNotFoundException e) {
//                            formaDePagoDescripcion = "";
//                        }
//                        infoXml.setFormaPago(formaDePagoDescripcion);
//
//                        //tomando todos las etiquetas de un xml y guardandolas en una lista
//                        cfdi_Comprobante = raizXml.getTagsHijos();
//
//                        for (int i = 0; i < cfdi_Comprobante.size(); i++) {
//                            //Obteniendo todas las etiquetas hijo de la raiz
//                            String nombreEtiqueta = cfdi_Comprobante.get(i).toString();
//                            //Buscando el la sub-etiqueta deceada
//                            switch (nombreEtiqueta) {
//                                case "<cfdi:Emisor>":
//                                    cfdi_Emisor = raizXml.getTagHijoByName("cfdi:Emisor");
//                                    rfc = cfdi_Emisor.getValorDeAtributo("Rfc");
//
//                                    infoXml.setRfc(rfc);
//                                    try {
//                                        proveedor = cfdi_Emisor.getValorDeAtributo("Nombre");
//                                    } catch (AtributoNotFoundException e) {
//                                        proveedor = "";
//                                    }
//                                    infoXml.setProveedor(proveedor);
//
//                                    break;
//                                case "<cfdi:Conceptos>":
//                                    List<Tag> cfdi_Conceptos;
//                                    List<Tag> retenciones;
//                                    //Obteniendo hijos de cfdi:Conceptos
//                                    cfdi_Conceptos = cfdi_Comprobante.get(i).getTagsHijos();
//
//                                    for (int j = 0; j < cfdi_Conceptos.size(); j++) {
//                                        //obteniendo el la primera sub-etiqueta de <cfdi:Conceptos>
//                                        cfdi_Concepto_h = cfdi_Conceptos.get(j);
//
//                                        //Cadena del concepto
//                                        if (valoresConcepto.toString().isEmpty()) {
//
//                                            valoresConcepto.append(cfdi_Concepto_h.getValorDeAtributo("Descripcion"));
//
//                                        }
//                                        if (!valoresConcepto.toString().isEmpty() && (numCertificado.equals(raizXml.getValorDeAtributo("NoCertificado")))) {
//                                            if (!valoresConcepto.toString().equals(cfdi_Concepto_h.getValorDeAtributo("Descripcion"))) {
//                                                valoresConcepto.append(". ");
//                                                valoresConcepto.append(cfdi_Concepto_h.getValorDeAtributo("Descripcion"));
//                                            }
//                                        }
//
//                                        cfdi_ConceptoImpuestos = cfdi_Concepto_h.getTagHijoByName("cfdi:Impuestos");
//                                        //Aqui impuestos traslado
//                                        cfdi_traslados = cfdi_ConceptoImpuestos.getTagHijoByName("cfdi:Traslados");
//                                        cfdi_traslado_hijo = cfdi_traslados.getTagHijoByName("cfdi:Traslado");
//                                        String traslado = cfdi_traslado_hijo.getValorDeAtributo("Base");
//
//                                        //Retenciones
//                                        try {
//
//                                            //Retenciones
//                                            cfdi_Retenciones = cfdi_ConceptoImpuestos.getTagHijoByName("cfdi:Retenciones");
//                                            retenciones = cfdi_Retenciones.getTagsHijos();
//                                            for (int k = 0; k < retenciones.size(); k++) {
//                                                String tipoRetencion = "";
//                                                cfdi_retencion_h = retenciones.get(k);
//                                                tipoRetencion = cfdi_retencion_h.getValorDeAtributo("TasaOCuota");
//                                                switch (tipoRetencion) {
//                                                    case "0.040000":
//                                                        if (retencionCuatro.isEmpty()) {
//                                                            retencionCuatro = cfdi_retencion_h.getValorDeAtributo("Importe");
//                                                        }
//                                                        break;
//                                                    case "0.100000":
//                                                        if (retencionDiez.isEmpty()) {
//                                                            retencionDiez = cfdi_retencion_h.getValorDeAtributo("Importe");
//                                                        }
//                                                        break;
//                                                    case "0.106700":
//                                                        if (retencion1016.isEmpty()) {
//                                                            retencion1016 = cfdi_retencion_h.getValorDeAtributo("Importe");
//                                                        }
//                                                        break;
//                                                    default:
//                                                        break;
//                                                }
//                                            }
//                                        } catch (TagHijoNotFoundException | NullPointerException e) {
//                                            Logger.getLogger(IvaAcredController.class.getName()).log(Level.SEVERE, null, e);
//                                        }
//
//                                    }
//
//                                    //Descripcion:Concepto
//                                    infoXml.setConceptoXml(valoresConcepto.toString());
//                                    //Retenciones
//                                    infoXml.setRetencionCuatro(retencionCuatro);
//                                    infoXml.setRetencionDiez(retencionDiez);
//                                    infoXml.setRetencion1016(retencion1016);
//
//                                    break;
//                                case "<cfdi:Complemento>":
//                                    try {
//                                        cfdi_Complemento = raizXml.getTagHijoByName("cfdi:Complemento");
//                                        tfd_TimbreFiscalDigital = cfdi_Complemento.getTagHijoByName("tfd:TimbreFiscalDigital");
//                                        folioFiscal = tfd_TimbreFiscalDigital.getValorDeAtributo("UUID").toUpperCase();
//                                    } catch (TagHijoNotFoundException e) {
//                                        folioFiscal = "";
//                                    }
//
//                                    infoXml.setFolioFiscal(folioFiscal);
//
//                                    break;
//                                case "<cfdi:Impuestos>":
//
//                                    double calIva = 0;
//                                    String tipoTasa;
//                                    cfdi_Impuestos = raizXml.getTagHijoByName("cfdi:Impuestos");
//
//                                    //Tipos de taza
//                                    cfdi_traslados = cfdi_Impuestos.getTagHijoByName("cfdi:Traslados");
//
//                                    cfdi_traslado_hijo = cfdi_traslados.getTagHijoByName("cfdi:Traslado");
//
//                                    try {
//                                        tipoTasa = cfdi_traslado_hijo.getValorDeAtributo("TasaOCuota");
//
//                                        //TasaOCuota puede aparecer tambien como tasa: De momento continuar sin tomarlo en cuenta
//                                        if (tipoTasa.equals("0.160000") || tipoTasa.equals("0.16")) {
//                                            tipoTasa = "0.160000";
//                                        }
//                                        switch (tipoTasa) {
//                                            case "0.000000":
//                                                baseCero = cfdi_traslado_hijo.getValorDeAtributo("importe");
//                                                infoXml.setBaseCero(baseCero);
//                                                infoXml.setIva("");
//                                                infoXml.setBase16("");
//                                                infoXml.setCuotaCompensatoria("");
//                                                break;
//                                            case "0.160000":
//                                                //Pendiente ajustar esto
//                                                base16 = cfdi_Impuestos.getValorDeAtributo("totalImpuestosTrasladados");
//                                                calIva = Double.parseDouble(base16);
//                                                calIva *= 0.16;
//                                                iva = String.valueOf(calIva);
//                                                infoXml.setIva(iva);
//                                                infoXml.setBase16(base16);
//                                                infoXml.setBaseCero("");
//                                                infoXml.setCuotaCompensatoria("");
//                                                break;
//                                            case "0.090000":
//                                                cuotaC = cfdi_traslado_hijo.getValorDeAtributo("importe");
//                                                infoXml.setCuotaCompensatoria(cuotaC);
//                                                infoXml.setIva("");
//                                                infoXml.setBase16("");
//                                                infoXml.setBaseCero("");
//                                                break;
//                                            //las retenciones estan dentro de impuestos, pero no dentro de traslado
//                                            //iva=base16*0.16
//                                        }
//                                    } catch (AtributoNotFoundException e) {
//                                        Logger.getLogger(IvaAcredController.class.getName()).log(Level.SEVERE, null, e);
//                                    }
//
//                                    break;
//                                default:
//                                    break;
//                            }
//                        }
//                        datosXml.add(infoXml);
//
//                    } catch (SAXException | AtributoNotFoundException | TagHijoNotFoundException e) {
//                        Logger.getLogger(IvaAcredController.class.getName()).log(Level.SEVERE, null, e);
//                    }
//                }
//
//            } catch (NullPointerException | IOException | ParserConfigurationException ex) {
//                Logger.getLogger(IvaAcredController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        return datosXml;
//    }
//Cambiar contraseñas rocketo fermin
    //106931
    //alta de usuarios
    /*
    
     */
}
