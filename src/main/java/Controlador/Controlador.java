/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import APIs.EnvioSMS;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import logicadenegocios.Banco;
import logicadenegocios.Cliente;
import logicadenegocios.CuentaBancaria;
import logicadenegocios.TipoOperacion;
import utilidades.ObtenerTipoCambio;
import utilidades.Ordenamiento;
import ConexionBase.ConsultasDB;
import java.sql.ResultSet;
import java.sql.SQLException;
import logicadenegocios.Administrador;
import logicadenegocios.Operacion;
import logicadenegocios.OperacionAdministrativa;
import logicadenegocios.OperacionMonetaria;
import logicadenegocios.Registro;
import logicadenegocios.TipoAccion;
import logicadenegocios.TipoVista;

/**
 *
 * @author valef
 */
public class Controlador {
    DecimalFormat redondear = new DecimalFormat("###.##");
    ConsultasDB consultasDB = new ConsultasDB();
    
    public Controlador(){
        
    }
    
    public String registrarCliente(String pPrimerApellido, String pSegundoApellido, String pNombre, int pIdentificacion, int pNumeroTelefonico, String pCorreoElectronico,
            int pDia, int pMes, int pAnio, Banco banco){
        banco.agregarCliente(pPrimerApellido, pSegundoApellido, pNombre, pIdentificacion, pNumeroTelefonico, pCorreoElectronico, pDia, pMes, pAnio);
        Cliente clienteAgregado = banco.buscarCliente(pIdentificacion);
        consultasDB.insertarClienteDB(clienteAgregado);
        String mensaje = "Se ha creado un nuevo cliente en el sistema, los datos que fueron almacenados son: \n"
                + "Codigo: " + clienteAgregado.getCodigo() + "\n"
                + "Nombre: " + clienteAgregado.getNombre() + " " + clienteAgregado.getPrimerApellido() + " " + clienteAgregado.getSegundoApellido() + "\n"
                + "Identificacion: " + clienteAgregado.getIdentificacion() + "\n"
                + "Fecha de nacimiento: " + clienteAgregado.getFechaNacimiento() + "\n"
                + "Numero telefonico: " + clienteAgregado.getNumeroTelefonico() + "\n"
                + "Correo electronico: " + clienteAgregado.getCorreoElectronico() + "\n";
        return mensaje; 
    }
    
    public String registrarCuenta(double pSaldo, String pPin, Cliente clienteRegistrado, Banco banco){
        CuentaBancaria nuevaCuenta = clienteRegistrado.crearCuenta(pSaldo, pPin);
        banco.agregarCuenta(nuevaCuenta);
        consultasDB.insertarCuentaDB(nuevaCuenta);
        consultasDB.insertarCuentaCliente(clienteRegistrado, nuevaCuenta);

        String mensaje = "Se ha creado una nueva cuenta en el sistema, los datos de la cuenta son: \n"
                + "Numero de cuenta: " + nuevaCuenta.getNumeroCuenta() + "\n"
                + "Estatus de la cuenta: " + nuevaCuenta.obtenerEstatusCuenta() + "\n"
                + "Saldo actual: " + redondear.format(nuevaCuenta.getSaldo()) + "\n"
                + "Nombre del dueño de la cuenta: " + clienteRegistrado.getNombre() + "\n"
                + "Numero de telefono “asociado” a la cuenta: " + clienteRegistrado.getNumeroTelefonico() + "\n"
                + "Direccion de correo electronico “asociada” a la cuenta: " + clienteRegistrado.getCorreoElectronico() + "\n";
        return mensaje; 
    }
    
    public String listarClientesAscendente(Banco banco){
        String mensaje = "";
        Ordenamiento.ordenarAscendente(banco.todosLosClientes);

        for(int indiceCliente = 0; indiceCliente < Cliente.getCantidadClientes(); indiceCliente++){
            Cliente clienteActual = banco.todosLosClientes[indiceCliente];

            mensaje += "Cliente #" + (indiceCliente + 1) + "\n" +
                    "Nombre: " + clienteActual.getPrimerApellido() + " " + clienteActual.getSegundoApellido() + " " + clienteActual.getNombre() + "\n" + 
                    "Identificacion: " + clienteActual.getIdentificacion() + "\n\n";               
        }
        return mensaje; 
    }
    
