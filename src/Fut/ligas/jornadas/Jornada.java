package Fut.ligas.jornadas;

import java.util.ArrayList;
import java.util.List;
import Fut.Decoracion;
import Fut.equipos.Equipo;
import Fut.personas.Jugador;

public class Jornada {
    private List<Partido> partidos;

    public Jornada() {
        this.partidos = new ArrayList<>();
    }

    public void agregarPartido(Partido p) {
        partidos.add(p);
    }

    public void mostrarPartidos() {
        for (Partido p : partidos) {
            System.out.printf(Decoracion.B_BLANCO + "  %22s   " +
                            Decoracion.NARANJA + "%d - %d" +
                            Decoracion.B_BLANCO + "   %-22s %n",
                    p.getEquipoLocal().getNombre().toUpperCase(),
                    p.getGolesLocal(),
                    p.getGolesVisitante(),
                    p.getEquipoVisitante().getNombre().toUpperCase());
        }
    }

    public void simular(int numeroJornada) {
        System.out.println("\n" + Decoracion.B_BLANCO + "  " + "—".repeat(55));
        System.out.println(Decoracion.B_AMARILLO + Decoracion.centrar("JORNADA " + String.format("%02d", numeroJornada), 55));
        System.out.println(Decoracion.B_BLANCO + "  " + "—".repeat(55) + Decoracion.RESET + "\n");

        for (Partido p : partidos) {
            limpiarSancionadosPasados(p.getEquipoLocal());
            limpiarSancionadosPasados(p.getEquipoVisitante());
        }

        for (Partido p : partidos) {
            p.jugarPartido();
            p.getEquipoLocal().registrarResultado(p.getGolesLocal(), p.getGolesVisitante());
            p.getEquipoVisitante().registrarResultado(p.getGolesVisitante(), p.getGolesLocal());

            System.out.printf(Decoracion.B_BLANCO + "  %22s   " +
                            Decoracion.NARANJA + "%d - %d" +
                            Decoracion.B_BLANCO + "   %-22s %n",
                    p.getEquipoLocal().getNombre().toUpperCase(),
                    p.getGolesLocal(),
                    p.getGolesVisitante(),
                    p.getEquipoVisitante().getNombre().toUpperCase());

        }
        System.out.println("\n" + Decoracion.B_BLANCO + "  " + "—".repeat(55) + Decoracion.RESET);
        System.out.println("");
        System.out.println(Decoracion.B_VERDE + Decoracion.centrar("JORNADA COMPLETADA", 55) + Decoracion.RESET);
    }

    private void limpiarSancionadosPasados(Equipo e) {
        for (Jugador j : e.getPlantilla()) {
            if (j.isSancionado()) {
                j.cumplirSancion();
            }
        }
    }

    public List<Partido> getPartidos() {
        return partidos;
    }
}