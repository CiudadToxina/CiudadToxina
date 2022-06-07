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


public class listarClientesAscendentes extends HttpServlet {
    static Controlador controlador = new Controlador();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String listaClientes = controlador.listarClientesAscendente(IniciarWeb.banco);
        String clientesSeparados = listaClientes.replace("\n","<br>");

        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Clientes listadas descendentemente</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>"+ clientesSeparados +"</br></h1>");
        out.println("<a href=\"listarClientesAscendentes.html\"><button>Volver</button></a>");
        out.println("<a href=\"MenuPrincipal.html\"><button>Volver al menú principal</button></a>");
        out.println("</body>");
        out.println("</html>");
        
    }
}
