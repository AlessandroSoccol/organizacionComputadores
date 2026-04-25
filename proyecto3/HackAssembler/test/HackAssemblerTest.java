/*********
* HackAssemblerTest.java – Pruebas para HackAssembler y HackDisassembler
* Autor 1: Alessandro Soccol Mejia
* Autor 2: Santiago Molano Jaramillo
*********/

public class HackAssemblerTest {

    public static void main(String[] args) throws Exception {

        System.out.println("=== Pruebas HackDisassembler ===\n");

        // Prueba 1: Tipo A simple
        String r1 = HackDisassembler.traduccionTipoA("0000000000010101".toCharArray());
        System.out.println("Tipo A - Esperado: @21 | Obtenido: " + r1);

        // Prueba 2: Tipo A valor 0
        String r2 = HackDisassembler.traduccionTipoA("0000000000000000".toCharArray());
        System.out.println("Tipo A - Esperado: @0 | Obtenido: " + r2);

        // Prueba 3: Dest
        String r3 = HackDisassembler.traducirDest("010".toCharArray());
        System.out.println("Dest   - Esperado: D  | Obtenido: " + r3);

        // Prueba 4: Jump
        String r4 = HackDisassembler.traducirJump("111".toCharArray());
        System.out.println("Jump   - Esperado: JMP | Obtenido: " + r4);

        // Prueba 5: Comp con a=0
        String r5 = HackDisassembler.traducirComp("110000".toCharArray(), '0');
        System.out.println("Comp   - Esperado: A  | Obtenido: " + r5);

        // Prueba 6: Comp con a=1
        String r6 = HackDisassembler.traducirComp("110000".toCharArray(), '1');
        System.out.println("Comp   - Esperado: M  | Obtenido: " + r6);

        // Prueba 7: Tipo C completa (D=A)
        String r7 = HackDisassembler.traduccionTipoC("1110110000010000".toCharArray());
        System.out.println("Tipo C - Esperado: D=A | Obtenido: " + r7);

        // Prueba 8: Tipo C sin dest (0;JMP)
        String r8 = HackDisassembler.traduccionTipoC("1110101010000111".toCharArray());
        System.out.println("Tipo C - Esperado: 0;JMP | Obtenido: " + r8);

        System.out.println("\n=== Pruebas HackAssembler ===\n");
        // Pendiente de implementacion
        System.out.println("Tipo A : pendiente");
        System.out.println("Tipo C : pendiente");
        System.out.println("Etiquetas : pendiente");
        System.out.println("Comentarios y lineas vacias : pendiente");
        System.out.println("Instrucciones shift : pendiente");
    }
}