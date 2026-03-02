package Fut.ligas;

import Fut.equipos.Equipo;
import Fut.equipos.EquipoPropio;
import Fut.ligas.jornadas.Jornada;
import Fut.ligas.jornadas.Partido;
import Fut.personas.Jugador;

import java.util.*;

public class Liga {
    private String nombre;
    private List<Equipo> equipos;
    private Scanner sc = new Scanner(System.in);
    private List<Jornada> jornadas;
    private int jornadaActual;

    public Liga(String nombre, List<Equipo> equipos, List<Jornada> jornadas) {
        this.nombre = nombre;
        this.equipos = equipos;
        this.jornadas = jornadas;
        this.jornadaActual = 0;
        this.sc = new Scanner(System.in);
    }

    public void menuFutDraft(Scanner sc, EquipoPropio equipoUsuario) {
        this.sc = sc;
        int opcion = -1;

        do {
            System.out.println("\n===== MENU LIGA: " + this.nombre + " =====");
            System.out.println("1. Ver clasificación");
            System.out.println("2. Ver jornada actual");
            System.out.println("3. Iniciar jornada (Jugar)");
            System.out.println("4. Ver mi equipo");
            System.out.println("5. Ranking goleadores");
            System.out.println("0. Salir");
            System.out.print("Selección: ");

            try {
                opcion = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Error: Introduce un número válido.");
                sc.nextLine();
                continue;
            }

            switch (opcion) {
                case 1: mostrarClasificacion(); break;
                case 2: mostrarJornada(); break;
                case 3: jugarJornada(); break;
                case 4: mostrarEquipoUsuario(equipoUsuario); break;
                case 5: mostrarGoleadores(); break;
                case 0: System.out.println("Regresando al menú principal..."); break;
                default: System.out.println("Opción no válida."); break;
            }
        } while (opcion != 0);
    }

    private void mostrarClasificacion() {
        System.out.println("\n. . . . . . . . . . . . . . . . . . . . . CLASIFICACIÓN . . . . . . . . . . . . . . . . . . . . .");

        equipos.sort((e1, e2) -> {
            int diffPuntos = Integer.compare(e2.getPuntos(), e1.getPuntos());
            if (diffPuntos != 0) return diffPuntos;
            int dg1 = e1.getGolesFavor() - e1.getGolesContra();
            int dg2 = e2.getGolesFavor() - e2.getGolesContra();
            if (dg2 != dg1) return Integer.compare(dg2, dg1);
            return Integer.compare(e2.getGolesFavor(), e1.getGolesFavor());
        });

        System.out.printf("%-4s %-25s %3s %3s %3s %3s %3s %3s %3s%n",
                "POS", "EQUIPO", "PTS", "PJ", "PG", "PE", "PP", "GF", "GC");
        System.out.println("------------------------------------------------------------------------------------------");

        for (int i = 0; i < equipos.size(); i++) {
            Equipo e = equipos.get(i);
            System.out.printf("%-4d %-25s %3d %3d %3d %3d %3d %3d %3d%n",
                    (i + 1), e.getNombre(), e.getPuntos(), e.getJugados(),
                    e.getGanados(), e.getEmpatados(), e.getPerdidos(),
                    e.getGolesFavor(), e.getGolesContra());
        }
    }

    private void mostrarJornada() {
        if (jornadaActual >= jornadas.size()) {
            System.out.println("La liga ha finalizado.");
            return;
        }
        System.out.println("\n--- PRÓXIMA JORNADA: " + (jornadaActual + 1) + " de " + jornadas.size() + " ---");
        jornadas.get(jornadaActual).mostrarPartidos();
    }

    private void jugarJornada() {
        if (jornadaActual < jornadas.size()) {
            jornadas.get(jornadaActual).simular(jornadaActual + 1);
            jornadaActual++;

            if (jornadaActual == jornadas.size()) {
                finLiga();
            }
        } else {
            System.out.println("La liga ha terminado.");
        }
    }

    private void finLiga() {
        System.out.println("\n¡La competición ha llegado a su fin!");
        equipos.sort((e1, e2) -> Integer.compare(e2.getPuntos(), e1.getPuntos()));

        List<Jugador> todosLosJugadores = new ArrayList<>();
        equipos.forEach(e -> todosLosJugadores.addAll(e.getPlantilla()));
        todosLosJugadores.sort((j1, j2) -> Integer.compare(j2.getGoles(), j1.getGoles()));

        LigaTerminada resumen = new LigaTerminada(this.nombre, equipos.get(0), equipos, todosLosJugadores);
        resumen.mostrarMenuFinal(sc);
    }

    public void agregarEquipo(Equipo e) {
        if (this.equipos == null) {
            this.equipos = new ArrayList<>();
        }
        this.equipos.add(e);
    }

    public static List<Jornada> generarCalendario(List<Equipo> listaEquipos) {
        List<Jornada> calendarioCompleto = new ArrayList<>();
        int numEquipos = listaEquipos.size();
        if (numEquipos % 2 != 0) return calendarioCompleto;

        List<Equipo> copia = new ArrayList<>(listaEquipos);
        Collections.shuffle(copia);

        for (int i = 0; i < numEquipos - 1; i++) {
            Jornada jornadaIda = new Jornada();
            for (int k = 0; k < numEquipos / 2; k++) {
                int local = (i + k) % (numEquipos - 1);
                int visitante = (numEquipos - 1 - k + i) % (numEquipos - 1);

                if (k == 0) visitante = numEquipos - 1;

                jornadaIda.agregarPartido(new Partido(copia.get(local), copia.get(visitante)));
            }
            calendarioCompleto.add(jornadaIda);
        }
        int numJornadasIda = calendarioCompleto.size();
        for (int i = 0; i < numJornadasIda; i++) {
            Jornada ida = calendarioCompleto.get(i);
            Jornada vuelta = new Jornada();
            for (Partido pIda : ida.getPartidos()) {
                vuelta.agregarPartido(new Partido(pIda.getEquipoVisitante(), pIda.getEquipoLocal()));
            }
            calendarioCompleto.add(vuelta);
        }
        return calendarioCompleto;
    }

    private void mostrarEquipoUsuario(EquipoPropio eq) {
        System.out.println("\n--- TU EQUIPO: " + eq.getNombre().toUpperCase() + " ---");
        eq.getPlantilla().forEach(j -> System.out.printf(" - %s %-20s (Media: %d)%n", j.getPosicion(), j.getNombre(), j.getMedia()));
    }

    private void mostrarGoleadores() {
        List<Jugador> goleadores = new ArrayList<>();
        equipos.forEach(e -> goleadores.addAll(e.getPlantilla()));
        goleadores.stream()
                .filter(j -> j.getGoles() > 0)
                .sorted(Comparator.comparingInt(Jugador::getGoles).reversed())
                .limit(10)
                .forEach(j -> System.out.printf("%-20s | %2d goles | %s%n", j.getNombre(), j.getGoles(), j.getEquipo()));
    }

    public String getNombre() { return nombre; }

    public List<Equipo> getEquipos() { return equipos; }
}