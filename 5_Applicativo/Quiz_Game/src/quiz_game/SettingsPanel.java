/*
 * Classe SettingsPanel
 * Classe che gestisce le impostazioni inserite dall'utente
 */
package quiz_game;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Leonardo Sciara
 */

class SettingsPanel extends JFrame {
    private ButtonGroup categoryGroup; // gruppo di pulsanti

    public SettingsPanel() {
        setTitle("Impostazioni Quiz");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout()); //imposta layout per poi posizionare vari componenti
        getContentPane().setBackground(new Color(245, 245, 255));

        // crea istanza GridBagConstraints per configurare il posizionamento dei componenti
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // margini tra i componenti
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel categoryLabel = new JLabel("Categoria:");
        categoryLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        
        // Categoria: posizionata alla prima riga, prima colonna
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(categoryLabel, gbc);

        JPanel categoryPanel = new JPanel(new GridLayout(2, 3));
        String[] categories = {"Sport", "Storia", "Cinema", "Scienza", "Musica", "Matematica"};
        categoryGroup = new ButtonGroup();

        // rende le categorie in pulsante
        for (String category : categories) {
            JRadioButton button = new JRadioButton(category);
            button.setFont(new Font("SansSerif", Font.PLAIN, 14));
            button.setActionCommand(category); // ActionCommand per recuperare valore, ChatGPT
            categoryGroup.add(button); // aggiunge il pulsante al gruppo
            categoryPanel.add(button); // aggiunge il pulsante al panel
            button.setBackground(new Color(245, 245, 255));
        }
        
        // categorie posizionate alla prima riga, seconda colonna
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(categoryPanel, gbc);

        JLabel difficultyLabel = new JLabel("Difficoltà:");
        difficultyLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        
        // Difficoltà: posizionato alla seconda riga, prima colonna
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(difficultyLabel, gbc);
        
        JLabel difficultyValue = new JLabel("Difficoltà: Medio");
        gbc.gridx = 2;
        gbc.gridy = 1;
        add(difficultyValue, gbc);

        JSlider difficulty = new JSlider(1, 3, 2); // slider con valore da 1 a 3
        difficulty.setBackground(new Color(245, 245, 255));
        
        // listener per cambiare lo stato dello slider
        difficulty.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (difficulty.getValue() == 1) {
                    difficultyValue.setText("Difficoltà: Facile");   
                }
                else if (difficulty.getValue() == 2) {
                    difficultyValue.setText("Difficoltà: Medio");   
                }
                else {
                difficultyValue.setText("Difficoltà: Difficile");   
                }
            }
        });
        
        // slider della difficoltà posizionato alla seconda riga, seconda colonna
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(difficulty, gbc);

        JLabel quantityLabel = new JLabel("Numero di Domande:");
        quantityLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        
        // Numero di Domande: posizionato alla terza riga riga, prima colonna
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(quantityLabel, gbc);
        
        JLabel quantityValue = new JLabel("Valore: 15");
        gbc.gridx = 2;
        gbc.gridy = 2;
        add(quantityValue, gbc);

        JSlider quantity = new JSlider(1, 30, 15); // Slider da 1 a 30 con valore predefinito 15
        quantity.setBackground(new Color(245, 245, 255));
        quantity.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                quantityValue.setText("Valore: " + quantity.getValue());
            }
        });

        // slider quantità domande posizionato alla terza riga riga, seconda colonna colonna
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(quantity, gbc);

        JButton startGameButton = new JButton("Inizia Gioco");
        startGameButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        startGameButton.setBackground(new Color(0, 0, 0));
        startGameButton.setForeground(new Color(245,245,255));

        // quando l'utente preme sul tasto parte il listener per far partire il gioco
        startGameButton.addActionListener(e -> {
            String category = getCategory(); // recupera la categoria selezionata
            if (category == null) { // controlla se la categoria è stata selezionata
                JOptionPane.showMessageDialog(this, "Seleziona una categoria"); // spunta il messaggio di selezionare una categoria
                return;
            }

            saveSettings(category, difficulty.getValue(), quantity.getValue());
        });

        // tasto posizionato posizionata alla quarta riga riga, seconda colonna colonna
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(startGameButton, gbc);

        setVisible(true);
    }

    // prende la categoria
    public String getCategory() {
        ButtonModel selectedModel = categoryGroup.getSelection(); // pulsante selezionato
        if (selectedModel != null) {
            return selectedModel.getActionCommand(); // ritorna il pulsante selezionato
        }
        return null;
    }

    // fa partire il quiz con le impostazioni salvate
    public void saveSettings(String category, int difficulty, int quantity) {
        setVisible(false);
        new QuizPanel(category, difficulty, quantity); // apre schermata quiz
    }
}
