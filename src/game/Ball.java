package game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Ball {
    private Circle ball;
    private int ballRadius = 5;
    private Paint ballColor = Color.GREEN;
    private Paint bonusBallColor = Color.LIGHTGREEN;
    private static final int FRAMES_PER_SECOND = 60;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private boolean isBonusBall;

    /**
     * Constructs a ball object and places it on paddle
     */
    public Ball(Scene currScene, boolean isBonus){
        Paint color;
        isBonusBall = isBonus;
        if (isBonus) {
            color = bonusBallColor;
        } else {
            color = ballColor;
        }
        ball = new Circle(currScene.getWidth() / 2 - ballRadius / 2,
                            5*currScene.getHeight() / 6 - ballRadius*2,
                            ballRadius,
                            color);
        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> ballStep(SECOND_DELAY));
        var animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
    }

    private void ballStep(double elapsedTime){
    }

    /**
     * Returns Ball as Circle object
     */
    public Circle getBall() {
        return ball;
    }

    /**
     * Returns the x coordinate of the ball
     */
    public double getX(){
        return ball.getCenterX();
    }

    /**
     * Returns the y coordinate of the ball
     */
    public double getY(){
        return ball.getCenterY();
    }

    /**
     * Sets the x coordinate of the ball
     */
    public void setX(double speed){
        ball.setCenterX(speed);
    }

    /**
     * Sets the y coordinate of the ball
     */
    public void setY(double speed){
        ball.setCenterY(speed);
    }

    /**
     * Returns the radius of the ball
     */
    public int getBallRadius() {
        return ballRadius;
    }
}
