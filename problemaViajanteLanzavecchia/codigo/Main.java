package codigo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public static void main(String[] args){

        // Seleccionar archivo de lectura o traerlo de los args
        String rutaLectura;
        if (args.length == 0){
            rutaLectura = "C:\\Users\\Usuario\\Desktop\\IS5\\ICE\\problemaViajanteLanzavecchia\\archivos\\br17.atsp";
        } else {
            rutaLectura = args[0];
        }
        File archivoLectura = new File(rutaLectura);

        // Inicializar parametros con valores elegidos
        // 0 = aleatoria
        final int metodoPoblacionInicial = 0; //[0]
        // Tamano de la poblacion
        final int tamanoPoblacionInicial = 100; //[1]
        // 0 = torneo (funciona mal); 1 = rueda de ruleta
        final int metodoSeleccionPadres = 0; //[2]
        // 0 = basado en arcos ; 1 = cruce de orden (Produce convergencia prematura, pero buen resultado, mejor con cruce y mutacion variables)
        final int operadorCruce = 0; //[3]
        // Probabilidad entre 0 y 100
        final int probabilidadCruce = 80; //[4]
        // 0 = por inversion (flojo en principio) ; 1 = por intercambio
        final int operadorMutacion = 1; //[5]
        // Probabilidad entre 0 y 100
        final int probabilidadMutacion = 5; //[6]
        // 0 = Steady-state ; 1 = remplazo basado en fitness (observar bien el tamano del torneo)
        final int metodoSeleccionSobrevivientes = 1; //[7]
        // La condicion de corte siendo el numero de generaciones a producir
        final int numeroGeneraciones = 100; //[8]
        
        final int[] configuracion = {metodoPoblacionInicial, tamanoPoblacionInicial, metodoSeleccionPadres, operadorCruce,
                                  probabilidadCruce, operadorMutacion, probabilidadMutacion, metodoSeleccionSobrevivientes,
                                  numeroGeneraciones};

        // Ejecutar algoritmo
        String registro = algoritmo.EjecutarAlgoritmo(archivoLectura, configuracion);

        // Seleccionar archivo de escritura o traerlo de los args
        String rutaEscritura;
        if (args.length < 2){
            rutaEscritura = "C:\\Users\\Usuario\\Desktop\\IS5\\ICE\\problemaViajanteLanzavecchia\\archivos\\resultados.txt";
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