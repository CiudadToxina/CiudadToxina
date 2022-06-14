/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicadenegocios;

public class BitacoraTramaPlana extends BitacoraObserver{
    
    public BitacoraTramaPlana(Bitacora pBitacora){
        bitacora = pBitacora;
        bitacora.agregarObservador(this);
    }
    
    @Override
    protected void actualizarBitacora(){
        
    }
}
