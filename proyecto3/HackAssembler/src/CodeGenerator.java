import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class CodeGenerator {

    private Parser parser;
    private SymbolTable tabla;
    private String archivoSalida;
    private int siguienteDireccion = 16;
    private HashMap<String, String> tablaComp;
    private HashMap<String, String> tablaDest;
    private HashMap<String, String> tablaJump;

    public CodeGenerator(Parser parser, String archivoSalida) {
        this.parser = parser;
        this.archivoSalida = archivoSalida;
        this.tabla = new SymbolTable();
        inicializarTablas();
    }

    private void inicializarTablas() {
         tablaComp = new HashMap<>();
        tablaComp.put("0",   "0101010");
        tablaComp.put("1",   "0111111");
        tablaComp.put("-1",  "0111010");
        tablaComp.put("D",   "0001100");
        tablaComp.put("A",   "0110000");
        tablaComp.put("!D",  "0001101");
        tablaComp.put("!A",  "0110001");
        tablaComp.put("-D",  "0001111");
        tablaComp.put("-A",  "0110011");
        tablaComp.put("D+1", "0011111");
        tablaComp.put("A+1", "0110111");
        tablaComp.put("D-1", "0001110");
        tablaComp.put("A-1", "0110010");
        tablaComp.put("D+A", "0000010");
        tablaComp.put("D-A", "0010011");
        tablaComp.put("A-D", "0000111");
        tablaComp.put("D&A", "0000000");
        tablaComp.put("D|A", "0010101");
        tablaComp.put("M",   "1110000");
        tablaComp.put("!M",  "1110001");
        tablaComp.put("-M",  "1110011");
        tablaComp.put("M+1", "1110111");
        tablaComp.put("M-1", "1110010");
        tablaComp.put("D+M", "1000010");
        tablaComp.put("D-M", "1010011");
        tablaComp.put("M-D", "1000111");
        tablaComp.put("D&M", "1000000");
        tablaComp.put("D|M", "1010101");
        // Shift
        tablaComp.put("D<<", "0000001");
        tablaComp.put("A<<", "0100001");
        tablaComp.put("M<<", "1100001");
        tablaComp.put("D>>", "0000011");
        tablaComp.put("A>>", "0100011");
        tablaComp.put("M>>", "1100011");


        tablaDest = new HashMap<>();
        tablaDest.put("null", "000");
        tablaDest.put("M",    "001");
        tablaDest.put("D",    "010");
        tablaDest.put("MD",   "011");
        tablaDest.put("A",    "100");
        tablaDest.put("AM",   "101");
        tablaDest.put("AD",   "110");
        tablaDest.put("AMD",  "111");

        tablaJump = new HashMap<>();
        tablaJump.put("null", "000");
        tablaJump.put("JGT",  "001");
        tablaJump.put("JEQ",  "010");
        tablaJump.put("JGE",  "011");
        tablaJump.put("JLT",  "100");
        tablaJump.put("JNE",  "101");
        tablaJump.put("JLE",  "110");
        tablaJump.put("JMP",  "111");
    }
    public void generate() {
        primerRecorrido();
     try {
         BufferedWriter escritor = new BufferedWriter(new FileWriter(archivoSalida));
         parser.reiniciar();
         while (parser.hayMasLineas()) {
            parser.avanzar();
            String tipo = parser.getTipo();
            String binario = "";
            if (tipo.equals("A")) {
                binario = traducirA();
           }  else if (tipo.equals("C")) {
                    binario = traducirC();
                } else {
                    continue;
                }
                escritor.write(binario);
                escritor.newLine();
         }
         escritor.close();
         System.out.println("Traduccion exitosa: " + archivoSalida);

     } catch (IOException e) {
            System.out.println("Error: no se logro escribir el archivo");
     }
    }
    private void primerRecorrido() {
        int contadorLinea = 0;
        for (String linea: parser.getLineas()) {
            if(linea.startsWith("(")) {
                String etiqueta = linea.substring(1, linea.length() - 1).trim();
                tabla.agregar(etiqueta, contadorLinea);
            } else {
                contadorLinea++;
            }
    }
}

private String traducirA() {
        String simbolo = parser.getSimbolo();
        int valor;
        try {
            valor = Integer.parseInt(simbolo);
        } catch (NumberFormatException e) {
            if (!tabla.existe(simbolo)) {
                tabla.agregar(simbolo, siguienteDireccion);
                siguienteDireccion++;
            }
            valor = tabla.getDireccion(simbolo);
        }
        return String.format("%16s", Integer.toBinaryString(valor))
                     .replace(' ', '0');
    }
private String traducirC() {
        String comp = tablaComp.get(parser.getComp());
        String dest = tablaDest.get(parser.getDest());
        String jump = tablaJump.get(parser.getJump());
        if (comp == null) {
            System.out.println("Error: instruccion desconocida → "
                + parser.getComp());
            return "0000000000000000";
        }
        if (dest == null) dest = "000";
        if (jump == null) jump = "000";
        return "111" + comp + dest + jump;
    }
}