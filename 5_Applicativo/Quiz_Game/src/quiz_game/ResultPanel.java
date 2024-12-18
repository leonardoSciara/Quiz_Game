/*
 * Classe ResultPanel
 * Classe che gestisce i risultati dei vari giocatori
 */
package quiz_game;

import java.awt.*;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import javax.swing.*;

/**
 *
 * @author Leonardo Sciara
 */

class ResultPanel extends JFrame {

    public ResultPanel(int score, List<Question> questions, List<List<Integer>> userAnswers, List<Integer> questionScores) {
        setTitle("Risultati");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(250, 240, 230));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel resultLabel = new JLabel("Hai totalizzato: " + score + " punti");
        resultLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        resultLabel.setForeground(new Color(50, 100, 50));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(resultLabel, gbc);

        JLabel leaderboardLabel = new JLabel("Classifica:");
        leaderboardLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        gbc.gridy = 1;
        add(leaderboardLabel, gbc);

        JPanel leaderboardPanel = new JPanel(new GridLayout(0, 2));
        leaderboardPanel.setBackground(new Color(250, 240, 230));
        // aggiorna la leaderboard passando nome e punteggio
        List<PlayerScore> leaderboard = LeaderboardManager.updateLeaderboard(getPlayerName(), score);

        for (PlayerScore entry : leaderboard) {
            JLabel nameLabel = new JLabel(entry.getName());
            nameLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
            JLabel scoreLabel = new JLabel(String.valueOf(entry.getScore()));
            scoreLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
            leaderboardPanel.add(nameLabel);
            leaderboardPanel.add(scoreLabel);
        }

        // ChatGPT
        //aggiunge capacità di scorrimento a leaderboardPanel
        JScrollPane scrollPanel = new JScrollPane(leaderboardPanel);
        // imposta bordo visivo
        scrollPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH; // espande in verticale e orizzontale
        gbc.weighty = 1.0;
        add(scrollPanel, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(250, 240, 230));

        JButton detailsButton = new JButton("Visualizza Dettagli");
        detailsButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        detailsButton.setBackground(new Color(70, 130, 180));
        detailsButton.setForeground(Color.WHITE);

        // listener per visualizzare dettagli delle domande
        // passando domande, risposte e punteggi dell'utente
        detailsButton.addActionListener(e -> new DetailsPanel(questions, userAnswers, questionScores));
        buttonPanel.add(detailsButton);

        JButton newGameButton = new JButton("Nuova Partita");
        newGameButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        newGameButton.setBackground(new Color(70, 130, 180));
        newGameButton.setForeground(Color.WHITE);
        newGameButton.addActionListener(e -> {
            setVisible(false);
            new SettingsPanel();
        });
        buttonPanel.add(newGameButton);
        
        JButton newPlayer = new JButton("Nuova Giocatroe");
        newPlayer.setFont(new Font("SansSerif", Font.BOLD, 14));
        newPlayer.setBackground(new Color(70, 130, 180));
        newPlayer.setForeground(Color.WHITE);
        newPlayer.addActionListener(e -> {
            setVisible(false);
            new UsernamePanel();
        });
        buttonPanel.add(newPlayer);
        
        JButton exitButton = new JButton("Chiudi Gioco");
        exitButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        exitButton.setBackground(new Color(200, 50, 50));
        exitButton.setForeground(Color.WHITE);
        exitButton.addActionListener(e -> System.exit(0)); // esce dal progranna
        buttonPanel.add(exitButton);

        gbc.gridy = 3;
        gbc.weighty = 0;
        add(buttonPanel, gbc);

        setVisible(true);
    }

    public String getPlayerName() {
        try {
            Path path = Paths.get("src/quiz_game/username.txt");
            List<String> lines = Files.readAllLines(path);
            for (String line : lines){
                return line;
            }
        } catch (IOException e) {
            System.out.println("Errore durante il caricamento del nome");
        }
        return "Nome Sconosciuto";
    }

}
