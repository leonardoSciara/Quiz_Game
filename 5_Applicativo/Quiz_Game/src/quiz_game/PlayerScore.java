/*
 * Classe PlayerScore
 * Rappresenta il punteggio di un giocatore, includendo il nome del giocatore e il punteggio ottenuto.
 */
package quiz_game;

/**
 *
 * @author Leonardo Sciara
 */

class PlayerScore {
    private String name; // Nome del giocatore
    private int score; // Punteggio del giocatore

    public PlayerScore(String name, int score) {
        this.name = name; // Imposta il nome del giocatore
        this.score = score; // Imposta il punteggio del giocatore
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
}
