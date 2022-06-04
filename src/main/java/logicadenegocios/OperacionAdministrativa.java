/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicadenegocios;

import java.time.LocalDate;

/**
 *
 * @author valef
 */
public class OperacionAdministrativa extends Operacion{
    public OperacionAdministrativa(TipoOperacion pTipoOperacion){
        super(pTipoOperacion);
    }
    
    @Override
    protected String obtenerInfoGeneralOperacion(){
        String mensaje = "";
        mensaje += "Fecha de la operacion: " + this.fechaOperacion + "\n" 
                + "Tipo de operacion: " + this.tipoOperacion + "\n"
                + "Monto de operacion: No aplica\n";
        return mensaje;
    }
}
