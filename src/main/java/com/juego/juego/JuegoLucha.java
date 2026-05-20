package com.juego.juego;

import com.juego.model.PersonajeLuchador;

/**
 * Motor del juego de lucha por turnos.
 */
public class JuegoLucha {

    private final PersonajeLuchador personaje1;
    private final PersonajeLuchador personaje2;
    private int turno;
    private PersonajeLuchador ganador;

    public JuegoLucha(PersonajeLuchador personaje1, PersonajeLuchador personaje2) {
        if (personaje1 == null || personaje2 == null)
            throw new IllegalArgumentException("Los personajes no pueden ser nulos");
        this.personaje1 = personaje1;
        this.personaje2 = personaje2;
        this.turno  = 1;
        this.ganador = null;
    }

    /**
     * Ejecuta un turno: p1 ataca, luego p2 contraataca si sigue vivo.
     * @return true si la batalla puede continuar; false si ha terminado.
     */
    public boolean ejecutarTurno() {
        if (haTerminado()) return false;

        System.out.println("\n--- Turno " + turno + " ---");
        System.out.println(personaje1);
        System.out.println(personaje2);

        personaje1.atacar(personaje2);
        if (personaje2.estaVivo()) {
            personaje2.atacar(personaje1);
        }

        turno++;
        determinarGanador();
        return !haTerminado();
    }

    /** Ejecuta la batalla completa hasta que un personaje caiga. */
    public PersonajeLuchador ejecutarBatallaCompleta() {
        System.out.println("═══════════════════════════════════════");
        System.out.println("⚔️   INICIO DE BATALLA");
        System.out.printf("   %s  vs  %s%n", personaje1.getNombre(), personaje2.getNombre());
        System.out.println("═══════════════════════════════════════");

        while (!haTerminado()) ejecutarTurno();

        System.out.println("\n═══════════════════════════════════════");
        if (ganador != null)
            System.out.println("🏆 ¡" + ganador.getNombre() + " gana la batalla!");
        else
            System.out.println("💀 ¡Empate! Ambos han caído.");
        System.out.println("═══════════════════════════════════════");

        return ganador;
    }

    public boolean haTerminado() {
        return !personaje1.estaVivo() || !personaje2.estaVivo();
    }

    private void determinarGanador() {
        if (!personaje1.estaVivo() && !personaje2.estaVivo()) ganador = null;
        else if (!personaje1.estaVivo()) ganador = personaje2;
        else if (!personaje2.estaVivo()) ganador = personaje1;
    }

    // Getters para pruebas
    public PersonajeLuchador getPersonaje1() { return personaje1; }
    public PersonajeLuchador getPersonaje2() { return personaje2; }
    public int getTurno()                    { return turno; }
    public PersonajeLuchador getGanador()    { return ganador; }
}
