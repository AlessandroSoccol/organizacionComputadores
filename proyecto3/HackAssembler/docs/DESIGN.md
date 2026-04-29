# DESIGN – HackAssembler

Descripción del diseño del sistema, diagrama de clases y decisiones de arquitectura.

---

## Diagrama de clases

```
┌─────────────────────────────────────┐
│           HackAssembler             │
│─────────────────────────────────────│
│ + main(args: String[]) : void       |
|  Parser                             |
|  - lineas: ArrayList                |
|  - posActual: int                   |
| + avanzar()                         |
|  + getTipo(): String                |
|  CodeGenerator                      |
|  - tablaComp: HashMap               |
|  - tablaDest: HashMap               |
|  + generate()                       |
|  + traducirA(): String              |
|  HackDisassembler                   |
|  - tablaComp: HashMap               | 
|  - tablaDest: HashMap               |
|  + disassemble()                    |
|  - traducirLinea()                  |
|  SymbolTable                        |
|  - tabla: HashMap                   |
|  + agregar(simbolo,dir)             |
|  +  existe(simbolo):boolean         |
|  + getDireccion(): in               |
└──────────────┬──────────────────────┘
               │ detecta modo por argumentos
       ┌───────┴────────┐
       │                │
       ▼                ▼
┌──────────────────────────────────────┐
│         HackDisassembler             │
│──────────────────────────────────────│
│ + main(args: String[]) : void        │
│ + traduccionTipoA(bits: char[])      │
│   : String                           │
│ + traduccionTipoC(bits: char[])      │
│   : String                           │
│ + traducirComp(compB: char[],        │
│   a: char) : String                  │
│ + traducirDest(destB: char[])        │
│   : String                           │
│ + traducirJump(bits: char[])         │
│   : String                           │
└──────────────────────────────────────┘
```

---

## Flujo de ejecución

### HackAssembler (`Prog.asm` → `Prog.hack`)
```
main(args)
  └── args.length == 1
        └── HackAssembler.ensamblar(Prog.asm)
              └── 
```

### HackDisassembler (`Prog.hack` → `ProgDis.asm`)
```
main(args)
  └── args.length == 2 && args[0] == "-d"
        └── HackDisassembler.main(args)
              └── Leer línea por línea de Prog.hack
                    ├── bits[0] == '0' → traduccionTipoA()  → "@valor"
                    └── bits[0] == '1' → traduccionTipoC()
                                            ├── traducirComp()  → "D+A", "M", ...
                                            ├── traducirDest()  → "A", "D", "MD", ...
                                            └── traducirJump()  → "JMP", "JGT", ...
                    └── Escribe resultado en ProgDis.asm
```

---

## Decisiones de diseño

### 1. Separación por tipo de instrucción
Se eligió separar la lógica en `traduccionTipoA` y `traduccionTipoC` para mantener cada responsabilidad aislada. El bit `bits[0]` actúa como discriminador principal.

### 2. Subfunciones para cada campo de Tipo C
En lugar de manejar toda la lógica de la instrucción C en un solo método, se crearon tres funciones independientes (`traducirComp`, `traducirDest`, `traducirJump`). Esto facilita la localización de errores y el mantenimiento del código.

### 3. Uso del bit `a` como parámetro explícito
El bit `a` (índice 3 del array) se extrae en `traduccionTipoC` y se pasa como parámetro a `traducirComp`. Esto evita que `traducirComp` tenga que conocer la posición del bit dentro del array original, respetando el principio de responsabilidad única.

### 4. Manejo de errores con `try/catch` interno
El `while` de lectura contiene un `try/catch` interno que captura cualquier excepción lanzada durante la traducción. Al detectar un error, imprime el número de línea y detiene el proceso inmediatamente, cumpliendo con el enunciado del proyecto.

### 5. Array de caracteres como unidad de procesamiento
Cada línea del archivo `.hack` se convierte en un `char[]` de 16 posiciones antes de procesarse. Esto permite acceder a bits individuales por índice de forma directa y eficiente, sin necesidad de parsear strings en cada subfunción.

---

## Estructura de archivos

```
├── organizacionComputadores/
│ ├── proyecto3/
│ │ ├── HackAssembler/
│ │ │ ├── docs/
│ │ │ │ ├── API.md # Documentación de las clases
│ │ │ │ ├── DESIGN.md # Diagrama de clases
│ │ │ │ ├── USER_GUIDE.md # Cómo ejecutar el programa
│ │ │ ├── src/
│ │ │ │ ├── HackAssembler.java
│ │ │ │ ├── HackAssembler.md5
│ │ │ │ ├── HackDisassembler.java
│ │ │ │ ├── HackDisassembler.md5
│ │ │ ├── test/
│ │ │ │ ├── HackAssemblerTest.java
│ │ │ │ ├── HackAssemblerTest.md5
│ │ │ ├── README.md
│ ├── CONTRIBUTORS.md # Colaboradores y roles
│ ├── CHANGELOG.md # Historial de cambios
│ ├── LICENSE # Licencia del proyecto
└────── README.md
```
