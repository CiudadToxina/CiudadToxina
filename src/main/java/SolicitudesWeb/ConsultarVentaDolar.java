/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package SolicitudesWeb;

import Controlador.Controlador;
import Controlador.ControladorValidaciones;
import Excepciones.BlankSpaceException;
import Excepciones.CuentaDoesNotExistException;
import Excepciones.CuentaInactivaException;
import Excepciones.PinDoesNotMatchException;
import static SolicitudesWeb.DepositarColones.controlador;
import static SolicitudesWeb.DepositarColones.controladorValidaciones;
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
 * @author vjiucl
 */
public class ConsultarVentaDolar extends HttpServlet {
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>"
                + "<html>"
                + "<head>"
                + "<title></title>"
                + "</head>"
                + "<body>"
                + "<h1><br/>" + controlador.consultarVentaDolar() + "</h1>"
                + "<a href=\"MenuPrincipal.html\"><button>Volver al menú principal</button></a>"
                + "</body>"
                + "</html>");
        
        controlador.crearRegistro(TipoAccion.ConsultarVentaDolar, TipoVista.WEB, IniciarWeb.bitacora);
    }
}
      