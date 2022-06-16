/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import APIs.EnvioCorreo;
import ConexionBase.ConsultasDB;
import Excepciones.AdminDoesNotExistException;
import Excepciones.BlankSpaceException;
import Excepciones.CedulaFormatException;
import Excepciones.ClienteAlreadyExistsException;
import Excepciones.ClienteDoesNotExistException;
import Excepciones.CorreoExistsException;
import Excepciones.CuentaDoesNotExistException;
import Excepciones.CuentaDoesNotHaveMoneyException;
import Excepciones.CuentaInactivaException;
import Excepciones.DateFormatException;
import Excepciones.MailFormatException;
import Excepciones.NumTelFormatException;
import Excepciones.PalabraTelefonoIncorrectaException;
import Excepciones.PinDoesNotMatchException;
import Excepciones.PinFormatException;
import Excepciones.TelefonoExistsException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.MessagingException;
import logicadenegocios.Administrador;
import logicadenegocios.Banco;
import logicadenegocios.Cliente;
import logicadenegocios.CuentaBancaria;

/**
 *
 * @author vjiucl
 */
public class ControladorValidaciones {
    ConsultasDB consultasDB = new ConsultasDB();
    
    public void pinCoincide(int pNumCuenta, String pPin, Banco pBanco) throws PinDoesNotMatchException, MessagingException{
        CuentaBancaria cuentaConsultada;
        cuentaConsultada = pBanco.buscarCuenta(pNumCuenta); 
        boolean pinCoincide = cuentaConsultada.validarPin(pPin);
        
        if(pinCoincide == false){
            int intentoFallidos = cuentaConsultada.getPinFallido();
            cuentaConsultada.setPinFallido(intentoFallidos+1);
            
            if(cuentaConsultada.getPinFallido() == 2){
                cuentaConsultada.setEstatus(false);
                consultasDB.actualizarEstatusCuenta(cuentaConsultada);
                
                Cliente clienteBuscado = pBanco.buscarDuenioCuenta(pNumCuenta);
                String correoCliente = clienteBuscado.getCorreoElectronico();
                
                EnvioCorreo correo = new EnvioCorreo();
                String mensaje = "El sistema bancario de Ciudad Toxina le informa que su cuenta " +pNumCuenta+ " ha sido bloqueada por escribir el PIN"
                + " incorrectamente 2 veces.";
                correo.enviarMensaje(correoCliente, mensaje);
                
                throw new PinDoesNotMatchException("El PIN ingresado no coincide con el PIN de la cuenta y se bloqueo su cuenta.");
            }
            else{
                throw new PinDoesNotMatchException("El PIN ingresado no coincide con el PIN de la cuenta.");
            }
        }
        cuentaConsultada.setPinFallido(0);    
    }
    
    public void cuentaExiste(int numeroCuentaProporcionado, Banco pBanco) throws CuentaDoesNotExistException{
        boolean cuentaExiste = false;
        
        for(int i=0; i<CuentaBancaria.getCantidadCuentas() ; i++){
            CuentaBancaria actual = pBanco.getTodasLasCuentas()[i];
            int numeroCuentaActual = actual.getNumeroCuenta();
            
            if(numeroCuentaActual == numeroCuentaProporcionado){
                    cuentaExiste = true;
                    break;
            }
        }
        
        if(cuentaExiste == false){
            throw new CuentaDoesNotExistException("La cuenta " + numeroCuentaProporcionado + " no existe.");
        }
    }
    
    public void clienteExiste(int identificacionCliente, Banco pBanco) throws ClienteDoesNotExistException{
        boolean clienteExiste = false;
        Cliente clienteActual;
         
        
        for(int i=0; i<Cliente.getCantidadClientes(); i++){
            clienteActual = pBanco.getTodosLosClientes()[i];
            if(clienteActual.getIdentificacion() == identificacionCliente){
                clienteExiste = true;
                break;
            }
        }
        if(clienteExiste == false){
            throw new ClienteDoesNotExistException("El cliente de identificacion " + identificacionCliente + " no esta registrado en el sistema.");
        }        
    }
    
    public void clienteRepetido(int identificacionCliente, Banco pBanco) throws ClienteAlreadyExistsException{
        Cliente clienteActual;
        
        for(int i=0; i<Cliente.getCantidadClientes(); i++){
            clienteActual = pBanco.getTodosLosClientes() [i];
            if(clienteActual.getIdentificacion() == identificacionCliente){
                throw new ClienteAlreadyExistsException("El cliente de identifiacion: " + identificacionCliente + " ya está registrado en el sistema.");
            }
        }
        
    }
    
    public void formatoPin(String pPin) throws PinFormatException{
        Pattern unaLetraMayuscula = Pattern.compile("[A-Z ]");
        Pattern unNumero = Pattern.compile("[1-9 ]");
        Pattern caracterEspecial = Pattern.compile("[^a-zA-Z0-9 ]");
         
        Matcher mat1 = unaLetraMayuscula.matcher(pPin);    
        Matcher mat2 = unNumero.matcher(pPin); 
        Matcher mat3 = caracterEspecial.matcher(pPin);
                
        if(!mat1.find() || !mat2.find() || !mat3.find() || pPin.length() != 6){
            throw new PinFormatException ("El pin debe tener 6 digitos con al menos una letra mayúscula, un número y un carácter especial.");
        }
    }
    
    public void espacioEnBlanco(String cadenaTexto) throws BlankSpaceException{
        if(cadenaTexto.equals("")){
            throw new BlankSpaceException("Debe completar todos los campos.");
        }
    }
    
    public void validarFecha(int pDia, int pMes, int pAnio) throws DateFormatException{
        try{
            LocalDate.of(pAnio, pMes, pDia);
        }
        catch(DateTimeException e){
            throw new DateFormatException("Debe ingresar una fecha valida.");
        }
        
    }  
    
