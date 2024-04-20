package codigo.seleccionPadres;

import java.util.Random;

public class seleccionPadresRuleta {
    
    public static int[][] seleccionPadresRuletae(int[][] poblacionActual, int[] fitnessActual) {
        int[][] padres = new int[poblacionActual.length][poblacionActual[0].length];

        // Sumar las distancias de todas las soluciones
        double sumaFitness = 0;
        for (int i = 0; i < fitnessActual.length; i++) {
            sumaFitness += fitnessActual[i];
        }

        // Crear el vector de probabilidades acumuladas
        double[] vectorProbabilidad = new double[fitnessActual.length];
        vectorProbabilidad[0] = 1 - (fitnessActual[0] / sumaFitness);
        for (int i = 1; i < fitnessActual.length; i++) {
            double probabilidadInvertida = 1 - (fitnessActual[i] / sumaFitness);
            vectorProbabilidad[i] = vectorProbabilidad[i - 1] + probabilidadInvertida;
        }

        // Normalizar el vector de probabilidades acumuladas
        double sumaProbabilidades = vectorProbabilidad[fitnessActual.length - 1];
        for (int i = 0; i < fitnessActual.length; i++) {
            vectorProbabilidad[i] = vectorProbabilidad[i] / sumaProbabilidades;
            //System.out.println("Probabilidad de elemento " + i + " : " + vectorProbabilidad[i]);
        }

        // Realizar las tiradas de ruleta para seleccionar a los padres
        Random random = new Random();
        for (int k = 0; k < padres.length; k++) {
            double randomNumber = random.nextDouble(); // Generar número aleatorio entre 0 y 1
            int indicePadre = buscarIndice(vectorProbabilidad, randomNumber);
            padres[k] = poblacionActual[indicePadre].clone();
        }

        return padres;
    }

    // Dado un numero random entre 0 y 1, y el vector de probabilidades segun el fitness, devuelve el incice del padre seleccionado
    // utilizo busqueda binaria para mejorar el rendimiento de este paso
    private static int buscarIndice(double[] vectorProbabilidad, double randomNumber) {
        int inicio = 0;
        int fin = vectorProbabilidad.length - 1;
        while (inicio <= fin) {
            int medio = inicio + (fin - inicio) / 2;
           if (vectorProbabilidad[medio] >= randomNumber) {
                fin = medio - 1;
            } else {
                inicio = medio + 1;
            }
        }
        return inicio;
    }

}
