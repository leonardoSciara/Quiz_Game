/*
 * Classe QuizPanel
 * Classe che gestisce domande che vengono generate sotto richiesta dell'utente
 */
package quiz_game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

/**
 *
 * @author Leonardo Sciara
 */

class QuizPanel extends JFrame {
    private List<Question> questions; // lista delle domande
    private List<List<Integer>> userAnswers = new ArrayList<>(); // risposte dell'utente
    private List<Integer> questionScores = new ArrayList<>(); // punteggio per ogni domanda
    private int currentQuestion = 0; // indice per tenere conto della domanda corrente
    private int score = 0; // punteggio totale

    public QuizPanel(String category, int difficulty, int quantity) {
        setTitle("Quiz");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(240, 240, 255));

        questions = loadQuestions(category, difficulty, quantity);

        // inizializza le liste per le risposte e i punteggi
        for (int i = 0; i < questions.size(); i++) {
            userAnswers.add(new ArrayList<>());
            questionScores.add(0); // aggiunge punteggio iniziale per ogni domanda
        }

        displayQuestion(difficulty);
        setVisible(true);
    }

    // carica in una lista il numero di domande scelte dall'utente in base alla categoria e alla difficoltà
    public List<Question> loadQuestions(String category, int difficulty, int quantity) {
        List<Question> allQuestions = new ArrayList<>();
        Path path = Paths.get("src/quiz_game/questions.txt");

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
                // aggiunge alla lista filtrata le domande che comprendono quel livello di difficoltà e quella categoria
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

    // converte la difficoltà da String a int 
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

    // mostra le domande salvate nella lista precedente 
    public void displayQuestion(int diff) {
        // se le domande sono finite
        if (currentQuestion >= questions.size()) {
            new ResultPanel(score, questions, userAnswers, questionScores); // apre schermata quiz passando punteggio,
            // lista di domande, lista di risposte e lista di punteggi
            setVisible(false);
            return; // esce dalla funzione
        }

        // ottiene domanda corrente
        Question question = questions.get(currentQuestion);
        // rimuove tutti i componenti precedenti dalla finestra
        getContentPane().removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // mostra numero della domanda corrente e testo della domanda
        JLabel questionLabel = new JLabel((currentQuestion + 1) + ". " + question.getText());
        questionLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(questionLabel, gbc);

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(0, 1));
        ButtonGroup buttonGroup = new ButtonGroup();
        List<JCheckBox> checkboxes = new ArrayList<>(); // lista per checkbox,
        // non ho fatto la lista per i radio button perché per i radiobutton
        // puoi mettere solo una risposta
        
        // mappa per salvare tasto e indice del tasto 
        Map<AbstractButton, Integer> buttonIndices = new HashMap<>();

        // mostra tutte le opzioni
        for (int i = 0; i < question.getOptions().size(); i++) {
            String option = question.getOptions().get(i); // prende l'opzione in quell'indice
            if (question.getType().equals("singola")) { // decide se mettere radiobutton o checkbox
                JRadioButton radioButton = new JRadioButton(option); // rende l'opzione un radiobutton
                radioButton.setActionCommand(String.valueOf(i)); // imposta al bottone posizione i,
                //il valore della sua apposita opzione
                
                buttonGroup.add(radioButton);
                optionsPanel.add(radioButton);
                buttonIndices.put(radioButton, i);
            } else {
                JCheckBox checkBox = new JCheckBox(option); // rende l'opzione un checkbox
                checkboxes.add(checkBox);
                optionsPanel.add(checkBox);
                buttonIndices.put(checkBox, i);
            }
        }

        gbc.gridy = 2;
        add(optionsPanel, gbc);

        JButton answerButton = new JButton("Visualizza Risposta");
        answerButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        answerButton.setBackground(new Color(0, 0, 0));
        answerButton.setForeground(new Color(245, 245, 255));
        answerButton.setVisible(false);

        // listener per rendere visibile il tasto solo quando una risposta è selezionata
        ActionListener visibleButton = e -> {
            if (question.getType().equals("singola")) {
                answerButton.setVisible(buttonGroup.getSelection() != null);
            } 
            // ChatGPT
            else {
                // stream permette l'iterazione sui dati
                // anyMatch controlla se un al meno soddisfa la condizione
                // AbstractButton::isSelected metodo che fa il controllo su ogni elemento se è selezionato
                boolean anySelected = checkboxes.stream().anyMatch(AbstractButton::isSelected);
                answerButton.setVisible(anySelected); // rende visibile il tasto se anySelected è true,
                // quindi se uno dei checkbox è selezionato
            }
        };

        // aggiunge il listener ai tasti delle opzioni
        if (question.getType().equals("singola")) {
            // inserisce tutti gli elementi appartenenti al gruppo di tasti in una lista
            List<AbstractButton> buttonsList = Collections.list(buttonGroup.getElements());

            for (AbstractButton button : buttonsList) {
                // aggiunge il listener ai tasti
                button.addActionListener(visibleButton);
            }
        } else {
            for (JCheckBox checkBox : checkboxes) {
                checkBox.addActionListener(visibleButton);
            }
        }

        // listener per il pulsante "Visualizza Risposta"
        answerButton.addActionListener(e -> {
            pointAnswer(answerButton, buttonIndices, question.getCorrectAnswers(), diff);
        });

        gbc.gridy = 3;
        add(answerButton, gbc);

        revalidate(); // aggiorna il layout, ChatGPT
        repaint(); // ridisegna la finestra, ChatGPT
    }

    private void pointAnswer(JButton button, Map<AbstractButton, Integer> buttonIndices, List<Integer> correctAnswers, int diff) {
        if (currentQuestion >= questionScores.size()) {
            System.out.print("");
            return; 
        }

        List<Integer> selectedAnswers = new ArrayList<>();
        int correctCount = 0;

        // buttonIndices passa chiave e valore a entry
        for (Map.Entry<AbstractButton, Integer> entry : buttonIndices.entrySet()) {
            // btn prende la chiave di entry
            AbstractButton btn = entry.getKey();
            // index prende il valore di entry (indice bottone)
            int index = entry.getValue();

            if (btn.isSelected()) {
                // se selezionato, aggiunge alla lista dei bottoni selezionati index
                selectedAnswers.add(index);
            }

            if (correctAnswers.contains(index)) {
                // se il bottone è una delle risposte corrette,
                // colora di verde il testo, sennò lo colora di rosso
                btn.setForeground(Color.GREEN);
                if (btn.isSelected()) {
                    correctCount++;
                }
            } else if (btn.isSelected()) {
                btn.setForeground(Color.RED);
            }
        }

        int totalCorrect = correctAnswers.size();
        int questionScore = 0;

        if (diff == 1) {
            questionScore = 10 * correctCount;
        }  
        else if (diff == 2) {
            if (totalCorrect == 1) {
                questionScore = correctCount * 7;
            }
            else if (totalCorrect == 2) {
                questionScore = correctCount * 10;
            } 
            else {
                questionScore = correctCount * 7;
                if (correctCount == 3) { 
                    questionScore = 20;
                }
            }
        } 
        else if (diff == 3) {
            if (totalCorrect == 1) {
                questionScore = correctCount * 10;
            }
            else if (totalCorrect == 2) {
                questionScore = correctCount * 15;
            } 
            else {
                questionScore = correctCount * 10;
            }
        }

        score += questionScore;
        
        // inserisce quel punteggio a quella posizione
        questionScores.set(currentQuestion, questionScore);
        // inserisce quella risposta a quella posizione
        userAnswers.set(currentQuestion, selectedAnswers);

        button.setText("Procedi");
        button.addActionListener(e -> {
            currentQuestion++; // incrementa l'indice, per poter prende la domanda successiva
            displayQuestion(diff); // richiama la funzione per poter mostrare la domanda
        });
    }

}