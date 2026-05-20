package com.juego.patrones.strategy;

import com.juego.model.Personaje;

/**
 * PATRÓN STRATEGY — Interfaz común para todos los algoritmos de ataque.
 * Permite intercambiar la estrategia en tiempo de ejecución sin cambiar
 * la clase Personaje.
 */
public interface EstrategiaAtaque {
    /**
     * Ejecuta el ataque y aplica el daño al defensor.
     * @return daño bruto calculado (antes de defensa del defensor).
     */
    int ejecutarAtaque(Personaje atacante, Personaje defensor);

    /** Nombre descriptivo de la estrategia. */
    String getNombre();
}
