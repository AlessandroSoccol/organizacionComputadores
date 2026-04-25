# USER GUIDE – HackAssembler

Guía completa para compilar y ejecutar el programa en sus dos modos de uso.

---

## Requisitos previos

- Tener instalado **Java JDK 8** o superior
- Tener acceso a una terminal (CMD, PowerShell, o terminal de Linux/Mac)
- Verificar la instalación con:
```bash
java -version
javac -version
```

---

## Compilación

Navegar hasta la carpeta `src` del proyecto y compilar ambos archivos:

```bash
cd ruta/proyecto3/HackAssembler/src
javac HackAssembler.java HackDisassembler.java
```

Esto genera los archivos `.class` necesarios para ejecutar el programa.

---

## Modos de uso

### Modo 1 – Ensamblador (`.asm` → `.hack`)

Traduce un programa escrito en lenguaje ensamblador Hack a binario de 16 bits.

```bash
java HackAssembler Prog.asm
```

**Resultado:** Se genera el archivo `Prog.hack` en la misma carpeta.

---

### Modo 2 – Desensamblador (`.hack` → `.asm`)

Traduce un archivo binario Hack de vuelta a lenguaje ensamblador.

```bash
java HackAssembler -d Prog.hack
```

**Resultado:** Se genera el archivo `ProgDis.asm` en la misma carpeta.

---

## Ejemplos

### Ejemplo de archivo de entrada `Prog.hack`
```
0000000000000010
1110110000010000
0000000000000011
1110000010010000
```

### Archivo de salida esperado `ProgDis.asm`
```
@2
D=A
@3
D=D+A
```

---

## Manejo de errores

Si el archivo de entrada contiene algún error, el programa mostrará un mensaje indicando la línea donde se encontró el problema y detendrá el proceso:

```
Error en línea 3: Campo comp inválido: 101101
```

### Tabla de errores comunes

| Error | Causa | Solución |
|---|---|---|
| `Uso: java HackAssembler ...` | Número de argumentos incorrecto | Verificar el comando ingresado |
| `No se encontró el archivo` | El archivo no existe en la ruta indicada | Verificar que el archivo existe |
| `Flag no reconocido` | Se usó un flag distinto a `-d` | Usar exactamente `-d` |
| `El archivo debe tener extensión .hack` | El archivo de entrada no termina en `.hack` | Verificar el nombre del archivo |
| `Campo X inválido` | Combinación de bits sin traducción válida | Verificar el archivo  |
|  | |  |
|  |  |  |

---

## Notas importantes

- El archivo de entrada debe estar en la **misma carpeta** desde donde se ejecuta el comando, o se debe indicar la ruta completa.
- El archivo de salida se genera automáticamente en la misma carpeta que el archivo de entrada.
- Si el archivo de salida ya existe, será **sobreescrito**.
- Cada línea del archivo `.hack` debe tener **exactamente 16 caracteres** compuestos únicamente por `0` y `1`.