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

    @Override
    public String toString() {
        return abreviatura;
    }
}
