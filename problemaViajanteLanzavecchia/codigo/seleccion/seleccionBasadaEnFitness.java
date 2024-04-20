package codigo.seleccion;

import java.util.Random;
import codigo.seleccionPadres.seleccionPadresTorneo;

public class seleccionBasadaEnFitness {
    
    public static int[][] seleccionBasadaEnFitnesse(int[][] hijos, int[][] padres, int[] fitnessGeneracion, int[] fitnessPadres, int metodoSeleccionPadres) {
        
        // Combinar padres e hijos en una sola matriz
        int[][] poblacionTotal = new int[padres.length + hijos.length][];
        System.arraycopy(padres, 0, poblacionTotal, 0, padres.length);
        System.arraycopy(hijos, 0, poblacionTotal, padres.length, hijos.length);
        
        // Combinar los arreglos de fitness
        int[] fitnessTotal = new int[fitnessGeneracion.length + fitnessPadres.length];
        System.arraycopy(fitnessPadres, 0, fitnessTotal, 0, fitnessPadres.length);
        System.arraycopy(fitnessGeneracion, 0, fitnessTotal, fitnessPadres.length, fitnessGeneracion.length);
        
        // Seleccionar padres según torneo
        int tamTorneo = 2;
        int[][] poblacionActual = new int[padres.length][];
        Random random = new Random();

        // Voy a realizar tantos torneos como padres deba tener
        for (int i = 0; i < padres.length; i++) {
            // Selecciona aleatoriamente individuos para el torneo
            int[][] torneo = new int[tamTorneo][poblacionTotal[0].length];
            int[] fitnessTorneo = new int[tamTorneo];
            for (int j = 0; j < tamTorneo; j++) {
                int indiceAleatorio = random.nextInt(poblacionTotal.length);
                torneo[j] = poblacionTotal[indiceAleatorio];
                fitnessTorneo[j] = fitnessTotal[indiceAleatorio];
            }

            // Encontrar el individuo con el mejor fitness en el torneo
            int indiceMejor = seleccionPadresTorneo.encontrarMejorIndividuo(torneo, fitnessTorneo);
            poblacionActual[i] = torneo[indiceMejor].clone();
        }
        return poblacionActual;
    }

}
