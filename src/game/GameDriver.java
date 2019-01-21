package game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import java.util.ArrayList;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class GameDriver {
    private static final int SIZE = 500;
    private static final int FRAMES_PER_SECOND = 60;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private static final String TITLE = "Breakout Game by Louis Jensen";
    private Stage GAME_STAGE;
    private static final Paint BACKGROUND = Color.AZURE;
    private Scene myScene;
    private Paddle paddle;
    private Ball ball;
    private Ball bonusBall;
    private int ballXSpeed = (int)(Math.random() * 300 + 1) - 150;
    private int bonusBallXSpeed = (int)(Math.random() * 300 + 1) - 150;
    private int ballYSpeed = -100;
    private int blockSpeed = 70;
    private int LEVEL = 1;
    private String stringLevel = "Level ";
    private int nextLEVEL;
    private String levelStatus;
    private int livesRemaining;
    private Group root = new Group();
    private ArrayList<Block>  blockList = new ArrayList<Block>();
    private int blocksDestroyed = 0;
    private int numBlocks;
    private Scene levelScene = new Scene(root, SIZE, SIZE, BACKGROUND);
    private Timeline animation;
    private Headings headings = new Headings();
    private int totalScore;
    private Text showTitle;
    private Text showScore;
    private Text showLevelNumber;
    private Text showLives;
    private int paddleWidth = 80;
    private int defualtPaddleWidth = 80;


    /**
     * Starts the game loop
     */
    public void startGame(Stage stage){
        GAME_STAGE = stage;
        GAME_STAGE.setTitle(TITLE);
        GAME_STAGE.show();
    }

    /**
     * Creates the initial game menu and handles next key input
     */
    public void playGame(){
        makeMenu();
    }

    /**
     * Creates and level and begins it on the screen
     * @param level indicates which level the method will create
     */
    private void makeLevel(int level){
        LEVEL = level;
        levelStatus = "You failed ";
        livesRemaining = 3;
        if (animation != null) animation.stop();
        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();

        root.getChildren().clear();
        blockList.clear();
        blocksDestroyed=0;
        numBlocks=0;
        paddle = new Paddle(levelScene);
        ball = new Ball(levelScene, false);
        if (level == 2) {
            bonusBall = new Ball(levelScene, true);
            root.getChildren().add(bonusBall.getBall());
        }
        makeBlockLayout(level);
        root.getChildren().add(paddle.getPaddle());
        root.getChildren().add(ball.getBall());
        root.getChildren().addAll(headings.headerFooter(myScene, "top"), headings.headerFooter(myScene, "bottom"));
        showLevelNumber = textMaker("Level: " + Integer.toString(level), 410, 25, "Verdana", 16, Color.WHITE);
        showTitle = textMaker("Breakout Game by Louis Jensen", 20, 25, "Verdana", 16, Color.WHITE);
        showLives = textMaker("Lives Remaining: " + Integer.toString(livesRemaining), 20, 485, "Verdana", 16, Color.WHITE);
        showScore = textMaker("Score: " + Integer.toString(totalScore), 410, 485, "Verdana", 16, Color.WHITE);
        root.getChildren().addAll(showLevelNumber, showTitle, showLives, showScore);
        levelScene.setOnKeyPressed(e -> levelHandleKeyInput(e.getCode()));
        myScene = levelScene;
        GAME_STAGE.setScene(levelScene);
    }

    /**
     * Creates Text that can be added to scene
     */
    private Text textMaker(String string, int xCor, int yCor, String font, int fontSize, Paint color){
        Text text = new Text(string);
        text.setFont(Font.font (font, fontSize));
        text.setX(xCor);
        text.setY(yCor);
        text.setFill(color);
        return text;
    }

    /**
     * Creates the layout of blocks to display on screen
     * @param lvl indicates which layout to create
     */
    private void makeBlockLayout(int lvl){
        if(lvl ==1) {
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
        if (lvl == 2){
            makeDiamondOfBlocks(95, 50);
        }
        if (lvl == 3){
            for (int i = 90; i < 340; i += 60){
                makeSixBlocks(i, (int)(Math.random() * 400 + 30));
            }
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

    /**
     * Creates main menu displayed on start up
     */
    private void makeMenu(){
        LEVEL++;
        StackPane root = new StackPane();
        Text text1 = new Text("Breakout Game \n \n");
        text1.setFont(Font.font ("Verdana", 40));
        text1.setTextAlignment(TextAlignment.CENTER);

        Text text2 = new Text("By: Louis Jensen \n \n");
        text2.setFont(Font.font ("Verdana", 14));
        Text text3 = new Text("Press B to begin");
        text3.setFont(Font.font ("Verdana", 10));

        root.getChildren().addAll(text1, text2, text3);

        makeAndDisplayScene(root, SIZE, SIZE, BACKGROUND);
    }

    /**
     * Creates a scene given a root, size, and color
     */
    private void makeAndDisplayScene(StackPane root, int width, int length, Paint color){
        Scene scene = new Scene(root, width, length, color);
        scene.setOnKeyPressed(e -> menuHandleKeyInput(e.getCode()));
        myScene = scene;
        GAME_STAGE.setScene(scene);
    }

    /**
     * Ends the level and displays between level screen
     */
    private void endLevel(){
        if (blocksDestroyed == numBlocks){
            levelStatus = "You completed ";
            nextLEVEL = LEVEL + 1;
        }
        StackPane pane = new StackPane();
        Text text1 = new Text(levelStatus + stringLevel + LEVEL+" \n \n");
        text1.setFont(Font.font ("Verdana", 20));
        text1.setTextAlignment(TextAlignment.CENTER);
        Text text2 = new Text("\n \n\nPress R to Replay Level \n" +
                                "Press C to continue to next level \n" +
                                "Press E to exit game \n" +
                                "Score: " + Integer.toString(totalScore));
        if (LEVEL == 3 && levelStatus.equals("You completed ")){
            text1 = new Text("Congratulations you have completed the game! \n \n");
            text2 = new Text("Press E to exit");
        }
        text1.setFont(Font.font ("Verdana", 20));
        pane.getChildren().addAll(text1, text2);
        makeAndDisplayScene(pane, SIZE,SIZE, BACKGROUND);
    }

    /**
     * Monitors and handles key input while a menu is on screen
     */
    private void menuHandleKeyInput (KeyCode code){
        if (code == KeyCode.R) {
            makeLevel(LEVEL);
        }
        if (code == KeyCode.B) {
            totalScore = 0;
            makeLevel(1);
        }
        if (code == KeyCode.C) {
            makeLevel(nextLEVEL);
        }
        if (code == KeyCode.E){
            Platform.exit();
        }
    }

    /**
     * Monitors and handles key input while playing level
     */
    private void levelHandleKeyInput (KeyCode code) {
        if (code == KeyCode.RIGHT && paddle.getX() < myScene.getWidth()) {
            paddle.setX(paddle.getX() + paddle.getSpeed());
        }
        else if (code == KeyCode.LEFT) {
            paddle.setX(paddle.getX() - paddle.getSpeed());
        }
        if (code == KeyCode.S){
            levelStatus = "You completed ";
            if (LEVEL < 3) nextLEVEL = LEVEL + 1;
            endLevel();
        }
        if (code == KeyCode.L){
            livesRemaining++;
        }
    }

    private boolean ballCollidesWithBlock(Ball ball, Block block){
            var ballBlockIntersect = Shape.intersect(ball.getBall(), block.getBlock());
            if (ballBlockIntersect.getBoundsInLocal().getWidth() != -1){
                return true;
            }
            return false;
    }

    /**
     * Update blocks when hit and remove eliminated blocks from screen
     */
    private void updateBlock(Block block){
        if (block.numHits == 1) {
            block.setX(-SIZE);
            block.setY(-SIZE);
            blocksDestroyed++;
            totalScore++;
            showScore.setText("Score: " + totalScore);
            if ((int)(Math.random() * 3 + 1) == 1) {
               // addPowerUp((int)(Math.random() * 3 + 1));
                addPowerUp(2);
            }
        } else {
            block.numHits --;
            block.updateColor();
            totalScore++;
            showScore.setText("Score: " + totalScore);
        }
    }

    private void addPowerUp(int powerUp){
        if (powerUp == 1){
            ball.setColor(Color.GOLD);
            ballXSpeed /= 2;
            ballYSpeed /= 2;
        } else if (powerUp == 2){
            paddle.getPaddle().setWidth(paddleWidth*1.5);
            paddle.getPaddle().setFill(Color.GOLD);
        } else {

        }
    }

    /**
     * Updates attributes of shapes to create animation
     */
    private void step (double elapsedTime) {
        if (ballXSpeed < 30 && ballXSpeed > -30) ballXSpeed*=2;
        if (ballXSpeed == 0) ballXSpeed+=30;
        ball.setX(ball.getX() + ballXSpeed * elapsedTime);
        ball.setY(ball.getY() + ballYSpeed * elapsedTime);

        if (ball.getX() > myScene.getWidth() - ball.getBallRadius() || ball.getX() < ball.getBallRadius()) {
            ballXSpeed*=-1;
        }
        if (ball.getY() < ball.getBallRadius() + myScene.getHeight()/13) {
            ballYSpeed*=-1;
        }
        var intersect = Shape.intersect(ball.getBall(), paddle.getPaddle());
        if (intersect.getBoundsInLocal().getWidth() != -1) {
            ballYSpeed*=-1;
        }
        for (Block b : blockList) {
            if (ballCollidesWithBlock(ball, b)) {
                ballYSpeed *= -1;
                updateBlock(b);

            }
        }

        if (livesRemaining == 0 || blocksDestroyed == numBlocks){
            endLevel();
        }

        if (ball.getY() > myScene.getHeight()){
            resetBallAndPaddle(ball);
            livesRemaining--;
            showLives.setText("Lives Remaining: " +Integer.toString(livesRemaining));
        }

        if (LEVEL == 3) moveBlocks(elapsedTime);
    }

    private void resetBallAndPaddle(Ball ball){
        ballYSpeed=-100;
        ballXSpeed = (int)(Math.random() * 240 + 1) - 120;
        ball.setX(myScene.getWidth() / 2 - ball.getBallRadius() / 2);
        ball.setY(5*myScene.getWidth() / 6 - ball.getBallRadius()*2);
        paddle.setX(myScene.getWidth() / 2 - paddle.getPaddleWidth() / 2);
        ball.setColor(Color.GREEN);
        paddle.getPaddle().setFill(Color.CRIMSON);
        paddle.getPaddle().setWidth(defualtPaddleWidth);
    }

    private void moveBlocks(double elapsedTime){
        for (Block b : blockList){
            b.setX(b.getX()+ b.getBlockSpeed() * elapsedTime);
            if (b.getX() < 0 || b.getX() > myScene.getWidth()-b.getWidth()){
                b.reverseBlockSpeed();
            }
        }
    }

  /*  public void moveAndBounceBall(Ball toBeMoved, int xspeed, double elapsedTime){
        toBeMoved.setX(toBeMoved.getX() + xspeed * elapsedTime);
        toBeMoved.setY(toBeMoved.getY() + ballYSpeed * elapsedTime);
        if (toBeMoved.getX() > myScene.getWidth() - toBeMoved.getBallRadius() || toBeMoved.getX() < toBeMoved.getBallRadius()) {
            ballXSpeed*=-1;
        }
        if (toBeMoved.getY() < toBeMoved.getBallRadius() + myScene.getHeight()/13) {
            ballYSpeed*=-1;
        }
        var intersect = Shape.intersect(toBeMoved.getBall(), paddle.getPaddle());
        if (intersect.getBoundsInLocal().getWidth() != -1) {
            ballYSpeed*=-1;
        }
        for (Block b : blockList) {
            if (ballCollidesWithBlock(toBeMoved, b)) {
                ballYSpeed *= -1;
                updateBlock(b);

            }
        }
    } */
}