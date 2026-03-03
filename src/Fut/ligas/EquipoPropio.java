package Fut.ligas;

import Fut.personas.AlmacenJugador;
import Fut.personas.Jugador;
import Fut.enumLiga.Posicion;

import java.util.*;
import java.util.stream.Collectors;

public class EquipoPropio extends Equipo {

    private static final int OPCIONES_DRAFT = 4;
    private final Set<Jugador> jugadoresElegidos = new HashSet<>();
    private String nombre;
    private String entrenador;
    private int titulos;
    private final List<Jugador> plantilla;

    public EquipoPropio(String nombre, String entrenador, int titulos) {
        super(nombre, "", "", entrenador, titulos);
        this.nombre = nombre;
        this.entrenador = entrenador;
        this.titulos = titulos;
        this.plantilla = new ArrayList<>();
    }

    public void iniciarFutDraft(AlmacenJugador almacen, Scanner sc) {

        System.out.println("¡Bienvenido al FutDraft!");

        System.out.print("Ingresa tu nombre de entrenador: ");
        this.entrenador = sc.nextLine();

        System.out.println("¡Hola " + entrenador + "! Comencemos a construir tu equipo.");

        System.out.print("Selecciona un nombre para tu equipo: ");
        this.nombre = sc.nextLine();

        System.out.println("¡Perfecto! Ahora comienza a formar tu plantilla.");

        Posicion[] esquema = {
                Posicion.PORTERO,
                Posicion.LATERAL_IZQUIERDO, Posicion.DEFENSA_CENTRAL, Posicion.DEFENSA_CENTRAL, Posicion.LATERAL_DERECHO,
                Posicion.MEDIOCENTRO, Posicion.MEDIOCENTRO, Posicion.MEDIOCENTRO_OFENSIVO,
                Posicion.EXTREMO_IZQUIERDO, Posicion.EXTREMO_DERECHO, Posicion.DELANTERO_CENTRAL
        };

        System.out.println("\n--- INICIANDO DRAFT PARA " + entrenador.toUpperCase() + " ---");

        for (Posicion pos : esquema) {
            seleccionarJugadorParaPosicion(almacen, sc, pos);
        }
    }

    private void seleccionarJugadorParaPosicion(AlmacenJugador almacen, Scanner sc, Posicion pos) {

        System.out.println("\nSeleccionando para la posición: " + pos);

        List<Jugador> opciones = generarOpciones(almacen, pos);

        for (int i = 0; i < opciones.size(); i++) {
            Jugador j = opciones.get(i);
            System.out.printf("%d. %s (Media: %.1f)%n", i + 1, j.getNombre(), (double) j.getMedia());
        }

        int seleccion = leerEntero(sc, 1, opciones.size());
        Jugador elegido = opciones.get(seleccion - 1);
        plantilla.add(elegido);
        jugadoresElegidos.add(elegido);
    }

    private int leerEntero(Scanner sc, int min, int max) {
        int valor = -1;
        while (valor < min || valor > max) {
            System.out.printf("Elige tu jugador (%d-%d): ", min, max);
            if (sc.hasNextInt()) {
                valor = sc.nextInt();
            } else {
                System.out.println("Entrada inválida.");
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
                .collect(Collectors.toList());

        Collections.shuffle(candidatos);

        return candidatos.stream()
                .limit(OPCIONES_DRAFT)
                .collect(Collectors.toList());
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

    public String getNombre() { return nombre; }
    public String getEntrenador() { return entrenador; }
    public int getTitulos() { return titulos; }
    public void setTitulos(int titulos) { this.titulos = titulos; }
}