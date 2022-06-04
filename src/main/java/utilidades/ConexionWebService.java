package utilidades;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author sanch
 */
public class ConexionWebService {
    
    protected static String getHTML(String urlDestinatario) throws Exception {
        
        StringBuilder indicesEconomicos = new StringBuilder();
        URL url = new URL(urlDestinatario);
        HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
        conexion.setRequestMethod("GET");
        
        BufferedReader informacionEconomica = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
        String line;
        while((line = informacionEconomica.readLine()) != null){
            indicesEconomicos.append(line);
        }
        informacionEconomica.close();
        return indicesEconomicos.toString();
    }
}
