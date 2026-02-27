package Fut.enumLiga;

public class Posicion {

    private String posicion;

    public static final Posicion PORTERO = new Posicion("GK");
    public static final Posicion LATERAL_IZQUIERDO = new Posicion("LB");
    public static final Posicion LATERAL_DERECHO = new Posicion("RB");
    public static final Posicion DEFENSA_CENTRAL = new Posicion("CB");
    public static final Posicion MEDIOCENTRO = new Posicion("CM");
    public static final Posicion MEDIOCENTRO_DEFENSIVO = new Posicion("CDM");
    public static final Posicion MEDIOCENTRO_OFENSIVO = new Posicion("CAM");
    public static final Posicion EXTREMO_IZQUIERDO = new Posicion("LW");
    public static final Posicion EXTREMO_DERECHO = new Posicion("RW");
    public static final Posicion DELANTERO_CENTRAL = new Posicion("ST");

    public static final Posicion[] POSICIONES = {
            PORTERO, LATERAL_IZQUIERDO, LATERAL_DERECHO, DEFENSA_CENTRAL,
            MEDIOCENTRO, MEDIOCENTRO_DEFENSIVO, MEDIOCENTRO_OFENSIVO,
            EXTREMO_IZQUIERDO, EXTREMO_DERECHO, DELANTERO_CENTRAL
    };

    private Posicion(String posicion){
        this.posicion = posicion;
    }

    public static Posicion valueOf(String nombrePosicion) {
        for (Posicion p : POSICIONES) {
            if (p.posicion.equals(nombrePosicion)) {
                return p;
            }
        }
        switch (nombrePosicion) {
            case "PORTERO":
                return PORTERO;
            case "LATERAL_IZQUIERDO":
                return LATERAL_IZQUIERDO;
            case "LATERAL_DERECHO":
                return LATERAL_DERECHO;
            case "DEFENSA_CENTRAL":
            case "DEFENSA":
                return DEFENSA_CENTRAL;
            case "MEDIOCENTRO":
            case "CENTROCAMPISTA":
                return MEDIOCENTRO;
            case "MEDIOCENTRO_DEFENSIVO":
                return MEDIOCENTRO_DEFENSIVO;
            case "MEDIOCENTRO_OFENSIVO":
                return MEDIOCENTRO_OFENSIVO;
            case "EXTREMO_IZQUIERDO":
            case "DELANTERO":
                return EXTREMO_IZQUIERDO;
            case "EXTREMO_DERECHO":
                return EXTREMO_DERECHO;
            case "DELANTERO_CENTRAL":
                return DELANTERO_CENTRAL;
        }
        throw new IllegalArgumentException("Posición no válida: " + nombrePosicion);
    }

    public String getNombre(){
        return posicion;
    }

    @Override
    public String toString() {
        return posicion;
    }
}
