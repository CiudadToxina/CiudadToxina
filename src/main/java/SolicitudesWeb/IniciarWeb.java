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
import static java.lang.System.out;
import logicadenegocios.Banco;
import logicadenegocios.Cliente;

/**
 *
 * @author valef
 */
public class IniciarWeb extends HttpServlet {
    static Controlador controlador = new Controlador();
    static Banco banco = new Banco();
    static int numCuenta;
    static String pin;
    static String palabraEnviada;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        controlador.fullerClientes(banco);
        controlador.fullerCuentas(banco);
        controlador.fullerOperaciones(banco);
        controlador.fullerAdministrador(banco);
        response.sendRedirect("SeleccionInicio.html");
    }
}
