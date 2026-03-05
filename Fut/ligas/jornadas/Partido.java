package Fut.ligas.jornadas;

import Fut.equipos.Equipo;
import Fut.personas.Jugador;
import java.util.List;
import java.util.Random;

public class Partido {
    private Equipo equipoLocal;
    private Equipo equipoVisitante;
    private int golesLocal;
    private int golesVisitante;
    private static final Random rand = new Random();

    public Partido(Equipo equipoLocal, Equipo equipoVisitante) {
        this.equipoLocal = equipoLocal;
        this.equipoVisitante = equipoVisitante;
        this.golesLocal = 0;
        this.golesVisitante = 0;
    }

    public void jugarPartido() {
        double intensidad = 2.5;
        double MEDIA_REFERENCIA = 90.0;

        double probLocal = equipoLocal.getPlantilla().stream()
                .mapToDouble(j -> j.getProbGol() * (j.getMedia() / MEDIA_REFERENCIA))
                .sum() * 1.05;

        double probVisitante = equipoVisitante.getPlantilla().stream()
                .mapToDouble(j -> j.getProbGol() * (j.getMedia() / MEDIA_REFERENCIA))
                .sum();

        for (int minuto = 1; minuto <= 90; minuto++) {
            double probGolEnMinuto = ((probLocal + probVisitante) * intensidad) / 90.0;

            if (rand.nextDouble() < probGolEnMinuto) {
                double margenVictoria = probLocal / (probLocal + probVisitante);

                if (rand.nextDouble() < margenVictoria) {
                    marcarGol(equipoLocal, true);
                } else {
                    marcarGol(equipoVisitante, false);
                }
            }
        }
    }

    private void marcarGol(Equipo equipo, boolean esLocal) {
        Jugador marcador = seleccionarJugador(equipo);
        if (marcador != null) {
            marcador.marcarGol();
        }
        if (esLocal) golesLocal++; else golesVisitante++;
    }

    private Jugador seleccionarJugador(Equipo equipo) {
        List<Jugador> plantilla = equipo.getPlantilla();
        if (plantilla == null || plantilla.isEmpty()) return null;

        double MEDIA_REFERENCIA = 90.0;
        
        double pesoTotal = plantilla.stream().mapToDouble(j -> j.getProbGol() * (j.getMedia() / MEDIA_REFERENCIA))
                .sum();
        if (pesoTotal <= 0) return plantilla.get(rand.nextInt(plantilla.size()));

        double valorAleatorio = rand.nextDouble() * pesoTotal;
        double sumaAcumulada = 0;

        for (Jugador j : plantilla) {
            double pesoJugador = j.getProbGol() * (j.getMedia() / MEDIA_REFERENCIA);
            sumaAcumulada += pesoJugador;
            if (valorAleatorio <= sumaAcumulada) {
                return j;
            }
        }
        return plantilla.get(0);
    }

    public int getGolesLocal() {
        return golesLocal;
    }

    public int getGolesVisitante() {
        return golesVisitante;
    }

    public Equipo getEquipoLocal() {
        return equipoLocal;
    }

    public Equipo getEquipoVisitante() {
        return equipoVisitante;
    }
}