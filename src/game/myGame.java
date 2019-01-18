package game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;

public class myGame extends Application {
    public static final String TITLE = "Breakout Game";
    public static final int SIZE = 400;
    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final Paint BACKGROUND = Color.AZURE;
    public static final Paint HIGHLIGHT = Color.OLIVEDRAB;
    public static final String BALL_IMAGE = "ball.gif";
    public static final int BALL_SPEED = 60;
    public static final String PADDLE_IMAGE = "paddle.gif";
    public static final int PADDLE_SPEED = 5;

    private Scene myScene;
    private ImageView myBall;
    private ImageView myPaddle;

    /**
     * Initialize what will be displayed and how it will be updated.
     */
    @Override
    public void start (Stage stage) {
        // attach scene to the stage and display it
        myScene = setupGame(SIZE, SIZE, BACKGROUND);
        stage.setScene(myScene);
        stage.setTitle(TITLE);
        stage.show();
        // attach "game loop" to timeline to play it
        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        var animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    // Create the game's "scene": what shapes will be in the game and their starting properties
    private Scene setupGame (int width, int height, Paint background) {
        // create one top level collection to organize the things in the scene
        var root = new Group();
        // create a place to see the shapes
        var scene = new Scene(root, width, height, background);
        // make some shapes and set their properties
        var image = new Image(this.getClass().getClassLoader().getResourceAsStream(BALL_IMAGE));
        myBall = new ImageView(image);
        var image2 = new Image(this.getClass().getClassLoader().getResourceAsStream(PADDLE_IMAGE));
        myPaddle = new ImageView(image2);
        // x and y represent the top left corner, so center it
        myBall.setX(width / 2 - myBall.getBoundsInLocal().getWidth() / 2);
        myBall.setY(height / 2 - myBall.getBoundsInLocal().getHeight() / 2);
        myPaddle.setX(width / 2 - myPaddle.getBoundsInLocal().getWidth() / 2);
        myPaddle.setY(height - 50);
        // order added to the group is the order in which they are drawn
        root.getChildren().add(myBall);
        root.getChildren().add(myPaddle);
        // respond to input
        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
      //  scene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
        return scene;
    }

    public boolean ballCollideWithPaddle(ImageView ball, ImageView paddle){
        return myBall.getY() == myPaddle.getY() &&
                (myBall.getX() > myPaddle.getX() - 30 && myBall.getX() < myPaddle.getX() + 30);
    }

    public int xSPEED = BALL_SPEED;
    public int ySPEED = BALL_SPEED;
    private void step (double elapsedTime) {
        // update attributes

        myBall.setX(myBall.getX() + xSPEED * elapsedTime);
        myBall.setY(myBall.getY() + ySPEED * elapsedTime);
       //check for wall collisions
        if (myBall.getX() > myScene.getWidth() - 5 || myBall.getX() < - 5) {
            xSPEED*=-1;
        }
        if (myBall.getY() > myScene.getHeight() - 5 ||
                myBall.getY() < - 5) {
            ySPEED*=-1;
        }
        //check collision with paddle
        if(ballCollideWithPaddle(myBall, myPaddle)){
            xSPEED*=-1;
        }
        // with shapes, can check precisely
   /*     var intersect = Shape.intersect(myMover, myGrower);
        if (intersect.getBoundsInLocal().getWidth() != -1) {
            myMover.setFill(HIGHLIGHT);
        }
        else {
            myMover.setFill(MOVER_COLOR);
        }
        // with images can only check bounding box
        if (myGrower.getBoundsInParent().intersects(myBouncer.getBoundsInParent())) {
            myGrower.setFill(HIGHLIGHT);
        }
        else {
            myGrower.setFill(GROWER_COLOR);
        } */
    }

    // What to do each time a key is pressed
    private void handleKeyInput (KeyCode code) {
        if (code == KeyCode.RIGHT) {
            myPaddle.setX(myPaddle.getX() + PADDLE_SPEED);
        }
        else if (code == KeyCode.LEFT) {
            myPaddle.setX(myPaddle.getX() - PADDLE_SPEED);
        }

    }

    // What to do each time a key is pressed
 /*   private void handleMouseInput (double x, double y) {
        if (myGrower.contains(x, y)) {
            myGrower.setScaleX(myGrower.getScaleX() * GROWER_RATE);
            myGrower.setScaleY(myGrower.getScaleY() * GROWER_RATE);
        }
    } */

    /**
     * Start the program.
     */
 //   public static void main (String[] args) {
  //      launch(args);
   // }
}



