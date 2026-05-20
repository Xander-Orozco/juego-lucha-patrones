package com.juego.patrones;

import com.juego.model.PersonajeLuchador;
import com.juego.patrones.factory.PersonajeFactory;
import com.juego.patrones.factory.TipoPersonaje;
import com.juego.patrones.strategy.AtaqueFuerte;
import com.juego.patrones.strategy.AtaqueMagico;
import com.juego.patrones.strategy.AtaqueNormal;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas — Factory Method (PersonajeFactory)")
class FactoryTest {

    @Test @DisplayName("Crea GUERRERO con AtaqueFuerte y HP > 100")
    void testCrearGuerrero() {
        PersonajeLuchador p = PersonajeFactory.crear(TipoPersonaje.GUERRERO, "Thor");
        assertEquals("GUERRERO", p.getTipo());
        assertInstanceOf(AtaqueFuerte.class, p.getEstrategiaAtaque());
        assertTrue(p.getPuntosDeVida() > 100);
        assertTrue(p.getDefensa() > 0);
    }

    @Test @DisplayName("Crea MAGO con AtaqueMagico y menor HP")
    void testCrearMago() {
        PersonajeLuchador p = PersonajeFactory.crear(TipoPersonaje.MAGO, "Gandalf");
        assertEquals("MAGO", p.getTipo());
        assertInstanceOf(AtaqueMagico.class, p.getEstrategiaAtaque());
        assertTrue(p.getPuntosDeVida() < 100);
    }

    @Test @DisplayName("Crea ARQUERO con AtaqueNormal y stats balanceados")
    void testCrearArquero() {
        PersonajeLuchador p = PersonajeFactory.crear(TipoPersonaje.ARQUERO, "Legolas");
        assertEquals("ARQUERO", p.getTipo());
        assertInstanceOf(AtaqueNormal.class, p.getEstrategiaAtaque());
    }

    @Test @DisplayName("Crea PALADIN con alta defensa y máximo HP")
    void testCrearPaladin() {
        PersonajeLuchador p = PersonajeFactory.crear(TipoPersonaje.PALADIN, "Uther");
        assertEquals("PALADIN", p.getTipo());
        assertInstanceOf(AtaqueNormal.class, p.getEstrategiaAtaque());
        assertTrue(p.getDefensa() >= 10);
        assertTrue(p.getPuntosDeVida() >= 150);
    }

    @Test @DisplayName("Lanza excepción con tipo nulo")
    void testTipoNulo() {
        assertThrows(IllegalArgumentException.class,
                () -> PersonajeFactory.crear(null, "X"));
    }

    @Test @DisplayName("Lanza excepción con nombre nulo")
    void testNombreNulo() {
        assertThrows(IllegalArgumentException.class,
                () -> PersonajeFactory.crear(TipoPersonaje.GUERRERO, null));
    }

    @Test @DisplayName("Lanza excepción con nombre vacío")
    void testNombreVacio() {
        assertThrows(IllegalArgumentException.class,
                () -> PersonajeFactory.crear(TipoPersonaje.MAGO, ""));
    }

    @Test @DisplayName("Cada llamada produce una instancia independiente")
    void testInstanciasIndependientes() {
        PersonajeLuchador a = PersonajeFactory.crear(TipoPersonaje.ARQUERO, "A");
        PersonajeLuchador b = PersonajeFactory.crear(TipoPersonaje.ARQUERO, "B");
        assertNotSame(a, b);
    }
}
