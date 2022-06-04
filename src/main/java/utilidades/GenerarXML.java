/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilidades;

import java.io.IOException;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author sanch
 */
public class GenerarXML {
    private String xml;
    private Element rootElement;
    
    public GenerarXML (String data) throws SAXException, IOException, ParserConfigurationException{
        
        data = cambiarCaracteres(data);
        this.xml = data;
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new StringReader(this.xml)));
        this.rootElement = document.getDocumentElement();
    }
    
    public String getXML(){
        return this.xml;
    }
    
    private String cambiarCaracteres(String cadena){
        cadena = cadena.replace("&lt;", "<");
        cadena = cadena.replace("&gt;", ">");
        
        return cadena;
    }
    
}
