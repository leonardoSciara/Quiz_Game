/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quiz_game;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author leonardo.sciara
 */
public class Question {
    private String text;
    private List<Answer> answers = new ArrayList<>();
    private String[] correct;
    
    public boolean checkAnswer(String[] selectedAnswers){
        return true;
    }
    
    public void showImage(){
        
    }
}
