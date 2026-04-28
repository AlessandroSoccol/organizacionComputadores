import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Parser {
    private ArrayList<String> lineas;
    private int posActual;
    private String lineaActual;

    public Parser(String archivo ) {
        lineas = new ArrayList<>();
        posActual = 0;
        cargarArchivo(archivo);
  }
private void cargarArchivo(String archivo) {
    try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
        String linea;
    
        while ((linea = lector.readLine()) != null) {
            // Eliminar comentarios y espacios en blanco
            linea = linea.trim();
            if (linea.isEmpty() || linea.startsWith("//")) {
                continue;
            }
            if (linea.contains("//")) {
                linea = linea.substring(0, linea.indexOf("//")).trim();
            }
            if (!linea.isEmpty()) {
                lineas.add(linea);
            }
        }
    } catch (IOException e) {
        System.out.println("Error: no se logro abrir el archivo");

    }
}
public boolean hayMasLineas(){
    return posActual < lineas.size();
}
 public void avanzar() {
    lineaActual = lineas.get(posActual);
    posActual++;
 }
 public String getTipo(){
    if (lineaActual.startsWith("@")){
return "A";
    } else if (lineaActual.startsWith("(")){
        return"L";
    } else {
        return "C";
    }
 }
 public String getSimbolo() {
        return lineaActual.substring(1).replace(")", "").trim();
    }

    public String getDest() {
        if (lineaActual.contains("=")) {
            return lineaActual.substring(0, lineaActual.indexOf("=")).trim();
        }
        return "null";
    }

    public String getComp() {
        String temp = lineaActual;
        if (temp.contains("=")) {
            temp = temp.substring(temp.indexOf("=") + 1);
        }
        if (temp.contains(";")) {
            temp = temp.substring(0, temp.indexOf(";"));
        }
        return temp.trim();
    }

    public String getJump() {
        if (lineaActual.contains(";")) {
            return lineaActual.substring(lineaActual.indexOf(";") + 1).trim();
        }
        return "null";
    }

    public ArrayList<String> getLineas() {
        return lineas;
    }

    public void reiniciar() {
        posActual = 0;
        lineaActual = null;
    }
}