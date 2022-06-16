/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package APIs;

import logicadenegocios.MensajeDecorador;

/**
 *
 * @author valef
 */
public class MensajeTraducidoIngles extends MensajeDecorador{
    MensajeTraducidoIngles(EnvioMensaje pEnvioMensaje){
        super(pEnvioMensaje);
    }
    
    @Override
    public void enviarMensaje(String correoDestino, String mensaje){
        this.mensaje.enviarMensaje(correoDestino, mensaje);
    }
    private void setMensajeTraducidoIngles(String pMensaje){
        Traducir traducir = new Traducir();
        this.mensajeTraducido = traducir.traducirIngles(pMensaje);
    }
}
