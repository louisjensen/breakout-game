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
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;



public class Paddle {
    private Rectangle paddle;
    private int paddleWidth = 80;
    private int paddleHeight = 10;
    private int paddleSpeed = 10;
    private Paint paddleColor = Color.CRIMSON;
    private static final int FRAMES_PER_SECOND = 60;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private static final int SIZE = 500;


    public Paddle(Scene currScene){
        paddle = new Rectangle(currScene.getWidth() / 2 - paddleWidth / 2,
                               5*currScene.getHeight() / 6 - paddleHeight / 2,
                                paddleWidth, paddleHeight);
        paddle.setFill(paddleColor);
        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> paddleStep(SECOND_DELAY));
        var animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
    }

    private void paddleStep(double elapsedTime){
      //  paddle.setX(paddle.getX());
     //   paddle.setY(paddle.getY());

    }

    public int getPaddleWidth(){
        return paddleWidth;
    }

    public Rectangle getPaddle(){
        return paddle;
    }

    public int getSpeed(){
        return paddleSpeed;
    }

    public double getX(){
        return paddle.getX();
    }

    public double getY(){
        return paddle.getY();
    }

    public void setX(double speed){
        paddle.setX(speed);
    }

    public void setY(double speed){
        paddle.setY(speed);
    }
}
