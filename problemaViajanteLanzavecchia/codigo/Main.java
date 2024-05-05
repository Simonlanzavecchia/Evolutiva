package codigo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public static void main(String[] args){

        // Seleccionar archivo de lectura o traerlo de los args
        String rutaLectura;
        if (args.length == 0){
            rutaLectura = "C:\\Users\\Usuario\\Desktop\\IS5\\ICE\\Evolutiva\\problemaViajanteLanzavecchia\\archivos\\p43.atsp";
        } else {
            rutaLectura = args[0];
        }
        File archivoLectura = new File(rutaLectura);

        // Inicializar parametros con valores elegidos
        // 0 = aleatoria
        final int metodoPoblacionInicial = 0; //[0]
        // Tamano de la poblacion
        final int tamanoPoblacionInicial = 1000; //[1]
        // 0 = torneo ; 1 = rueda de ruleta
        final int metodoSeleccionPadres = 1; //[2]
        // 0 = basado en arcos; 1 = cruce de orden
        final int operadorCruce = 1; //[3]
        // Probabilidad entre 0 y 100
        final int probabilidadCruce = 85; //[4]
        // 0 = por inversion; 1 = por intercambio
        final int operadorMutacion = 0; //[5]
        // Probabilidad entre 0 y 100
        final int probabilidadMutacion = 5; //[6]
        // 0 = Steady-state ; 1 = reemplazo basado en fitness (observar bien el tamano del torneo)
        final int metodoSeleccionSobrevivientes = 0; //[7]
        // La condicion de corte siendo el numero de generaciones a producir
        final int numeroGeneraciones = 1000; //[8]
        
        final int[] configuracion = {metodoPoblacionInicial, tamanoPoblacionInicial, metodoSeleccionPadres, operadorCruce,
                                  probabilidadCruce, operadorMutacion, probabilidadMutacion, metodoSeleccionSobrevivientes,
                                  numeroGeneraciones};

        // Ejecutar algoritmo
        String registro = algoritmo.EjecutarAlgoritmo(archivoLectura, configuracion);

        // Seleccionar archivo de escritura o traerlo de los args
        String rutaEscritura;
        if (args.length < 2){
            rutaEscritura = "C:\\Users\\Usuario\\Desktop\\IS5\\ICE\\Evolutiva\\problemaViajanteLanzavecchia\\archivos\\resultados.txt";
        } else {
            rutaEscritura = args[1];
        }
        File archivoEscritura = new File(rutaEscritura);

        // Guardar registro de la ejecucion
        FileWriter fw = null;
        try {
            fw = new FileWriter(archivoEscritura, true);
            fw.write(registro);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}