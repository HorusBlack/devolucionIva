/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import java.io.File;

/**
 *
 * @author Macktronica
 */
public class Recoleccion {

    public void listarArchivos(String ruta) throws NullPointerException{
        File dir;
        dir = new File(ruta);
        String[] ficheros = dir.list();
        if (ficheros == null) {
            System.out.println("No hay ficheros en el directorio especificado");
        } else {
            for (String fichero : ficheros) {
                System.out.println("[Ficheros]: " + fichero.length());
            }          
        }
    }
}
