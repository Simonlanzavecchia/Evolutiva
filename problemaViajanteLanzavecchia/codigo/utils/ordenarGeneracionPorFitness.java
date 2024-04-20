package codigo.utils;

import java.util.Arrays;

public class ordenarGeneracionPorFitness {
    
    public static int[][] ordenarGeneracionPorFitnesse(int[][] generacion, int[] fitness) {
        Integer[] indices = new Integer[generacion.length];
        for (int i = 0; i < generacion.length; i++) {
            indices[i] = i;
        }
    
        // Ordena los índices de la generación según el fitness
        Arrays.sort(indices, (a, b) -> Integer.compare(fitness[a], fitness[b]));
    
        // Crea una nueva matriz ordenada según los índices
        int[][] generacionOrdenada = new int[generacion.length][];
        for (int i = 0; i < generacion.length; i++) {
            generacionOrdenada[i] = generacion[indices[i]];
        }
    
        return generacionOrdenada;
    }

}
