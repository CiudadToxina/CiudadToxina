/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import APIs.EnvioCorreo;
import Controlador.Controlador;
import Controlador.ControladorValidaciones;
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
import Excepciones.PinFormatException;
import Excepciones.PinDoesNotMatchException;
import Excepciones.TelefonoExistsException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import javax.mail.MessagingException;
import logicadenegocios.Banco;
import logicadenegocios.Cliente;
import ConexionBase.ConexionDB;
import SolicitudesWeb.IniciarWeb;
import java.time.LocalDateTime;
import logicadenegocios.Bitacora;
import logicadenegocios.BitacoraCSV;
import logicadenegocios.BitacoraObserver;
import logicadenegocios.BitacoraTramaPlana;
import logicadenegocios.BitacoraXML;
import logicadenegocios.CuentaBancaria;
import logicadenegocios.Operacion;
import logicadenegocios.Registro;
import logicadenegocios.TipoAccion;
import logicadenegocios.TipoVista;

/**
 *
 * @author valef
 */

public class VistaCLI {
    static Banco banco = new Banco();
    //Estas son las declaraciones de los objetos de la entrada y salida estándar
    static BufferedReader in;
    static PrintStream out;
    static EnvioCorreo correo = new EnvioCorreo();
    static Controlador controlador = new Controlador();
    static ControladorValidaciones controladorValidaciones = new ControladorValidaciones();
    static Bitacora bitacora = new Bitacora();
    static BitacoraObserver bitacoraXML = new BitacoraXML(bitacora);
    static BitacoraObserver bitacoraCSV = new BitacoraCSV(bitacora);
    static BitacoraObserver bitacoraTramaPlana = new BitacoraTramaPlana(bitacora);
    
    /**
     * Ejecuta la aplicación
     */
    public static void main(String[] args)throws Exception{
        /*Se crean los objetos tanto para la entrada de datos desde el
        teclado (in) como para la salida de datos a la consola (out)*/
        in = new BufferedReader( new InputStreamReader( System.in ) );
        out = System.out;
        boolean noSalir = true;
        int opcion;
        prueba();
      
        /*controlador.fullerClientes(banco);
        controlador.fullerCuentas(banco);
        controlador.fullerOperaciones(banco);
        controlador.fullerAdministrador(banco);
        controlador.fullerRegistros(bitacora);*/
                
        do {
            mostrarMenu();
            opcion = leerOpcion();
            noSalir = ejecutarAccion(opcion);
        } while (noSalir);
    }
    
    /**
     * Permite mostrar en pantalla el menu
    */
    static void mostrarMenu() {
        out.println("1. Registrar Cliente");
        out.println("2. Crear cuenta");
        out.println("3. Listar clientes ascendentemente");
        out.println("4. Listar todas las cuentas creadas");
        out.println("5. Cambiar PIN");
        out.println("6. Realizar deposito en colones");
        out.println("7. Realizar deposito en dolares");
        out.println("8. Realizar retiro colones");
        out.println("9. Realizar retiro dolares");
        out.println("10. Realizar transferencia para moneda local");
        out.println("11. Consultar tipo de cambio de compra de divisa extranjera");
        out.println("12. Consultar tipo de cambio de venta de divisa extranjera");
        out.println("13. Consultar saldo actual");
        out.println("14. Consultar saldo actual en dolares");
        out.println("15. Consultar estado de cuenta colones");
        out.println("16. Consultar estado de cuenta en dolares");
        out.println("17. Consultar estatus de una cuenta");
        out.println("18. Consultar ganancias del banco producto del cobro de comisiones TOTALIZADO");
        out.println("19. Consultar ganancias del banco producto del cobro de comisiones para una cuenta");
        out.println("20. Salir");
    }
    
    /**
     * Permite leer la opcion seleccionada por el usuario en el menu
     * @return opcion seleccionada por el usuario
     * @throws IOException si el usuario ingresa datos incorrectos en el programa
     */
    static int leerOpcion()throws java.io.IOException{
        try{
            int opcion;
            out.print("Seleccione su opcion: ");
            opcion = Integer.parseInt(in.readLine());
            out.println();
            return opcion;
        }
        catch (NumberFormatException entradaInvalida){
            System.out.println("Debe ingresar un numero entero.");
        }   
        return 0;
    }
       
