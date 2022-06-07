/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package SolicitudesWeb;

import Controlador.Controlador;
import Controlador.ControladorValidaciones;
import Excepciones.BlankSpaceException;
import Excepciones.CuentaDoesNotExistException;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author valef
 */
public class ConsultarEstatusCuenta extends HttpServlet {
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
        
        try{
            response.setContentType("text/html;charset=UTF-8");
            
            String numeroCuentaTexto = request.getParameter("numeroCuenta");
            controladorValidaciones.espacioEnBlanco(numeroCuentaTexto);
            
            int numeroCuenta = Integer.parseInt(numeroCuentaTexto);
            controladorValidaciones.cuentaExiste(numeroCuenta, IniciarWeb.banco);
            
            String estatusCuenta = controlador.consultarEstatusCuenta(numeroCuenta, IniciarWeb.banco);
            
            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Consultar Estatus Cuenta</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>" + estatusCuenta + "</h1>");
            out.println("<a href=\"MenuPrincipal.html\"><button>Volver al menú principal</button></a>");
            out.println("</body>");
            out.println("</html>");
        }
        catch(BlankSpaceException espacioEnBlanco){
            PrintWriter out = response.getWriter();
            out.println(controladorValidaciones.auxiliarWeb(espacioEnBlanco.getLocalizedMessage(), "ConsultarEstatusCuenta"));
        }
        catch(CuentaDoesNotExistException cuentaNoExiste){
            PrintWriter out = response.getWriter();
            out.println(controladorValidaciones.auxiliarWeb(cuentaNoExiste.getLocalizedMessage(), "ConsultarEstatusCuenta"));
        }
    }
}
