package SolicitudesWeb;

import Controlador.Controlador;
import static SolicitudesWeb.ConsultarEstadoCuenta.controlador;
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
public class ConsultarGananciasTotalesBanco extends HttpServlet {

    Controlador controlador = new Controlador();

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
        response.setContentType("text/html;charset=UTF-8");
        
        String ganaciasTotales = controlador.consultarGananciasBanco(IniciarWeb.banco);

        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Servlet ConsultarGananciasTotalesBanco</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>" + ganaciasTotales + "</h1>");
        out.println("<a href=\"MenuPrincipal.html\"><button>Volver al menú principal</button></a>");
        out.println("</body>");
        out.println("</html>");
                
        controlador.crearRegistro(TipoAccion.ConsultarGananciasTotalesBanco, TipoVista.WEB, IniciarWeb.bitacora);

    }
}
