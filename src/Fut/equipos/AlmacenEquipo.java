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
                ligaEncontrada = new Liga(e.getNombreLiga(), new ArrayList<>(), new ArrayList<>(), this);
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

    public static void vincularJugadoresAEquipos(AlmacenEquipo alEquipo, AlmacenJugador alJugador) {
        List<Jugador> todosLosJugadores = new ArrayList<>();
        todosLosJugadores.addAll(alJugador.getPorteros());
        todosLosJugadores.addAll(alJugador.getDefensas());
        todosLosJugadores.addAll(alJugador.getMedios());
        todosLosJugadores.addAll(alJugador.getDelanteros());

        for (Equipo equipo : alEquipo.getEquipos()) {
            equipo.getPlantilla().clear();

            for (Jugador jugador : todosLosJugadores) {
                if (jugador.getEquipo().trim().equalsIgnoreCase(equipo.getNombre().trim())) {
                    equipo.añadirJugador(jugador);
                }
            }
        }
        alEquipo.organizarEnLigas();
    }

    public void mostrarSubmenuEquipos(AlmacenEquipo alEquipo, Scanner sc, String ligaSeleccionada, boolean desdeLiga) {
        int seleccion;
        int ANCHO_INTERIOR = 43;
        List<Equipo> listaFiltrada = new ArrayList<>();

        String colorLiga;
        switch (ligaSeleccionada.toUpperCase()) {
            case "LALIGA":
                colorLiga = Decoracion.B_ROJO;
                break;
            case "PREMIER LEAGUE":
                colorLiga = Decoracion.B_AZUL;
                break;
            case "BUNDESLIGA":
                colorLiga = Decoracion.B_AMARILLO;
                break;
            case "SERIE A":
                colorLiga = Decoracion.B_VERDE;
                break;
            case "LIGUE 1":
                colorLiga = Decoracion.B_PURPURA;
                break;
            default:
                colorLiga = Decoracion.B_CIAN;
                break;
        }

        for (Equipo e : alEquipo.getEquipos()) {
            if (e.getNombreLiga().equalsIgnoreCase(ligaSeleccionada)) {
                listaFiltrada.add(e);
            }
        }

        if (listaFiltrada.isEmpty()) {
            System.out.println("\n" + Decoracion.B_BLANCO + "ERROR: " + Decoracion.RESET +
                    Decoracion.ROJO + "No hay equipos en la liga: " + ligaSeleccionada + Decoracion.RESET);
            return;
        }

        do {
            System.out.println("\n" + colorLiga + "╔" + "═".repeat(ANCHO_INTERIOR + 2) + "╗" + Decoracion.RESET);
            System.out.println(colorLiga + "║ " + Decoracion.B_BLANCO +
                    Decoracion.centrar(" LIGA: " + ligaSeleccionada.toUpperCase() + " ", ANCHO_INTERIOR) +
                    Decoracion.RESET + colorLiga + " ║");
            System.out.println(colorLiga + "╠" + "═".repeat(ANCHO_INTERIOR + 2) + "╣" + Decoracion.RESET);

            for (int i = 0; i < listaFiltrada.size(); i++) {
                String indice = String.format("[%02d]", (i + 1));
                String nombreEquipo = listaFiltrada.get(i).getNombre();

                System.out.printf(colorLiga + "║  " + Decoracion.B_AMARILLO + "%s" + Decoracion.RESET +
                                " %-" + (ANCHO_INTERIOR - 6) + "s " + colorLiga + "║%n",
                        indice, nombreEquipo);
            }

            System.out.println(colorLiga + "╠" + "═".repeat(ANCHO_INTERIOR + 2) + "╣" + Decoracion.RESET);
            String textoSalir = desdeLiga ? "VOLVER AL MENÚ LIGA FUTDRAFT" : "VOLVER AL MENÚ LIGAS DISPONIBLES";
            System.out.printf(colorLiga + "║  " + Decoracion.ROJO + "[00]" + Decoracion.RESET +
                    " %-" + (ANCHO_INTERIOR - 6) + "s " + colorLiga + "║%n", textoSalir);
            System.out.println(colorLiga + "╚" + "═".repeat(ANCHO_INTERIOR + 2) + "╝" + Decoracion.RESET);
            System.out.print(Decoracion.B_AMARILLO + " -> Selecciona un equipo (Presiona 0 para salir): " + Decoracion.RESET);

            while (!sc.hasNextInt()) {
                sc.next();
                System.out.print(Decoracion.ROJO + "Entrada no válida. Intenta de nuevo: " + Decoracion.RESET);
            }

            seleccion = sc.nextInt();
            sc.nextLine();

            if (seleccion > 0 && seleccion <= listaFiltrada.size()) {
                Equipo equipoElegido = listaFiltrada.get(seleccion -1);
                mostrarPlantillaEquipo(equipoElegido, ligaSeleccionada);

                System.out.println("\n" + Decoracion.B_AMARILLO + "[ Presiona ENTER para volver a la lista de equipos ]" + Decoracion.RESET);
                sc.nextLine();
            }
        }while (seleccion != 0);
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
            System.err.println("Error al leer la informacion de los equipos: " + e.getMessage());
        }
    }

    public void mostrarPlantillaEquipo(Equipo equipo, String ligaSeleccionada) {
        int ANCHO = 55;
        String colorLiga;
        switch (ligaSeleccionada.toUpperCase()) {
            case "LALIGA":
                colorLiga = Decoracion.B_ROJO;
                break;
            case "PREMIER LEAGUE":
                colorLiga = Decoracion.B_AZUL;
                break;
            case "BUNDESLIGA":
                colorLiga = Decoracion.B_AMARILLO;
                break;
            case "SERIE A":
                colorLiga = Decoracion.B_VERDE;
                break;
            case "LIGUE 1":
                colorLiga = Decoracion.B_PURPURA;
                break;
            default:
                colorLiga = Decoracion.B_CIAN;
                break;
        }
        System.out.println("\n" + colorLiga + "┌" + "─".repeat(ANCHO) + "┐");
        String titulo = "PLANTILLA DE " + equipo.getNombre().toUpperCase();
        System.out.println(colorLiga + "│" + Decoracion.B_BLANCO + Decoracion.centrar(titulo, ANCHO) + colorLiga + "│");
        System.out.println(colorLiga + "├" + "─".repeat(ANCHO) + "┤");

        List<Jugador> plantilla = equipo.getPlantilla();

        if (plantilla == null || plantilla.isEmpty()) {
            System.out.println(colorLiga + "│" + Decoracion.ROJO + Decoracion.centrar(" No hay jugadores registrados en este equipo.",ANCHO)  + colorLiga + "│");
        } else {
            for (Jugador j : plantilla) {
                String colorEstado = Decoracion.RESET;
                if (j.isSancionado()) {
                    colorEstado = Decoracion.B_ROJO;
                } else if (j.getAmarillas() > 0) {
                    colorEstado = Decoracion.B_AMARILLO;
                }
                String colorMedia = (j.getMedia() >= 90) ? Decoracion.B_VERDE :
                        (j.getMedia() >= 80) ? Decoracion.B_AMARILLO : Decoracion.B_ROJO;
                System.out.print(colorLiga + "│" + Decoracion.RESET);

                System.out.printf("   " + colorLiga + "%-5s" + Decoracion.RESET, j.getPosicion());

                System.out.printf(" " + colorEstado + "%-26s " + Decoracion.RESET, j.getNombre());

                System.out.printf(colorMedia + "MEDIA: %-4d" + Decoracion.RESET + "        ", j.getMedia());

                System.out.println(colorLiga + "│");
            }
        }
        System.out.println(colorLiga + "└" + "─".repeat(ANCHO) + "┘" + Decoracion.RESET);
    }
}