package com.juego.model;

import com.juego.patrones.strategy.AtaqueFuerte;
import com.juego.patrones.strategy.AtaqueMagico;
import com.juego.patrones.strategy.AtaqueNormal;
import com.juego.patrones.strategy.EstrategiaAtaque;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas — PersonajeLuchador")
class PersonajeLuchadorTest {

    private PersonajeLuchador luchador;
    private PersonajeLuchador oponente;

    @Mock EstrategiaAtaque estrategiaMock;

    @BeforeEach
    void setUp() {
        luchador = new PersonajeLuchador("Kratos", 100, 5, "GUERRERO");
        oponente  = new PersonajeLuchador("Ares",  100, 0, "DIOS");
    }

    // ── Estrategia por defecto ────────────────────────────────────────────────
    @Test @DisplayName("Estrategia por defecto es AtaqueNormal")
    void testEstrategiaPorDefecto() {
        assertInstanceOf(AtaqueNormal.class, luchador.getEstrategiaAtaque());
    }

    // ── setEstrategiaAtaque (Strategy) ────────────────────────────────────────
    @Test @DisplayName("Cambia estrategia en tiempo de ejecución")
    void testCambiarEstrategia() {
        luchador.setEstrategiaAtaque(new AtaqueFuerte());
        assertInstanceOf(AtaqueFuerte.class, luchador.getEstrategiaAtaque());

        luchador.setEstrategiaAtaque(new AtaqueMagico());
        assertInstanceOf(AtaqueMagico.class, luchador.getEstrategiaAtaque());
    }

    @Test @DisplayName("Lanza excepción con estrategia nula")
    void testEstrategiaNula() {
        assertThrows(IllegalArgumentException.class,
                () -> luchador.setEstrategiaAtaque(null));
    }

    // ── atacar con Mock ───────────────────────────────────────────────────────
    @Test @DisplayName("atacar delega en la estrategia configurada")
    void testAtacarDelegaEnEstrategia() {
        when(estrategiaMock.ejecutarAtaque(luchador, oponente)).thenReturn(20);
        luchador.setEstrategiaAtaque(estrategiaMock);

        int resultado = luchador.atacar(oponente);

        assertEquals(20, resultado);
        verify(estrategiaMock, times(1)).ejecutarAtaque(luchador, oponente);
    }

    @Test @DisplayName("atacar lanza excepción con oponente nulo")
    void testAtacarOponenteNulo() {
        assertThrows(IllegalArgumentException.class, () -> luchador.atacar(null));
    }

    @Test @DisplayName("El ataque efectivamente reduce HP del oponente")
    void testAtaqueReduceHP() {
        luchador.setEstrategiaAtaque(new AtaqueNormal(new java.util.Random(42)));
        int antes = oponente.getPuntosDeVida();
        luchador.atacar(oponente);
        assertTrue(oponente.getPuntosDeVida() < antes);
    }
}
