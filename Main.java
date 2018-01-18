package gameapp;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;


public class Main extends Application {
	   private final int MINBUTTONSIZE = 100;
	    // the 3 stages of the game - main, settings and game.
	    private static Stage mainWindow;
	    private static Scene layoutDisplay;
	    private static Stage gameWindow;
	    // the buttons of the main window
	    private Button startGameButton;
	    private Button settingsButton;
	    private Button closeButton;


	    public static void main(String[] args) {
	        launch(args);
	    }

	    @Override
	    public void start(Stage primaryStage) throws Exception {
	        // defining the main window.
	        mainWindow = primaryStage;
	        mainWindow.setTitle("Reversi");

	        VBox layoutMain = new VBox(20);
	        layoutDisplay = new Scene(layoutMain, 300, 300);

	        // set height and width of window
	        mainWindow.setMinWidth(140);
	        mainWindow.setMinHeight(200);

	        // define the main windows buttons
	        this.startGameButton = new Button("Start game!");
	        // set action of the startGame button.
	        this.startGameButton.setOnAction(e -> {
	            try {
	                gameWindow = new Stage();
	                // loading preferences from fxml file.
	                HBox root = FXMLLoader.load(getClass().getResource("ReversiGame.fxml"));
	                Scene scene = new Scene(root, 500, 400);
	                scene.getStylesheets().add(getClass().getResource("Reversi.css").toExternalForm());
	                // setting the game window
	                gameWindow.setMinWidth(500);
	                gameWindow.setMinHeight(400);
	                gameWindow.setTitle("Reversi");
	                gameWindow.setScene(scene);
	                gameWindow.show();
	                primaryStage.close();
	            } catch (Exception e1) {
	                e1.printStackTrace();
	            }
	        });
	        // setting the settings button
	        SettingsWindow settingsWindow = new SettingsWindow();
	        this.startGameButton.setMinWidth(MINBUTTONSIZE);
	        this.settingsButton = new Button("Settings");
	        // setting the action of the settings button.
	        this.settingsButton.setOnAction(e -> settingsWindow.display());
	        this.settingsButton.setMinWidth(MINBUTTONSIZE);
	        // setting the close button
	        this.closeButton = new Button("Close");
	        this.closeButton.setMinWidth(100);
	        // setting the action of the close button.
	        this.closeButton.setOnMouseClicked(e -> {
	            mainWindow.close();
	        });

	        layoutMain.setAlignment(Pos.CENTER);
	        layoutMain.getChildren().addAll(this.startGameButton, this.settingsButton, this.closeButton);

	        // set all of the main windows properties
	        primaryStage.setTitle("Reversi");
	        primaryStage.setScene(layoutDisplay);
	        // set window to center of screen
	        primaryStage.centerOnScreen();

	        // show window
	        primaryStage.show();
	    }

	    /*
	     * function name: switchBacToMain.
	     * input: none.
	     * output: none.
	     * operation: closes the game window and shows the main menu.
	     */
	    public static void switchBackToMain() {
	        gameWindow.close();
	        mainWindow.setScene(layoutDisplay);
	        mainWindow.show();
	    }
	}
