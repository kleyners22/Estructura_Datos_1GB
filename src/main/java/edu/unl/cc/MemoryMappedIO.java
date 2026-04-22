package edu.unl.cc;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MemoryMappedIO {
    static final long SEGMENTO = 512 * 1024 * 1024L; // 512 MB por segmento

    public static long leerArchivo(String ruta) throws IOException {
        long bytesLeidos = 0;

        try (RandomAccessFile raf = new RandomAccessFile(ruta, "r");
             FileChannel channel = raf.getChannel()) {

            long tamanoTotal = channel.size();
            long posicion = 0;

            while (posicion < tamanoTotal) {
                long tamanoSegmento = Math.min(SEGMENTO, tamanoTotal - posicion);

                // MAPEO DIRECTO: kernel <-> espacio usuario sin copia intermedia
                MappedByteBuffer mbb = channel.map(
                        FileChannel.MapMode.READ_ONLY, posicion, tamanoSegmento);
                mbb.load(); // carga páginas en RAM

                bytesLeidos += procesarMappedBuffer(mbb);
                posicion += tamanoSegmento;
            }
        }
        return bytesLeidos;
    }

    private static long procesarMappedBuffer(MappedByteBuffer mbb) {
        long suma = 0;
        while (mbb.hasRemaining()) {
            suma += mbb.get();
        }
        return mbb.capacity();
    }
}