    /**
     * Permite ejecutar la accion seleccionada por el usuario en el menu
     */
    static boolean ejecutarAccion(int opcion)throws Exception{
        boolean noSalir = true;
        int opc = 0;
        switch(opcion){
            case 1: 
                solicitarDatosCliente();
                break;
            case 2: 
                solicitarDatosCuenta();
                break;
            case 3: 
                listarClientesAscendente(); 
                break;
            case 4: 
                listarCuentasDescendente();
                break;
            case 5: 
                solicitarDatosPin();
                break;
            case 6: 
                realizarDepositoColones();
                break;
            case 7:
                realizarDepositoDolares();
                break;
            case 8: 
                realizarRetiroColones();
                break;
             case 9: 
                realizarRetiroDolares();
                break;
            case 10: 
                realizarTransferenciaLocal();
                break;
            case 11: 
                consultarCompraDivisaExtranjera();
                break;
            case 12: 
                consultarVentaDivisaExtranjera();
                break;
            case 13: 
                consultarSaldoActual();
                break;
            case 14: 
                consultarSaldoActualDivisaExtranjera();
                break;               
            case 15: 
                consultarEstadoCuentaLocal();
                break;
            case 16: 
                consultarEstadoCuentaExtranjero();
                break;    
            case 17: 
                consultarEstatusCuenta();
                break;
            case 18: 
                consultarGananciasBanco();
                break;
            case 19: 
                consultarGananciasCuenta();
                break;                
            case 20:
                noSalir = false;
                break;
            default: 
                out.println("Opción no válida");
                break;
        }//fin del switch
        out.println();
        return noSalir;
    }
    
    static void solicitarDatosCliente() throws IOException, BlankSpaceException, ClienteAlreadyExistsException,
            DateFormatException, CedulaFormatException, NumTelFormatException, MailFormatException,
            TelefonoExistsException, CorreoExistsException{
        String primerApellido;
        String segundoApellido;
        String nombre;
        int identificacion;
        int dia;
        int mes;
        int anio;
        int numeroTelefonico;
        String correoElectronico;
        
        
        try{
            out.print("Ingrese el primer apellido del cliente: ");
            primerApellido = in.readLine();
            controladorValidaciones.espacioEnBlanco(primerApellido);
            
            out.print("Ingrese el segundo apellido del cliente: ");
            segundoApellido = in.readLine();
            controladorValidaciones.espacioEnBlanco(segundoApellido);

            out.print("Ingrese el nombre del cliente: ");
            nombre = in.readLine();
            controladorValidaciones.espacioEnBlanco(nombre);

            out.print("Ingrese la identificacion del cliente: ");
            identificacion = Integer.parseInt(in.readLine());
            controladorValidaciones.validarCedula(Integer.toString(identificacion));
            controladorValidaciones.clienteRepetido(identificacion, banco); 

            out.print("Ingrese el dia de nacimiento del cliente: ");
            dia = Integer.parseInt(in.readLine());

            out.print("Ingrese el mes de nacimiento del cliente: ");
            mes = Integer.parseInt(in.readLine());

            out.print("Ingrese el anio de nacimiento del cliente: ");
            anio = Integer.parseInt(in.readLine());
            controladorValidaciones.validarFecha(dia, mes, anio); 

            out.print("Ingrese el numero telefonico del cliente: ");
            numeroTelefonico = Integer.parseInt(in.readLine());
            controladorValidaciones.validarNumTel(Integer.toString(numeroTelefonico));
            controladorValidaciones.telRepetido(numeroTelefonico, banco); 

            out.print("Ingrese el correo electronico del cliente: ");
            correoElectronico = in.readLine();
            controladorValidaciones.espacioEnBlanco(correoElectronico);
            controladorValidaciones.validarCorreo(correoElectronico);
            controladorValidaciones.correoRepetido(correoElectronico, banco);
            
            out.print(controlador.registrarCliente(primerApellido, segundoApellido, nombre, identificacion, numeroTelefonico, correoElectronico, dia, mes, anio, banco));
            controlador.crearRegistro(TipoAccion.RegistrarCliente , TipoVista.CLI, bitacora);
        }
        catch (BlankSpaceException espacioEnBlanco){
            System.out.println(espacioEnBlanco.getLocalizedMessage());
        }
        catch (NumberFormatException entradaInvalida){
            System.out.println("Debe ingresar un numero entero.");
        }
        catch (ClienteAlreadyExistsException clienteExiste){
            System.out.println(clienteExiste.getLocalizedMessage());
        }
        catch (DateFormatException fechaIncorrecta){
            System.out.println(fechaIncorrecta.getLocalizedMessage());
        }   
        catch (CedulaFormatException cedulaIncorrecta){
            System.out.println(cedulaIncorrecta.getLocalizedMessage());
        } 
        catch (NumTelFormatException telIncorrecto){
            System.out.println(telIncorrecto.getLocalizedMessage());
        } 
        catch (MailFormatException correoIncorrecto){
            System.out.println(correoIncorrecto.getLocalizedMessage());
        }
        catch (TelefonoExistsException telefonoExiste){
            System.out.println(telefonoExiste.getLocalizedMessage());
        }
        catch (CorreoExistsException correoExiste){
            System.out.println(correoExiste.getLocalizedMessage());
        }
    }
    
