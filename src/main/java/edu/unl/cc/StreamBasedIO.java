package edu.unl.cc;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class StreamBasedIO {

    public static long leerArchivo(String ruta) throws IOException {
        long bytesLeidos = 0;
        byte[] buffer = new byte[8192]; // buffer clásico de 8 KB

        try (FileInputStream fis = new FileInputStream(ruta);
             BufferedInputStream bis = new BufferedInputStream(fis, 8192);
             DataInputStream dis = new DataInputStream(bis)) {

            int leido;
            while ((leido = dis.read(buffer)) != -1) {
                bytesLeidos += leido;
                // Simulamos procesamiento mínimo
                procesarBytes(buffer, leido);
            }
        }
        return bytesLeidos;
    }

    private static void procesarBytes(byte[] buffer, int longitud) {
        // Suma de verificación simple (evita que JIT elimine el código)
        long suma = 0;
        for (int i = 0; i < longitud; i++) {
            suma += buffer[i];
        }
    }
}
