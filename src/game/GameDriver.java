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
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;

public class GameDriver {
    private static final int SIZE = 500;
    private static final int FRAMES_PER_SECOND = 60;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private static final String TITLE = "Breakout Game";
    private Stage GAME_STAGE;
    private static final Paint BACKGROUND = Color.AZURE;
    public static final Paint MOVER_COLOR = Color.PLUM;
    public static final int MOVER_SIZE = 50;
    public static final int MOVER_SPEED = 5;
    private Scene myScene;
    private Paddle paddle;
    private Ball ball;
    private int ballXSpeed = (int)(Math.random() * 160 + 1) - 80;
    private int ballYSpeed = -80;

    public void startGame(Stage stage){
        GAME_STAGE = stage;
        GAME_STAGE.setTitle(TITLE);
        GAME_STAGE.setTitle(TITLE);
        GAME_STAGE.show();
        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        var animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    public void playGame(){
        var root = new Group();
        Scene beginScene = new Scene(root, SIZE, SIZE, BACKGROUND);
        myScene = beginScene;
        beginScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        paddle = new Paddle(beginScene);
        ball = new Ball(beginScene);
        root.getChildren().add(paddle.getPaddle());
        root.getChildren().add(ball.getBall());
        GAME_STAGE.setScene(beginScene);
    }

    private void handleKeyInput (KeyCode code) {
        if (code == KeyCode.RIGHT && paddle.getX() < myScene.getWidth()) {
            paddle.setX(paddle.getX() + paddle.getSpeed());
        }
        else if (code == KeyCode.LEFT) {
            paddle.setX(paddle.getX() - paddle.getSpeed());
        }
    }

    private void step (double elapsedTime) {
        // update attributes
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
    }
}
