package game;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.geometry.VPos;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.awt.*;

public class GameDriver {
    private static final int SIZE = 500;
    private static final int FRAMES_PER_SECOND = 60;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private static final String TITLE = "Breakout Game by Louis Jensen";
    private Stage GAME_STAGE;
    private static final Paint BACKGROUND = Color.AZURE;
    public static final Paint MOVER_COLOR = Color.PLUM;
    public static final int MOVER_SIZE = 50;
    public static final int MOVER_SPEED = 5;
    private Scene myScene;
    private Paddle paddle;
    private Ball ball;
    private int ballXSpeed = (int)(Math.random() * 360 + 1) - 180;
    private int ballYSpeed = -180;
    private int LEVEL = 1;
    private String stringLevel = "Level ";
    private int intLevel = 1;
    private int nextLEVEL;
    private String levelStatus;
    private int livesRemaining;
    private Group root = new Group();
    private ArrayList<Block>  blockList = new ArrayList<Block>();
    private int blocksDestroyed = 0;
    private int numBlocks;
    private Scene levelScene = new Scene(root, SIZE, SIZE, BACKGROUND);
    private Timeline animation;



    public void startGame(Stage stage){
        GAME_STAGE = stage;
        GAME_STAGE.setTitle(TITLE);
        GAME_STAGE.show();

    }

    public void playGame(){
        makeMenu();
    }

    private void makeLevel(int level){
        LEVEL = level;
        levelStatus = "You failed ";
        livesRemaining = 3;
        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();

        root.getChildren().clear();
        blockList.clear();
        blocksDestroyed=0;
        numBlocks=0;
        //root = new Group();
       // Scene levelScene = new Scene(root, SIZE, SIZE, BACKGROUND);

        paddle = new Paddle(levelScene);
        ball = new Ball(levelScene);
        makeBlockLayout(level);
        root.getChildren().add(paddle.getPaddle());
        root.getChildren().add(ball.getBall());
        levelScene.setOnKeyPressed(e -> levelHandleKeyInput(e.getCode()));

        myScene = levelScene;
        GAME_STAGE.setScene(levelScene);
    }

    private void makeBlockLayout(int lvl){
        if(lvl ==1) {
            int count = 1;
            for (int i = 10; i < 500; i += 50) {
                int evenOdd = count % 2;
                Block block = new Block(i, 200, 1);
                numBlocks++;
                Block blockUpper = new Block(i, 100, evenOdd + 2);
                root.getChildren().addAll((block.getBlock()), (blockUpper.getBlock()));
                blockList.add(block);
                blockList.add(blockUpper);
                numBlocks++;
                count++;
            }

        }
        if (lvl == 2){
            makeDiamondOfBlocks(80, 30);
        }
        if (lvl == 3){
            for (int i = 80; i < 330; i += 60){
                makeSixBlocks(i, (int)(Math.random() * 400 + 30));
            }
        }
    }

    private void makeDiamondOfBlocks(int xPos, int yPos){
        int distanceBetween = 50;
        for (int i = 0; i<distanceBetween*7; i+=distanceBetween){
            for (int j = 0; j<distanceBetween*7; j+=distanceBetween){
                int distanceFromCenter = Math.abs(i-distanceBetween*3) + Math.abs(j-distanceBetween*3);
                if(distanceFromCenter > distanceBetween*3) continue;
                int blockHits = 4 - distanceFromCenter/distanceBetween;
                if (blockHits ==4) blockHits=1;
                Block diamondBlock = new Block(xPos+i, yPos+j, blockHits);
                root.getChildren().add(diamondBlock.getBlock());
                blockList.add(diamondBlock);
                numBlocks++;
            }
        }
    }

  //  private int checkHitsForDiamond(){

    //}

    private void makeSixBlocks(int yPos, int xPos){
        int blockHits = 1;
        for (int i = 0; i < 60; i+=20){
            Block blockLeft = new Block(xPos, yPos-i, blockHits);
            Block blockRight = new Block(xPos + 40, yPos-i, blockHits);
            root.getChildren().addAll((blockLeft.getBlock()), (blockRight.getBlock()));
            blockList.add(blockLeft);
            blockList.add(blockRight);
            numBlocks+=2;
            blockHits++;
        }
    }

