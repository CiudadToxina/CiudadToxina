/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package APIs;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

/**
 *
 * @author valef
 */
public class Traducir {
    
    public Traducir(){
        
    }
    
    public String traducirIngles(String mensaje){
        Translate translate = TranslateOptions.getDefaultInstance().getService();
        Translation translation = translate.translate(mensaje);
        return translation.getTranslatedText();
    }  
}
