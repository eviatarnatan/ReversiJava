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
 * determine the settings of the game.
 * responsible of board size, players' color and starting player.
 * reads the settings from file.
 */
public class SettingsWindow {

    private static ComboBox<String> selectStartingPlayer = new ComboBox<>();
    private static ColorPicker firstColorPicker = new ColorPicker();
    private static ColorPicker secondColorPicker = new ColorPicker();
    private static Slider boardSize = new Slider();
    private Label boardSizeLabel = new Label();

    private static final String FILEPATH = "src/settings.txt";
    private static final int MINSIZE = 4;
    private static final int MAXSIZE = 20;
    private static final int SLIDERMAXWIDTH = 150;
    private static final double DEFAULTBOARDSIZE = 4;
    private static final double BLOCKINCREMENT = 0;

    // defaults in case settings file dosent exist yet
    private static final String FIRSTPLAYER = "BlackPlayer";
    private static final String FIRSTPLAYERCOLOR = "#000000";
    private static final String SECONDPLAYERCOLOR = "#ffffff";

    /*
     * function name: display.
     * input: none.
     * output: none.
     * operation: sets the display of the settings window.
     */
    public void display() {
        final int WIDTH = 300;
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

        // choose first players color display
        Label firstPlayerColor = new Label("First player color:");
        //firstColorPicker.setValue(Color.BLACK);
        // choose second players color display
        Label secondPlayerColor = new Label("Second player color:");

        Label sizeLabel = new Label("Board size:");
        // set min and max size of board size.
        boardSize.setMin(MINSIZE);
        boardSize.setMax(MAXSIZE);
        boardSize.setMaxWidth(SLIDERMAXWIDTH);
        boardSize.setShowTickMarks(true);
        boardSize.setShowTickLabels(true);
        boardSize.setBlockIncrement(BLOCKINCREMENT);
        for(int i = 1; i < 5; i++) {
            boardSize.adjustValue(MINSIZE + i * 2);
        }
        // display chosen size when slider is dragged
        boardSize.setOnMouseDragged(e -> {
            this.boardSizeLabel.setText(String.valueOf(Math.floor(boardSize.getValue())).replace(".0", ""));
        });
        // positioning the slider.
        HBox slideLayout = new HBox(10);
        slideLayout.setAlignment(Pos.CENTER);
        slideLayout.getChildren().addAll(sizeLabel, boardSize, boardSizeLabel);

        // initialize the color pickers to default.
        firstColorPicker.setValue(Color.BLACK);
        secondColorPicker.setValue(Color.WHITE);

        // read settings from the settings file.
        loadSettings();

        // setting the button that closes the window.
        Button closeAndsave = new Button("Close & save");
        // set action to the "save & close" button.
        closeAndsave.setOnAction(e -> {
            try {
                // write the current settings into the settings file.
                writeSettings(boardSize.getValue(), firstColorPicker.getValue().toString(),
                        secondColorPicker.getValue().toString(), selectStartingPlayer.getValue());
            } catch (IOException exc) {
                System.out.println("Error writing into settings file");
            }
            window.close();
        });

        // setting the button that closes the window.
        Button closeNoSave = new Button("Close w/o saving");
        // set action to "close w/o save" button.
        closeNoSave.setOnAction(e -> {
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
        innerLayout.getChildren().add(closeAndsave);
        innerLayout.getChildren().add(closeNoSave);
        layout.getChildren().addAll(startingPlayer, selectStartingPlayer, firstPlayerColor, firstColorPicker,
                secondPlayerColor, secondColorPicker, slideLayout, innerLayout);

        // set the windows scene to be the layout

        // display window
        window.showAndWait();
    }

    /**
     * writing the current settings into the settings file.
     * @param size the size of the new board according to the slider.
     * @param firstColor the color of the first player.
     * @param secondColor the color of the second player.
     * @param starter the starting player.
     * @throws IOException throws an error in case of not able writing into the file.
     */
    private static void writeSettings(Double size, String firstColor,
                               String secondColor, String starter) throws IOException {
        // opening the file.
        File file = new File(FILEPATH);
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            Writer writer = bufferedWriter;
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
            bufferedWriter.close();
        }
    }

    /**
     * loads the settings from the file into the game.
     * @return a list of strings that represents the settings from the file.
     */
    public static ArrayList<String> loadSettings() {
        // opening the file.
        File file = new File(FILEPATH);
        FileReader fileReader;
        ArrayList<String> info = new ArrayList<>();
        if(file.exists()) {
            String firstColor, secondColor, starter, line;
            BufferedReader bufferedReader = null;
            try {
                fileReader = new FileReader(FILEPATH);
                bufferedReader = new BufferedReader(fileReader);
                // reading first player color
                line = bufferedReader.readLine();
                line = line.replace("first Player color: ", "");
                firstColor = line;
                // reading second player color
                line = bufferedReader.readLine();
                line = line.replace("second Player color: ", "");
                secondColor = line;
                line = bufferedReader.readLine();
                // reading starting player
                line = line.replace("Starting player: ", "");
                starter = line;
                line = bufferedReader.readLine();
                // reading board size
                line = line.replace("size: ", "");
                // set values
                boardSize.setValue(Double.valueOf(line));
                selectStartingPlayer.setValue(starter);
                firstColorPicker.setValue(Color.valueOf(firstColor));
                secondColorPicker.setValue(Color.valueOf(secondColor));

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
            info.add(firstColorPicker.getValue().toString());
            info.add(secondColorPicker.getValue().toString());
            info.add(String.valueOf(Math.floor(boardSize.getValue())).replace(".0", ""));
            // if the file is empty
        } else {
            try {
                writeSettings(DEFAULTBOARDSIZE,"Black", "White", "BlackPlayer");
            } catch (IOException e) {
                System.out.println("Error creating the file");
            }
            info.add(FIRSTPLAYER);
            info.add(FIRSTPLAYERCOLOR);
            info.add(SECONDPLAYERCOLOR);
            info.add(String.valueOf((int)Math.floor(DEFAULTBOARDSIZE)));
        }
        return info;
    }
}