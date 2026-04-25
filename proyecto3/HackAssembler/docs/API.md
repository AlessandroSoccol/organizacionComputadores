# API Documentation – HackAssembler

Documentación técnica de todas las clases y métodos públicos del proyecto.

---

## Clase `HackAssembler`

Punto de entrada principal del programa. Detecta el modo de ejecución y delega al ensamblador o desensamblador según los argumentos recibidos.

### `main(String[] args)`
| Campo | Detalle |
|---|---|
| **Descripción** | Método principal. Detecta el modo de ejecución según los argumentos. |
| **Parámetros** | `args[0]` – Nombre del archivo `.asm` (modo ensamblador) o flag `-d` (modo desensamblador) |
| | `args[1]` – Nombre del archivo `.hack` (solo en modo desensamblador) |
| **Salida** | Archivo `.hack` (modo ensamblador) o archivo `Dis.asm` (modo desensamblador) |
| **Errores** | Imprime mensaje en pantalla si los argumentos son inválidos |

---

## Clase `HackDisassembler`

Traduce un archivo binario `.hack` de 16 bits por línea a su equivalente en lenguaje ensamblador Hack.

---

### `main(String[] args)`
| Campo | Detalle |
|---|---|
| **Descripción** | Valida los argumentos y coordina el proceso de desensamblado. |
| **Parámetros** | `args[0]` – Flag `-d` |
| | `args[1]` – Nombre del archivo `.hack` de entrada |
| **Salida** | Archivo `NombreDis.asm` con el programa traducido |
| **Errores** | Número de argumentos incorrecto, flag inválido, extensión incorrecta, archivo no encontrado |

---

### `traduccionTipoA(char[] bits)`
| Campo | Detalle |
|---|---|
| **Descripción** | Traduce una instrucción tipo A (primer bit = `0`) a su representación en assembler. |
| **Parámetros** | `bits` – Array de 16 caracteres `'0'` o `'1'` |
| **Retorna** | `String` con formato `@valor`, donde `valor` es el decimal de los 15 bits restantes |
| **Ejemplo** | `0000000000010101` → `@21` |

---

### `traduccionTipoC(char[] bits)`
| Campo | Detalle |
|---|---|
| **Descripción** | Traduce una instrucción tipo C (primer bit = `1`) a su representación en assembler. Valida que los primeros 3 bits sean `111`. |
| **Parámetros** | `bits` – Array de 16 caracteres `'0'` o `'1'` |
| **Retorna** | `String` con formato `dest=comp;jump` (omitiendo `dest=` o `;jump` si están vacíos) |
| **Lanza** | `Exception` si los primeros 3 bits no son `111` o si algún campo es inválido |
| **Ejemplo** | `1110110000010000` → `D=A` |

---

### `traducirComp(char[] compB, char a)`
| Campo | Detalle |
|---|---|
| **Descripción** | Traduce los 6 bits del campo `comp` usando el bit `a` para determinar si la operación involucra el registro `A` o `M`. |
| **Parámetros** | `compB` – Array de 6 caracteres (`c1` a `c6`) |
| | `a` – Carácter `'0'` (usa `A`) o `'1'` (usa `M`) |
| **Retorna** | `String` con la operación (`"D+A"`, `"M"`, `"0"`, etc.) |
| **Lanza** | `Exception` si la combinación de bits no corresponde a ninguna operación válida |

---

### `traducirDest(char[] destB)`
| Campo | Detalle |
|---|---|
| **Descripción** | Traduce los 3 bits del campo `dest` al registro de destino correspondiente. |
| **Parámetros** | `destB` – Array de 3 caracteres (`d1`, `d2`, `d3`) |
| **Retorna** | `String` con el destino (`"A"`, `"D"`, `"M"`, `"AMD"`, etc.) o `""` si no hay destino |
| **Lanza** | `Exception` si la combinación de bits no es válida |

| Bits | Resultado |
|---|---|
| `000` | *(vacío)* |
| `001` | `M` |
| `010` | `D` |
| `011` | `MD` |
| `100` | `A` |
| `101` | `AM` |
| `110` | `AD` |
| `111` | `AMD` |

---

### `traducirJump(char[] bits)`
| Campo | Detalle |
|---|---|
| **Descripción** | Traduce los 3 bits del campo `jump` a la condición de salto correspondiente. |
| **Parámetros** | `bits` – Array de 3 caracteres (`j1`, `j2`, `j3`) |
| **Retorna** | `String` con la condición de salto (`"JGT"`, `"JMP"`, etc.) o `""` si no hay salto |
| **Lanza** | `Exception` si la combinación de bits no es válida |

| Bits | Resultado |
|---|---|
| `000` | *(vacío)* |
| `001` | `JGT` |
| `010` | `JEQ` |
| `011` | `JGE` |
| `100` | `JLT` |
| `101` | `JNE` |
| `110` | `JLE` |
| `111` | `JMP` |

---

## Estructura de una instrucción de 16 bits

```
Tipo A:
┌─────┬───────────────────────────────┐
│  0  │     valor (15 bits)           │
└─────┴───────────────────────────────┘
  [0]              [1–15]

Tipo C:
┌─────┬─────┬─────┬─────┬──────────────────┬───────────┬───────────┐
│  1  │  1  │  1  │  a  │  c1 c2 c3 c4 c5 c6│  d1 d2 d3 │  j1 j2 j3 │
└─────┴─────┴─────┴─────┴──────────────────┴───────────┴───────────┘
  [0]  [1]  [2]  [3]        [4–9]              [10–12]     [13–15]
```