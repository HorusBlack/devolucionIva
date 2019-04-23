/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

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
public class xmlConcentrado {

    public static void main(String args[]) throws ParserConfigurationException, TagHijoNotFoundException {
        Tag raizXml, et_Concepto;
        Tag p_Conceptos, p_Complemento, c_Concepto, co_TimbreFiscalD;
        String fechaFactura, folioFiscal, subTotal, total;
        List<String> datosXml = new ArrayList<>();
        List<Tag> listEtiquetas;
        DecimalFormat formateador = new DecimalFormat("0.00000");
        //url de la carpeta del xml
        String URI = "C:\\Users\\Macktronica\\Desktop\\xmlConcentrado";
        String url2 = "C:\\Users\\Macktronica\\Desktop\\Dac-Pruebas\\01 Enero";
        String url3 = "C:\\Users\\Macktronica\\Desktop\\Dac-Unico";
        try {
            //Tomando la ruta de la carpeta
            JespXML xmlCarpeta = new JespXML(new File(url2));
            //Array con todos los archivos de la carpeta
            File[] archivos = xmlCarpeta.listFiles();
            if (archivos.length > 0) {
                for (File archivo : archivos) {
                    //verificando que sean archivos xml
                    if (archivo.isFile() && archivo.getName().endsWith(".xml")) {
                        //obteniendo el archivo xml
                        File oneFile = (File) archivo;
                        //Obteniendo la ruta del archivo
                        JespXML fileXml = new JespXML(oneFile.getAbsolutePath());
                        StringBuilder valoresConcepto = new StringBuilder();
                        //raiz de los archivos
                        raizXml = fileXml.leerXML();

                        //Atributos de la raiz
                        String numCertificado = raizXml.getValorDeAtributo("NoCertificado");
                        fechaFactura = raizXml.getValorDeAtributo("Fecha");
                        subTotal = raizXml.getValorDeAtributo("SubTotal");
                        total = raizXml.getValorDeAtributo("Total");

                        datosXml.add(fechaFactura);
                        datosXml.add(subTotal);
                        datosXml.add(total);

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
                                    datosXml.add(valoresConcepto.toString());
                                    break;
                                case "<cfdi:Complemento>":
                                    p_Complemento = raizXml.getTagHijoByName("cfdi:Complemento");
                                    co_TimbreFiscalD = p_Complemento.getTagHijoByName("tfd:TimbreFiscalDigital");
                                    folioFiscal = co_TimbreFiscalD.getValorDeAtributo("UUID").toUpperCase();
                                    datosXml.add(folioFiscal);
                                    break;
                                default:
                                    break;
                            }
                        }

                        datosXml.forEach((string) -> {
                            System.out.println(string);
                            System.out.println("\n");
                        });
                    }
                }
            }
        } catch (SAXException | AtributoNotFoundException e) {
            System.out.println("error: " + e);
        } catch (NullPointerException | IOException | ParserConfigurationException ex) {
            System.out.println("null: " + ex);
        }

    }
}
