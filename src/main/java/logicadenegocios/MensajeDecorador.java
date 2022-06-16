/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicadenegocios;

import APIs.EnvioCorreo;
import APIs.EnvioMensaje;

/**
 *
 * @author valef
 */
public abstract class MensajeDecorador implements EnvioMensaje{
    protected EnvioMensaje mensaje;
    protected String mensajeTraducido;
    
    public MensajeDecorador(EnvioMensaje pEnvioMensaje){
        this.mensaje = pEnvioMensaje;
    }
    
    @Override
    public void enviarMensaje(String contactoDestino, String mensaje){
        this.mensaje.enviarMensaje(contactoDestino, mensaje);
    }
}