    public String consultarCliente(int pIdentificacionCliente, Banco banco){
        Cliente duenioCuenta;
        duenioCuenta = banco.buscarCliente(pIdentificacionCliente); 
        return duenioCuenta.consultarDatosCliente();
    }
    
    public String listarCuentasDescendente(Banco banco){
        String mensaje = "";
        Ordenamiento.ordenarDescendente(banco.todasLasCuentas);

        for(int indiceCuenta = 0; indiceCuenta < CuentaBancaria.getCantidadCuentas(); indiceCuenta++){
            CuentaBancaria cuentaActual = banco.todasLasCuentas[indiceCuenta];
            Cliente clienteActual = banco.buscarDuenioCuenta(cuentaActual.getNumeroCuenta());
            
            mensaje += "Cuenta #" + (indiceCuenta + 1) + "\n" +
                    "Número de cuenta: " + cuentaActual.getNumeroCuenta() + "\n" +
                    "Estatus de la cuenta: " + cuentaActual.obtenerEstatusCuenta() + "\n" +
                    "Saldo de la cuenta: " + redondear.format(cuentaActual.getSaldo()) + "\n" +
                    "Identificación del dueño de la cuenta: " + clienteActual.getIdentificacion() + "\n" +
                    "Nombre del dueño de la cuenta: " + clienteActual.getNombre() + " " + clienteActual.getPrimerApellido() + " " + clienteActual.getSegundoApellido() + "\n\n";
        }
        return mensaje; 
    }
    
    public String consultarCuenta(int pNumCuenta, Banco pBanco){
        CuentaBancaria cuentaConsultada;
        cuentaConsultada = pBanco.buscarCuenta(pNumCuenta); 
        return cuentaConsultada.consultarDatosCuenta();
    }
        
    public String cambiarPin(String pPin, int pNumCuenta, Banco pBanco){
        String mensaje = "";
        CuentaBancaria cuentaConsultada;
        cuentaConsultada = pBanco.buscarCuenta(pNumCuenta); 
        cuentaConsultada.setPin(pPin);
        consultasDB.actualizarPINCuenta(cuentaConsultada);
        cuentaConsultada.registrarOperacionAdministrativa(TipoOperacion.CambioPIN);
        
        mensaje = "\nEstimado usuario, se ha cambiado satisfactoriamente el PIN de su cuenta " + cuentaConsultada.getNumeroCuenta() + ".";
        return mensaje;
    }
    
    public String depositarColones (int pNumCuenta, double pDeposito, Banco pBanco){
        String mensaje = "";
        CuentaBancaria cuentaADepositar = pBanco.buscarCuenta(pNumCuenta); 
        cuentaADepositar.depositarPropiaCuentaLocal(pDeposito);
        consultasDB.actualizarSaldoCuenta(cuentaADepositar);
        double comision = cuentaADepositar.calcularComision(pDeposito);
        double depositoReal = pDeposito - comision;
        
        Cliente clienteEncontrado = pBanco.buscarDuenioCuenta(pNumCuenta);
        int ultimaOperacion = cuentaADepositar.getCantOperacionesMonetarias();

        OperacionMonetaria operacion = cuentaADepositar.getMisOperacionesMonetarias()[ultimaOperacion-1];
        consultasDB.insertarOperacionMonetariaDB(operacion);
        consultasDB.insertarOperacionCuenta(cuentaADepositar, operacion);
        consultasDB.actualizarCantOperacionesCuenta(cuentaADepositar);
        consultasDB.actualizarCantOperacionesMonetarias(cuentaADepositar);
        consultasDB.actualizarTotalComisionesDepositosRetiros(cuentaADepositar);
        consultasDB.actualizarTotalComisionesDepositos(cuentaADepositar);
        

        mensaje += "Estimado usuario, se han depositado correctamente " + redondear.format(pDeposito) + " colones\n" +
                "[El monto real depositado a su cuenta " + pNumCuenta + " es de " + redondear.format(depositoReal) + " colones]\n" +
                "[El monto cobrado por concepto de comisión fue de " + redondear.format(comision) + " colones, que\n" +
                "fueron rebajados automáticamente de su saldo actual]";

        return mensaje;
    }
    
