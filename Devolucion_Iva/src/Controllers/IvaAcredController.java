/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.Consultas;
import Models.PolizaDatos;
import Models.XmlDatos;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
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
    private Tag cfdi_Impuestos, cfdi_Complemento, cfdi_Concepto_h, tfd_TimbreFiscalDigital, cfdi_Emisor;
    private String fechaFactura, folioFiscal, folioInterno, baseCero, total, base16, rfc, proveedor, formaPago;
    private final List<XmlDatos> datosXml = new ArrayList<>();
    private List<PolizaDatos> polizaDat = new ArrayList<>();
    private List<Tag> cfdi_Comprobante;

    /**
     * Funcion que recibe una url en forma de String, para obtener datos de
     * archivos xml segun los conceptos solicitados
     *
     * @param URL
     * @return List<XmlDatos>
     */
    public List<XmlDatos> listDatosXmlCienAcred(String URL) {
        String nameArchivo;
        //Falta checar que los xml esten separados por fecha, y filtrar solo los que se necesitan realmente
        DecimalFormat formateador = new DecimalFormat("0.00000");
        //url de la carpeta del xml
        if (!URL.isEmpty() || !URL.equals("")) {
            try {
                //Tomando la ruta de la carpeta
                JespXML xmlCarpeta = new JespXML(new File(URL));

                //Array con todos los archivos de la carpeta
                File[] archivos = xmlCarpeta.listFiles();
                //Aqui se podria checar el # de archivos disponibles
                if (archivos != null) {
                    if (archivos.length > 0) {
                        for (File archivo : archivos) {
                            try {
                                //verificando que sean archivos xml
                                if (archivo.isFile() && (archivo.getName().endsWith(".xml") || archivo.getName().endsWith(".XML"))) {
                                    //obteniendo el archivo xml
                                    File oneFile = (File) archivo;
                                    //Obteniendo la ruta del archivo
                                    JespXML fileXml = new JespXML(oneFile.getAbsolutePath());
                                    XmlDatos infoXml = new XmlDatos();
                                    StringBuilder valoresConcepto = new StringBuilder();
                                    String formaDePagoDescripcion="";
                                    //raiz de los archivos
                                    raizXml = fileXml.leerXML();

                                    //Atributos de la raiz
                                    String numCertificado = raizXml.getValorDeAtributo("NoCertificado");

                                    fechaFactura = raizXml.getValorDeAtributo("Fecha");
                                    infoXml.setFechaFactura(fechaFactura);

                                    folioInterno = raizXml.getValorDeAtributo("Folio");
                                    infoXml.setFolioInterno(folioInterno);

                                    baseCero = raizXml.getValorDeAtributo("SubTotal");
                                    infoXml.setBaseCero(baseCero);

                                    total = raizXml.getValorDeAtributo("Total");
                                    infoXml.setTotal(total);

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
                                            break;

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
                                                proveedor = cfdi_Emisor.getValorDeAtributo("Nombre");
                                                infoXml.setRfc(rfc);
                                                infoXml.setProveedor(proveedor);

                                                break;
                                            case "<cfdi:Conceptos>":
                                                List<Tag> cfdi_Conceptos;
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
                                                }
                                                infoXml.setConceptoXml(valoresConcepto.toString());

                                                break;
                                            case "<cfdi:Complemento>":
                                                cfdi_Complemento = raizXml.getTagHijoByName("cfdi:Complemento");
                                                tfd_TimbreFiscalDigital = cfdi_Complemento.getTagHijoByName("tfd:TimbreFiscalDigital");
                                                folioFiscal = tfd_TimbreFiscalDigital.getValorDeAtributo("UUID").toUpperCase();
                                                infoXml.setFolioFiscal(folioFiscal);

                                                break;
                                            case "<cfdi:Impuestos>":
                                                cfdi_Impuestos = raizXml.getTagHijoByName("cfdi:Impuestos");
                                                base16 = cfdi_Impuestos.getValorDeAtributo("totalImpuestosTrasladados");
                                                infoXml.setBase16(base16);
                                                break;
                                            default:
                                                break;
                                        }
                                    }
                                    datosXml.add(infoXml);
                                }

                            } catch (SAXException | AtributoNotFoundException | TagHijoNotFoundException e) {
                                Logger.getLogger(IvaAcredController.class.getName()).log(Level.SEVERE, null, e);
                            }
                        }
                    }
                }

            } catch (NullPointerException | IOException | ParserConfigurationException ex) {
                //AQUI SE GENERA EL PROBLEMA 
                Logger.getLogger(IvaAcredController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return datosXml;
    }

    /**
     * Funcion que obtiene un Periodo [int] y un ejercicio [int] y solicitar los
     * datos a la base.
     *
     * @param periodo
     * @param ejercicio
     * @return List PolizaDatos
     */
    public List<PolizaDatos> solicitudPolizaDatos(int periodo, int ejercicio) {

        consultas = new Consultas();
        polizaDat = new ArrayList<>();
        periodo += 1;

        if (periodo > 0 && ejercicio >= 2017) {
            polizaDat = consultas.polizasPeriodoEjercicio(periodo, ejercicio);
        }
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
                    if (xmlArchivo.isFile() && (xmlArchivo.getName().endsWith(".xml") || xmlArchivo.getName().endsWith(".XML"))) {
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
}
