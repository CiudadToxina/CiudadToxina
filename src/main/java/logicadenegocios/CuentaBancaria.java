
package logicadenegocios;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;
import utilidades.ObtenerTipoCambio;
import utilidades.Comparable;
import utilidades.SimpleOperacionFactory;
import logicadepresentacion.TiendaOperaciones;

/**
 *
 * @author valef
 */
public class CuentaBancaria implements Comparable{
    private static int cantidadCuentas = 0;
    private LocalDate fechaCreacion;
    private double saldo;
    private boolean estatus;
    private int numeroCuenta;
    private String pin;
    private double totalComisionesDepositosRetirosCuenta; // monto total de todas las comisiones hechas
    private double totalComisionesDepositosCuenta; // monto total de comisiones por depositos
    private double totalComisionesRetirosCuenta; // monto total de comisiones por retiros
    private int cantComisionesTotal; //cantidad de comisiones que se han hecho
    private int cantOperacionesMonetarias = 0;
    private int cantOperacionesAdministrativas = 0;
    private int pinFallido = 0;
    private int telFallido = 0;
    
    private static ArrayList<Integer> numerosCuenta = new ArrayList<>();
    public OperacionMonetaria[] misOperacionesMonetarias = new OperacionMonetaria[50];
    public OperacionAdministrativa[] misOperacionesAdministrativas = new OperacionAdministrativa[50];
    
    private static SimpleOperacionFactory operacionFactory = new SimpleOperacionFactory();
    private static TiendaOperaciones tiendaOperaciones = new TiendaOperaciones(operacionFactory);

    //Constructor
    public CuentaBancaria(double pSaldo, String pPin) {
        this.fechaCreacion = LocalDate.now();
        this.saldo = pSaldo;
        this.estatus = true;
        setNumeroCuenta();

        this.pin = pPin;
        cantidadCuentas++;
    }
    
    //Métodos Accesores
    public static int getCantidadCuentas() {
        return cantidadCuentas;
    }

    public static void setCantidadCuentas(int pCantidadCuentas) {
        CuentaBancaria.cantidadCuentas = pCantidadCuentas;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double pSaldo) {
        this.saldo = pSaldo;
    }

    public boolean getEstatus() {
        return estatus;
    }

    public void setEstatus(boolean pEstatus) {
        this.estatus = pEstatus;
    }

    public int getNumeroCuenta() {
        return numeroCuenta;
    }
     
    public void setNumeroCuenta() {
        Random rand = new Random();
        
        //Como se buscan 9 digitos, el maximo se inicia en n y en caso de ser 0, se le suma 100000000
        int n = rand.nextInt(899999999);

        n += 100000000;
        this.numeroCuenta = n;
        if(numCuentaExiste(n)){
            setNumeroCuenta();
        }
    }

    public void setFechaCreacion(int pAnio, int pMes, int pDia) {
        this.fechaCreacion =  LocalDate.of(pAnio, pMes, pDia);
    }
    
