
package pacmanapp;

/*
 * @author captn_000
 */

public class HighScore {
    String name;
    int score;
    
    public HighScore(String newName, String newScore){
        name = newName;
        score = Integer.parseInt(newScore);
    }
    
    public HighScore(String newName, int newScore){
        name = newName;
        score = newScore;
    }
    
    public String getName(){
        return name;
    }
    
    public int getScore(){
        return score;
    }
}
