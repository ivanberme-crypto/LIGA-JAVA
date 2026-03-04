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
            boolean plantillaCompleta = equipoPropio.estaCompleta();
            System.out.println("\n\n");
            System.out.println(Decoracion.CIAN + "╔═══════════════════════════════════════════╗" + Decoracion.RESET);
            System.out.println(Decoracion.CIAN + "║" + " ".repeat(ANCHO_INTERIOR) + "║" + Decoracion.RESET);
            System.out.println(Decoracion.CIAN + "║" + Decoracion.AMARILLO + Decoracion.centrar("FUTDRAFT XTART", ANCHO_INTERIOR) + Decoracion.CIAN + "║" + Decoracion.RESET);
            System.out.println(Decoracion.CIAN + "║" + " ".repeat(ANCHO_INTERIOR) + "║" + Decoracion.RESET);
            System.out.println(Decoracion.CIAN + "╠═══════════════════════════════════════════╣" + Decoracion.RESET);
            System.out.println(Decoracion.CIAN + "║" + " ".repeat(ANCHO_INTERIOR) + "║" + Decoracion.RESET);
            System.out.printf(Decoracion.CIAN + "║   " + Decoracion.AMARILLO + "[01]" + Decoracion.RESET + " %-34s " + Decoracion.CIAN + "║%n", "VER LIGAS Y EQUIPOS");
            if (plantillaCompleta) {
                System.out.printf(Decoracion.CIAN + "║   " + Decoracion.AMARILLO + "[02]" + Decoracion.RESET + " %-34s " + Decoracion.CIAN + "║%n", "REHACER FUTDRAFT");
                System.out.printf(Decoracion.CIAN + "║   " + Decoracion.AMARILLO + "[03]" + Decoracion.RESET + " %-34s " + Decoracion.CIAN + "║%n", "CONTINUAR CON EL FUTDRAFT");
            } else {
                System.out.printf(Decoracion.CIAN + "║   " + Decoracion.AMARILLO + "[02]" + Decoracion.RESET + " %-34s " + Decoracion.CIAN + "║%n", "COMENZAR NUEVO FUTDRAFT");
            }
            System.out.println(Decoracion.CIAN + "║" + " ".repeat(ANCHO_INTERIOR) + "║" + Decoracion.RESET);
            System.out.printf(Decoracion.CIAN + "║   " + Decoracion.ROJO + "[00]" + Decoracion.RESET + " %-34s " + Decoracion.CIAN + "║%n", "SALIR DE LA APLICACIÓN");
            System.out.println(Decoracion.CIAN + "║" + " ".repeat(ANCHO_INTERIOR) + "║" + Decoracion.RESET);

            System.out.println(Decoracion.CIAN + "╚═══════════════════════════════════════════╝" + Decoracion.RESET);

            try {
                opcionPrincipal = Decoracion.leerEntero(sc,Decoracion.AMARILLO + "-> SELECCIONA UNA OPCION: " + Decoracion.RESET, 0, 3);
                switch (opcionPrincipal) {
                    case 1:
                        alLiga.mostrarSubMenuLiga(sc);
                        break;

                    case 2:
                        if (plantillaCompleta) {
                            System.out.print(Decoracion.ROJO + "¿Seguro que quieres borrar tu equipo actual? (S/N): " + Decoracion.RESET);
                            if (sc.nextLine().equalsIgnoreCase("S")) {
                                this.equipoPropio = new EquipoPropio("", "", "", 0);
                                alLiga.setEquipoPropio(this.equipoPropio);
                                alLiga.gestionarSeleccionLiga(sc);
                            }
                        } else {
                            alLiga.gestionarSeleccionLiga(sc);
                        }
                        break;

                    case 3:
                        if (plantillaCompleta) {
                            if (alLiga.getLigaEnCurso() != null) {
                                alLiga.getLigaEnCurso().menuFutDraft(sc, equipoPropio);
                            } else {
                                System.out.println(Decoracion.ROJO + "Error: No hay una liga activa." + Decoracion.RESET);
                            }
                        } else {
                            System.out.println(Decoracion.ROJO + "Opción bloqueada: Primero debes completar tu equipo." + Decoracion.RESET);
                        }
                        break;

                    case 0:
                        System.out.println(Decoracion.VERDE + "¡Hasta la próxima!" + Decoracion.RESET);
                        break;

                    default:
                        System.out.println(Decoracion.ROJO + "Opción no válida." + Decoracion.RESET);
                        break;
                }
            } catch (Exception e) {
                System.out.println(Decoracion.ROJO + "Error: Entrada no válida." + Decoracion.RESET);
                sc.nextLine();
                opcionPrincipal = -1;
            }
        } while (opcionPrincipal != 0);
    }
}
