/*********
* HackDisassembler.java – Clase que permite traducir ordenes en binario de 16 bits a instrucciones de assembler
* Autor 1: Alessandro Soccol Mejia
* Autor 2: Santiago Molano Jaramillo
*********/


import java.io.*;
import java.util.Arrays;

public class HackDisassembler {

    // Traducción de instrucciones A
    public static String traduccionTipoA(char[] bits){
        // Extraer los 15 bits de la instrucción A
        StringBuilder valor = new StringBuilder();
        for (int i = 1; i < bits.length; i++) {
            valor.append(bits[i]);
        }

        // Convertir el valor binario a decimal
        int valorDecimal = Integer.parseInt(valor.toString(), 2);

        // Retornar la instrucción A en formato assembler
        return "@" + valorDecimal;
    }

    // Traducción de instrucciones C
    public static String traduccionTipoC(char[] bits) throws Exception{
        // Validacion de los primeros 3 bits para asegurarse de que es una instrucción C
        if (bits[0] != '1'|| bits[1] != '1' || bits[2] != '1') {
            return "Error: No es una instrucción C válida";
        }
        char a = bits[3];                                // bit 'a' está en índice 3
        char[] compB = Arrays.copyOfRange(bits, 4, 10); // solo los 6 bits c1-c6 (índice 4 al 9)
        char[] destB = Arrays.copyOfRange(bits, 10, 13); // índice 10 al 12
        char[] jumpB = Arrays.copyOfRange(bits, 13, 16); // índice 13 al 15
        
        //Traduccion de cada seccion
        String comp = traducirComp(compB, a);
        String dest = traducirDest(destB);
        String jump = traducirJump(jumpB);

        //Armado de la instriccion C 
        String resultado = "";
        if(!dest.isEmpty()) resultado += dest + "=";
        resultado += comp;
        if(!jump.isEmpty()) resultado += ";" + jump;

        return resultado;
    }

    //TIPO C: Traduccion de operaciones
    public static String traducirComp(char[] compB, char a) throws Exception {
        String compBin = new String(compB);

        switch (compBin) {
            //Instrucciones que no dependen del bit 'a'
            case "101010": return "0";
            case "111111": return "1";
            case "111010": return "-1";
            case "001100": return "D";
            case "001101": return "!D";
            case "001111": return "-D";
            case "011111": return "D+1";
            case "001110": return "D-1";

            // Instrucciones que cambian segun el bit 'a'
            case "110000": return a == '0' ? "A"   : "M";
            case "110001": return a == '0' ? "!A"  : "!M";
            case "110011": return a == '0' ? "-A"  : "-M";
            case "110111": return a == '0' ? "A+1" : "M+1";
            case "110010": return a == '0' ? "A-1" : "M-1";
            case "000010": return a == '0' ? "D+A" : "D+M";
            case "010011": return a == '0' ? "D-A" : "D-M";
            case "000111": return a == '0' ? "A-D" : "M-D";
            case "000000": return a == '0' ? "D&A" : "D&M";
            case "010101": return a == '0' ? "D|A" : "D|M";

            default: throw new Exception("Campo comp inválido: " + compBin);
        }
    }

    //TIPO C: Traduccion de campo de destino
    public static String traducirDest(char[] destB) throws Exception {
        String destBin = new String(destB);
        switch (destBin) {
            case "000":
                return ""; // No se escribe nada si no hay destino
            case "001":
                return "M";
            case "010":
                return "D";
            case "011":
                return "MD";
            case "100":
                return "A";
            case "101":
                return "AM";
            case "110":
                return "AD";
            case "111":
                return "AMD";
            default:
                throw new Exception("Error: Destino no reconocido"+destBin);
        }
    }

    //TIPO C: Traduccion de campo de salto
    public static String traducirJump(char[] bits) throws Exception {
        String jumpBin = new String(bits);
        switch (jumpBin) {
            case "000":
                return ""; // No se escribe nada si no hay salto
            case "001":
                return "JGT";
            case "010":
                return "JEQ";
            case "011":
                return "JGE";
            case "100":
                return "JLT";
            case "101":
                return "JNE";
            case "110":
                return "JLE";
            case "111":
                return "JMP";
            default:
                throw new Exception("Error: Salto no reconocido"+jumpBin);
        }
    }

    public static void main(String[] args){
        //VALIDACIONES
        //Validacion de numero de argumentos
        if(args.length !=2){
            System.out.println("Error en el uso. \n Por favor ingreselo de la siguiente forma: java HackAssembler -d Prog.hack");
            return;
        }
        
        //Validacion de flag
        if(!args[0].equals("-d")){
            System.out.println("Error en el uso. \n Flag no reconocido");
            return;
        }

        //Validacion de extension del archivo de entrada
        String inputFileName = args[1];
        if(!inputFileName.endsWith(".hack")){
            System.out.println("Error en el uso. \n El archivo de entrada debe tener extension .hack");
            return;
        }

        //Proceso de desensamblado
        String base= inputFileName.replace(".hack", "");
        String salida = base + "Dis.asm";

        // Lectura del archivo de entrada y escritura del archivo de salida
        try(
            BufferedReader reader= new BufferedReader(new FileReader(inputFileName));
            PrintWriter writer= new PrintWriter(new FileWriter(salida));
        ){
            String linea;
            int nLinea=1;
            while((linea=reader.readLine())!=null){
                try {
                    // Procesar cada línea
                    char[] bits = linea.toCharArray(); // Convertir la línea a un arreglo de caracteres
                    String resultado;
                    if(bits[0]=='0'){
                        resultado = traduccionTipoA(bits);
                    }else if(bits[0]=='1'){
                        resultado = traduccionTipoC(bits);
                    }else{
                        throw new Exception("instrucción inválida");
                    }
                    writer.println(resultado); // Escribir el resultado en el archivo de salida
                    nLinea++;
                } catch (Exception e) {
                    // Si hay un error, mostrar el número de línea y detener el proceso
                    System.out.println("Error en línea " + nLinea + ": " + e.getMessage());
                    return;
                }
            }
        }catch (FileNotFoundException e) {
            System.out.println("Error: No se encontró el archivo " + inputFileName);
        }catch (IOException e) {
            System.out.println("Error leyendo/escribiendo archivo: " + e.getMessage());
        }
    }

}