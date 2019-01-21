package game;

import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;

public class Headings {
    private double height;

    public Rectangle headerFooter(Scene currScene, String topOrBottom){
        if (topOrBottom.equals("top")) height = 0;
        if (topOrBottom.equals("bottom")) height = currScene.getHeight()-currScene.getHeight()/13;
        return new Rectangle(0, height, currScene.getWidth(), currScene.getHeight()/13);
    }

}
