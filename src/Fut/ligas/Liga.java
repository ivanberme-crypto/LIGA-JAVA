package Fut.ligas;

import Fut.Decoracion;
import Fut.equipos.AlmacenEquipo;
import Fut.equipos.Equipo;
import Fut.equipos.EquipoPropio;
import Fut.ligas.jornadas.Jornada;
import Fut.ligas.jornadas.Partido;
import Fut.personas.Jugador;

import java.util.*;
import java.util.stream.Collectors;

public class Liga {
    private String nombre;
    private List<Equipo> equipos;
    private List<Jornada> jornadas;
    private int jornadaActual;
    private AlmacenEquipo alEquipo;

    public Liga(String nombre, List<Equipo> equipos, List<Jornada> jornadas, AlmacenEquipo alEquipo) {
        this.nombre = nombre;
        this.equipos = equipos;
        this.jornadas = jornadas;
        this.jornadaActual = 0;
        this.alEquipo = alEquipo;
    }

    public void menuFutDraft(Scanner sc, EquipoPropio equipoUsuario) {
        int opcion = -1;
        int ANCHO = 45;

        do {
            System.out.println("\n" + Decoracion.B_AZUL + "╔" + "═".repeat(ANCHO) + "╗");

            System.out.println("║" + Decoracion.B_BLANCO + Decoracion.centrar("MODO LIGA: " + this.nombre.toUpperCase(), ANCHO) + Decoracion.B_AZUL + "║");

            System.out.println("╠" + "═".repeat(ANCHO) + "╣");
            System.out.printf(Decoracion.B_AZUL + "║ " + Decoracion.B_CIAN + " 1. " + Decoracion.RESET + "%-39s" + Decoracion.B_AZUL + " ║%n", "Ver Clasificación Actual");
            System.out.printf(Decoracion.B_AZUL + "║ " + Decoracion.B_CIAN + " 2. " + Decoracion.RESET + "%-39s" + Decoracion.B_AZUL + " ║%n", "Ver Calendario Completo");
            System.out.printf(Decoracion.B_AZUL + "║ " + Decoracion.B_CIAN + " 3. " + Decoracion.RESET + "%-39s" + Decoracion.B_AZUL + " ║%n", "Ver Próxima Jornada");
            System.out.printf(Decoracion.B_AZUL + "║ " + Decoracion.B_CIAN + " 4. " + Decoracion.B_BLANCO + "%-39s" + Decoracion.B_AZUL + " ║%n", "JUGAR SIGUIENTE JORNADA");
            System.out.printf(Decoracion.B_AZUL + "║ " + Decoracion.B_CIAN + " 5. " + Decoracion.RESET + "%-39s" + Decoracion.B_AZUL + " ║%n", "Ver Mi Plantilla");
            System.out.printf(Decoracion.B_AZUL + "║ " + Decoracion.B_CIAN + " 6. " + Decoracion.RESET + "%-39s" + Decoracion.B_AZUL + " ║%n", "Pichichis de la liga");
            System.out.printf(Decoracion.B_AZUL + "║ " + Decoracion.B_CIAN + " 7. " + Decoracion.RESET + "%-39s" + Decoracion.B_AZUL + " ║%n", "Ver Equipos y Plantillas");

            System.out.println("╠" + "═".repeat(ANCHO) + "╣");
            System.out.printf(Decoracion.B_AZUL + "║ " + Decoracion.B_ROJO + " 0. " + Decoracion.RESET + "%-39s" + Decoracion.B_AZUL + " ║%n", "Salir al Menú Principal");
            System.out.println("╚" + "═".repeat(ANCHO) + "╝" + Decoracion.RESET);
            System.out.print(Decoracion.B_AMARILLO + " -> Selección: " + Decoracion.RESET);

            try {
                opcion = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println(Decoracion.ROJO + " Error: Introduce un número válido." + Decoracion.RESET);
                sc.nextLine();
                continue;
            }

            switch (opcion) {
                case 1: mostrarClasificacion(sc); break;
                case 2: verCalendarioCompleto(); break;
                case 3: mostrarJornada(); break;
                case 4: jugarJornada(sc); break;
                case 5: mostrarEquipoUsuario(equipoUsuario); break;
                case 6: mostrarGoleadores(); break;
                case 7: mostrarEquiposYPlantillas(sc); break;
                case 0: System.out.println(Decoracion.B_ROJO + " Volviendo al menu principal..." + Decoracion.RESET); break;
                default: System.out.println(Decoracion.ROJO + " Opción no válida." + Decoracion.RESET); break;
            }
        } while (opcion != 0);
    }

