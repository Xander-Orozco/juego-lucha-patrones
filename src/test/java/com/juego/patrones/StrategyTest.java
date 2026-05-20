package com.juego.patrones;

import com.juego.model.PersonajeLuchador;
import com.juego.patrones.strategy.AtaqueFuerte;
import com.juego.patrones.strategy.AtaqueMagico;
import com.juego.patrones.strategy.AtaqueNormal;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas — Strategy (Estrategias de Ataque)")
class StrategyTest {

    private PersonajeLuchador atacante;
    private PersonajeLuchador defensor;

    @BeforeEach
    void setUp() {
        atacante = new PersonajeLuchador("Atacante", 100, 0, "TEST");
        defensor  = new PersonajeLuchador("Defensor", 200, 0, "TEST");
    }

    // ── AtaqueNormal ──────────────────────────────────────────────────────────
    @Test @DisplayName("AtaqueNormal causa daño entre 10 y 30")
    void testAtaqueNormalRango() {
        AtaqueNormal est = new AtaqueNormal();
        int dano = est.ejecutarAtaque(atacante, defensor);
        assertTrue(dano >= 10 && dano <= 30, "Daño fue: " + dano);
    }

    @RepeatedTest(10)
    @DisplayName("AtaqueNormal siempre en rango válido (10 rep.)")
    void testAtaqueNormalRepetido() {
        int dano = new AtaqueNormal().ejecutarAtaque(atacante, defensor);
        assertTrue(dano >= 10 && dano <= 30);
    }

    @Test @DisplayName("AtaqueNormal nombre correcto")
    void testAtaqueNormalNombre() {
        assertEquals("Ataque Normal", new AtaqueNormal().getNombre());
    }

    @Test @DisplayName("AtaqueNormal reduce HP del defensor")
    void testAtaqueNormalReduceHP() {
        int antes = defensor.getPuntosDeVida();
        new AtaqueNormal().ejecutarAtaque(atacante, defensor);
        assertTrue(defensor.getPuntosDeVida() < antes);
    }

    // ── AtaqueFuerte ──────────────────────────────────────────────────────────
    @Test @DisplayName("AtaqueFuerte retorna 0 (fallo) o daño entre 35 y 55")
    void testAtaqueFuerteRango() {
        int dano = new AtaqueFuerte(new java.util.Random(99)).ejecutarAtaque(atacante, defensor);
        assertTrue(dano == 0 || (dano >= 35 && dano <= 55), "Daño fue: " + dano);
    }

    @RepeatedTest(20)
    @DisplayName("AtaqueFuerte siempre en rango válido (20 rep.)")
    void testAtaqueFuerteRepetido() {
        int dano = new AtaqueFuerte().ejecutarAtaque(atacante, defensor);
        assertTrue(dano == 0 || (dano >= 35 && dano <= 55));
    }

    @Test @DisplayName("AtaqueFuerte nombre correcto")
    void testAtaqueFuerteNombre() {
        assertEquals("Ataque Fuerte", new AtaqueFuerte().getNombre());
    }

    // ── AtaqueMagico ──────────────────────────────────────────────────────────
    @Test @DisplayName("AtaqueMagico causa daño entre 20 y 40")
    void testAtaqueMagicoRango() {
        int dano = new AtaqueMagico(new java.util.Random(7)).ejecutarAtaque(atacante, defensor);
        assertTrue(dano >= 20 && dano <= 40, "Daño fue: " + dano);
    }

    @Test @DisplayName("AtaqueMagico ignora la defensa del defensor")
    void testAtaqueMagicoIgnoraDefensa() {
        PersonajeLuchador defendidoConArmadura = new PersonajeLuchador("Armado", 100, 30, "TEST");
        int antes = defendidoConArmadura.getPuntosDeVida();
        new AtaqueMagico(new java.util.Random(5)).ejecutarAtaque(atacante, defendidoConArmadura);
        assertTrue(defendidoConArmadura.getPuntosDeVida() < antes,
                "El ataque mágico debe reducir HP aunque haya defensa alta");
    }

    @Test @DisplayName("AtaqueMagico nombre correcto")
    void testAtaqueMagicoNombre() {
        assertEquals("Ataque Mágico", new AtaqueMagico().getNombre());
    }

    @RepeatedTest(10)
    @DisplayName("AtaqueMagico siempre en rango 20-40 (10 rep.)")
    void testAtaqueMagicoRepetido() {
        int dano = new AtaqueMagico().ejecutarAtaque(atacante, defensor);
        assertTrue(dano >= 20 && dano <= 40);
    }
}