    public String depositarDolares (int pNumCuenta, double pDeposito, Banco pBanco){
        String mensaje = "";
        CuentaBancaria cuentaADepositar = pBanco.buscarCuenta(pNumCuenta); 
        cuentaADepositar.depositarPropiaCuentaExtranjero(pDeposito);
        consultasDB.actualizarSaldoCuenta(cuentaADepositar);
        double montoColones = cuentaADepositar.convertirDolaresAColones(pDeposito);
        double comision = cuentaADepositar.calcularComision(montoColones);
        double depositoReal = montoColones - comision;

        Cliente clienteEncontrado = pBanco.buscarDuenioCuenta(pNumCuenta);
        
        ObtenerTipoCambio tipoCambio = new ObtenerTipoCambio();
        double compraDolar = tipoCambio.obtenerTipoCambio(317);

        int ultimaOperacion = cuentaADepositar.getCantOperacionesMonetarias();

        OperacionMonetaria operacion = cuentaADepositar.getMisOperacionesMonetarias()[ultimaOperacion-1];
        consultasDB.insertarOperacionMonetariaDB(operacion);
        consultasDB.insertarOperacionCuenta(cuentaADepositar, operacion);
        consultasDB.actualizarCantOperacionesCuenta(cuentaADepositar);
        consultasDB.actualizarCantOperacionesMonetarias(cuentaADepositar);
        consultasDB.actualizarTotalComisionesDepositosRetiros(cuentaADepositar);
        consultasDB.actualizarTotalComisionesDepositos(cuentaADepositar);
        
        mensaje += "Estimado usuario, se han recibido correctamente " + redondear.format(pDeposito) + " dólares\n" +
                "[Según el BCCR, el tipo de cambio de compra del dólar de " + 
                LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " es:\n" + redondear.format(compraDolar) + "\n" +
                "[El monto equivalente en colones es " + redondear.format(montoColones) + "\n" +
                "[El monto real depositado a su cuenta " + pNumCuenta + " es de " + redondear.format(depositoReal) + " colones]\n" +
                "[El monto cobrado por concepto de comisión fue de " + redondear.format(comision) + " colones, que\n" +
                "fueron rebajados automáticamente de su saldo actual].";

        return mensaje;
    }
    
    public String retirarColones (int pNumCuenta, double pMontoRetiro, Banco pBanco){
        String mensaje = "";
        CuentaBancaria cuentaDeRetiro = pBanco.buscarCuenta(pNumCuenta); 
        cuentaDeRetiro.retirarPropiaCuentaLocal(pMontoRetiro);
        consultasDB.actualizarSaldoCuenta(cuentaDeRetiro);
        
        double comision = cuentaDeRetiro.calcularComision(pMontoRetiro);

        int ultimaOperacion = cuentaDeRetiro.getCantOperacionesMonetarias();
        
        Cliente clienteEncontrado = pBanco.buscarDuenioCuenta(pNumCuenta);

        OperacionMonetaria operacion = cuentaDeRetiro.getMisOperacionesMonetarias()[ultimaOperacion-1];
        consultasDB.insertarOperacionMonetariaDB(operacion);
        consultasDB.insertarOperacionCuenta(cuentaDeRetiro, operacion);
        consultasDB.actualizarCantOperacionesCuenta(cuentaDeRetiro);
        consultasDB.actualizarCantOperacionesMonetarias(cuentaDeRetiro);
        consultasDB.actualizarTotalComisionesDepositosRetiros(cuentaDeRetiro);
        consultasDB.actualizarTotalComisionesRetiros(cuentaDeRetiro);
        
        mensaje += "Estimado usuario, el monto de este retiro es " + redondear.format(pMontoRetiro) + " colones\n" +
                "[El monto cobrado por concepto de comisión fue de " + redondear.format(comision) + " colones, que\n" +
                "fueron rebajados automáticamente de su saldo actual]";

        return mensaje;
    }
    