    static void solicitarDatosCuenta() throws IOException, NumberFormatException, ClienteDoesNotExistException, PinFormatException,
            BlankSpaceException{
        int identificacionCliente;
        String PIN;
        int depositoInicial;
        Cliente duenioCuenta;

        try{
            out.print("Ingrese la identificacion del cliente: ");
            identificacionCliente = Integer.parseInt(in.readLine());
            controladorValidaciones.clienteExiste(identificacionCliente, banco); 
            duenioCuenta = banco.buscarCliente(identificacionCliente);
            
            out.print("Ingrese el PIN asignado a la cuenta: ");
            PIN = in.readLine();
            controladorValidaciones.espacioEnBlanco(PIN);
            controladorValidaciones.formatoPin(PIN);
            
            out.print("Ingrese el monto de deposito inicial para la cuenta: ");
            depositoInicial = Integer.parseInt(in.readLine());
            
            out.print(controlador.registrarCuenta(depositoInicial, PIN, duenioCuenta, banco));  
            controlador.crearRegistro(TipoAccion.CrearCuenta, TipoVista.CLI, bitacora);
        }
        catch (NumberFormatException entradaInvalida){
            System.out.println("Debe ingresar un numero entero.");
        }
        catch(ClienteDoesNotExistException clienteNoExiste){
            System.out.println(clienteNoExiste.getLocalizedMessage());
        }
        catch (PinFormatException formatoPin){
            System.out.println(formatoPin.getLocalizedMessage());
        }
        catch (BlankSpaceException espacioEnBlanco){
            System.out.println(espacioEnBlanco.getLocalizedMessage());
        }

    }    
    
    static void listarClientesAscendente() throws IOException, NumberFormatException, ClienteDoesNotExistException{
        int identificacionCliente;
        int opcionConsulta;
        
        out.print(controlador.listarClientesAscendente(banco));
        controlador.crearRegistro(TipoAccion.ListarClientesAscendente , TipoVista.CLI, bitacora);
        try{
            out.print("Ingrese 1 si desea consultar un cliente o cualquier tecla para volver al menu principal: ");
            opcionConsulta = Integer.parseInt(in.readLine());
            if (opcionConsulta == 1){
                out.print("Ingrese la identificacion del cliente por consultar: ");
                identificacionCliente = Integer.parseInt(in.readLine());
                controladorValidaciones.clienteExiste(identificacionCliente, banco);
                out.print(controlador.consultarCliente(identificacionCliente, banco)); 
                controlador.crearRegistro(TipoAccion.BuscarCliente , TipoVista.CLI, bitacora);
            }
        }
        catch (NumberFormatException entradaInvalida){
            System.out.println("Debe ingresar un numero entero.");
        }
        catch(ClienteDoesNotExistException clienteNoExiste){
            System.out.println(clienteNoExiste.getLocalizedMessage());
        }
    }
    
    static void listarCuentasDescendente()throws IOException, NumberFormatException, CuentaDoesNotExistException{
        int numCuenta;
        int opcionConsulta;
        
        out.print(controlador.listarCuentasDescendente(banco));
        controlador.crearRegistro(TipoAccion.ListarCuentasDescendente, TipoVista.CLI, bitacora);
        try{
            out.print("Ingrese 1 si desea consultar una cuenta o cualquier tecla para volver al menu principal: ");
            opcionConsulta = Integer.parseInt(in.readLine());
            if (opcionConsulta == 1){
                out.print("Ingrese el numero de cuenta por consultar: ");
                numCuenta = Integer.parseInt(in.readLine());
                controladorValidaciones.cuentaExiste(numCuenta, banco);
                out.print(controlador.consultarCuenta(numCuenta, banco));  
                controlador.crearRegistro(TipoAccion.BuscarCuenta , TipoVista.CLI, bitacora);
            }         
        }
        catch (NumberFormatException entradaInvalida){
            System.out.println("Debe ingresar un numero entero.");
        }
        catch(CuentaDoesNotExistException cuentaNoExiste){
            System.out.println(cuentaNoExiste.getLocalizedMessage());
        }
    }
       
