package SolicitudesWeb;

import Controlador.Controlador;
import Controlador.ControladorValidaciones;
import Excepciones.BlankSpaceException;
import Excepciones.CuentaDoesNotExistException;
import Excepciones.CuentaInactivaException;
import Excepciones.PinDoesNotMatchException;
import static SolicitudesWeb.ConsultarSaldoColones.controlador;
import static SolicitudesWeb.ConsultarSaldoColones.controladorValidaciones;
import static SolicitudesWeb.RegistroCliente.controlador;
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
public class ConsultarEstadoCuenta extends HttpServlet {
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String pin;
        int numCuenta;
        
        try{
            numCuenta = Integer.parseInt(request.getParameter("numCuenta"));
            controladorValidaciones.cuentaExiste(numCuenta, IniciarWeb.banco);
            controladorValidaciones.cuentaInactiva(numCuenta, IniciarWeb.banco);

            pin = request.getParameter("pin");
            controladorValidaciones.espacioEnBlanco(pin);
            controladorValidaciones.pinCoincide(numCuenta, pin, IniciarWeb.banco);
            
            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html>"
                + "<html>"
                + "<head>"
                + "<title>Registro completo.</title>"
                + "</head>"
                + "<body>"
                + "<h1><br/>" + controlador.consultarEstadoCuentaColones(numCuenta, IniciarWeb.banco) + "</h1>"
                + "<a href=\"MenuPrincipal.html\"><button>Volver al menú principal</button></a>"
                + "</body>"
                + "</html>");
            
            controlador.crearRegistro(TipoAccion.ConsultarEstadoCuentaColones, TipoVista.WEB, IniciarWeb.bitacora);
        }
        catch (PinDoesNotMatchException pinNoCoincide){
            PrintWriter out = response.getWriter(); 
            out.println(controladorValidaciones.auxiliarWeb(pinNoCoincide.getLocalizedMessage(), "ConsultarSaldoColones"));
        }
        catch (CuentaDoesNotExistException cuentaNoExiste){
            PrintWriter out = response.getWriter();
            out.println(controladorValidaciones.auxiliarWeb(cuentaNoExiste.getLocalizedMessage(), "ConsultarSaldoColones"));
        }
        catch (NumberFormatException entradaInvalida){
            PrintWriter out = response.getWriter();
            out.println(controladorValidaciones.auxiliarWeb("Debe ingresar un número entero.", "ConsultarSaldoColones"));
        }
        catch (BlankSpaceException espacioEnBlanco){
            PrintWriter out = response.getWriter();
            out.println(controladorValidaciones.auxiliarWeb(espacioEnBlanco.getLocalizedMessage(), "ConsultarSaldoColones"));
        }
        catch (CuentaInactivaException cuentaInactiva){
            PrintWriter out = response.getWriter();
            out.println(controladorValidaciones.auxiliarWeb(cuentaInactiva.getLocalizedMessage(), "ConsultarSaldoColones"));
        }
        catch (MessagingException errorCorreo){
            PrintWriter out = response.getWriter();
            out.println(controladorValidaciones.auxiliarWeb("No se pudo enviar el correo correctamente.", "ConsultarSaldoColones"));
        }   
    }
}

