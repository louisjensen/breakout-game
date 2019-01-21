package game;

import javafx.application.Application;
import javafx.stage.Stage;

public class RunGame extends Application {

    GameDriver gameDriver = new GameDriver();

    public void start(Stage stage){
        gameDriver.startGame(stage);
        gameDriver.playGame();
        stage.show();
    }

    /**
     * Start the program.
     */
    public static void main (String[] args) {
        launch(args);
    }
}
