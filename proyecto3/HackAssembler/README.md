# HackAssembler

Herramienta de línea de comandos para ensamblar y desensamblar programas escritos para la arquitectura Hack.

---

## Descripción

El proyecto implementa dos funcionalidades principales en un solo programa:

- **Ensamblador:** Traduce código escrito en lenguaje ensamblador Hack (`.asm`) a binario de 16 bits (`.hack`)
- **Desensamblador:** Traduce un archivo binario Hack (`.hack`) de vuelta a lenguaje ensamblador (`.asm`)

---

## Integrantes Proyecto 3

| Nombre | 
|---|
| Alessandro Soccol Mejia |
| Santiago Molano Jaramillo |

---

## Requisitos

- Java JDK 8 o superior

## Compilación

Desde la carpeta `src/`:

```bash
javac HackAssembler.java HackDisassembler.java
```

---

## Uso

### Modo ensamblador
```bash
java HackAssembler Prog.asm
```
Genera `Prog.hack` en la misma carpeta.

### Modo desensamblador
```bash
java HackAssembler -d Prog.hack
```
Genera `ProgDis.asm` en la misma carpeta.

---

## Ejemplo

**Entrada** `Prog.hack`:
```
0000000000000010
1110110000010000
0000000000000011
1110000010010000
```

**Salida** `ProgDis.asm`:
```
@2
D=A
@3
D=D+A
```

---

## Estructura del proyecto

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
│ ├── .GITIGNORE # Archivos ignorados por github
└────── README.md

---

## Documentación

| Archivo | Contenido |
|---|---|
| `docs/API.md` | Referencia técnica de todos los métodos públicos |
| `docs/DESIGN.md` | Diagrama de clases y decisiones de arquitectura |
| `docs/USER_GUIDE.md` | Guía completa de compilación, uso y errores |