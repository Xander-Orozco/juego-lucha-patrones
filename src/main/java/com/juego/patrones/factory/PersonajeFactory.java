package com.juego.patrones.factory;

import com.juego.model.PersonajeLuchador;
import com.juego.patrones.strategy.AtaqueFuerte;
import com.juego.patrones.strategy.AtaqueMagico;
import com.juego.patrones.strategy.AtaqueNormal;

/**
 * PATRÓN FACTORY METHOD
 * Centraliza la creación de personajes. El cliente solicita un tipo y recibe
 * un {@link PersonajeLuchador} completamente configurado, sin necesidad de
 * conocer cómo se construye ni qué estrategia usa cada tipo.
 */
public class PersonajeFactory {

    private PersonajeFactory() { /* clase utilitaria, no instanciable */ }

    /**
     * Crea un personaje del tipo solicitado.
     *
     * @param tipo   Tipo de personaje (GUERRERO, MAGO, ARQUERO, PALADIN).
     * @param nombre Nombre del personaje.
     * @return {@link PersonajeLuchador} listo para combatir.
     */
    public static PersonajeLuchador crear(TipoPersonaje tipo, String nombre) {
        if (tipo == null)
            throw new IllegalArgumentException("El tipo de personaje no puede ser nulo");
        if (nombre == null || nombre.isBlank())
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacío");

        return switch (tipo) {
            case GUERRERO -> crearGuerrero(nombre);
            case MAGO     -> crearMago(nombre);
            case ARQUERO  -> crearArquero(nombre);
            case PALADIN  -> crearPaladin(nombre);
        };
    }

    // ── Métodos de fábrica específicos ───────────────────────────────────────

    private static PersonajeLuchador crearGuerrero(String nombre) {
        // Alta defensa, mucho HP, golpe fuerte con riesgo de fallo
        PersonajeLuchador p = new PersonajeLuchador(nombre, 130, 10, "GUERRERO");
        p.setEstrategiaAtaque(new AtaqueFuerte());
        return p;
    }

    private static PersonajeLuchador crearMago(String nombre) {
        // Poco HP y defensa, pero su ataque mágico penetra armaduras
        PersonajeLuchador p = new PersonajeLuchador(nombre, 80, 2, "MAGO");
        p.setEstrategiaAtaque(new AtaqueMagico());
        return p;
    }

    private static PersonajeLuchador crearArquero(String nombre) {
        // Stats balanceados, ataque normal confiable
        PersonajeLuchador p = new PersonajeLuchador(nombre, 100, 5, "ARQUERO");
        p.setEstrategiaAtaque(new AtaqueNormal());
        return p;
    }

    private static PersonajeLuchador crearPaladin(String nombre) {
        // Máxima defensa y HP, ataque normal constante
        PersonajeLuchador p = new PersonajeLuchador(nombre, 150, 15, "PALADIN");
        p.setEstrategiaAtaque(new AtaqueNormal());
        return p;
    }
}
