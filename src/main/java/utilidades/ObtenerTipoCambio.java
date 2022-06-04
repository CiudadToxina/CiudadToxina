package utilidades;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
/**
 *
 * @author sanch
 */
public class ObtenerTipoCambio {
    
    private final int indicadorVenta = 318;
    private final int indicadorCompra = 317;
    private String fechaInicial;
    private String fechaFinal;
    private final String nombre = "CiudadToxina";
    private final String  subNiveles = "N";
    private final String correoElectronico = "ciudadtoxina05@gmail.com";
    private final String token = "ODMI2X2TM0";
    private final String dirWEB = "https://gee.bccr.fi.cr/Indicadores/Suscripciones/WS/wsindicadoreseconomicos.asmx/ObtenerIndicadoresEconomicosXML";
    private final String VALUE_TAG = "NUM_VALOR";
    private String url;

   
    public double obtenerTipoCambio(int pIndicador){
        try{
            setFecha();
            setUrl(pIndicador);
            String indicadoresEconomicos = ConexionWebService.getHTML(url);
            GenerarXML xmlNuevo = new GenerarXML(indicadoresEconomicos);
            double tipoDeCambio = extraerTipoCambio(xmlNuevo.getXML());
            return tipoDeCambio;    
        }
        catch(Exception e) {
            return 0;
        }
    }
    
    private void setFecha(){
        LocalDate today = LocalDate.now();
        this.fechaFinal = today.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.fechaInicial = this.fechaFinal;
    }
    
    private void setUrl(int pIndicador){
        String params = 
                "Indicador="+pIndicador+"&FechaInicio="+fechaInicial+"&FechaFinal="+fechaFinal+
                "&Nombre="+nombre+"&SubNiveles="+subNiveles+"&CorreoElectronico="+correoElectronico+
                "&Token="+token;
        this.url = dirWEB+"?"+params;
    }
    
    private double extraerTipoCambio(String urlConsulta){
        double tipoCambio;
        
        int indiceEncontrado = urlConsulta.indexOf(VALUE_TAG);
        String tipoDeCambioEncontrado = urlConsulta.substring(indiceEncontrado+10, indiceEncontrado+16);
        
        tipoCambio = Double.parseDouble(tipoDeCambioEncontrado);
        
        return tipoCambio;
    }
}
