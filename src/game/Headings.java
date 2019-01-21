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
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class Headings {
    private double height;

    public Rectangle headerFooter(Scene currScene, String topOrBottom){
        if (topOrBottom.equals("top")) height = 0;
        if (topOrBottom.equals("bottom")) height = currScene.getHeight()-currScene.getHeight()/13;
        return new Rectangle(0, height, currScene.getWidth(), currScene.getHeight()/13);
    }

}
