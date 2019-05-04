/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.Consultas;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
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
    private Tag p_Conceptos, p_Complemento, c_Concepto, co_TimbreFiscalD;
    private String fechaFactura, folioFiscal, subTotal, total;
    private final List<XmlDatos> datosXml = new ArrayList<>();
    private List<PolizaDatos> polizaDat = new ArrayList<>();
    private List<Tag> listEtiquetas;

    /**
     * Funcion que recibe una url en forma de String, para obtener datos de
     * archivos xml segun los conceptos solicitados
     *
     * @param URL
     * @return List<XmlDatos>
     */
    public List<XmlDatos> datosDevolucionIva(String URL) {
        //Falta checar que los xml esten separados por fecha, y filtrar solo los que se necesitan realmente
        DecimalFormat formateador = new DecimalFormat("0.00000");
        //url de la carpeta del xml
        if (!URL.isEmpty() || !URL.equals("")) {
            try {
                //Tomando la ruta de la carpeta
                JespXML xmlCarpeta = new JespXML(new File(URL));

                //Array con todos los archivos de la carpeta
                File[] archivos = xmlCarpeta.listFiles();
                if (archivos != null) {
                    if (archivos.length > 0) {
                        for (File archivo : archivos) {
                            //verificando que sean archivos xml
                            if (archivo.isFile() && (archivo.getName().endsWith(".xml") || archivo.getName().endsWith(".XML"))) {
                                //obteniendo el archivo xml
                                File oneFile = (File) archivo;
                                //Obteniendo la ruta del archivo
                                JespXML fileXml = new JespXML(oneFile.getAbsolutePath());
                                XmlDatos infoXml = new XmlDatos();
                                StringBuilder valoresConcepto = new StringBuilder();
                                //raiz de los archivos
                                raizXml = fileXml.leerXML();

                                //Atributos de la raiz
                                String numCertificado = raizXml.getValorDeAtributo("NoCertificado");
                                fechaFactura = raizXml.getValorDeAtributo("Fecha");
                                infoXml.setFechaFactura(fechaFactura);
                                subTotal = raizXml.getValorDeAtributo("SubTotal");
                                infoXml.setSubTotal(subTotal);
                                total = raizXml.getValorDeAtributo("Total");
                                infoXml.setTotal(total);

                                //tomando todos las etiquetas de un xml y guardandolas en una lista
                                listEtiquetas = raizXml.getTagsHijos();

                                for (int i = 0; i < listEtiquetas.size(); i++) {
                                    String nombreEtiqueta = listEtiquetas.get(i).toString();

                                    switch (nombreEtiqueta) {
                                        case "<cfdi:Conceptos>":
                                            List<Tag> multiConceptos;
                                            multiConceptos = listEtiquetas.get(i).getTagsHijos();

                                            for (int j = 0; j < multiConceptos.size(); j++) {
                                                c_Concepto = multiConceptos.get(j);

                                                String valUnidad, claveUnidad, claveProvServ;

                                                //validando que existan atributos que pueden o no estar en el xml
                                                try {
                                                    valUnidad = c_Concepto.getValorDeAtributo("Unidad");
                                                } catch (AtributoNotFoundException ex) {
                                                    valUnidad = "N/A";
                                                }

                                                try {
                                                    claveUnidad = c_Concepto.getValorDeAtributo("ClaveUnidad");
                                                } catch (AtributoNotFoundException ex) {
                                                    claveUnidad = "";
                                                }

                                                try {
                                                    claveProvServ = c_Concepto.getValorDeAtributo("ClaveProdSer");
                                                } catch (AtributoNotFoundException ex) {
                                                    claveProvServ = "";
                                                }

                                                //Cadena del concepto
                                                if (valoresConcepto.toString().isEmpty()) {
                                                    valoresConcepto.append("Importe=\"");
                                                    valoresConcepto.append(c_Concepto.getValorDeAtributo("Importe"));
                                                    valoresConcepto.append("\" ValorUnitario=\"");
                                                    valoresConcepto.append(formateador.format(Double.parseDouble(c_Concepto.getValorDeAtributo("ValorUnitario"))));
                                                    valoresConcepto.append("\" Descripción=\"");
                                                    valoresConcepto.append(c_Concepto.getValorDeAtributo("Descripcion"));
                                                    if (!claveUnidad.equals("")) {
                                                        valoresConcepto.append("\" ClaveUnidad=\"");
                                                        valoresConcepto.append(claveUnidad.toUpperCase());
                                                    }
                                                    if (!claveProvServ.equals("")) {
                                                        valoresConcepto.append("\" ClaveProvServ=\"");
                                                        valoresConcepto.append(claveProvServ.toUpperCase());
                                                    }
                                                    valoresConcepto.append("\" Unidad=\"");
                                                    valoresConcepto.append(valUnidad.toUpperCase());
                                                    valoresConcepto.append("\" Cantidad=\"");
                                                    valoresConcepto.append(formateador.format(Double.parseDouble(c_Concepto.getValorDeAtributo("Cantidad"))));
                                                    valoresConcepto.append("\"");
                                                }
                                                if (!valoresConcepto.toString().isEmpty() && (numCertificado.equals(raizXml.getValorDeAtributo("NoCertificado")))) {
                                                    valoresConcepto.append("\nImporte=\"");
                                                    valoresConcepto.append(c_Concepto.getValorDeAtributo("Importe"));
                                                    valoresConcepto.append("\" ValorUnitario=\"");
                                                    valoresConcepto.append(formateador.format(Double.parseDouble(c_Concepto.getValorDeAtributo("ValorUnitario"))));
                                                    valoresConcepto.append("\" Descripción=\"");
                                                    valoresConcepto.append(c_Concepto.getValorDeAtributo("Descripcion"));
                                                    if (!claveUnidad.equals("")) {
                                                        valoresConcepto.append("\" ClaveUnidad=\"");
                                                        valoresConcepto.append(claveUnidad.toUpperCase());
                                                    }
                                                    if (!claveProvServ.equals("")) {
                                                        valoresConcepto.append("\" ClaveProvServ=\"");
                                                        valoresConcepto.append(claveProvServ.toUpperCase());
                                                    }
                                                    valoresConcepto.append("\" Unidad=\"");
                                                    valoresConcepto.append(valUnidad.toUpperCase());
                                                    valoresConcepto.append("\" Cantidad=\"");
                                                    valoresConcepto.append(formateador.format(Double.parseDouble(c_Concepto.getValorDeAtributo("Cantidad"))));
                                                    valoresConcepto.append("\"");
                                                }
                                            }
                                            infoXml.setConceptoXml(valoresConcepto.toString());

                                            break;
                                        case "<cfdi:Complemento>":
                                            p_Complemento = raizXml.getTagHijoByName("cfdi:Complemento");
                                            co_TimbreFiscalD = p_Complemento.getTagHijoByName("tfd:TimbreFiscalDigital");
                                            folioFiscal = co_TimbreFiscalD.getValorDeAtributo("UUID").toUpperCase();
                                            infoXml.setFolioFiscal(folioFiscal);

                                            break;
                                        default:
                                            break;
                                    }
                                }
                                datosXml.add(infoXml);
                            }

                        }
                    }
                }

            } catch (SAXException | AtributoNotFoundException | TagHijoNotFoundException e) {
                System.out.println("error IvaAcredController: " + e);
            } catch (NullPointerException | IOException | ParserConfigurationException ex) {
                //AQUI SE GENERA EL PROBLEMA 
                System.out.println("null IvaAcredController: " + ex);

            }

        }
        return datosXml;
    }
    
    /**
     * Funcion que obtiene un Periodo [int] y un ejercicio [int] y solicitar los datos
     * a la base.
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
     * Funcion booleana que verifica que la url de un directorio contenga en su interior
     * al menos un archivo XML
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
