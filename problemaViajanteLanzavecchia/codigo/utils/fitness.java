package codigo.utils;

public class fitness {
  
    // Mido el fitness de cada solucion de la poblacion dada, devolviendo el vector de fitness
    public static int[] fitnessPoblacione(int[][] poblacion, int[][] matrizDistancias) {
        int[] fitness = new int[poblacion.length];
        int sumaFitness = 0;
        for (int i = 0; i < fitness.length; i++){
            fitness[i] = fitnessSolucion(poblacion[i], matrizDistancias);
            sumaFitness = sumaFitness + fitness[i];
        }
        //System.out.println("Promedio fitness generacion = " + (sumaFitness/poblacion.length));
        return fitness;
    }

    // Mido el fitness (distancia en positivo) de la solucion dada, devolviendo el valor como entero
    public static int fitnessSolucion(int[] solucion, int[][] matrizDistancias) {
        int fitness = 0;
        for (int i = 0; i < solucion.length-1; i++){
            fitness = fitness + matrizDistancias[solucion[i]][solucion[i+1]];
        }
        // En caso que de la ultima ciudad se vuelva a la primera. Caso contrario quitar la siguiente linea
        fitness = fitness + matrizDistancias[solucion[solucion.length-1]][solucion[0]];
        return fitness;
    }

}
