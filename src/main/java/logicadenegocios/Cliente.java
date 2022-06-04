
package logicadenegocios;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import utilidades.Comparable;

/**
 *
 * @author sanch
 */
public class Cliente implements Comparable{
    private String primerApellido;
    private String segundoApellido;
    private String nombre;
    private int identificacion;
    private LocalDate fechaNacimiento;
    private int numeroTelefonico;
    private String correoElectronico;
    private String codigo;
    private static int cantidadClientes = 0;
    private int cantidadCuentasCliente = 0;
    
    public CuentaBancaria[] misCuentas = new CuentaBancaria[25]; //CAMBIAR
    
    
    public Cliente(String pPrimerApellido, String pSegundoApellido, String pNombre, int pIdentificacion, int pNumeroTelefonico, String pCorreoElectronico,
            int pDia, int pMes, int pAnio) {
        cantidadClientes++;
        this.primerApellido = pPrimerApellido;
        this.segundoApellido = pSegundoApellido;
        this.nombre = pNombre;
        this.identificacion = pIdentificacion;
        this.numeroTelefonico = pNumeroTelefonico;
        this.correoElectronico = pCorreoElectronico;
        this.fechaNacimiento = LocalDate.of(pAnio, pMes, pDia);
        setCodigo(cantidadClientes); 
    }
    
    public CuentaBancaria crearCuenta(double pSaldo, String pPin){
        CuentaBancaria nuevaCuenta = new CuentaBancaria(pSaldo, pPin);
        misCuentas [cantidadCuentasCliente] = nuevaCuenta;
        this.cantidadCuentasCliente++;
        
        return nuevaCuenta;
    }
    
    // METODOS ACCESORES
    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(int identificacion) {
        this.identificacion = identificacion;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public void setFechaNacimiento(int pDia, int pMes, int pAnio) {
        this.fechaNacimiento = LocalDate.of(pAnio, pMes, pDia);
    }

    public int getNumeroTelefonico() {
        return numeroTelefonico;
    }

    public void setNumeroTelefonico(int numeroTelefonico) {
        this.numeroTelefonico = numeroTelefonico;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correcoElectronico) {
        this.correoElectronico = correcoElectronico;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(int ClienteActual) {
        String codigoBase = "CIF_";
        String numeroActual = String.valueOf(ClienteActual);
        this.codigo = codigoBase + numeroActual;
    }

    public static int getCantidadClientes() {
        return cantidadClientes;
    }

    public static void setCantidadClientes(int cantidadClientes) {
        Cliente.cantidadClientes = cantidadClientes;
    }

    public int getCantidadCuentasCliente() {
        return cantidadCuentasCliente;
    }

    public void setCantidadCuentasCliente(int cantidadCuentasCliente) {
        this.cantidadCuentasCliente = cantidadCuentasCliente;
    }

    public CuentaBancaria[] getMisCuentas() {
        return misCuentas;
    }

    public void setMisCuentas(CuentaBancaria[] misCuentas) {
        this.misCuentas = misCuentas;
    }
    
    public boolean menorQue(utilidades.Comparable objeto){
        Cliente clientePorComparar = (Cliente)objeto;
        String primerApellidoPorComparar = clientePorComparar.getNombre();
        
        return (getNombre().compareTo(primerApellidoPorComparar) > 0);
    }
    
    private String obtenerNumerosCuentaCliente(){
        String numerosCuenta = " ";
        CuentaBancaria cuentaActual;
        for(int i=0; i<cantidadCuentasCliente; i++){
            cuentaActual = misCuentas[i];
            numerosCuenta += "Cuenta: " + cuentaActual.getNumeroCuenta() + "\n";
        }
        return numerosCuenta;
    }

    public String consultarDatosCliente(){
        String mensaje = "Datos del cliente: \n"
                + "Nombre completo: " + this.nombre + " " + this.primerApellido + " " + this.segundoApellido + "\n"
                + "Identificacion: " + this.identificacion + "\n"
                + "Fecha de nacimiento: " + getFechaNacimiento() + "\n"
                + "Numero telefonico: " + this.numeroTelefonico + "\n"
                + "Correo electronico: " + this.correoElectronico + "\n"
                + "Numeros de cuenta:\n" + obtenerNumerosCuentaCliente();
        return mensaje;
    }
    
    public String cambiarFechaInsercion(){
        return this.fechaNacimiento.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")); 
    }

    @Override
    public String toString() {
        return "Cliente{" + "primerApellido=" + primerApellido + ", segundoApellido=" + segundoApellido + ", nombre=" + nombre + ", identificacion=" + identificacion + ", fechaNacimiento=" + fechaNacimiento + ", numeroTelefonico=" + numeroTelefonico + ", correoElectronico=" + correoElectronico + ", codigo=" + codigo + '}';
    }
    
    
}
