package codigo.seleccion;

import codigo.utils.ordenarGeneracionPorFitness;

public class seleccionSteadyState {
    
    public static int[][] seleccionSteadyStatee(int[][] hijos, int[][] padres, int[] fitnessGeneracion, int[] fitnessPadres) {
        int[][] poblacionActual = padres;
        // Establecer el corte 
        int corte = (int) (fitnessGeneracion.length*0.66);

        // Ordenar generaciones
        int[][] hijosOrdenados = ordenarGeneracionPorFitness.ordenarGeneracionPorFitnesse(hijos, fitnessGeneracion);
        int[][] padresOrdenados = ordenarGeneracionPorFitness.ordenarGeneracionPorFitnesse(padres, fitnessPadres);

        // Agrego padres hasta el corte
        for(int i = 0; i < corte; i++){
            poblacionActual[i] = padresOrdenados[i].clone();
        }

        // LLeno con los mejores hijos
        for(int i = 0; (i + corte) < poblacionActual.length; i++){
            poblacionActual[i+corte] = hijosOrdenados[i].clone();
        }

        return poblacionActual;
    }

}
