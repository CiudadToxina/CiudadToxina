/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicadenegocios;

public class BitacoraXML extends BitacoraObserver{
    
    public BitacoraXML(Bitacora pBitacora){
        bitacora = pBitacora;
        bitacora.agregarObservador(this);
    }
    
    @Override
    public void generarBitacora(){
        System.out.println("bitacora xml");
    }
}
