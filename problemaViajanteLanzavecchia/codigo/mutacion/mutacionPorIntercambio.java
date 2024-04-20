package codigo.mutacion;

import java.util.Random;

public class mutacionPorIntercambio {
    
    public static int[][] mutacionPorIntercambioe(int[][] padres, int probabilidadMutacion) {
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

                hijos[i] = hijos[i].clone();
                int aux = hijos[i][pos1];
                hijos[i][pos1] = hijos[i][pos2];
                hijos[i][pos2] = aux;

            }
        }
        return hijos;
    }

}
