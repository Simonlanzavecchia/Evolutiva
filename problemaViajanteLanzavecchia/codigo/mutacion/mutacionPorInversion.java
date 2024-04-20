package codigo.mutacion;

import java.util.Random;

public class mutacionPorInversion {
    
    public static int[][] mutacionPorInversione(int[][] padres, int probabilidadMutacion) {
        int[][] hijos = padres;
        Random random = new Random();
        // Realizo la mutacion en cada elemento donde se de la probabilidad
        for (int i = 0; i < padres.length; i++) {

            // Verificar si se realiza la mutacion según la probabilidad dada
            double randomNumber = random.nextDouble() * 100;
            if (randomNumber < probabilidadMutacion) {
                int pos1 = random.nextInt(hijos[i].length);
                int pos2 = random.nextInt(hijos[i].length);
                while (pos1 == pos2){
                    pos2 = random.nextInt(hijos[i].length);
                }
                if (pos1 > pos2){
                    int aux = pos1;
                    pos1 = pos2;
                    pos2 = aux;
                }
                for(int j = 0; j < (pos2-pos1); j++){
                    hijos[i] = hijos[i].clone();
                    int aux = hijos[i][pos1+j];
                    hijos[i][pos1+j] = hijos[i][pos2-j];
                    hijos[i][pos2-j] = aux;
                }
            }
        }
        return hijos;
    }

}
