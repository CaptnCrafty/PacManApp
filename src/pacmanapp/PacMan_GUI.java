
package pacmanapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.animation.PathTransition;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 *
 * @author devin.leon
 */
public class PacMan_GUI extends Application  {
    final int WALL=0, DOT=1, DOT2=2, DOT3=5, PELLET=3, FRUIT=4, SPACE1=9;
    final int RED_GHOST=1, PINK_GHOST=2, ORANGE_GHOST=3, BLUE_GHOST=4;
    final int UP1=1, RIGHT2=2, LEFT3=3, DOWN4=4;
    final int GHOST_GRIDROW=11, GHOST_GRIDCOL=14;
    final int GHOST_STARTX=350, GHOST_STARTY=275;
    final int RG_DEADX=296, PG_DEADX=325, OG_DEADX=355, BG_DEADX=385, G_DEADY=350;
    boolean needLeftCheck=true, needUpCheck=true, needRightCheck=false, needDownCheck=true;
    boolean needLeftCheckRG=true, needUpCheckRG=true, needRightCheckRG=false, needDownCheckRG=true;
    boolean needLeftCheckPG=true, needUpCheckPG=true, needRightCheckPG=false, needDownCheckPG=true;
    boolean needLeftCheckOG=true, needUpCheckOG=true, needRightCheckOG=false, needDownCheckOG=true;
    boolean needLeftCheckBG=true, needUpCheckBG=true, needRightCheckBG=false, needDownCheckBG=true;
    int redDirect=2, pinkDirect=3,orangeDirect=2,blueDirect=3;
    int dotCount=0, seconds=0, pelletTime=-1, pelletCount=-1, levelInt=1, timeBonus=0;
    boolean levelOver=false, gameOver=false, paused=false;
    LinkedList levelHigh, totalHigh;
    
    Image pacmanRight, pacmanLeft, pacmanUp, pacmanDown, 
            redghost, pinkghost, orangeghost, blueghost, ghostpelletblue, ghostpelletwhite;
    
    ImageView pacIV, redGhostIV, pinkGhostIV, orangeGhostIV, blueGhostIV,
            livesOneIV, livesTwoIV, livesThreeIV, ghostDescIV;
    
    AnimationTimer timer;
    
    PacMan pacMan;
    Level level;
    Ghost redGhost;
    Ghost pinkGhost;
    Ghost orangeGhost;
    Ghost blueGhost;
    
    GridPane gp1, gp2, gp3, gp4, gp5, gp6;
    Group gameBoard;
    BorderPane bp;
    Scene scene;
    VBox vb1, vb2, vb3, vb4, vb5, vb6, vb7, vb8, vb9;
    HBox hb1;

    Stage levelOneStage = new Stage(), levelTwoStage = new Stage(), levelThreeStage = new Stage(),
            levelFourStage = new Stage(), levelFiveStage = new Stage(), levelSixStage = new Stage(),
            levelSevenStage = new Stage(), levelEightStage = new Stage();
    
    Label levelScoreTitleL, levelScoreL, totalScoreTitleL, totalScoreL, livesL,
            highScore1, highScore2, highScore3, highScore4, highScore5,
            highScore6, highScore7, highScore8, highScore9, highScore10,
            highScore1N, highScore2N, highScore3N, highScore4N, highScore5N,
            highScore6N, highScore7N, highScore8N, highScore9N, highScore10N,
            highScore1P, highScore2P, highScore3P, highScore4P, highScore5P,
            highScore6P, highScore7P, highScore8P, highScore9P, highScore10P,
            pointGuideL, dotDescL, pelletDescL, ghostDescL, levelHighL,
            totalHighL, startL1, startL2, startL3, pauseL1, pauseL2,
            levelL1, levelL2, levelL3, levelL4, levelL5, levelL6, levelL7,
            levelL8, endL1, endL2, endL3, updateL1, updateL2, updateL3, updateL4;
    
    Button closeGameB, nextLevelB, doneB;
    
    //@Override
    public void start(Stage primaryStage) {
        pacMan = new PacMan();
        playGame(levelOneStage);
    }

    public static void main(String[] arg) {
        launch(arg);
    }
    
