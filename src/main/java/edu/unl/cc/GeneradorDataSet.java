package edu.unl.cc;
import java.io.*;
import java.util.Random;

public class GeneradorDataSet {
    static final int TAMANO_CONTENIDO = 980; // bytes de contenido por registro
    static final long REGISTROS_OBJETIVO = 1_100_000L; // ~1 GB aprox

    public static void main(String[] args) throws IOException {
        String rutaArchivo = "archivo_datos.bin";
        Random random = new Random(42);

        System.out.println("Generando archivo binario...");
        long inicio = System.nanoTime();

        try (DataOutputStream dos = new DataOutputStream(
                new BufferedOutputStream(
                        new FileOutputStream(rutaArchivo), 8 * 1024 * 1024))) {

            for (long i = 0; i < REGISTROS_OBJETIVO; i++) {
                dos.writeLong(i);

                dos.writeLong(System.currentTimeMillis() + i);

                byte[] contenido = new byte[TAMANO_CONTENIDO];
                random.nextBytes(contenido);
                dos.write(contenido);

                if (i % 100_000 == 0) {
                    System.out.printf("Progreso: %.1f%%%n",
                            (i * 100.0) / REGISTROS_OBJETIVO);
                }
            }
        }

        long fin = System.nanoTime();
        File archivo = new File(rutaArchivo);
        System.out.printf(" Archivo creado: %.2f GB en %.2f segundos%n",
                archivo.length() / (1024.0 * 1024 * 1024),
                (fin - inicio) / 1_000_000_000.0);
    }
}

