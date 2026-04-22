package edu.unl.cc;

import java.io.File;

public class BenchmarkRunner {
    static final String ARCHIVO = "archivo_datos.bin";
    static final int ITERACIONES = 5;
    static final int WARM_UP = 2; // iteraciones de calentamiento JIT

    public static void main(String[] args) throws Exception {
        System.out.println("=== BENCHMARK DE ESTRATEGIAS I/O EN JAVA ===\n");

        // ── WARM-UP (evita sesgo del compilador JIT) ──
        System.out.println("🔥 Fase Warm-Up (JIT Compilation)...");
        for (int i = 0; i < WARM_UP; i++) {
            StreamBasedIO.leerArchivo(ARCHIVO);
            NIOChannelIO.leerArchivo(ARCHIVO);
            MemoryMappedIO.leerArchivo(ARCHIVO);
        }
        System.out.println("✅ Warm-up completado\n");

        // ── MEDICIÓN OFICIAL ──
        double[] tiemposStream = new double[ITERACIONES];
        double[] tiemposNIO    = new double[ITERACIONES];
        double[] tiemposMMAP   = new double[ITERACIONES];

        for (int i = 0; i < ITERACIONES; i++) {
            System.out.printf("Iteración %d/%d...%n", i + 1, ITERACIONES);

            tiemposStream[i] = medirTiempo(() -> StreamBasedIO.leerArchivo(ARCHIVO));
            tiemposNIO[i]    = medirTiempo(() -> NIOChannelIO.leerArchivo(ARCHIVO));
            tiemposMMAP[i]   = medirTiempo(() -> MemoryMappedIO.leerArchivo(ARCHIVO));
        }

        // ── REPORTE ──
        long bytesArchivo = new File(ARCHIVO).length();
        ReporteMetricas.imprimirReporte("Stream-Based IO",  tiemposStream, bytesArchivo);
        ReporteMetricas.imprimirReporte("NIO Heap Buffer",  tiemposNIO,    bytesArchivo);
        ReporteMetricas.imprimirReporte("Memory-Mapped IO", tiemposMMAP,   bytesArchivo);

        ReporteMetricas.imprimirComparacion(tiemposStream, tiemposNIO, tiemposMMAP);
    }

    static double medirTiempo(IOTask tarea) throws Exception {
        long inicio = System.nanoTime();
        tarea.ejecutar();
        long fin = System.nanoTime();
        return (fin - inicio) / 1_000_000.0; // en milisegundos
    }

    @FunctionalInterface
    interface IOTask {
        void ejecutar() throws Exception;
    }
}
