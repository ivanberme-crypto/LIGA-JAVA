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
            System.out.println(Decoracion.ROJO + "\n[!] No hay ligas cargadas en el sistema." + Decoracion.RESET);
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
                String nombreLiga = ligasDisponibles.get(i).getNombre().toUpperCase();
                System.out.printf(Decoracion.CIAN + "║  " + Decoracion.AMARILLO + "[%02d]" + Decoracion.RESET + " %-35s " + Decoracion.CIAN + "║%n",
                        (i + 1), nombreLiga);
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
                    AlmacenEquipo.mostrarSubmenuEquipos(alEquipo, sc, seleccionada.getNombre());
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
        System.out.println("\n--- SELECCIONA TU LIGA ---");
        for (int i = 0; i < ligasDisponibles.size(); i++) {
            System.out.println("  [" + (i + 1) + "] " + ligasDisponibles.get(i).getNombre());
        }

        System.out.print("Elección: ");
        int sel = sc.nextInt();
        sc.nextLine();

        Liga ligaBase = ligasDisponibles.get(sel - 1);

        this.equipoPropio.iniciarFutDraft(alJugador, sc);
        this.equipoPropio.setNombreLiga(ligaBase.getNombre());

        List<Equipo> participantes = new ArrayList<>(ligaBase.getEquipos());

        participantes.removeIf(e -> e.getNombre().equalsIgnoreCase(equipoPropio.getNombre()));
        participantes.add(equipoPropio);

        List<Jornada> calendario = Liga.generarCalendario(participantes);

        this.ligaEnCurso = new Liga(ligaBase.getNombre(), participantes, calendario);

        System.out.println("\n¡Todo listo! Suerte en la " + ligaBase.getNombre() + "!");

        this.ligaEnCurso.menuFutDraft(sc, equipoPropio);
    }
}