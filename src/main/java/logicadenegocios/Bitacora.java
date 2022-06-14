/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicadenegocios;

import java.util.ArrayList;

public class Bitacora {
    private ArrayList<Registro> registros = new ArrayList<>(); 
    private ArrayList<Registro> registrosFiltrados = new ArrayList<>(); 
    private ArrayList<BitacoraObserver> bitacorasObservadoras = new ArrayList<>(); 
    
    public void agregarObservador(BitacoraObserver pObserver){
        bitacorasObservadoras.add(pObserver);
    }
    
    public void notificarTodosObservadores(){
        for(int i=0; i<bitacorasObservadoras.size(); i++){
            bitacorasObservadoras.get(i).actualizarBitacora();
        }
    }
    
    public void agregarRegistro(Registro pRegistro){
        registros.add(pRegistro);
    }
    
}
