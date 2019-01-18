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
    private static final int SIZE = 400;
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

    public void startGame(Stage stage){
        GAME_STAGE = stage;
        GAME_STAGE.setTitle(TITLE);
        GAME_STAGE.setTitle(TITLE);
        GAME_STAGE.show();
    }

    public void playGame(){
        var root = new Group();
        Scene beginScene = new Scene(root, SIZE, SIZE, BACKGROUND);
        paddle = new Paddle(beginScene);
        root.getChildren().add(paddle.getPaddle());
        GAME_STAGE.setScene(beginScene);
    }

    private Scene setupScene(int height, int width, Paint background){
        // create one top level collection to organize the things in the scene
        var root = new Group();
        // create a place to see the shapes
        var scene = new Scene(root, width, height, background);
        return scene;
    }

    private void handleKeyInput (KeyCode code) {
        if (code == KeyCode.RIGHT) {
            paddle.setX(paddle.getX() + paddle.getSpeed());
        }
        else if (code == KeyCode.LEFT) {
            paddle.setX(paddle.getX() - paddle.getSpeed());
        }
    }
}
