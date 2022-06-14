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

    public ArrayList<Registro> getRegistros() {
        return registros;
    }

    public void setRegistros(ArrayList<Registro> registros) {
        this.registros = registros;
    }

    public ArrayList<Registro> getRegistrosFiltrados() {
        return registrosFiltrados;
    }

    public void setRegistrosFiltrados(ArrayList<Registro> registrosFiltrados) {
        this.registrosFiltrados = registrosFiltrados;
    }

    public ArrayList<BitacoraObserver> getBitacorasObservadoras() {
        return bitacorasObservadoras;
    }

    public void setBitacorasObservadoras(ArrayList<BitacoraObserver> bitacorasObservadoras) {
        this.bitacorasObservadoras = bitacorasObservadoras;
    }    
    
    public void agregarObservador(BitacoraObserver pObserver){
        bitacorasObservadoras.add(pObserver);
    }
    
    public void notificarTodosObservadores(){
        for(int i=0; i<bitacorasObservadoras.size(); i++){
            bitacorasObservadoras.get(i).generarBitacora();
        }
    }
    
    public void agregarRegistro(Registro pRegistro){
        registros.add(pRegistro);
    }    
    
    public void agregarRegistroFiltro(Registro pRegistro){
        registrosFiltrados.add(pRegistro);
    }  
    
    public String imprimirRegistros(){
        String mensaje = "";
        for (Registro registro : this.registros){
            mensaje += registro.getFechaHoraBitacora();
            mensaje += registro.getTipoAccion();
            mensaje += registro.getTipoVista();
        }
        return mensaje;
    }
    
    public String imprimirRegistrosFiltrados(){
        String mensaje = "";
        for (Registro registro : this.registrosFiltrados){
            mensaje += registro.getFechaHoraBitacora();
            mensaje += registro.getTipoAccion();
            mensaje += registro.getTipoVista();
        }
        return mensaje;
    }
}
