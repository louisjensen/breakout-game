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

public class Block {

    private int blockWidth = 30;
    private int blockHeight = 10;
    private Paint oneHitColor = Color.PURPLE;
    private Paint twoHitColor = Color.BLUE;
    private Paint threeHitColor = Color.ORANGE;

    public int numHits;

    private Rectangle block;

    public Block(int xCor, int yCor, int hits){
        block = new Rectangle(xCor, yCor, blockWidth, blockHeight);
        numHits = hits;
        if(hits == 1) {
            block.setFill(oneHitColor);
            numHits = 1;
        } else if (hits == 2){
            block.setFill(twoHitColor);
            numHits = 2;
        } else if (hits == 2){
            block.setFill(threeHitColor);
            numHits = 3;
        }
    }

    public Rectangle getBlock(){
        return block;
    }

    public void setX(double d){
        block.setX(d);
    }

    public void setY(double d){
        block.setY(d);
    }
}
