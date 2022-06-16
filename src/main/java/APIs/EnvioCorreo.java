package APIs;

import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import APIs.Traducir;

/**
 * Clase Correo
 * @author valef
 */
public class EnvioCorreo implements EnvioMensaje{
    
    @Override
    public void enviarMensaje(String correoDestinatario, String mensaje){
        Properties propiedades = new Properties();
        propiedades.setProperty("mail.smtp.host", "smtp.outlook.com");
        //propiedades.setProperty("mail.smtp.host", "smtp.office365.com");
        //propiedades.setProperty("mail.smtp.user", "ciudadtoxina@outlook.com");
        propiedades.setProperty("mail.smtp.auth", "true");
        propiedades.setProperty("mail.smtp.starttls.enable", "true");
        propiedades.setProperty("mail.smtp.port", "587");
        
        Session sesion = Session.getDefaultInstance(propiedades);
        String correo_emisor = "ciudadtoxina@outlook.com";
        String contraseña_emisor = "Vivaca69420";
        
        String correo_receptor = correoDestinatario;
        String asunto = "Sistema Bancario Ciudad Toxina";
        
        BodyPart texto = new MimeBodyPart();
        Traducir traducir = new Traducir();
        String mensajeTraducido = traducir.traducirIngles(mensaje);
        mensaje = mensaje + 
                "<br>__________________________________________________________________________________________<br><br>" 
                + mensajeTraducido;
        try{
            texto.setContent(mensaje,"text/html");

            MimeMultipart partes= new MimeMultipart();
            partes.addBodyPart(texto);

            //Ahora esto es la construccion 
            MimeMessage message = new MimeMessage(sesion);
            message.setFrom(new InternetAddress(correo_emisor));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(correo_receptor));
            message.setSubject(asunto);
            //message.setText(mensaje);
            message.setContent(partes);

            //Esto es lo que hace el transporte
            Transport transporte = sesion.getTransport("smtp");
            transporte.connect(correo_emisor,contraseña_emisor);
            transporte.sendMessage(message , message.getRecipients(Message.RecipientType.TO));
            transporte.close();             
        }catch(MessagingException me){
             me.printStackTrace();
        }  
    }  
}