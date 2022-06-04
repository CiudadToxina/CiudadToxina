/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicadenegocios;

import java.time.LocalDate;
import utilidades.ObtenerTipoCambio;

/**
 *
 * @author valef
 */
public class OperacionMonetaria extends Operacion{
    private boolean cargoConceptoComision;
    private double montoOperacion;

    public OperacionMonetaria(){
        
    }
    
    public OperacionMonetaria(boolean pCargoConceptoComision, double pMontoOperacion, TipoOperacion pTipoOperacion) {
        super(pTipoOperacion);
        this.cargoConceptoComision = pCargoConceptoComision;
        this.montoOperacion = pMontoOperacion;
    }

    public boolean isCargoConceptoComision() {
        return cargoConceptoComision;
    }

    public void setCargoConceptoComision(boolean pCargoConceptoComision) {
        this.cargoConceptoComision = pCargoConceptoComision;
    }

    public double getMontoOperacion() {
        return montoOperacion;
    }

    public void setMontoOperacion(double pMontoOperacion) {
        this.montoOperacion = pMontoOperacion;
    } 
    
    private String obtenerCargoPorComision(){
        String cargoPorComision;
        if(this.cargoConceptoComision == true){
            cargoPorComision = "SI";
        }else{
            cargoPorComision = "NO";
        }
        return cargoPorComision;
    }
    
    @Override
    protected String obtenerInfoGeneralOperacion(){
        String mensaje = "";
        mensaje += "Fecha de la operacion: " + this.fechaOperacion + "\n" 
                + "Tipo de operacion: " + this.tipoOperacion + "\n"
                + "Cargo por concepto de comision: " + obtenerCargoPorComision() + "\n";
        return mensaje;
    }
    
    private double convertirColonesADolares(double pMontoColones){
        ObtenerTipoCambio tipoCambio = new ObtenerTipoCambio();
        double compraDolar = tipoCambio.obtenerTipoCambio(317);
        
        double montoEnColones = pMontoColones/compraDolar;
        return montoEnColones;
    }
    
    String consultarOperacionLocal(){
        String mensaje = obtenerInfoGeneralOperacion()
                + "Monto de operacion: " + redondear.format(this.montoOperacion) + "\n";
        return mensaje;
    }
    
    String consultarOperacionExtranjera(){
        String mensaje = obtenerInfoGeneralOperacion()
                + "Monto de operacion: " + redondear.format(convertirColonesADolares(montoOperacion)) + "\n";
        return mensaje;
    }
}
