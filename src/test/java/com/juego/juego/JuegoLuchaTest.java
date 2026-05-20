package com.juego.juego;

import com.juego.model.PersonajeLuchador;
import com.juego.patrones.factory.PersonajeFactory;
import com.juego.patrones.factory.TipoPersonaje;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas — JuegoLucha")
class JuegoLuchaTest {

    private PersonajeLuchador p1;
    private PersonajeLuchador p2;
    private JuegoLucha juego;

    @BeforeEach
    void setUp() {
        p1   = PersonajeFactory.crear(TipoPersonaje.ARQUERO,  "Robin");
        p2   = PersonajeFactory.crear(TipoPersonaje.ARQUERO,  "Link");
        juego = new JuegoLucha(p1, p2);
    }

    // ── Constructor ───────────────────────────────────────────────────────────
    @Test @DisplayName("Lanza excepción con personajes nulos")
    void testConstructorNulos() {
        assertThrows(IllegalArgumentException.class, () -> new JuegoLucha(null, p2));
        assertThrows(IllegalArgumentException.class, () -> new JuegoLucha(p1, null));
    }

    @Test @DisplayName("Estado inicial: turno 1, no terminado, sin ganador")
    void testEstadoInicial() {
        assertEquals(1, juego.getTurno());
        assertFalse(juego.haTerminado());
        assertNull(juego.getGanador());
    }

    // ── haTerminado ───────────────────────────────────────────────────────────
    @Test @DisplayName("haTerminado es true cuando p1 muere")
    void testHaTerminadoP1Muerto() {
        p1.recibirDano(9999);
        assertTrue(juego.haTerminado());
    }

    @Test @DisplayName("haTerminado es true cuando p2 muere")
    void testHaTerminadoP2Muerto() {
        p2.recibirDano(9999);
        assertTrue(juego.haTerminado());
    }

    // ── ejecutarTurno ─────────────────────────────────────────────────────────
    @Test @DisplayName("ejecutarTurno incrementa el contador de turno")
    void testTurnoIncrementa() {
        juego.ejecutarTurno();
        assertEquals(2, juego.getTurno());
    }

    @Test @DisplayName("ejecutarTurno retorna false si el juego ya terminó")
    void testEjecutarTurnoJuegoTerminado() {
        p1.recibirDano(9999);
        assertFalse(juego.ejecutarTurno());
        assertEquals(1, juego.getTurno()); // no avanza
    }

    @Test @DisplayName("ejecutarTurno retorna false cuando alguien muere en ese turno")
    void testEjecutarTurnoFinalizaBatalla() {
        p2.recibirDano(p2.getPuntosDeVida() - 1); // p2 queda con 1 HP
        boolean continua = juego.ejecutarTurno();
        assertFalse(continua);
    }

    // ── ejecutarBatallaCompleta ───────────────────────────────────────────────
    @Test @DisplayName("La batalla completa termina con un resultado definido")
    void testBatallaCompletaTermina() {
        PersonajeLuchador ganador = juego.ejecutarBatallaCompleta();
        assertTrue(juego.haTerminado());
        assertTrue(ganador == null || ganador == p1 || ganador == p2);
    }

    @Test @DisplayName("El ganador debe estar vivo")
    void testGanadorEstaVivo() {
        PersonajeLuchador ganador = juego.ejecutarBatallaCompleta();
        if (ganador != null) assertTrue(ganador.estaVivo());
    }

    @Test @DisplayName("Al menos un personaje recibe daño durante la batalla")
    void testAlguienRecibioDano() {
        int hpP1 = p1.getPuntosDeVida();
        int hpP2 = p2.getPuntosDeVida();
        juego.ejecutarBatallaCompleta();
        assertTrue(p1.getPuntosDeVida() < hpP1 || p2.getPuntosDeVida() < hpP2);
    }

    // ── Getters ───────────────────────────────────────────────────────────────
    @Test @DisplayName("getPersonaje1 y getPersonaje2 retornan los personajes correctos")
    void testGetters() {
        assertSame(p1, juego.getPersonaje1());
        assertSame(p2, juego.getPersonaje2());
    }
}
