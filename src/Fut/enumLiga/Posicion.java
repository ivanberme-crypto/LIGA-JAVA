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
            if (p == PORTERO && nombrePosicion.equals("PORTERO")) return p;
            if (p == LATERAL_IZQUIERDO && nombrePosicion.equals("LATERAL_IZQUIERDO")) return p;
            if (p == LATERAL_DERECHO && nombrePosicion.equals("LATERAL_DERECHO")) return p;
            if (p == DEFENSA_CENTRAL && (nombrePosicion.equals("DEFENSA_CENTRAL") || nombrePosicion.equals("DEFENSA"))) return p;
            if (p == MEDIOCENTRO && (nombrePosicion.equals("MEDIOCENTRO") || nombrePosicion.equals("CENTROCAMPISTA"))) return p;
            if (p == MEDIOCENTRO_DEFENSIVO && (nombrePosicion.equals("MEDIOCENTRO_DEFENSIVO") || nombrePosicion.equals("CENTROCAMPISTA"))) return p;
            if (p == MEDIOCENTRO_OFENSIVO && (nombrePosicion.equals("MEDIOCENTRO_OFENSIVO") || nombrePosicion.equals("CENTROCAMPISTA"))) return p;
            if (p == EXTREMO_IZQUIERDO && (nombrePosicion.equals("EXTREMO_IZQUIERDO") || nombrePosicion.equals("DELANTERO"))) return p;
            if (p == EXTREMO_DERECHO && (nombrePosicion.equals("EXTREMO_DERECHO") || nombrePosicion.equals("DELANTERO"))) return p;
            if (p == DELANTERO_CENTRAL && (nombrePosicion.equals("DELANTERO_CENTRAL") || nombrePosicion.equals("DELANTERO"))) return p;
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
