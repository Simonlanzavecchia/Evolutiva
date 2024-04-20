package codigo.seleccionPadres;

import java.util.Random;

public class seleccionPadresTorneo {
    
    public static int[][] seleccionPadresTorneoe(int[][] poblacionActual, int[] fitnessActual) {
        // Definir aqui el tamaño de individuos seleccionados para el torneo (tamaño de k)
        int tamTorneo = 4;
        int[][] padres = new int[poblacionActual.length][poblacionActual[0].length];
        Random random = new Random();

        // Voy a realizar tantos torneos como padres deba tener
        for (int i = 0; i < poblacionActual.length; i++) {
            // Selecciona aleatoriamente individuos para el torneo
            int[][] torneo = new int[tamTorneo][poblacionActual[0].length];
            int[] fitnessTorneo = new int[tamTorneo];
            for (int j = 0; j < tamTorneo; j++) {
                int indiceAleatorio = random.nextInt(poblacionActual.length);
                torneo[j] = poblacionActual[indiceAleatorio];
                fitnessTorneo[j] = fitnessActual[indiceAleatorio];
            }

            // Encontrar el individuo con el mejor fitness en el torneo
            int indiceMejor = encontrarMejorIndividuo(torneo, fitnessTorneo);
            padres[i] = torneo[indiceMejor].clone();
            System.out.println("Fitness padre: "+fitnessTorneo[indiceMejor]);
        }

        return padres;
    }

    // De los k inviduos del torneo, selecciono al mejor
    public static int encontrarMejorIndividuo(int[][] torneo, int[] fitnessTorneo) {
        int indiceMejor = 0;
        for (int i = 1; i < torneo.length; i++) {
            if (fitnessTorneo[i] < fitnessTorneo[indiceMejor]) {
                indiceMejor = i;
            }
        }
        return indiceMejor;
    }

}
