package Fut;

import Fut.equipos.AlmacenEquipo;
import Fut.equipos.EquipoPropio;
import Fut.ligas.AlmacenLiga;
import Fut.personas.AlmacenJugador;
import java.util.Scanner;

public class Menu {

    private Scanner sc;
    private String ruta = "src/Fut/almacen/Almacen.txt";
    private AlmacenEquipo alEquipo;
    private AlmacenJugador alJugador;
    private EquipoPropio equipoPropio;
    private AlmacenLiga alLiga;

    public Menu() {
        this.sc = new Scanner(System.in);
        this.alEquipo = new AlmacenEquipo();
        this.alJugador = new AlmacenJugador();
        this.equipoPropio = new EquipoPropio("", "", "", 0);

        alJugador.cargarDesdeTxt(ruta);
        AlmacenEquipo.vincularJugadoresAEquipos(alEquipo, alJugador);
        this.alLiga = new AlmacenLiga(alEquipo, alJugador, equipoPropio);
    }

    public void ejecutarMenu() {
        int opcionPrincipal;
        int ANCHO_INTERIOR = 43;

        do {
            System.out.println("\n\n");
            System.out.println(Decoracion.CIAN + "╔═══════════════════════════════════════════╗" + Decoracion.RESET);
            System.out.println(Decoracion.CIAN + "║" + " ".repeat(ANCHO_INTERIOR) + "║" + Decoracion.RESET);

            System.out.println(Decoracion.CIAN + "║" + Decoracion.AMARILLO + Decoracion.centrar("FUTDRAFT XTART", ANCHO_INTERIOR) + Decoracion.CIAN + "║" + Decoracion.RESET);

            System.out.println(Decoracion.CIAN + "║" + " ".repeat(ANCHO_INTERIOR) + "║" + Decoracion.RESET);
            System.out.println(Decoracion.CIAN + "╠═══════════════════════════════════════════╣" + Decoracion.RESET);
            System.out.println(Decoracion.CIAN + "║" + " ".repeat(ANCHO_INTERIOR) + "║" + Decoracion.RESET);

            System.out.printf(Decoracion.CIAN + "║   " + Decoracion.AMARILLO + "1." + Decoracion.RESET + " %-36s " + Decoracion.CIAN + "║%n", "VER LIGAS Y EQUIPOS");
            System.out.printf(Decoracion.CIAN + "║   " + Decoracion.AMARILLO + "2." + Decoracion.RESET + " %-36s " + Decoracion.CIAN + "║%n", "COMENZAR NUEVO FUTDRAFT");
            System.out.println(Decoracion.CIAN + "║" + " ".repeat(ANCHO_INTERIOR) + "║" + Decoracion.RESET);
            System.out.printf(Decoracion.CIAN + "║   " + Decoracion.ROJO + "0." + Decoracion.RESET + " %-36s " + Decoracion.CIAN + "║%n", "SALIR DE LA APLICACIÓN");
            System.out.println(Decoracion.CIAN + "║" + " ".repeat(ANCHO_INTERIOR) + "║" + Decoracion.RESET);

            System.out.println(Decoracion.CIAN + "╚═══════════════════════════════════════════╝" + Decoracion.RESET);
            System.out.print(Decoracion.AMARILLO + " -> SELECCIONA UNA OPCION: " + Decoracion.RESET);

            try {
                opcionPrincipal = sc.nextInt();
                sc.nextLine();
                switch (opcionPrincipal) {
                    case 1 -> alLiga.mostrarSubMenuLiga(sc);
                    case 2 -> alLiga.gestionarSeleccionLiga(sc);
                    case 0 -> System.out.println(Decoracion.VERDE + "¡Gracias por usar FUTDRAFT XTART! ¡Hasta la próxima!" + Decoracion.RESET);
                    default -> {
                        System.out.println(Decoracion.ROJO + "Opción no válida. Por favor, selecciona una opción del menú." + Decoracion.RESET);
                        opcionPrincipal = -1;
                    }
                }
            } catch (Exception e) {
                System.out.println(Decoracion.ROJO + "Error: Entrada no válida." + Decoracion.RESET);
                sc.nextLine();
                opcionPrincipal = -1;
            }
        } while (opcionPrincipal != 0);
    }
}
