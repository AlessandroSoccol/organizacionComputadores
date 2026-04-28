/*********
 * HackAssembler.java – Clase principal del traductor Assembler Hack
 * Autor 1:
 * Autor 2:
 *********/

public class HackAssembler {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Uso:");
            System.out.println("  Ensamblar:    java HackAssembler Prog.asm");
            System.out.println("  Desensamblar: java HackAssembler -d Prog.hack");
            return;
        }

     if (args[0].equals("-d")) {
            if (args.length < 2) {
                System.out.println("Error: falta el archivo .hack");
                return;
            }
            String archivoHack = args[1];
            if (!archivoHack.endsWith(".hack")) {
                System.out.println("Error: el archivo debe tener extension .hack");
                return;
            }
            // HackDisassembler sera implementado por el compañero
            System.out.println("Desensamblador pendiente de implementacion");
            return;
        }
        
        String archivoAsm = args[0];
        if (!archivoAsm.endsWith(".asm")) {
            System.out.println("Error: el archivo debe tener extension .asm");
            return;
        }
        String archivoHack = archivoAsm.replace(".asm", ".hack");
        Parser parser = new Parser(archivoAsm);
        CodeGenerator codeGen = new CodeGenerator(parser, archivoHack);
        codeGen.generate();
    }
}