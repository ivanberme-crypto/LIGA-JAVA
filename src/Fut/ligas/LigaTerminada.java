package Fut.ligas;

import Fut.equipos.Equipo;
import Fut.personas.Jugador;
import Fut.Decoracion;

import java.util.List;
import java.util.Scanner;

public class LigaTerminada {

    private String nombreLiga;
    private Equipo campeon;
    private List<Equipo> clasificacionFinal;
    private List<Jugador> goleadores;

    public LigaTerminada(String nombreLiga, Equipo campeon, List<Equipo> clasificacion, List<Jugador> goleadores) {
        this.nombreLiga = nombreLiga;
        this.campeon = campeon;
        this.clasificacionFinal = clasificacion;
        this.goleadores = goleadores;
    }

    public void mostrarMenuFinal(Scanner sc) {

        int opcion;
        do {
            System.out.println("\n################################################");
            System.out.println("#              RESUMEN DE TEMPORADA            #");
            System.out.println("################################################");
            System.out.println("  [1] Cuadro de Honor (Campeón)");
            System.out.println("  [2] Clasificación Final Detallada");
            System.out.println("  [3] Tabla de Goleadores (Pichichi)");
            System.out.println("  [0] Salir y cerrar programa");
            System.out.println("------------------------------------------------");
            System.out.print("-> Selección: ");

            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    System.out.println("\n\t+---------------------------------------+");
                    System.out.println("\t|           CAMPEÓN OFICIAL             |");
                    System.out.println("\t+---------------------------------------+");
                    System.out.println("\t  FELICITACIONES A: " + campeon.getNombre().toUpperCase());
                    System.out.println("\t  Puntos: " + campeon.getPuntos() + " | GF: " + campeon.getGolesFavor());
                    System.out.println("\t+---------------------------------------+");
                    break;
                case 2:
                    System.out.println("\n====================================================");
                    System.out.printf("%-5s | %-25s | %-5s%n", "POS", "EQUIPO", "PTS");
                    System.out.println("====================================================");
                    for (int i = 0; i < clasificacionFinal.size(); i++) {
                        Equipo e = clasificacionFinal.get(i);
                        System.out.printf("[%02d]  | %-25s | %3d pts%n", (i + 1), e.getNombre(), e.getPuntos());
                    }
                    System.out.println("====================================================");
                    break;
                case 3:
                    System.out.println("\n--- PICHICHI ---");
                    goleadores.stream().limit(5).forEach(j ->
                            System.out.println(j.getNombre() + " (" + j.getEquipo() + ") - " + j.getGoles() + " goles")
                    );
                    break;
                case 0:
                    System.out.println("Cerrando el resumen de la temporada... ¡Gracias por jugar!");
                    break;

                default:
                    System.out.println("Opción no válida.");
                    break;
            }

        } while (opcion != 0);

        System.out.println("Cerrando FutDraft...");
    }
}