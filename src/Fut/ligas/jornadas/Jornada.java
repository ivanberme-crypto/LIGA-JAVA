package Fut.ligas.jornadas;

import java.util.ArrayList;
import java.util.List;

public class Jornada {
    private List<Partido> partidos;

    public Jornada() {
        this.partidos = new ArrayList<>();
    }

    public void agregarPartido(Partido p) {
        partidos.add(p);
    }

    public void mostrarPartidos() {
        for (Partido p : partidos) {
            System.out.println(p.getEquipoLocal().getNombre() + "   VS   " + p.getEquipoVisitante().getNombre());
        }
    }

    public void simular(int numeroJornada) {
        System.out.println("\n------------------------------------------------------------");
        System.out.println("             RESULTADOS: JORNADA NÚMERO " + String.format("%02d", numeroJornada));
        System.out.println("------------------------------------------------------------");

        for (Partido p : partidos) {
            p.jugarPartido();

            p.getEquipoLocal().registrarResultado(p.getGolesLocal(), p.getGolesVisitante());
            p.getEquipoVisitante().registrarResultado(p.getGolesVisitante(), p.getGolesLocal());

            System.out.printf("%20s   [ %d - %d ]   %-20s%n",
                    p.getEquipoLocal().getNombre(),
                    p.getGolesLocal(),
                    p.getGolesVisitante(),
                    p.getEquipoVisitante().getNombre());
        }
        System.out.println("------------------------------------------------------------");
    }

    public List<Partido> getPartidos() {
        return partidos;
    }
}