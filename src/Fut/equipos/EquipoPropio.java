package Fut.equipos;

import Fut.Decoracion;
import Fut.personas.AlmacenJugador;
import Fut.personas.Jugador;
import Fut.enumLiga.Posicion;

import java.util.*;
import java.util.stream.Collectors;

public class EquipoPropio extends Equipo {

    private static final int OPCIONES_DRAFT = 7;
    private final Set<Jugador> jugadoresElegidos = new HashSet<>();
    private String nombre;
    private String entrenador;
    private int titulos;
    private final List<Jugador> plantilla;

    public EquipoPropio(String liga, String nombre, String entrenador, int titulos) {
        super(liga, nombre, "", "", entrenador, titulos);
        this.nombre = nombre;
        this.entrenador = entrenador;
        this.titulos = titulos;
        this.plantilla = new ArrayList<>();
    }

    public void iniciarFutDraft(AlmacenJugador almacen, Scanner sc) {

        System.out.println("\n" + Decoracion.B_CIAN + "╔" + "═".repeat(50) + "╗");
        System.out.println("║" + Decoracion.B_AMARILLO + Decoracion.centrar(" BIENVENIDO AL FUT DRAFT ", 50) + Decoracion.B_CIAN + "║");
        System.out.println("╚" + "═".repeat(50) + "╝" + Decoracion.RESET);

        System.out.print(Decoracion.B_BLANCO + " -> Ingresa tu nombre de entrenador: " + Decoracion.RESET);
        this.entrenador = sc.nextLine();

        System.out.println("\n" + Decoracion.B_VERDE + " ¡Hola, Míster " + entrenador.toUpperCase() + "!" + Decoracion.RESET);
        System.out.print(Decoracion.B_BLANCO + " -> Selecciona un nombre para tu nuevo club: " + Decoracion.RESET);
        this.nombre = sc.nextLine();
        super.setNombre(this.nombre);

        System.out.println("\n" + Decoracion.B_AMARILLO + " Preparando el terreno de juego... Comienza el Draft." + Decoracion.RESET);
        Posicion[] esquema = {
                Posicion.PORTERO,
                Posicion.LATERAL_IZQUIERDO, Posicion.DEFENSA_CENTRAL, Posicion.DEFENSA_CENTRAL, Posicion.LATERAL_DERECHO,
                Posicion.MEDIOCENTRO, Posicion.MEDIOCENTRO, Posicion.MEDIOCENTRO_OFENSIVO,
                Posicion.EXTREMO_IZQUIERDO, Posicion.EXTREMO_DERECHO, Posicion.DELANTERO_CENTRAL
        };

        System.out.println("\n" + Decoracion.B_BLANCO + " FUTDRAFT REALIZADO CON ÉXITO " + Decoracion.RESET);
        System.out.printf(Decoracion.B_CIAN + " Equipo: %s | Media Total: " + Decoracion.B_AMARILLO + "%.1f%n" + Decoracion.RESET,
                this.nombre, getMediaEquipo());

        for (Posicion pos : esquema) {
            seleccionarJugadorParaPosicion(almacen, sc, pos);
        }
    }

    private void seleccionarJugadorParaPosicion(AlmacenJugador almacen, Scanner sc, Posicion pos) {
        int ANCHO = 45;
        System.out.println("\n" + Decoracion.B_BLANCO + "┌" + "─".repeat(ANCHO) + "┐");
        System.out.println("│ " + Decoracion.B_PURPURA + Decoracion.centrar("BUSCANDO: " + pos,43) + Decoracion.B_BLANCO + " │");
        System.out.println("├" + "─".repeat(ANCHO) + "┤");

        List<Jugador> opciones = generarOpciones(almacen, pos);

        for (int i = 0; i < opciones.size(); i++) {
            Jugador j = opciones.get(i);
            String colorMedia = (j.getMedia() >= 90) ? Decoracion.B_VERDE :
                    (j.getMedia() >= 80) ? Decoracion.B_AMARILLO :
                            (j.getMedia() >= 70) ? Decoracion.B_ROJO : Decoracion.BLANCO;
            System.out.printf(Decoracion.B_BLANCO + "│ " + Decoracion.B_CIAN + "[%d]" + Decoracion.RESET +
                            " %-27s " + Decoracion.B_BLANCO + "│" + colorMedia + " MEDIA: %d " + Decoracion.B_BLANCO + "│%n",
                    i + 1, j.getNombre(), j.getMedia());
        }
        System.out.println(Decoracion.B_BLANCO + "└" + "─".repeat(ANCHO) + "┘" + Decoracion.RESET);

        int seleccion = leerEntero(sc, 1, opciones.size());
        Jugador elegido = opciones.get(seleccion - 1);

        System.out.println(Decoracion.VERDE + " Has fichado a " + elegido.getNombre() + Decoracion.RESET);
        Jugador copia = elegido.clonar();
        copia.setEquipo(this.nombre);
        plantilla.add(copia);
        super.añadirJugador(copia);
        jugadoresElegidos.add(elegido);
    }

    private int leerEntero(Scanner sc, int min, int max) {
        int valor = -1;
        while (valor < min || valor > max) {
            System.out.print(Decoracion.B_AMARILLO + " -> Elige tu jugador (" + min + "-" + max + "): " + Decoracion.RESET);
            if (sc.hasNextInt()) {
                valor = sc.nextInt();
                if (valor < min || valor > max) {
                    System.out.println(Decoracion.ROJO + " Opción fuera del rango." + Decoracion.RESET);
                }
            } else {
                System.out.println(Decoracion.ROJO + " Por favor, introduce un número válido." + Decoracion.RESET);
                sc.next();
            }
        }
        sc.nextLine();
        return valor;
    }

    private List<Jugador> generarOpciones(AlmacenJugador almacen, Posicion pos) {

        List<Jugador> todos = new ArrayList<>();
        todos.addAll(almacen.getPorteros());
        todos.addAll(almacen.getDefensas());
        todos.addAll(almacen.getMedios());
        todos.addAll(almacen.getDelanteros());

        List<Jugador> candidatos = todos.stream()
                .filter(j -> j.getPosicion() == pos)
                .filter(j -> !jugadoresElegidos.contains(j))
                .distinct()
                .collect(Collectors.toList());

        Collections.shuffle(candidatos);

        return candidatos.stream()
                .limit(OPCIONES_DRAFT)
                .collect(Collectors.toList());
    }

    public boolean estaCompleta() {
        return this.plantilla.size() == 11;
    }

    public double getMediaEquipo() {
        return plantilla.stream()
                .mapToDouble(Jugador::getMedia)
                .average()
                .orElse(0);
    }

    public List<Jugador> getPlantilla() {
        return Collections.unmodifiableList(plantilla);
    }

    public String getNombre() {
        return nombre;
    }

    public String getEntrenador() {
        return entrenador;
    }
}