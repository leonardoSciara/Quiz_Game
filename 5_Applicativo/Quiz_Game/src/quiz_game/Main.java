/*
 * Classe MainApp
 * Classe principale, dove l'utente avvia il programma
 */
package quiz_game;

import javax.swing.SwingUtilities;

/**
 *
 * @author Leonardo Sciara
 */

public class Main {
    public static void main(String[] args) {
        // Creazione di UsernamePanel sul thread dell'interfaccia grafica, ChatGPT
        SwingUtilities.invokeLater(() -> new UsernamePanel());
    }
}