    static void solicitarDatosPin() throws IOException, CuentaDoesNotExistException, PinDoesNotMatchException, PinFormatException, BlankSpaceException,
            CuentaInactivaException, MessagingException{
        String pin;
        String nuevoPin;
        int numCuenta;
        
        try{
            out.print("Ingrese el numero de cuenta por consultar: ");
            numCuenta = Integer.parseInt(in.readLine());
            controladorValidaciones.cuentaExiste(numCuenta, banco);
            controladorValidaciones.cuentaInactiva(numCuenta, banco);

            out.print("Ingrese su PIN actual: ");
            pin = in.readLine();
            controladorValidaciones.espacioEnBlanco(pin);
            controladorValidaciones.pinCoincide(numCuenta, pin, banco);
            
            out.print("Ingrese su nuevo PIN: ");
            nuevoPin = in.readLine();
            controladorValidaciones.espacioEnBlanco(nuevoPin);
            controladorValidaciones.formatoPin(nuevoPin);
            
            out.println(controlador.cambiarPin(nuevoPin, numCuenta, banco));
            controlador.crearRegistro(TipoAccion.CambiarPin, TipoVista.CLI, bitacora);
        }
        catch (PinDoesNotMatchException pinNoCoincide){
            System.out.println(pinNoCoincide.getLocalizedMessage());
        }
        catch (CuentaDoesNotExistException cuentaNoExiste){
            System.out.println(cuentaNoExiste.getLocalizedMessage());
        }
        catch (NumberFormatException entradaInvalida){
            System.out.println("Debe ingresar un número entero.");
        }
        catch (CuentaInactivaException cuentaInactiva){
            System.out.println(cuentaInactiva.getLocalizedMessage());
        }
        catch (PinFormatException formatoPin){
            System.out.println(formatoPin.getLocalizedMessage());
        }
        catch (BlankSpaceException espacioEnBlanco){
            System.out.println(espacioEnBlanco.getLocalizedMessage());
        }
        catch (MessagingException errorCorreo){
            System.out.println("No se pudo enviar el correo correctamente.");
            System.out.println(errorCorreo.getMessage());
        }
        
    }
    
    static void realizarDepositoColones() throws IOException, CuentaDoesNotExistException{
        int numCuenta;
        int montoDeposito;
        
        try{
            out.print("Ingrese el número de cuenta al que va a depositar: ");
            numCuenta = Integer.parseInt(in.readLine());
            controladorValidaciones.cuentaExiste(numCuenta, banco);

            out.print("Ingrese el monto en colones que va a depositar: ");
            montoDeposito = Integer.parseInt(in.readLine()); 
            
            out.println(controlador.depositarColones(numCuenta, montoDeposito, banco));
            controlador.crearRegistro(TipoAccion.DepositoColones, TipoVista.CLI, bitacora);
        }
        catch (NumberFormatException entradaInvalida){
            System.out.println("Debe ingresar un número entero.");
        }
        catch (CuentaDoesNotExistException cuentaNoExiste){
            System.out.println(cuentaNoExiste.getLocalizedMessage());
        }   
    }
    
    static void realizarDepositoDolares() throws IOException, CuentaDoesNotExistException{
        int numCuenta;
        int montoDeposito;
        
        try{
            out.print("Ingrese el número de cuenta al que va a depositar: ");
            numCuenta = Integer.parseInt(in.readLine());
            controladorValidaciones.cuentaExiste(numCuenta, banco);

            out.print("Ingrese el monto en dolares que va a depositar: ");
            montoDeposito = Integer.parseInt(in.readLine()); 
            
            out.println(controlador.depositarDolares(numCuenta, montoDeposito, banco));
            controlador.crearRegistro(TipoAccion.DepositoDolares, TipoVista.CLI, bitacora);
        }
        catch (NumberFormatException entradaInvalida){
            System.out.println("Debe ingresar un número entero.");
        }
        catch (CuentaDoesNotExistException cuentaNoExiste){
            System.out.println(cuentaNoExiste.getLocalizedMessage());
        }
    }
    
