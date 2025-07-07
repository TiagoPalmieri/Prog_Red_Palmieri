package ar.edu.et32;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ManejadorArchivos {
    private static final String ARCHIVO_ORIGINAL = "datos.dat";
    private static final String ARCHIVO_CSV = "tuti-fruti.csv";
    private static final String ARCHIVO_ERRORES = "ERRORES.log";
    public PrintStream ps = new PrintStream(System.out);


    public boolean prepararArchivos() {
        File archivoCSV = new File(ARCHIVO_CSV);
        File archivoOriginal = new File(ARCHIVO_ORIGINAL);

        if (archivoCSV.exists()) {
            ps.printf("%sel archivo '%s' ya está reparado.", Consola.GREEN, ARCHIVO_CSV, Consola.RESET);
            return true;
        }

        if (!archivoOriginal.exists()) {
            registrarError("noo aparece el archivo original '" + ARCHIVO_ORIGINAL + "' No se puede continuar.");
            return false;
        }

        ps.printf("%sarchivo original encontrado. Iniciando conversión a formato CSV...%s%n", Consola.YELLOW, Consola.RESET);
        return convertirACSV(archivoOriginal, archivoCSV);
    }
    private boolean convertirACSV(File original, File nuevo) {
        File tempFile = new File("temp.tmp");

        try (BufferedReader reader = new BufferedReader(new FileReader(original));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String linea;
            while ((linea = reader.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;

                String[] palabras = linea.split(",");
                if (palabras.length < 4) continue;


                char letra = palabras[0].trim().toUpperCase().charAt(0);


                String nuevaLinea = String.format("%c;%s;%s;%s;%s",
                        letra,
                        palabras[0].trim(), // color
                        palabras[1].trim(), // animal
                        palabras[2].trim(), // objetos
                        palabras[3].trim()  // alimento
                );
                writer.write(nuevaLinea);
                writer.newLine();
            }
        } catch (IOException e) {
            registrarError("Error en la conversión del archivo: " + e.getMessage());
            return false;
        }


        if (!original.delete()) {
            registrarError("No se puede borrar el archivo ORG.");
            return false;
        }
        if (!tempFile.renameTo(nuevo)) {
            registrarError("No se pudo renombrar el archivo temporal a '" + ARCHIVO_CSV + "'.");
            return false;
        }

        ps.printf("%Conversión exitosa arcivo '%s' creado.%s%n", Consola.GREEN, ARCHIVO_CSV, Consola.RESET);
        return true;
    }

    public List<RenglonTuti> leerRenglones() {
        List<RenglonTuti> renglones = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_CSV))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(";");
                if (datos.length == 5) {
                    renglones.add(new RenglonTuti(datos[0].charAt(0), datos[1], datos[2], datos[3], datos[4]));
                }
            }
        } catch (IOException e) {
            registrarError("Error al leer el archivo de datos: " + e.getMessage());
        }
        return renglones;
    }

    public void escribirRenglones(List<RenglonTuti> renglones) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_CSV))) {
            for (RenglonTuti renglon : renglones) {
                writer.write(renglon.toCSVString());
                writer.newLine();
            }
        } catch (IOException e) {
            registrarError("error al reescribir el archivo de datos: " + e.getMessage());
        }
    }

    public void registrarError(String mensaje) {
        ps.printf("%sERROR: %s%s%n", Consola.RED, mensaje, Consola.RESET);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_ERRORES, true))) {
            writer.write(new java.util.Date() + ": " + mensaje);
            writer.newLine();
        } catch (IOException e) {
            ps.printf("%sFALLO GRANDE: No se pudo escribir en el archivo de errores.%s%n", Consola.RED, Consola.RESET);
        }
    }

}
