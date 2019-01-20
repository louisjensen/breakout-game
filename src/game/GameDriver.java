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
    public int nextLEVEL;
    private String levelStatus;
    private int livesRemaining;
    private Group root = new Group();
    private ArrayList<Block>  blockList = new ArrayList<Block>();
    private int blocksDestroyed = 0;
    private int numBlocks;
    private Scene levelScene = new Scene(root, SIZE, SIZE, BACKGROUND);




    public void startGame(Stage stage){
        GAME_STAGE = stage;
        GAME_STAGE.setTitle(TITLE);
        GAME_STAGE.show();

    }

    public void playGame(){
        makeMenu();
    }

    private void makeLevel(int level){
        levelStatus = "You failed ";
        livesRemaining = 3;
        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        var animation = new Timeline();
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
        if(intLevel ==1) {
            for (int i = 10; i < 500; i += 50) {
                Block block = new Block(i, 200, 1);
                root.getChildren().add((block.getBlock()));
                blockList.add(block);
                numBlocks++;
            }
        } else if (intLevel == 2){
            for(int i = 0; i < 2; i++){
                for (int k = 10; k < 500; k += 50) {
                    Block block = new Block(i, 200, 1);
                    root.getChildren().add((block.getBlock()));
                    blockList.add(block);
                    numBlocks++;
                }
                for (int j = 10; j < 500; j += 50) {
                    Block block = new Block(i, 100, 2);
                    root.getChildren().add((block.getBlock()));
                    blockList.add(block);
                    numBlocks++;
                }
            }
        }
        root.getChildren().add(paddle.getPaddle());
        root.getChildren().add(ball.getBall());
        levelScene.setOnKeyPressed(e -> levelHandleKeyInput(e.getCode()));

        myScene = levelScene;
        GAME_STAGE.setScene(levelScene);
    }

    private void makeMenu(){
        StackPane root = new StackPane();
        Text text1 = new Text("Breakout Game \n \n");
        text1.setFont(Font.font ("Verdana", 40));
        text1.setTextAlignment(TextAlignment.CENTER);

        Text text2 = new Text("By: Louis Jensen \n \n");
        text2.setFont(Font.font ("Verdana", 14));
        Text text3 = new Text("Press SPACE to play");
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
            intLevel = 1;
            nextLEVEL=2;
        }
        StackPane pane = new StackPane();
        Text text1 = new Text(levelStatus + stringLevel + intLevel+" \n \n");
        text1.setFont(Font.font ("Verdana", 20));
        text1.setTextAlignment(TextAlignment.CENTER);
        Text text2 = new Text("\n\nPress R to Replay Level \n" +
                                "Press SPACE to continue to next level \n" +
                                "Press E to exit game");
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
        if (code == KeyCode.SPACE) {
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
    }

    private boolean ballCollidesWithBlock(Ball ball, Block block){
            var ballBlockIntersect = Shape.intersect(ball.getBall(), block.getBlock());
            if (ballBlockIntersect.getBoundsInLocal().getWidth() != -1){
                return true;
            }
            return false;
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
                b.setX(-SIZE);
                b.setY(-SIZE);
                blocksDestroyed++;
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
