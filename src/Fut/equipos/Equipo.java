package Fut.equipos;

import Fut.personas.Jugador;
import java.util.ArrayList;
import java.util.List;

public class Equipo {

    private String liga;
    private String nombre;
    private String ciudad;
    private String estadio;
    private String entrenador;
    private int titulos;
    private List<Jugador> plantilla;
    private int puntos = 0;
    private int jugados = 0;
    private int ganados = 0;
    private int empatados = 0;
    private int perdidos = 0;
    private int golesFavor = 0;
    private int golesContra = 0;

    public Equipo(String liga, String nombre, String ciudad, String estadio, String entrenador, int titulos) {
        this.liga = liga;
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.estadio = estadio;
        this.entrenador = entrenador;
        this.titulos = titulos;
        this.plantilla = new ArrayList<>();
    }

    public double getMediaEquipo() {
        if (plantilla == null || plantilla.isEmpty()) return 0;
        double suma = 0;
        for (Jugador j : plantilla) {
            suma += j.getMedia();
        }
        return suma / plantilla.size();
    }

    public void registrarResultado(int golesPro, int golesRival) {
        this.jugados++;
        this.golesFavor += golesPro;
        this.golesContra += golesRival;
        
        if (golesPro > golesRival) {
            this.ganados++;
            this.puntos += 3;
        } else if (golesPro == golesRival) {
            this.empatados++;
            this.puntos += 1;
        } else {
            this.perdidos++;
        }
    }

    public String getNombre() { return nombre; }
    
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEntrenador() { return entrenador; }

    public List<Jugador> getPlantilla() { return plantilla; }
    
    public void añadirJugador(Jugador j) { this.plantilla.add(j); }

    public int getPuntos() { return puntos; }
    
    public int getJugados() { return jugados; }
    
    public int getGanados() { return ganados; }
    
    public int getEmpatados() { return empatados; }
    
    public int getPerdidos() { return perdidos; }
    
    public int getGolesFavor() { return golesFavor; }
    
    public int getGolesContra() { return golesContra; }

    public String getNombreLiga() {
        return this.liga;
    }

    public void setNombreLiga(String ligaSeleccionada) {
    }
}