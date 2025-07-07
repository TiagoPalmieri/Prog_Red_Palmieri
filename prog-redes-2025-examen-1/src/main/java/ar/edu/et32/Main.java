package ar.edu.et32;

import java.io.PrintStream;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static Consola consola = new Consola();
    public static ManejadorArchivos manejador = new ManejadorArchivos();
    public static PrintStream ps = new PrintStream(System.out);
    public static void main(String[] args) {
        if (!manejador.prepararArchivos()) {
            ps.printf("%sEl programa se cierra debido a un error. Ve a '%s'.%s%n", Consola.RED, "ERRORES.log", Consola.RESET);
            return;
        }

        while (true) {
            consola.mostrarMenu();
            String opcion = consola.leerTexto("Seleccione una opcion:");

            switch (opcion) {
                case "1":
                    mostrarDatos();
                    break;
                case "2":
                    agregarDatos();
                    break;
                case "3":
                    eliminarDatos();
                    break;
                case "4":
                    ps.printf("%n%sgracias por jugar%s%n", Consola.GREEN, Consola.RESET);
                    return;
                default:
                    ps.printf("%sopción no valida. Por favor intenta de nuevo.%s%n", Consola.RED, Consola.RESET);
            }
        }
    }

    private static void mostrarDatos() {
        List<RenglonTuti> renglones = manejador.leerRenglones();
        Collections.sort(renglones);

        ps.printf("%n%sDATOS DEL TUTI-FRUTI%s%n", Consola.PURPLE, Consola.RESET);



        for (int i = 0; i < renglones.size(); i++) {
            RenglonTuti r = renglones.get(i);

            String colorFila = (i % 2 == 0) ? Consola.GREEN : Consola.YELLOW;

            ps.printf(colorFila + "%-5d %-7c %-15s %-15s %-15s %-15s" + Consola.RESET + "%n",
                    (i + 1),
                    r.getLetra(),
                    r.getColor(),
                    r.getAnimal(),
                    r.getObjetos(),
                    r.getAlimento());
        }
    }
    private static void agregarDatos() {
        ps.print("\n" + Consola.CONSORTI + "AGREGAR NUEVA JUGADA" + Consola.RESET + "\n");
        List<RenglonTuti> renglones = manejador.leerRenglones();
        Set<Character> letrasUsadas = new HashSet<>();
        for (RenglonTuti r : renglones) {
            letrasUsadas.add(r.getLetra());
        }

        char letra;
        while (true) {
            String inputLetra = consola.leerTexto("ingresa una letra para jugar:").toUpperCase();
            if (inputLetra.length() != 1) {
                ps.print(Consola.RED + "error debes ingresar una sola letra." + Consola.RESET + "\n");
                continue;
            }
            letra = inputLetra.charAt(0);
            if (letrasUsadas.contains(letra)) {
                ps.print(Consola.RED + "error la letra '" + letra + "' ya se jugó. Intentar con otra letra." + Consola.RESET + "\n");

                StringBuilder letrasDisponibles = new StringBuilder("Letras disponibles: ");
                for (int ascii = 97; ascii <= 122; ascii++) {
                    char letraAscii = (char) ascii;
                    if (!letrasUsadas.contains(letraAscii)) {
                        letrasDisponibles.append(letraAscii).append(" ");
                    }
                }
                ps.print(letrasDisponibles.toString() + "\n");
            } else {
                break;
            }
        }

        String color = pedirPalabra("Color:", letra);
        String animal = pedirPalabra("Animal:", letra);
        String objetos = pedirPalabra("Objeto:", letra);
        String alimento = pedirPalabra("Alimento:", letra);

        renglones.add(new RenglonTuti(letra, color, animal, objetos, alimento));
        manejador.escribirRenglones(renglones);
        ps.print("\n" + Consola.GREEN + "jugada con la letra '" + letra + "' agregada" + Consola.RESET + "\n");
    }

    private static String pedirPalabra(String categoria, char letra) {
        while (true) {
            String palabra = consola.leerTexto(categoria);
            if (palabra.trim().toUpperCase().startsWith(String.valueOf(letra))) {
                return palabra.trim();
            } else {
                ps.printf("%serror la palabra debe empezar con la letra '%c'.%s%n", Consola.RED, letra, Consola.RESET);
            }
        }
    }

    private static void eliminarDatos() {
        mostrarDatos();
        List<RenglonTuti> renglones = manejador.leerRenglones();
        if (renglones.isEmpty()) {
            ps.printf("%sno hay datos para eliminar.%s%n", Consola.RED, Consola.RESET);
            return;
        }

        try {
            int nro = Integer.parseInt(consola.leerTexto("ingresa el NRO de la fila a eliminar:"));
            if (nro > 0 && nro <= renglones.size()) {
                renglones.remove(nro - 1);
                manejador.escribirRenglones(renglones);
                ps.printf("%n%sfila eliminada%s%n", Consola.GREEN, Consola.RESET);
            } else {
                ps.printf("%serror: El número está fuera de rango.%s%n", Consola.RED, Consola.RESET);
            }
        } catch (NumberFormatException e) {
            ps.printf("%serror debes ingresar un número válido.%s%n", Consola.RED, Consola.RESET);
        }
    }
}
