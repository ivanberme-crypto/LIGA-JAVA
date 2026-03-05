FUTDRAFT
FutDraft es una aplicación de consola que hemos desarrollado en Java, que simula la gestión básica de equipos, jugadores y ligas de fútbol.
El programa permite cargar jugadores desde un archivo, organizar equipos, crear una liga y simular jornadas y partidos.
El usuario también puede crear y gestionar su propio equipo, participando dentro de la liga junto con otros equipos.

CARACTERISTICAS
- Carga de jugadores desde archivo .txt
- Gestión de jugadores y equipos
- Creación de ligas
- Simulación de jornadas
- Simulación de partidos
- Creación de un equipo propio
- Menú interactivo en consola
- Salida en consola con decoraciones y colores

ESTRUCTURA DEL PROYECTO
El proyecto está organizado en varios paquetes para separar responsabilidades:

│
├── Main.java                # Punto de entrada del programa.
├── Menu.java                # Menú principal de la aplicación.
├── Decoracion.java          # Colores y decoración de consola y otros metodos.
│
├── almacen
│   └── Almacen.txt          # Archivo de datos de jugadores, equipos y ligas.
│
├── equipos
│   ├── Equipo.java          # Clase para calcular lo relacionado con los equipos.
│   ├── EquipoPropio.java    # Clase relacionada con el equipo como crea el usuario.
│   └── AlmacenEquipo.java   # Clase que muestra los menus de los equipos.
│
├── personas
│   ├── Persona.java         # Clase padre Jugador.java.
│   ├── Jugador.java         # Clase que hereda lo de Persona.java y estan los metodos de las sanciones y los atributos de los jugadores.
│   └── AlmacenJugador.java  # Clase en la que carga los jugadores del txt.
│
├── ligas
│   ├── Liga.java            # Menu del futdraft, con los metodos necesarios para que funcione.
│   ├── LigaTerminada.java   # Ultimo menu que se muestra al terminar la liga.
│   ├── AlmacenLiga.java     # Menu de seleccion de liga.
│   │
│   └── jornadas
│       ├── Jornada.java     # Muestra los partidos y los simula, ademas de tener un metodo para que quite la sancion de los jugadores.
│       └── Partido.java     # Tiene el metodo que hace que el partido se simule, un metodo para seleccionar que jugador marca gol y otro para que jugador recibe tarjeta.
│
└── enumLiga
    └── Posicion.java        # Un enum que muestra las posiciones disponibles.

PARA EJECUTARLO
1. - O bien poner este comando en la terminal de IntelliJ: git clone https://github.com/ivanberme-crypto/LIGA-JAVA.git
   - O descargar el archivo Fut.zip
2. Ejecutar la clase Main para que te lleve al menu principal y disfrutar del FutDraft.

ARCHIVO DE DATOS
El archivo de datos donde esta toda la informacion guardada es Almacen.txt con la ruta "src/Fut/almacen/Almacen.txt"

OBJETIVO DEL PROYECTO
El objetivo principal de este proyecto es practicar la programacion orientada a objetos mediante la simulación de un de un juego de decision que incluye:
- gestión de entidades
- relaciones entre clases
- simulación de procesos (las ligas y los partidos)

MEJORAS FUTURAS QUE NOS GUSTARIA IMPLEMENTAR
- Creacion del torneo UEFA Champions League.
- Txt que se sobrescribe con los datos de los jugadores.
- Opcion de ver el partido del equipo del usuario minuto por minuto con decisiones de si hacer cambios y mas cosas.

AUTORES
- Javier Clemente
- Ivan Bermejo
- Javier Vega
