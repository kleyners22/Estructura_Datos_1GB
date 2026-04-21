package edu.unl.cc.main;

public class Solution {


    public static void main(String[] args) {

        Optimizador_de_Fluidos optimizador = new Optimizador_de_Fluidos();

        int []altura = {8,1,3,5,7,8,6};

        long inicio = System.currentTimeMillis();

        int resultado = optimizador.calcularCapacidadMaxima(altura);

        long fin = System.currentTimeMillis();
        System.out.println("Área máxima: " + resultado);
        System.out.println("Tiempo de ejecución: " + (fin - inicio) + " ms");
    }
}
