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
import org.w3c.dom.*;

/**
 *
 * @author Macktronica
 */
public class validacionXml {

    public static void main(String arg[]) throws TagHijoNotFoundException, ParserConfigurationException, SAXException, IOException, AtributoNotFoundException {
        try {
            Tag raizXml;
            Tag hijos, p_Complemento, repetidoss, co_TimbreFiscalD;
            String fechaFactura, folioFiscal, subTotal, total;
            List<String> datosXml = new ArrayList<>();
            //url de la carpeta del xml
            String URI = "C:\\Users\\Macktronica\\Desktop\\PruebasXml";
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
                        //tomando todos las etiquetas de un xml y guardandolas en una lista
                        List<Tag> hijosTag = raizXml.getTagsHijos();

                        //recorriendo la lista de etiquetas
                        for (int i = 0; i < hijosTag.size(); i++) {
                            Tag etiquet = hijosTag.get(i);
                            //Si se encuentra la etiqueta deceada
                            if ("<comida>".equals(etiquet.toString())) {
                                Tag nombre = etiquet.getTagHijoByName("nombre");
                                String valorNombre = nombre.getValorDeAtributo("Importe");
                                System.out.println("importe: " + valorNombre);
                            } else {
                                //otras etiquetas
                                Tag nombre = etiquet.getTagHijoByName("precio");
                                System.out.println("valor oe: " + nombre.getContenido());
                            }
                        }
                    }
                }
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            //exception lanzada cuando no se encuentra el atributo
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
