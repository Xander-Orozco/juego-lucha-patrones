package com.juego.patrones.strategy;

import com.juego.model.Personaje;
import java.util.Random;

/**
 * PATRÓN STRATEGY — Ataque Fuerte.
 * Daño alto entre 35 y 55 puntos, pero con 30 % de probabilidad de fallar.
 */
public class AtaqueFuerte implements EstrategiaAtaque {

    private static final int MIN_DANO    = 35;
    private static final int MAX_DANO    = 55;
    private static final double PROB_FALLO = 0.30;
    private final Random random;

    public AtaqueFuerte()              { this.random = new Random(); }
    public AtaqueFuerte(Random random) { this.random = random; }

    @Override
    public int ejecutarAtaque(Personaje atacante, Personaje defensor) {
        if (random.nextDouble() < PROB_FALLO) {
            System.out.printf("💨 %s usa [%s] sobre %s... ¡pero falla!%n",
                    atacante.getNombre(), getNombre(), defensor.getNombre());
            return 0;
        }
        int dano = random.nextInt(MAX_DANO - MIN_DANO + 1) + MIN_DANO;
        defensor.recibirDano(dano);
        System.out.printf("💥 %s usa [%s] sobre %s causando %d puntos de daño.%n",
                atacante.getNombre(), getNombre(), defensor.getNombre(), dano);
        return dano;
    }

    @Override
    public String getNombre() { return "Ataque Fuerte"; }
}
