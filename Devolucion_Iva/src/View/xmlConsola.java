/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

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
import sun.security.tools.keytool.Main;

/**
 *
 * @author Macktronica
 */
public class xmlConsola {

    public static void main(String arg[]) throws TagHijoNotFoundException {
        try {
            Tag raizXml;
            Tag p_Conceptos, p_Complemento, c_Concepto, co_TimbreFiscalD;
            String fechaFactura, folioFiscal, subTotal, total;
            List<String> datosXml = new ArrayList<>();
            //url de la carpeta del xml
            String URI = "C:\\Users\\Macktronica\\Desktop\\xmlConcentrado";
            //Tomando la ruta de la carpeta
            JespXML xmlCarpeta = new JespXML(new File(URI));
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
                        //procesos del xml
                        /*
                        Validaciones:
                            Si existe la etiqueta
                            Si el atributo no esta vacio
                            Si existe 2 veces la misma etiqueta
                        */
                        raizXml = fileXml.leerXML();
                        
                        p_Complemento = raizXml.getTagHijoByName("cfdi:Complemento");
                        co_TimbreFiscalD = p_Complemento.getTagHijoByName("tfd:TimbreFiscalDigital");
                        //Concepto
                        p_Conceptos = raizXml.getTagHijoByName("cfdi:Conceptos");
                        c_Concepto = p_Conceptos.getTagHijoByName("cfdi:Concepto");

                        //Valores raiz
                        fechaFactura = raizXml.getValorDeAtributo("Fecha");
                        subTotal = raizXml.getValorDeAtributo("SubTotal");
                        total = raizXml.getValorDeAtributo("Total");
                        folioFiscal = co_TimbreFiscalD.getValorDeAtributo("UUID");

                        //Concepto Segun Xml
                        //VALIDAD QUE TODOS EXISTAN
                        StringBuilder valoresConcepto = new StringBuilder("Importe= \"");
                        valoresConcepto.append(c_Concepto.getValorDeAtributo("Importe"));
                        valoresConcepto.append("\" ValorUnitario=\"");
                        valoresConcepto.append(c_Concepto.getValorDeAtributo("ValorUnitario"));
                        valoresConcepto.append("00\" Descripcion=\"");
                        valoresConcepto.append(c_Concepto.getValorDeAtributo("Descripcion"));
                        valoresConcepto.append("\" Unidad=\"");
                        valoresConcepto.append(c_Concepto.getValorDeAtributo("Unidad"));
                        valoresConcepto.append("\" Cantidad=\"");
                        valoresConcepto.append(c_Concepto.getValorDeAtributo("Cantidad"));
                        valoresConcepto.append(".00000\"");

                        //Llenando lista
                        datosXml.add(fechaFactura);
                        datosXml.add(folioFiscal);
                        datosXml.add(valoresConcepto.toString());
                        datosXml.add(subTotal);
                        datosXml.add(total);

                        //recorriendo lista
                        datosXml.forEach((string) -> {
                            System.out.println(string);
                            System.out.println("\n");
                        });

                    }
                }
            }

        } catch (TagHijoNotFoundException | NullPointerException | AtributoNotFoundException | ParserConfigurationException | SAXException | IOException ex) {
            //exception lanzada cuando no se encuentra el atributo
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }        //exception lanzada cuando no se encuentra el tag hijo

    }
}
