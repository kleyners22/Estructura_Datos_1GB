
package edu.unl.cc.main;

public class Optimizador_de_Fluidos {
    /**
     * Encuentra el área máxima de agua que se puede contener.
     * @param alturas Arreglo de enteros que representan la altura de
    los muros.
     * @return El área máxima calculada.
     */
    public int calcularCapacidadMaxima(int[] alturas) {
        int maxArea = 0;
        int izquierda = 0;
        int derecha = alturas.length - 1;

        if (alturas == null|| alturas.length < 2) {
            return 0;
        }

        while (izquierda < derecha) {
            // 1. Calcular el ancho entre los punteros
            int ancho = derecha - izquierda;

            // 2. Calcular el área actual con la altura mínima
            int areaActual = Math.min(alturas[izquierda],
                    alturas[derecha]) * ancho;

            // 3. Actualizar el máximo global
            maxArea = Math.max(maxArea, areaActual);
            // 4. Lógica de movimiento de punteros (Optimización)
            if (alturas[izquierda] < alturas[derecha]) {
                izquierda++;
            } else {
                derecha--;
            }
        }
        return maxArea;
    }
}


