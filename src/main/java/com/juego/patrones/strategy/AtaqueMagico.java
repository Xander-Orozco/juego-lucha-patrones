package com.juego.patrones.strategy;

import com.juego.model.Personaje;
import java.util.Random;

/**
 * PATRÓN STRATEGY — Ataque Mágico.
 * Daño entre 20 y 40 puntos que penetra la defensa del oponente.
 */
public class AtaqueMagico implements EstrategiaAtaque {

    private static final int MIN_DANO = 20;
    private static final int MAX_DANO = 40;
    private final Random random;

    public AtaqueMagico()              { this.random = new Random(); }
    public AtaqueMagico(Random random) { this.random = random; }

    @Override
    public int ejecutarAtaque(Personaje atacante, Personaje defensor) {
        int dano = random.nextInt(MAX_DANO - MIN_DANO + 1) + MIN_DANO;
        // Penetra armadura: compensamos la defensa para que recibirDano aplique el daño completo
        defensor.recibirDano(dano + defensor.getDefensa());
        System.out.printf("✨ %s usa [%s] sobre %s causando %d daño mágico (ignora defensa).%n",
                atacante.getNombre(), getNombre(), defensor.getNombre(), dano);
        return dano;
    }

    @Override
    public String getNombre() { return "Ataque Mágico"; }
}
