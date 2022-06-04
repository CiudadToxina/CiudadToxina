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

/**
 * Clase Correo
 * @author valef
 */
public class EnvioCorreo {
    
    /**
     * Metodo que permite generar un correo 
     * @param correoDestinatario Correo destinatario del reporte
     * @throws AddressException si la dirección de correo electrónico no existe
     * @throws MessagingException 
     */
    
    
    public void generarCorreo(String correoDestinatario, String mensaje) throws AddressException, MessagingException{
        Properties propiedades = new Properties();
        propiedades.setProperty("mail.smtp.host", "smtp.gmail.com");
        propiedades.setProperty("mail.smtp.starttls.enable", "true");
        propiedades.setProperty("mail.smtp.port", "587");
        propiedades.setProperty("mail.smtp.auth", "true");
        
        
        Session sesion = Session.getDefaultInstance(propiedades);
        String correo_emisor = "ciudadtoxina05@gmail.com";
        String contraseña_emisor = "VVC69420";
        
        String correo_receptor = correoDestinatario;
        String asunto = "Sistema Bancario Ciudad Toxina";
        
        BodyPart texto = new MimeBodyPart();
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
    }  
}