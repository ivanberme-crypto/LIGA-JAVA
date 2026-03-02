package Fut.equipos;

import Fut.Decoracion;
import Fut.ligas.Liga;
import Fut.personas.AlmacenJugador;
import Fut.personas.Jugador;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class AlmacenEquipo {

    private List<Equipo> todosLosEquipos;
    private List<Liga> ligas;

    public AlmacenEquipo() {
        this.todosLosEquipos = new ArrayList<>();
        this.ligas = new ArrayList<>();
        cargarEquiposDesdeTxt("src/Fut/almacen/Almacen.txt");
    }

    public void organizarEnLigas() {
        ligas.clear();
        for (Equipo e : todosLosEquipos) {
            Liga ligaEncontrada = null;
            for (Liga l : ligas) {
                if (l.getNombre().equalsIgnoreCase(e.getNombreLiga())) {
                    ligaEncontrada = l;
                    break;
                }
            }

            if (ligaEncontrada == null) {
                ligaEncontrada = new Liga(e.getNombreLiga(), new ArrayList<>(), new ArrayList<>());
                ligas.add(ligaEncontrada);
            }
            ligaEncontrada.agregarEquipo(e);
        }
    }

    public List<Equipo> getEquipos() {
        return todosLosEquipos;
    }

    public List<Liga> getLigas() {
        return ligas;
    }

    public static void mostrarSubmenuEquipos(AlmacenEquipo alEquipo, Scanner sc, String ligaSeleccionada) {
        int seleccion;
        int ANCHO_INTERIOR = 43;
        List<Equipo> listaFiltrada = new ArrayList<>();

        for (Equipo e : alEquipo.getEquipos()) {
            if (e.getNombreLiga().equalsIgnoreCase(ligaSeleccionada)) {
                listaFiltrada.add(e);
            }
        }

        if (listaFiltrada.isEmpty()) {
            System.out.println(Decoracion.ROJO + "\n[!] No hay equipos en la liga: " + ligaSeleccionada + Decoracion.RESET);
            return;
        }

        do {
            System.out.println("\n\n");
            System.out.println(Decoracion.CIAN + "╔═══════════════════════════════════════════╗" + Decoracion.RESET);
            System.out.println(Decoracion.CIAN + "║" + Decoracion.AMARILLO + Decoracion.centrar(ligaSeleccionada.toUpperCase(), ANCHO_INTERIOR) + Decoracion.CIAN + "║" + Decoracion.RESET);
            System.out.println(Decoracion.CIAN + "╠═══════════════════════════════════════════╣" + Decoracion.RESET);
            System.out.println(Decoracion.CIAN + "║" + " ".repeat(ANCHO_INTERIOR) + "║" + Decoracion.RESET);

            for (int i = 0; i < listaFiltrada.size(); i++) {
                System.out.printf(Decoracion.CIAN + "║  " + Decoracion.AMARILLO + "[%02d]" + Decoracion.RESET + " %-35s " + Decoracion.CIAN + "║%n",
                        (i + 1), listaFiltrada.get(i).getNombre());
            }

            System.out.println(Decoracion.CIAN + "║" + " ".repeat(ANCHO_INTERIOR) + "║" + Decoracion.RESET);
            System.out.println(Decoracion.CIAN + "╠═══════════════════════════════════════════╣" + Decoracion.RESET);
            System.out.printf(Decoracion.CIAN + "║  " + Decoracion.ROJO + "[00]" + Decoracion.RESET + " %-35s " + Decoracion.CIAN + "║%n", "VOLVER ATRAS");
            System.out.println(Decoracion.CIAN + "╚═══════════════════════════════════════════╝" + Decoracion.RESET);
            System.out.print(Decoracion.AMARILLO + " >> SELECCIONA UN EQUIPO: " + Decoracion.RESET);

            seleccion = sc.nextInt();
            sc.nextLine();

            if (seleccion > 0 && seleccion <= listaFiltrada.size()) {
                Equipo seleccionado = listaFiltrada.get(seleccion - 1);

                System.out.println("\n\n" + Decoracion.VERDE + "  COMPOSICIÓN DE LA PLANTILLA: " + seleccionado.getNombre().toUpperCase() + Decoracion.RESET);
                System.out.println("  " + "─".repeat(ANCHO_INTERIOR));

                System.out.printf(Decoracion.AMARILLO + "  %-20s | %-10s | %-5s" + Decoracion.RESET + "%n", "NOMBRE", "POS", "MED");
                System.out.println("  " + "─".repeat(ANCHO_INTERIOR));

                if (seleccionado.getPlantilla().isEmpty()) {
                    System.out.println("  No hay jugadores cargados en este equipo.");
                } else {
                    for (Jugador j : seleccionado.getPlantilla()) {
                        System.out.printf("  %-20s | %-10s | %-5d%n",
                                j.getNombre(), j.getPosicion(), j.getMedia());
                    }
                }
                System.out.println("  " + "─".repeat(ANCHO_INTERIOR));
                System.out.println(Decoracion.AMARILLO + "  Pulse ENTER para continuar..." + Decoracion.RESET);
                sc.nextLine();
            }
        } while (seleccion != 0);
    }

    public static void vincularJugadoresAEquipos(AlmacenEquipo alEquipo, AlmacenJugador alJugador) {
        List<Jugador> todosLosJugadores = new ArrayList<>();
        todosLosJugadores.addAll(alJugador.getPorteros());
        todosLosJugadores.addAll(alJugador.getDefensas());
        todosLosJugadores.addAll(alJugador.getMedios());
        todosLosJugadores.addAll(alJugador.getDelanteros());

        for (Equipo equipo : alEquipo.getEquipos()) {
            for (Jugador jugador : todosLosJugadores) {
                if (jugador.getEquipo().equalsIgnoreCase(equipo.getNombre())) {
                    equipo.añadirJugador(jugador);
                }
            }
        }
        alEquipo.organizarEnLigas();
    }

    private void cargarEquiposDesdeTxt(String ruta) {
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                linea = linea.trim();

                if (linea.startsWith("equipos.add")) {
                    int inicioConstructor = linea.indexOf("new Equipo(") + 11;
                    int finConstructor = linea.lastIndexOf(")");

                    String datos = linea.substring(inicioConstructor, finConstructor);
                    datos = datos.replace("\"", "");

                    String[] partes = datos.split(",");

                    if (partes.length >= 6) {
                        String liga = partes[0].trim();
                        String nombre = partes[1].trim();
                        String ciudad = partes[2].trim();
                        String estadio = partes[3].trim();
                        String entrenador = partes[4].trim();

                        String titulosStr = partes[5].replaceAll("[^0-9]", "").trim();
                        int titulos = Integer.parseInt(titulosStr);

                        todosLosEquipos.add(new Equipo(liga, nombre, ciudad, estadio, entrenador, titulos));
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error procesando el archivo de equipos: " + e.getMessage());
        }
    }
}