    public String retirarDolares (int pNumCuenta, double pMontoRetiro, Banco pBanco){
        String mensaje = "";
        CuentaBancaria cuentaDeRetiro = pBanco.buscarCuenta(pNumCuenta); 
        cuentaDeRetiro.retirarPropiaCuentaExtranjero(pMontoRetiro);
        consultasDB.actualizarSaldoCuenta(cuentaDeRetiro);
        
        double montoEnColones = cuentaDeRetiro.convertirDolaresAColones(pMontoRetiro);
        double comision = cuentaDeRetiro.calcularComision(montoEnColones);

        ObtenerTipoCambio tipoCambio = new ObtenerTipoCambio();
        double ventaDolar = tipoCambio.obtenerTipoCambio(318);

        int ultimaOperacion = cuentaDeRetiro.getCantOperacionesMonetarias();
        
        Cliente clienteEncontrado = pBanco.buscarDuenioCuenta(pNumCuenta);

        OperacionMonetaria operacion = cuentaDeRetiro.getMisOperacionesMonetarias()[ultimaOperacion-1];
        consultasDB.insertarOperacionMonetariaDB(operacion);
        consultasDB.insertarOperacionCuenta(cuentaDeRetiro, operacion);
        consultasDB.actualizarCantOperacionesCuenta(cuentaDeRetiro);
        consultasDB.actualizarCantOperacionesMonetarias(cuentaDeRetiro);
        consultasDB.actualizarTotalComisionesDepositosRetiros(cuentaDeRetiro);
        consultasDB.actualizarTotalComisionesRetiros(cuentaDeRetiro);
        
        mensaje += "Estimado usuario, el monto de este retiro es " + redondear.format(pMontoRetiro) + " dólares\n" +
                "[Según el BCCR, el tipo de cambio de venta del dólar de hoy es:\n" + redondear.format(ventaDolar) + "\n" +
                "[El monto equivalente de su retiro es " + redondear.format(montoEnColones) + " colones\n" +
                "[El monto cobrado por concepto de comisión fue de " + redondear.format(comision) + " colones, que\n" +
                "fueron rebajados automáticamente de su saldo actual].";
        return mensaje;
    }
    
    public String realizarTransferencias (int pNumCuentaOrigen, int pNumCuentaDestino, double pDeposito, Banco pBanco){
        String mensaje = "";
        CuentaBancaria cuentaOrigen = pBanco.buscarCuenta(pNumCuentaOrigen); 
        CuentaBancaria cuentaDestino = pBanco.buscarCuenta(pNumCuentaDestino); 

        cuentaOrigen.realizarTransferenciaLocal(cuentaDestino, pDeposito);
        consultasDB.actualizarSaldoCuenta(cuentaOrigen);
        consultasDB.actualizarSaldoCuenta(cuentaDestino);
        int ultimaOperacion = cuentaOrigen.getCantOperacionesMonetarias();

        OperacionMonetaria operacion = cuentaOrigen.getMisOperacionesMonetarias()[ultimaOperacion-1];
        Cliente clienteEncontrado = pBanco.buscarDuenioCuenta(pNumCuentaOrigen);
        consultasDB.insertarOperacionMonetariaDB(operacion);
        consultasDB.insertarOperacionCuenta(cuentaOrigen, operacion);
        consultasDB.actualizarCantOperacionesCuenta(cuentaOrigen);
        consultasDB.actualizarCantOperacionesMonetarias(cuentaOrigen);
        
        consultasDB.actualizarTotalComisionesDepositosRetiros(cuentaOrigen);
        consultasDB.actualizarTotalComisionesRetiros(cuentaOrigen);
        
        double comision = cuentaOrigen.calcularComision(pDeposito);

        mensaje += "Estimado usuario, la transferencia de fondos se ejecutó satisfactoriamente.\n" +
                "El monto retirado de la cuenta origen y depositado en la cuenta destino es\n" +
                 redondear.format(pDeposito) + "\n" +
                "[El monto cobrado por concepto de comisión a la cuenta origen fue de " + redondear.format(comision) + "\n" +
                "colones, que fueron rebajados automáticamente de su saldo actual]";

        return mensaje;
    }
    
