
// * SymbolTable.java – Tabla de símbolos del ensamblador Hack
// * Guarda todos los simbolos y sus direcciones de memoria

import java.util.HashMap;

public class SymbolTable {
    
    //Diccionario que guarda: nombre del simbolo → direccion
    private HashMap<String, Integer> tabla;

    public SymbolTable() {
        tabla = new HashMap<>();
        
        // Estos simbolos ya vienen definidos en el lenguaje Hack
        tabla.put("SP",     0);
        tabla.put("LCL",    1);
        tabla.put("ARG",    2);
        tabla.put("THIS",   3);
        tabla.put("THAT",   4);
        tabla.put("R0",     0);
        tabla.put("R1",     1);
        tabla.put("R2",     2);
        tabla.put("R3",     3);
        tabla.put("R4",     4);
        tabla.put("R5",     5);
        tabla.put("R6",     6);
        tabla.put("R7",     7);
        tabla.put("R8",     8);
        tabla.put("R9",     9);
        tabla.put("R10",   10);
        tabla.put("R11",   11);
        tabla.put("R12",   12);
        tabla.put("R13",   13);
        tabla.put("R14",   14);
        tabla.put("R15",   15);
        tabla.put("SCREEN", 16384);
        tabla.put("KBD",    24576);
    }

    // Agregar un simbolo nuevo con su direccion
    public void agregar(String simbolo, int direccion) {
        tabla.put(simbolo, direccion);
    }

    // Pregunta si un simbolo ya existe
    public boolean existe(String simbolo) {
        return tabla.containsKey(simbolo);
    }

    // Obtener la direccion de un simbolo
    public int getDireccion(String simbolo) {
        return tabla.get(simbolo);
    }
}