
package pacmanapp;

/*
 * @author captn_000
 */

public class PacMan {
    int imgWidth, imgHieght;
    int locX, locY;
    int startLocX, startLocY;
    int gridRow, gridCol;
    int startGridRow, startGridCol;
    int totalScore, levelScore;
    int lives;
     
    public PacMan(){
         imgWidth = 25;
         imgHieght = 25;
         startLocX = 350;
         startLocY = 425;
         locX = 350;
         locY = 425;
         startGridRow = 17;
         startGridCol = 14;
         gridRow = 17;
         gridCol = 14;
         totalScore = 0;
         levelScore = 0;
         lives = 3;
    }
}
