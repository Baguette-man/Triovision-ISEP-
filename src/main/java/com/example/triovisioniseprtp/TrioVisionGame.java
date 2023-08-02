package com.example.triovisioniseprtp;

import com.example.triovisioniseprtp.classes.Color;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.Objects;

public class TrioVisionGame extends Application {
    Button[] buttons = new Button[16];
    HelloController controller;
    int[] score = {0,0};

    public Color[] plateau = {
            Color.WHITE, Color.RED, Color.RED, Color.WHITE,
            Color.BLUE, Color.WHITE, Color.WHITE, Color.GREEN,
            Color.BLUE, Color.WHITE, Color.WHITE, Color.GREEN,
            Color.WHITE, Color.YELLOW, Color.YELLOW, Color.WHITE
    };
    public int highlighted = -1;


    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
        Parent root = loader.load();
        controller = loader.getController();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());

        initCards();
        controller.setMain(this);
        controller.setStage(primaryStage);

        buttons= new Button[]{
                (Button) scene.lookup("#button00"), (Button) scene.lookup("#button01"), (Button) scene.lookup("#button02"), (Button) scene.lookup("#button03"),
                (Button) scene.lookup("#button10"), (Button) scene.lookup("#button11"), (Button) scene.lookup("#button12"), (Button) scene.lookup("#button13"),
                (Button) scene.lookup("#button20"), (Button) scene.lookup("#button21"), (Button) scene.lookup("#button22"), (Button) scene.lookup("#button23"),
                (Button) scene.lookup("#button30"), (Button) scene.lookup("#button31"), (Button) scene.lookup("#button32"), (Button) scene.lookup("#button33")
        };
        updatePlateau();
        primaryStage.setTitle("Triovision Game");

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void updatePlateau(){
        for (int i = 0; i < 16; i++) {
            buttons[i].getStyleClass().clear();
            buttons[i].getStyleClass().add("square-button");
            if (i == highlighted){
                buttons[i].getStyleClass().add("highlighted");
            }
            buttons[i].getStyleClass().add(plateau[i].toCSS());
        }

        if(score[0]+score[1] == 12){
            String winner;
            if(score[0]>score[1]){
                winner = "Joueur 1";
            }
            else{
                winner = "Joueur 2";
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Bravo");
            alert.setHeaderText(null);
            alert.setContentText("Bravo "+ winner + " vous avez gagné la partie, relancez le jeu pour refaire une partie");

            // Add "OK" button
            ButtonType buttonTypeOk = new ButtonType("OK :)");
            alert.getButtonTypes().setAll(buttonTypeOk);

            // Show the popup and wait for the user's response
            alert.showAndWait().ifPresent(buttonType -> {
                // Close the game when "OK" is clicked
                System.exit(0);
            });
        }

    }


    public void movePawn(Integer position1, Integer position2, int player) {
        Color[] og = plateau.clone();
        Color temp = plateau[position1];
        plateau[position1] = plateau[position2];
        plateau[position2] = temp;
        updatePlateau();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Carte valide ?");
        alert.setHeaderText(null);
        alert.setContentText("Cette carte a bien été validée ?");

        ButtonType buttonTypeYes = new ButtonType("Oui");
        ButtonType buttonTypeNo = new ButtonType("Non");
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);


        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == buttonTypeYes) {
                controller.rmCard();
                score[player] = score[player]+1;
                //System.out.println(Arrays.toString(score));
            } else {
                plateau = og;
                updatePlateau();
                controller.setHelper("Le déplacement a été annulé");
            }
        });


    }

    public void initCards(){
        int numRows = 4; // Number of big rectangles (rows)
        int numCols = 3; // Number of big rectangles (columns)
        int smallRectRows = 2; // Number of small rectangles (rows) in each big rectangle
        int smallRectCols = 3; // Number of small rectangles (columns) in each big rectangle

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                Rectangle bigRectangle = createRectangle(100, 100, javafx.scene.paint.Color.BLUE); // Adjust the size and color as needed
                GridPane.setConstraints(bigRectangle, col * smallRectCols, row * smallRectRows);
                //gridPane.getChildren().add(bigRectangle);

                for (int smallRow = 0; smallRow < smallRectRows; smallRow++) {
                    for (int smallCol = 0; smallCol < smallRectCols; smallCol++) {
                        Rectangle smallRectangle = createRectangle(30, 30, javafx.scene.paint.Color.LIGHTBLUE); // Adjust the size and color as needed
                        GridPane.setConstraints(smallRectangle, col * smallRectCols + smallCol, row * smallRectRows + smallRow);
                        //gridPane.getChildren().add(smallRectangle);
                    }
                }
            }
        }

    }

    private Rectangle createRectangle(double width, double height, javafx.scene.paint.Color color) {
        Rectangle rectangle = new Rectangle(width, height);
        rectangle.setFill(color);
        rectangle.setStroke(javafx.scene.paint.Color.BLACK);
        return rectangle;
    }
}