    private void mostrarClasificacion(Scanner sc) {
        int anchoTotal = 75;

        System.out.println("\n" + Decoracion.B_BLANCO + "┌" + "─".repeat(anchoTotal) + "┐");
        System.out.println("│" + Decoracion.B_AMARILLO + Decoracion.centrar("TABLA DE POSICIONES - " + nombre.toUpperCase(), anchoTotal) + Decoracion.B_BLANCO + "│");

        System.out.println("├" + "─".repeat(27) + "┬" + "─".repeat(5) + "┬" + "─".repeat(5) + "┬" + "─".repeat(5) + "┬" + "─".repeat(5) + "┬" + "─".repeat(5) + "┬" + "─".repeat(5) + "┬" + "─".repeat(5) + "┬" + "─".repeat(5) + "┤");
        System.out.printf("│ %-25s │ %3s │ %3s │ %3s │ %3s │ %3s │ %3s │ %3s │ %3s │%n",
                "CLUB", "PJ", "V", "E", "D", "GF", "GC", "DG", "PTS");

        System.out.println("├" + "─".repeat(27) + "┼" + "─".repeat(5) + "┼" + "─".repeat(5) + "┼" + "─".repeat(5) + "┼" + "─".repeat(5) + "┼" + "─".repeat(5) + "┼" + "─".repeat(5) + "┼" + "─".repeat(5) + "┼" + "─".repeat(5) + "┤");
        equipos.sort((e1, e2) -> {
            int diffPuntos = Integer.compare(e2.getPuntos(), e1.getPuntos());
            if (diffPuntos != 0) return diffPuntos;
            return Integer.compare(e2.diferenciaGoles(), e1.diferenciaGoles());
        });

        for (int i = 0; i < equipos.size(); i++) {
            Equipo e = equipos.get(i);

            String colorNombre = (i < 3) ? Decoracion.B_VERDE : (i >= equipos.size() - 3) ? Decoracion.B_ROJO : Decoracion.RESET;

            System.out.printf(Decoracion.B_BLANCO + "│ " + colorNombre + "%-25s" + Decoracion.B_BLANCO + " │ %3d │ %3d │ %3d │ %3d │ %3d │ %3d │ %3d │ " + Decoracion.B_AMARILLO + "%3d" + Decoracion.B_BLANCO + " │%n",
                    ((i+1)+". ")+e.getNombre(), e.getJugados(),
                    e.getGanados(), e.getEmpatados(), e.getPerdidos(),
                    e.getGolesFavor(), e.getGolesContra(), e.diferenciaGoles(), e.getPuntos());
        }

        System.out.println("└" + "─".repeat(anchoTotal) + "┘" + Decoracion.RESET);
        System.out.println(Decoracion.B_AMARILLO + " [ Presiona ENTER para volver al menú ]" + Decoracion.RESET);
        sc.nextLine();
        sc.nextLine();
    }

    private void mostrarJornada() {
        System.out.println("\n" + Decoracion.B_BLANCO + "  " + "—".repeat(55));
        System.out.println(Decoracion.B_AMARILLO + Decoracion.centrar("PARTIDOS - JORNADA " + (jornadaActual + 1), 55));
        System.out.println(Decoracion.B_BLANCO + "  " + "—".repeat(55) + Decoracion.RESET + "\n");

        jornadas.get(jornadaActual).mostrarPartidos();

        System.out.println("\n" + Decoracion.B_BLANCO + "  " + "—".repeat(55) + Decoracion.RESET);
    }

