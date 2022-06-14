/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package SolicitudesWeb;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Controlador.Controlador;
import Excepciones.BlankSpaceException;
import Excepciones.ClienteDoesNotExistException;
import Excepciones.PinFormatException;
import static SolicitudesWeb.RegistroCliente.controlador;
import static SolicitudesWeb.RegistroCliente.controladorValidaciones;
import logicadenegocios.Cliente;
import logicadenegocios.TipoAccion;
import logicadenegocios.TipoVista;

/**
 *
 * @author valef
 */
public class RegistroCuenta extends HttpServlet {
    static Controlador controlador = new Controlador();
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Cliente duenioCuenta;
        try{
            response.setContentType("text/html;charset=UTF-8");
            int identificacion = Integer.parseInt(request.getParameter("identificacionCliente"));
            controladorValidaciones.clienteExiste(identificacion, IniciarWeb.banco); 
            duenioCuenta = IniciarWeb.banco.buscarCliente(identificacion);
                 
            String pin = request.getParameter("pin");
            controladorValidaciones.espacioEnBlanco(pin);
            controladorValidaciones.formatoPin(pin);
            
            double saldo = Integer.parseInt(request.getParameter("saldo"));

            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html>"
                + "<html>"
                + "<head>"
                + "<title>Registro completo.</title>"
                + "</head>"
                + "<body>"
                + "<h1><br/>" + controlador.registrarCuenta(saldo, pin, duenioCuenta, IniciarWeb.banco) + "</h1>"
                + "<a href=\"MenuPrincipal.html\"><button>Volver al menú principal</button></a>"
                + "</body>"
                + "</html>");
            
            controlador.crearRegistro(TipoAccion.CrearCuenta, TipoVista.WEB, IniciarWeb.bitacora);
        }
        
        catch (NumberFormatException entradaInvalida){
            PrintWriter out = response.getWriter();      
            out.println(controladorValidaciones.auxiliarWeb("Debe ingresar un numero entero.", "RegistroCuenta"));
        }
        catch(ClienteDoesNotExistException clienteNoExiste){
            PrintWriter out = response.getWriter();   
            out.println(controladorValidaciones.auxiliarWeb(clienteNoExiste.getLocalizedMessage(), "RegistroCuenta"));
        }
        catch (PinFormatException formatoPin){
            PrintWriter out = response.getWriter();   
            out.println(controladorValidaciones.auxiliarWeb(formatoPin.getLocalizedMessage(), "RegistroCuenta"));
        }
        catch (BlankSpaceException espacioEnBlanco){
            PrintWriter out = response.getWriter(); 
            out.println(controladorValidaciones.auxiliarWeb(espacioEnBlanco.getLocalizedMessage(), "RegistroCuenta"));
        }
    }
}
