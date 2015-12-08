
package pacmanapp;

/*
 * @author captn_000
 */

public class Ghost {
    int imgWidth, imgHieght;
    int locX, locY;
    int deadX, deadY;
    int gridRow, gridCol;
    boolean alive, canEat;
    
    public Ghost(int color){
        switch(color){
            case 1:
                imgWidth = 25;
                imgHieght = 25;
                locX = 350;
                locY = 275;
                deadX = 295;
                deadY = 350;
                gridRow = 0;
                gridCol = 0;
                alive = false;
                canEat = false;
                break;
            case 2:
                imgWidth = 25;
                imgHieght = 25;
                locX = 350;
                locY = 275;
                deadX = 325;
                deadY = 350;
                gridRow = 0;
                gridCol = 0;
                alive = false;
                canEat = false;
                break;
            case 3:
                imgWidth = 25;
                imgHieght = 25;
                locX = 350;
                locY = 275;
                deadX = 355;
                deadY = 350;
                gridRow = 0;
                gridCol = 0;
                alive = false;
                canEat = false;
                break;
            case 4:
                imgWidth = 25;
                imgHieght = 25;
                locX = 350;
                locY = 275;
                deadX = 385;
                deadY = 350;
                gridRow = 0;
                gridCol = 0;
                alive = false;
                canEat = false;
                break;
                      
        }
    }
}
