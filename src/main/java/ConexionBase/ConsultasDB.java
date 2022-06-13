/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionBase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import logicadenegocios.Administrador;
import logicadenegocios.Cliente;
import logicadenegocios.CuentaBancaria;
import logicadenegocios.Banco;
import logicadenegocios.Operacion;
import logicadenegocios.OperacionMonetaria;
import logicadenegocios.OperacionAdministrativa;
import logicadenegocios.TipoOperacion;

/**
 *
 * @author valef
 */
public class ConsultasDB {
    ConexionDB objetoConexion = new ConexionDB();
    
    public ConsultasDB(){
        
    }
    
    public void ejecutarQuery(String query){
        try{
            Connection conectar = objetoConexion.establecerConexion();
            Statement s = conectar.createStatement();
            s.executeUpdate(query);
            //s.close();
        }
        catch(SQLException e){
            System.out.println(e.getErrorCode() + "" + e.getMessage());
        }
    }
    
    public ResultSet ejecutarQueryFuller(String query){
        try{
            Connection conectar = objetoConexion.establecerConexion();
            Statement s = conectar.createStatement();
            return s.executeQuery(query);
            //s.close();
        }
        catch(SQLException e){
            System.out.println(e.getErrorCode() + "" + e.getMessage());
        }
        return null;
    }
    
    public void insertarClienteDB(Cliente pCliente){
        String consulta = "insert into cliente values "
                + "('"+pCliente.getIdentificacion()+"', '"+pCliente.getPrimerApellido()+"', '"+pCliente.getSegundoApellido()+"', "
                + "'"+pCliente.getNombre()+"', '"+pCliente.getNumeroTelefonico()+"', '"+pCliente.getCodigo()+"', "
                + "'"+pCliente.getCorreoElectronico()+"', '"+pCliente.cambiarFechaInsercion()+"')";        
        ejecutarQuery(consulta);
    }
    
    public void insertarCuentaDB(CuentaBancaria pCuenta){
        String consulta = "insert into cuentaBancaria values "
                + "(aes_encrypt('"+String.valueOf(pCuenta.getNumeroCuenta())+"', 'CiudadToxina'), "+"aes_encrypt('"+String.valueOf(pCuenta.getSaldo())+"', 'CiudadToxina'), '"+((pCuenta.getEstatus())?1 : 0)+"', "
                + "'"+pCuenta.getFechaCreacion()+"', aes_encrypt('"+pCuenta.getPin()+"', 'CiudadToxina'), '"+pCuenta.getTotalComisionesDepositosRetirosCuenta()+"', "
                + "'"+pCuenta.getTotalComisionesDepositosCuenta()+"', '"+pCuenta.getTotalComisionesRetirosCuenta()+"', "
                + "'"+pCuenta.getCantComisionesTotal()+"', '"+pCuenta.getCantOperacionesMonetarias()+"', "     
                + "'"+pCuenta.getCantOperacionesAdministrativas()+"', '"+pCuenta.getPinFallido()+"')";
        ejecutarQuery(consulta);
    }
    
    public void insertarOperacionMonetariaDB(OperacionMonetaria pOperacion){
        String consulta = "insert into operacion values " +
                "('"+pOperacion.getIdentificadorOperacion()+"', '"+pOperacion.getFechaOperacion()+"', '"+pOperacion.getTipoOperacion()+
                "', '"+((pOperacion.isCargoConceptoComision()) ? 1:0)+"', '"+pOperacion.getMontoOperacion()+"')";
        ejecutarQuery(consulta);
    }

    public void insertarOperacionAdministrativaDB(OperacionAdministrativa pOperacion){
        String consulta = "insert into operacion values " +
                "('"+pOperacion.getIdentificadorOperacion()+"', '"+pOperacion.getFechaOperacion()+"', '"+pOperacion.getTipoOperacion()+
                "', '0', '-1')";
        ejecutarQuery(consulta);
    }

    public void insertarOperacionCuenta(CuentaBancaria pCuenta, Operacion pOperacion){
        String consulta = "insert into cuentaOperaciones values " +
                "(aes_encrypt('"+String.valueOf(pCuenta.getNumeroCuenta())+"', 'CiudadToxina'), '"+pOperacion.getIdentificadorOperacion()+"')";
        ejecutarQuery(consulta);
    }
    
    public void insertarCuentaCliente(Cliente pCliente, CuentaBancaria pCuenta){
        String consulta = "insert into cuentaCliente values " +
                "('"+pCliente.getIdentificacion()+"', aes_encrypt('"+pCuenta.getNumeroCuenta()+"', 'CiudadToxina'))";
        ejecutarQuery(consulta);
    }
    
    public void actualizarEstatusCuenta(CuentaBancaria pCuenta){
        String consulta = "UPDATE cuentaBancaria SET estatusCuenta = " + 
                "'"+((pCuenta.getEstatus()) ? 1:0)+"' WHERE numeroCuenta = aes_encrypt('"+pCuenta.getNumeroCuenta()+"', 'CiudadToxina')";
        ejecutarQuery(consulta);
    }
    
    public void actualizarPINCuenta(CuentaBancaria pCuenta){
        String consulta = "UPDATE cuentaBancaria SET pin = aes_encrypt('"+pCuenta.getPin()+"', 'CiudadToxina')"
                + " WHERE numeroCuenta = aes_encrypt('"+pCuenta.getNumeroCuenta()+"', 'CiudadToxina')";
        ejecutarQuery(consulta);
    }
    
