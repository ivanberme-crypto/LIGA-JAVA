package Fut.ligas;

import Fut.equipos.Equipo;
import Fut.personas.Jugador;
import Fut.Decoracion;

import java.util.List;
import java.util.Scanner;

public class LigaTerminada {

    private String nombreLiga;
    private Equipo campeon;
    private List<Equipo> clasificacionFinal;
    private List<Jugador> goleadores;
    private final int ANCHO_GENERAL = 44;

    public LigaTerminada(String nombreLiga, Equipo campeon, List<Equipo> clasificacion, List<Jugador> goleadores) {
        this.nombreLiga = nombreLiga;
        this.campeon = campeon;
        this.clasificacionFinal = clasificacion;
        this.goleadores = goleadores;
    }

    public void mostrarMenuFinal(Scanner sc) {
        int opcion = -1;
        String colorLiga = Decoracion.B_CIAN;

        do {
            try {
                System.out.println("\n" + colorLiga + "╔" + "═".repeat(ANCHO_GENERAL) + "╗");
                System.out.println("║" + Decoracion.B_AMARILLO + Decoracion.centrar(" RESUMEN DE TEMPORADA: " + nombreLiga.toUpperCase() + " ", ANCHO_GENERAL) + colorLiga + "║");
                System.out.println("╠" + "═".repeat(ANCHO_GENERAL) + "╣");
                System.out.println("║" + Decoracion.B_BLANCO + Decoracion.centrar("[01] Cuadro de Honor", ANCHO_GENERAL) + colorLiga + "║");
                System.out.println("║" + Decoracion.B_BLANCO + Decoracion.centrar("[02] Clasificación Final", ANCHO_GENERAL) + colorLiga + "║");
                System.out.println("║" + Decoracion.B_BLANCO + Decoracion.centrar("[03] Tabla de Goleadores", ANCHO_GENERAL) + colorLiga + "║");
                System.out.println("║" + Decoracion.B_ROJO + Decoracion.centrar("[00] Salir y cerrar programa", ANCHO_GENERAL) + colorLiga + "║");
                System.out.println("╚" + "═".repeat(ANCHO_GENERAL) + "╝" + Decoracion.RESET);
                System.out.print(Decoracion.B_AMARILLO + " -> Selección: " + Decoracion.RESET);

                if (!sc.hasNextInt()) {
                    System.out.println("\n" + Decoracion.ROJO + Decoracion.centrar("Error: Por favor, introduce un número.", ANCHO_GENERAL) + Decoracion.RESET);
                    sc.next();
                    continue;
                }

                opcion = sc.nextInt();
                sc.nextLine();

                switch (opcion) {
                    case 1 -> mostrarCuadroHonor();
                    case 2 -> mostrarClasificacion();
                    case 3 -> mostrarPichichi();
                    case 0 -> {
                        System.out.println("\n" + Decoracion.B_PURPURA + Decoracion.centrar("¡Gracias por jugar a FutDraft!", ANCHO_GENERAL) + Decoracion.RESET);
                        System.out.println(Decoracion.B_CIAN + Decoracion.centrar(" Cerrando el despacho... ", ANCHO_GENERAL) + Decoracion.RESET);
                        System.exit(0);
                    }
                    default -> System.out.println("\n" + Decoracion.ROJO + Decoracion.centrar("Opción no válida. Inténtalo de nuevo.", ANCHO_GENERAL) + Decoracion.RESET);
                }
                if (opcion != 0) {
                    System.out.println("\n" + Decoracion.centrar(Decoracion.B_AMARILLO + "[ Presiona ENTER para continuar ]" + Decoracion.RESET, ANCHO_GENERAL));
                    sc.nextLine();
                }

            } catch (Exception e) {
                System.out.println("\n" + Decoracion.ROJO + Decoracion.centrar("Ha ocurrido un error inesperado.", ANCHO_GENERAL) + Decoracion.RESET);
                if (sc.hasNext()) sc.nextLine();
            }
        } while (opcion != 0);
    }