    public void establecerNumeroCuenta(int pNumCuenta){
        this.numeroCuenta = pNumCuenta;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
    
    public double getTotalComisionesDepositosRetirosCuenta() {
        return totalComisionesDepositosRetirosCuenta;
    }

    public void setTotalComisionesDepositosRetirosCuenta(double totalComisionesDepositosRetirosCuenta) {
        this.totalComisionesDepositosRetirosCuenta = totalComisionesDepositosRetirosCuenta;
    }

    public double getTotalComisionesDepositosCuenta() {
        return totalComisionesDepositosCuenta;
    }

    public void setTotalComisionesDepositosCuenta(double totalComisionesDepositosCuenta) {
        this.totalComisionesDepositosCuenta = totalComisionesDepositosCuenta;
    }

    public double getTotalComisionesRetirosCuenta() {
        return totalComisionesRetirosCuenta;
    }

    public void setTotalComisionesRetirosCuenta(double totalComisionesRetirosCuenta) {
        this.totalComisionesRetirosCuenta = totalComisionesRetirosCuenta;
    }
    
    public int getCantComisionesTotal() {
        return cantComisionesTotal;
    }

    public void setCantComisionesTotal(int cantComisionesTotal) {
        this.cantComisionesTotal = cantComisionesTotal;
    }

    public int getCantOperacionesMonetarias() {
        return cantOperacionesMonetarias;
    }

    public void setCantOperacionesMonetarias(int cantOperacionesMonetarias) {
        this.cantOperacionesMonetarias = cantOperacionesMonetarias;
    }

    public int getCantOperacionesAdministrativas() {
        return cantOperacionesAdministrativas;
    }

    public void setCantOperacionesAdministrativas(int cantOperacionesAdministrativas) {
        this.cantOperacionesAdministrativas = cantOperacionesAdministrativas;
    }

    public int getPinFallido() {
        return pinFallido;
    }

    public void setPinFallido(int pinFallido) {
        this.pinFallido = pinFallido;
    }

    public int getTelFallido() {
        return telFallido;
    }

    public void setTelFallido(int telFallido) {
        this.telFallido = telFallido;
    }

    public OperacionMonetaria[] getMisOperacionesMonetarias() {
        return misOperacionesMonetarias;
    }

    //Métodos de clase
    private boolean numCuentaExiste(int numCuenta){
        boolean resultado = false;
        int indiceNumCuenta = numerosCuenta.indexOf(numCuenta);
        if(indiceNumCuenta != -1){
            resultado = true;
        }
        return resultado;
    }
    
    public boolean validarPin(String pPin){
        String pinActual = this.getPin();
        return pinActual.equals(pPin);
    }
    
    public boolean verificarFondos(double pMonto){
        double saldoActual = this.getSaldo();
        
        if(saldoActual > pMonto){
            return true;
        }
        else{
            return false;
        }
    }
    
    public String consultarDatosCuenta(){
        String mensaje = "Datos de la cuenta:\n" 
                + obtenerInfoGeneralCuenta() 
                + "Saldo actual: " + this.saldo + "\n" 
                + "Numero de cuenta: " + this.numeroCuenta + "\n";
        return mensaje;
    }
    
    public double calcularComision(double pMonto){
        double comision;
        
        if(cantComisionesTotal >= 3){
            comision = pMonto * 0.02;
        }
        else{
            comision = 0;
        }
        
        return comision;
    }
    
    private void depositar(double pMonto){
        saldo += pMonto;
    }   
    
    private void retirar(double pMonto){
        saldo -= pMonto;
    }
       
    public double convertirDolaresAColones(double pMontoDolares){
        ObtenerTipoCambio tipoCambio = new ObtenerTipoCambio();
        double ventaDolar = tipoCambio.obtenerTipoCambio(318);
        
        double montoEnColones = pMontoDolares * ventaDolar;
        return montoEnColones;
    }
    
    private double convertirColonesADolares(double pMontoColones){
        ObtenerTipoCambio tipoCambio = new ObtenerTipoCambio();
        double compraDolar = tipoCambio.obtenerTipoCambio(317);
        
        double montoEnColones = pMontoColones/compraDolar;
        return montoEnColones;
    }
    
    private boolean cargoPorConceptoDeComision(double pComision){
        if (pComision > 0){
            return true;
        }else{
            return false;
        }
    }
    
    public void registrarOperacionMonetaria(boolean pCargoConceptoComision, double pMontoOperacion, TipoOperacion pTipoOperacion){
        OperacionMonetaria nuevaOperacion = new OperacionMonetaria(pCargoConceptoComision, pMontoOperacion, pTipoOperacion);
        //Operacion nuevaOperacion = tiendaOperaciones.crearOperacion("OperacionMonetaria");
        this.misOperacionesMonetarias[cantOperacionesMonetarias] = nuevaOperacion;
        this.cantOperacionesMonetarias++;
    }
    
    public void registrarOperacionAdministrativa(TipoOperacion pTipoOperacion){
        OperacionAdministrativa nuevaOperacion = new OperacionAdministrativa(pTipoOperacion);
        this.misOperacionesAdministrativas[cantOperacionesAdministrativas] = nuevaOperacion;
        this.cantOperacionesAdministrativas++;
    }
    
    public void depositarPropiaCuentaLocal(double pColones) {
        cantComisionesTotal ++;
        double comision = calcularComision(pColones);
        boolean cargoComision = cargoPorConceptoDeComision(comision);
        
        this.depositar(pColones);
        this.retirar(comision);
          
        this.totalComisionesDepositosCuenta += comision;
        this.totalComisionesDepositosRetirosCuenta += comision;      
        
        registrarOperacionMonetaria(cargoComision, pColones, TipoOperacion.Deposito);
    }
     
    public void depositarPropiaCuentaExtranjero(double pDolares) {        
        cantComisionesTotal ++;
        double pColones = convertirDolaresAColones(pDolares);
        double comision = calcularComision(pColones);
        boolean cargoComision = cargoPorConceptoDeComision(comision);
        
        this.depositar(pColones);
        this.retirar(comision);
         
        this.totalComisionesDepositosCuenta += comision;
        this.totalComisionesDepositosRetirosCuenta += comision;
        
        registrarOperacionMonetaria(cargoComision, pColones, TipoOperacion.Deposito);
    }
      
    public void retirarPropiaCuentaLocal(double pColones) {
        cantComisionesTotal ++;
        double comision = calcularComision(pColones);
        boolean cargoComision = cargoPorConceptoDeComision(comision);
        
        this.retirar(pColones);
        this.retirar(comision);
            
        this.totalComisionesRetirosCuenta += comision;
        this.totalComisionesDepositosRetirosCuenta += comision;
      
        
        registrarOperacionMonetaria(cargoComision, pColones, TipoOperacion.Retiro);
    }
    
    public void retirarPropiaCuentaExtranjero(double pDolares) {
        cantComisionesTotal ++;
        double pColones = convertirDolaresAColones(pDolares);
        double comision = calcularComision(pColones);
        boolean cargoComision = cargoPorConceptoDeComision(comision);
        
        this.retirar(pColones);
        this.retirar(comision);
            
        this.totalComisionesRetirosCuenta += comision;
        this.totalComisionesDepositosRetirosCuenta += comision;
        
        
        registrarOperacionMonetaria(cargoComision, pColones, TipoOperacion.Retiro);
    }
    
    public void realizarTransferenciaLocal(CuentaBancaria pCuentaDestino, double pColones) {
        this.retirarPropiaCuentaLocal(pColones);
        pCuentaDestino.depositar(pColones);
    }
    
    public String obtenerEstatusCuenta(){
        String estatusCuenta = "";
        if (this.estatus == true){
            estatusCuenta += "ACTIVA";
        }else{
            estatusCuenta += "INACTIVA";
        }
        return estatusCuenta;
    }
     
    public String consultarEstatusCuenta() {
        String mensaje = "La cuenta numero: " + numeroCuenta + " tiene estatus " + obtenerEstatusCuenta();
        return mensaje;
    }
       
    @Override
    public boolean menorQue(Comparable objeto){
        return saldo < ((CuentaBancaria)objeto).getSaldo();
    } 
    
    private double obtenerSaldoCuentaExtranjero(){
        double saldoEnDolares = convertirColonesADolares(this.saldo);
        return saldoEnDolares;
    }
    
    public String consultarSaldoActualExtranjero(){
        ObtenerTipoCambio tipoCambio = new ObtenerTipoCambio();
        double compraDolar = tipoCambio.obtenerTipoCambio(317);
        String mensaje = "";
        mensaje += "Estimado usuario el saldo actual de su cuenta es " + obtenerSaldoCuentaExtranjero() + " dolares\n"
                + "Para esta conversion se utilizo el tipo de cambio del dolar, precio de compra\n"
                + "[Segun el BCCR, el tipo de cambio de compra del dolar de hoy es: " + compraDolar + "]\n";
        return mensaje;
    }
    
    private String obtenerInfoGeneralCuenta(){
        String mensaje = "Fecha de creacion: " + this.fechaCreacion + "\n"
                + "Estatus cuenta: " + obtenerEstatusCuenta() + "\n";
        return mensaje;        
    }
    
    private String imprimirOperacionesAdministrativas(){
        String mensaje = "";
        for(int indice = 0; indice<this.cantOperacionesAdministrativas; indice++){
            OperacionAdministrativa operacionActual = this.misOperacionesAdministrativas[indice];
            mensaje += operacionActual.obtenerInfoGeneralOperacion();
        }
        return mensaje;        
    }
    
    private String imprimirOperacionesColones(){
        String mensaje = "\nOperaciones realizadas (en colones): \n";
        for(int indice = 0; indice<this.cantOperacionesMonetarias; indice++){
            OperacionMonetaria operacionActual = this.misOperacionesMonetarias[indice];
            mensaje += operacionActual.consultarOperacionLocal() + "\n";
        }
        mensaje += imprimirOperacionesAdministrativas();
        return mensaje;
    }    
    
    private String imprimirOperacionesDolares(){
        String mensaje = "\nOperaciones realizadas (en dolares): \n";
        for(int indice = 0; indice<this.cantOperacionesMonetarias; indice++){
            OperacionMonetaria operacionActual = this.misOperacionesMonetarias[indice];
            mensaje += operacionActual.consultarOperacionExtranjera() + "\n";
        }
        mensaje += imprimirOperacionesAdministrativas();
        return mensaje;        
    } 
    
    public String consultarEstadoCuentaLocal(){
        DecimalFormat redondear = new DecimalFormat("###.##");
        
        String mensaje = "\nEstado de Cuenta:\n"
                + obtenerInfoGeneralCuenta()
                + "Saldo actual: " + redondear.format(this.saldo) + "\n"
                + imprimirOperacionesColones();     
        return mensaje;
    }
    
    public String consultarEstadoCuentaExtranjero(){
        DecimalFormat redondear = new DecimalFormat("###.##");
        
        String mensaje = "Estado de Cuenta (divisa extranjera):\n"
                + obtenerInfoGeneralCuenta()
                + "Saldo actual: " + redondear.format(obtenerSaldoCuentaExtranjero()) + "\n"
                + imprimirOperacionesDolares();
        return mensaje;
    }
    
    public String generarPalabra(){
        String mensaje = "Su palabra de confirmacion es: ";
       
        Random random = new Random();
        String setOfCharacters = "abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        
        for(int i=0; i<5; i++){
             int randomInt = random.nextInt(setOfCharacters.length());
             char randomChar = setOfCharacters.charAt(randomInt);
             mensaje = mensaje + randomChar;
        }
        
        return mensaje;
    }

    @Override
    public String toString() {
        return "CuentaBancaria{" + ", fechaCreacion=" + fechaCreacion + ", saldo=" + saldo + ", estatus=" + estatus + ", numeroCuenta=" + numeroCuenta + ", pin=" + pin + ", totalComisionesDepositosRetirosCuenta=" + totalComisionesDepositosRetirosCuenta + ", totalComisionesDepositosCuenta=" + totalComisionesDepositosCuenta + ", totalComisionesRetirosCuenta=" + totalComisionesRetirosCuenta + ", cantComisionesTotal=" + cantComisionesTotal + '}';
    } 

    public OperacionAdministrativa[] getMisOperacionesAdministrativas() {
        return this.misOperacionesAdministrativas;
    }
}
