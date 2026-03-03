package Fut;

import Fut.ligas.AlmacenEquipo;
import Fut.ligas.EquipoPropio;
import Fut.ligas.Jornada;
import Fut.ligas.LaLiga;
import Fut.personas.AlmacenJugador;

import java.util.List;
import java.util.Scanner;

public class Menu {

    private Scanner sc;
    private String ruta = "src/Fut/almacen/Almacen.txt";
    private AlmacenEquipo alEquipo;
    private AlmacenJugador alJugador;
    private EquipoPropio equipoPropio;
    private LaLiga liga;

    public Menu() {
        this.sc = new Scanner(System.in);
        this.alEquipo = new AlmacenEquipo();
        this.alJugador = new AlmacenJugador();
        this.equipoPropio = new EquipoPropio("", "", 0);


        alJugador.cargarDesdeTxt(ruta);
        AlmacenEquipo.vincularJugadoresAEquipos(alEquipo, alJugador);
    }

    public void ejecutarMenu() {
        int opcionPrincipal;

        do {
            System.out.println("\n========== MENU PRINCIPAL ==========");
            System.out.println("1. Ver equipos");
            System.out.println("2. Comenzar FutDraft");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            opcionPrincipal = sc.nextInt();
            sc.nextLine();

            if (opcionPrincipal == 1) {
                alEquipo.mostrarSubmenuEquipos(alEquipo, sc);
            }
            if (opcionPrincipal == 2) {
                System.out.println("Iniciando FutDraft...");
                this.equipoPropio.iniciarFutDraft(alJugador, sc);

                if (!alEquipo.getEquipos().contains(equipoPropio)) {
                    alEquipo.getEquipos().add(equipoPropio);
                }
                List<Jornada> jornadas = LaLiga.generarCalendario(alEquipo.getEquipos());
                this.liga = new LaLiga(alEquipo.getEquipos(), jornadas);
                liga.menuLiga(sc, equipoPropio);
            }
        } while (opcionPrincipal != 0);

        System.out.println("¡Gracias por jugar!");
        sc.close();
    }

}