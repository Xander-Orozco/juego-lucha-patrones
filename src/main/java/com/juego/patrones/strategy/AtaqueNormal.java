package com.juego.patrones.strategy;

import com.juego.model.Personaje;
import java.util.Random;

/**
 * PATRÓN STRATEGY — Ataque Normal.
 * Daño aleatorio estándar entre 10 y 30 puntos. Nunca falla.
 */
public class AtaqueNormal implements EstrategiaAtaque {

    private static final int MIN_DANO = 10;
    private static final int MAX_DANO = 30;
    private final Random random;

    public AtaqueNormal()              { this.random = new Random(); }
    public AtaqueNormal(Random random) { this.random = random; }

    @Override
    public int ejecutarAtaque(Personaje atacante, Personaje defensor) {
        int dano = random.nextInt(MAX_DANO - MIN_DANO + 1) + MIN_DANO;
        defensor.recibirDano(dano);
        System.out.printf("⚔️  %s usa [%s] sobre %s causando %d puntos de daño.%n",
                atacante.getNombre(), getNombre(), defensor.getNombre(), dano);
        return dano;
    }

    @Override
    public String getNombre() { return "Ataque Normal"; }
}
