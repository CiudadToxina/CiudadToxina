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
import Excepciones.BlankSpaceException;
import Excepciones.CedulaFormatException;

/**
 *
 * @author valef
 */
public class RegistroCliente extends HttpServlet {
    static Controlador controlador = new Controlador();
    static ControladorValidaciones controladorValidaciones = new ControladorValidaciones();
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
        try{
            response.setContentType("text/html;charset=UTF-8");
            String primerApellido = request.getParameter("primerApellido");
            //controladorValidaciones.espacioEnBlanco(primerApellido);

            String segundoApellido = request.getParameter("segundoApellido");
            String nombre  = request.getParameter("nombre");
            int identificacion = Integer.parseInt(request.getParameter("identificacion"));
            controladorValidaciones.validarCedula(Integer.toString(identificacion));
            
            String fechaNacimiento = request.getParameter("fechaNacimiento");
            int pAnio = Integer.valueOf(fechaNacimiento.substring(0,4));
            int pMes = Integer.valueOf(fechaNacimiento.substring(5,7));
            int pDia = Integer.valueOf(fechaNacimiento.substring(8,10));
            int numeroTelefonico = Integer.parseInt(request.getParameter("numeroTelefonico"));
            String correoElectronico = request.getParameter("correoElectronico");

            controlador.registrarCliente(primerApellido, segundoApellido, nombre, identificacion, numeroTelefonico, correoElectronico, pDia, pMes, pAnio, IniciarWeb.banco);
        }
        catch (CedulaFormatException cedulaIncorrecta){
            //System.out.println(espacioEnBlanco.getLocalizedMessage());
            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet RegistroCliente</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RegistroCliente at " + cedulaIncorrecta.getLocalizedMessage() + "</h1>");
            out.println("<a href=\"index.html\"><button>Volver al menú principal</button></a>");
            out.println("</body>");
            out.println("</html>");
        }
        catch (NumberFormatException entradaInvalida){
            System.out.println("Debe ingresar un numero entero.");
            
            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet RegistroCliente</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RegistroCliente at " + "tumba la casa mami" + "</h1>");
            out.println("<a href=\"index.html\"><button>Volver al menú principal</button></a>");
            out.println("</body>");
            out.println("</html>");
            
        }

            
        /*try ( PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet RegistroCliente</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RegistroCliente at " + request.getContextPath() + "</h1>");
            out.println("<a href=\"index.html\"><button>Volver al menú principal</button></a>");
            out.println("</body>");
            out.println("</html>");
        }*/
    }
}
