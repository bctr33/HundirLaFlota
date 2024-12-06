import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Hundirlaflota {

        public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);
            int contadorPartidas = 0;
            char continua = 'a';

            do {
                System.out.println("Hunde la flota");
                System.out.println("¿Qué modo desea jugar? ");
                String nivel;
                nivel = sc.nextLine();
                boolean continuar = true;

                if (nivel.contains("v")) {
                    System.out.println("Ha elegido el modo avanzado");
                    System.out.println("¿Cuantos desean jugar (1 jugador o 2 jugadores)?");
                    String jugadores = sc.nextLine();

                    if(jugadores.equals("1")) {
                        modoAvanzado();
                    }
                    else if(jugadores.equals("2")) {
                        modoDosJugadores();
                    }
                    else {
                        System.out.println("Error en la elección de jugadores");
                    }
                    continuar = false;

                    int turno = 0; // para el jugador 1 vale 0 y para el 2 vale 1

                }
                else if (nivel.contains("b")) {
                    System.out.println("Ha elegido el modo básico");
                    modoBasico();
                } else {
                    System.out.println("No ha elegido ni el modo básico ni el avanzado, vuelva a escribir uno de ellos.");
                }

                System.out.println("¿Desea jugar nuevamente (S/N)?");
                continua = sc.nextLine().charAt(0);

            } while (continua == 'S' || continua == 's' );

        }

        // modo básico del juego
        public static void modoBasico () {
            Scanner sc = new Scanner(System.in);
            String [] [] tablero = new String[5][5]; // para el modo básico se crea un tablero 5x5
            String [] [] tablerobarcos= new String[5][5];
            inicializarTablero(tablero);// el primer tablero que ve el usuario
            inicializarTablero(tablerobarcos);

            // colocar los 4 barcos en el tablero 5x5
            colocarBarcos(tablerobarcos, 2);
            colocarBarcos(tablerobarcos, 2);
            colocarBarcos(tablerobarcos, 3);
            colocarBarcos(tablerobarcos, 3);


            String simboloTocado = "#";
            String simboloAgua = "0";
            String simboloHundido= "X";
            int filas = 0;
            int columnas = 0;
            int barcosPorHundir = 4;
            int barcosHundidos = 0;

            // ciclos del juego
            while (barcosHundidos<4) {
                mostrarTablero(tablero);
                System.out.println("Ingresa las coordenadas (ejemplo: 1 B): ");
                int [] coordenadas = obtenerCoordenadasValidas(tablero.length, tablero[0].length);

                // para extraer las coordenadas válidas
                int fila = coordenadas[0];
                int columna = coordenadas[1];

                // procesar el disparo, hundido, agua o tocado
                if (tablero[fila][columna].equals("□")) {
                    tablero[fila][columna] = simboloAgua;
                    tablerobarcos[fila][columna] = simboloAgua;
                    System.out.println("Has fallado. ¡Agua!");

                } else if (tablerobarcos[fila][columna].equals(simboloHundido) || tablerobarcos[fila][columna].equals(simboloAgua) || tablerobarcos[fila][columna].equals(simboloTocado)) {
                    System.out.println("Ya has disparado esa coordenada");

                } else { // cuando es un barco
                    if (tablerobarcos[fila][columna].equals("B")) {
                        tablero[fila][columna] = simboloTocado;
                        tablerobarcos[fila][columna] = simboloTocado; // marcar el impacto
                        System.out.println("¡Has tocado!");

                    }
                    // validar cuando se hunde el barco por completo
                    if (estaHundido(tablero, tablerobarcos, fila, columna)) {
                        System.out.println("Barco hundido!");
                        barcosHundidos++;
                    }

                }

                // actualización del tablero con las jugadas realizadas
                printTablero(tablero);
                System.out.println("Barcos hundidos: " + barcosHundidos + "/ Barcos por hundir: " + barcosPorHundir);
            }

            // Fin del juego
            System.out.println("\n ¡Felicidades! Has conseguido hundir todos los barcos.");
            reset(tablero, tablerobarcos, 4, 0);

        }

        // el modo avanzado del juego
        public static void modoAvanzado () {
            Scanner sc = new Scanner(System.in);

            // para que seleccione el tamaño del tablero
            System.out.println("Seleccione el tamaño del tablero, del 5 al 10");
            Scanner bacteria = new Scanner(System.in);
            int gridSize = Integer.parseInt(bacteria.nextLine());
            String [][] tablero = new String[gridSize][gridSize];
            String [][] tablerobarcos = new String[gridSize][gridSize];

            inicializarTablero(tablero);
            inicializarTablero(tablerobarcos);


            // colocar los 4 barcos en el tablero 5x5
            colocarBarcos(tablerobarcos, 2);
            colocarBarcos(tablerobarcos, 2);
            colocarBarcos(tablerobarcos, 3);
            colocarBarcos(tablerobarcos, 3);

            // las coordenadas a introducir por el usuario tras haber colocado los barcos
            System.out.println("Introduce un símbolo para tocado:");
            String simboloTocado = sc.nextLine();
            System.out.println("Introduce un símbolo para agua:");
            String simboloAgua = sc.nextLine();
            System.out.println("Introduce un símbolo para hundido:");
            String simboloHundido= sc.nextLine();


            int filas = 0;
            int columnas = 0;
            int barcosPorHundir = 4;
            int barcosHundidos = 0;

            // ciclos del juego
            while (barcosHundidos<4) {
                mostrarTablero(tablero);

                System.out.println("Ingresa las coordenadas (ejemplo: 1 B): ");
                int [] coordenadas = obtenerCoordenadasValidas(tablerobarcos.length, tablerobarcos[0].length);

                // para extraer las coordenadas válidas
                int fila = coordenadas[0];
                int columna = coordenadas[1];

                // procesar el disparo, hundido, agua o tocado
                if (tablerobarcos[fila][columna].equals("□")) {
                    tablero[fila][columna] = simboloAgua;
                    tablerobarcos[fila][columna] = simboloAgua;
                    System.out.println("Has fallado. ¡Agua!");

                } else if (tablerobarcos[fila][columna].equals(simboloHundido) || tablerobarcos[fila][columna].equals(simboloAgua) || tablerobarcos[fila][columna].equals(simboloTocado)) {
                    System.out.println("Ya has disparado esa coordenada");

                } else { // cuando es un barco
                    if (tablerobarcos[fila][columna].equals("B")) {
                        tablero[fila][columna] = simboloTocado;
                        tablerobarcos[fila][columna] = simboloTocado; // marcar el impacto
                        System.out.println("¡Has tocado!");

                        // validar cuando se hunde el barco por completo
                        if (estaHundido(tablero, tablerobarcos, fila, columna)) {
                            System.out.println("Barco hundido!");
                            barcosHundidos++;
                        }
                    }

                }

                // actualización del tablero con las jugadas realizadas
                printTablero(tablero);
                System.out.println("Barcos hundidos: " + barcosHundidos + "/ Barcos por hundir: " + barcosPorHundir);
            }

            // Fin del juego
            System.out.println("\n ¡Felicidades! Has conseguido hundir todos los barcos.");
            reset(tablero, tablerobarcos, 4 , 0);

        }

        // falta poner el modo dos jugadores
        public static void modoDosJugadores() {
            Scanner sc = new Scanner(System.in);
            int turno = 0;

            while (true) {
                if (turno == 0) { // para turno = 0 juega el primer jugador
                    System.out.println("Jugador 1");
                    modoAvanzado();
                    turno = 1;

                } else //if (turno == 1) juega el jugador 2
                {
                    System.out.println("Jugador 2");
                    modoAvanzado();
                    turno = 0; // el turno cambia al jugador 1

                }}
        }


        // para inicializar el tablero
        public static void inicializarTablero (String [][] tablero) {
            for (int i = 0; i <tablero.length; i++) {
                for (int j = 0; j<tablero.length; j++) {
                    tablero[i][j] = "□";
                }
            }
        }

        // para mostrar el tablero con letras y números para las coordenadas
        public static void mostrarTablero (String [][] tablero) {
            int gridSize = tablero.length;

            // encabezado de las letras en las columnas
            // Espacio para la esquina superior
            System.out.print("    ");
            for (int i = 0; i < tablero[0].length; i++) { // como poner el encabezado bien?

                System.out.print((char) ('A' + i) + " ");
            }
            System.out.println();

            //contenido del tablero con números en las filas
            for (int i = 0; i < gridSize; i++) {
                System.out.printf("%2d ", i); // número de fila alineado a la derecha
                System.out.print(" ");

                for (int j = 0; j < gridSize; j++) {
                    System.out.print(tablero[i][j] + " ");
                }
                System.out.println();
            }
            System.out.println();

        }

        // imprimir el tablero
        public static void printTablero (String [][] tablero) {
            for (int i = 0; i <tablero.length; i++) {
                for (int j = 0; j<tablero.length; j++) {
                    System.out.print(tablero[i][j] + " ");
                }
                System.out.println();
            }
        }

        // colocar los barcos
        public static void colocarBarcos(String[][] tablero, int longitudBarco) {
            Random r = new Random();
            boolean colocado = false;
            int longitudTablero = tablero.length;

            while (!colocado) { // repetir hasta que el barco se coloque bien
                int x = r.nextInt(longitudTablero); // Random con la coordenada x -- columnas
                int y = r.nextInt(tablero[0].length); // Random con la coordenada y -- filas
                int direccion = r.nextInt(2); // 0 para el barco horizontal, 1 para vertical

                if (canPlaceShip(tablero, x, y, longitudBarco, direccion)) {
                    for (int i = 0; i < longitudBarco; i++) {
                        if (direccion == 0) { // Horizontal -- barco
                            tablero[x][y + i] = "B";
                        } else { // Vertical  -- barco
                            tablero[x + i][y] = "B";
                        }
                    }
                    colocado = true; // acabar el bucle cuando el barco esté bien colocado
                }
            }
        }

        // validar si es posible colocar los barcos
        public static boolean canPlaceShip(String[][] tablero, int x, int y, int longitudBarco, int direccion) {
            int longitud = tablero.length;

            if (direccion == 0) { // Horizontal
                if (y + longitudBarco > longitud) return false; // fuera de los límites
                for (int i = 0; i < longitudBarco; i++) {
                    if (!tablero[x][y + i].equals("□")) return false; // cailla ocupada
                }
            } else { // Vertical
                if (x + longitudBarco > longitud) return false; // fuera de los límites
                for (int i = 0; i < longitudBarco; i++) {
                    if (!tablero[x + i][y].equals("□")) return false; // casilla ocupada
                }
            }
            return true;
        }

        // validar y obterner las coordenadas introducidas por el usuario
        public static int[] obtenerCoordenadasValidas (int filasMax, int columnasMax) {
            Scanner sc = new Scanner(System.in);
            boolean valido = false;
            int [] coordenada = new int [2];

            while (!valido) {
                System.out.println("Ingrese las coordenadas (ejemplo: 1 B):");
                String entrada = sc.nextLine().toUpperCase();

                try {
                    int fila = Integer.parseInt(entrada.split(" ")[0]);
                    char columnaChar = entrada.split(" ")[1].charAt(0);

                    System.out.println("Las coordenadas introducidas son: " + "[" + fila + "," + columnaChar + "]");
                    int columna = columnaChar - 'A'; // para convertir la letra de columna a número

                    // para verificar que estén dentro de los límites del tablero
                    if (fila >= 0 && fila< filasMax && columna >= 0 && columna < columnasMax) {
                        coordenada[0] = fila;
                        coordenada[1] = columna;
                        valido = true;
                    } else {
                        System.out.println("Coordenadas fuera de los límites");
                    }

                } catch (InputMismatchException e) { // si el usuario ingresa algo que no es un número entero, se muestra el siguiente mensaje
                    System.out.println("Las coordenadas no son válidas.");
                    sc.nextLine(); //para que vuelva a ingresar las coordenadas
                }
            }
            return coordenada;
        }

        // para comprobar si el barco tocado es hundido también
        public static boolean estaHundido (String[][] tablero, String [][] tablerobarcos, int fila, int columna) {
            // Comprobar dirección del barco (horizontal o vertical)
            boolean horizontal = false;

            // para saber la dirección del barco
            if (columna + 1 < tablero[0].length && tablerobarcos[fila][columna + 1].equals("B") || columna > 0 && tablerobarcos[fila][columna - 1].equals("#")) {
                horizontal = true;
            }

            // Verificar todas las celdas del barco en la dirección correspondiente
            if (horizontal) {
                // Revisar horizontalmente
                for (int j = columna; j >= 0 && tablero[fila][j].equals("#"); j--) {
                    if (tablero[fila][j].equals("B")) return false;
                }
                for (int j = columna; j < tablero[0].length && tablero[fila][j].equals("#"); j++) {
                    if (tablero[fila][j].equals("B")) return false;
                }
            } else {
                // Revisar verticalmente
                for (int i = fila; i >= 0 && tablero[i][columna].equals("#"); i--) {
                    if (tablero[i][columna].equals("B")) return false;
                }
                for (int i = fila; i < tablero.length && tablero[i][columna].equals("#"); i++) {
                    if (tablero[i][columna].equals("B")) return false;
                }
            }

            // Si todas las celdas están marcadas como "#", el barco está hundido
            return true;
        }

        // para reiniciar el juego y el contador de partidas
        public static String [][] reset (String [][] tablero, String [][] tablerobarcos, int barcosPorHundir, int barcosHundidos) {
            Scanner sc = new Scanner(System.in);
            int contadorPartidas = 0;

            while (barcosHundidos == barcosPorHundir) {
                for (int i = 0; i < tablero.length; i++) {
                    for (int j = 0; j < tablero.length; j++) {
                        tablero[i][j] = "□";
                        barcosHundidos = 0;
                        barcosPorHundir = 4;
                        tablerobarcos [i][j] = "□";

                        if (tablero[i][j] == "□") {
                            contadorPartidas = contadorPartidas + 1;
                            System.out.println(contadorPartidas + ": juegos realizados.");
                        } else {
                            System.out.println(contadorPartidas + ": juegos realizados.");
                        }
                    }
                }
                return tablero;
            }
            return tablero;
        }
    }