    public String consultarCompraDolar(){
        String mensaje = "";
        
        ObtenerTipoCambio tipoCambio = new ObtenerTipoCambio();
        double compraDolar = tipoCambio.obtenerTipoCambio(317);
        
        mensaje += "El tipo de cambio de compra del dolar es: " + redondear.format(compraDolar);
        
        return mensaje;
    }
    
    public String consultarVentaDolar(){
        String mensaje = "";
        
        ObtenerTipoCambio tipoCambio = new ObtenerTipoCambio();
        double ventaDolar = tipoCambio.obtenerTipoCambio(318);
        
        mensaje += "El tipo de cambio de venta del dolar es: " + redondear.format(ventaDolar);
        
        return mensaje;
    }
    
    public String consultarSaldoColones(int pNumCuenta, Banco pBanco){
        String mensaje = "";
        CuentaBancaria cuentaBuscada = pBanco.buscarCuenta(pNumCuenta);
        
        
        double saldoCuenta = cuentaBuscada.getSaldo();
        mensaje += "El saldo de su cuenta es " + redondear.format(saldoCuenta) + " colones.";
        
        cuentaBuscada.registrarOperacionAdministrativa(TipoOperacion.Consulta);
        int ultimaOperacion = cuentaBuscada.getCantOperacionesAdministrativas();
        OperacionAdministrativa operacion = cuentaBuscada.getMisOperacionesAdministrativas()[ultimaOperacion-1];

        consultasDB.insertarOperacionAdministrativaDB(operacion);
        consultasDB.insertarOperacionCuenta(cuentaBuscada, operacion);
        consultasDB.actualizarCantOperacionesCuenta(cuentaBuscada);
        consultasDB.actualizarCantOperacionesAdministrativas(cuentaBuscada);
        
        return mensaje;
    }
    
    public String consultarSaldoDolares(int pNumCuenta, Banco pBanco){
        String mensaje = "";
        CuentaBancaria cuentaBuscada = pBanco.buscarCuenta(pNumCuenta);
     
        ObtenerTipoCambio tipoCambio = new ObtenerTipoCambio();
        double ventaDolar = tipoCambio.obtenerTipoCambio(318);
        
        double saldoCuentaDolares = cuentaBuscada.getSaldo() / ventaDolar;
        mensaje += "El saldo de su cuenta es " + redondear.format(saldoCuentaDolares) + " dolares.";
        
        cuentaBuscada.registrarOperacionAdministrativa(TipoOperacion.Consulta);
        int ultimaOperacion = cuentaBuscada.getCantOperacionesAdministrativas();
        OperacionAdministrativa operacion = cuentaBuscada.getMisOperacionesAdministrativas()[ultimaOperacion-1];

        consultasDB.insertarOperacionAdministrativaDB(operacion);
        consultasDB.insertarOperacionCuenta(cuentaBuscada, operacion);    
        consultasDB.actualizarCantOperacionesCuenta(cuentaBuscada);
        consultasDB.actualizarCantOperacionesAdministrativas(cuentaBuscada);
        
        return mensaje;
    }
    
    public String consultarEstadoCuentaColones(int pNumCuenta, Banco pBanco){
        String mensaje = "";
        CuentaBancaria cuentaBuscada = pBanco.buscarCuenta(pNumCuenta);
        
        mensaje += cuentaBuscada.consultarEstadoCuentaLocal();
        
        cuentaBuscada.registrarOperacionAdministrativa(TipoOperacion.Consulta);
        int ultimaOperacion = cuentaBuscada.getCantOperacionesAdministrativas();
        OperacionAdministrativa operacion = cuentaBuscada.getMisOperacionesAdministrativas()[ultimaOperacion-1];

        consultasDB.insertarOperacionAdministrativaDB(operacion);
        consultasDB.insertarOperacionCuenta(cuentaBuscada, operacion);
        consultasDB.actualizarCantOperacionesCuenta(cuentaBuscada);
        consultasDB.actualizarCantOperacionesAdministrativas(cuentaBuscada);
        
        return mensaje;
    }
    