    private void jugarJornada(Scanner sc) {
        if (jornadaActual < jornadas.size()) {
            jornadas.get(jornadaActual).simular(jornadaActual + 1);
            jornadaActual++;

            if (jornadaActual == jornadas.size()) {
                finLiga(sc);
            }
        }
    }

    private void finLiga(Scanner sc) {
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

    public void verCalendarioCompleto() {
        System.out.println("\n" + Decoracion.B_CIAN + "╔" + "═".repeat(55) + "╗");
        System.out.println("║" + Decoracion.centrar("CALENDARIO COMPLETO: " + this.nombre.toUpperCase(), 55) + "║");
        System.out.println("╚" + "═".repeat(55) + "╝" + Decoracion.RESET);
        if (jornadas == null || jornadas.isEmpty()) {
            System.out.println(Decoracion.ROJO + "  No hay jornadas generadas todavía." + Decoracion.RESET);
            return;
        }
        for (int i = 0; i < jornadas.size(); i++) {
            System.out.println("\n" + Decoracion.B_AMARILLO + " JORNADA Nº" + (i + 1) + Decoracion.RESET);
            System.out.println("  " + "─".repeat(53));
            jornadas.get(i).mostrarPartidos();
        }
        System.out.println("\n" + Decoracion.B_CIAN + " [ Fin del calendario ] " + Decoracion.RESET);
    }

    private void mostrarEquipoUsuario(EquipoPropio eq) {
        int ANCHO = 55;
        List<Jugador> plantilla = eq.getPlantilla();

        System.out.println("\n" + Decoracion.B_BLANCO + "  " + "—".repeat(ANCHO));
        System.out.println(Decoracion.B_AMARILLO + Decoracion.centrar("PLANTILLA 4-3-3: " + eq.getNombre().toUpperCase(), ANCHO));
        System.out.println(Decoracion.B_BLANCO + "  " + "—".repeat(ANCHO) + Decoracion.RESET);

        List<Jugador> delanteros = plantilla.stream()
                .filter(j -> j.getPosicion().toString().matches("LW|ST|RW"))
                .limit(3).collect(Collectors.toCollection(ArrayList::new));

        List<Jugador> medios = plantilla.stream()
                .filter(j -> j.getPosicion().toString().matches("CDM|CM|CAM"))
                .limit(3).collect(Collectors.toCollection(ArrayList::new));

        List<Jugador> defensas = plantilla.stream()
                .filter(j -> j.getPosicion().toString().matches("LB|CB|RB"))
                .limit(4).collect(Collectors.toCollection(ArrayList::new));

        List<Jugador> porteros = plantilla.stream()
                .filter(j -> j.getPosicion().toString().equals("GK"))
                .limit(1).collect(Collectors.toCollection(ArrayList::new));

        defensas.sort(Comparator.comparing(j -> {
            String pos = j.getPosicion().toString();
            if (pos.equals("LB")) return 1;
            if (pos.equals("CB")) return 2;
            return 3;
        }));

        medios.sort(Comparator.comparing(j -> {
            String pos = j.getPosicion().toString();
            if (pos.equals("CDM") || pos.equals("CAM")) return 2;
            return 1;
        }));

        delanteros.sort(Comparator.comparing(j -> {
            String pos = j.getPosicion().toString();
            if (pos.equals("LW")) return 1;
            if (pos.equals("ST")) return 2;
            return 3;
        }));

        System.out.println("");
        imprimirFilaCampo(delanteros, 3);
        System.out.println("");
        imprimirFilaCampo(medios, 3);
        System.out.println("");
        imprimirFilaCampo(defensas, 4);
        System.out.println("");
        imprimirFilaCampo(porteros, 1);
        System.out.println("\n" + Decoracion.B_BLANCO + "  " + "—".repeat(ANCHO));
        double media = eq.getMediaEquipo();
        String colorMed = (media >= 85) ? Decoracion.B_VERDE : (media >= 75) ? Decoracion.B_AMARILLO : Decoracion.B_ROJO;
        System.out.printf("   " + Decoracion.B_CIAN + "%-30s" + colorMed + "%5.1f" + Decoracion.RESET + "%n",
                "VALORACIÓN MEDIA DEL ONCE:", media);
        System.out.println(Decoracion.B_BLANCO + "  " + "—".repeat(ANCHO) + Decoracion.RESET);
    }

    private void imprimirFilaCampo(List<Jugador> jugadores, int esperado) {
        StringBuilder filaNombres = new StringBuilder();
        StringBuilder filaPosiciones = new StringBuilder();
        int anchoColumna = (esperado == 4) ? 14 : (esperado == 3) ? 18 : 55;

        for (int i = 0; i < esperado; i++) {
            String nombreTxt = "---";
            String posTxt = " ";

            if (i < jugadores.size()) {
                Jugador j = jugadores.get(i);
                String nombreFull = j.getNombre().trim();

                nombreTxt = nombreFull.contains(" ") ?
                        nombreFull.substring(nombreFull.lastIndexOf(" ") + 1).toUpperCase() :
                        nombreFull.toUpperCase();

                if (nombreTxt.length() > anchoColumna - 1) {
                    nombreTxt = nombreTxt.substring(0, anchoColumna - 3) + ".";
                }

                posTxt = "[" + j.getPosicion().toString() + "]";
            }

            filaNombres.append(Decoracion.centrarEnColumna(nombreTxt, anchoColumna));
            filaPosiciones.append(Decoracion.centrarEnColumna(posTxt, anchoColumna));
        }

        System.out.println(Decoracion.B_BLANCO + filaNombres.toString() + Decoracion.RESET);
        System.out.println(filaPosiciones.toString());
    }
    private void mostrarEquiposYPlantillas(Scanner sc) {
        this.alEquipo.mostrarSubmenuEquipos(this.alEquipo, sc, this.nombre, true);
    }

    private void mostrarGoleadores() {
        int ANCHO = 60;
        List<Jugador> goleadores = new ArrayList<>();
        equipos.forEach(e -> goleadores.addAll(e.getPlantilla()));

        List<Jugador> topGoleadores = goleadores.stream()
                .filter(j -> j.getGoles() > 0)
                .sorted(Comparator.comparingInt(Jugador::getGoles).reversed())
                .limit(10)
                .collect(Collectors.toList());

        if (topGoleadores.isEmpty()) {
            System.out.println(Decoracion.ROJO + "\n Comienza la liga para ver los goleadores." + Decoracion.RESET);
            return;
        }

        System.out.println("\n" + Decoracion.B_BLANCO + "  " + "—".repeat(ANCHO));
        System.out.println(Decoracion.B_AMARILLO + Decoracion.centrar(" TABLA DE GOLEADORES - BOTA DE ORO ", ANCHO));
        System.out.println(Decoracion.B_BLANCO + "  " + "—".repeat(ANCHO) + Decoracion.RESET);

        String fmtGoleador = "  " + Decoracion.B_BLANCO + "%2d. " + Decoracion.RESET + "%s%-22s%s %s %-18s %s %s%2d GOLES%s%n";

        int puesto = 1;
        for (Jugador j : topGoleadores) {
            String colorNombre = (puesto == 1) ? Decoracion.B_AMARILLO : Decoracion.RESET;
            String colorGoles = (puesto == 1) ? Decoracion.B_AMARILLO : Decoracion.NARANJA;

            String nombreEquipo = "Sin Equipo";
            for (Equipo eq : equipos) {
                if (eq.getPlantilla().contains(j)) {
                    nombreEquipo = eq.getNombre();
                    break;
                }
            }
            if (nombreEquipo.length() > 18) {
                nombreEquipo = nombreEquipo.substring(0, 15) + "...";
            }

            System.out.printf(fmtGoleador,
                    puesto,
                    colorNombre, j.getNombre().toUpperCase(), Decoracion.RESET,
                    Decoracion.B_CIAN, nombreEquipo, Decoracion.RESET,
                    colorGoles, j.getGoles(), Decoracion.RESET);

            puesto++;
            Decoracion.pausa(70);
        }

        System.out.println(Decoracion.B_BLANCO + "  " + "—".repeat(ANCHO) + Decoracion.RESET);
    }

    public String getNombre() { return nombre; }

    public List<Equipo> getEquipos() { return equipos; }
}