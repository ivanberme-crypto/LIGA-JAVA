package Fut.personas;

import Fut.enumLiga.Posicion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AlmacenJugador {

    private ArrayList<Jugador> porteros;
    private ArrayList<Jugador> defensas;
    private ArrayList<Jugador> medios;
    private ArrayList<Jugador> delanteros;
    private static final Pattern LINEA_JUGADOR = Pattern.compile(
            "^(porteros|defensas|medios|delanteros)\\.add\\(new Jugador\\(\"([^\"]+)\",\\s*(\\d+),\\s*Fut\\.enumLiga\\.Posicion\\.([A-Z_]+),\\s*(\\d+),\\s*([0-9]+\\.[0-9]+),\\s*\"([^\"]+)\"\\)\\);\\s*$"
    );

    public AlmacenJugador() {
        porteros = new ArrayList<>();
        defensas = new ArrayList<>();
        medios = new ArrayList<>();
        delanteros = new ArrayList<>();
    }

    public void cargarDesdeTxt(String rutaTxt) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaTxt))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;

                Matcher m = LINEA_JUGADOR.matcher(linea);
                if (!m.matches()) continue;

                String lista = m.group(1);
                String nombre = m.group(2);
                int edad = Integer.parseInt(m.group(3));
                Posicion posicion = Posicion.valueOf(m.group(4));
                int media = Integer.parseInt(m.group(5));
                double probGol = Double.parseDouble(m.group(6));
                String equipo = m.group(7);

                Jugador j = new Jugador(nombre, edad, posicion, media, probGol, equipo);

                switch (lista) {
                    case "porteros":
                        porteros.add(j);
                        break;
                    case "defensas":
                        defensas.add(j);
                        break;
                    case "medios":
                        medios.add(j);
                        break;
                    case "delanteros":
                        delanteros.add(j);
                        break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error leyendo el archivo: " + rutaTxt, e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Posición inválida en el txt (revisa los valores del enum Posicion).", e);
        }
    }

    public ArrayList<Jugador> getPorteros() {
        return porteros;
    }

    public ArrayList<Jugador> getDefensas() {
        return defensas;
    }

    public ArrayList<Jugador> getMedios() {
        return medios;
    }

    public ArrayList<Jugador> getDelanteros() {
        return delanteros;
    }
}