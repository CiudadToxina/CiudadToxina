package SolicitudesWeb;

import Controlador.Controlador;
import Controlador.ControladorValidaciones;
import Excepciones.BlankSpaceException;
import Excepciones.ClienteDoesNotExistException;
import Excepciones.CuentaDoesNotExistException;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logicadenegocios.CuentaBancaria;

/**
 *
 * @author sanch
 */
public class buscarCuenta extends HttpServlet {

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
            int numCuenta = Integer.parseInt(request.getParameter("numeroCuenta"));
            controladorValidaciones.cuentaExiste(numCuenta, IniciarWeb.banco);
            
            CuentaBancaria cuentaEncontrada = IniciarWeb.banco.buscarCuenta(numCuenta);
            String infoCuenta = cuentaEncontrada.consultarDatosCuenta();
            String infoCuentaSeparada = infoCuenta.replace("\n", "<br>");

            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title></title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>" + infoCuentaSeparada + "</h1>");
            out.println("<a href=\"listarClientesAscendentes.html\"><button>Volver</button></a>");
            out.println("<a href=\"MenuPrincipal.html\"><button>Volver al menú principal</button></a>");
            out.println("</body>");
            out.println("</html>");
        } 
        catch (NumberFormatException entradaInvalida){
            PrintWriter out = response.getWriter();
            out.println(controladorValidaciones.auxiliarWeb("Debe ingresar un numero entero.", "listarCuentasDescendentes"));
        }
        catch(CuentaDoesNotExistException cuentaNoExiste){
            PrintWriter out = response.getWriter();
            out.println(controladorValidaciones.auxiliarWeb(cuentaNoExiste.getLocalizedMessage(), "listarCuentasDescendentes"));
        }
    }
}
