/*
 * Classe LeaderboardManager
 * Questa classe gestisce il caricamento, il salvataggio e l'aggiornamento della classifica dei punteggi.
 * Utilizza un file di testo per memorizzare i dati in modo persistente.
 */
package quiz_game;

import java.nio.file.*;
import java.io.*;
import java.util.*;
import java.util.stream.*;

/**
 *
 * @author Leonardo Sciara
 */

class LeaderboardManager {
    // Percorso del file che contiene la classifica
    private static final Path LEADERBOARD_FILE = Paths.get("src/prova3/leaderboard.txt");

    public static List<PlayerScore> loadLeaderboard() {
        List<PlayerScore> leaderboard = new ArrayList<>(); // Inizializza la lista dei punteggi
        try {
            // Controlla se il file esiste
            if (Files.exists(LEADERBOARD_FILE)) {
                // Legge tutte le righe dal file
                List<String> lines = Files.readAllLines(LEADERBOARD_FILE);
                for (String line : lines) {
                    String[] parts = line.split("\\|"); // Divide ogni riga in nome e punteggio
                    if (parts.length == 2) { // Verifica che il formato sia corretto
                        String name = parts[0];
                        int score = Integer.parseInt(parts[1]);
                        leaderboard.add(new PlayerScore(name, score)); // Aggiunge un nuovo punteggio
                    }
                }
            }
        } catch (IOException e) {
            // Gestisce eventuali errori di I/O
            System.err.println("Errore durante il caricamento della classifica: " + e.getMessage());
        }
        return leaderboard; // Ritorna la lista dei punteggi
    }

    public static void saveLeaderboard(List<PlayerScore> leaderboard) {
        try {
            // Converte ogni PlayerScore in una stringa formattata
            List<String> lines = leaderboard.stream()
                    .map(score -> score.getName() + "|" + score.getScore()) // Concatena nome e punteggio con "|"
                    .collect(Collectors.toList());
            Files.write(LEADERBOARD_FILE, lines); // Scrive tutte le righe nel file
        } catch (IOException e) {
            // Gestisce eventuali errori di I/O
            System.err.println("Errore durante il salvataggio della classifica: " + e.getMessage());
        }
    }

    public static List<PlayerScore> updateLeaderboard(String playerName, int score) {
        List<PlayerScore> leaderboard = loadLeaderboard(); // Carica la classifica esistente
        leaderboard.add(new PlayerScore(playerName, score)); // Aggiunge il nuovo punteggio

        // Ordina la classifica in base al punteggio in ordine decrescente
        leaderboard.sort((a, b) -> Integer.compare(b.getScore(), a.getScore()));

        // Mantiene solo i primi 10 punteggi
        if (leaderboard.size() > 10) {
            leaderboard = leaderboard.subList(0, 10); // Trunca la lista ai primi 10
        }

        saveLeaderboard(leaderboard); // Salva la classifica aggiornata
        return leaderboard; // Ritorna la classifica aggiornata
    }
}
