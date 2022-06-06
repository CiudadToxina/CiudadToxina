/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package SolicitudesWeb;

import Controlador.Controlador;
import Controlador.ControladorValidaciones;
import Excepciones.BlankSpaceException;
import Excepciones.CuentaDoesNotExistException;
import Excepciones.CuentaInactivaException;
import Excepciones.PinDoesNotMatchException;
import Excepciones.PinFormatException;
import java.io.IOException;
import java.io.PrintWriter;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author vjiucl
 */
public class CambiarPin extends HttpServlet {
    static Controlador controlador = new Controlador();
    static ControladorValidaciones controladorValidaciones = new ControladorValidaciones();
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int numCuenta;
        String pinActual;
        String pinNuevo;
        
        PrintWriter out = response.getWriter();
        
        try{
            response.setContentType("text/html;charset=UTF-8");     
            numCuenta = Integer.parseInt(request.getParameter("numCuenta"));
            controladorValidaciones.cuentaExiste(numCuenta, IniciarWeb.banco);
            controladorValidaciones.cuentaInactiva(numCuenta, IniciarWeb.banco);

            pinActual = request.getParameter("pinActual");
            controladorValidaciones.espacioEnBlanco(pinActual);
            controladorValidaciones.pinCoincide(numCuenta, pinActual, IniciarWeb.banco);
            
            pinNuevo = request.getParameter("pinNuevo");
            controladorValidaciones.espacioEnBlanco(pinNuevo);
            controladorValidaciones.formatoPin(pinNuevo);
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CambiarPIN</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CambiarPIN at " + controlador.cambiarPin(pinNuevo, numCuenta, IniciarWeb.banco) + "</h1>");
            out.println("<a href=\"index.html\"><button>Volver al menú principal</button></a>");
            out.println("</body>");
            out.println("</html>");
        }
        catch (PinDoesNotMatchException pinNoCoincide){
            out.println(controladorValidaciones.auxiliarWeb(pinNoCoincide.getLocalizedMessage(), "CambiarPIN"));
        }
        catch (CuentaDoesNotExistException cuentaNoExiste){
            out.println(controladorValidaciones.auxiliarWeb(cuentaNoExiste.getLocalizedMessage(), "CambiarPIN"));
        }
        catch (NumberFormatException entradaInvalida){
            out.println(controladorValidaciones.auxiliarWeb("Debe ingresar un número entero.", "CambiarPIN"));
        }
        catch (CuentaInactivaException cuentaInactiva){
            out.println(controladorValidaciones.auxiliarWeb(cuentaInactiva.getLocalizedMessage(), "CambiarPIN"));
        }
        catch (PinFormatException formatoPin){
            out.println(controladorValidaciones.auxiliarWeb(formatoPin.getLocalizedMessage(), "CambiarPIN"));
        }
        catch (BlankSpaceException espacioEnBlanco){
            out.println(controladorValidaciones.auxiliarWeb(espacioEnBlanco.getLocalizedMessage(), "CambiarPIN"));
        }
        catch (MessagingException errorCorreo){
            out.println(controladorValidaciones.auxiliarWeb("No se pudo enviar el correo correctamente.", "CambiarPIN"));
        }
    }
}
