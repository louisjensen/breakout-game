package game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Scene;
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
    private int ballXSpeed = (int)(Math.random() * 300 + 1) - 150;
    private int ballYSpeed = -150;
    private int LEVEL = 1;
    private String stringLevel = "Level ";
    private int nextLEVEL;
    private String levelStatus;
    private int livesRemaining;
    private int blocksDestroyed = 0;
    private LevelMaker levelMaker = new LevelMaker();
    private Scene levelScene = new Scene(levelMaker.getRoot(), SIZE, SIZE, BACKGROUND);
    private Timeline animation;
    private Headings headings = new Headings();
    private int totalScore;
    private Text showTitle;
    private Text showScore;
    private Text showLevelNumber;
    private Text showLives;
    private int paddleWidth = 80;
    private int defualtPaddleWidth = 80;
    private boolean catchAndRelease = false;

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

        //Set to level defaults
        levelStatus = "You failed ";
        livesRemaining = 3;

        //stop any current timeline
        if (animation != null) animation.stop();

        //Create new timeline
        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();

        //Set speed for level
        ballYSpeed += level*10;

        //Clear display and reset block counts
        levelMaker.getRoot().getChildren().clear();
        levelMaker.getListOfBlocks().clear();
        blocksDestroyed=0;
        levelMaker.setNumBlocks(0);

        //Create scene
        paddle = new Paddle(levelScene);
        ball = new Ball(levelScene, false);
        levelMaker.makeBlockLayout(level);
        levelMaker.getRoot().getChildren().add(paddle.getPaddle());
        levelMaker.getRoot().getChildren().add(ball.getBall());
        levelMaker.getRoot().getChildren().addAll(headings.headerFooter(myScene, "top"), headings.headerFooter(myScene, "bottom"));
        showLevelNumber = textMaker("Level: " + Integer.toString(level), 410, 25, "Verdana", 16, Color.WHITE);
        showTitle = textMaker("Breakout Game by Louis Jensen", 20, 25, "Verdana", 16, Color.WHITE);
        showLives = textMaker("Lives Remaining: " + Integer.toString(livesRemaining), 20, 485, "Verdana", 16, Color.WHITE);
        showScore = textMaker("Score: " + Integer.toString(totalScore), 410, 485, "Verdana", 16, Color.WHITE);
        levelMaker.getRoot().getChildren().addAll(showLevelNumber, showTitle, showLives, showScore);
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
        Text text3 = new Text("\nUse the Arrow Keys to play \nPress B to begin");
        text3.setFont(Font.font ("Verdana", 14));
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
        //Check to see if player won level
        if (blocksDestroyed == levelMaker.getNumBlocks()){
            levelStatus = "You completed ";
            nextLEVEL = LEVEL + 1;
        }

        //Create screen to display level ersults
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
            if (catchAndRelease && ball.getY() < paddle.getY()+3) ball.setX(ball.getX() + paddle.getSpeed());
        }
        else if (code == KeyCode.LEFT) {
            paddle.setX(paddle.getX() - paddle.getSpeed());
            if(catchAndRelease&& ball.getY() < paddle.getY()+3) ball.setX(ball.getX() - paddle.getSpeed());
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

    /**
     *Returns true if ball and block collide
     */
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
        //Block has been eliminated remove from screen
        if (block.numHits == 1) {
            block.setX(-SIZE);
            block.setY(-SIZE);
            blocksDestroyed++;
            totalScore++;
            showScore.setText("Score: " + totalScore);
            if ((int)(Math.random() * 10 + 1) == 1) {
                addPowerUp((int)(Math.random() * 2 + 1));
            }
        //Block not eliminated but needs color change
        } else {
            block.numHits --;
            block.updateColor();
            totalScore++;
            showScore.setText("Score: " + totalScore);
        }
    }

    /**
     * Generates one of two randomly selected power ups
     */
    private void addPowerUp(int powerUp){
        //Slow ball power up
        if (powerUp == 1){
            ball.setColor(Color.GOLD);
            ballXSpeed /= 2;
            ballYSpeed /= 2;
        //Big paddle power up
        } else if (powerUp == 2){
            paddle.getPaddle().setWidth(paddleWidth*1.5);
            paddle.getPaddle().setFill(Color.GOLD);
        }
    }

    /**
     * Updates attributes of shapes to create animation
     */
    private void step (double elapsedTime) {
        //Set limits to prevent paddle from going off the screen
        if (paddle.getX() > myScene.getWidth() - paddle.getPaddleWidth()){
            paddle.setX(myScene.getWidth() - paddle.getPaddleWidth());
        }
        if (paddle.getX() < 0){
            paddle.setX(0);
        }

        //Increase x speed so ball bounving straight up and down doesn't get boring
        if (ballXSpeed < 30 && ballXSpeed > -30) ballXSpeed*=2;
        ball.setX(ball.getX() + ballXSpeed * elapsedTime);
        ball.setY(ball.getY() + ballYSpeed * elapsedTime);

        //Bounce ball on edges of screen
        if (ball.getX() > myScene.getWidth() - ball.getBallRadius() || ball.getX() < ball.getBallRadius()) {
            ballXSpeed*=-1;
        }
        if (ball.getY() < ball.getBallRadius() + myScene.getHeight()/13) {
            ballYSpeed*=-1;
        }

        //Bounce ball off of paddle
        var intersect = Shape.intersect(ball.getBall(), paddle.getPaddle());
        if (intersect.getBoundsInLocal().getWidth() != -1) {
            ballYSpeed*=-1;
            //If ball hits left third angle bounce left
            if (ball.getX() < paddle.getX() + paddle.getPaddleWidth()/3){
                ballXSpeed-=ballXSpeed*0.2;
            }
            //If ball hits right third angle bounce right
            if (ball.getX() > paddle.getX() + 2*paddle.getPaddleWidth()/3){
                ballXSpeed+=ballXSpeed*0.2;
            }
        }

        //Check blocks for collision with ball
        for (Block b : levelMaker.getListOfBlocks()) {
            if (ballCollidesWithBlock(ball, b)) {
                ballYSpeed *= -1;
                updateBlock(b);
            }
        }

        //End level when appropriate
        if (livesRemaining == 0 || blocksDestroyed == levelMaker.getNumBlocks()){
            endLevel();
        }

        //If paddle misses ball lose a  life and reset
        if (ball.getY() > myScene.getHeight()){
            resetBallAndPaddle(ball);
            livesRemaining--;
            showLives.setText("Lives Remaining: " +Integer.toString(livesRemaining));
        }

        //Create moving blocks on level three
        if (LEVEL == 3) moveBlocks(elapsedTime);
    }

    /**
     * Moves ball and paddle back to default size, color, and position
     */
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

    /**
     *Allows blocks to move and is applied in level three
     */
    private void moveBlocks(double elapsedTime){
        for (Block b : levelMaker.getListOfBlocks()){
            b.setX(b.getX()+ b.getBlockSpeed() * elapsedTime);
            if (b.getX() < 0 || b.getX() > myScene.getWidth()-b.getWidth()){
                b.reverseBlockSpeed();
            }
        }
    }
}