package game;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Block {

    private int blockSpeed = 60;
    private int blockWidth = 30;
    private int blockHeight = 10;
    private Paint oneHitColor = Color.PURPLE;
    private Paint twoHitColor = Color.BLUE;
    private Paint threeHitColor = Color.ORANGE;
    private Rectangle block;
    public int numHits;

    /**
     * Constructs block object and positions it on scene
     */
    public Block(int xCor, int yCor, int hits){
        block = new Rectangle(xCor, yCor, blockWidth, blockHeight);
        numHits = hits;
        if(hits == 1) {
            block.setFill(oneHitColor);
            numHits = 1;
        } else if (hits == 2){
            block.setFill(twoHitColor);
            numHits = 2;
        } else if (hits == 3){
            block.setFill(threeHitColor);
            numHits = 3;
        }
    }

    /**
     *Changes the color of the block when it is hit
     */
    public void updateColor(){
        if(numHits == 1) {
            block.setFill(oneHitColor);
        } else if (numHits == 2){
            block.setFill(twoHitColor);
            numHits = 2;
        } else if (numHits == 3){
            block.setFill(threeHitColor);
        }
    }

    /**
     * Returns block as rectangle
     */
    public Rectangle getBlock(){
        return block;
    }

    /**
     *Sets the x coordinate of the block
     */
    public void setX(double d){
        block.setX(d);
    }

    /**
     * Sets the y coordinate of the block
     */
    public void setY(double d){
        block.setY(d);
    }

    /**
     * Returns x coordinate of paddle
     */
    public double getX(){
        return block.getX();
    }

    /**
     * Returns y coordinate of paddle
     */
    public double getY(){
        return block.getY();
    }

    /**
     * Returns block width
     */
    public int getWidth(){
        return blockWidth;
    }

    /**
     * Returns block width
     */
    public int getBlockSpeed(){
        return blockSpeed;
    }

    /**
     * Reverses direction of moving block
     */
    public void reverseBlockSpeed(){
        blockSpeed*=-1;
    }
}