    static void realizarRetiroColones() throws IOException, CuentaDoesNotExistException, CuentaInactivaException, 
            PinDoesNotMatchException, CuentaDoesNotHaveMoneyException, BlankSpaceException, PalabraTelefonoIncorrectaException, MessagingException{
        int numCuenta;
        String pin;
        String palabraEnviada;
        String palabraIngresada;
        int montoRetiro;
        int cantPalabraErronea = 0;
        
        
        try{
            out.print("Ingrese el número de cuenta del que va a retirar: ");
            numCuenta = Integer.parseInt(in.readLine());
            controladorValidaciones.cuentaExiste(numCuenta, banco);
            controladorValidaciones.cuentaInactiva(numCuenta, banco);
            
            out.print("Ingrese el PIN de la cuenta: ");
            pin = in.readLine(); 
            controladorValidaciones.espacioEnBlanco(pin);
            controladorValidaciones.pinCoincide(numCuenta, pin, banco);
            
            palabraEnviada = controlador.enviarMensaje(numCuenta, banco);
            out.print("Indique la palabra de confirmacion enviada su numero telefonico: ");
            palabraIngresada = in.readLine(); 
            controladorValidaciones.espacioEnBlanco(palabraIngresada);
            controladorValidaciones.palabraTelIncorrecta(palabraEnviada, palabraIngresada, numCuenta, banco);
            
            out.print("Ingrese el monto en colones que va a retirar: ");
            montoRetiro = Integer.parseInt(in.readLine()); 
            controladorValidaciones.fondosInsuficientes(numCuenta, montoRetiro, banco);
            
            out.println(controlador.retirarColones(numCuenta, montoRetiro, banco));
            controlador.crearRegistro(TipoAccion.RetiroColones, TipoVista.CLI, bitacora);
        }
        catch (CuentaDoesNotExistException cuentaNoExiste){
            System.out.println(cuentaNoExiste.getLocalizedMessage());
        }   
        catch (CuentaInactivaException cuentaInactiva){
            System.out.println(cuentaInactiva.getLocalizedMessage());
        }
        catch (PinDoesNotMatchException pinNoCoincide){
            System.out.println(pinNoCoincide.getLocalizedMessage());
        }
        catch (NumberFormatException entradaInvalida){
            System.out.println("Debe ingresar un número entero.");
        }
        catch (CuentaDoesNotHaveMoneyException cuentaSinFondosSuficientes){
            System.out.println(cuentaSinFondosSuficientes.getLocalizedMessage());
        }
        catch (BlankSpaceException espacioEnBlanco){
            System.out.println(espacioEnBlanco.getLocalizedMessage());
        }
        catch (PalabraTelefonoIncorrectaException palabraIncorrecta){
            System.out.println(palabraIncorrecta.getLocalizedMessage());
        }
        catch (MessagingException errorCorreo){
            System.out.println("No se pudo enviar el correo correctamente.");
        }
    }
    
    static void realizarRetiroDolares() throws IOException, CuentaDoesNotExistException, CuentaInactivaException, 
            PinDoesNotMatchException, CuentaDoesNotHaveMoneyException, BlankSpaceException, PalabraTelefonoIncorrectaException, MessagingException{
        int numCuenta;
        String pin;
        String palabraEnviada;
        String palabraIngresada;
        int montoRetiro;
        
        try{
            out.print("Ingrese el número de cuenta del que va a retirar: ");
            numCuenta = Integer.parseInt(in.readLine());
            controladorValidaciones.cuentaExiste(numCuenta, banco);
            controladorValidaciones.cuentaInactiva(numCuenta, banco);
            
            out.print("Ingrese el PIN de la cuenta: ");
            pin = in.readLine(); 
            controladorValidaciones.espacioEnBlanco(pin);
            controladorValidaciones.pinCoincide(numCuenta, pin, banco);
            
            palabraEnviada = controlador.enviarMensaje(numCuenta, banco);
            out.print("Indique la palabra de confirmacion enviada su numero telefonico: ");
            palabraIngresada = in.readLine(); 
            controladorValidaciones.espacioEnBlanco(palabraIngresada);
            controladorValidaciones.palabraTelIncorrecta(palabraEnviada, palabraIngresada, numCuenta, banco);

            out.print("Ingrese el monto en dolares que va a retirar: ");
            montoRetiro = Integer.parseInt(in.readLine()); 
            controladorValidaciones.fondosInsuficientes(numCuenta, montoRetiro, banco);
            
            out.println(controlador.retirarDolares(numCuenta, montoRetiro, banco));
            controlador.crearRegistro(TipoAccion.RetiroDolares, TipoVista.CLI, bitacora);
        }
        catch (CuentaDoesNotExistException cuentaNoExiste){
            System.out.println(cuentaNoExiste.getLocalizedMessage());
        }   
        catch (CuentaInactivaException cuentaInactiva){
            System.out.println(cuentaInactiva.getLocalizedMessage());
        }
        catch (PinDoesNotMatchException pinNoCoincide){
            System.out.println(pinNoCoincide.getLocalizedMessage());
        }
        catch (NumberFormatException entradaInvalida){
            System.out.println("Debe ingresar un número entero.");
        }
        catch (CuentaDoesNotHaveMoneyException cuentaSinFondosSuficientes){
            System.out.println(cuentaSinFondosSuficientes.getLocalizedMessage());
        }
        catch (BlankSpaceException espacioEnBlanco){
            System.out.println(espacioEnBlanco.getLocalizedMessage());
        }
        catch (PalabraTelefonoIncorrectaException palabraIncorrecta){
            System.out.println(palabraIncorrecta.getLocalizedMessage());
        }
        catch (MessagingException errorCorreo){
            System.out.println("No se pudo enviar el correo correctamente.");
        }
    }
    