    public void actualizarSaldoCuenta(CuentaBancaria pCuenta){
        String consulta = "UPDATE cuentaBancaria SET saldo = aes_encrypt('"+String.valueOf(pCuenta.getSaldo())+"', 'CiudadToxina')"
                + " WHERE numeroCuenta = aes_encrypt('"+pCuenta.getNumeroCuenta()+"', 'CiudadToxina')";
        ejecutarQuery(consulta);
    }
    
    public void actualizarCantOperacionesCuenta(CuentaBancaria pCuenta){
        String consulta = "UPDATE cuentaBancaria SET cantidadComisionesTotal = '"+pCuenta.getCantComisionesTotal()+"'"
                + " WHERE numeroCuenta = aes_encrypt('"+pCuenta.getNumeroCuenta()+"', 'CiudadToxina')";
        ejecutarQuery(consulta);
    }
    
    public void actualizarCantOperacionesMonetarias(CuentaBancaria pCuenta){
        String consulta = "UPDATE cuentaBancaria SET cantidadOperacionesMonetarias = '"+pCuenta.getCantOperacionesMonetarias()+"'"
                + " WHERE numeroCuenta = aes_encrypt('"+pCuenta.getNumeroCuenta()+"', 'CiudadToxina')";
        ejecutarQuery(consulta);
    }
    
    public void actualizarCantOperacionesAdministrativas(CuentaBancaria pCuenta){
        String consulta = "UPDATE cuentaBancaria SET cantidadOperacionesAdministrativas = '"+pCuenta.getCantOperacionesAdministrativas()+"'"
                + " WHERE numeroCuenta = aes_encrypt('"+pCuenta.getNumeroCuenta()+"', 'CiudadToxina')";
        ejecutarQuery(consulta);
    }
    
    public void actualizarTotalComisionesDepositosRetiros(CuentaBancaria pCuenta){
        String consulta = "UPDATE cuentaBancaria SET totalComisionesDepositosRetirosCuenta = '"+pCuenta.getTotalComisionesDepositosRetirosCuenta()+"'"
                + " WHERE numeroCuenta = aes_encrypt('"+pCuenta.getNumeroCuenta()+"', 'CiudadToxina')";
        ejecutarQuery(consulta);
    }
    
    public void actualizarTotalComisionesDepositos(CuentaBancaria pCuenta){
        String consulta = "UPDATE cuentaBancaria SET totalComisionesDepositosCuenta = '"+pCuenta.getTotalComisionesDepositosCuenta()+"'"
                + " WHERE numeroCuenta = aes_encrypt('"+pCuenta.getNumeroCuenta()+"', 'CiudadToxina')";
        ejecutarQuery(consulta);
    }
    
    public void actualizarTotalComisionesRetiros(CuentaBancaria pCuenta){
        String consulta = "UPDATE cuentaBancaria SET totalComisionesRetirosCuenta = '"+pCuenta.getTotalComisionesRetirosCuenta()+"'"
                + " WHERE numeroCuenta = aes_encrypt('"+pCuenta.getNumeroCuenta()+"', 'CiudadToxina')";
        ejecutarQuery(consulta);
    }
    
    public ResultSet fullerClientes(){
        ResultSet clientes ;
        String consulta = "SELECT * FROM cliente";
        clientes = ejecutarQueryFuller(consulta);
        
        return clientes;
    }
    
    public ResultSet fullerAdministradores(){
        ResultSet administradores ;
        String consulta = "SELECT CAST(aes_decrypt(nombreUsuario,'CiudadToxina')AS CHAR(40)) as nombreUsuario, CAST(aes_decrypt(contrasena,'CiudadToxina')AS CHAR(40)) as contrasena FROM administrador;";
        administradores = ejecutarQueryFuller(consulta);
        return administradores;
    }
    
    public ResultSet fullerCuentas(){
        ResultSet clientes ;
        String consulta = "SELECT identificacion, CAST(aes_decrypt(numeroCuenta,'CiudadToxina')AS CHAR(40)) as numeroCuenta, "
                + "CAST(aes_decrypt(saldo,'CiudadToxina')AS CHAR(40)) as saldo, estatusCuenta, fechaCreacion,"
                + "CAST(aes_decrypt(pin,'CiudadToxina')AS CHAR(40)) as pin, totalComisionesDepositosRetirosCuenta,\n"
                + "totalComisionesDepositosCuenta, totalComisionesRetirosCuenta, cantidadComisionesTotal, cantidadOperacionesMonetarias,\n"
                + "cantidadOperacionesAdministrativas, pinFallido\n"
                + "FROM cliente \n"
                + "INNER JOIN cuentaCliente ON cliente.identificacion = cuentaCliente.identificacionCliente\n"
                + "INNER JOIN cuentaBancaria ON cuentaBancaria.numeroCuenta = cuentaCliente.numeroCuentaCuenta;";
        clientes = ejecutarQueryFuller(consulta);
        
        return clientes;
    }
    
    public ResultSet fullerOperaciones(){
        ResultSet operaciones;
        String sqlQuery = "SELECT CAST(aes_decrypt(numeroCuenta,'CiudadToxina')AS CHAR(40)) as numeroCuenta, "
                + "identificadorOperaciones, fechaOperacion, tipoOperacion, cargoConceptoComision, montoOperacion\n" +
                "FROM operacion INNER JOIN cuentaOperaciones \n"
                + "ON operacion.identificadorOperaciones = cuentaOperaciones.identificadorOperacionesIntermedio\n"
                + "INNER JOIN cuentaBancaria ON cuentaBancaria.numeroCuenta = cuentaOperaciones.numeroCuentaIntermedio";
        operaciones = ejecutarQueryFuller(sqlQuery);
        return operaciones;
    }
}
