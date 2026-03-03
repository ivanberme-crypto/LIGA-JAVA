package Fut.ligas;

import java.util.*;
import Fut.personas.Jugador;

public class LaLiga {

    private List<Equipo> equipos;
    private List<Jornada> jornadas;
    private int jornadaActual;


    public LaLiga(List<Equipo> equipos, List<Jornada> jornadas) {
        this.equipos = equipos;
        this.jornadas = jornadas;
        this.jornadaActual = 0;
    }

    public void menuLiga(Scanner sc, EquipoPropio equipoUsuario) {

        int opcion;

        do {
            System.out.println("\n===== MENU LIGA =====");
            System.out.println("1. Ver clasificación");
            System.out.println("2. Ver jornada actual");
            System.out.println("3. Iniciar jornada");
            System.out.println("4. Ver mi equipo");
            System.out.println("5. Ranking goleadores");
            System.out.println("0. Salir");

            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    mostrarClasificacion();
                    break;
                case 2:
                    mostrarJornada();
                    break;
                case 3:
                    jugarJornada();
                    break;
                case 4:
                    mostrarEquipoUsuario(equipoUsuario);
                    break;
                case 5:
                    mostrarGoleadores();
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción inválida");
                    break;
            }

        } while (opcion != 0);
    }

    private void mostrarClasificacion() {

        System.out.println("\n--- CLASIFICACIÓN ---");

        equipos.sort(Comparator.comparingInt(Equipo::getPuntos).thenComparingInt(e -> e.getGolesFavor() - e.getGolesContra()).reversed());

        for (int num = 0; num < equipos.size(); num++) {
            Equipo e = equipos.get(num);
            System.out.printf(
                    "%d. %-20s | PJ:%d G:%d E:%d P:%d | GF:%d GC:%d | Pts:%d%n",
                    (num + 1),
                    e.getNombre(),
                    e.getJugados(),
                    e.getGanados(),
                    e.getEmpatados(),
                    e.getPerdidos(),
                    e.getGolesFavor(),
                    e.getGolesContra(),
                    e.getPuntos()
            );
        }
    }

    private void mostrarJornada() {

        if (jornadaActual >= jornadas.size()) {
            System.out.println("La liga ya terminó.");
            return;
        }

        System.out.println("\n--- JORNADA " + (jornadaActual + 1) + " ---");

        Jornada j = jornadas.get(jornadaActual);
        j.mostrarPartidos();
    }

    private void jugarJornada() {

        if (jornadaActual >= jornadas.size()) {
            System.out.println("La liga ya terminó.");
            return;
        }

        Jornada j = jornadas.get(jornadaActual);
        j.simular();

        jornadaActual++;
    }

    public static List<Jornada> generarCalendario(List<Equipo> listaEquipos) {
        List<Jornada> jornadas = new ArrayList<>();
        int numEquipos = listaEquipos.size();

        int numJornadas = numEquipos - 1;
        int partidosPorJornada = numEquipos / 2;

        for (int i = 0; i < numJornadas; i++) {
            Jornada jornada = new Jornada();
            for (int j = 0; j < partidosPorJornada; j++) {
                int local = (i + j) % (numEquipos - 1);
                int visitante = (numEquipos - 1 - j + i) % (numEquipos - 1);

                if (j == 0) {
                    visitante = numEquipos - 1;
                }
                jornada.agregarPartido(new Partido(listaEquipos.get(local), listaEquipos.get(visitante)));
            }
            jornadas.add(jornada);
        }
        return jornadas;
    }

    private void mostrarEquipoUsuario(EquipoPropio eq) {

        System.out.println("\n--- TU EQUIPO ---");
        System.out.println(eq.getNombre());
        System.out.println("Entrenador: " + eq.getEntrenador());
        System.out.println("Media: " + eq.getMediaEquipo());

        eq.getPlantilla().forEach(j ->
                System.out.println(j.getNombre() + " - " + j.getPosicion())
        );
    }
    //NO FUNCIONA
    private void mostrarGoleadores() {

        System.out.println("\n--- TOP GOLEADORES ---");

        List<Jugador> todos = new ArrayList<>();

        for (Equipo e : equipos) {
            todos.addAll(e.getPlantilla());
        }

        todos.stream()
                .sorted(Comparator.comparingInt(Jugador::getGoles).reversed())
                .limit(10)
                .forEach(j ->
                        System.out.println(j.getNombre() + " - " + j.getGoles() + " goles")
                );
    }


}