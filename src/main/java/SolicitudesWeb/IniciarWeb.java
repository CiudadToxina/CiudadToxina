/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package SolicitudesWeb;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Controlador.Controlador;
import java.time.LocalDateTime;
import logicadenegocios.Banco;
import logicadenegocios.Bitacora;
import logicadenegocios.BitacoraObserver;
import logicadenegocios.BitacoraXML;
import logicadenegocios.BitacoraCSV;
import logicadenegocios.BitacoraTramaPlana;
import logicadenegocios.Registro;
import logicadenegocios.TipoAccion;
import logicadenegocios.TipoVista;

/**
 *
 * @author valef
 */
public class IniciarWeb extends HttpServlet {
    static Controlador controlador = new Controlador();
    static Banco banco = new Banco();
    static int numCuenta;
    static String pin;
    static String palabraEnviada;
    static Bitacora bitacora = new Bitacora();
    static BitacoraObserver bitacoraXML = new BitacoraXML(bitacora);
    static BitacoraObserver bitacoraCSV = new BitacoraCSV(bitacora);
    static BitacoraObserver bitacoraTramaPlana = new BitacoraTramaPlana(bitacora);
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        controlador.fullerClientes(banco);
        controlador.fullerCuentas(banco);
        controlador.fullerOperaciones(banco);
        controlador.fullerAdministrador(banco);
        controlador.fullerRegistros(bitacora);
         
        response.sendRedirect("SeleccionInicio.html");
    }
}
