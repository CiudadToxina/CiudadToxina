/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package SolicitudesWeb;

import Controlador.Controlador;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logicadenegocios.TipoAccion;
import logicadenegocios.TipoVista;

/**
 *
 * @author sanch
 */
public class listarCuentasDescendentes extends HttpServlet {
    static Controlador controlador = new Controlador();

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
    protected void doPost (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String listaCuentas = controlador.listarCuentasDescendente(IniciarWeb.banco);
        String cuentasSeparadas = listaCuentas.replace("\n","<br>");
       
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();  
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Cuentas listadas descendentemente</title>");            
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>"+ cuentasSeparadas +"</br></h1>");
        out.println("<a href=\"listarCuentasDescendentes.html\"><button>Volver</button></a>");
        out.println("<a href=\"MenuPrincipal.html\"><button>Volver al menú principal</button></a>");
        out.println("</body>");
        out.println("</html>");
        
        controlador.crearRegistro(TipoAccion.ListarCuentasDescendente, TipoVista.WEB, IniciarWeb.bitacora);
        
    }
}
