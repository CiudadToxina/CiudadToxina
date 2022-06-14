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
import Controlador.ControladorValidaciones;
import Excepciones.BlankSpaceException;
import Excepciones.CedulaFormatException;
import Excepciones.ClienteAlreadyExistsException;
import Excepciones.CorreoExistsException;
import Excepciones.DateFormatException;
import Excepciones.MailFormatException;
import Excepciones.NumTelFormatException;
import Excepciones.TelefonoExistsException;
import logicadenegocios.TipoAccion;
import logicadenegocios.TipoVista;

/**
 *
 * @author valef
 */
public class RegistroCliente extends HttpServlet {
    static Controlador controlador = new Controlador();
    static ControladorValidaciones controladorValidaciones = new ControladorValidaciones();
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
        try{
            response.setContentType("text/html;charset=UTF-8");
            String primerApellido = request.getParameter("primerApellido");
            controladorValidaciones.espacioEnBlanco(primerApellido);

            String segundoApellido = request.getParameter("segundoApellido");
            controladorValidaciones.espacioEnBlanco(segundoApellido);
            
            String nombre  = request.getParameter("nombre");
            controladorValidaciones.espacioEnBlanco(nombre);
            
            int identificacion = Integer.parseInt(request.getParameter("identificacion"));
            controladorValidaciones.validarCedula(Integer.toString(identificacion));
            controladorValidaciones.clienteRepetido(identificacion, IniciarWeb.banco); 
            
            String fechaNacimiento = request.getParameter("fechaNacimiento");
            int pAnio = Integer.valueOf(fechaNacimiento.substring(0,4));
            int pMes = Integer.valueOf(fechaNacimiento.substring(5,7));
            int pDia = Integer.valueOf(fechaNacimiento.substring(8,10));
            controladorValidaciones.validarFecha(pDia, pMes, pAnio);
            
            int numeroTelefonico = Integer.parseInt(request.getParameter("numeroTelefonico"));
            controladorValidaciones.validarNumTel(Integer.toString(numeroTelefonico));
            controladorValidaciones.telRepetido(numeroTelefonico, IniciarWeb.banco); 
            
            String correoElectronico = request.getParameter("correoElectronico");
            controladorValidaciones.espacioEnBlanco(correoElectronico);
            controladorValidaciones.validarCorreo(correoElectronico);
            controladorValidaciones.correoRepetido(correoElectronico, IniciarWeb.banco);
            
            controlador.crearRegistro(TipoAccion.RegistrarCliente, TipoVista.WEB, IniciarWeb.bitacora);

            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html>"
                + "<html>"
                + "<head>"
                + "<title>Registro completo.</title>"
                + "</head>"
                + "<body>"
                + "<h1><br/>" + controlador.registrarCliente(primerApellido, segundoApellido, nombre, identificacion, numeroTelefonico, correoElectronico, pDia, pMes, pAnio, IniciarWeb.banco) + "</h1>"
                + "<a href=\"MenuPrincipal.html\"><button>Volver al menú principal</button></a>"
                + "</body>"
                + "</html>");
        }
        
        catch (BlankSpaceException espacioEnBlanco){
            PrintWriter out = response.getWriter(); 
            out.println(controladorValidaciones.auxiliarWeb(espacioEnBlanco.getLocalizedMessage(), "RegistroCliente"));
        }
        catch (NumberFormatException entradaInvalida){
            PrintWriter out = response.getWriter();      
            out.println(controladorValidaciones.auxiliarWeb("Debe ingresar un numero entero.", "RegistroCliente"));
        }
        catch (ClienteAlreadyExistsException clienteExiste){
            PrintWriter out = response.getWriter(); 
            out.println(controladorValidaciones.auxiliarWeb(clienteExiste.getLocalizedMessage(), "RegistroCliente"));
        }
        catch (DateFormatException fechaIncorrecta){
            PrintWriter out = response.getWriter(); 
            out.println(controladorValidaciones.auxiliarWeb(fechaIncorrecta.getLocalizedMessage(), "RegistroCliente"));
        }   
        catch (CedulaFormatException cedulaIncorrecta){
            PrintWriter out = response.getWriter();            
            out.println(controladorValidaciones.auxiliarWeb(cedulaIncorrecta.getLocalizedMessage(), "RegistroCliente"));
        } 
        catch (NumTelFormatException telIncorrecto){
            PrintWriter out = response.getWriter(); 
            out.println(controladorValidaciones.auxiliarWeb(telIncorrecto.getLocalizedMessage(), "RegistroCliente"));
        } 
        catch (MailFormatException correoIncorrecto){
            PrintWriter out = response.getWriter(); 
            out.println(controladorValidaciones.auxiliarWeb(correoIncorrecto.getLocalizedMessage(), "RegistroCliente"));
        }
        catch (TelefonoExistsException telefonoExiste){
            PrintWriter out = response.getWriter(); 
            out.println(controladorValidaciones.auxiliarWeb(telefonoExiste.getLocalizedMessage(), "RegistroCliente"));
        }
        catch (CorreoExistsException correoExiste){
            PrintWriter out = response.getWriter(); 
            out.println(controladorValidaciones.auxiliarWeb(correoExiste.getLocalizedMessage(), "RegistroCliente"));
        }
    }
}
