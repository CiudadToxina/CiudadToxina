/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package APIs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;

/**
 *
 * @author sanch
 */
public class EnvioSMS {
    
    public void enviarMensaje(String numeroTelefono, String mensaje) throws MalformedURLException, IOException{
        String myURI = "https://api.bulksms.com/v1/messages";
        
        String myUsername = "vjiucl";
        String myPassword = "PC-@KEiF-#t8LxH";
        String codPais = "+506";

        String myData = "{to: \""+codPais+""+numeroTelefono+"\", encoding: \"UNICODE\", body: \""+mensaje+"\"}";

        URL url = new URL(myURI);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.setDoOutput(true);

        String authStr = myUsername + ":" + myPassword;
        String authEncoded = Base64.getEncoder().encodeToString(authStr.getBytes());
        request.setRequestProperty("Authorization", "Basic " + authEncoded);

        request.setRequestMethod("POST");
        request.setRequestProperty( "Content-Type", "application/json");

        OutputStreamWriter out = new OutputStreamWriter(request.getOutputStream());
        out.write(myData);
        out.close();

        try {
          InputStream response = request.getInputStream();
          BufferedReader in = new BufferedReader(new InputStreamReader(response));
          String replyText;
          while ((replyText = in.readLine()) != null) {
          }
          in.close();
        } catch (IOException ex) {
          BufferedReader in = new BufferedReader(new InputStreamReader(request.getErrorStream()));

          String replyText;
          while ((replyText = in.readLine()) != null) {
          }
          in.close();
        }
    }
}
