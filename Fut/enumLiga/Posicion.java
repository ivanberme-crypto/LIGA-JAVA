package Fut.enumLiga;

public enum Posicion {
    PORTERO("GK"),
    LATERAL_IZQUIERDO("LB"),
    LATERAL_DERECHO("RB"),
    DEFENSA_CENTRAL("CB"),
    MEDIOCENTRO("CM"),
    MEDIOCENTRO_DEFENSIVO("CDM"),
    MEDIOCENTRO_OFENSIVO("CAM"),
    EXTREMO_IZQUIERDO("LW"),
    EXTREMO_DERECHO("RW"),
    DELANTERO_CENTRAL("ST");

    private final String abreviatura;

    private Posicion(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public String getAbreviatura() {
        return abreviatura;
    }
    public static Posicion desdeString(String nombre) {
        try {
            return Posicion.valueOf(nombre.toUpperCase());
        } catch (IllegalArgumentException e) {
            switch (nombre.toUpperCase()) {
                case "DEFENSA": return DEFENSA_CENTRAL;
                case "CENTROCAMPISTA": return MEDIOCENTRO;
                case "DELANTERO": return EXTREMO_IZQUIERDO;
                default: throw new IllegalArgumentException("Posición no válida: " + nombre);
            }
        }
    }
    @Override
    public String toString() {
        return abreviatura;
    }
}