    public String consultarEstadoCuentaDolares(int pNumCuenta, Banco pBanco){
        String mensaje = "";
        CuentaBancaria cuentaBuscada = pBanco.buscarCuenta(pNumCuenta);
        
        mensaje += cuentaBuscada.consultarEstadoCuentaExtranjero();
        cuentaBuscada.registrarOperacionAdministrativa(TipoOperacion.Consulta);
        int ultimaOperacion = cuentaBuscada.getCantOperacionesAdministrativas();
        OperacionAdministrativa operacion = cuentaBuscada.getMisOperacionesAdministrativas()[ultimaOperacion-1];

        consultasDB.insertarOperacionAdministrativaDB(operacion);
        consultasDB.insertarOperacionCuenta(cuentaBuscada, operacion);
        consultasDB.actualizarCantOperacionesCuenta(cuentaBuscada);
        consultasDB.actualizarCantOperacionesAdministrativas(cuentaBuscada);
        
        return mensaje;
    }
    
    public String consultarEstatusCuenta(int pNumCuenta, Banco pBanco){
        String mensaje = "";
        CuentaBancaria cuentaBuscada = pBanco.buscarCuenta(pNumCuenta);
        
        mensaje += cuentaBuscada.consultarEstatusCuenta();
        cuentaBuscada.registrarOperacionAdministrativa(TipoOperacion.Consulta);
        OperacionAdministrativa operacionActual = cuentaBuscada.getMisOperacionesAdministrativas()[cuentaBuscada.getCantOperacionesAdministrativas() - 1];
        consultasDB.insertarOperacionAdministrativaDB(operacionActual);
        consultasDB.insertarOperacionCuenta(cuentaBuscada, operacionActual);
        consultasDB.actualizarCantOperacionesCuenta(cuentaBuscada);
        consultasDB.actualizarCantOperacionesAdministrativas(cuentaBuscada);
        
        return mensaje;
    }
    
    public String consultarGananciasBanco(Banco banco){
        String mensaje = "";
        
        double gananciasBanco = banco.calcularComisionesTotales();
        
        mensaje += "Las ganancias totales del por concepto de comisiones es de: " + redondear.format(gananciasBanco);
        return mensaje;
    }
    
    public String consultarGananciasCuenta(int pNumCuenta, Banco pBanco){
        String mensaje = "";
        CuentaBancaria cuentaBuscada = pBanco.buscarCuenta(pNumCuenta);
                 
        double gananciasCuenta = cuentaBuscada.getTotalComisionesDepositosRetirosCuenta();
        
        mensaje += "Las ganancias totales del banco por concepto de comisiones a la cuenta " + pNumCuenta + " es de: " + redondear.format(gananciasCuenta);
        cuentaBuscada.registrarOperacionAdministrativa(TipoOperacion.Consulta);
        return mensaje;
    }
    
