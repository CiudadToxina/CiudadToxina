/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicadenegocios;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BitacoraCSV extends BitacoraObserver{
    
    public BitacoraCSV(Bitacora pBitacora){
        bitacora = pBitacora;
        bitacora.agregarObservador(this);
    }
    
    @Override
    public void generarBitacora(){
        try (PrintWriter csvWriter = new PrintWriter("C:\\Users\\vjiuc\\Desktop\\SegundoProyectoDS\\bitacora.csv")) {
            String dataHeaders = "FechaYHora,Accion,Vista";
            csvWriter.println(dataHeaders);
            for (Registro registro : bitacora.getRegistrosFiltrados()){
                String fila = registro.getFechaHoraBitacora() + "," + registro.getTipoAccion() + "," + registro.getTipoVista();
                csvWriter.println(fila);
            }   
        }
        catch (IOException ex) {
            System.out.println("chale vuhleh");
        }            
    }       
}
