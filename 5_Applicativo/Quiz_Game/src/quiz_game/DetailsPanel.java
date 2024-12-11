/*
 * Classe DetailsPanel
 * Questa classe gestisce la visualizzazione dei dettagli del quiz, inclusi le domande,
 * le risposte dell'utente e le risposte corrette.
 */
package quiz_game;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Leonardo Sciara
 */

class DetailsPanel extends JFrame {

    public DetailsPanel(List<Question> questions, List<List<Integer>> userAnswers) {
        setTitle("Dettagli Quiz"); // Imposta il titolo della finestra
        setSize(800, 600); // Imposta le dimensioni della finestra
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Chiude l'applicazione alla chiusura della finestra
        setLayout(new BorderLayout()); // Imposta il layout principale

        // Pannello principale per i dettagli
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS)); // Layout verticale per gli elementi

        // Itera su ogni domanda per mostrare i dettagli
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i); // Ottiene la domanda corrente
            List<Integer> userAnswer = userAnswers.get(i); // Ottiene le risposte dell'utente
            List<Integer> correctAnswers = question.getCorrectAnswers(); // Ottiene le risposte corrette

            // Testo della domanda
            String questionText = (i + 1) + ". " + question.getText();
            // Testo delle risposte date dall'utente
            String userAnswerText = "Risposte date: " + formatAnswers(question.getOptions(), userAnswer);
            // Testo delle risposte corrette
            String correctAnswerText = "Risposte corrette: " + formatAnswers(question.getOptions(), correctAnswers);

            // Etichetta HTML per visualizzare la domanda e le risposte
            JLabel questionLabel = new JLabel("<html>" + questionText + "<br>" + userAnswerText + "<br>" + correctAnswerText + "</html>");
            questionLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Aggiunge un margine intorno al testo
            detailsPanel.add(questionLabel); // Aggiunge l'etichetta al pannello
        }

        // Pannello scrollabile per contenere i dettagli (utile se ci sono molte domande)
        JScrollPane scrollPane = new JScrollPane(detailsPanel);
        add(scrollPane, BorderLayout.CENTER); // Aggiunge il pannello scrollabile al centro della finestra

        // Pulsante "Indietro" per chiudere la finestra
        JButton backButton = new JButton("Indietro");
        backButton.addActionListener(e -> setVisible(false)); // Chiude la finestra quando viene premuto
        add(backButton, BorderLayout.SOUTH); // Aggiunge il pulsante nella parte inferiore

        setVisible(true); // Rende visibile la finestra
    }

    public String formatAnswers(List<String> options, List<Integer> answers) {
        if (answers.isEmpty()) {
            return "Nessuna risposta"; // Ritorna un messaggio se non ci sono risposte
        }
        List<String> formatted = new ArrayList<>(); // Lista per le risposte formattate
        for (int index : answers) {
            if (index >= 0 && index < options.size()) {
                formatted.add(options.get(index)); // Aggiunge la risposta corrispondente all'indice
            }
        }
        return String.join(", ", formatted); // Combina le risposte con una virgola
    }
}
