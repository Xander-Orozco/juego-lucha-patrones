package com.juego.model;

import com.juego.patrones.strategy.AtaqueNormal;
import com.juego.patrones.strategy.EstrategiaAtaque;

/**
 * Personaje luchador que delega su comportamiento de ataque
 * a una {@link EstrategiaAtaque} intercambiable (patrón Strategy).
 */
public class PersonajeLuchador extends Personaje {

    private EstrategiaAtaque estrategiaAtaque;

    public PersonajeLuchador(String nombre, int puntosDeVida, int defensa, String tipo) {
        super(nombre, puntosDeVida, defensa, tipo);
        this.estrategiaAtaque = new AtaqueNormal();
    }

    /** Cambia la estrategia de ataque en tiempo de ejecución. */
    public void setEstrategiaAtaque(EstrategiaAtaque estrategia) {
        if (estrategia == null)
            throw new IllegalArgumentException("La estrategia de ataque no puede ser nula");
        this.estrategiaAtaque = estrategia;
    }

    public EstrategiaAtaque getEstrategiaAtaque() { return estrategiaAtaque; }

    public int atacar(PersonajeLuchador oponente) {
        if (oponente == null)
            throw new IllegalArgumentException("El oponente no puede ser nulo");
        return estrategiaAtaque.ejecutarAtaque(this, oponente);
    }
}
