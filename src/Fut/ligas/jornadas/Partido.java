package Fut.ligas.jornadas;

import Fut.Decoracion;
import Fut.equipos.Equipo;
import Fut.personas.Jugador;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public final class Partido {
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
            if (rand.nextDouble() < 0.005) {
                Equipo equipoSancionado = (rand.nextBoolean()) ? equipoLocal : equipoVisitante;
                Jugador amonestado = seleccionarJugador(equipoSancionado);

                if (amonestado != null) {
                    if (rand.nextDouble() < 0.10) {
                        amonestado.roja();
                        System.out.println(Decoracion.B_ROJO + "TARJETA ROJA: " + Decoracion.RESET +
                                amonestado.getNombre() + " (" + equipoSancionado.getNombre() + ") en el min " + minuto);
                    } else {
                        amonestado.amarilla();
                        System.out.print(Decoracion.B_AMARILLO + "TARJETA AMARILLA: " + Decoracion.RESET +
                                amonestado.getNombre() + " (" + equipoSancionado.getNombre() + ") min " + minuto);
                        if (amonestado.isSancionado()) {
                            System.out.println(Decoracion.ROJO + " ->TARJETA ROJA POR DOBLE AMARILLA" + Decoracion.RESET);
                        } else {
                            System.out.println();
                        }
                    }
                }
            }
        }
        Decoracion.pausa(100);
        simularTarjetas(equipoLocal);
        simularTarjetas(equipoVisitante);
    }

    private void marcarGol(Equipo equipo, boolean esLocal) {
        Jugador marcador = seleccionarJugador(equipo);
        if (marcador != null) {
            marcador.marcarGol();
        }
        if (esLocal) golesLocal++; else golesVisitante++;
    }

    private Jugador seleccionarJugador(Equipo equipo) {
        List<Jugador> disponibles = equipo.getPlantilla().stream()
                .filter(j -> !j.isSancionado())
                .collect(Collectors.toList());

        if (disponibles.isEmpty()) return null;

        double MEDIA_REFERENCIA = 90.0;
        
        double pesoTotal = disponibles.stream().mapToDouble(j -> j.getProbGol() * (j.getMedia() / MEDIA_REFERENCIA))
                .sum();
        if (pesoTotal <= 0) return disponibles.get(rand.nextInt(disponibles.size()));

        double valorAleatorio = rand.nextDouble() * pesoTotal;
        double sumaAcumulada = 0;

        for (Jugador j : disponibles) {
            double pesoJugador = j.getProbGol() * (j.getMedia() / MEDIA_REFERENCIA);
            sumaAcumulada += pesoJugador;
            if (valorAleatorio <= sumaAcumulada) {
                return j;
            }
        }
        return disponibles.get(0);
    }
    private void simularTarjetas(Equipo equipo) {
        for (Jugador j : equipo.getPlantilla()) {
            if (!j.isSancionado()) {
                double azar = rand.nextDouble();
                if (azar < 0.05) {
                    j.amarilla();
                } else if (azar < 0.005) {
                    j.roja();
                }
            }
        }
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