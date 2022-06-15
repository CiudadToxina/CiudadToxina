/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicadenegocios;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public class BitacoraXML extends BitacoraObserver {

    public BitacoraXML(Bitacora pBitacora) {
        bitacora = pBitacora;
        bitacora.agregarObservador(this);
    }

    @Override
    public void generarBitacora() {

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation implementation = builder.getDOMImplementation();

            Document documento = implementation.createDocument(null, "bitacoraXML", null);
            documento.setXmlVersion("1.0");

            for (Registro registro : bitacora.getRegistrosFiltrados()) {
                
                Element todasLasBitacoras = documento.createElement("bitacoras");
                Element bitacoraSeleccionada = documento.createElement("bitacora");
                Element fechaHora = documento.createElement("fechaYhora");
                Element tipoAccion = documento.createElement("tipoAccion");
                Element tipoVista = documento.createElement("tipoVista");
                
                
                Text fechaYhoraTexto = documento.createTextNode(registro.getFechaHoraBitacora().toString());
                fechaHora.appendChild(fechaYhoraTexto);
                bitacoraSeleccionada.appendChild(fechaHora);

                Text tipoAccionTexto = documento.createTextNode(registro.getTipoAccion().toString());
                tipoAccion.appendChild(tipoAccionTexto);
                bitacoraSeleccionada.appendChild(tipoAccion);

                Text tipoVistaTexto = documento.createTextNode(registro.getTipoVista().toString());
                tipoVista.appendChild(tipoVistaTexto);
                bitacoraSeleccionada.appendChild(tipoVista);
                
                todasLasBitacoras.appendChild(bitacoraSeleccionada);
                documento.getDocumentElement().appendChild(todasLasBitacoras);
            }
            
            Source source = new DOMSource(documento);
            Result result = new StreamResult(new File("C:\\Users\\sanch\\Documents\\GitHub\\CiudadToxina\\bitacoraXML.xml"));

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(source, result);

            System.out.println("bitacora xml");
        } catch (ParserConfigurationException | TransformerException ex) {
            Logger.getLogger(BitacoraXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
