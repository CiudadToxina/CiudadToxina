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
        
        String opcionFiltro = request.getParameter("Filtro");
        String opcionFormato = request.getParameter("Formato");

        try ( PrintWriter out = response.getWriter()) {
            TipoVista tipoVista = TipoVista.valueOf(opcionFiltro);
            
            if(opcionFiltro.equals("Hoy")){
                
                controlador.filtrarRegistroFecha(IniciarWeb.bitacora);
                controlador.consultarBitacora(IniciarWeb.bitacora);
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet ConsultarBitacora</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("</body>");
                out.println("</html>");
            }
            else if(opcionFiltro.equals("Todos")){
                
                controlador.filtrarTodos(IniciarWeb.bitacora);
                controlador.consultarBitacora(IniciarWeb.bitacora);
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet ConsultarBitacora</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("</body>");
                out.println("</html>");
            }
            else{
                controlador.filtrarRegistros(tipoVista, IniciarWeb.bitacora);
                controlador.consultarBitacora(IniciarWeb.bitacora);
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet ConsultarBitacora</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("</body>");
                out.println("</html>");
            }

        }
    }

}
