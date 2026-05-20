package com.juego.model;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas — Personaje")
class PersonajeTest {

    private Personaje p;

    @BeforeEach
    void setUp() { p = new Personaje("Thor", 100, 0, "GUERRERO"); }

    // ── Constructor ──────────────────────────────────────────────────────────
    @Test @DisplayName("Crea personaje con atributos correctos")
    void testCreacion() {
        assertEquals("Thor", p.getNombre());
        assertEquals(100, p.getPuntosDeVida());
        assertEquals(100, p.getPuntosDeVidaMaximos());
        assertEquals(0,   p.getDefensa());
        assertEquals("GUERRERO", p.getTipo());
        assertTrue(p.estaVivo());
    }

    @Test @DisplayName("Lanza excepción con nombre nulo")
    void testNombreNulo() {
        assertThrows(IllegalArgumentException.class,
                () -> new Personaje(null, 100, 0, "T"));
    }

    @Test @DisplayName("Lanza excepción con nombre vacío")
    void testNombreVacio() {
        assertThrows(IllegalArgumentException.class,
                () -> new Personaje("  ", 100, 0, "T"));
    }

    @Test @DisplayName("Lanza excepción con HP ≤ 0")
    void testHpInvalido() {
        assertThrows(IllegalArgumentException.class, () -> new Personaje("X", 0,  0, "T"));
        assertThrows(IllegalArgumentException.class, () -> new Personaje("X", -1, 0, "T"));
    }

    @Test @DisplayName("Lanza excepción con defensa negativa")
    void testDefensaNegativa() {
        assertThrows(IllegalArgumentException.class, () -> new Personaje("X", 100, -1, "T"));
    }

    // ── recibirDano ──────────────────────────────────────────────────────────
    @Test @DisplayName("recibirDano reduce HP correctamente")
    void testRecibirDano() {
        p.recibirDano(30);
        assertEquals(70, p.getPuntosDeVida());
    }

    @Test @DisplayName("HP no puede ser negativo")
    void testHpNoNegativo() {
        p.recibirDano(999);
        assertEquals(0, p.getPuntosDeVida());
        assertFalse(p.estaVivo());
    }

    @Test @DisplayName("Daño negativo es ignorado")
    void testDanoNegativoIgnorado() {
        p.recibirDano(-10);
        assertEquals(100, p.getPuntosDeVida());
    }

    @Test @DisplayName("La defensa reduce el daño recibido")
    void testDefensaReduceDano() {
        Personaje armado = new Personaje("Aragorn", 100, 10, "GUERRERO");
        armado.recibirDano(25); // daño real = 25 - 10 = 15
        assertEquals(85, armado.getPuntosDeVida());
    }

    @Test @DisplayName("Si defensa ≥ daño, HP no cambia")
    void testDefensaSuperaDano() {
        Personaje armado = new Personaje("Paladin", 100, 20, "PALADIN");
        armado.recibirDano(15); // daño real = 0
        assertEquals(100, armado.getPuntosDeVida());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 10, 50, 99, 100})
    @DisplayName("Daño válido reduce HP correctamente (sin defensa)")
    void testDanoParametrizado(int dano) {
        p.recibirDano(dano);
        assertEquals(Math.max(0, 100 - dano), p.getPuntosDeVida());
    }

    // ── toString ─────────────────────────────────────────────────────────────
    @Test @DisplayName("toString contiene nombre y tipo")
    void testToString() {
        String s = p.toString();
        assertTrue(s.contains("Thor"));
        assertTrue(s.contains("GUERRERO"));
    }
}
