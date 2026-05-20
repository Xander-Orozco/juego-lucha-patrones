package com.juego;

import com.juego.juego.JuegoLucha;
import com.juego.model.PersonajeLuchador;
import com.juego.patrones.factory.PersonajeFactory;
import com.juego.patrones.factory.TipoPersonaje;
import com.juego.patrones.strategy.AtaqueMagico;

/**
 * Punto de entrada. Demuestra Factory Method y Strategy en acción.
 */
public class Main {

    public static void main(String[] args) {

        System.out.println("=== JUEGO DE LUCHA — Factory Method + Strategy ===\n");

        // ── Factory Method: el cliente pide el tipo, la fábrica configura todo ──
        PersonajeLuchador guerrero = PersonajeFactory.crear(TipoPersonaje.GUERRERO, "Thor");
        PersonajeLuchador mago    = PersonajeFactory.crear(TipoPersonaje.MAGO,     "Gandalf");
        PersonajeLuchador paladin = PersonajeFactory.crear(TipoPersonaje.PALADIN,  "Arthas");

        System.out.println("Personajes creados:");
        System.out.println("  " + guerrero + " | Estrategia: " + guerrero.getEstrategiaAtaque().getNombre());
        System.out.println("  " + mago     + " | Estrategia: " + mago.getEstrategiaAtaque().getNombre());
        System.out.println("  " + paladin  + " | Estrategia: " + paladin.getEstrategiaAtaque().getNombre());

        // ── Strategy: cambiamos la estrategia del guerrero en tiempo de ejecución ──
        System.out.println("\nGuerrero aprende magia... cambiando estrategia en tiempo de ejecución.");
        guerrero.setEstrategiaAtaque(new AtaqueMagico());
        System.out.println("  Nueva estrategia: " + guerrero.getEstrategiaAtaque().getNombre());

        // ── Batalla ──
        System.out.println();
        PersonajeLuchador p1 = PersonajeFactory.crear(TipoPersonaje.GUERRERO, "Leonidas");
        PersonajeLuchador p2 = PersonajeFactory.crear(TipoPersonaje.PALADIN,  "Uther");
        new JuegoLucha(p1, p2).ejecutarBatallaCompleta();
    }
}
