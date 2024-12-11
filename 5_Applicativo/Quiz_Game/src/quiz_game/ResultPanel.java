/*
 * Classe ResultPanel
 * Restituisce i punteggi che hai totalizzato e la classifica generale
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

    public ResultPanel(int score, List<Question> questions, List<List<Integer>> userAnswers) {
        setTitle("Risultati");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(250, 240, 230));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        String tScore = "";
        if(score == 1){
            tScore = "punto";
        }
        else{
            tScore = "punti";
        }
        JLabel resultLabel = new JLabel("Hai totalizzato: " + score + " " + tScore);
        resultLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        resultLabel.setForeground(new Color(50, 100, 50)); // Colore verde per evidenziare il punteggio
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // occupa 2 colonne
        add(resultLabel, gbc);

        JLabel leaderboardLabel = new JLabel("Classifica:");
        leaderboardLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        gbc.gridy = 1;
        add(leaderboardLabel, gbc);

        JPanel leaderboardPanel = new JPanel(new GridLayout(0, 2)); // layout per nome e punteggio
        leaderboardPanel.setBackground(new Color(250, 240, 230));
        List<PlayerScore> leaderboard = LeaderboardManager.updateLeaderboard(getPlayerName(), score); // Aggiorna la classifica

        // Aggiunge i nomi e i punteggi al pannello della classifica
        for (PlayerScore entry : leaderboard) {
            JLabel nameLabel = new JLabel(entry.getName()); // nome giocatore
            nameLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
            JLabel scoreLabel = new JLabel(String.valueOf(entry.getScore())); // punteggio giocatore
            scoreLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
            leaderboardPanel.add(nameLabel);
            leaderboardPanel.add(scoreLabel);
        }

        // pannello scrollabile per la classifica
        JScrollPane scrollPane = new JScrollPane(leaderboardPanel);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0; // permette al pannello di espandersi verticalmente
        add(scrollPane, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(250, 240, 230)); 

        JButton detailsButton = new JButton("Visualizza Dettagli");
        detailsButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        detailsButton.setBackground(new Color(70, 130, 180));
        detailsButton.setForeground(Color.WHITE);
        detailsButton.addActionListener(e -> new DetailsPanel(questions, userAnswers)); // Apre il pannello dei dettagli
        buttonPanel.add(detailsButton);

        JButton newGameButton = new JButton("Nuova Partita");
        newGameButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        newGameButton.setBackground(new Color(70, 130, 180));
        newGameButton.setForeground(Color.WHITE);
        newGameButton.addActionListener(e -> {
            setVisible(false);; // Chiude il pannello dei risultati
            new SettingsPanel(); // Apre il pannello delle impostazioni
        });
        buttonPanel.add(newGameButton);

        JButton newPlayerButton = new JButton("Nuovo Giocatore");
        newPlayerButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        newPlayerButton.setBackground(new Color(70, 130, 180));
        newPlayerButton.setForeground(Color.WHITE);
        newPlayerButton.addActionListener(e -> {
            try {
                // Cancella il contenuto del file username.txt
                Files.write(Paths.get("src/prova3/username.txt"), "".getBytes());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Errore durante il reset del nome utente");
            }
            setVisible(false); // Chiude la finestra attuale
            new UsernamePanel(); // Torna al pannello per l'inserimento del nome utente
        });
        buttonPanel.add(newPlayerButton);

        // Pulsante "Chiudi Gioco"
        JButton exitButton = new JButton("Chiudi Gioco");
        exitButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        exitButton.setBackground(new Color(200, 50, 50)); // Colore rosso per evidenziare la chiusura
        exitButton.setForeground(Color.WHITE);
        exitButton.addActionListener(e -> System.exit(0)); // Termina l'applicazione
        buttonPanel.add(exitButton);

        gbc.gridy = 3;
        gbc.weighty = 0; // Non si espande verticalmente
        add(buttonPanel, gbc);

        setVisible(true); // Rende visibile la finestra
    }

    public String getPlayerName() {
        try {
            Path path = Paths.get("src/prova3/username.txt");
            if (Files.exists(path)) {
                return Files.readString(path);
            }
        } catch (IOException e) {
            System.out.println("Errore durante il caricamento del nome utente.");
        }
        return "Nome Sconosciuto";
    }
}
