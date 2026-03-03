package Fut.ligas;

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

    /**
     * Calculate the average goal probability from all players in the team
     */
    private double getProbabilidadGolEquipo(Equipo equipo) {
        List<Jugador> plantilla = equipo.getPlantilla();
        if (plantilla == null || plantilla.isEmpty()) {
            return 0.1; // Default low probability if no players
        }
        double sumaProbGol = 0;
        for (Jugador j : plantilla) {
            sumaProbGol += j.getProbGol();
        }
        return sumaProbGol / plantilla.size();
    }

    /**
     * Select a scorer based on each player's probGol weight
     */
    private Jugador seleccionarJugador(Equipo equipo) {
        List<Jugador> plantilla = equipo.getPlantilla();
        if (plantilla == null || plantilla.isEmpty()) {
            return null;
        }
        
        // Calculate total weight (sum of all probGol)
        double pesoTotal = 0;
        for (Jugador j : plantilla) {
            pesoTotal += j.getProbGol();
        }
        
        // Random selection based on weights
        double randomValue = rand.nextDouble() * pesoTotal;
        double acumulado = 0;
        
        for (Jugador j : plantilla) {
            acumulado += j.getProbGol();
            if (randomValue <= acumulado) {
                return j;
            }
        }
        
        // Fallback to last player
        return plantilla.get(plantilla.size() - 1);
    }

    public void jugarPartido() {
        double mediaLocal = equipoLocal.getMediaEquipo();
        double mediaVisitante = equipoVisitante.getMediaEquipo();
        
        // Get average goal probability from players
        double probGolLocal = getProbabilidadGolEquipo(equipoLocal);
        double probGolVisitante = getProbabilidadGolEquipo(equipoVisitante);
        
        // Simulate 90 minutes
        for (int minuto = 1; minuto <= 90; minuto++) {
            // Calculate goal probability for this minute
            // Combine team media (normalized) with player goal probabilities
            // Very low base probability to simulate realistic match (avg 2-3 goals per match)
            double probMinutoLocal = ((mediaLocal / 100.0) * 0.4 + probGolLocal * 0.6) * 0.03;
            double probMinutoVisitante = ((mediaVisitante / 100.0) * 0.4 + probGolVisitante * 0.6) * 0.03;
            
            // Small chance for either team to score this minute
            if (rand.nextDouble() < probMinutoLocal) {
                // Local team scores
                Jugador marcador = seleccionarJugador(equipoLocal);
                if (marcador != null) {
                    marcador.marcarGol();
                }
                golesLocal++;
            }
            
            if (rand.nextDouble() < probMinutoVisitante) {
                // Visitor team scores
                Jugador marcador = seleccionarJugador(equipoVisitante);
                if (marcador != null) {
                    marcador.marcarGol();
                }
                golesVisitante++;
            }
        }
    }

    public Equipo getEquipoLocal() {
        return equipoLocal;
    }

    public Equipo getEquipoVisitante() {
        return equipoVisitante;
    }

    public int getGolesLocal() {
        return golesLocal;
    }

    public int getGolesVisitante() {
        return golesVisitante;
    }
}