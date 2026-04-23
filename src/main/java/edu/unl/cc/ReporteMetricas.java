package edu.unl.cc;

public class ReporteMetricas {
    public static void imprimirReporte(String nombre, double[] tiempos, long bytes) {
        double media = calcularMedia(tiempos);
        double desviacion = calcularDesviacion(tiempos, media);
        double throughput = (bytes / (1024.0 * 1024)) / (media / 1000.0); // MB/s
        double eficiencia = bytes / (media / 1000.0); // Bytes por segundo

        System.out.println("┌─────────────────────────────────────────────────┐");
        System.out.printf( "│ Método:     %-36s│%n", nombre);
        System.out.println("├─────────────────────────────────────────────────┤");
        System.out.printf( "│ Media:          %10.2f ms                    │%n", media);
        System.out.printf( "│ Desv. Estándar: %10.2f ms                    │%n", desviacion);
        System.out.printf( "│ Throughput:     %10.2f MB/s                  │%n", throughput);
        System.out.printf( "│ Eficiencia:     %10.2f MB/seg                │%n", eficiencia / (1024 * 1024));
        System.out.println("└─────────────────────────────────────────────────┘");
        System.out.println();
    }

    public static void imprimirComparacion(double[] stream, double[] nio, double[] mmap) {
        double mediaStream = calcularMedia(stream);
        double mediaNIO    = calcularMedia(nio);
        double mediaMMAP   = calcularMedia(mmap);
        System.out.println("╔═════════════════════════════════════════════════╗");
        System.out.println("║         COMPARACIÓN FINAL DE MÉTODOS           ║");
        System.out.println("╠═════════════════════════════════════════════════╣");
        System.out.printf( "║ Stream-Based IO  → %10.2f ms (base)         ║%n", mediaStream);
        System.out.printf( "║ NIO Heap Buffer  → %10.2f ms (%.1fx más rápido) ║%n",
                mediaNIO, mediaStream / mediaNIO);
        System.out.printf( "║ Memory-Mapped IO → %10.2f ms (%.1fx más rápido) ║%n",
                mediaMMAP, mediaStream / mediaMMAP);
        System.out.println("╠═════════════════════════════════════════════════╣");

        String ganador = mediaMMAP < mediaNIO ? "Memory-Mapped IO" : "NIO Heap Buffer";
        System.out.printf( "║ 🏆 Método más eficiente: %-23s║%n", ganador);
        System.out.println("╚═════════════════════════════════════════════════╝");
    }
    static double calcularMedia(double[] datos) {
        double suma = 0;
        for (double v : datos) suma += v;
        return suma / datos.length;
    }
    static double calcularDesviacion(double[] datos, double media) {
        double suma = 0;
        for (double v : datos) suma += Math.pow(v - media, 2);
        return Math.sqrt(suma / datos.length);
    }
}

