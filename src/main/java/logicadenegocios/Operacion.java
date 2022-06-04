
package logicadenegocios;

import java.text.DecimalFormat;
import java.time.LocalDate;

/**
 *
 * @author valef
 */
public abstract class Operacion {
    protected LocalDate fechaOperacion;
    protected TipoOperacion tipoOperacion;
    protected static int cantidadOperaciones = 0;
    protected int identificadorOperacion;
    
    DecimalFormat redondear = new DecimalFormat("###.##");

    public Operacion(){
        
    }
    
    public Operacion(TipoOperacion pTipoOperacion) {
        this.fechaOperacion = LocalDate.now();
        this.tipoOperacion = pTipoOperacion;
        cantidadOperaciones++;
        this.identificadorOperacion = cantidadOperaciones;
    }
    
    public LocalDate getFechaOperacion() {
        return fechaOperacion;
    }

    public void setFechaOperacion() {
        this.fechaOperacion = LocalDate.now();
    }

    public TipoOperacion getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(TipoOperacion tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    public int getIdentificadorOperacion() {
        return identificadorOperacion;
    }

    public void setIdentificadorOperacion(int identificadorOperacion) {
        this.identificadorOperacion = identificadorOperacion;
    }
    
    public void setFechaOperacionBD(LocalDate fecha){
        this.fechaOperacion = fecha;
    }

    public static int getCantidadOperaciones() {
        return cantidadOperaciones;
    }
    
    public static void setCantidadOperaciones(int cantidadOperaciones) {
        Operacion.cantidadOperaciones = cantidadOperaciones;
    }
    
    protected abstract String obtenerInfoGeneralOperacion();    
}
