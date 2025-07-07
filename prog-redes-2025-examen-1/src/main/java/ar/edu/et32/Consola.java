package ar.edu.et32;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class Consola {

    public static final String RESET = "\u001B[0m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String CONSORTI = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String RED = "\u001B[31m";
    public static final String WHITE_BG = "\u001B[47;30m";
    public PrintStream ps = new PrintStream(System.out);

    public String leerTexto(String mensaje) {
        ps.printf("%s%s%s ", CYAN, mensaje, RESET);
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            return reader.readLine();
        } catch (IOException e) {
            return "";
        }
    }


    public void mostrarMenu() {
        ps.printf("%s  MENÚ TUTI-FRUTI  %s%n", CONSORTI, RESET);
        ps.printf("%s1. Mostrar Datos Ordenados%s%n", YELLOW, RESET);
        ps.printf("%s2. Agregar Datos%s%n", YELLOW, RESET);
        ps.printf("%s3. Eliminar Datos%s%n", YELLOW, RESET);
        ps.printf("%s4. Salir%s%n", YELLOW, RESET);
    }
}