    static void realizarTransferenciaLocal() throws IOException, CuentaDoesNotExistException, PinDoesNotMatchException, PinFormatException,
            BlankSpaceException, CuentaInactivaException, CuentaDoesNotHaveMoneyException, PalabraTelefonoIncorrectaException, MessagingException{
        String pin;
        String palabraEnviada;
        String palabraIngresada;
        int numCuentaOrigen;
        int numCuentaDestino;
        int montoDeposito;
        
        try{
            out.print("Ingrese su número de cuenta: ");
            numCuentaOrigen = Integer.parseInt(in.readLine());
            controladorValidaciones.cuentaExiste(numCuentaOrigen, banco);
            controladorValidaciones.cuentaInactiva(numCuentaOrigen, banco);

            out.print("Ingrese su PIN actual: ");
            pin = in.readLine();
            controladorValidaciones.espacioEnBlanco(pin);
            controladorValidaciones.pinCoincide(numCuentaOrigen, pin, banco);
            
            palabraEnviada = controlador.enviarMensaje(numCuentaOrigen, banco);
            out.print("Indique la palabra de confirmacion enviada su numero telefonico: ");
            palabraIngresada = in.readLine(); 
            controladorValidaciones.espacioEnBlanco(palabraIngresada);
            controladorValidaciones.palabraTelIncorrecta(palabraEnviada, palabraIngresada, numCuentaOrigen, banco);
            
            out.print("Ingrese el numero de cuenta a depositar: ");
            numCuentaDestino = Integer.parseInt(in.readLine());
            controladorValidaciones.cuentaExiste(numCuentaDestino, banco);
            controladorValidaciones.cuentaInactiva(numCuentaDestino, banco);

            out.print("Ingrese el monto en colones que va a transferir: ");
            montoDeposito = Integer.parseInt(in.readLine());
            controladorValidaciones.fondosInsuficientes(numCuentaDestino, montoDeposito, banco);
            
            out.println(controlador.realizarTransferencias(numCuentaOrigen, numCuentaDestino, montoDeposito, banco));
            controlador.crearRegistro(TipoAccion.TransferenciaColones, TipoVista.CLI, bitacora);
        }
        catch (PinDoesNotMatchException pinNoCoincide){
            System.out.println(pinNoCoincide.getLocalizedMessage());
        }
        catch (CuentaDoesNotExistException cuentaNoExiste){
            System.out.println(cuentaNoExiste.getLocalizedMessage());
        }
        catch (NumberFormatException entradaInvalida){
            System.out.println("Debe ingresar un número entero.");
        }
        catch (CuentaInactivaException cuentaInactiva){
            System.out.println(cuentaInactiva.getLocalizedMessage());
        }
        catch (BlankSpaceException espacioEnBlanco){
            System.out.println(espacioEnBlanco.getLocalizedMessage());
        }
        catch (CuentaDoesNotHaveMoneyException fondosInsuficientes){
            System.out.println(fondosInsuficientes.getLocalizedMessage());
        }  
        catch (PalabraTelefonoIncorrectaException palabraIncorrecta){
            System.out.println(palabraIncorrecta.getLocalizedMessage());
        }
        catch (MessagingException errorCorreo){
            System.out.println("No se pudo enviar el correo correctamente.");
        }
    }
    
    static void consultarSaldoActual() throws IOException, CuentaDoesNotExistException, PinDoesNotMatchException,
            BlankSpaceException, CuentaInactivaException, MessagingException{
        String pin;
        int numCuenta;
        
        try{
            out.print("Ingrese el numero de cuenta por consultar: ");
            numCuenta = Integer.parseInt(in.readLine());
            controladorValidaciones.cuentaExiste(numCuenta, banco);
            controladorValidaciones.cuentaInactiva(numCuenta, banco);

            out.print("Ingrese su PIN actual: ");
            pin = in.readLine();
            controladorValidaciones.espacioEnBlanco(pin);
            controladorValidaciones.pinCoincide(numCuenta, pin, banco);
            
            out.println(controlador.consultarSaldoColones(numCuenta, banco));
            controlador.crearRegistro(TipoAccion.ConsultarSaldoColones, TipoVista.CLI, bitacora);
        }
        catch (PinDoesNotMatchException pinNoCoincide){
            System.out.println(pinNoCoincide.getLocalizedMessage());
        }
        catch (CuentaDoesNotExistException cuentaNoExiste){
            System.out.println(cuentaNoExiste.getLocalizedMessage());
        }
        catch (NumberFormatException entradaInvalida){
            System.out.println("Debe ingresar un número entero.");
        }
        catch (BlankSpaceException espacioEnBlanco){
            System.out.println(espacioEnBlanco.getLocalizedMessage());
        }
        catch (CuentaInactivaException cuentaInactiva){
            System.out.println(cuentaInactiva.getLocalizedMessage());
        }
        catch (MessagingException errorCorreo){
            System.out.println("No se pudo enviar el correo correctamente.");
        }
    }
    
