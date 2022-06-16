/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicadepresentacion;

import logicadenegocios.Operacion;
import utilidades.SimpleOperacionFactory;

/**
 *
 * @author valef
 */
public class TiendaOperaciones {
    private SimpleOperacionFactory operacionFactory;
    public TiendaOperaciones(SimpleOperacionFactory pOperacionFactory){
        operacionFactory = pOperacionFactory;
    }
    
    public Operacion crearOperacion(String nombreOperacion)throws ClassNotFoundException, InstantiationException, IllegalAccessException{
        Operacion operacion;
        operacion = operacionFactory.crearOperacion(nombreOperacion);
        return operacion;
    }
}