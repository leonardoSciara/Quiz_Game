/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quiz_game;

/**
 *
 * @author leonardo.sciara
 */
public class Settings {
    private String category;
    private String difficulty;
    private int questionQuantity;

    public Settings(String category, String difficulty, int questionQuantity) {
        this.category = category;
        this.difficulty = difficulty;
        this.questionQuantity = questionQuantity;
    }
    
    public void selectCategory(String inputCategory){
    }
    
    public void selectDifficulty(String inputDifficulty){
        
    }
    
    public void selectQuantity(String inputQuantity){
    
    }
    
    public boolean checkSettings(){
        return true;
    }
}
