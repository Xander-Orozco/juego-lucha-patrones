# ⚔️ Juego de Lucha en Java

![Java CI with Maven](https://github.com/Xander-Orozco/juego-lucha-patrones/actions/workflows/ci.yml/badge.svg)
![Coverage](https://img.shields.io/badge/coverage-≥80%25-brightgreen)
![Java](https://img.shields.io/badge/Java-17-blue)
![Maven](https://img.shields.io/badge/Maven-3.8-orange)
![JUnit5](https://img.shields.io/badge/JUnit-5.10-orange)
![Mockito](https://img.shields.io/badge/Mockito-5.5-blue)

> Proyecto de **Ingeniería de Software II** — Refinamiento arquitectónico de un juego de lucha por turnos aplicando patrones de diseño, pruebas unitarias con JUnit 5 + Mockito e integración continua con GitHub Actions en Codespaces.
>
> **Docente:** Ing. Jhon Haide Cano Beltran MSc.

---

## 📋 Tabla de contenidos

1. [Descripción del proyecto](#descripción-del-proyecto)
2. [Patrones de diseño](#patrones-de-diseño)
3. [Diagrama de clases](#diagrama-de-clases)
4. [Estructura del proyecto](#estructura-del-proyecto)
5. [Pruebas unitarias](#pruebas-unitarias)
6. [Integración continua](#integración-continua)
7. [Cómo ejecutar el proyecto](#cómo-ejecutar-el-proyecto)
8. [Comandos útiles](#comandos-útiles)
9. [Ejemplo de uso](#ejemplo-de-uso)

---

## Descripción del proyecto

El proyecto parte de una implementación básica de un juego de lucha por turnos (dos personajes que se atacan alternativamente hasta que uno cae) y lo refina aplicando dos patrones de diseño clásicos de la familia GoF:

- **Factory Method** (creacional): elimina la creación rígida de personajes con `new` directo.
- **Strategy** (estructural): reemplaza la lógica de ataque fija por algoritmos intercambiables.

El resultado es un sistema extensible donde agregar un nuevo tipo de personaje o un nuevo comportamiento de ataque **no requiere modificar las clases existentes**, siguiendo el principio **Open/Closed (OCP)**.

---

## Patrones de diseño

### 1. Factory Method (Creacional)

**Problema que resuelve:** el código original creaba todos los personajes con `new Personaje("nombre")`, acoplando al cliente con los detalles de construcción e impidiendo diferencias de atributos por tipo.

**Solución:** `PersonajeFactory` centraliza la creación. El cliente solicita un tipo (`TipoPersonaje`) y recibe un `PersonajeLuchador` completamente configurado — con su HP, defensa y estrategia de ataque — sin conocer cómo se construye internamente.

```java
// Sin Factory (código original — frágil)
Personaje p = new Personaje("Thor");   // todos iguales, mismo HP, sin estrategia

// Con Factory Method (refactorizado)
PersonajeLuchador guerrero = PersonajeFactory.crear(TipoPersonaje.GUERRERO, "Thor");
PersonajeLuchador mago     = PersonajeFactory.crear(TipoPersonaje.MAGO,     "Gandalf");
```

| Tipo     | HP  | Defensa | Estrategia de ataque |
|----------|-----|---------|----------------------|
| GUERRERO | 130 | 10      | `AtaqueFuerte`       |
| MAGO     | 80  | 2       | `AtaqueMagico`       |
| ARQUERO  | 100 | 5       | `AtaqueNormal`       |
| PALADIN  | 150 | 15      | `AtaqueNormal`       |

**Participantes UML:**
- `TipoPersonaje` — enum que actúa como selector del producto.
- `PersonajeFactory` — Creator con el método de fábrica `crear()`.
- `PersonajeLuchador` — Producto generado y configurado por la fábrica.

---

### 2. Strategy (Estructural)

**Problema que resuelve:** el ataque original era un único bloque `rand.nextInt(MAX - MIN + 1) + MIN` dentro de `atacar()`. Agregar lógica especial (probabilidad de fallo, penetración de armadura) obligaba a modificar la clase `Personaje`.

**Solución:** la interfaz `EstrategiaAtaque` encapsula el algoritmo de ataque. `PersonajeLuchador` delega la ejecución en la estrategia configurada, que puede cambiarse en tiempo de ejecución sin modificar la clase.

```java
// Cambiar estrategia en runtime sin recompilar PersonajeLuchador
guerrero.setEstrategiaAtaque(new AtaqueMagico());
```

| Estrategia     | Daño    | Particularidad                                |
|----------------|---------|-----------------------------------------------|
| `AtaqueNormal` | 10 – 30 | Siempre acierta, daño estándar                |
| `AtaqueFuerte` | 35 – 55 | 30 % de probabilidad de fallar                |
| `AtaqueMagico` | 20 – 40 | Penetra completamente la defensa del oponente |

**Participantes UML:**
- `EstrategiaAtaque` — interfaz Strategy (contrato común).
- `AtaqueNormal`, `AtaqueFuerte`, `AtaqueMagico` — algoritmos concretos intercambiables.
- `PersonajeLuchador` — Context que delega el ataque en la estrategia activa.

---

## Diagrama de clases

```
com.juego.patrones.strategy
┌─────────────────────────────────┐
│         «interface»             │
│       EstrategiaAtaque          │
│─────────────────────────────────│
│ + ejecutarAtaque(atacante,      │
│     defensor): int              │
│ + getNombre(): String           │
└───────────────┬─────────────────┘
        ╔═══════╩══════════╗
        ▽                  ▽                    ▽
┌───────────────┐  ┌───────────────┐  ┌───────────────┐
│  AtaqueNormal │  │ AtaqueFuerte  │  │  AtaqueMagico │
│───────────────│  │───────────────│  │───────────────│
│ 10–30 daño    │  │ 35–55 daño    │  │ 20–40 daño    │
│ Nunca falla   │  │ 30% fallo     │  │ Ignora defensa│
└───────────────┘  └───────────────┘  └───────────────┘

com.juego.model
┌──────────────────────────────────┐
│             Personaje            │
│──────────────────────────────────│
│ - nombre: String                 │
│ - puntosDeVida: int              │
│ - puntosDeVidaMaximos: int       │
│ - defensa: int                   │
│ - tipo: String                   │
│──────────────────────────────────│
│ + recibirDano(dano: int): void   │
│ + estaVivo(): boolean            │
│ + getNombre(): String            │
│ + getPuntosDeVida(): int         │
│ + getPuntosDeVidaMaximos(): int  │
│ + getDefensa(): int              │
│ + getTipo(): String              │
│ + toString(): String             │
└──────────────────┬───────────────┘
                   △ herencia
┌──────────────────┴───────────────────────┐
│           PersonajeLuchador              │
│──────────────────────────────────────────│
│ - estrategiaAtaque: EstrategiaAtaque ◇──►  EstrategiaAtaque
│──────────────────────────────────────────│
│ + setEstrategiaAtaque(e): void           │
│ + getEstrategiaAtaque(): EstrategiaAtaque│
│ + atacar(oponente): int                  │
└──────────────────────────────────────────┘
         ▲ crea y configura
         │
com.juego.patrones.factory
┌──────────────────────────────────────┐
│          PersonajeFactory            │
│──────────────────────────────────────│
│ + crear(tipo: TipoPersonaje,         │
│         nombre: String)              │
│         : PersonajeLuchador          │
└──────────────────┬───────────────────┘
                   │ usa
┌──────────────────┴───────────────────┐
│          TipoPersonaje «enum»        │
│──────────────────────────────────────│
│  GUERRERO  │  MAGO  │  ARQUERO  │ PALADIN  │
└──────────────────────────────────────┘

com.juego.juego
┌────────────────────────────────────────────┐
│                JuegoLucha                  │
│────────────────────────────────────────────│
│ - personaje1: PersonajeLuchador        ◇──►  PersonajeLuchador
│ - personaje2: PersonajeLuchador        ◇──►  PersonajeLuchador
│ - turno: int                               │
│ - ganador: PersonajeLuchador               │
│────────────────────────────────────────────│
│ + ejecutarTurno(): boolean                 │
│ + ejecutarBatallaCompleta(): PersonajeLuchador │
│ + haTerminado(): boolean                   │
│ + getPersonaje1(): PersonajeLuchador       │
│ + getPersonaje2(): PersonajeLuchador       │
│ + getTurno(): int                          │
│ + getGanador(): PersonajeLuchador          │
└────────────────────────────────────────────┘

Relaciones:
  △  herencia (extends)
  ▽  implementación (implements)
  ◇► composición (tiene una referencia)
  ──► uso / creación
```

---

## Estructura del proyecto

```
juego-lucha-patrones/
├── .github/
│   └── workflows/
│       └── ci.yml                         # Pipeline CI/CD con GitHub Actions
├── src/
│   ├── main/java/com/juego/
│   │   ├── model/
│   │   │   ├── Personaje.java             # Clase base del personaje
│   │   │   └── PersonajeLuchador.java     # Context del patrón Strategy
│   │   ├── patrones/
│   │   │   ├── factory/
│   │   │   │   ├── TipoPersonaje.java     # Enum con los tipos disponibles
│   │   │   │   └── PersonajeFactory.java  # Factory Method
│   │   │   └── strategy/
│   │   │       ├── EstrategiaAtaque.java  # Interfaz Strategy
│   │   │       ├── AtaqueNormal.java      # Estrategia concreta — daño estándar
│   │   │       ├── AtaqueFuerte.java      # Estrategia concreta — alto daño con fallo
│   │   │       └── AtaqueMagico.java      # Estrategia concreta — ignora defensa
│   │   ├── juego/
│   │   │   └── JuegoLucha.java            # Motor de la batalla por turnos
│   │   └── Main.java                      # Punto de entrada y demostración
│   └── test/java/com/juego/
│       ├── model/
│       │   ├── PersonajeTest.java         # 11 tests — modelo base
│       │   └── PersonajeLuchadorTest.java # 7 tests  — Strategy + Mockito
│       ├── patrones/
│       │   ├── FactoryTest.java           # 8 tests  — Factory Method
│       │   └── StrategyTest.java          # 12 tests — las tres estrategias
│       └── juego/
│           └── JuegoLuchaTest.java        # 10 tests — flujo de batalla
├── pom.xml                                # Dependencias Maven + JaCoCo
└── README.md
```

---

## Pruebas unitarias

Las pruebas cubren métodos individuales, casos borde, validaciones y el comportamiento de cada patrón de diseño usando JUnit 5 y Mockito.

| Clase de prueba          | Tests | Qué verifica                                           |
|--------------------------|-------|--------------------------------------------------------|
| `PersonajeTest`          | 11    | HP, daño, defensa, curación, validaciones, `toString`  |
| `PersonajeLuchadorTest`  | 7     | Estrategia por defecto, cambio en runtime, Mockito     |
| `FactoryTest`            | 8     | Cada tipo, validaciones, instancias independientes     |
| `StrategyTest`           | 12    | Rangos de daño, penetración mágica, `@RepeatedTest`    |
| `JuegoLuchaTest`         | 10    | Turnos, ganador, flujo completo de batalla             |
| **Total**                | **48**| —                                                      |

### Técnicas aplicadas

- `@BeforeEach` — inicialización común de objetos antes de cada test.
- `@ParameterizedTest` + `@ValueSource` — mismo test con múltiples valores de entrada.
- `@RepeatedTest` — verificar comportamientos aleatorios en múltiples ejecuciones.
- `@Mock` + `when()` + `verify()` (Mockito) — aislar la dependencia de `EstrategiaAtaque` en `PersonajeLuchador`.
- Casos borde: daño negativo, HP que llega a cero, defensa mayor que el daño, nombre vacío o nulo.

### Cobertura

JaCoCo está configurado en el `pom.xml`. El reporte HTML se genera en `target/site/jacoco/index.html`:

```bash
mvn jacoco:report
```

---

## Integración continua

El pipeline `.github/workflows/ci.yml` se ejecuta automáticamente en cada `push` a `main`/`develop` y en cada Pull Request:

```
push / PR
    │
    ▼
actions/checkout@v4
    │
    ▼
actions/setup-java@v4  (JDK 17 Temurin)
    │
    ▼
mvn clean compile
    │
    ▼
mvn test
    │
    ▼
mvn jacoco:report
    │
    ├──► upload-artifact@v4  →  coverage-report/  (descargable desde Actions)
    └──► upload-artifact@v4  →  test-results/      (descargable desde Actions)
```

> Todas las actions usan `@v4` para evitar las advertencias de deprecación de Node.js 16/20 introducidas por GitHub en 2024.

---

## Cómo ejecutar el proyecto

### En GitHub Codespaces (recomendado)

1. Abrir el repositorio → `Code` → `Codespaces` → `Create codespace on main`
2. Esperar a que el entorno cargue (~1-2 min)
3. Ejecutar en la terminal integrada:

```bash
# Compilar
mvn clean compile

# Ejecutar todas las pruebas
mvn test

# Generar reporte de cobertura
mvn jacoco:report
# Ver en: target/site/jacoco/index.html

# Ejecutar el juego
mvn exec:java -Dexec.mainClass="com.juego.Main"
```

### En local (requiere Java 17+ y Maven 3.8+)

```bash
git clone https://github.com/Xander-Orozco/juego-lucha-patrones.git
cd juego-lucha-patrones
mvn clean test
```

---

## Comandos útiles

| Comando | Descripción |
|---|---|
| `mvn clean compile` | Compilar el proyecto desde cero |
| `mvn test` | Ejecutar todas las pruebas |
| `mvn test -Dtest=FactoryTest` | Ejecutar una clase de prueba específica |
| `mvn test -Dtest=StrategyTest#testAtaqueNormalRango` | Ejecutar un test específico |
| `mvn jacoco:report` | Generar reporte HTML de cobertura |
| `cat target/surefire-reports/*.txt` | Ver resultados detallados en consola |
| `git add . && git commit -m "msg" && git push` | Subir cambios y disparar el pipeline |
| `java -version` | Verificar versión de Java instalada |
| `mvn -version` | Verificar versión de Maven instalada |

---

## Ejemplo de uso

```java
// ── Factory Method: tipos predefinidos, configuración automática ─────────────
PersonajeLuchador guerrero = PersonajeFactory.crear(TipoPersonaje.GUERRERO, "Thor");
PersonajeLuchador mago     = PersonajeFactory.crear(TipoPersonaje.MAGO,     "Gandalf");

System.out.println(guerrero);
// [GUERRERO] Thor | HP: 130/130 | DEF: 10

System.out.println(guerrero.getEstrategiaAtaque().getNombre());
// Ataque Fuerte

// ── Strategy: cambiar algoritmo en tiempo de ejecución ──────────────────────
guerrero.setEstrategiaAtaque(new AtaqueMagico());
System.out.println(guerrero.getEstrategiaAtaque().getNombre());
// Ataque Mágico

// ── Batalla completa ─────────────────────────────────────────────────────────
PersonajeLuchador p1 = PersonajeFactory.crear(TipoPersonaje.GUERRERO, "Leonidas");
PersonajeLuchador p2 = PersonajeFactory.crear(TipoPersonaje.PALADIN,  "Arthas");

JuegoLucha batalla = new JuegoLucha(p1, p2);
batalla.ejecutarBatallaCompleta();

// ═══════════════════════════════════════
// ⚔️   INICIO DE BATALLA
//    Leonidas  vs  Arthas
// ═══════════════════════════════════════
// --- Turno 1 ---
// 💥 Leonidas usa [Ataque Fuerte] sobre Arthas causando 48 puntos de daño.
// ⚔️  Arthas usa [Ataque Normal] sobre Leonidas causando 22 puntos de daño.
// ...
// ═══════════════════════════════════════
// 🏆 ¡Leonidas gana la batalla!
// ═══════════════════════════════════════
```

---

## 👥 Autores

> Xander Orozco | Edward Pantoja | Estefania Mamian

> Proyecto desarrollado para **Ingeniería de Software II** — Institución Universitaria Antonio José Camacho.

> Docente: Ing. Jhon Haide Cano Beltran MSc.
