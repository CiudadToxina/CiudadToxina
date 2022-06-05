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
import logicadenegocios.Cliente;

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
        response.setContentType("text/html;charset=UTF-8");
        int identificacion = Integer.parseInt(request.getParameter("identificacionCliente"));
        double saldo = Integer.parseInt(request.getParameter("saldo"));
        String pin = request.getParameter("pin");
        
        //buscar cliente registrado
        Cliente cliente = IniciarWeb.banco.buscarCliente(identificacion);
        
        controlador.registrarCuenta(saldo, pin, cliente, IniciarWeb.banco);
        
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet RegistroCuenta</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RegistroCuenta at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}
