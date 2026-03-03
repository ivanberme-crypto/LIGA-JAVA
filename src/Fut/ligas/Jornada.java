package Fut.ligas;

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

    public void simular() {
        System.out.println("\n--- RESULTADOS DE LA JORNADA ---");
        for (Partido p : partidos) {
            p.jugarPartido();

            p.getEquipoLocal().registrarResultado(p.getGolesLocal(), p.getGolesVisitante());
            p.getEquipoVisitante().registrarResultado(p.getGolesVisitante(), p.getGolesLocal());

            System.out.printf("%s %d - %d %s%n",
                    p.getEquipoLocal().getNombre(), p.getGolesLocal(),
                    p.getGolesVisitante(), p.getEquipoVisitante().getNombre());
        }
    }
}