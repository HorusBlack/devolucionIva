/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Macktronica
 */
public class ConexionDB {

    Connection cn = null;
    //Pendiente cambiar datos de conexion
    
    public Connection Entrar(String dataBase) throws SQLException, ClassNotFoundException {
        try {
            //Servidor Agro
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://25.62.86.238\\BASES;databaseName=" + dataBase + ";";
            String usuarioDB = "Usuario";
            String passwordDB = "0000";
            cn = DriverManager.getConnection(url, usuarioDB, passwordDB);
        } catch (SQLException | ClassNotFoundException e) {
            cn = null;
            JOptionPane.showMessageDialog(null, "No se pudo conectar con el servidor\n intentelo nuevamente por favor");
        }
        return cn;
    }

    public static void Salir(Connection conex) {
        try {
            conex.close();
        } catch (SQLException e) {
            System.out.println("Error conexion: " + e);
        }
    }
}
