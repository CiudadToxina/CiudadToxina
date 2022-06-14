/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicadenegocios;

import java.time.LocalDateTime;

public class Registro {
    private LocalDateTime fechaHoraBitacora;
    private TipoAccion tipoAccion;
    private TipoVista tipoVista;
    
    public Registro(TipoAccion pTipoAccion, TipoVista pTipoVista){
        this.fechaHoraBitacora = LocalDateTime.now();
        this.tipoAccion = pTipoAccion;
        this.tipoVista = pTipoVista;
    } 
}