    static void consultarSaldoActualDivisaExtranjera() throws IOException, CuentaDoesNotExistException, PinDoesNotMatchException,
            BlankSpaceException, CuentaInactivaException, MessagingException{
        String pin;
        int numCuenta;
        
        try{
            out.print("Ingrese el numero de cuenta por consultar: ");
            numCuenta = Integer.parseInt(in.readLine());
            controladorValidaciones.cuentaExiste(numCuenta, banco);
            controladorValidaciones.cuentaInactiva(numCuenta, banco);

            out.print("Ingrese su PIN actual: ");
            pin = in.readLine();
            controladorValidaciones.espacioEnBlanco(pin);
            controladorValidaciones.pinCoincide(numCuenta, pin, banco);
            
            out.println(controlador.consultarSaldoDolares(numCuenta, banco));
            controlador.crearRegistro(TipoAccion.ConsultarSaldoDolares, TipoVista.CLI, bitacora);
        }
        catch (CuentaDoesNotExistException cuentaNoExiste){
            System.out.println(cuentaNoExiste.getLocalizedMessage());
        }
        catch (NumberFormatException entradaInvalida){
            System.out.println("Debe ingresar un número entero.");
        }
        catch (BlankSpaceException espacioEnBlanco){
            System.out.println(espacioEnBlanco.getLocalizedMessage());
        }
        catch (CuentaInactivaException cuentaInactiva){
            System.out.println(cuentaInactiva.getLocalizedMessage());
        }
        catch (MessagingException errorCorreo){
            System.out.println("No se pudo enviar el correo correctamente.");
        }
    }        
    
    static void consultarCompraDivisaExtranjera(){
        System.out.println(controlador.consultarCompraDolar());
        controlador.crearRegistro(TipoAccion.ConsultarCompraDolar, TipoVista.CLI, bitacora);
    }
    
    static void consultarVentaDivisaExtranjera(){
        System.out.println(controlador.consultarVentaDolar());
        controlador.crearRegistro(TipoAccion.ConsultarVentaDolar, TipoVista.CLI, bitacora);
    }
    
    static void consultarEstadoCuentaLocal() throws IOException, CuentaDoesNotExistException, PinDoesNotMatchException,
            CuentaInactivaException, MessagingException{
        String pin;
        int numCuenta;
        
        try{
            out.print("Ingrese el numero de cuenta por consultar: ");
            numCuenta = Integer.parseInt(in.readLine());
            controladorValidaciones.cuentaExiste(numCuenta, banco);

            out.print("Ingrese su PIN actual: ");
            pin = in.readLine();
            controladorValidaciones.espacioEnBlanco(pin);
            controladorValidaciones.pinCoincide(numCuenta, pin, banco);
            controladorValidaciones.cuentaInactiva(numCuenta, banco);
            
            out.println(controlador.consultarEstadoCuentaColones(numCuenta, banco));
            controlador.crearRegistro(TipoAccion.ConsultarEstadoCuentaColones, TipoVista.CLI, bitacora);
        }
        catch (PinDoesNotMatchException pinNoCoincide){
            System.out.println(pinNoCoincide.getLocalizedMessage());
        }
        catch (CuentaDoesNotExistException cuentaNoExiste){
            System.out.println(cuentaNoExiste.getLocalizedMessage());
        }
        catch (NumberFormatException entradaInvalida){
            System.out.println("Debe ingresar un número entero.");
        }
        catch (BlankSpaceException espacioEnBlanco){
            System.out.println(espacioEnBlanco.getLocalizedMessage());
        }
        catch (CuentaInactivaException cuentaInactiva){
            System.out.println(cuentaInactiva.getLocalizedMessage());
        }
        catch (MessagingException errorCorreo){
            System.out.println("No se pudo enviar el correo correctamente.");
        }
    }
    
    static void consultarEstadoCuentaExtranjero() throws IOException, CuentaDoesNotExistException, PinDoesNotMatchException,
            CuentaInactivaException, MessagingException{
        String pin;
        int numCuenta;
        
        try{
            out.print("Ingrese el numero de cuenta por consultar: ");
            numCuenta = Integer.parseInt(in.readLine());
            controladorValidaciones.cuentaExiste(numCuenta, banco);

            out.print("Ingrese su PIN actual: ");
            pin = in.readLine();
            controladorValidaciones.espacioEnBlanco(pin);
            controladorValidaciones.pinCoincide(numCuenta, pin, banco);
            controladorValidaciones.cuentaInactiva(numCuenta, banco);
            
            out.println(controlador.consultarEstadoCuentaDolares(numCuenta, banco));
            controlador.crearRegistro(TipoAccion.ConsultarEstadoCuentaDolares, TipoVista.CLI, bitacora);
        }
        catch (PinDoesNotMatchException pinNoCoincide){
            System.out.println(pinNoCoincide.getLocalizedMessage());
        }
        catch (CuentaDoesNotExistException cuentaNoExiste){
            System.out.println(cuentaNoExiste.getLocalizedMessage());
        }
        catch (NumberFormatException entradaInvalida){
            System.out.println("Debe ingresar un número entero.");
        }
        catch (BlankSpaceException espacioEnBlanco){
            System.out.println(espacioEnBlanco.getLocalizedMessage());
        }
        catch (CuentaInactivaException cuentaInactiva){
            System.out.println(cuentaInactiva.getLocalizedMessage());
        }
        catch (MessagingException errorCorreo){
            System.out.println("No se pudo enviar el correo correctamente.");
        }
    }
    