    public void validarCorreo(String cadenaTexto) throws MailFormatException{
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        Matcher matcher = pattern.matcher(cadenaTexto);

        if(matcher.find() == false){
            throw new MailFormatException("Debe ingresar un correo valido.");
        }
    }
    
   public void validarCedula(String cadena) throws CedulaFormatException{
        Pattern pat = Pattern.compile("[1-9 ]");

        Matcher mat = pat.matcher(cadena);    
                
        if(!mat.find() || cadena.length() != 9){
            throw new CedulaFormatException ("La cedula debe tener 9 caracteres numericos solamente.");
        }
    }
   
   public void validarNumTel(String cadena) throws NumTelFormatException{
        Pattern pat = Pattern.compile("[1-9 ]");

        Matcher mat = pat.matcher(cadena);    
                
        if(!mat.find() || cadena.length() != 8){
            throw new NumTelFormatException ("El telefono debe tener 8 caracteres numericos solamente.");
        }
    }
   
   public void cuentaInactiva(int pNumCuenta, Banco pBanco) throws CuentaInactivaException{
        CuentaBancaria cuentaConsultada = pBanco.buscarCuenta(pNumCuenta); ;
                
        if(cuentaConsultada.getEstatus() == false){
            int intentoFallidos = cuentaConsultada.getPinFallido();
            cuentaConsultada.setPinFallido(intentoFallidos+1);
  
            throw new CuentaInactivaException("La cuenta esta inactiva y no puede realizar esta funcionalidad.");
        }
    }
   
    public void fondosInsuficientes(int pNumCuenta, int pMonto, Banco pBanco) throws CuentaDoesNotHaveMoneyException{
        CuentaBancaria cuentaConsultada = pBanco.buscarCuenta(pNumCuenta); 
                
        if(pMonto > cuentaConsultada.getSaldo()){
            throw new CuentaDoesNotHaveMoneyException("No cuenta con los fondos suficientes para esta transferencia.");
        }
    }
    
    public void telRepetido(int numTel, Banco pBanco) throws TelefonoExistsException{
        Cliente clienteActual;
        
        for(int i=0; i<Cliente.getCantidadClientes(); i++){
            clienteActual = pBanco.getTodosLosClientes() [i];
            if(clienteActual.getNumeroTelefonico() == numTel){
                throw new TelefonoExistsException("El cliente con numero: " + numTel + " ya está registrado en el sistema.");
            }
        }
    }
    
    public void correoRepetido(String correoE, Banco pBanco) throws CorreoExistsException{
        Cliente clienteActual;
        
        for(int i=0; i<Cliente.getCantidadClientes(); i++){
            clienteActual = pBanco.getTodosLosClientes() [i];
            if(clienteActual.getCorreoElectronico().equals(correoE)){
                throw new CorreoExistsException("El cliente con correo: " + correoE + " ya está registrado en el sistema.");
            }
        }
    }
    
    public void palabraTelIncorrecta(String pPalabraEnviada, String pPalabraIngresada, int pNumCuenta, Banco pBanco) throws MessagingException,
            PalabraTelefonoIncorrectaException{
        CuentaBancaria cuentaConsultada;
        cuentaConsultada = pBanco.buscarCuenta(pNumCuenta); 
        boolean palabraCoincide = false;
        
        if(pPalabraEnviada.equals("Su palabra de confirmacion es: " + pPalabraIngresada)){
            palabraCoincide = true;
        }
        
        if(palabraCoincide == false){
            int intentoFallidos = cuentaConsultada.getTelFallido();
            cuentaConsultada.setTelFallido(intentoFallidos+1);
            
            if(cuentaConsultada.getTelFallido() == 2){
                cuentaConsultada.setEstatus(false);
                consultasDB.actualizarEstatusCuenta(cuentaConsultada);
                
                Cliente clienteBuscado = pBanco.buscarDuenioCuenta(pNumCuenta);
                String correoCliente = clienteBuscado.getCorreoElectronico();
                
                EnvioCorreo correo = new EnvioCorreo();
                String mensaje = "El sistema bancario de Ciudad Toxina le informa que su cuenta ha sido bloqueada por escribir la palabra"
                + " de confirmación incorrectamente 2 veces.";
                correo.enviarMensaje(correoCliente, mensaje);
                
                throw new PalabraTelefonoIncorrectaException("La palabra ingresada no coincide con la enviada por telefono "
                        + "y se bloqueo su cuenta.");
            }
            else{
                throw new PalabraTelefonoIncorrectaException("La palabra ingresada no coincide con la enviada por telefono.");
            }
        }
        cuentaConsultada.setTelFallido(0);
    }
    
    public void credencialesAdmin(String pUsuario, String pContrasena, Banco pBanco) throws AdminDoesNotExistException{
        Administrador adminConsultado = pBanco.getMiAdmin();
        
        if(pUsuario.equals(adminConsultado.getNombreUsuario()) == false 
                || pContrasena.equals(adminConsultado.getContrasena()) == false){
            
            throw new AdminDoesNotExistException("Las credenciales de acceso son incorrectas");
        }
    }
    
    public String auxiliarWeb(String pMensaje, String pVistaWeb){
        String mensaje = "<!DOCTYPE html>"
                + "<html>"
                + "<head>"
                + "<h1>¡No se pudo completar su solicitud!</h1>"
                + "</head>"
                + "<body>"
                + "<h3><br><br/>Razón: " + pMensaje + "</h3><br><br/>"
                + "<a href=\"" + pVistaWeb + ".html\"><button>Volver</button></a>"
                + "</body>"
                + "</html>";
        return mensaje;
    }
}
