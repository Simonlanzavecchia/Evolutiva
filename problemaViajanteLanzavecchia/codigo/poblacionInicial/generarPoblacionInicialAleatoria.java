package codigo.poblacionInicial;

import java.util.Random;

public class generarPoblacionInicialAleatoria {
    
    public static int[][] generarPoblacionInicialAleatoriae(int tamanoPoblacionInicial, int dimension) {
        int[][] poblacionInicial = new int[tamanoPoblacionInicial][dimension];
        Random rand = new Random();

        for (int i = 0; i < tamanoPoblacionInicial; i++) {
            // Inicializar la permutación
            for (int j = 0; j < dimension; j++) {
                poblacionInicial[i][j] = j;
            }
            // Fisher-Yates shuffle
            for (int j = dimension - 1; j > 0; j--) {
                int index = rand.nextInt(j + 1);
                int temp = poblacionInicial[i][index];
                poblacionInicial[i][index] = poblacionInicial[i][j];
                poblacionInicial[i][j] = temp;
            }
        }
        
        /*System.out.print("Poblacion inicial: ");
        for(int i = 0; i < tamanoPoblacionInicial; i++){
            System.out.print("\n");
            for(int j = 0; j < dimension; j++){
                System.out.print(poblacionInicial[i][j]+",");
            }
        }*/
        
        return poblacionInicial;
    }

}
