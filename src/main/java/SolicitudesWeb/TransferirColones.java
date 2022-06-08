/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package SolicitudesWeb;

import Controlador.Controlador;
import Controlador.ControladorValidaciones;
import Excepciones.BlankSpaceException;
import Excepciones.CuentaDoesNotExistException;
import Excepciones.CuentaDoesNotHaveMoneyException;
import Excepciones.CuentaInactivaException;
import Excepciones.PalabraTelefonoIncorrectaException;
import Excepciones.PinDoesNotMatchException;
import static SolicitudesWeb.RetirarColones.controlador;
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
public class TransferirColones extends HttpServlet {
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
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String pin;
        String palabraEnviada;
        String palabraIngresada;
        int numCuentaOrigen;
        int numCuentaDestino;
        int montoDeposito;
        
        try{
            PrintWriter out = response.getWriter();
            palabraIngresada = request.getParameter("palabra");
            controladorValidaciones.espacioEnBlanco(palabraIngresada);
            controladorValidaciones.palabraTelIncorrecta(IniciarWeb.palabraEnviada, palabraIngresada, IniciarWeb.numCuenta, IniciarWeb.banco);
            
            numCuentaDestino = Integer.parseInt(request.getParameter("numCuentaDestino"));
            controladorValidaciones.cuentaExiste(numCuentaDestino, IniciarWeb.banco);
            controladorValidaciones.cuentaInactiva(numCuentaDestino, IniciarWeb.banco);

            montoDeposito = Integer.parseInt(request.getParameter("cantTransferir"));
            controladorValidaciones.fondosInsuficientes(IniciarWeb.numCuenta, montoDeposito, IniciarWeb.banco);
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Retirar Colones</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>" + controlador.realizarTransferencias(IniciarWeb.numCuenta, numCuentaDestino, montoDeposito, IniciarWeb.banco) + "</h1>");
            out.println("<a href=\"MenuPrincipal.html\"><button>Volver al menú principal</button></a>");
            out.println("</body>");
            out.println("</html>"); 
        }
        catch (CuentaDoesNotExistException cuentaNoExiste){
            System.out.println(cuentaNoExiste.getLocalizedMessage());
        }
        catch (NumberFormatException entradaInvalida){
            System.out.println("Debe ingresar un número entero.");
        }
        catch (CuentaInactivaException cuentaInactiva){
            System.out.println(cuentaInactiva.getLocalizedMessage());
        }
        catch (BlankSpaceException espacioEnBlanco){
            System.out.println(espacioEnBlanco.getLocalizedMessage());
        }
        catch (CuentaDoesNotHaveMoneyException fondosInsuficientes){
            System.out.println(fondosInsuficientes.getLocalizedMessage());
        }  
        catch (PalabraTelefonoIncorrectaException palabraIncorrecta){
            System.out.println(palabraIncorrecta.getLocalizedMessage());
        }
        catch (MessagingException errorCorreo){
            System.out.println("No se pudo enviar el correo correctamente.");
        }
    }
}