    public void playGame(Stage pStage){
        Stage primaryStage = pStage;
        //create pacman and ghost objects
        level = new Level(levelInt);
        redGhost = new Ghost(RED_GHOST);
        pinkGhost = new Ghost(PINK_GHOST);
        orangeGhost = new Ghost(ORANGE_GHOST);
        blueGhost = new Ghost(BLUE_GHOST);
        
        //create images
        pacmanRight = new Image("pacmanRight.png");
        pacmanLeft = new Image("pacmanLeft.png");
        pacmanDown = new Image("pacmanDown.png");
        pacmanUp = new Image("pacmanUp.png");
        redghost = new Image("ghostred.png");
        pinkghost = new Image("ghostpink.png");
        orangeghost = new Image("ghostorange.png");
        blueghost = new Image("ghostblue.png");
        ghostpelletblue = new Image("ghostpelletblue.png");
        ghostpelletwhite = new Image("ghostpelletwhite.png");
        
        //put images into ImageViews
        pacIV = new ImageView();
            pacIV.setImage(pacmanRight);
            pacIV.setFitWidth(pacMan.imgWidth);
            pacIV.setFitHeight(pacMan.imgHieght);
            pacIV.setX(pacMan.locX);
            pacIV.setY(pacMan.locY);
        redGhostIV = new ImageView();
            redGhostIV.setImage(redghost);
            redGhostIV.setFitWidth(redGhost.imgWidth);
            redGhostIV.setFitHeight(redGhost.imgHieght);
            redGhostIV.setX(RG_DEADX);
            redGhostIV.setY(G_DEADY);
        pinkGhostIV = new ImageView();
            pinkGhostIV.setImage(pinkghost);
            pinkGhostIV.setFitWidth(pinkGhost.imgWidth);
            pinkGhostIV.setFitHeight(pinkGhost.imgHieght);
            pinkGhostIV.setX(PG_DEADX);
            pinkGhostIV.setY(G_DEADY);
        orangeGhostIV = new ImageView();
            orangeGhostIV.setImage(orangeghost);
            orangeGhostIV.setFitWidth(orangeGhost.imgWidth);
            orangeGhostIV.setFitHeight(orangeGhost.imgHieght);
            orangeGhostIV.setX(OG_DEADX);
            orangeGhostIV.setY(G_DEADY);
        blueGhostIV = new ImageView();
            blueGhostIV.setImage(blueghost);
            blueGhostIV.setFitWidth(blueGhost.imgWidth);
            blueGhostIV.setFitHeight(blueGhost.imgHieght);
            blueGhostIV.setX(BG_DEADX);
            blueGhostIV.setY(G_DEADY);
        livesOneIV = new ImageView();
            livesOneIV.setImage(pacmanRight);
            livesOneIV.setFitWidth(30);
            livesOneIV.setFitHeight(30);
        livesTwoIV = new ImageView();
            livesTwoIV.setImage(pacmanRight);
            livesTwoIV.setFitWidth(30);
            livesTwoIV.setFitHeight(30);
        livesThreeIV = new ImageView();
            livesThreeIV.setImage(pacmanRight);
            livesThreeIV.setFitWidth(30);
            livesThreeIV.setFitHeight(30);
        ghostDescIV = new ImageView();
            ghostDescIV.setImage(ghostpelletblue);
            ghostDescIV.setFitWidth(25);
            ghostDescIV.setFitHeight(25);
        
        //create all labels needed
        livesL = new Label("   Lives   ");
            livesL.setFont(new Font("Arial", 30));
            livesL.getStyleClass().addAll("label", "labelTwo");
        levelScoreTitleL = new Label("  Level Points  ");
            levelScoreTitleL.setFont(new Font("Arial", 30));
            levelScoreTitleL.getStyleClass().add("labelTwo");
        levelScoreL = new Label(pacMan.levelScore+"");
            levelScoreL.setFont(new Font("Arial", 30));
        totalScoreTitleL = new Label("  Total Points  ");
            totalScoreTitleL.setFont(new Font("Arial", 30));
            totalScoreTitleL.getStyleClass().add("labelTwo");
        totalScoreL = new Label(pacMan.totalScore+"");
            totalScoreL.setFont(new Font("Arial", 30));
        pointGuideL = new Label("  Point Guide  ");
            pointGuideL.setFont(new Font("Arial", 30));
            pointGuideL.getStyleClass().addAll("labelTwo");
        dotDescL = new Label("  -   25 ");
            dotDescL.setFont(new Font("Arial", 25));
        pelletDescL = new Label("  -  100 ");
            pelletDescL.setFont(new Font("Arial", 25));
        ghostDescL = new Label("  -  200 ");
            ghostDescL.setFont(new Font("Arial", 25));
        levelHighL = new Label(" Level "+levelInt+" High Scores ");
            levelHighL.setFont(new Font("Arial", 32));
            levelHighL.getStyleClass().addAll("label", "labelTwo");
        totalHighL = new Label("  Total High Scores  ");
            totalHighL.setFont(new Font("Arial", 33));
            totalHighL.getStyleClass().addAll("label", "labelTwo");
                        
        //read the high scores file and update highscore list
        levelHigh = readScoreFile("HighScores"+levelInt+".dat");
        totalHigh = readScoreFile("HighScores0.dat");

        //create tilepane to hold overall high scores
        gp6 = showHighScores(totalHigh, 0);
            
        //createvbox to hold total score label and scores
        vb8 = new VBox();
            vb8.getChildren().addAll(totalHighL,gp6);
            vb8.getStyleClass().addAll("vboxOne");
            vb8.setAlignment(Pos.CENTER);
        
        //create tilepane to hold all highscore labels
        gp5 = showHighScores(levelHigh, levelInt);
        
        //create vbox to hold level high score label and scores
        vb1 = new VBox();
            vb1.getChildren().addAll(levelHighL,gp5);
            vb1.getStyleClass().addAll("vboxOne");
            vb1.setAlignment(Pos.CENTER);
            
        //create vbox to hold all of left panel
        vb3 = new VBox();
            vb3.getChildren().addAll(vb1, vb8);
            vb3.getStyleClass().addAll("pane", "leftPane");
            vb3.setAlignment(Pos.CENTER_LEFT);
        
        //hbox to hold lives images
        gp3 = new GridPane();
            gp3.add(livesOneIV,0,0);
            gp3.add(livesTwoIV,1,0);
            gp3.add(livesThreeIV,2,0);
            gp3.setAlignment(Pos.CENTER);
            gp3.setHgap(10);
            updateLivesDisplay();
            
        //vbox to hold lives display
        vb4 = new VBox();
            vb4.getChildren().addAll(livesL,gp3);
            vb4.getStyleClass().addAll("vboxSeven");
            vb4.setAlignment(Pos.CENTER);
        
        //vbox to hold level score display
        vb5 = new VBox();
            vb5.getChildren().addAll(levelScoreTitleL, levelScoreL);
            vb5.setAlignment(Pos.CENTER_RIGHT);
        
        //vbox to hold total score display
        vb6 = new VBox();
            vb6.getChildren().addAll(totalScoreTitleL, totalScoreL);
            vb6.setAlignment(Pos.CENTER_RIGHT);
        
        //gridpane to hold point descriptions
        gp2 = new GridPane();
            Circle dotDesc = new Circle(4);
            dotDesc.setFill(Color.YELLOW);
            gp2.add(dotDesc,0,0);
            gp2.setHalignment(dotDesc, HPos.CENTER);
            gp2.add(dotDescL,1,0);
            Circle pelletDesc = new Circle(9);
            pelletDesc.setFill(Color.BLUE);
            gp2.add(pelletDesc,0,1);
            gp2.setHalignment(pelletDesc, HPos.CENTER);
            gp2.add(pelletDescL,1,1);
            gp2.add(ghostDescIV,0,2);
            gp2.add(ghostDescL,1,2);
            gp2.setAlignment(Pos.CENTER);
            gp2.setVgap(15);

        //vbox to hold point description display
        vb7 = new VBox();
            vb7.getChildren().addAll(pointGuideL, gp2);
            vb7.getStyleClass().addAll("vboxSeven");
            vb7.setAlignment(Pos.CENTER);
            
        //create vbox to hold everything on right panel
        vb2 = new VBox();
            vb2.getChildren().addAll(vb4, vb5, vb6, vb7);
            vb2.getStyleClass().addAll("pane", "vboxTwo");
        
        //create gridpane that holds map
        gp1 = makeMap(level.map);
        
        //create group to hold map, pacman, and ghost together
        gameBoard = new Group();
            gameBoard.getChildren().addAll(gp1, pacIV,
                    redGhostIV, pinkGhostIV, orangeGhostIV, blueGhostIV);
        
        //create border pane that holds it all
        bp = new BorderPane();
            bp.setCenter(gameBoard);
            bp.setLeft(vb3);
            bp.setRight(vb2);
            bp.getStyleClass().addAll("borderpane");
            
        //create the scene with specific size
        scene = new Scene(bp, 1450, 900);
            scene.getStylesheets().add("pacmanapp/PacManCSS.css");
        
        //create keylistener to respond when direction keys are pressed
        scene.setOnKeyPressed((KeyEvent event) -> {
            switch (event.getCode()) {
                case UP:
                    if(level.map[pacMan.gridRow-1][pacMan.gridCol]!=0){
                        //change image for direction and move pacman
                        pacIV.setImage(pacmanUp);
                        //fix up movement
                        if(needUpCheck){
                            pacMan.locY+=25;
                            if(!needLeftCheck){
                                pacMan.locX-=25;
                            }
                            needUpCheck=false;
                        }
                        movePacMan(UP1);
                        //update pacman new location
                        pacMan.locY-=25;
                        pacMan.gridRow--;
                        //set all move checks
                        needLeftCheck=true;
                        needRightCheck=true;
                        needDownCheck=true;
                    }
                    break;
                case DOWN:
                    if(level.map[pacMan.gridRow+1][pacMan.gridCol]!=0){
                        //change image for direction and move pacman
                        pacIV.setImage(pacmanDown);
                        //fix down movement
                        if(needDownCheck){
                            pacMan.locY-=25;
                            if(!needLeftCheck){
                                pacMan.locX-=25;
                                pacMan.locY+=25;
                            }
                            if(!needRightCheck){
                                pacMan.locY+=25;
                            }
                            needDownCheck=false;
                        }
                        movePacMan(DOWN4);
                        //update pacman new location
                        pacMan.locY+=25;
                        pacMan.gridRow++;
                        needLeftCheck=true;
                        needRightCheck=true;
                        needUpCheck=true;
                    }
                    break;
                case LEFT:
                    if(level.map[pacMan.gridRow][pacMan.gridCol-1]!=0){
                        //change image for direction and move pacman
                        pacIV.setImage(pacmanLeft);
                        //fix left movement
                        if(!needUpCheck){
                            pacMan.locY-=25;
                        }
                        if(needLeftCheck){
                            pacMan.locX+=25;
                            needLeftCheck=false;
                        }
                        movePacMan(LEFT3);
                        //update pacman new location
                        pacMan.locX-=25;
                        pacMan.gridCol--;
                        needRightCheck=true;
                        needDownCheck=true;
                        needUpCheck=true;
                    }
                    break;
                case RIGHT:
                    if(level.map[pacMan.gridRow][pacMan.gridCol+1]!=0){
                        //change image for direction and move pacman
                        pacIV.setImage(pacmanRight);
                        //fix right movement
                        if(!needUpCheck){
                            pacMan.locY-=25;
                            pacMan.locX+=25;
                        }
                        if(!needDownCheck){
                            pacMan.locX+=25;
                        }
                        if(needRightCheck){
                            pacMan.locX-=25;
                            needRightCheck=false;
                        }
                        movePacMan(RIGHT2);
                        //update pacman new location
                        pacMan.locX+=25;
                        pacMan.gridCol++;
                        needLeftCheck=true;
                        needUpCheck=true;
                        needDownCheck=true;
                    }
                    break;
                //pause the game if the space bar is pushed
                case SPACE:
                    timer.stop();
                    pausedPopUp();
                    break;
                    
            }
            //check if pacman is now able to eat dot
            if(level.map[pacMan.gridRow][pacMan.gridCol]==DOT ||
                    level.map[pacMan.gridRow][pacMan.gridCol]==DOT2){
                //update score and remove dot
                eatDot();
            }
            //check if pacman is now able to eat a pellet
            else if(level.map[pacMan.gridRow][pacMan.gridCol]==PELLET){
                //update score, remove pellet, and make ghost edible
                eatPellet();
            }
            //check if pacman can eat a ghost
            if(redGhost.canEat || pinkGhost.canEat || orangeGhost.canEat || blueGhost.canEat){
                //check if any ghost are in same spot as pacman
                if(pacMan.gridRow==redGhost.gridRow && pacMan.gridCol==redGhost.gridCol){
                    eatGhost(RED_GHOST);
                }
                if(pacMan.gridRow==pinkGhost.gridRow && pacMan.gridCol==pinkGhost.gridCol){
                    eatGhost(PINK_GHOST);
                }
                if(pacMan.gridRow==orangeGhost.gridRow && pacMan.gridCol==orangeGhost.gridCol){
                    eatGhost(ORANGE_GHOST);
                }
                if(pacMan.gridRow==blueGhost.gridRow && pacMan.gridCol==blueGhost.gridCol){
                    eatGhost(BLUE_GHOST);
                }
            }
            //check if a ghost can kill pacman
            else if((!redGhost.canEat || !pinkGhost.canEat || !orangeGhost.canEat || !blueGhost.canEat) &&
                    ((pacMan.gridRow==redGhost.gridRow && pacMan.gridCol==redGhost.gridCol) ||
                    (pacMan.gridRow==pinkGhost.gridRow && pacMan.gridCol==pinkGhost.gridCol) ||
                    (pacMan.gridRow==orangeGhost.gridRow && pacMan.gridCol==orangeGhost.gridCol) ||
                    (pacMan.gridRow==blueGhost.gridRow && pacMan.gridCol==blueGhost.gridCol))){      
                //kill pacman
                killPacMan();
            }
                    
        });
        
        //create time to control the ghost and other
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) 
            {
                //set when ghost spawn for first time
                if(seconds==100){
                    reviveGhost(RED_GHOST);
                }
                if(seconds==400){
                    reviveGhost(PINK_GHOST);
                }
                if(seconds==700){
                    reviveGhost(ORANGE_GHOST);
                }
                if(seconds==1000){
                    reviveGhost(BLUE_GHOST);
                }
                
                //move the ghosts
                if(seconds%(40-(levelInt*3))==0 && redGhost.alive){
                    moveRedGhost();
                }
                if(seconds%(50-(levelInt*3))==0 && pinkGhost.alive){
                    movePinkGhost();
                }
                if(seconds%(60-(levelInt*3))==0 && orangeGhost.alive){
                    moveOrangeGhost();
                }
                if(seconds%(70-(levelInt*3))==0 && blueGhost.alive){
                    moveBlueGhost();
                }
                
                //revive ghost if they died
                if(pelletCount<0 && seconds%400==0 && seconds>1000){
                    if(!redGhost.alive){
                        reviveGhost(RED_GHOST);
                    }
                    else if(!pinkGhost.alive){
                        reviveGhost(PINK_GHOST);
                    }
                    else if(!orangeGhost.alive){
                        reviveGhost(ORANGE_GHOST);
                    }
                    else if(!blueGhost.alive){
                        reviveGhost(BLUE_GHOST);
                    }
                }
                
                //time when a ghost is edible
                if(pelletTime+pelletCount==seconds){
                    //alternate the ghost color if they are alive
                    if((pelletCount%100==0) && pelletCount>=500){
                        if(redGhost.alive){
                            redGhostIV.setImage(ghostpelletwhite);
                        }
                        if(pinkGhost.alive){
                            pinkGhostIV.setImage(ghostpelletwhite);
                        }
                        if(orangeGhost.alive){
                            orangeGhostIV.setImage(ghostpelletwhite);
                        }
                        if(blueGhost.alive){
                            blueGhostIV.setImage(ghostpelletwhite);
                        }
                    }
                    else{
                        if(redGhost.alive){
                            redGhostIV.setImage(ghostpelletblue);
                        }
                        if(pinkGhost.alive){
                            pinkGhostIV.setImage(ghostpelletblue);
                        }
                        if(orangeGhost.alive){
                            orangeGhostIV.setImage(ghostpelletblue);
                        }
                        if(blueGhost.alive){
                            blueGhostIV.setImage(ghostpelletblue);
                        }
                    }
                    pelletCount+=50;
                    //after 8 seconds make the ghost normal and not edible and revive
                    if(pelletCount==800){
                        pelletCount=-1;
                        pelletTime=-1;
                        redGhostIV.setImage(redghost);
                        pinkGhostIV.setImage(pinkghost);
                        orangeGhostIV.setImage(orangeghost);
                        blueGhostIV.setImage(blueghost);
                        redGhost.canEat = false;
                        pinkGhost.canEat = false;
                        orangeGhost.canEat = false;
                        blueGhost.canEat = false;
                    } 
                }
                seconds++;
            }
        };
        
        //create the primary stage
        primaryStage.setTitle("PacMan");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
        
        //display start popup
        startPopUp();
        
    }
    
    //method that will createthe map based on level
    public GridPane makeMap(int[][] mapArr){
        //create gridpane to return
        GridPane gPane = new GridPane();
        gPane.setAlignment(Pos.CENTER);
        //create the loop to fill the gridpane
        for(int row = 0; row<mapArr.length; row++){
            for(int col = 0; col<mapArr[0].length; col++){
                //the switch to determine what to put in the grid spot
                switch(mapArr[row][col]){
                    case WALL:
                        //create the wall and put in place
                        Rectangle wall = new Rectangle(25,25,25,25);
                        wall.setFill(level.color);
                        gPane.add(wall,col,row);
                        break;
                    //case DOT:
                    case DOT:
                    case DOT2:
                    case DOT3:
                        //create the dot an put in place
                        Circle dot = new Circle(4);
                        dot.setFill(Color.YELLOW);
                        gPane.add(dot,col,row);
                        GridPane.setHalignment(dot, HPos.CENTER);
                        dotCount++;
                        break;
                    case PELLET:
                        //create the pellet and put in place
                        Circle pellet = new Circle(9);
                        pellet.setFill(Color.BLUE);
                        gPane.add(pellet,col,row);
                        GridPane.setHalignment(pellet, HPos.CENTER);
                        break;
                }
            }
        }
        return gPane;
    }
    
    //method to display start popup
    public void startPopUp(){
        //declare labels for start popup
        startL1 = new Label("Welcome To Level "+levelInt+"!");
            startL1.setFont(new Font("Arial", 30));
            startL1.getStyleClass().addAll("label", "labelTwo");
        startL2 = new Label("You Have "+pacMan.lives+" Lives");
            startL2.setFont(new Font("Arial", 27));
            startL2.getStyleClass().addAll("label");
        startL3 = new Label("(Press ENTER To Start)");
            startL3.setFont(new Font("Arial", 20));
            startL3.getStyleClass().addAll("label");
        
        //create start popup stage
        Stage startPopUp = new Stage();
            startPopUp.initModality(Modality.APPLICATION_MODAL);
            startPopUp.initStyle(StageStyle.UNDECORATED);
            //create vbox to hold start box labels
            vb9 = new VBox();
                vb9.getChildren().addAll(startL1, startL2, startL3);
                vb9.getStyleClass().addAll("borderpane", "vboxStart");
                vb9.setAlignment(Pos.CENTER);
            //create start box scene
            Scene popUpScene = new Scene(vb9, 400, 300);
            popUpScene.getStylesheets().add("pacmanapp/PacManCSS.css");
            popUpScene.setOnKeyPressed((KeyEvent event) -> {
                switch (event.getCode()) {
                    case ENTER:
                        timer.start();
                        startPopUp.close();
                        break;
                }
            });
        startPopUp.setScene(popUpScene);
        startPopUp.setResizable(false);
        startPopUp.show();
    }
    
    //popup for paused screen
    public void pausedPopUp(){
        //declare labels for pause popup
        pauseL1 = new Label("Game Is Paused!");
            pauseL1.setFont(new Font("Arial", 30));
            pauseL1.getStyleClass().addAll("label", "labelTwo");
        pauseL2 = new Label("(Press SPACE To Resume)");
                  pauseL2.setFont(new Font("Arial", 20));
            pauseL2.getStyleClass().addAll("label");
        
        //create start popup stage
        Stage pausedPopUp = new Stage();
            pausedPopUp.initModality(Modality.APPLICATION_MODAL);
            pausedPopUp.initStyle(StageStyle.UNDECORATED);
            //create vbox to hold end box labels
            vb9 = new VBox();
                vb9.getChildren().addAll(pauseL1, pauseL2);
                vb9.getStyleClass().addAll("borderpane", "vboxStart");
                vb9.setAlignment(Pos.CENTER);
            //create start box scene
            Scene popUpScene = new Scene(vb9, 400, 300);
            popUpScene.getStylesheets().add("pacmanapp/PacManCSS.css");
            popUpScene.setOnKeyPressed((KeyEvent event) -> {
                switch (event.getCode()) {
                    case SPACE:
                        timer.start();
                        pausedPopUp.close();
                        break;
                }
            });
        pausedPopUp.setScene(popUpScene);
        pausedPopUp.setResizable(false);
        pausedPopUp.show();
    }
    
    //method to end the level and show popup
    public void endLevel(){
        timer.stop();
        //figure out time bonus
        timeBonus = 10000-seconds;
        if(timeBonus<0){
            timeBonus = 0;
        }
        //create start popup stage
        Stage levelPopUp = new Stage();
            levelPopUp.initModality(Modality.APPLICATION_MODAL);
            levelPopUp.initStyle(StageStyle.UNDECORATED);
        //declare labels for end level popup
        levelL1 = new Label("Level "+levelInt+" Complete!");
            levelL1.setFont(new Font("Arial", 30));
            levelL1.getStyleClass().addAll("label", "labelTwo");
        levelL2 = new Label("You have "+pacMan.lives+" lives left");
            levelL2.setFont(new Font("Arial", 27));
            levelL2.getStyleClass().addAll("label");
        levelL3 = new Label("Level Points:");
            levelL3.setFont(new Font("Arial", 20));
            levelL3.getStyleClass().addAll("label");
        levelL4 = new Label(pacMan.levelScore+"");
            levelL4.setFont(new Font("Arial", 20));
            levelL4.getStyleClass().addAll("label");
        levelL5 = new Label("Time Bonus:");
            levelL5.setFont(new Font("Arial", 20));
            levelL5.getStyleClass().addAll("label");
        levelL6 = new Label(timeBonus+"");
            levelL6.setFont(new Font("Arial", 20));
            levelL6.getStyleClass().addAll("label", "labelTwo");
        levelL7 = new Label("Total Level Points:");
            levelL7.setFont(new Font("Arial", 20));
            levelL7.getStyleClass().addAll("label");
        levelL8 = new Label((pacMan.levelScore+timeBonus)+"");
            levelL8.setFont(new Font("Arial", 20));
            levelL8.getStyleClass().addAll("label");
        //declare buttons, gridpane, and hbox for end level popup
        nextLevelB = new Button();
            nextLevelB.setText("Next Level");
            nextLevelB.setOnAction(e->{
                if(levelInt>=8){
                    noNextLevel();
                }
                else{
                    levelPopUp.close();
                    switch(levelInt){
                        case 1:
                            levelOneStage.close();
                            break;
                        case 2:
                            levelTwoStage.close();
                            break;
                        case 3:
                            levelThreeStage.close();
                            break;
                        case 4:
                            levelFourStage.close();
                            break;
                        case 5:
                            levelFiveStage.close();
                            break;
                        case 6:
                            levelSixStage.close();
                            break;
                        case 7:
                            levelSevenStage.close();
                            break;
                        case 8:
                            levelEightStage.close();
                            break;
                    }
                    startNextLevel();
                }

            });
        closeGameB = new Button();
            closeGameB.setText("Close Game");
            closeGameB.setOnAction(e->{
                HighScore tempScore = (HighScore)totalHigh.get(4);
                if(pacMan.totalScore>tempScore.score){
                    updateHighScores(0);
                }
                else{
                    System.exit(0);
                }
            });
        gp4 = new GridPane();
            gp4.add(levelL3,0,0);
            gp4.setHalignment(levelL3, HPos.RIGHT);
            gp4.add(levelL4,1,0);
            gp4.setHalignment(levelL4, HPos.RIGHT);
            gp4.add(levelL5,0,1);
            gp4.setHalignment(levelL5, HPos.RIGHT);
            gp4.add(levelL6,1,1);
            gp4.setHalignment(levelL6, HPos.RIGHT);
            gp4.add(levelL7,0,2);
            gp4.setHalignment(levelL7, HPos.RIGHT);
            gp4.add(levelL8,1,2);
            gp4.setHalignment(levelL8, HPos.RIGHT);
            gp4.setAlignment(Pos.CENTER);
            gp4.setVgap(10);
            gp4.setHgap(10);
        hb1 = new HBox();
            hb1.getChildren().addAll(closeGameB,nextLevelB);
            hb1.getStyleClass().addAll("vboxSeven");
            hb1.setAlignment(Pos.CENTER);
            //create vbox to hold end box labels
            vb9 = new VBox();
                vb9.getChildren().addAll(levelL1, levelL2, gp4, hb1);
                vb9.getStyleClass().addAll("borderpane", "vboxStart");
                vb9.setAlignment(Pos.CENTER);
            //create start box scene
            Scene popUpScene = new Scene(vb9, 400, 300);
            popUpScene.getStylesheets().add("pacmanapp/PacManCSS.css");
            
        levelPopUp.setScene(popUpScene);
        levelPopUp.setResizable(false);
        levelPopUp.show();
        HighScore tempScore = (HighScore)levelHigh.get(9);
        //update level/overall Score and update highscores
        pacMan.levelScore+=timeBonus;
        pacMan.totalScore+=pacMan.levelScore;
        totalScoreL.setText(pacMan.totalScore+"");
        if(pacMan.levelScore>tempScore.score){
            updateHighScores(1);
        }
    }
    
    //method to end the game and show popup
    public void endGame(){
        //create start popup stage
        Stage endPopUp = new Stage();
            endPopUp.initModality(Modality.APPLICATION_MODAL);
            endPopUp.initStyle(StageStyle.UNDECORATED);
        
        //declae labels for end popup
        endL1 = new Label("Game Over!");
            endL1.setFont(new Font("Arial", 30));
            endL1.getStyleClass().addAll("label", "labelTwo");
        endL2 = new Label("You Are Out Of Lives");
            endL2.setFont(new Font("Arial", 27));
            endL2.getStyleClass().addAll("label");
        //declare buttons and hbox for end popup
        closeGameB = new Button();
            closeGameB.setText("Close Game");
            closeGameB.setOnAction(e->{
                HighScore tempScore = (HighScore)totalHigh.getLast();
                if(pacMan.totalScore>tempScore.score && pacMan.totalScore!=0){
                    updateHighScores(0);
                }
                System.exit(0);
            });
        hb1 = new HBox();
            hb1.getChildren().addAll(closeGameB);
            hb1.getStyleClass().addAll("vboxSeven");
            hb1.setAlignment(Pos.CENTER);
        
            //create vbox to hold end box labels
            vb9 = new VBox();
                vb9.getChildren().addAll(endL1, endL2, hb1);
                vb9.getStyleClass().addAll("borderpane", "vboxStart");
                vb9.setAlignment(Pos.CENTER);
            //create start box scene
            Scene popUpScene = new Scene(vb9, 400, 300);
            popUpScene.getStylesheets().add("pacmanapp/PacManCSS.css");
            
        endPopUp.setScene(popUpScene);
        endPopUp.setResizable(false);
        endPopUp.show();
        timer.stop();
        HighScore tempScore = (HighScore)totalHigh.get(4);
        if(pacMan.totalScore>tempScore.score){
            updateHighScores(0);
        }
    }
    
    //method to move to next level
    public void startNextLevel(){
        levelInt++;
        pacMan.levelScore = 0;
        seconds = 0;
        pacMan.gridCol = pacMan.startGridCol;
        pacMan.gridRow = pacMan.startGridRow;
        pacMan.locX = pacMan.startLocX;
        pacMan.locY = pacMan.startLocY;
        needLeftCheck=true;
        needUpCheck=true;
        needRightCheck=false;
        needDownCheck=true;
        
        switch(levelInt){
            case 2:
                playGame(levelTwoStage);
                break;
            case 3:
                playGame(levelThreeStage);
                break;
            case 4:
                playGame(levelFourStage);
                break;
            case 5:
                playGame(levelFiveStage);
                break;
            case 6:
                playGame(levelSixStage);
                break;
            case 7:
                playGame(levelSevenStage);
                break;
            case 8:
                playGame(levelEightStage);
                break;
        }
    }
   
    //method to show there is no next level
    public void noNextLevel(){
        //create start popup stage
        Stage pausedPopUp = new Stage();
            pausedPopUp.initModality(Modality.APPLICATION_MODAL);
            pausedPopUp.initStyle(StageStyle.UNDECORATED);
            
        //declare labels for pause popup
        pauseL1 = new Label("There Is No More Levels!");
            pauseL1.setFont(new Font("Arial", 30));
            pauseL1.getStyleClass().addAll("label", "labelTwo");
        pauseL2 = new Label("More Levels Coming Soon!");
            pauseL2.setFont(new Font("Arial", 20));
            pauseL2.getStyleClass().addAll("label");
            
        closeGameB = new Button();
            closeGameB.setText("OK");
            closeGameB.setOnAction(e->{
                pausedPopUp.close();
            });
        
            //create vbox to hold end box labels
            vb9 = new VBox();
                vb9.getChildren().addAll(pauseL1, pauseL2, closeGameB);
                vb9.getStyleClass().addAll("borderpane", "vboxStart");
                vb9.setAlignment(Pos.CENTER);
            //create start box scene
            Scene popUpScene = new Scene(vb9, 400, 300);
            popUpScene.getStylesheets().add("pacmanapp/PacManCSS.css");
           
        pausedPopUp.setScene(popUpScene);
        pausedPopUp.setResizable(false);
        pausedPopUp.show();
    }
    
    //method to move pacman when user wants to
    public void movePacMan(int direction){
        Path tempPath = new Path();
        //depending on what direction is inputed, move pacman in that direction
        switch(direction){
            case UP1:
                tempPath.getElements().add(new MoveTo(pacMan.locX+13,pacMan.locY-13));
                tempPath.getElements().add(new LineTo(pacMan.locX+13,pacMan.locY-38));
                break;
            case RIGHT2:
                tempPath.getElements().add(new MoveTo(pacMan.locX+13,pacMan.locY+13));
                tempPath.getElements().add(new LineTo(pacMan.locX+38,pacMan.locY+13));
                break;
            case LEFT3:
                tempPath.getElements().add(new MoveTo(pacMan.locX-13,pacMan.locY+13));
                tempPath.getElements().add(new LineTo(pacMan.locX-38,pacMan.locY+13));
                break;
            case DOWN4:
                tempPath.getElements().add(new MoveTo(pacMan.locX+13,pacMan.locY+13));
                tempPath.getElements().add(new LineTo(pacMan.locX+13,pacMan.locY+38));
                break;
        }
        PathTransition tempTransition = new PathTransition();
        tempTransition.setDuration(Duration.millis(300));
        tempTransition.setNode(pacIV);
        tempTransition.setPath(tempPath);
        tempTransition.play();
    }    
    
    //revive any dead ghost
    public void reviveGhost(int ghost){
        Path tempPath = new Path();
        PathTransition tempTransition = new PathTransition();
        tempTransition.setDuration(Duration.millis(200));
        tempTransition.setPath(tempPath);
        //determine which ghost needs to be revived and put them in right position
        switch(ghost){
            case 1:
                redGhost.locX = redGhost.deadX;
                redGhost.locY = redGhost.deadY;
                tempTransition.setNode(redGhostIV);
                tempPath.getElements().add(new MoveTo(redGhost.locX+13,redGhost.locY+13));
                tempPath.getElements().add(new LineTo(GHOST_STARTX+13,GHOST_STARTY+13));
                tempTransition.play();
                //reset ghost location and movement checks
                redGhost.locX = GHOST_STARTX;
                redGhost.locY = GHOST_STARTY;
                redGhost.gridCol = GHOST_GRIDCOL;
                redGhost.gridRow = GHOST_GRIDROW;
                redGhost.alive = true;
                redGhost.canEat = false;
                redDirect = 2;
                needLeftCheckRG = true;
                needUpCheckRG = true;
                needRightCheckRG = false;
                needDownCheckRG = true;
                break;
            case 2:
                pinkGhost.locX = pinkGhost.deadX;
                pinkGhost.locY = pinkGhost.deadY;
                tempTransition.setNode(pinkGhostIV);
                tempPath.getElements().add(new MoveTo(pinkGhost.locX+13,pinkGhost.locY+13));
                tempPath.getElements().add(new LineTo(GHOST_STARTX+13,GHOST_STARTY+13));
                tempTransition.play();
                //reset ghost location and movement checks
                pinkGhost.locX = GHOST_STARTX;
                pinkGhost.locY = GHOST_STARTY;
                pinkGhost.gridCol = GHOST_GRIDCOL;
                pinkGhost.gridRow = GHOST_GRIDROW;
                pinkGhost.alive = true;
                pinkGhost.canEat = false;
                pinkDirect = 3;
                needLeftCheckPG = true;
                needUpCheckPG = true;
                needRightCheckPG = false;
                needDownCheckPG = true;
                break;
            case 3:
                orangeGhost.locX = orangeGhost.deadX;
                orangeGhost.locY = orangeGhost.deadY;
                tempTransition.setNode(orangeGhostIV);
                tempPath.getElements().add(new MoveTo(orangeGhost.locX+13,orangeGhost.locY+13));
                tempPath.getElements().add(new LineTo(GHOST_STARTX+13,GHOST_STARTY+13));
                tempTransition.play();
                orangeGhost.locX = GHOST_STARTX;
                orangeGhost.locY = GHOST_STARTY;
                orangeGhost.gridCol = GHOST_GRIDCOL;
                orangeGhost.gridRow = GHOST_GRIDROW;
                orangeGhost.alive = true;
                orangeGhost.canEat = false;
                orangeDirect = 2;
                needLeftCheckOG = true;
                needUpCheckOG = true;
                needRightCheckOG = false;
                needDownCheckOG = true;
                break;
            case 4:
                blueGhost.locX = blueGhost.deadX;
                blueGhost.locY = blueGhost.deadY;
                tempTransition.setNode(blueGhostIV);
                tempPath.getElements().add(new MoveTo(blueGhost.locX+13,blueGhost.locY+13));
                tempPath.getElements().add(new LineTo(GHOST_STARTX+13,GHOST_STARTY+13));
                tempTransition.play();
                blueGhost.locX = GHOST_STARTX;
                blueGhost.locY = GHOST_STARTY;
                blueGhost.gridCol = GHOST_GRIDCOL;
                blueGhost.gridRow = GHOST_GRIDROW;
                blueGhost.alive = true;
                blueGhost.canEat = false;
                blueDirect = 3;
                needLeftCheckBG = true;
                needUpCheckBG = true;
                needRightCheckBG = false;
                needDownCheckBG = true;
                break;
        }
    }
    
    //method to move red ghost
    public void moveRedGhost(){
        Path tempPath = new Path();
        PathTransition tempTransition = new PathTransition();
        tempTransition.setDuration(Duration.millis(300));
        tempTransition.setNode(redGhostIV);
        tempTransition.setPath(tempPath);
        //determine the direction
        if(level.map[redGhost.gridRow][redGhost.gridCol]==2 ||
                level.map[redGhost.gridRow][redGhost.gridCol]==5){
            //generate a random direction
            Random rand = new Random();
            redDirect = rand.nextInt(5)+1;
        }
        //depending on what direction is inputed, move ghost in that direction
        switch(redDirect){
            case UP1:
                if(level.map[redGhost.gridRow-1][redGhost.gridCol]!=0){
                    //fix up movement
                    if(needUpCheckRG){
                        redGhost.locY+=25;
                        if(!needLeftCheckRG){
                            redGhost.locX-=25;
                        }
                        needUpCheckRG=false;
                    }
                    tempPath.getElements().add(new MoveTo(redGhost.locX+13,redGhost.locY-13));
                    tempPath.getElements().add(new LineTo(redGhost.locX+13,redGhost.locY-38));
                    tempTransition.play();
                    //update ghost new location
                    redGhost.locY-=25;
                    redGhost.gridRow--;
                    //set all move checks
                    needLeftCheckRG=true;
                    needRightCheckRG=true;
                    needDownCheckRG=true;
                }
                break;
            case RIGHT2:
                if(level.map[redGhost.gridRow][redGhost.gridCol+1]!=0){
                    //fix right movement
                    if(!needUpCheckRG){
                        redGhost.locY-=25;
                        redGhost.locX+=25;
                    }
                    if(!needDownCheckRG){
                        redGhost.locX+=25;
                    }
                    if(needRightCheckRG){
                        redGhost.locX-=25;
                        needRightCheckRG=false;
                    }
                    tempPath.getElements().add(new MoveTo(redGhost.locX+13,redGhost.locY+13));
                    tempPath.getElements().add(new LineTo(redGhost.locX+38,redGhost.locY+13));
                    tempTransition.play();
                    //update ghost new location
                    redGhost.locX+=25;
                    redGhost.gridCol++;
                    //set all move checks
                    needLeftCheckRG=true;
                    needUpCheckRG=true;
                    needDownCheckRG=true;
                }
                break;
            case LEFT3:
                if(level.map[redGhost.gridRow][redGhost.gridCol-1]!=0){
                    //fix left movement
                    if(!needUpCheckRG){
                        redGhost.locY-=25;
                    }
                    if(needLeftCheckRG){
                        redGhost.locX+=25;
                        needLeftCheckRG=false;
                    }
                    tempPath.getElements().add(new MoveTo(redGhost.locX-13,redGhost.locY+13));
                    tempPath.getElements().add(new LineTo(redGhost.locX-38,redGhost.locY+13));
                    tempTransition.play();
                    //update ghost new location
                    redGhost.locX-=25;
                    redGhost.gridCol--;
                    //update checks
                    needRightCheckRG=true;
                    needDownCheckRG=true;
                    needUpCheckRG=true;
                }
                break;
            case DOWN4:
                if(level.map[redGhost.gridRow+1][redGhost.gridCol]!=0){
                    //fix down movement
                    if(needDownCheckRG){
                        redGhost.locY-=25;
                        if(!needLeftCheckRG){
                            redGhost.locX-=25;
                            redGhost.locY+=25;
                        }
                        if(!needRightCheckRG){
                            redGhost.locY+=25;
                        }
                        needDownCheckRG=false;
                    }
                    tempPath.getElements().add(new MoveTo(redGhost.locX+13,redGhost.locY+13));
                    tempPath.getElements().add(new LineTo(redGhost.locX+13,redGhost.locY+38));
                    tempTransition.play();
                    //update new location
                    redGhost.locY+=25;
                    redGhost.gridRow++;
                    //update checks
                    needLeftCheckRG=true;
                    needRightCheckRG=true;
                    needUpCheckRG=true;
                }
                break;
        }
        //kill pacman if ghost can
        if(!redGhost.canEat &&(pacMan.gridRow==redGhost.gridRow && pacMan.gridCol==redGhost.gridCol)){
            killPacMan();
        }
    }

    //method to move pink ghost
    public void movePinkGhost(){
        Path tempPath = new Path();
        PathTransition tempTransition = new PathTransition();
        tempTransition.setDuration(Duration.millis(300));
        tempTransition.setNode(pinkGhostIV);
        tempTransition.setPath(tempPath);
        //determine the direction
        if(level.map[pinkGhost.gridRow][pinkGhost.gridCol]==2 ||
                level.map[pinkGhost.gridRow][pinkGhost.gridCol]==5){
            //generate a random direction
            Random rand = new Random();
            pinkDirect = rand.nextInt(5)+1;
        }
        //depending on what direction is inputed, move ghost in that direction
        switch(pinkDirect){
            case UP1:
                if(level.map[pinkGhost.gridRow-1][pinkGhost.gridCol]!=0){
                    //fix up movement
                    if(needUpCheckPG){
                        pinkGhost.locY+=25;
                        if(!needLeftCheckPG){
                            pinkGhost.locX-=25;
                        }
                        needUpCheckPG=false;
                    }
                    tempPath.getElements().add(new MoveTo(pinkGhost.locX+13,pinkGhost.locY-13));
                    tempPath.getElements().add(new LineTo(pinkGhost.locX+13,pinkGhost.locY-38));
                    tempTransition.play();
                    //update ghost new location
                    pinkGhost.locY-=25;
                    pinkGhost.gridRow--;
                    //set all move checks
                    needLeftCheckPG=true;
                    needRightCheckPG=true;
                    needDownCheckPG=true;
                }
                break;
            case RIGHT2:
                if(level.map[pinkGhost.gridRow][pinkGhost.gridCol+1]!=0){
                    //fix right movement
                    if(!needUpCheckPG){
                        pinkGhost.locY-=25;
                        pinkGhost.locX+=25;
                    }
                    if(!needDownCheckPG){
                        pinkGhost.locX+=25;
                    }
                    if(needRightCheckPG){
                        pinkGhost.locX-=25;
                        needRightCheckPG=false;
                    }
                    tempPath.getElements().add(new MoveTo(pinkGhost.locX+13,pinkGhost.locY+13));
                    tempPath.getElements().add(new LineTo(pinkGhost.locX+38,pinkGhost.locY+13));
                    tempTransition.play();
                    //update ghost new location
                    pinkGhost.locX+=25;
                    pinkGhost.gridCol++;
                    //set all move checks
                    needLeftCheckPG=true;
                    needUpCheckPG=true;
                    needDownCheckPG=true;
                }
                break;
            case LEFT3:
                if(level.map[pinkGhost.gridRow][pinkGhost.gridCol-1]!=0){
                    //fix left movement
                    if(!needUpCheckPG){
                        pinkGhost.locY-=25;
                    }
                    if(needLeftCheckPG){
                        pinkGhost.locX+=25;
                        needLeftCheckPG=false;
                    }
                    tempPath.getElements().add(new MoveTo(pinkGhost.locX-13,pinkGhost.locY+13));
                    tempPath.getElements().add(new LineTo(pinkGhost.locX-38,pinkGhost.locY+13));
                    tempTransition.play();
                    //update ghost new location
                    pinkGhost.locX-=25;
                    pinkGhost.gridCol--;
                    //update checks
                    needRightCheckPG=true;
                    needDownCheckPG=true;
                    needUpCheckPG=true;
                }
                break;
            case DOWN4:
                if(level.map[pinkGhost.gridRow+1][pinkGhost.gridCol]!=0){
                    //fix down movement
                    if(needDownCheckPG){
                        pinkGhost.locY-=25;
                        if(!needLeftCheckPG){
                            pinkGhost.locX-=25;
                            pinkGhost.locY+=25;
                        }
                        if(!needRightCheckPG){
                            pinkGhost.locY+=25;
                        }
                        needDownCheckPG=false;
                    }
                    tempPath.getElements().add(new MoveTo(pinkGhost.locX+13,pinkGhost.locY+13));
                    tempPath.getElements().add(new LineTo(pinkGhost.locX+13,pinkGhost.locY+38));
                    tempTransition.play();
                    //update new location
                    pinkGhost.locY+=25;
                    pinkGhost.gridRow++;
                    //update checks
                    needLeftCheckPG=true;
                    needRightCheckPG=true;
                    needUpCheckPG=true;
                }
                break;
        }
        //kill pacman if ghost can
        if(!pinkGhost.canEat &&(pacMan.gridRow==pinkGhost.gridRow && pacMan.gridCol==pinkGhost.gridCol)){
            killPacMan();
        }
    }
    
    //method to move orange ghost
    public void moveOrangeGhost(){
        Path tempPath = new Path();
        PathTransition tempTransition = new PathTransition();
        tempTransition.setDuration(Duration.millis(300));
        tempTransition.setNode(orangeGhostIV);
        tempTransition.setPath(tempPath);
        //determine the direction
        if(level.map[orangeGhost.gridRow][orangeGhost.gridCol]==2 ||
                level.map[orangeGhost.gridRow][orangeGhost.gridCol]==5){
            //generate a random direction
            Random rand = new Random();
            orangeDirect = rand.nextInt(5)+1;
        }
        //depending on what direction is inputed, move ghost in that direction
        switch(orangeDirect){
            case UP1:
                if(level.map[orangeGhost.gridRow-1][orangeGhost.gridCol]!=0){
                    //fix up movement
                    if(needUpCheckOG){
                        orangeGhost.locY+=25;
                        if(!needLeftCheckOG){
                            orangeGhost.locX-=25;
                        }
                        needUpCheckOG=false;
                    }
                    tempPath.getElements().add(new MoveTo(orangeGhost.locX+13,orangeGhost.locY-13));
                    tempPath.getElements().add(new LineTo(orangeGhost.locX+13,orangeGhost.locY-38));
                    tempTransition.play();
                    //update ghost new location
                    orangeGhost.locY-=25;
                    orangeGhost.gridRow--;
                    //set all move checks
                    needLeftCheckOG=true;
                    needRightCheckOG=true;
                    needDownCheckOG=true;
                }
                break;
            case RIGHT2:
                if(level.map[orangeGhost.gridRow][orangeGhost.gridCol+1]!=0){
                    //fix right movement
                    if(!needUpCheckOG){
                        orangeGhost.locY-=25;
                        orangeGhost.locX+=25;
                    }
                    if(!needDownCheckOG){
                        orangeGhost.locX+=25;
                    }
                    if(needRightCheckOG){
                        orangeGhost.locX-=25;
                        needRightCheckOG=false;
                    }
                    tempPath.getElements().add(new MoveTo(orangeGhost.locX+13,orangeGhost.locY+13));
                    tempPath.getElements().add(new LineTo(orangeGhost.locX+38,orangeGhost.locY+13));
                    tempTransition.play();
                    //update ghost new location
                    orangeGhost.locX+=25;
                    orangeGhost.gridCol++;
                    //set all move checks
                    needLeftCheckOG=true;
                    needUpCheckOG=true;
                    needDownCheckOG=true;
                }
                break;
            case LEFT3:
                if(level.map[orangeGhost.gridRow][orangeGhost.gridCol-1]!=0){
                    //fix left movement
                    if(!needUpCheckOG){
                        orangeGhost.locY-=25;
                    }
                    if(needLeftCheckOG){
                        orangeGhost.locX+=25;
                        needLeftCheckOG=false;
                    }
                    tempPath.getElements().add(new MoveTo(orangeGhost.locX-13,orangeGhost.locY+13));
                    tempPath.getElements().add(new LineTo(orangeGhost.locX-38,orangeGhost.locY+13));
                    tempTransition.play();
                    //update ghost new location
                    orangeGhost.locX-=25;
                    orangeGhost.gridCol--;
                    //update checks
                    needRightCheckOG=true;
                    needDownCheckOG=true;
                    needUpCheckOG=true;
                }
                break;
            case DOWN4:
                if(level.map[orangeGhost.gridRow+1][orangeGhost.gridCol]!=0){
                    //fix down movement
                    if(needDownCheckOG){
                        orangeGhost.locY-=25;
                        if(!needLeftCheckOG){
                            orangeGhost.locX-=25;
                            orangeGhost.locY+=25;
                        }
                        if(!needRightCheckOG){
                            orangeGhost.locY+=25;
                        }
                        needDownCheckOG=false;
                    }
                    tempPath.getElements().add(new MoveTo(orangeGhost.locX+13,orangeGhost.locY+13));
                    tempPath.getElements().add(new LineTo(orangeGhost.locX+13,orangeGhost.locY+38));
                    tempTransition.play();
                    //update new location
                    orangeGhost.locY+=25;
                    orangeGhost.gridRow++;
                    //update checks
                    needLeftCheckOG=true;
                    needRightCheckOG=true;
                    needUpCheckOG=true;
                }
                break;
        }
        //kill pacman if ghost can
        if(!orangeGhost.canEat &&(pacMan.gridRow==orangeGhost.gridRow && pacMan.gridCol==orangeGhost.gridCol)){
            killPacMan();
        }
    }
    
    //method to move blue ghost
    public void moveBlueGhost(){
        Path tempPath = new Path();
        PathTransition tempTransition = new PathTransition();
        tempTransition.setDuration(Duration.millis(300));
        tempTransition.setNode(blueGhostIV);
        tempTransition.setPath(tempPath);
        //determine the direction
        if(level.map[blueGhost.gridRow][blueGhost.gridCol]==2 ||
                level.map[blueGhost.gridRow][blueGhost.gridCol]==5){
            //generate a random direction
            Random rand = new Random();
            blueDirect = rand.nextInt(5)+1;
        }
        //depending on what direction is inputed, move ghost in that direction
        switch(blueDirect){
            case UP1:
                if(level.map[blueGhost.gridRow-1][blueGhost.gridCol]!=0){
                    //fix up movement
                    if(needUpCheckBG){
                        blueGhost.locY+=25;
                        if(!needLeftCheckBG){
                            blueGhost.locX-=25;
                        }
                        needUpCheckBG=false;
                    }
                    tempPath.getElements().add(new MoveTo(blueGhost.locX+13,blueGhost.locY-13));
                    tempPath.getElements().add(new LineTo(blueGhost.locX+13,blueGhost.locY-38));
                    tempTransition.play();
                    //update ghost new location
                    blueGhost.locY-=25;
                    blueGhost.gridRow--;
                    //set all move checks
                    needLeftCheckBG=true;
                    needRightCheckBG=true;
                    needDownCheckBG=true;
                }
                break;
            case RIGHT2:
                if(level.map[blueGhost.gridRow][blueGhost.gridCol+1]!=0){
                    //fix right movement
                    if(!needUpCheckBG){
                        blueGhost.locY-=25;
                        blueGhost.locX+=25;
                    }
                    if(!needDownCheckBG){
                        blueGhost.locX+=25;
                    }
                    if(needRightCheckBG){
                        blueGhost.locX-=25;
                        needRightCheckBG=false;
                    }
                    tempPath.getElements().add(new MoveTo(blueGhost.locX+13,blueGhost.locY+13));
                    tempPath.getElements().add(new LineTo(blueGhost.locX+38,blueGhost.locY+13));
                    tempTransition.play();
                    //update ghost new location
                    blueGhost.locX+=25;
                    blueGhost.gridCol++;
                    //set all move checks
                    needLeftCheckBG=true;
                    needUpCheckBG=true;
                    needDownCheckBG=true;
                }
                break;
            case LEFT3:
                if(level.map[blueGhost.gridRow][blueGhost.gridCol-1]!=0){
                    //fix left movement
                    if(!needUpCheckBG){
                        blueGhost.locY-=25;
                    }
                    if(needLeftCheckBG){
                        blueGhost.locX+=25;
                        needLeftCheckBG=false;
                    }
                    tempPath.getElements().add(new MoveTo(blueGhost.locX-13,blueGhost.locY+13));
                    tempPath.getElements().add(new LineTo(blueGhost.locX-38,blueGhost.locY+13));
                    tempTransition.play();
                    //update ghost new location
                    blueGhost.locX-=25;
                    blueGhost.gridCol--;
                    //update checks
                    needRightCheckBG=true;
                    needDownCheckBG=true;
                    needUpCheckBG=true;
                }
                break;
            case DOWN4:
                if(level.map[blueGhost.gridRow+1][blueGhost.gridCol]!=0){
                    //fix down movement
                    if(needDownCheckBG){
                        blueGhost.locY-=25;
                        if(!needLeftCheckBG){
                            blueGhost.locX-=25;
                            blueGhost.locY+=25;
                        }
                        if(!needRightCheckBG){
                            blueGhost.locY+=25;
                        }
                        needDownCheckBG=false;
                    }
                    tempPath.getElements().add(new MoveTo(blueGhost.locX+13,blueGhost.locY+13));
                    tempPath.getElements().add(new LineTo(blueGhost.locX+13,blueGhost.locY+38));
                    tempTransition.play();
                    //update new location
                    blueGhost.locY+=25;
                    blueGhost.gridRow++;
                    //update checks
                    needLeftCheckBG=true;
                    needRightCheckBG=true;
                    needUpCheckBG=true;
                }
                break;
        }
        //kill pacman if ghost can
        if(!blueGhost.canEat &&(pacMan.gridRow==blueGhost.gridRow && pacMan.gridCol==blueGhost.gridCol)){
            killPacMan();
        }
    }
    
    //method to remove the dot if pacman eats it
    public void eatDot(){
        //update scores
        pacMan.levelScore+=25;
        levelScoreL.setText(pacMan.levelScore+"");
        //remove the dot from screen
        Rectangle wall = new Rectangle(25,25,25,25);
        wall.setFill(Color.BLACK);
        gp1.add(wall,pacMan.gridCol,pacMan.gridRow);
        //update map to show dot is gone
        if(level.map[pacMan.gridRow][pacMan.gridCol]==DOT){
            level.map[pacMan.gridRow][pacMan.gridCol] = SPACE1;
        }
        else if(level.map[pacMan.gridRow][pacMan.gridCol]==DOT2){
            level.map[pacMan.gridRow][pacMan.gridCol] = DOT3;
        }
        //update the count and check if all the dots are gone
        dotCount--;
        if(dotCount<=0){
           levelOver=true;
           endLevel();
        }
    }
    
    //method to eat pellet and change ghost to edible
    public void eatPellet(){
        //update scores
        pacMan.levelScore+=100;
        levelScoreL.setText(pacMan.levelScore+"");
        //remove the pellet from screen
        Rectangle space = new Rectangle(25,25,25,25);
        space.setFill(Color.BLACK);
        gp1.add(space,pacMan.gridCol,pacMan.gridRow);
        //update map to show pellet is gone
        level.map[pacMan.gridRow][pacMan.gridCol] = SPACE1;
        //make ghost edible
        redGhost.canEat = true;
        pinkGhost.canEat = true;
        orangeGhost.canEat = true;
        blueGhost.canEat = true;
        //change their color
        if(redGhost.alive){
            redGhostIV.setImage(ghostpelletblue);
        }
        if(pinkGhost.alive){
            pinkGhostIV.setImage(ghostpelletblue);
        }
        if(orangeGhost.alive){
            orangeGhostIV.setImage(ghostpelletblue);
        }
        if(blueGhost.alive){
            blueGhostIV.setImage(ghostpelletblue);
        }
        pelletTime = seconds;
        pelletCount = 50;
    }
    
    //method to eat ghost
    public void eatGhost(int ghost){
        Path tempPath = new Path();
        PathTransition tempTransition = new PathTransition();
        tempTransition.setDuration(Duration.millis(1000));
        tempTransition.setPath(tempPath);
        //update scores
        pacMan.levelScore+=200;
        levelScoreL.setText(pacMan.levelScore+"");
        //kill which ghost was ate and reset to middle and grid position
        switch(ghost){
            case RED_GHOST:
                redGhost.alive = false;
                tempTransition.setNode(redGhostIV);
                tempPath.getElements().add(new MoveTo(redGhost.locX+13,redGhost.locY+13));
                tempPath.getElements().add(new LineTo(RG_DEADX+13,G_DEADY+13));
                tempTransition.play();
                redGhostIV.setImage(redghost);
                redGhost.gridCol = 0;
                redGhost.gridRow = 0;
                break;
            case PINK_GHOST:
                pinkGhost.alive = false;
                tempTransition.setNode(pinkGhostIV);
                tempPath.getElements().add(new MoveTo(pinkGhost.locX+13,pinkGhost.locY+13));
                tempPath.getElements().add(new LineTo(PG_DEADX+13,G_DEADY+13));
                tempTransition.play();
                pinkGhostIV.setImage(pinkghost);
                pinkGhost.gridCol = 0;
                pinkGhost.gridRow = 0;
                break;
            case ORANGE_GHOST:
                orangeGhost.alive = false;
                tempTransition.setNode(orangeGhostIV);
                tempPath.getElements().add(new MoveTo(orangeGhost.locX+13,orangeGhost.locY+13));
                tempPath.getElements().add(new LineTo(OG_DEADX+13,G_DEADY+13));
                tempTransition.play();
                orangeGhostIV.setImage(orangeghost);
                orangeGhost.gridCol = 0;
                orangeGhost.gridRow = 0;
                break;
            case BLUE_GHOST:
                blueGhost.alive = false;
                tempTransition.setNode(blueGhostIV);
                tempPath.getElements().add(new MoveTo(blueGhost.locX+13,blueGhost.locY+13));
                tempPath.getElements().add(new LineTo(BG_DEADX+13,G_DEADY+13));
                tempTransition.play();
                blueGhostIV.setImage(blueghost);
                blueGhost.gridCol = 0;
                blueGhost.gridRow = 0;
                break;
        }
        
    }
    
    //method to kill pacman
    public void killPacMan(){
        //update lives
        pacMan.lives--;
        //move pacman to starting position 
        pacIV.setImage(pacmanRight);
        Path tempPath = new Path();
        PathTransition tempTransition = new PathTransition();
        tempTransition.setDuration(Duration.millis(300));
        tempTransition.setNode(pacIV);
        tempTransition.setPath(tempPath);
        tempPath.getElements().add(new MoveTo(pacMan.locX+13,pacMan.locY+13));
        tempPath.getElements().add(new LineTo(pacMan.startLocX+13,pacMan.startLocY+13));
        tempTransition.play();
        pacMan.gridCol = pacMan.startGridCol;
        pacMan.gridRow = pacMan.startGridRow;
        pacMan.locX = pacMan.startLocX;
        pacMan.locY = pacMan.startLocY;
        //reset move checks
        needLeftCheck = true;
        needUpCheck = true;
        needRightCheck = false;
        needDownCheck = true;
        //update the lives display
        updateLivesDisplay();
        //check if game is over
        if(pacMan.lives<=0){
            gameOver=true;
            endGame();
        }
    }
    
    //method to display the high scores
    public GridPane showHighScores(LinkedList levelList, int level){
        //create temp gridpane
        GridPane tempGP = new GridPane();
            tempGP.setHgap(20);
        
        highScore1 = new Label("1.");
            highScore1.setFont(new Font("Arial", 30));
        highScore1N = new Label(showHighScoreName(levelList, 0));
            highScore1N.setFont(new Font("Arial", 30));
            highScore1N.getStyleClass().addAll("label", "labelName");
        highScore1P = new Label(showHighScorePoints(levelList, 0));
            highScore1P.setFont(new Font("Arial", 30));
            highScore1P.getStyleClass().addAll("label", "labelScore");
        highScore2 = new Label("2.");
            highScore2.setFont(new Font("Arial", 30));
        highScore2N = new Label(showHighScoreName(levelList, 1));
            highScore2N.setFont(new Font("Arial", 30));
        highScore2P = new Label(showHighScorePoints(levelList, 1));
            highScore2P.setFont(new Font("Arial", 30));
        highScore3 = new Label("3.");
            highScore3.setFont(new Font("Arial", 30));
        highScore3N = new Label(showHighScoreName(levelList, 2));
            highScore3N.setFont(new Font("Arial", 30));
        highScore3P = new Label(showHighScorePoints(levelList, 2));
            highScore3P.setFont(new Font("Arial", 30));
        highScore4 = new Label("4.");
            highScore4.setFont(new Font("Arial", 30));
        highScore4N = new Label(showHighScoreName(levelList, 3));
            highScore4N.setFont(new Font("Arial", 30));
        highScore4P = new Label(showHighScorePoints(levelList, 3));
            highScore4P.setFont(new Font("Arial", 30));
        highScore5 = new Label("5.");
            highScore5.setFont(new Font("Arial", 30));
        highScore5N = new Label(showHighScoreName(levelList, 4));
            highScore5N.setFont(new Font("Arial", 30));
        highScore5P = new Label(showHighScorePoints(levelList, 4));
            highScore5P.setFont(new Font("Arial", 30));
            
            tempGP.add(highScore1,0,0);
            tempGP.add(highScore1N,1,0);
            tempGP.add(highScore1P,2,0);
                tempGP.setHalignment(highScore1, HPos.RIGHT);
                tempGP.setHalignment(highScore1N, HPos.LEFT);
                tempGP.setHalignment(highScore1P, HPos.RIGHT);
            tempGP.add(highScore2,0,1);
            tempGP.add(highScore2N,1,1);
            tempGP.add(highScore2P,2,1);
                tempGP.setHalignment(highScore2, HPos.RIGHT);
                tempGP.setHalignment(highScore2N, HPos.LEFT);
                tempGP.setHalignment(highScore2P, HPos.RIGHT);
            tempGP.add(highScore3,0,2);
            tempGP.add(highScore3N,1,2);
            tempGP.add(highScore3P,2,2);
                tempGP.setHalignment(highScore3, HPos.RIGHT);
                tempGP.setHalignment(highScore3N, HPos.LEFT);
                tempGP.setHalignment(highScore3P, HPos.RIGHT);
            tempGP.add(highScore4,0,3);
            tempGP.add(highScore4N,1,3);
            tempGP.add(highScore4P,2,3);
                tempGP.setHalignment(highScore4, HPos.RIGHT);
                tempGP.setHalignment(highScore4N, HPos.LEFT);
                tempGP.setHalignment(highScore4P, HPos.RIGHT);
            tempGP.add(highScore5,0,4);
            tempGP.add(highScore5N,1,4);
            tempGP.add(highScore5P,2,4);
                tempGP.setHalignment(highScore5, HPos.RIGHT);
                tempGP.setHalignment(highScore5N, HPos.LEFT);
                tempGP.setHalignment(highScore5P, HPos.RIGHT);

        //if a level then show all 10
        if(level>0){
            highScore6 = new Label("6.");
                highScore6.setFont(new Font("Arial", 30));
            highScore6N = new Label(showHighScoreName(levelList, 5));
                highScore6N.setFont(new Font("Arial", 30));
            highScore6P = new Label(showHighScorePoints(levelList, 5));
                highScore6P.setFont(new Font("Arial", 30));
            highScore7 = new Label("7.");
                highScore7.setFont(new Font("Arial", 30));
            highScore7N = new Label(showHighScoreName(levelList, 6));
                highScore7N.setFont(new Font("Arial", 30));
            highScore7P = new Label(showHighScorePoints(levelList, 6));
                highScore7P.setFont(new Font("Arial", 30));
            highScore8 = new Label("8.");
                highScore8.setFont(new Font("Arial", 30));
            highScore8N = new Label(showHighScoreName(levelList, 7));
                highScore8N.setFont(new Font("Arial", 30));
            highScore8P = new Label(showHighScorePoints(levelList, 7));
                highScore8P.setFont(new Font("Arial", 30));
            highScore9 = new Label("9.");
                highScore9.setFont(new Font("Arial", 30));
            highScore9N = new Label(showHighScoreName(levelList, 8));
                highScore9N.setFont(new Font("Arial", 30));
            highScore9P = new Label(showHighScorePoints(levelList, 8));
                highScore9P.setFont(new Font("Arial", 30));
            highScore10 = new Label("10.");
                highScore10.setFont(new Font("Arial", 30));
            highScore10N = new Label(showHighScoreName(levelList, 9));
                highScore10N.setFont(new Font("Arial", 30));
            highScore10P = new Label(showHighScorePoints(levelList, 9));
                highScore10P.setFont(new Font("Arial", 30));
           
                tempGP.add(highScore6,0,5);
                tempGP.add(highScore6N,1,5);
                tempGP.add(highScore6P,2,5);
                    tempGP.setHalignment(highScore6, HPos.RIGHT);
                    tempGP.setHalignment(highScore6N, HPos.LEFT);
                    tempGP.setHalignment(highScore6P, HPos.RIGHT);
                tempGP.add(highScore7,0,6);
                tempGP.add(highScore7N,1,6);
                tempGP.add(highScore7P,2,6);
                    tempGP.setHalignment(highScore7, HPos.RIGHT);
                    tempGP.setHalignment(highScore7N, HPos.LEFT);
                    tempGP.setHalignment(highScore7P, HPos.RIGHT);
                tempGP.add(highScore8,0,7);
                tempGP.add(highScore8N,1,7);
                tempGP.add(highScore8P,2,7);
                    tempGP.setHalignment(highScore8, HPos.RIGHT);
                    tempGP.setHalignment(highScore8N, HPos.LEFT);
                    tempGP.setHalignment(highScore8P, HPos.RIGHT);
                tempGP.add(highScore9,0,8);
                tempGP.add(highScore9N,1,8);
                tempGP.add(highScore9P,2,8);
                    tempGP.setHalignment(highScore9, HPos.RIGHT);
                    tempGP.setHalignment(highScore9N, HPos.LEFT);
                    tempGP.setHalignment(highScore9P, HPos.RIGHT);
                tempGP.add(highScore10,0,9);
                tempGP.add(highScore10N,1,9);
                tempGP.add(highScore10P,2,9);
                    tempGP.setHalignment(highScore10, HPos.RIGHT);
                    tempGP.setHalignment(highScore10N, HPos.LEFT);
                    tempGP.setHalignment(highScore10P, HPos.RIGHT);
        }
        return tempGP;
    }
    
    //update highscores after a new one is added
    public void reshowScores(LinkedList levelList, int level){
        highScore1N.setText(showHighScoreName(levelList, 0));
        highScore1P.setText(showHighScorePoints(levelList, 0));
        highScore2N.setText(showHighScoreName(levelList, 1));
        highScore2P.setText(showHighScorePoints(levelList, 1));
        highScore3N.setText(showHighScoreName(levelList, 2));
        highScore3P.setText(showHighScorePoints(levelList, 2));
        highScore4N.setText(showHighScoreName(levelList, 3));
        highScore4P.setText(showHighScorePoints(levelList, 3));
        highScore5N.setText(showHighScoreName(levelList, 4));
        highScore5P.setText(showHighScorePoints(levelList, 4));
        highScore6N.setText(showHighScoreName(levelList, 5));
        highScore6P.setText(showHighScorePoints(levelList, 5));
        highScore7N.setText(showHighScoreName(levelList, 6));
        highScore7P.setText(showHighScorePoints(levelList, 6));
        highScore8N.setText(showHighScoreName(levelList, 7));
        highScore8P.setText(showHighScorePoints(levelList, 7));
        highScore9N.setText(showHighScoreName(levelList, 8));
        highScore9P.setText(showHighScorePoints(levelList, 8));
        highScore10N.setText(showHighScoreName(levelList, 9));
        highScore10P.setText(showHighScorePoints(levelList, 9));
    }
    
    //method to read the high score file
    public LinkedList readScoreFile(String fileName){
        //create the ArrayList
        LinkedList scoreArr = new LinkedList();
        try{
            FileInputStream fileInput = new FileInputStream(new File(fileName));
            BufferedReader buffRead = new BufferedReader(new InputStreamReader(fileInput));
            String line = null;
            while ((line = buffRead.readLine()) != null){
                String[] fields = line.split(",");
                //add a new car object to array list using the data from file
                scoreArr.add(new HighScore(fields[0], fields[1]));
            }
        }
        catch(Exception e){}
        return scoreArr;
    }
    
    //method to update high scores
    public void updateHighScores(int temp){
        //create start popup stage
        Stage scorePopUp = new Stage();
            scorePopUp.initModality(Modality.APPLICATION_MODAL);
            scorePopUp.initStyle(StageStyle.UNDECORATED);
        //declae labels for update popup
        updateL1 = new Label("You Have A New High Score!");
            updateL1.setFont(new Font("Arial", 27));
            updateL1.getStyleClass().addAll("label", "labelTwo");
            if(temp==1){
                updateL2 = new Label("Level "+levelInt+" Score: "+pacMan.levelScore);
            }
            else{
                updateL2 = new Label("Total Score: "+pacMan.totalScore);
            }
            updateL2.setFont(new Font("Arial", 25));
            updateL2.getStyleClass().addAll("label");
        updateL3 = new Label("Enter Your Name");
            updateL3.setFont(new Font("Arial", 25));
            updateL3.getStyleClass().addAll("label");
        updateL4 = new Label("(5 Charactor Limit)");
            updateL4.setFont(new Font("Arial", 15));
            updateL4.getStyleClass().addAll("label");
        TextField tempText = new TextField();
            tempText.setMaxWidth(100);
            
        doneB = new Button();
            doneB.setText("Done");
            doneB.setOnAction(e->{
                if(tempText.getText().length()!= 0 && tempText.getText().length() <= 5){
                    for(int i=0;i<9;i++){
                        if(temp==1){
                            HighScore tempScore = (HighScore)levelHigh.get(i);
                            if(pacMan.levelScore>tempScore.score){
                                HighScore newScore = new HighScore(tempText.getText().toUpperCase(),pacMan.levelScore);
                                levelHigh.add(i,newScore);
                                writeScoreFile(1);
                                i=10;
                                reshowScores(levelHigh, levelInt);
                            }
                        }
                        else{
                            HighScore tempScore = (HighScore)totalHigh.get(i);
                            if(pacMan.totalScore>tempScore.score){
                                HighScore newScore = new HighScore(tempText.getText().toUpperCase(),pacMan.totalScore);
                                totalHigh.add(i,newScore);
                                writeScoreFile(0);
                                i=10;
                                System.exit(0);
                            }
                        }
                    }
                    scorePopUp.close();
            }
            });
       
            //create vbox to hold end box labels
            vb9 = new VBox();
                vb9.getChildren().addAll(updateL1, updateL2, updateL3, updateL4, tempText, doneB);
                vb9.getStyleClass().addAll("borderpane", "vboxStart");
                vb9.setAlignment(Pos.CENTER);
            //create start box scene
            Scene popUpScene = new Scene(vb9, 400, 500);
            popUpScene.getStylesheets().add("pacmanapp/PacManCSS.css");
            
        scorePopUp.setScene(popUpScene);
        scorePopUp.setResizable(false);
        scorePopUp.show();
    }
    
    //method to save high scores
    public void writeScoreFile(int temp){
        try{
            if(temp==1){
                FileWriter filewrite = new FileWriter("HighScores"+levelInt+".dat");
                PrintWriter printwrite = new PrintWriter(filewrite);
                for(int i=0; i<=9; i++){
                    HighScore score = (HighScore)levelHigh.get(i);
                    String record = (score.name+","+score.score);
                    printwrite.println(record);
                }
                filewrite.close();
                printwrite.close();
            }
            else{
                FileWriter filewrite = new FileWriter("HighScores0.dat");
                PrintWriter printwrite = new PrintWriter(filewrite);
                for(int i=0; i<=9; i++){
                    HighScore score = (HighScore)totalHigh.get(i);
                    String record = (score.name+","+score.score);
                    printwrite.println(record);
                }
                filewrite.close();
                printwrite.close();
            } 
        }
        catch(Exception e){e.printStackTrace();}
    }
    
    //method to show each high score name
    public String showHighScoreName(LinkedList scoreList, int place){
        HighScore temp = (HighScore)scoreList.get(place);
        return  ""+temp.name;
    }
    
    //method to show each highscore score
    public String showHighScorePoints(LinkedList scoreList, int place){
        HighScore temp = (HighScore)scoreList.get(place);
        return ""+temp.score;
    }
    
    public void updateLivesDisplay(){
        Rectangle blank = new Rectangle(30,30,30,30);
            blank.setFill(Color.BLACK);
        Rectangle blank2 = new Rectangle(30,30,30,30);
            blank2.setFill(Color.BLACK);
        Rectangle blank3 = new Rectangle(30,30,30,30);
            blank3.setFill(Color.BLACK);
        Rectangle blank4 = new Rectangle(30,30,30,30);
            blank3.setFill(Color.BLACK);
            
        //depending on how many lives pacman hs populate display
        switch(pacMan.lives){
            case 2:
                gp3.add(blank,2,0);
                break;
            case 1:
                gp3.add(blank2,1,0);
                gp3.add(blank3,2,0);
                break;
            case 0:
                gp3.add(blank4,0,0);

        }
    }  

}