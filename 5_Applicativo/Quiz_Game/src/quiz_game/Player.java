/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package quiz_game;

import javax.swing.JFrame;

/**
 *
 * @author leonardo.sciara
 */
public class Player{
    
    private String name;
    private int score;

    public Player(String name, int score) {
        this.name = name;
        this.score = score;
    }
    
    public void addScore(int points) {
    
    }
    
    public boolean checkName(String name) {
        String[] proibites = {""};
        for(String n : proibites){
            if(n.equals(name)){
                System.out.println("Nome proibito");
                return false;
            }
        }
        return true;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    }
    
}
