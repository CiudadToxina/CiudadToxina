package SolicitudesWeb;

import Controlador.Controlador;
import Controlador.ControladorValidaciones;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logicadenegocios.Cliente;
import Excepciones.BlankSpaceException;
import Excepciones.ClienteDoesNotExistException;
import Excepciones.CuentaDoesNotExistException;
import static SolicitudesWeb.RegistroCliente.controladorValidaciones;
import static SolicitudesWeb.buscarCuenta.controladorValidaciones;

/**
 *
 * @author sanch
 */
public class buscarCliente extends HttpServlet {
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            response.setContentType("text/html;charset=UTF-8");
            int identificadorCliente = Integer.parseInt(request.getParameter("identificacionCliente"));
            controladorValidaciones.clienteExiste(identificadorCliente, IniciarWeb.banco);
            
            Cliente clienteEncontrado = IniciarWeb.banco.buscarCliente(identificadorCliente);
            String infoCliente = clienteEncontrado.consultarDatosCliente();
            String listaSeparada = infoCliente.replace("\n","<br>");
            
            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title></title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>" + listaSeparada + "</h1>");
            out.println("<a href=\"listarClientesAscendentes.html\"><button>Volver</button></a>");
            out.println("<a href=\"MenuPrincipal.html\"><button>Volver al menú principal</button></a>");
            out.println("</body>");
            out.println("</html>");
        }
        catch (NumberFormatException entradaInvalida){
            PrintWriter out = response.getWriter();            
            out.println(controladorValidaciones.auxiliarWeb("Debe ingresar un numero entero.", "listarClientesAscendentes"));
        }
        catch(ClienteDoesNotExistException clienteNoExiste){
            PrintWriter out = response.getWriter();
            out.println(controladorValidaciones.auxiliarWeb(clienteNoExiste.getLocalizedMessage(), "listarClientesAscendentes"));
        }
    }
}
