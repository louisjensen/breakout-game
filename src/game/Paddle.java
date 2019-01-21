package game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
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

    /**
     * Constructs paddle object and puts it in bottom center of scene
     */
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

    }

    /**
     * Returns the width of the paddle
     */
    public int getPaddleWidth(){
        return paddleWidth;
    }

    /**
     * Returns paddle as a Rectangle object
     */
    public Rectangle getPaddle(){
        return paddle;
    }

    /**
     * Returns paddle speed
     */
    public int getSpeed(){
        return paddleSpeed;
    }

    /**
     * Returns x coordinate of paddle
     */
    public double getX(){
        return paddle.getX();
    }

    /**
     * Returns y coordinate of paddle
     */
    public double getY(){
        return paddle.getY();
    }

    /**
     * Sets x coordinate of paddle
     */
    public void setX(double x){
        paddle.setX(x);
    }

    /**
     * Sets y coordinate of paddle
     */
    public void setY(double y){
        paddle.setY(y);
    }
}