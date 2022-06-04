/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;



/**
 *
 * @author sanch
 */
public class ConexionDB {
    Connection conectar = null;
    String usuario = "root";
    String contrasenia = "Celefronm12";
    String bd = "bancociudadtoxina";
    String puerto = "3307";
    String driver = "com.mysql.cj.jdbc.Driver";
    String cadena = "jdbc:mysql://localhost:3306/";
    
    public Connection establecerConexion(){
        try{
            Class.forName(driver);
            conectar = DriverManager.getConnection(cadena+bd, usuario, contrasenia);
            
        }
        catch(Exception e){
            
        }
        return conectar;
    }   
}
