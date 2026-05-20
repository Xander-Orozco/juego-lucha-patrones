# ⚔️ Juego de Lucha en Java

![Java CI with Maven](https://github.com/TU_USUARIO/juego-lucha-patrones/actions/workflows/ci.yml/badge.svg)
![Coverage](https://img.shields.io/badge/coverage-≥80%25-brightgreen)
![Java](https://img.shields.io/badge/Java-17-blue)

> Refinamiento arquitectónico con **2 patrones de diseño**: Factory Method (creacional) y Strategy (estructural), pruebas unitarias con JUnit 5 + Mockito, e integración continua con GitHub Actions.

---

## 📐 Patrones de Diseño

### 1. Factory Method (Creacional) — `PersonajeFactory`

Centraliza la creación de personajes. El cliente sólo indica el tipo y recibe un `PersonajeLuchador` completamente configurado; desconoce cómo se construye internamente.

```java
PersonajeLuchador guerrero = PersonajeFactory.crear(TipoPersonaje.GUERRERO, "Thor");
PersonajeLuchador mago     = PersonajeFactory.crear(TipoPersonaje.MAGO,     "Gandalf");
```

| Tipo | HP | DEF | Estrategia asignada |
|---|---|---|---|
| GUERRERO | 130 | 10 | AtaqueFuerte |
| MAGO | 80 | 2 | AtaqueMagico |
| ARQUERO | 100 | 5 | AtaqueNormal |
| PALADIN | 150 | 15 | AtaqueNormal |

### 2. Strategy (Estructural) — `EstrategiaAtaque`

Encapsula los algoritmos de ataque en clases intercambiables. La estrategia puede cambiarse en tiempo de ejecución sin modificar `PersonajeLuchador`.

```java
// Cambiar estrategia en tiempo de ejecución
guerrero.setEstrategiaAtaque(new AtaqueMagico());
```

| Estrategia | Daño | Particularidad |
|---|---|---|
| `AtaqueNormal` | 10 – 30 | Siempre acierta |
| `AtaqueFuerte` | 35 – 55 | 30 % de probabilidad de fallar |
| `AtaqueMagico` | 20 – 40 | Penetra la defensa del oponente |

---

## 🏗️ Estructura

```
src/
├── main/java/com/juego/
│   ├── model/
│   │   ├── Personaje.java
│   │   └── PersonajeLuchador.java      ← usa Strategy
│   ├── patrones/
│   │   ├── factory/
│   │   │   ├── TipoPersonaje.java
│   │   │   └── PersonajeFactory.java   ← Factory Method
│   │   └── strategy/
│   │       ├── EstrategiaAtaque.java   ← interfaz Strategy
│   │       ├── AtaqueNormal.java
│   │       ├── AtaqueFuerte.java
│   │       └── AtaqueMagico.java
│   └── juego/
│       └── JuegoLucha.java
└── test/java/com/juego/
    ├── model/
    │   ├── PersonajeTest.java
    │   └── PersonajeLuchadorTest.java
    ├── patrones/
    │   ├── FactoryTest.java
    │   └── StrategyTest.java
    └── juego/
        └── JuegoLuchaTest.java
```

---

## 🧪 Pruebas

```bash
mvn clean test          # ejecutar pruebas
mvn jacoco:report       # generar reporte HTML → target/site/jacoco/index.html
mvn jacoco:check        # verificar cobertura mínima del 80 %
```

---

## ⚙️ GitHub Actions

El pipeline se dispara en cada push a `main`/`develop` y en Pull Requests:

1. Compila el proyecto
2. Ejecuta todas las pruebas
3. Verifica cobertura ≥ 80 %
4. Sube el reporte JaCoCo como artefacto descargable

---

## 👥 Autores

> Xander Orozco | Edward Pantoja | Estefania Mamian
