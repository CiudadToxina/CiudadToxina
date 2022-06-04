package utilidades;

public class Ordenamiento {
    
    public static void ordenarAscendente(Comparable [] arreglo) { 
        for (int indice = 1; indice < arreglo.length && arreglo[indice] != null; indice ++) { 
            Comparable comparar = arreglo [indice]; 
            int contador = indice - 1; 
            while (contador >= 0 && ! arreglo [contador].menorQue(comparar)) { 
                arreglo [contador+1] = arreglo [contador]; 
                contador --; 
            } 
            arreglo [contador+1] = comparar; 
        }
    }
    
    public static void ordenarDescendente(Comparable [] arreglo) { 
        for (int indice = 1; indice < arreglo.length && arreglo[indice] != null; indice ++) { 
            Comparable comparar = arreglo [indice]; 
            int contador = indice - 1; 
            while (contador >= 0 && arreglo [contador].menorQue(comparar)) { 
                arreglo [contador+1] = arreglo [contador]; 
                contador --; 
            } 
            arreglo [contador+1] = comparar; 
        }
    }
}
