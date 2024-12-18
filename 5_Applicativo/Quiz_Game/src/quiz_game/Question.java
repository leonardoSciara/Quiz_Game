/*
 * Classe Question
 * Rappresenta una domanda nel quiz
 */

package quiz_game;

import java.util.List;

/**
 *
 * @author Leonardo Sciara
 */

class Question {
    private String type; // tipo della domanda, se singola o multipla
    private String category; // categoria della domanda
    private String difficulty; // difficoltà della domanda
    private String text; // testo della domanda
    private List<String> options; // opzioni della domanda
    private List<Integer> correctAnswers; // risposte corrette della domanda

    public Question(String type, String category, String difficulty, String text, List<String> options, List<Integer> correctAnswers) {
        this.type = type;
        this.category = category;
        this.difficulty = difficulty;
        this.text = text;
        this.options = options;
        this.correctAnswers = correctAnswers;
    }

    public String getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getText() {
        return text;
    }

    public List<String> getOptions() {
        return options;
    }

    public List<Integer> getCorrectAnswers() {
        return correctAnswers;
    }
}
