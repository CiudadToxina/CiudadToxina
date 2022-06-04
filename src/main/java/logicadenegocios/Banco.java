
package logicadenegocios;

/**
 *
 * @author sanch
 */
public class Banco {
    private double totalComisionesDepositosRetiros;
    private double totalComisionesDepositos;
    private double totalComisionesRetiros;
    public CuentaBancaria[] todasLasCuentas = new CuentaBancaria [50];
    public Cliente[] todosLosClientes = new Cliente [25];
   
    public Banco(){
        
    }
    
    //Métodos de clase
    // NÚMERO 20 -- CALCULAR EL TOTAL DE LAS COMISIONES
    public double calcularComisionesTotales(){
       
       for(int i = 0; i < CuentaBancaria.getCantidadCuentas(); i++){
           CuentaBancaria actual = todasLasCuentas [i];
           totalComisionesDepositosRetiros += actual.getTotalComisionesDepositosRetirosCuenta();
       }
       return totalComisionesDepositosRetiros;
   }
    
    public CuentaBancaria buscarCuenta(int pNumeroCuenta){
        for(int i = 0; i < CuentaBancaria.getCantidadCuentas(); i++){
            CuentaBancaria cuentaActual = todasLasCuentas[i];
            
            if(cuentaActual.getNumeroCuenta() == pNumeroCuenta){
                return cuentaActual;
            }
        }
        return null;
    }
    
    public Cliente buscarCliente(int pIdentificacion){
        Cliente clienteEncontrado = null;
        for(int i = 0; i < Cliente.getCantidadClientes() ; i++){
            clienteEncontrado = todosLosClientes[i];
            
            if(clienteEncontrado.getIdentificacion() == pIdentificacion){
                break;
            }
            clienteEncontrado = null;
        }
        return clienteEncontrado;
    }
    
    public Cliente buscarDuenioCuenta(int pNumeroCuenta){
        for(int indiceCliente = 0; indiceCliente < Cliente.getCantidadClientes() ; indiceCliente++){
            Cliente clienteActual = todosLosClientes[indiceCliente];
            if (compararCuentaCliente(clienteActual, pNumeroCuenta) == true){
                return clienteActual;
            }
        }
            return null;
    }
    
    private boolean compararCuentaCliente(Cliente clienteActual, int pNumeroCuenta){
        for(int indiceCuenta = 0; indiceCuenta < clienteActual.getCantidadCuentasCliente() ; indiceCuenta++){
            CuentaBancaria cuentaActual = clienteActual.getMisCuentas()[indiceCuenta];
            if(cuentaActual.getNumeroCuenta() == pNumeroCuenta){
                return true;
            }
        }
        return false;
    }
    
    public void agregarCliente(String pPrimerApellido, String pSegundoApellido, String pNombre, int pIdentificacion, int pNumeroTelefonico, String pCorreoElectronico,
            int pDia, int pMes, int pAnio){
        Cliente nuevoCliente = new Cliente(pPrimerApellido, pSegundoApellido, pNombre, pIdentificacion, pNumeroTelefonico, pCorreoElectronico, pDia, pMes, pAnio);
        todosLosClientes [Cliente.getCantidadClientes()-1] = nuevoCliente;
    }  
    
    public void agregarCuenta(CuentaBancaria pCuenta){
        todasLasCuentas [CuentaBancaria.getCantidadCuentas()-1] = pCuenta;
    }  
    public Cliente[] getTodosLosClientes() {
        return todosLosClientes;
    }

    public CuentaBancaria[] getTodasLasCuentas() {
        return todasLasCuentas;
    }
}
