/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package SolicitudesWeb;

import Excepciones.BlankSpaceException;
import Excepciones.CuentaDoesNotExistException;
import Excepciones.CuentaDoesNotHaveMoneyException;
import Excepciones.CuentaInactivaException;
import Excepciones.PalabraTelefonoIncorrectaException;
import Controlador.Controlador;
import Controlador.ControladorValidaciones;
import Excepciones.PinDoesNotMatchException;
import java.io.IOException;
import java.io.PrintWriter;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author valef
 */
public class EnviarPalabraColones extends HttpServlet {
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //response.setContentType("text/html;charset=UTF-8");
        String pin;
        String palabraIngresada;
        int montoRetiro;
        
        PrintWriter out = response.getWriter();
        
        try{
            IniciarWeb.numCuenta = Integer.parseInt(request.getParameter("numCuenta"));
            controladorValidaciones.cuentaExiste(IniciarWeb.numCuenta, IniciarWeb.banco);
            controladorValidaciones.cuentaInactiva(IniciarWeb.numCuenta, IniciarWeb.banco);
            
            IniciarWeb.pin = request.getParameter("pin");
            controladorValidaciones.espacioEnBlanco(IniciarWeb.pin);
            controladorValidaciones.pinCoincide(IniciarWeb.numCuenta, IniciarWeb.pin, IniciarWeb.banco);
            
            IniciarWeb.palabraEnviada = controlador.enviarMensaje(IniciarWeb.numCuenta, IniciarWeb.banco);
 
            response.sendRedirect("http://localhost:8080/CiudadToxina/RetirarColonesCopia.html");
        }
        catch (CuentaDoesNotExistException cuentaNoExiste){
            out.println(controladorValidaciones.auxiliarWeb(cuentaNoExiste.getLocalizedMessage(), "RetirarColones"));
        }   
        catch (CuentaInactivaException cuentaInactiva){
            out.println(controladorValidaciones.auxiliarWeb(cuentaInactiva.getLocalizedMessage(), "RetirarColones"));            
        }
        catch (PinDoesNotMatchException pinNoCoincide){
            out.println(controladorValidaciones.auxiliarWeb(pinNoCoincide.getLocalizedMessage(), "RetirarColones")); 
        }
        catch (NumberFormatException entradaInvalida){
            out.println(controladorValidaciones.auxiliarWeb("Debe ingresar un número entero.", "RetirarColones"));
        }
        catch (BlankSpaceException espacioEnBlanco){
            out.println(controladorValidaciones.auxiliarWeb(espacioEnBlanco.getLocalizedMessage(), "RetirarColones"));
        }
        catch (MessagingException errorCorreo){
            out.println(controladorValidaciones.auxiliarWeb("No se pudo enviar el correo correctamente.", "RetirarColones"));
        }         
    }
}