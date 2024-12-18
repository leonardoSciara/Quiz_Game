/*
 * Classe UsernamePanel
 * Classe che gestisce l'inserimento dello username da parte dell'utente
 */
package quiz_game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;

/**
 *
 * @author Leonardo Sciara
 */

class UsernamePanel extends JFrame {
    private JTextField usernameInput; // campo di testo per inserimento username
    private JButton nextButton; // tasto per passare alla pagina successiva

    public UsernamePanel() {
        setTitle("Inserisci Nome Utente");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // chiude l'applicazione alla chiusura della finestra, ChatGPT

        JPanel panel = new JPanel(new GridLayout(3, 1)); // pannello principale, GridLayout per i componenti in verticale

        JLabel label = new JLabel("Inserisci il tuo nome:");
        usernameInput = new JTextField();
        nextButton = new JButton("Avanti");
        nextButton.setVisible(false);

        // listener per rendere visibile/non visibile il tasto "Avanti"
        usernameInput.addKeyListener(new KeyAdapter() { // KeyListener usato per rilevare input dalla tastiera
            @Override
            public void keyReleased(KeyEvent e) { // viene eseuito quando un tasto viene rilasciato nel campo usernameInput
                nextButton.setVisible(!usernameInput.getText().isEmpty()); // Mostra il tasto se il campo non è vuoto
            }
        });

        // listener per richiamare saveUsername
        nextButton.addActionListener(e -> saveUsername());

        // aggiunge i componenti al pannello
        panel.add(label);
        panel.add(usernameInput);
        panel.add(nextButton);

        // aggiunge il pannello alla finestra
        add(panel);

        // rende visibile la finestra
        setVisible(true);
    }

    // funzione per salvare lo username
    public void saveUsername() {
        String username = usernameInput.getText().trim(); // rimuove spazi iniziali e finali

        if (username.isEmpty()) {
            username = "Nome Sconosciuto"; // imposta nome di default
        }

        try {
            Path path = Paths.get("src/quiz_game/username.txt"); // memorizza file in cui salverà il nome
            Files.writeString(path, username); // scrive il nome nel file
            setVisible(false);
            new SettingsPanel(); // apre schermata impostazioni
        } catch (IOException ex) {
            System.out.println("Errore nel salvataggio del nome");
        }
    }

}