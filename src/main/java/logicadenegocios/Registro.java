/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicadenegocios;

import java.time.LocalDateTime;

public class Registro {
    private int identificadorRegistro;
    private LocalDateTime fechaHoraBitacora;
    private TipoAccion tipoAccion;
    private TipoVista tipoVista;
    private static int cantRegistros = 0;
    
    public Registro(TipoAccion pTipoAccion, TipoVista pTipoVista){
        this.identificadorRegistro = cantRegistros + 1;
        
        /*
        if(cantRegistros == 1 || cantRegistros == 4){
            this.setFechaHoraBitacora(LocalDateTime.of(2015, 03, 31, 5, 40, 23));
        }
        else{
            this.fechaHoraBitacora = LocalDateTime.now();
        }*/
        
        this.fechaHoraBitacora = LocalDateTime.now();
        
        
        this.tipoAccion = pTipoAccion;
        this.tipoVista = pTipoVista;
        cantRegistros += 1;
    } 

    public int getIdentificadorRegistro() {
        return identificadorRegistro;
    }

    public void setIdentificadorRegistro(int identificadorRegistro) {
        this.identificadorRegistro = identificadorRegistro;
    }

    public LocalDateTime getFechaHoraBitacora() {
        return fechaHoraBitacora;
    }

    public void setFechaHoraBitacora(LocalDateTime fechaHoraBitacora) {
        this.fechaHoraBitacora = fechaHoraBitacora;
    }

    public TipoAccion getTipoAccion() {
        return tipoAccion;
    }

    public void setTipoAccion(TipoAccion tipoAccion) {
        this.tipoAccion = tipoAccion;
    }

    public TipoVista getTipoVista() {
        return tipoVista;
    }

    public void setTipoVista(TipoVista tipoVista) {
        this.tipoVista = tipoVista;
    }
    
    
}
