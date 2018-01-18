package gameapp;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.*;
import javafx.scene.control.*;
import java.io.*;
import java.util.ArrayList;

/**
 * determines the settings of the game.
 * responsible of starting player, player's colors and board size.
 * can read a setting file that the user has saved.
 */
public class GameSettings {

    private static ComboBox<String> selectStartingPlayer = new ComboBox<>();
    private static ColorPicker firstPlayerColorPicker = new ColorPicker();
    private static ColorPicker secondPlayerColorPicker = new ColorPicker();
    private static Slider boardSize = new Slider();
    private Label boardSizeLabel = new Label();

    private static final String SETTINGSFILEPATH = "settings.txt";
    private static final int MINBOARDSIZE = 4;
    private static final int MAXSIZE = 20;
    private static final int MAXSLIDERWIDTH = 200;
    private static final double DEFAULTBOARDSIZE = 4;
    private static final double BLOCKINCREMENT = 0;

    // defaults settings in case the settings file does not exists.
    private static final String DEFAULTFIRSTPLAYER = "BlackPlayer";
    private static final String DEFAULTFIRSTPLAYERCOLOR = "#000000";
    private static final String DEFAULTSECONDPLAYERCOLOR = "#ffffff";

    /**
     * responsible for game settings display.
     */
    public void display() {
        final int WIDTH = 350;
        final int HEIGHT = 400;

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Settings");
        window.setResizable(false);

        // starting player options display options
        Label startingPlayer = new Label("Starting player:");
        selectStartingPlayer.setValue("BlackPlayer");
        selectStartingPlayer.getItems().clear();
        selectStartingPlayer.getItems().add("BlackPlayer");
        selectStartingPlayer.getItems().add("WhitePlayer");

        // first player color display
        Label firstPlayerColor = new Label("First player color:");
        // second player color display
        Label secondPlayerColor = new Label("Second player color:");

        Label sizeLabel = new Label("Board size:");
        // set the minimum and maximum board sizes.
        boardSize.setMin(MINBOARDSIZE);
        boardSize.setMax(MAXSIZE);
        boardSize.setMaxWidth(MAXSLIDERWIDTH);
        boardSize.setShowTickMarks(true);
        boardSize.setShowTickLabels(true);
        boardSize.setBlockIncrement(BLOCKINCREMENT);
        for(int i = 1; i < 5; i++) {
            boardSize.adjustValue(MINBOARDSIZE + i * 2);
        }
        // display chosen size when slider is dragged
        boardSize.setOnMouseDragged(e -> {
            this.boardSizeLabel.setText(String.valueOf(Math.floor(boardSize.getValue())).replace(".0", ""));
        });
        HBox slideLayout = new HBox(10);
        slideLayout.setAlignment(Pos.CENTER);
        slideLayout.getChildren().addAll(sizeLabel, boardSize, boardSizeLabel);

        // initialize the color pickers to default.
        firstPlayerColorPicker.setValue(Color.BLACK);
        secondPlayerColorPicker.setValue(Color.WHITE);

        // read settings from the settings file.
        loadSettings();

        // setting the button that closes the window.
        Button closeAndSave = new Button("Close and save");
        // set action to the "save & close" button.
        closeAndSave.setOnAction(e -> {
            try {
                // write the current settings into the settings file.
                writeSettingsToFile(boardSize.getValue(), firstPlayerColorPicker.getValue().toString(),
                        secondPlayerColorPicker.getValue().toString(), selectStartingPlayer.getValue());
            } catch (IOException exc) {
                System.out.println("Error writing into settings file");
            }
            window.close();
        });

        // setting the button that closes the window.
        Button closeWithoutSaving = new Button("Close and don't save");
        // set action to "close w/o save" button.
        closeWithoutSaving.setOnAction(e -> {
            window.close();
        });

        // setting the layout of the window.
        VBox layout = new VBox(15);
        Scene scene = new Scene(layout, WIDTH, HEIGHT);
        window.setScene(scene);


        VBox innerLayout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        innerLayout.setAlignment(Pos.CENTER);

        // add all of the wanted objects to the layouts
        innerLayout.getChildren().add(closeAndSave);
        innerLayout.getChildren().add(closeWithoutSaving);
        layout.getChildren().addAll(startingPlayer, selectStartingPlayer, firstPlayerColor, firstPlayerColorPicker,
                secondPlayerColor, secondPlayerColorPicker, slideLayout, innerLayout);

        // set the windows scene to be the layout

        // display window
        window.showAndWait();
    }

    /**
     * writes current settings to settings file.
     * @param size - the size of the new board according to the slider.
     * @param firstColor - the color of the first player.
     * @param secondColor - the color of the second player.
     * @param starter - the starting player.
     * @throws IOException throws an error in case of not able writing into the file.
     */
    private static void writeSettingsToFile(Double size, String firstColor,
                               String secondColor, String starter) throws IOException {
        // opening the file.
        File settingsFile = new File(SETTINGSFILEPATH);
        BufferedWriter bufferWriter = null;
        try {
            bufferWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(settingsFile)));
            Writer writer = bufferWriter;
            // writing first player color
            writer.write("first Player color: " + firstColor + "\n");
            // writing second player color
            writer.write("second Player color: " + secondColor + "\n");
            // writing starting player
            writer.write("Starting player: " + starter + "\n");
            // writing board size
            writer.write("size: " + size + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            bufferWriter.close();
        }
    }

    /**
     * loads game settings.
     * @return a list of settings read from the file.
     */
    public static ArrayList<String> loadSettings() {
        // opening the file.
        File file = new File(SETTINGSFILEPATH);
        FileReader fileReader;
        ArrayList<String> info = new ArrayList<>();
        if(file.exists()) {
            String firstColor, secondColor, starter, line;
            BufferedReader bufferedReader = null;
            try {
                fileReader = new FileReader(SETTINGSFILEPATH);
                bufferedReader = new BufferedReader(fileReader);
                line = bufferedReader.readLine();
                line = line.replace("first Player color: ", "");
                firstColor = line;
                line = bufferedReader.readLine();
                line = line.replace("second Player color: ", "");
                secondColor = line;
                line = bufferedReader.readLine();
                line = line.replace("Starting player: ", "");
                starter = line;
                line = bufferedReader.readLine();
                line = line.replace("size: ", "");
                // set values
                boardSize.setValue(Double.valueOf(line));
                selectStartingPlayer.setValue(starter);
                firstPlayerColorPicker.setValue(Color.valueOf(firstColor));
                secondPlayerColorPicker.setValue(Color.valueOf(secondColor));

            } catch (IOException e) {
                System.out.println("Error reading from file");
            } finally {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    System.out.println("Error closing the file");
                }
            }
            info.add(selectStartingPlayer.getValue());
            info.add(firstPlayerColorPicker.getValue().toString());
            info.add(secondPlayerColorPicker.getValue().toString());
            info.add(String.valueOf(Math.floor(boardSize.getValue())).replace(".0", ""));
            // if the file is empty
        } else {
            try {
                writeSettingsToFile(DEFAULTBOARDSIZE,"Black", "White", "BlackPlayer");
            } catch (IOException e) {
                System.out.println("Error creating the file");
            }
            info.add(DEFAULTFIRSTPLAYER);
            info.add(DEFAULTFIRSTPLAYERCOLOR);
            info.add(DEFAULTSECONDPLAYERCOLOR);
            info.add(String.valueOf((int)Math.floor(DEFAULTBOARDSIZE)));
        }
        return info;
    }
}