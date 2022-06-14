/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicadenegocios;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BitacoraTramaPlana extends BitacoraObserver{
    
    public BitacoraTramaPlana(Bitacora pBitacora){
        bitacora = pBitacora;
        bitacora.agregarObservador(this);
    }
    
    @Override
    public void generarBitacora(){
        
        try {
            FileWriter bitacoraTramaPlana = new FileWriter("C:\\Users\\sanch\\Documents\\GitHub\\CiudadToxina\\BitacoraTramaPlana.txt");
            PrintWriter escritor = new PrintWriter(bitacoraTramaPlana);
            
            escritor.println("---Fecha y hora---/--------Tipo de acción-------/Tipo de vista");
 
            for(Registro registro : bitacora.getRegistrosFiltrados()){
                String fila = registro.getFechaHoraBitacora().toString() + registro.getTipoAccion() + registro.getTipoVista();
                escritor.println(fila);
            }
            
            bitacoraTramaPlana.close();
            
        } catch (IOException ex) {
            Logger.getLogger(BitacoraTramaPlana.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("bitacora trama plana");
    }
}