    static void consultarEstatusCuenta() throws IOException, CuentaDoesNotExistException, PinDoesNotMatchException, PinFormatException, BlankSpaceException{
        int numCuenta;
        
        try{
            out.print("Ingrese el numero de cuenta por consultar: ");
            numCuenta = Integer.parseInt(in.readLine());
            controladorValidaciones.cuentaExiste(numCuenta, banco);
            
            out.println(controlador.consultarEstatusCuenta(numCuenta, banco));
            controlador.crearRegistro(TipoAccion.ConsultarEstatusCuenta, TipoVista.CLI, bitacora);
        }
        catch (CuentaDoesNotExistException cuentaNoExiste){
            System.out.println(cuentaNoExiste.getLocalizedMessage());
        }
        catch (NumberFormatException entradaInvalida){
            System.out.println("Debe ingresar un número entero.");
        }
    }
    
    static void consultarGananciasBanco(){
        System.out.println(controlador.consultarGananciasBanco(banco));
        controlador.crearRegistro(TipoAccion.ConsultarGananciasTotalesBanco, TipoVista.CLI, bitacora);
        
    }
    
    static void consultarGananciasCuenta() throws IOException{
        int numCuenta;
        
        try{
            out.print("Ingrese el numero de cuenta por consultar: ");
            numCuenta = Integer.parseInt(in.readLine());
            controladorValidaciones.cuentaExiste(numCuenta, banco);
            
            System.out.println(controlador.consultarGananciasCuenta(numCuenta, banco));
            controlador.crearRegistro(TipoAccion.ConsultarGananciasPorCuenta, TipoVista.CLI, bitacora);
        }
        catch (CuentaDoesNotExistException cuentaNoExiste){
            System.out.println(cuentaNoExiste.getLocalizedMessage());
        }
        catch (NumberFormatException entradaInvalida){
            System.out.println("Debe ingresar un número entero.");
        }
    }
    
    static void prueba(){
        controlador.registrarCliente("Sanchez", "Leiva", "Carlos", 305400520, 84784736, "candressanchez31@gmail.com", 31, 3, 2002, banco);
        Cliente Cliente1 = banco.buscarCliente(305400520);
        controlador.registrarCuenta(2000000, "abcA1#", Cliente1, banco);
            
        controlador.registrarCliente("Fernández", "Barquero", "Valeria", 402570359, 62967740, "valeria700602@gmail.com", 27, 9, 2002, banco);
        Cliente Cliente2 = banco.buscarCliente(402570359);
        controlador.registrarCuenta(1500000, "abcA2#", Cliente2, banco);
        
        controlador.registrarCliente("Acuña", "Coto", "Víctor", 117450718, 71051146, "victoracuna99@gmail.com", 9, 6, 1999, banco);
        Cliente Cliente3 = banco.buscarCliente(117450718);
        controlador.registrarCuenta(3500000, "abcA3#", Cliente3, banco);
        
        controlador.registrarCliente("Sanchez", "Pereira", "Daniel", 118000504, 83149643, "trabajodanielpereira@gmail.com", 25, 1, 2001, banco);
        Cliente Cliente4 = banco.buscarCliente(118000504);
        controlador.registrarCuenta(1550000, "abcA4#", Cliente4, banco);
                
        controlador.crearRegistro(TipoAccion.BuscarCliente, TipoVista.CLI, bitacora);   
        controlador.crearRegistro(TipoAccion.CambiarPin, TipoVista.CLI, bitacora);
        
        controlador.crearRegistro(TipoAccion.CrearCuenta, TipoVista.WEB, bitacora);
        controlador.crearRegistro(TipoAccion.ConsultarGananciasTotalesBanco, TipoVista.WEB, bitacora);
        
        controlador.crearRegistro(TipoAccion.ConsultarGananciasPorCuenta, TipoVista.GUI, bitacora);
        controlador.crearRegistro(TipoAccion.ConsultarEstatusCuenta, TipoVista.GUI, bitacora);
        
    }
}
