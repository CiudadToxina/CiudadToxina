/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicadenegocios;

/**
 *
 * @author sanch
 */
public class BitacoraXML extends BitacoraObserver{
    
    public BitacoraXML(Registro pRegistro){
        registro = pRegistro;
        registro.agregarObservador(this);
    }
    
    @Override
    protected void crearBitacora(){
    }
}