    private void makeMenu(){
        LEVEL++;
        StackPane root = new StackPane();
        Text text1 = new Text("Breakout Game \n \n");
        text1.setFont(Font.font ("Verdana", 40));
        text1.setTextAlignment(TextAlignment.CENTER);

        Text text2 = new Text("By: Louis Jensen \n \n");
        text2.setFont(Font.font ("Verdana", 14));
        Text text3 = new Text("Press B to begin");
        text3.setFont(Font.font ("Verdana", 10));

        root.getChildren().addAll(text1, text2, text3);


        Scene menuScene = new Scene(root,SIZE, SIZE, BACKGROUND);
        menuScene.setOnKeyPressed(e -> menuHandleKeyInput(e.getCode()));
        myScene = menuScene;
        GAME_STAGE.setScene(menuScene);
    }

    private void endLevel(){
        if (blocksDestroyed == numBlocks){
            levelStatus = "You completed ";
            nextLEVEL = LEVEL + 1;
        }
        StackPane pane = new StackPane();
        Text text1 = new Text(levelStatus + stringLevel + LEVEL+" \n \n");
        text1.setFont(Font.font ("Verdana", 20));
        text1.setTextAlignment(TextAlignment.CENTER);
        Text text2 = new Text("\n\nPress R to Replay Level \n" +
                                "Press C to continue to next level \n" +
                                "Press E to exit game");
        if (LEVEL == 3 && levelStatus.equals("You completed ")){
            text1 = new Text("Congratulations you have completed the game! \n \n");
            text2 = new Text("Press E to exit");
        }
        text1.setFont(Font.font ("Verdana", 20));
        pane.getChildren().addAll(text1, text2);
        Scene betweenLevelScene = new Scene(pane,SIZE, SIZE, BACKGROUND);
        betweenLevelScene.setOnKeyPressed(e -> menuHandleKeyInput(e.getCode()));
        myScene = betweenLevelScene;
        GAME_STAGE.setScene(betweenLevelScene);
    }

    private void menuHandleKeyInput (KeyCode code){
        if (code == KeyCode.R) {
            makeLevel(LEVEL);
        }
        if (code == KeyCode.B) {
            makeLevel(1);
        }
        if (code == KeyCode.C) {
            makeLevel(nextLEVEL);
        }
        if (code == KeyCode.E){
            Platform.exit();
        }
    }

    private void levelHandleKeyInput (KeyCode code) {
        if (code == KeyCode.RIGHT && paddle.getX() < myScene.getWidth()) {
            paddle.setX(paddle.getX() + paddle.getSpeed());
        }
        else if (code == KeyCode.LEFT) {
            paddle.setX(paddle.getX() - paddle.getSpeed());
        }
        if (code == KeyCode.S){
            levelStatus = "You completed ";
            if (LEVEL < 3) nextLEVEL = LEVEL + 1;
            endLevel();
        }
        if (code == KeyCode.L){
            livesRemaining++;
        }
    }

    private boolean ballCollidesWithBlock(Ball ball, Block block){
            var ballBlockIntersect = Shape.intersect(ball.getBall(), block.getBlock());
            if (ballBlockIntersect.getBoundsInLocal().getWidth() != -1){
                return true;
            }
            return false;
    }

    private void updateBlock(Block block){
        if (block.numHits == 1) {
            block.setX(-SIZE);
            block.setY(-SIZE);
            blocksDestroyed++;
        } else {
            block.numHits --;
            block.updateColor();
        }
    }


    private void step (double elapsedTime) {
        // update attributes
        if (ballXSpeed < 30 && ballXSpeed > -30) ballXSpeed*=2;
        ball.setX(ball.getX() + ballXSpeed * elapsedTime);
        ball.setY(ball.getY() + ballYSpeed * elapsedTime);

        if (ball.getX() > myScene.getWidth() - ball.getBallRadius() || ball.getX() < ball.getBallRadius()) {
            ballXSpeed*=-1;
        }
        if (ball.getY() < ball.getBallRadius()) {
            ballYSpeed*=-1;
        }
        var intersect = Shape.intersect(ball.getBall(), paddle.getPaddle());
        if (intersect.getBoundsInLocal().getWidth() != -1) {
            ballYSpeed*=-1;
        }
        for (Block b : blockList) {
            if (ballCollidesWithBlock(ball, b)) {
                ballYSpeed *= -1;
                updateBlock(b);
            }
        }

        if (livesRemaining == 0 || blocksDestroyed == numBlocks){
            endLevel();
        }

        if (ball.getY() > myScene.getHeight()){
            ballYSpeed*=-1;
            ballXSpeed = (int)(Math.random() * 240 + 1) - 120;
            ball.setX(myScene.getWidth() / 2 - ball.getBallRadius() / 2);
            ball.setY(5*myScene.getWidth() / 6 - ball.getBallRadius()*2);
            paddle.setX(myScene.getWidth() / 2 - paddle.getPaddleWidth() / 2);
            livesRemaining--;
        }
    }
}
