/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilidades;

import logicadenegocios.Operacion;
import logicadenegocios.OperacionAdministrativa;
import logicadenegocios.OperacionMonetaria;
import logicadenegocios.TipoOperacion;

/**
 *
 * @author valef
 */
public class SimpleOperacionFactory {
    public Operacion crearOperacion(String nombreOperacion) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
        Operacion operacion = null;
        operacion = (Operacion)Class.forName("logicadenegocios."+nombreOperacion).newInstance();
        return operacion;
    }
}
