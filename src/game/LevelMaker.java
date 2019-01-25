package game;
import java.util.ArrayList;
import javafx.scene.Group;

public class LevelMaker {

    private int numBlocks;
    private ArrayList<Block>  blockList = new ArrayList<Block>();
    private Group root = new Group();

    public LevelMaker() { }

    /**
     * Creates the layout of blocks to display on screen
     * @param lvl indicates which layout to create
     */
    public void makeBlockLayout(int lvl){
        if(lvl ==1) {
            //Create level one layout in grid
            makeRowsOfBlocks();
        }
        if (lvl == 2){
            //Create level two layout in diamond
            makeDiamondOfBlocks(95, 50);
        }
        if (lvl == 3){
            //Create level three layout in groups of six
            for (int i = 90; i < 340; i += 60){
                makeSixBlocks(i, (int)(Math.random() * 400 + 30));
            }
        }
    }

    private void makeRowsOfBlocks(){
        int count = 1;
        for (int i = 10; i < 500; i += 50) {
            int evenOdd = count % 2;
            Block block = new Block(i, 200, 1);
            numBlocks++;
            Block blockUpper = new Block(i, 100, evenOdd + 2);
            root.getChildren().addAll((block.getBlock()), (blockUpper.getBlock()));
            blockList.add(block);
            blockList.add(blockUpper);
            numBlocks++;
            count++;
        }
    }

    /**
     * Creates blocks and makes the shape of a diamond at an initial position
     */
    private void makeDiamondOfBlocks(int xPos, int yPos){
        int distanceBetween = 45;
        for (int i = 0; i<distanceBetween*7; i+=distanceBetween){
            for (int j = 0; j<distanceBetween*7; j+=distanceBetween){
                int distanceFromCenter = Math.abs(i-distanceBetween*3) + Math.abs(j-distanceBetween*3);
                if(distanceFromCenter > distanceBetween*3) continue;
                int blockHits = 4 - distanceFromCenter/distanceBetween;
                if (blockHits ==4) blockHits=1;
                Block diamondBlock = new Block(xPos+i, yPos+j, blockHits);
                root.getChildren().add(diamondBlock.getBlock());
                blockList.add(diamondBlock);
                numBlocks++;
            }
        }
    }

    /**
     * Creates a group of six blocks to display on the screen
     */
    private void makeSixBlocks(int yPos, int xPos){
        int blockHits = 1;
        for (int i = 0; i < 60; i+=20){
            Block blockLeft = new Block(xPos, yPos-i, blockHits);
            Block blockRight = new Block(xPos + 40, yPos-i, blockHits);
            root.getChildren().addAll((blockLeft.getBlock()), (blockRight.getBlock()));
            blockList.add(blockLeft);
            blockList.add(blockRight);
            numBlocks+=2;
            blockHits++;
        }
    }

    public ArrayList<Block> getListOfBlocks(){
        return blockList;
    }

    public int getNumBlocks(){
        return numBlocks;
    }

    public Group getRoot(){
        return root;
    }

    public void setNumBlocks(int set){
        numBlocks = set;
    }
}
