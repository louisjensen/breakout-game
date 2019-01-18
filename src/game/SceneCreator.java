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
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class SceneCreator {

    private int SCENE_NUMBER;
    private static final int SIZE = 500;
    /* int SceneNumber
     1, 2, and 3 refer to those levels.
     55 refers to main menu
     66 refers to rule screen
     77 refers to level win screen
     88 refers to level lose screen
     */
    public SceneCreator(int SceneNumber){
        SCENE_NUMBER = SceneNumber;

    }

    Text title = new Text ("Breakout Game");
    Text author = new Text("By: Louis Jensen");


    public Scene makeStartMenu(){
        var root = new Group();
        root.getChildren().add()
        Scene beginScene = new Scene(root, SIZE, SIZE, BACKGROUND);
    }
}
