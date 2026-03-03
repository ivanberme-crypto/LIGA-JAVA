package Fut.ligas;

import Fut.personas.AlmacenJugador;
import Fut.personas.Jugador;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class AlmacenEquipo {

    private List<Equipo> equipos;

    public AlmacenEquipo() {
        equipos = new ArrayList<>();
        cargarEquiposDesdeTxt("src/Fut/almacen/Almacen.txt");
    }

    public static void mostrarSubmenuEquipos(AlmacenEquipo alEquipo, Scanner sc) {
        int seleccion;
        List<Equipo> lista = alEquipo.getEquipos();

        do {
            System.out.println("\n EQUIPOS DISPONIBLES ");
            for (int i = 0; i < lista.size(); i++) {
                System.out.println((i + 1) + ". " + lista.get(i).getNombre());
            }
            System.out.println("0. Volver al menú principal");
            System.out.print("Elija un equipo para ver su plantilla: ");
            seleccion = sc.nextInt();

            if (seleccion > 0 && seleccion <= lista.size()) {
                Equipo seleccionado = lista.get(seleccion - 1);
                System.out.println("\n Plantilla del " + seleccionado.getNombre() + " ");


                if (seleccionado.getPlantilla().isEmpty()) {
                    System.out.println("No hay jugadores cargados en este equipo.");
                } else {
                    for (Jugador j : seleccionado.getPlantilla()) {
                        System.out.println(j);
                    }
                }

                System.out.println("..................................................");
                System.out.println("Presione 0 para volver o el número de otro equipo.");
                seleccion = sc.nextInt();
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

                    if (partes.length >= 5) {
                        String nombre = partes[0].trim();
                        String ciudad = partes[1].trim();
                        String estadio = partes[2].trim();
                        String entrenador = partes[3].trim();

                        String titulosStr = partes[4].replaceAll("[^0-9]", "").trim();
                        int titulos = Integer.parseInt(titulosStr);

                        equipos.add(new Equipo(nombre, ciudad, estadio, entrenador, titulos));
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error procesando la línea: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Equipo> getEquipos() {
        return equipos;
    }
}
