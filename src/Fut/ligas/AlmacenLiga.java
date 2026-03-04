package Fut.ligas;

import Fut.Decoracion;
import Fut.equipos.AlmacenEquipo;
import Fut.equipos.Equipo;
import Fut.equipos.EquipoPropio;
import Fut.ligas.jornadas.Jornada;
import Fut.personas.AlmacenJugador;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AlmacenLiga {
    private AlmacenEquipo alEquipo;
    private AlmacenJugador alJugador;
    private EquipoPropio equipoPropio;
    private Liga ligaEnCurso;

    public AlmacenLiga(AlmacenEquipo alEquipo, AlmacenJugador alJugador, EquipoPropio equipoPropio) {
        this.alEquipo = alEquipo;
        this.alJugador = alJugador;
        this.equipoPropio = equipoPropio;
    }

    public void mostrarSubMenuLiga(Scanner sc) {
        List<Liga> ligasDisponibles = alEquipo.getLigas();
        int ANCHO_INTERIOR = 43;

        if (ligasDisponibles.isEmpty()) {
            System.out.println(Decoracion.ROJO + "\n No hay ligas cargadas en el sistema." + Decoracion.RESET);
            return;
        }

        boolean volver = false;
        while (!volver) {
            System.out.println("\n\n");
            System.out.println(Decoracion.CIAN + "╔═══════════════════════════════════════════╗" + Decoracion.RESET);
            System.out.println(Decoracion.CIAN + "║" + Decoracion.AMARILLO + Decoracion.centrar("LIGAS DISPONIBLES", ANCHO_INTERIOR) + Decoracion.CIAN + "║" + Decoracion.RESET);
            System.out.println(Decoracion.CIAN + "╠═══════════════════════════════════════════╣" + Decoracion.RESET);
            System.out.println(Decoracion.CIAN + "║" + " ".repeat(ANCHO_INTERIOR) + "║" + Decoracion.RESET);
            for (int i = 0; i < ligasDisponibles.size(); i++) {
                String nombreLiga = ligasDisponibles.get(i).getNombre();
                String colorLiga = obtenerColorPorLiga(nombreLiga);

                System.out.printf(Decoracion.B_CIAN + "║ " + Decoracion.B_AMARILLO + " [0%d] " + colorLiga + "%-35s" + Decoracion.B_CIAN + " ║%n",
                        (i + 1), nombreLiga.toUpperCase());
            }
            System.out.println(Decoracion.CIAN + "║" + " ".repeat(ANCHO_INTERIOR) + "║" + Decoracion.RESET);
            System.out.println(Decoracion.CIAN + "╠═══════════════════════════════════════════╣" + Decoracion.RESET);
            System.out.printf(Decoracion.CIAN + "║  " + Decoracion.ROJO + "[00]" + Decoracion.RESET + " %-35s " + Decoracion.CIAN + "║%n",
                    "VOLVER AL MENÚ PRINCIPAL");
            System.out.println(Decoracion.CIAN + "╚═══════════════════════════════════════════╝" + Decoracion.RESET);
            System.out.print(Decoracion.AMARILLO + " -> SELECCIONA UNA LIGA: " + Decoracion.RESET);

            try {
                int opcion = sc.nextInt();
                sc.nextLine();

                if (opcion == 0) {
                    volver = true;
                } else if (opcion > 0 && opcion <= ligasDisponibles.size()) {
                    Liga seleccionada = ligasDisponibles.get(opcion - 1);
                    alEquipo.mostrarSubmenuEquipos(alEquipo, sc, seleccionada.getNombre(), false);
                } else {
                    System.out.println(Decoracion.ROJO + "Opción no válida." + Decoracion.RESET);
                }
            } catch (Exception e) {
                System.out.println(Decoracion.ROJO + "Error: Entrada no válida." + Decoracion.RESET);
                sc.nextLine();
            }
        }
    }

    public void gestionarSeleccionLiga(Scanner sc) {
        List<Liga> ligasDisponibles = alEquipo.getLigas();
        int ANCHO = 40;

        System.out.println("\n" + Decoracion.B_CIAN + "╔" + "═".repeat(ANCHO + 2) + "╗");
        System.out.println("║ " + Decoracion.B_BLANCO + Decoracion.centrar(" SELECCIONA LA LIGA ", ANCHO) + Decoracion.B_CIAN + " ║");
        System.out.println("╠" + "═".repeat(ANCHO + 2) + "╣");

        for (int i = 0; i < ligasDisponibles.size(); i++) {
            String nombreLiga = ligasDisponibles.get(i).getNombre();
            String colorLiga = obtenerColorPorLiga(nombreLiga);

            System.out.printf(Decoracion.B_CIAN + "║ " + Decoracion.B_AMARILLO + " [0%d] " + colorLiga + "%-34s" + Decoracion.B_CIAN + " ║%n",
                    (i + 1), nombreLiga.toUpperCase());
        }

        System.out.println(Decoracion.B_CIAN + "╚" + "═".repeat(ANCHO + 2) + "╝" + Decoracion.RESET);
        System.out.print(Decoracion.B_AMARILLO + " -> Seleccion: " + Decoracion.RESET);

        int sel = sc.nextInt();
        sc.nextLine();

        if (sel < 1 || sel > ligasDisponibles.size()) {
            System.out.println(Decoracion.ROJO + "Selección no válida." + Decoracion.RESET);
            return;
        }

        Liga ligaBase = ligasDisponibles.get(sel - 1);
        String colorElegido = obtenerColorPorLiga(ligaBase.getNombre());

        this.equipoPropio.iniciarFutDraft(alJugador, sc);
        this.equipoPropio.setNombreLiga(ligaBase.getNombre());

        List<Equipo> participantes = new ArrayList<>(ligaBase.getEquipos());
        participantes.removeIf(e -> e.getNombre().equalsIgnoreCase(equipoPropio.getNombre()));
        participantes.add(equipoPropio);

        List<Jornada> calendario = Liga.generarCalendario(participantes);
        this.ligaEnCurso = new Liga(ligaBase.getNombre(), participantes, calendario, this.alEquipo);

        System.out.println("\n" + Decoracion.B_BLANCO + "┌" + "─".repeat(ANCHO) + "┐");
        System.out.println("│" + Decoracion.centrar("¡CALENDARIO GENERADO!", ANCHO) + "│");
        System.out.println("└" + "─".repeat(ANCHO) + "┘");

        System.out.println(Decoracion.B_VERDE + " Has ingresado en la " + colorElegido + ligaBase.getNombre().toUpperCase() + Decoracion.RESET);
        System.out.println(Decoracion.B_CIAN + " ¡Mucha suerte en tu carrera, Míster!" + Decoracion.RESET);
        System.out.println("\n" + Decoracion.B_AMARILLO + " [ Presiona ENTER para entrar al despacho ] " + Decoracion.RESET);
        sc.nextLine();

        this.ligaEnCurso.menuFutDraft(sc, equipoPropio);
    }

    private String obtenerColorPorLiga(String nombreLiga) {
        return switch (nombreLiga.toUpperCase()) {
            case "LALIGA" -> Decoracion.ROJO_ANARANJADO;
            case "PREMIER LEAGUE" -> Decoracion.B_AZUL;
            case "BUNDESLIGA" -> Decoracion.B_ROJO;
            case "SERIE A" -> Decoracion.B_VERDE;
            case "LIGUE 1" -> Decoracion.B_PURPURA;
            default -> Decoracion.B_CIAN;
        };
    }

    public Liga getLigaEnCurso() {
        return ligaEnCurso;
    }

    public void setEquipoPropio(EquipoPropio equipoPropio) {
        this.equipoPropio = equipoPropio;
    }

}