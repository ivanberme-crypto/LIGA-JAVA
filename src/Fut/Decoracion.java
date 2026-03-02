package Fut;

public class Decoracion {
    // Colores Estándar
    public static final String RESET = "\u001B[0m";
    public static final String ROJO = "\u001B[31m";
    public static final String VERDE = "\u001B[32m";
    public static final String AMARILLO = "\u001B[33m";
    public static final String AZUL = "\u001B[34m";
    public static final String PURPURA = "\u001B[35m";
    public static final String CIAN = "\u001B[36m";
    public static final String BLANCO = "\u001B[37m";

    // Colores Brillantes (Bold/Bright) - Quedan mejor en la mayoría de consolas
    public static final String B_ROJO = "\u001B[1;31m";
    public static final String B_VERDE = "\u001B[1;32m";
    public static final String B_AMARILLO = "\u001B[1;33m";
    public static final String B_AZUL = "\u001B[1;34m";
    public static final String B_PURPURA = "\u001B[1;35m";
    public static final String B_CIAN = "\u001B[1;36m";
    public static final String B_BLANCO = "\u001B[1;37m";

    // Fondos (Para resaltar marcadores o avisos importantes)
    public static final String BG_ROJO = "\u001B[41m";
    public static final String BG_VERDE = "\u001B[42m";

    public static String centrar(String texto, int ancho) {
        int espacios = (ancho - texto.length()) / 2;
        StringBuilder sb = new StringBuilder();

        // Espacios a la izquierda
        for (int i = 0; i < espacios; i++) sb.append(" ");
        sb.append(texto);

        // Rellenar hasta alcanzar el ancho exacto a la derecha
        while (sb.length() < ancho) {
            sb.append(" ");
        }
        return sb.toString();
    }
}