    private void mostrarCuadroHonor() {
        int ANCHO_CUADRO = 41;
        System.out.println("\n" + Decoracion.B_AMARILLO + "┌" + "─".repeat(ANCHO_CUADRO) + "┐");
        System.out.println("│" + Decoracion.B_BLANCO + Decoracion.centrar("CAMPEÓN OFICIAL", ANCHO_CUADRO) + Decoracion.B_AMARILLO + "│");
        System.out.println("├" + "─".repeat(ANCHO_CUADRO) + "┤");
        System.out.println("│" + Decoracion.B_VERDE + Decoracion.centrar(campeon.getNombre().toUpperCase(), ANCHO_CUADRO) + Decoracion.B_AMARILLO + "│");
        String info = "Puntos: " + campeon.getPuntos() + " | GF: " + campeon.getGolesFavor();
        System.out.println("│" + Decoracion.RESET + Decoracion.centrar(info, ANCHO_CUADRO) + Decoracion.B_AMARILLO + "│");
        System.out.println("└" + "─".repeat(ANCHO_CUADRO) + "┘" + Decoracion.RESET);
    }

    private void mostrarClasificacion() {
        int ancho = 52;
        String bordeColor = Decoracion.B_BLANCO;

        System.out.println("\n" + bordeColor + "┌" + "─".repeat(ancho - 2) + "┐");
        System.out.printf(bordeColor + "│ " + Decoracion.B_PURPURA + "%-5s %-30s %-9s" + bordeColor + "   │%n",
                "POS", "EQUIPO", "PUNTOS");

        System.out.println(bordeColor + "├" + "─".repeat(ancho - 2) + "┤");

        for (int i = 0; i < clasificacionFinal.size(); i++) {
            Equipo e = clasificacionFinal.get(i);
            String colorNombre = (e == campeon) ? Decoracion.B_AMARILLO : Decoracion.RESET;
            System.out.print(bordeColor + "│ " + Decoracion.RESET);
            System.out.print(Decoracion.B_CIAN + String.format("%02d   ", (i + 1)) + Decoracion.RESET);
            String nombre = e.getNombre();
            if (nombre.length() > 30) nombre = nombre.substring(0, 27) + "...";
            System.out.print(colorNombre + String.format("%-31s", nombre) + Decoracion.RESET);
            System.out.print(Decoracion.B_VERDE + String.format("%3d pts   ", e.getPuntos()) + Decoracion.RESET);
            System.out.println(bordeColor + "   │");
        }
        System.out.println(bordeColor + "└" + "─".repeat(ancho - 2) + "┘" + Decoracion.RESET);
    }

    private void mostrarPichichi() {
        int ANCHO_PICHICHI = 53;

        System.out.println("\n" + Decoracion.NARANJA + "┌" + "─".repeat(ANCHO_PICHICHI) + "┐");
        System.out.println("│" + Decoracion.B_BLANCO + Decoracion.centrar("TOP 5 GOLEADORES - PICHICHI", ANCHO_PICHICHI) + Decoracion.NARANJA + "│");
        System.out.println("├" + "─".repeat(ANCHO_PICHICHI) + "┤");

        int ranking = 1;
        List<Jugador> top5 = goleadores.stream().limit(5).toList();

        for (Jugador j : top5) {
            String eqNombre = (j.getEquipo() != null) ? j.getEquipo() : "S.E.";
            if (eqNombre.length() > 18) eqNombre = eqNombre.substring(0, 15) + "...";
            System.out.print(Decoracion.NARANJA + "│ " + Decoracion.RESET);
            System.out.printf("%s%-4s%s %-18s %s%-18s%s %s%2d Goles%s",
                    Decoracion.B_AMARILLO, "[" + ranking + "]", Decoracion.RESET,
                    j.getNombre(),
                    Decoracion.B_CIAN, eqNombre, Decoracion.RESET,
                    Decoracion.B_VERDE, j.getGoles(), Decoracion.RESET
            );
            System.out.println(Decoracion.NARANJA + " │");
            ranking++;
        }
        System.out.println(Decoracion.NARANJA + "└" + "─".repeat(ANCHO_PICHICHI) + "┘" + Decoracion.RESET);
    }
}