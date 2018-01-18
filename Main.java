/*
 * Eviatar Natan
 * 307851808
 */
package gameapp;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;


public class Main extends Application {
        private static Stage mainMenuWindow;
        private static Scene layoutDisplay;
        private static Stage gameWindow;
        private Button playButton;
        private Button gameSettingsButton;
        private Button exitGameButton;
        private final int buttonMinSize = 100;

        public static void main(String[] args) {
            launch(args);
        }

        @Override
        public void start(Stage primaryStage) throws Exception {
            // defining the main window.
            mainMenuWindow = primaryStage;
            mainMenuWindow.setTitle("Reversi");

            VBox mainLayout = new VBox(25);
            layoutDisplay = new Scene(mainLayout,250,250);
            mainMenuWindow.setMinWidth(140);
            mainMenuWindow.setMinHeight(200);
            // creates the Play button.
            this.playButton = new Button("Play");
            // responsible for opening and closing the game window.
            this.playButton.setOnAction(e -> {
                try {
                    gameWindow = new Stage();
                    // loading preferences from fxml file.
                    HBox root = FXMLLoader.load(getClass().getResource("ReversiGame.fxml"));
                    Scene scene = new Scene(root, 500, 400);
                    scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
                    // sets the game window
                    gameWindow.setMinWidth(500);
                    gameWindow.setMinHeight(400);
                    gameWindow.setTitle("ReversiGame");
                    gameWindow.setScene(scene);
                    gameWindow.show();
                    primaryStage.close();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            });
            // creates the settings button
            GameSettings settingsWindow = new GameSettings();
            this.playButton.setMinWidth(buttonMinSize);
            this.gameSettingsButton = new Button("Settings");
            // opens the game settings window
            this.gameSettingsButton.setOnAction(e -> settingsWindow.display());
            this.gameSettingsButton.setMinWidth(buttonMinSize);
            // sets the close button
            this.exitGameButton = new Button("Exit");
            this.exitGameButton.setMinWidth(100);
            // sets the action of the close button.
            this.exitGameButton.setOnMouseClicked(e -> {
                mainMenuWindow.close();
            });

            mainLayout.setAlignment(Pos.CENTER);
            mainLayout.getChildren().addAll(this.playButton, this.gameSettingsButton, this.exitGameButton);
            primaryStage.setTitle("Reversi");
            primaryStage.setScene(layoutDisplay);
            primaryStage.centerOnScreen();
            primaryStage.show();
        }

        /**
         * closes the game's window and returns to the main menu.
         */
        public static void returnToMainMenu() {
            gameWindow.close();
            mainMenuWindow.setScene(layoutDisplay);
            mainMenuWindow.show();
        }
    }
