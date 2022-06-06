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
import logicadenegocios.Banco;
import logicadenegocios.Cliente;

/**
 *
 * @author valef
 */
public class IniciarWeb extends HttpServlet {
    static Controlador controlador = new Controlador();
    static Banco banco = new Banco();
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
        
        response.sendRedirect("MenuPrincipal.html");
        
        /*Cliente cliente = banco.buscarCliente(117450718);
        try ( PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet IniciarWeb</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet IniciarWeb at " + request.getContextPath() + "</h1>");
            out.println("<h1>" + cliente.consultarDatosCliente() + "</h1>");
            out.println("</body>");
            out.println("<a href=\"index.html\"><button>Volver al menú principal</button></a>");
            out.println("</html>");
        }*/
    }
}
