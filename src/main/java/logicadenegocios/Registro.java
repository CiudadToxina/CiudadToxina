/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicadenegocios;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author sanch
 */
public class Registro {
    private LocalDateTime fechaHoraBitacora;
    private TipoAccion tipoAccion;
    private TipoVista tipoVista;
    private ArrayList<BitacoraObserver> observadores = new ArrayList<BitacoraObserver>(); 
    
    public Registro(TipoAccion pTipoAccion, TipoVista pTipoVista){
        this.fechaHoraBitacora = LocalDateTime.now();
        this.tipoAccion = pTipoAccion;
        this.tipoVista = pTipoVista;
        observadores = new ArrayList<>();
        
    }
    
    public void agregarObservador(BitacoraObserver pObserver){
        observadores.add(pObserver);
    }
    
    public void notificarTodosObservadores(){
        for(int i=0; i<observadores.size(); i++){
            observadores.get(i).crearBitacora();
        }
    }
    
}
