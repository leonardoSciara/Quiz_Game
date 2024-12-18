/*
 * Classe PlayerScore
 * Rappresenta il punteggio e il nome di un giocatore
 */
package quiz_game;

/**
 *
 * @author Leonardo Sciara
 */

class PlayerScore {
    private String name; // nome giocatore
    private int score; // punteggio giocatore

    public PlayerScore(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
}
