/*
 * Classe DetailsPanel
 * Classe che si occupa di mostrare i dettagli
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

    public DetailsPanel(List<Question> questions, List<List<Integer>> userAnswers, List<Integer> questionScores) {
        setTitle("Dettagli Quiz");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));

        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            List<Integer> userAnswer = userAnswers.get(i);
            List<Integer> correctAnswers = question.getCorrectAnswers();

            String questionText = (i + 1) + ". " + question.getText();
            String userAnswerText = "Risposta data: " + formatAnswers(question.getOptions(), userAnswer);
            String correctAnswerText = "Risposta corretta: " + formatAnswers(question.getOptions(), correctAnswers);
            String questionScoreText = "Punteggio totalizzato: " + questionScores.get(i);

            JLabel questionLabel = new JLabel("<html>" + questionText + "<br>" + userAnswerText + "<br>" + correctAnswerText + "<br>" + questionScoreText + "</html>");
            questionLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            detailsPanel.add(questionLabel);
        }

        JScrollPane scrollPane = new JScrollPane(detailsPanel);
        add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Indietro");
        backButton.addActionListener(e -> setVisible(false));
        add(backButton, BorderLayout.SOUTH);

        setVisible(true);
    }





    public String formatAnswers(List<String> options, List<Integer> answers) {
        List<String> formatted = new ArrayList<>();
        for (int index : answers) {
            formatted.add(options.get(index)); // aggiunge la risposta corrispondente all'indice
        }
        return String.join(", ", formatted); // combina le risposte con una virgola
    }
}