    public void fullerClientes(Banco banco){
        ResultSet resultSetClientes = consultasDB.fullerClientes();
        String pPrimerApellido;
        String pSegundoApellido;
        String pNombre;
        int pIdentificacion;
        int pNumeroTelefonico;
        String pCorreoElectronico;
        int pDia;
        int pMes;
        int pAnio;
        String fecha;
        int cantidadClientes = 0;
        
        try{
            while (resultSetClientes.next()){
                pPrimerApellido = resultSetClientes.getString("primerApellido");
                pSegundoApellido = resultSetClientes.getString("segundoApellido");
                pNombre = resultSetClientes.getString("nombre");
                pIdentificacion = resultSetClientes.getInt("identificacion");
                pNumeroTelefonico = resultSetClientes.getInt("numeroTelefonico");
                pCorreoElectronico = resultSetClientes.getString("correoElectronico");
                
                fecha = resultSetClientes.getString("fechaNacimiento");
                pAnio = Integer.valueOf(fecha.substring(0,4));
                pMes = Integer.valueOf(fecha.substring(5,7));
                pDia = Integer.valueOf(fecha.substring(8,10));
                
                banco.agregarCliente(pPrimerApellido, pSegundoApellido, pNombre, pIdentificacion, 
                        pNumeroTelefonico, pCorreoElectronico, pDia, pMes, pAnio);
                cantidadClientes ++;
               
            }
            Cliente.setCantidadClientes(cantidadClientes);
        }
        catch (SQLException e) {
        }   
    }
    
   
    public void fullerAdministrador(Banco banco){
        ResultSet resultSetAdministrador = consultasDB.fullerAdministradores();
        
        String usuario;
        String contrasena;
        
        Administrador admin;
        
        try{
            while(resultSetAdministrador.next()){
                    usuario = resultSetAdministrador.getString("nombreUsuario");
                    contrasena = resultSetAdministrador.getString("contrasena");

                    admin = new Administrador(usuario, contrasena);
                    banco.setMiAdmin(admin);
            }
        }
        catch(SQLException e){
            System.out.println(e.getErrorCode() + "" + e.getMessage());
        }
    }
   
    
    public void fullerCuentas(Banco banco){
        ResultSet resultSetCuentas = consultasDB.fullerCuentas();
        
        CuentaBancaria nuevaCuenta;
        Cliente clienteEncontrado;
        
        int identificacionCliente;
        int cantidadCuentas = 0; //static
        String fechaCreacion;
        int pAnio;
        int pMes;
        int pDia;
        double saldo;
        boolean estatus;
        int numeroCuenta;
        String pin;
        double totalComisionesDepositosRetirosCuenta; // monto total de todas las comisiones hechas
        double totalComisionesDepositosCuenta; // monto total de comisiones por depositos
        double totalComisionesRetirosCuenta; // monto total de comisiones por retiros
        int cantComisionesTotal; //cantidad de comisiones que se han hecho
        int cantOperacionesMonetarias;
        int cantOperacionesAdministrativas;
        int pinFallido;
        
        try{
            while (resultSetCuentas.next()){
                identificacionCliente = resultSetCuentas.getInt("identificacion");
                numeroCuenta = resultSetCuentas.getInt("numeroCuenta");
                saldo = resultSetCuentas.getDouble("saldo");
                estatus = resultSetCuentas.getBoolean("estatusCuenta");
                fechaCreacion = resultSetCuentas.getString("fechaCreacion");
                pAnio = Integer.valueOf(fechaCreacion.substring(0,4));
                pMes = Integer.valueOf(fechaCreacion.substring(5,7));
                pDia = Integer.valueOf(fechaCreacion.substring(8,10));
                pin = resultSetCuentas.getString("pin");
                totalComisionesDepositosRetirosCuenta = resultSetCuentas.getDouble("totalComisionesDepositosRetirosCuenta");
                totalComisionesDepositosCuenta = resultSetCuentas.getDouble("totalComisionesDepositosCuenta");
                totalComisionesRetirosCuenta = resultSetCuentas.getDouble("totalComisionesRetirosCuenta");
                cantComisionesTotal = resultSetCuentas.getInt("cantidadComisionesTotal");
                cantOperacionesMonetarias = resultSetCuentas.getInt("cantidadOperacionesMonetarias");
                cantOperacionesAdministrativas = resultSetCuentas.getInt("cantidadOperacionesAdministrativas");
                pinFallido = resultSetCuentas.getInt("pinFallido");
                cantidadCuentas++;
                
                clienteEncontrado = banco.buscarCliente(identificacionCliente);
                nuevaCuenta = clienteEncontrado.crearCuenta(saldo, pin);
                banco.agregarCuenta(nuevaCuenta);
                
                nuevaCuenta.establecerNumeroCuenta(numeroCuenta);
                nuevaCuenta.setEstatus(estatus);
                nuevaCuenta.setFechaCreacion(pAnio, pMes, pDia);
                nuevaCuenta.setTotalComisionesDepositosRetirosCuenta(totalComisionesDepositosRetirosCuenta);
                nuevaCuenta.setTotalComisionesDepositosCuenta(totalComisionesDepositosCuenta);
                nuevaCuenta.setTotalComisionesRetirosCuenta(totalComisionesRetirosCuenta);
                nuevaCuenta.setCantComisionesTotal(cantComisionesTotal);
                nuevaCuenta.setCantOperacionesMonetarias(cantOperacionesMonetarias);
                nuevaCuenta.setCantOperacionesAdministrativas(cantOperacionesAdministrativas);
                nuevaCuenta.setPinFallido(pinFallido);
            }
            CuentaBancaria.setCantidadCuentas(cantidadCuentas);
        }
        catch (SQLException e) {
            System.out.println(e.getErrorCode() + "" + e.getMessage());
        }   
    }
    
