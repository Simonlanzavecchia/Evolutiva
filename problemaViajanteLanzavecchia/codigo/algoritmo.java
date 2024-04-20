package codigo;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Scanner;

import codigo.cruce.*;
import codigo.mutacion.*;
import codigo.poblacionInicial.*;
import codigo.seleccion.*;
import codigo.seleccionPadres.*;
import codigo.utils.*;


public class algoritmo {
    //Funcion que lee el archivo de entrada, prepara los parametros de entrada y llama al algoritmo
    public static String EjecutarAlgoritmo(File archivoLectura, int[] configuracion){

        // Inicializo parametros a guardar
        String registro = "";
        int cantidadGeneraciones = configuracion[8];
        System.out.println("Cantidad de generaciones: " + cantidadGeneraciones);
        int[] fitnessPorGeneracion = new int[cantidadGeneraciones];
        Instant tiempoInicial = Instant.now();

        // Dentro de try por si no hay archivo
        try {
            // Abro el lector
            Scanner lector = new Scanner(archivoLectura);
            System.out.println("Procesando archivo");

            // Obtengo el nombre del problema
            lector.skip("NAME: ");
            String nombreArchivo = lector.next();

            // Obtengo la matriz de distancias
            int[][] matrizDistancias = obtenerDistancias(lector);


            int solucion = algoritmoGenetico(configuracion, matrizDistancias, fitnessPorGeneracion);

            // Cierro el lector
            lector.close();

            // Preparo el registro de la ejecucion para guardarlo en el archivo de resultados
            Instant tiempoFinal = Instant.now();
            long tiempoEjecucion = Duration.between(tiempoInicial, tiempoFinal).toMillis();
            registro = new String("Nombre de archivo: " + nombreArchivo + "\n" + "Configuracion usada: " + Arrays.toString(configuracion) +
                                  "\n" + "Tiempo de ejecucion: " + Duration.ofMillis(tiempoEjecucion) + " milisegundos \n" + "Mejor fitness por generacion: " + 
                                  Arrays.toString(fitnessPorGeneracion) + "\n" + "Mejor resultado: " + solucion + "\n\n");            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("Registro de la ejecucion: \n" + registro);
        return registro;
    }

    //El algoritmo en si
    private static int algoritmoGenetico(int[] configuracion, int[][] matrizDistancias, int[] fitnessPorGeneracion) {
        // solucionFitness = Integer.MAX_VALUE: ERROR
        int solucionFitness = Integer.MAX_VALUE;
        int iteracion = 0;

        /// Inicializar poblacion segun parametro de metodo y tamano
        int tamanoPoblacionInicial = configuracion[1];
        System.out.println("tamanoPoblacionInicial: " + tamanoPoblacionInicial);
        int dimension = matrizDistancias[0].length;
        int[] solucion = new int[dimension];
        int[][] poblacionInicial = new int[tamanoPoblacionInicial][dimension];
        // metodoPoblacionInicial == 0: generacion aleatoria
        if ((configuracion[0] == 0)){
            poblacionInicial = generarPoblacionInicialAleatoria.generarPoblacionInicialAleatoriae(tamanoPoblacionInicial, dimension);
            /*System.out.println("generarPoblacionInicialAleatoria: ");
            for(int i = 0; i < poblacionInicial.length; i++){
                for(int j = 0; j < poblacionInicial[0].length; j++){
                    System.out.print(poblacionInicial[i][j]+" ");
                }
                System.out.print("\n");
            }*/
        }

        // Evaluar cada candidato (funcion fitness) de la poblacion inicial
        int[] fitnessInicial = new int[tamanoPoblacionInicial];
        fitnessInicial = fitness.fitnessPoblacione(poblacionInicial, matrizDistancias);
        /*System.out.println("fitnessInicial: ");
        for(int i = 0; i < fitnessInicial.length; i++){
            System.out.print(fitnessInicial[i]+" ");
        }
        System.out.print("\n");*/
        
        /// Iteraciones

        // Inicializar la poblacion y fitness para la primera iteracion con la inicial
        // TODO verificar si puedo usar referencia o si quiero guardar la inicial y deberia realizar copia
        int[][] poblacionActual = poblacionInicial;
        int[] fitnessActual = fitnessInicial;

        // Mientras no se cumpla la condicion de corte (maximo de iteraciones, numero de generaciones)
        int numeroGeneraciones = configuracion[8];
        while (iteracion < numeroGeneraciones){

            // Seleccionar pardes segun parametro de metodo
            int metodoSeleccionPadres = configuracion[2];
            int[][] padres = new int[tamanoPoblacionInicial][dimension];
            //metodoSeleccionPadres == 0: torneo
            if (metodoSeleccionPadres == 0){
                // Tengo el fitness de la generacion debido al seguimiento realizado, entonces lo uso para no generar mas computo
                // pero si no lo tuviese, solo calcularia el fitness de las soluciones en el torneo
                padres = seleccionPadresTorneo.seleccionPadresTorneoe(poblacionActual, fitnessActual);
                System.out.println("generacion resultante de torneo: ");
                for(int i = 0; i < padres.length; i++){
                    System.out.print("Solucion "+i+": ");
                    for(int j = 0; j < padres[0].length; j++){
                        System.out.print(padres[i][j]+" ");
                    }
                    System.out.print("Fitness: "+fitness.fitnessSolucion(padres[i], matrizDistancias));
                    System.out.print("\n");
                }
            }
            // metodoSeleccionPadres == 1: rueda de ruleta
            else if (metodoSeleccionPadres == 1){
                padres = seleccionPadresRuleta.seleccionPadresRuletae(poblacionActual, fitnessActual);
                /*System.out.println("generacion resultante de ruleta: ");
                for(int i = 0; i < padres.length; i++){
                    for(int j = 0; j < padres[0].length; j++){
                        System.out.print(padres[i][j]+" ");
                    }
                    System.out.print("Fitness: "+fitness.fitnessSolucion(padres[i], matrizDistancias));
                    System.out.print("\n");
                }*/
            }

            // Realizar cruce segun parametro de metodo y 
            // 0 = basado en arcos ; 1 = cruce de orden
            int operadorCruce = configuracion[3];
            // Probabilidad entre 0 y 100
            int probabilidadCruce = configuracion[4];
            int[][] hijos = new int[padres.length][padres[0].length];;
            // Cruce por arcos
            if(operadorCruce == 0){
                hijos = cruzarPorArcos.cruzarPorArcose(padres, probabilidadCruce);
                /*System.out.println("Generacion resultante cruce por arcos: ");
                for(int i = 0; i < hijos.length; i++){
                    System.out.print("Solucion "+i+": ");
                    for(int j = 0; j < hijos[0].length; j++){
                        System.out.print(hijos[i][j]+" ");
                    }
                    System.out.print("Fitness: "+fitness.fitnessSolucion(hijos[i], matrizDistancias));
                    System.out.print("\n");
                }*/
            }
            // Cruce por orden
            else if (operadorCruce == 1){
                hijos = cruzarPorOrden.cruzarPorOrdene(padres, probabilidadCruce);
                /*System.out.println("Generacion resultante cruce por orden: ");
                for(int i = 0; i < hijos.length; i++){
                    for(int j = 0; j < hijos[0].length; j++){
                        System.out.print(hijos[i][j]+" ");
                    }
                    System.out.print("Fitness: "+fitness.fitnessSolucion(hijos[i], matrizDistancias));
                    System.out.print("\n");
                }*/
            }


            //Realizar mutacion segun parametro de metodo y probabilidad
            int operadorMutacion = configuracion[5];
            // Probabilidad entre 0 y 100
            int probabilidadMutacion = configuracion[6];
            // Mutacion por inversion
            if(operadorMutacion == 0){
                hijos = mutacionPorInversion.mutacionPorInversione(hijos, probabilidadMutacion);
                /*System.out.println("Generacion resultante mutacion por inversion: ");
                for(int i = 0; i < hijos.length; i++){
                    System.out.print("Solucion "+i+": ");
                    for(int j = 0; j < hijos[0].length; j++){
                        System.out.print(hijos[i][j]+" ");
                    }
                    System.out.print("Fitness: "+fitness.fitnessSolucion(hijos[i], matrizDistancias));
                    System.out.print("\n");
                }*/
            }
            // Mutacion por intercambio
            else if (operadorMutacion == 1){
                hijos = mutacionPorIntercambio.mutacionPorIntercambioe(hijos, probabilidadMutacion);
                /*System.out.println("Generacion resultante mutacion por intercambio: ");
                for(int i = 0; i < hijos.length; i++){
                    for(int j = 0; j < hijos[0].length; j++){
                        System.out.print(hijos[i][j]+" ");
                    }
                    System.out.print("Fitness: "+fitness.fitnessSolucion(hijos[i], matrizDistancias));
                    System.out.print("\n");
                }*/
            }


            //Evaluar a los nuevos candidatos (nuevo llamado a funcion de fitness)
            //if fitness > solucion: solucion = fitness
            int[] fitnessGeneracion = fitness.fitnessPoblacione(hijos, matrizDistancias);
            int mejorFitnessGeneracion = Integer.MAX_VALUE;
            int[] mejorSolucionGeneracion = new int[hijos[0].length];
            //System.out.println();
            for(int i = 0; i < fitnessGeneracion.length; i++){
                if(fitnessGeneracion[i] < mejorFitnessGeneracion){
                    mejorFitnessGeneracion = fitnessGeneracion[i];
                    mejorSolucionGeneracion = hijos[i];
                    //System.out.print(fitnessGeneracion[i]+" ");
                }
            }
            fitnessPorGeneracion[iteracion] = mejorFitnessGeneracion;
            if (mejorFitnessGeneracion < solucionFitness){
                solucionFitness = mejorFitnessGeneracion;
                solucion = mejorSolucionGeneracion;
            }

            //Seleccion de sovrevivientes segun parametro
            // 0 = Steady-state ; 1 = remplazo basado en fitness
            int operadorSeleccion = configuracion[7];
            int[] fitnessPadres = fitness.fitnessPoblacione(padres, matrizDistancias);
            // Seleccion por Steady-state
            if(operadorSeleccion == 0){
                poblacionActual = seleccionSteadyState.seleccionSteadyStatee(hijos, padres, fitnessGeneracion, fitnessPadres);
                /*System.out.println("Generacion resultante de seleccion por remplazo basado en fitness: ");
                for(int i = 0; i < poblacionActual.length; i++){
                    System.out.print("Solucion "+i+": ");
                    for(int j = 0; j < poblacionActual[0].length; j++){
                        System.out.print(poblacionActual[i][j]+" ");
                    }
                    System.out.print("Fitness: "+fitness.fitnessSolucion(poblacionActual[i], matrizDistancias));
                    System.out.print("\n");
                }*/
            }
            // Seleccion por remplazo basado en fitness
            else if (operadorSeleccion == 1){
                poblacionActual = seleccionBasadaEnFitness.seleccionBasadaEnFitnesse(hijos, padres, fitnessGeneracion, fitnessPadres, metodoSeleccionPadres);
                /*System.out.println("Generacion resultante de seleccion por remplazo basado en fitness: ");
                for(int i = 0; i < poblacionActual.length; i++){
                    for(int j = 0; j < poblacionActual[0].length; j++){
                        System.out.print(poblacionActual[i][j]+" ");
                    }
                    System.out.print("Fitness: "+fitness.fitnessSolucion(poblacionActual[i], matrizDistancias));
                    System.out.print("\n");
                }*/
            }

            iteracion++;
        }

        System.err.print("Solucion: [ ");
        for(int i = 0; i < solucion.length; i++){
            System.out.print(solucion[i]+" ");
        }
        System.out.println("]");

        return solucionFitness;
    }

    //Funcion que llena la matriz de distancias a partir del archivo de lectura
    private static int[][] obtenerDistancias(Scanner lector) {
        int dimension = 0;
    
        // Buscar la linea que contiene la dimension
        while (lector.hasNextLine()) {
            String linea = lector.nextLine();
            if (linea.startsWith("DIMENSION:")) {
                dimension = Integer.parseInt(linea.split(":")[1].trim());
                break;
            }
        }
    
        // Inicializar la matriz con la dimension obtenida
        int[][] matriz = new int[dimension][dimension];
    
        // Buscar la seccion de pesos de arista
        while (lector.hasNextLine()) {
            String linea = lector.nextLine();
            if (linea.equals("EDGE_WEIGHT_SECTION")) {
                break;
            }
        }
    
        // Leer la seccion de pesos de arista y llenar la matriz
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                matriz[i][j] = lector.nextInt();
            }
        }
    
        return matriz;
    }

}
