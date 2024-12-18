/*
 * Classe LeaderboardManager
 * Classe che gestisce la classifica del quiz
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
    // percorso file che contiene classifica
    private static final Path LEADERBOARD_FILE = Paths.get("src/quiz_game/leaderboard.txt");

    public static List<PlayerScore> loadLeaderboard() {
        List<PlayerScore> leaderboard = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(LEADERBOARD_FILE);
            for (String line : lines) {
                String[] parts = line.split("\\|");
                String name = parts[0];
                int score = Integer.parseInt(parts[1]);
                leaderboard.add(new PlayerScore(name, score));
            }
        } catch (IOException e) {
            System.err.println("Errore durante il caricamento della classifica");
        }
        return leaderboard;
    }

    public static void saveLeaderboard(List<PlayerScore> leaderboard) {
        try {
            // ChatGPT
            // converte leaderboard in una lista di stringhe formattate
            // per ogni elemento della leaderboard si estrae nome e punteggio
            List<String> lines = leaderboard.stream()
                    .map(score -> score.getName() + "|" + score.getScore())
                    .collect(Collectors.toList());
            Files.write(LEADERBOARD_FILE, lines);
        } catch (IOException e) {
            System.err.println("Errore durante il salvataggio della classifica");
        }
    }

    public static List<PlayerScore> updateLeaderboard(String playerName, int score) {
        List<PlayerScore> leaderboard = loadLeaderboard(); // carica classifica esistente
        leaderboard.add(new PlayerScore(playerName, score)); // aggiunge nuovo gicatore

        // ordina la classifica in ordine decrescente, ChatGPT
        leaderboard.sort((a, b) -> Integer.compare(b.getScore(), a.getScore()));

        // mostra primi 10 giocatori
        if (leaderboard.size() > 10) {
            leaderboard = leaderboard.subList(0, 10);
        }

        saveLeaderboard(leaderboard);
        return leaderboard;
    }
}