    public void fullerOperaciones(Banco pBanco) {
        ResultSet resultSetOperaciones = consultasDB.fullerOperaciones();

        CuentaBancaria cuentaEncontrada;
        int identificadorOperacion = 0;
        int numeroCuenta;
        LocalDate fechaOperacion;
        String tipoOperacion;
        boolean cargoConceptoComision;
        double montoOperacion;
        String fecha;
        int pAnio;
        int pMes;
        int pDia;

        try {
            while (resultSetOperaciones.next()) {
                numeroCuenta = resultSetOperaciones.getInt("numeroCuenta");
                identificadorOperacion = resultSetOperaciones.getInt("identificadorOperaciones");
                fecha = resultSetOperaciones.getString("fechaOperacion");
                pAnio = Integer.valueOf(fecha.substring(0, 4));
                pMes = Integer.valueOf(fecha.substring(5, 7));
                pDia = Integer.valueOf(fecha.substring(8, 10));
                fechaOperacion = LocalDate.of(pAnio, pMes, pDia);
                tipoOperacion = resultSetOperaciones.getString("tipoOperacion");
                TipoOperacion tipoOperacionEnum = TipoOperacion.valueOf(tipoOperacion);
                cargoConceptoComision = resultSetOperaciones.getBoolean("cargoConceptoComision");
                montoOperacion = resultSetOperaciones.getDouble("montoOperacion");

                cuentaEncontrada = pBanco.buscarCuenta(numeroCuenta);
                if (montoOperacion == -1){
                    cuentaEncontrada.registrarOperacionAdministrativa(tipoOperacionEnum);
                    OperacionAdministrativa operacionActual = cuentaEncontrada.getMisOperacionesAdministrativas()[cuentaEncontrada.getCantOperacionesAdministrativas() - 1];
                    operacionActual.setIdentificadorOperacion(identificadorOperacion);
                    operacionActual.setFechaOperacionBD(fechaOperacion);
                }else{
                    cuentaEncontrada.registrarOperacionMonetaria(cargoConceptoComision, montoOperacion, tipoOperacionEnum);
                    OperacionMonetaria operacionActual = cuentaEncontrada.getMisOperacionesMonetarias()[cuentaEncontrada.getCantOperacionesMonetarias() - 1];
                    operacionActual.setFechaOperacionBD(fechaOperacion);
                    operacionActual.setIdentificadorOperacion(identificadorOperacion);
                }                    
            }
            Operacion.setCantidadOperaciones(identificadorOperacion);
        }
        catch (SQLException e) {
            e.getErrorCode();
        }
    }
    
    public String enviarMensaje(int pNumCuenta, Banco pBanco) throws IOException{
        Cliente clienteBuscado = pBanco.buscarDuenioCuenta(pNumCuenta);
        CuentaBancaria cuentaNueva = pBanco.buscarCuenta(pNumCuenta);
        
        String telefonoCliente  = Integer.toString(clienteBuscado.getNumeroTelefonico());
        String palabraAleatoria = cuentaNueva.generarPalabra();
        
        EnvioSMS nuevoEnvio = new EnvioSMS();
        nuevoEnvio.enviarMensaje(telefonoCliente, palabraAleatoria);   
        
        return palabraAleatoria;
    }
    
    public void crearRegistro(TipoAccion accion, TipoVista vista){
        Registro nuevoRegistro = new Registro(accion, vista);
    }
}
