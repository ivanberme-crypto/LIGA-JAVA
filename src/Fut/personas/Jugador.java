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

    public Jugador(String nombre, int edad, Posicion posicion, int media, double probGol, String equipo) {
        super(nombre, edad);
        this.posicion = posicion;
        this.media = media;
        this.probGol = probGol;
        this.equipo = equipo;
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
    }

    public int getMedia() {
        return media;
    }

    public void setMedia(int media) {
        this.media = media;
    }

    public double getProbGol() {
        return probGol;
    }

    public void setProbGol(double probGol) {
        this.probGol = probGol;
    }

    public int getAmarillas() {
        return amarillas;
    }

    public void setAmarillas(int amarillas) {
        this.amarillas = amarillas;
    }

    public int getRojas() {
        return rojas;
    }

    public void setRojas(int rojas) {
        this.rojas = rojas;
    }

    public int getGoles() {
        return goles;
    }

    public void setGoles(int goles) {
        this.goles = goles;
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public void marcarGol(){ goles++; }
    public void amarilla(){ amarillas++; }
    public void roja(){ rojas++; }

    @Override
    public String toString(){
        return getNombre() + " | " + posicion + " | " + equipo +
                " | Media: " + media +
                " | Goles: " + goles;
    }

}
