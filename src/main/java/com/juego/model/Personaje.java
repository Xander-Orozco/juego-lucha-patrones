package com.juego.model;

/**
 * Clase base que representa un personaje del juego de lucha.
 */
public class Personaje {

    private final String nombre;
    private int puntosDeVida;
    private final int puntosDeVidaMaximos;
    private final int defensa;
    private final String tipo;

    public Personaje(String nombre, int puntosDeVida, int defensa, String tipo) {
        if (nombre == null || nombre.isBlank())
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacío");
        if (puntosDeVida <= 0)
            throw new IllegalArgumentException("Los puntos de vida deben ser positivos");
        if (defensa < 0)
            throw new IllegalArgumentException("La defensa no puede ser negativa");

        this.nombre = nombre;
        this.puntosDeVida = puntosDeVida;
        this.puntosDeVidaMaximos = puntosDeVida;
        this.defensa = defensa;
        this.tipo = tipo;
    }

    public void recibirDano(int dano) {
        if (dano < 0) return;
        int danoReal = Math.max(0, dano - this.defensa);
        this.puntosDeVida = Math.max(0, this.puntosDeVida - danoReal);
    }

    public boolean estaVivo() {
        return this.puntosDeVida > 0;
    }

    // Getters
    public String getNombre()            { return nombre; }
    public int getPuntosDeVida()         { return puntosDeVida; }
    public int getPuntosDeVidaMaximos()  { return puntosDeVidaMaximos; }
    public int getDefensa()              { return defensa; }
    public String getTipo()              { return tipo; }

    @Override
    public String toString() {
        return String.format("[%s] %s | HP: %d/%d | DEF: %d",
                tipo, nombre, puntosDeVida, puntosDeVidaMaximos, defensa);
    }
}
