/*
 * Classe QuizPanel
 * Domande che vengono generate sotto richiesta dell'utente
 */

package quiz_game;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 *
 * @author Leonardo Sciara
 */

class QuizPanel extends JFrame {
    private List<Question> questions; // lista delled domande
    private int currentQuestion = 0; // indice per tenere conto della domanda corrente
    private int score = 0; // punteggio

    public QuizPanel(String category, int difficulty, int quantity) {
        setTitle("Quiz");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(240, 240, 255));

        questions = loadQuestions(category, difficulty, quantity);

        displayQuestion(difficulty);
        setVisible(true); // Rende la finestra visibile
    }

    //carica il numero di domande scelte dall'utente in base alla categoria e alla difficoltà sempre scelta dall'utente
    public List<Question> loadQuestions(String category, int difficulty, int quantity) {
        List<Question> allQuestions = new ArrayList<>();
        Path path = Paths.get("src/prova3/questions.txt");

        try {
            List<String> lines = Files.readAllLines(path); // legge tutte le righe del file
            for (String line : lines) { // passa riga per riga
                try {
                    String[] parts = line.split("\\|"); // divide la riga nei vari campi
                    String type = parts[0]; // tipo di pulsanti
                    String questionCategory = parts[1]; // categoria della domanda
                    String questionDifficulty = parts[2]; // difficoltà della domanda
                    String text = parts[3]; // testo della domanda
                    List<String> options = Arrays.asList(parts[4].split(",")); // divide il campo nelle opzioni possibili e li passa sottoforma di lista
                    List<Integer> correctAnswers = new ArrayList<>(); 
                    // essendo che serve una lista di numeri e le risposte sono sottoforma di String,
                    // passa risposta per risposta e le trasforma in Integer per poi passarle alla lista
                    for (String index : parts[5].split(",")) {
                        correctAnswers.add(Integer.parseInt(index));
                    }
                    // aggiunge la domanda alla lista
                    allQuestions.add(new Question(type, questionCategory, questionDifficulty, text, options, correctAnswers));
                } catch (Exception e) {
                    System.out.println("Errore durante il caricamento di una domanda: " + line);
                }
            }

            // aggiunge le domande filtrate a seconda della categoria e della difficoltà che richiede l'utente
            List<Question> filteredQuestions = new ArrayList<>();
            for (Question question : allQuestions) {
                // aggiunge alla lista filtrata le domande che comprendon quel livello di difficoltà e quella categoria
                if (question.getCategory().equals(category) && convertDifficulty(question.getDifficulty()) == difficulty) {
                    filteredQuestions.add(question);
                }
            }

            // mescola le domande 
            Collections.shuffle(filteredQuestions);
            // ritorna il numero di domande, sempre sottoforma di lista, che richiedeva l'utente, ChatGPT
            return filteredQuestions.subList(0, Math.min(quantity, filteredQuestions.size()));

        } catch (IOException e) {
            System.out.println("Errore durante il caricamento del file delle domande");
            return Collections.emptyList();
        }
    }

    public int convertDifficulty(String difficulty) {
        if (difficulty.equals("Facile")) {
            return 1;
        } else if (difficulty.equals("Medio")) {
            return 2;
        } else if (difficulty.equals("Difficile")) {
            return 3;
        } else {
            return 0;
        }
    }

    public void displayQuestion(int diff) {
        if (currentQuestion >= questions.size()) {
            new ResultPanel(score, questions, new ArrayList<>()); // apre schermata risultati
            setVisible(false);
            return;
        }

        // ottiene domanda corrente
        Question question = questions.get(currentQuestion);
        // rimuove tutti i componenti precedenti dalla finestra
        getContentPane().removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL; //componente si espande in larghezza

        JLabel questionLabel = new JLabel((currentQuestion + 1) + ". " + question.getText());
        questionLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(questionLabel, gbc);

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(0, 1));
        ButtonGroup buttonGroup = new ButtonGroup(); 
        List<JCheckBox> checkboxes = new ArrayList<>(); // lista per domande a scelta multipla
        Map<AbstractButton, Integer> buttonIndices = new HashMap<>(); // mappa per associare i pulsanti agli indici

        // mostra tutte le opzioni
        for (int i = 0; i < question.getOptions().size(); i++) {
            String option = question.getOptions().get(i);
            if (question.getType().equals("singola")) {
                JRadioButton radioButton = new JRadioButton(option); // inserisce testo
                radioButton.setActionCommand(String.valueOf(i)); // assegna un comando di azione, che viene rhiamato tramite i, ChatGPT
                buttonGroup.add(radioButton); // aggiunge il bottone al gruppo di bottoni
                optionsPanel.add(radioButton); // aggiunge al panel il bottone 
                buttonIndices.put(radioButton, i); // aggiunge il bottone alla mappa e gli associa il valore
                radioButton.setBackground(new Color(245,245,255));
            } else {
                JCheckBox checkBox = new JCheckBox(option);
                checkboxes.add(checkBox);
                optionsPanel.add(checkBox);
                buttonIndices.put(checkBox, i);
                checkBox.setBackground(new Color(245,245,255));
            }
        }

        gbc.gridy = 1;
        add(optionsPanel, gbc);

        JButton viewAnswerButton = new JButton("Visualizza Risposta");
        viewAnswerButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        viewAnswerButton.setBackground(new Color(0, 0, 0));
        viewAnswerButton.setForeground(new Color(245,245,255));
        viewAnswerButton.setVisible(false);

        // listener per abilitare il pulsante solo quando una risposta è selezionata
        ActionListener enableViewButtonListener = e -> {
            if (question.getType().equals("singola")) {
                viewAnswerButton.setVisible(buttonGroup.getSelection() != null); // controlla se il gruppo di tasti è selezionato
            } else {
                boolean anySelected = checkboxes.stream().anyMatch(AbstractButton::isSelected);
                viewAnswerButton.setEnabled(anySelected);
            }
        };

        // aggiunge il listener ai tasti delle opzioni
        if (question.getType().equals("singola")) {
            Enumeration<AbstractButton> buttons = buttonGroup.getElements();
            while (buttons.hasMoreElements()) {
                buttons.nextElement().addActionListener(enableViewButtonListener);
            }
        } else {
            for (JCheckBox checkBox : checkboxes) {
                checkBox.addActionListener(enableViewButtonListener);
            }
        }

        // listener per il pulsante "Visualizza Risposta"
        viewAnswerButton.addActionListener(e -> {
            List<Integer> correctAnswers = question.getCorrectAnswers();
            for (Map.Entry<AbstractButton, Integer> entry : buttonIndices.entrySet()) {
                AbstractButton button = entry.getKey();
                int index = entry.getValue();
                if (correctAnswers.contains(index)) {
                    button.setForeground(Color.GREEN);
                    if (button.isSelected()) {
                        if(diff == 1) {
                            score+=1;
                        }
                        else if(diff == 2){
                            score+=2;
                        }
                        else {
                            score+=3;
                        }
                    }
                } else if (button.isSelected()) {
                    button.setForeground(Color.RED); // Evidenzia le risposte errate in rosso
                }
            }

            viewAnswerButton.setText("Procedi");
            viewAnswerButton.removeActionListener(viewAnswerButton.getActionListeners()[0]);
            viewAnswerButton.addActionListener(ev -> {
                currentQuestion++; // passa alla prossima domanda
                displayQuestion(diff); // visualizza la prossima domanda
            });
        });

        gbc.gridy = 2;
        add(viewAnswerButton, gbc);

        revalidate(); // aggiorna il layout, ChatGPT
        repaint(); // ridisegna la finestra, ChatGPT
    }
}

