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
import java.io.IOException;
import java.io.PrintWriter;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logicadenegocios.TipoAccion;
import logicadenegocios.TipoVista;

/**
 *
 * @author valef
 */
public class RetirarDolares extends HttpServlet {
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
        String palabraIngresada;
        int montoRetiro;
        
        PrintWriter out = response.getWriter();
        
        try{
            palabraIngresada = request.getParameter("palabra");
            controladorValidaciones.espacioEnBlanco(palabraIngresada);
            controladorValidaciones.palabraTelIncorrecta(IniciarWeb.palabraEnviada, palabraIngresada, IniciarWeb.numCuenta, IniciarWeb.banco);
            
            montoRetiro = Integer.parseInt(request.getParameter("cantRetirar"));
            controladorValidaciones.fondosInsuficientes(IniciarWeb.numCuenta, montoRetiro, IniciarWeb.banco);
           
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Retirar Colones</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>" + controlador.retirarDolares(IniciarWeb.numCuenta, montoRetiro, IniciarWeb.banco) + "</h1>");
            out.println("<a href=\"MenuPrincipal.html\"><button>Volver al menú principal</button></a>");
            out.println("</body>");
            out.println("</html>");
            
            controlador.crearRegistro(TipoAccion.RetiroDolares, TipoVista.WEB, IniciarWeb.bitacora);
            
        }
        catch (NumberFormatException entradaInvalida){
            out.println(controladorValidaciones.auxiliarWeb("Debe ingresar un número entero.", "RetirarColones"));
        }
        catch (CuentaDoesNotHaveMoneyException cuentaSinFondosSuficientes){
            out.println(controladorValidaciones.auxiliarWeb(cuentaSinFondosSuficientes.getLocalizedMessage(), "RetirarColones"));
        }
        catch (BlankSpaceException espacioEnBlanco){
            out.println(controladorValidaciones.auxiliarWeb(espacioEnBlanco.getLocalizedMessage(), "RetirarColones"));
        }
        catch (PalabraTelefonoIncorrectaException palabraIncorrecta){
            out.println(controladorValidaciones.auxiliarWeb(palabraIncorrecta.getLocalizedMessage(), "RetirarColones"));
        }
        catch (MessagingException errorCorreo){
            out.println(controladorValidaciones.auxiliarWeb("No se pudo enviar el correo correctamente.", "RetirarColones"));
        }       
    }
}
