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
import logicadenegocios.TipoVista;

/**
 *
 * @author valef
 */
public class ConsultaBitacora extends HttpServlet {
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String opcionFiltro = request.getParameter("Registro");
        String opcionFormato = request.getParameter("Formato");
        TipoVista tipoVista = TipoVista.valueOf(opcionFiltro);
        
        controlador.filtrarRegistros(tipoVista, IniciarWeb.bitacora);
        controlador.consultarBitacora(IniciarWeb.bitacora);

        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ConsultarBitacora</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ConsultarBitacora at " + controlador.filtrarRegistros(tipoVista, IniciarWeb.bitacora) + "</h1>");
            out.println("<h1> " + controlador.consultarBitacora(IniciarWeb.bitacora) + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

}
