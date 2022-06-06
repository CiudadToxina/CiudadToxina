/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package SolicitudesWeb;

import Controlador.Controlador;
import Controlador.ControladorValidaciones;
import Excepciones.CuentaDoesNotExistException;
import static SolicitudesWeb.RegistroCliente.controladorValidaciones;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author vjiucl
 */
public class DepositarColones extends HttpServlet {
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
        int numCuenta;
        int montoDeposito;
        
        try{
            response.setContentType("text/html;charset=UTF-8");
            numCuenta = Integer.parseInt(request.getParameter("numCuenta"));
            controladorValidaciones.cuentaExiste(numCuenta, IniciarWeb.banco);
            
            montoDeposito = Integer.parseInt(request.getParameter("monto")); 
            
            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html>"
                + "<html>"
                + "<head>"
                + "<title>Registro completo.</title>"
                + "</head>"
                + "<body>"
                + "<h1><br/>" + controlador.depositarColones(numCuenta, montoDeposito, IniciarWeb.banco) + "</h1>"
                + "<a href=\"index.html\"><button>Volver al menú principal</button></a>"
                + "</body>"
                + "</html>");
        }
        catch (NumberFormatException entradaInvalida){
            PrintWriter out = response.getWriter();   
            out.println(controladorValidaciones.auxiliarWeb("Debe ingresar un número entero.", "DepositarColones"));
        }
        catch (CuentaDoesNotExistException cuentaNoExiste){
            PrintWriter out = response.getWriter();   
            out.println(controladorValidaciones.auxiliarWeb(cuentaNoExiste.getLocalizedMessage(), "DepositarColones"));
        }   
    }
}
