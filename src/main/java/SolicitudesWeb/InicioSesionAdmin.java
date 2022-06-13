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
import Controlador.ControladorValidaciones;
import Excepciones.AdminDoesNotExistException;
import Excepciones.BlankSpaceException;

/**
 *
 * @author sanch
 */
public class InicioSesionAdmin extends HttpServlet {
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
    protected void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String usuario;
        String contrasena;
        
        PrintWriter out = response.getWriter();
        
        
        try{
            response.setContentType("text/html;charset=UTF-8");
            
            usuario = request.getParameter("usuario");
            controladorValidaciones.espacioEnBlanco(usuario);
            
            contrasena = request.getParameter("contrasena");
            controladorValidaciones.espacioEnBlanco(contrasena);
            
            controladorValidaciones.credencialesAdmin(usuario, contrasena, IniciarWeb.banco);
            
            response.sendRedirect("MenuPrincipalAdmin.html");
        }
        catch(BlankSpaceException espacioEnBlanco){
            out.println(controladorValidaciones.auxiliarWeb(espacioEnBlanco.getLocalizedMessage(), "InicioSesionAdmin"));
        }
        catch(AdminDoesNotExistException credencialesIncorrectas){
            out.println(controladorValidaciones.auxiliarWeb(credencialesIncorrectas.getLocalizedMessage(), "InicioSesionAdmin"));
        }
    }
}
