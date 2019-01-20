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

public class Ball {
    private Circle ball;
    private int ballRadius = 5;
    private int ballSpeed = 60;
    private Paint ballColor = Color.GREEN;
    private static final int FRAMES_PER_SECOND = 60;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private int ballXSpeed = 50;
    private int ballYSpeed = 50;
    private static final int SIZE = 500;


    public Ball(){
        ball = new Circle(SIZE / 2 - ballRadius / 2,
                5*SIZE / 6 - ballRadius*2,
                ballRadius,
                ballColor);
        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> ballStep(SECOND_DELAY));
        var animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
    }

    public Ball(Scene currScene){
        ball = new Circle(currScene.getWidth() / 2 - ballRadius / 2,
                            5*currScene.getHeight() / 6 - ballRadius*2,
                            ballRadius,
                            ballColor);
        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> ballStep(SECOND_DELAY));
        var animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
    }

    private void ballStep(double elapsedTime){
     /*   ball.setCenterX(ball.getCenterX() + ballXSpeed * elapsedTime);
        ball.setCenterY(ball.getCenterY() + ballYSpeed * elapsedTime); */
    }

    public Circle getBall() {
        return ball;
    }

    public double getX(){
        return ball.getCenterX();
    }

    public double getY(){
        return ball.getCenterY();
    }

    public void setX(double speed){
        ball.setCenterX(speed);
    }

    public void setY(double speed){
        ball.setCenterY(speed);
    }

    public int getBallRadius() {
        return ballRadius;
    }
}
