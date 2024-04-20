package codigo.cruce;

import java.util.Random;

public class cruzarPorOrden {
    
    public static int[][] cruzarPorOrdene(int[][] padres, int probabilidadCruce) {
        Random random = new Random();
        int[][] hijos = new int[padres.length][padres[0].length];

        // Tomo de a dos padres y avanzo dos porque se generan dos hijos
        for (int i = 0; i < padres.length - 1; i += 2) {
            // Verificar si se realiza el cruce según la probabilidad dada
            double randomNumber = random.nextDouble();
            if ((randomNumber*100) < probabilidadCruce) {
                int dimension = padres[0].length;
                int puntoCruce = random.nextInt(dimension); // Elegir un punto de cruce al azar

                // Crear los hijos
                int[] hijo1 = crearHijoPorOrden(padres[i], padres[i + 1], puntoCruce);
                int[] hijo2 = crearHijoPorOrden(padres[i + 1], padres[i], puntoCruce);

                // Asignar los hijos generados
                hijos[i] = hijo1.clone();
                hijos[i + 1] = hijo2.clone();
            } else {
                // Si no se realiza el cruce, los padres se mantienen sin cambios
                hijos[i] = padres[i].clone();
                hijos[i + 1] = padres[i + 1].clone();
            }
        }
        return hijos;
    }

    private static int[] crearHijoPorOrden(int[] padre1, int[] padre2, int puntoCruce) {
        int dimension = padre1.length;
        int[] hijo = new int[dimension];
    
        // Copiar los valores del primer padre antes del punto de cruce en el hijo
        for (int i = 0; i < puntoCruce; i++) {
            hijo[i] = padre1[i];
        }
    
        // Copiar los valores restantes en el hijo, manteniendo el orden relativo del segundo padre
        int indiceHijo = puntoCruce;
        for (int i = 0; i < dimension; i++) {
            int genActual = padre2[i];
            boolean estaPresente = false;
            for (int j = 0; j < puntoCruce; j++) {
                if (hijo[j] == genActual) {
                    estaPresente = true;
                    break;
                }
            }
            if (!estaPresente) {
                hijo[indiceHijo++] = genActual;
            }
        }
    
        return hijo;
    }

}
