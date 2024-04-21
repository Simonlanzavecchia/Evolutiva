package codigo.cruce;

import java.util.Arrays;
import java.util.Random;

public class cruzarPorArcos {
    
    public static int[][] cruzarPorArcose(int[][] padres, int probabilidadCruce) {
        int dimension = padres[0].length;
        Random random = new Random();
        int[][] hijos = new int[padres.length][dimension];
    
        // Realizo el procedimiento avanzando de a 1 y reutilizando padres 
        for (int i = 0; i < padres.length; i++) {

            // Inicializar arreglo binario para marcar elementos utilizados
            boolean[] elementosUsados = new boolean[dimension];
            Arrays.fill(elementosUsados, false);

            // Verificar si se realiza el cruce según la probabilidad de cruce dada
            double randomNumber = random.nextDouble() * 100;
            if (randomNumber < probabilidadCruce) {
                // Construir la tabla de arcos
                int sig;
                if(i == padres.length-1){
                    sig = 0;
                }else{
                    sig = i+1;
                }
                int[][] tablaArcos = crearTablaArcos(padres[i], padres[sig]);

                // Elegir al azar un elemento y ubicarlo en el hijo
                int[] hijo = new int[dimension];
                int indiceInicial = random.nextInt(dimension);
                hijo[0] = padres[i][indiceInicial];
                elementosUsados[hijo[0]] = true;

                // Remover todas las referencias al elemento elegido
                for (int j = 0; j < dimension; j++) {
                    for (int k = 0; k < 4; k++) {
                        if (tablaArcos[j][k] == hijo[0]) {
                            tablaArcos[j][k] = -1; // Marcar como removido
                        }
                    }
                }

                // Examinar la lista de arcos del elemento elegido
                for (int j = 1; j < dimension; j++) {
                    int[] arcos = tablaArcos[hijo[j - 1]]; // Obtener la lista de arcos del elemento actual
                    int siguienteElemento = -1;

                    // Buscar un arco común (+) o el elemento con la lista de arcos más corta
                    for (int k = 0; k < 4; k++) {
                        if (arcos[k] != -1 && (arcos[k] == hijo[0] || contiene(tablaArcos[arcos[k]], hijo[0]))) {
                            siguienteElemento = arcos[k];
                            break;
                        }
                    }

                    // Si no se encontró un arco común, elegir el elemento con la lista de arcos más corta
                    if (siguienteElemento == -1) {
                        int minLongitud = Integer.MAX_VALUE;
                        for (int k = 0; k < 4; k++) {
                            if ((arcos[k] != -1) && (contarArcos(tablaArcos[arcos[k]]) < minLongitud)) {
                                siguienteElemento = arcos[k];
                                minLongitud = contarArcos(tablaArcos[arcos[k]]);
                            }
                        }
                    }

                    // Si después de buscar en los arcos ninguno es válido, buscar en los no usados el mas corto
                    if (siguienteElemento == -1) {
                        int minLongitud = Integer.MAX_VALUE;
                        for (int k = 0; k < dimension; k++) {
                            if ((!elementosUsados[k]) && (contarArcos(tablaArcos[k]) < minLongitud)) {
                                siguienteElemento = k;
                                minLongitud = contarArcos(tablaArcos[k]);
                            }
                        }
                    }

                    // Agregar el siguiente elemento al hijo
                    hijo[j] = siguienteElemento;
                    elementosUsados[siguienteElemento] = true;

                    // Remover todas las referencias al elemento elegido
                    for (int k = 0; k < dimension; k++) {
                        for (int l = 0; l < 4; l++) {
                            if (tablaArcos[k][l] == siguienteElemento) {
                                tablaArcos[k][l] = -1; // Marcar como removido
                            }
                        }
                    }

                    if (i == 0){
                        System.out.print(" "+siguienteElemento);
                    }
                }
                // Tratar el último elemento del hijo fuera del bucle
                /*int[] arcosUltimoElemento = tablaArcos[hijo[dimension - 1]];
                int siguienteElementoUltimo = -1;
                for (int k = 0; k < 4; k++) {
                    if (arcosUltimoElemento[k] != -1) {
                        siguienteElementoUltimo = arcosUltimoElemento[k];
                        break;
                    }
                }
                hijo[dimension - 1] = siguienteElementoUltimo;*/

                if (i == 0){
                    System.out.print("Arcos padre "+i+": ");
                for(int j = 0; j < dimension; j++){
                    System.out.print(padres[i][j]+" ");
                }
                System.out.println();

                System.out.print("Arcos hijo "+i+": ");
                for(int j = 0; j < dimension; j++){
                    System.out.print(hijo[j]+" ");
                }
                System.out.println();
                }

                
                // Asignar el hijo generado a la matriz de hijos
                hijos[i] = hijo.clone();
            } else {
                // Si no se indica realizar el cruce, el hijo es igual al padre
                hijos[i] = padres[i].clone();
            }
        }

        return hijos;
    }
    
    
    
    // verificar si un determinado elemento está presente en un array
    private static boolean contiene(int[] array, int elemento) {
        for (int valor : array) {
            if (valor == elemento) {
                return true;
            }
        }
        return false;
    }
    
    // Cuenta la cantidad de arcos no vacios (!= de -1) en el array
    private static int contarArcos(int[] array) {
        int contador = 0;
        for (int valor : array) {
            if (valor != -1) {
                contador++;
            }
        }
        return contador;
    }

    private static int[][] crearTablaArcos(int[] is, int[] is2) {
        int longitud = is.length;
        // Son 2 arcos por padre
        int[][] tabla = new int[longitud][4];
        // Anoto los arcos del primero
        tabla[0][0] = is[longitud-1];
        tabla[0][1] = is[1];
        tabla[0][2] = is2[longitud-1];
        tabla[0][3] = is2[1];
        // Anoto los arcos del resto
        for (int i = 1; i < longitud-1; i++){
            tabla[i][0] = is[i-1];
            tabla[i][1] = is[i+1];
            tabla[i][2] = is2[i-1];
            tabla[i][3] = is2[i+1];
        }
        // Anoto los arcos del ultimo elemento
        tabla[longitud-1][0] = is[longitud-2];
        tabla[longitud-1][1] = is[0];
        tabla[longitud-1][2] = is2[longitud-2];
        tabla[longitud-1][3] = is2[0];
        return tabla;
    }

}
