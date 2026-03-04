package Fut.personas;

import Fut.enumLiga.Posicion;

public class Jugador extends Persona {

    private Posicion posicion;
    private int media;
    private double probGol;
    private int amarillas;
    private int rojas;
    private int goles;
    private String equipo;
    private boolean sancionado = false;
    private static final int LIMITE_AMARILLAS = 5;

    public Jugador(String nombre, int edad, Posicion posicion, int media, double probGol, String equipo) {
        super(nombre, edad);
        this.posicion = posicion;
        this.media = media;
        this.probGol = probGol;
        this.equipo = equipo;
    }

    public void amarilla() {
        this.amarillas++;
        if (this.amarillas >= LIMITE_AMARILLAS) {
            this.sancionado = true;
        }
    }

    public void roja() {
        this.rojas++;
        this.sancionado = true;
    }
    public boolean isSancionado() {
        return sancionado;
    }

    public void cumplirSancion() {
        this.sancionado = false;
        this.amarillas = 0;
    }

    public void marcarGol(){ goles++; }

    public Jugador clonar() {
        Jugador copia = new Jugador(this.getNombre(), this.getEdad(), this.posicion, this.media, this.probGol, this.equipo);
        return copia;
    }

    public Posicion getPosicion() { return posicion; }
    public int getMedia() { return media; }
    public double getProbGol() { return probGol; }
    public int getAmarillas() { return amarillas; }
    public int getGoles() { return goles; }
    public String getEquipo() { return equipo; }
    public void setEquipo(String equipo) { this.equipo = equipo; }

    @Override
    public String toString(){
        return getNombre() + " | " + posicion + " | " + equipo +
                " | Media: " + media +
                " | Goles: " + goles +
                (sancionado ? " [SANCIONADO]" : "");
    }

}
