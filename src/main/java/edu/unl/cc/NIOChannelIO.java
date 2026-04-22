package edu.unl.cc;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOChannelIO {

    public static long leerArchivo(String ruta) throws IOException {
        long bytesLeidos = 0;
        // Buffer en el HEAP de la JVM (no directo)
        ByteBuffer buffer = ByteBuffer.allocate(64 * 1024); // 64 KB

        try (FileInputStream fis = new FileInputStream(ruta);
             FileChannel channel = fis.getChannel()) {

            while (channel.read(buffer) != -1) {
                buffer.flip(); // prepara para lectura
                bytesLeidos += buffer.limit();
                procesarBuffer(buffer);
                buffer.clear(); // prepara para siguiente escritura
            }
        }
        return bytesLeidos;
    }

    private static void procesarBuffer(ByteBuffer buffer) {
        long suma = 0;
        while (buffer.hasRemaining()) {
            suma += buffer.get();
        }
    }
}