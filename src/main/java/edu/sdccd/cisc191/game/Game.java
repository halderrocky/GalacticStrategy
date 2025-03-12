package edu.sdccd.cisc191.game;

import javafx.animation.AnimationTimer; // 1 Game loop that calls handle() method
import javafx.application.Application;  // 2 JavaFX application base class
import javafx.scene.Scene;              // 3 Container for all content in a scene graph
import javafx.scene.control.Label;      // 4 IU control text display
import javafx.scene.input.KeyCode;      // 5 Provides key codes to handle keyboard input
import javafx.scene.layout.StackPane;   // 6 Layout pane that centers its children on top of one another
import javafx.stage.Stage;              // 7 Top-level window


public class Game extends Application { // Inherits lifecycle methods: start() & extends to become JavaFX app

    private long lastUpdate = 0;        // Field to track timestamp (in nanoseconds)

    private GameState gameState;        // Field to store the current state of the game

    public enum GameState {             // Define an enumeration for different game states
        MENU,
        PLAYING,
        PAUSED,
        GAME_OVER
    }


    public static void main(String[] args) {
        // Launch the JavaFX application
        launch(args);
    }

    @Override
    public void start (Stage primaryStage) {    // Main window
        // Initialize game components
        GameBoard board = new GameBoard();
        board.initializeBoard();

        gameState = GameState.MENU;

        // Create a simple IU for demonstration purposes
        Label statusLabel = new Label("Welcome to Galactic Strategy! (Press ENTER to start)");

        StackPane root = new StackPane(statusLabel);

        Scene scene = new Scene(root, 400, 200);   // Displays

        // Set up a key event handler on the scene to listen for keyboard input for state changes
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER && gameState == GameState.MENU) {
                gameState = GameState.PLAYING;
            }

            else if (event.getCode() == KeyCode.P) {
                if (gameState == GameState.PLAYING) {
                    gameState= GameState.PAUSED;
                } else if (gameState == GameState.PAUSED) {
                    gameState = GameState.PLAYING;
                }
            }

            else if (event.getCode() == KeyCode.ESCAPE) {
                gameState = GameState.GAME_OVER;
            }
        });



        primaryStage.setTitle("Galactic Strategy"); // Set Title window (Primary stage)
        primaryStage.setScene(scene);               // Attach the scene to the primary stage
        primaryStage.show();                        // Displays primary stage (Opens window)




        // Create an AnimationTimer to serve as the game loop
        // The handle() method is called every frame
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {  // 'now' is the current timestamp in nanoseconds
                if (lastUpdate == 0) {     // For the very first frame, initialize lastUpdate
                    lastUpdate = now;
                    return;
                }
                // Calculate deltaTime: the time elapse since the last update (converted to seconds)
                double deltaTime = (now - lastUpdate) / 1_000_000_000.0;
                // Update the lastUpdate time to the current timestamp
                lastUpdate = now;

                // Call the method to update game logic based on the current game state
                updateGame(deltaTime, statusLabel);
            }
        };
        // Start the game loop so that handle() gets called every frame
        gameLoop.start();
    }




    private void updateGame(double deltaTime, Label statusLabel) {
        // Use the switch statement to handle different game states
        switch (gameState) {
            case MENU:
                // In the MENU state, display a prompt for the player
                statusLabel.setText("In Menu... Press 'Enter' to play");
                // TODO: Add logic to change the state to PLAYING when the player starts the game
                break;
            case PLAYING:
                // in the PLAYING state, update game mechanics (e.g., move ships, process combat)
                statusLabel.setText("Game is running. Delta time: " + deltaTime + " seconds");
                // TODO: Implement game logic such as updating positions, detecting collisions, etc
                break;
            case PAUSED:
                // In the PAUSED state, show that the game is paused
                statusLabel.setText("Game is Paused. Press 'P'' to resume!");
                // TODO:
                break;
            case GAME_OVER:
                // In the GAME_OVER state, display the game over message
                statusLabel.setText("Game Over! Press 'ESC' to exit");
                break;
        }
    }
}