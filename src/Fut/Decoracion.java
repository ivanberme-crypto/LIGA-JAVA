package Fut;

import java.util.Scanner;

public final class Decoracion {

    Scanner sc = new Scanner(System.in);
    public static final String RESET = "\u001B[0m";
    public static final String ROJO = "\u001B[31m";
    public static final String VERDE = "\u001B[32m";
    public static final String AMARILLO = "\u001B[33m";
    public static final String NARANJA = "\u001B[38;5;202m";
    public static final String AZUL = "\u001B[34m";
    public static final String PURPURA = "\u001B[35m";
    public static final String CIAN = "\u001B[36m";
    public static final String BLANCO = "\u001B[37m";
    public static final String B_ROJO = "\u001B[1;31m";
    public static final String B_VERDE = "\u001B[1;32m";
    public static final String B_AMARILLO = "\u001B[1;33m";
    public static final String B_AZUL = "\u001B[1;34m";
    public static final String B_PURPURA = "\u001B[1;35m";
    public static final String B_CIAN = "\u001B[1;36m";
    public static final String B_BLANCO = "\u001B[1;37m";
    public static final String NARANJA_SUAVE = "\u001B[38;5;208m";
    public static final String ROJO_ANARANJADO = "\u001B[38;2;255;69;0m";

    public static String centrar(String texto, int ancho) {
        int espacios = (ancho - texto.length()) / 2;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < espacios; i++) sb.append(" ");
        sb.append(texto);

        while (sb.length() < ancho) {
            sb.append(" ");
        }
        return sb.toString();
    }

    public static void pausa(int milisegundos) {
        try {
            Thread.sleep(milisegundos);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static String centrarEnColumna(String texto, int ancho) {
        if (texto == null) texto = "";
        if (texto.length() >= ancho) return texto;

        int espaciosTotales = ancho - texto.length();
        int espaciosIzquierda = espaciosTotales / 2;
        int espaciosDerecha = espaciosTotales - espaciosIzquierda;

        return " ".repeat(espaciosIzquierda) + texto + " ".repeat(espaciosDerecha);
    }

    public static int leerEntero(Scanner sc, String mensaje, int min, int max) {
        int valor;
        while (true) {
            System.out.print(mensaje);
            try {
                if (sc.hasNextInt()) {
                    valor = sc.nextInt();
                    sc.nextLine();
                    if (valor >= min && valor <= max) {
                        return valor;
                    } else {
                        System.out.println(ROJO + "Error: El número debe estar entre " + min + " y " + max + "." + RESET);
                    }
                } else {
                    System.out.println(ROJO + "Error: Debes introducir un número entero." + RESET);
                    sc.next();
                }
            } catch (Exception e) {
                System.out.println(ROJO + "Error inesperado al leer datos." + RESET);
                sc.nextLine();
            }
        }
    }
}