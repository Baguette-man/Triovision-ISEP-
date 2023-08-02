package com.example.triovisioniseprtp;


import com.example.triovisioniseprtp.classes.Color;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {


    private TrioVisionGame game;
    private int selectedPlayer =-1;
    private int selectedCard =-1;
    private Stage stage;

    private boolean[] cardsDone = new boolean[12];

    @FXML
    private Label helperText;
    @FXML
    private Label actionText;
    @FXML
    HBox cardHor;


    public void setMain(TrioVisionGame main) {
        this.game = main;
    }

    @FXML
    public void player1(){
        selectedPlayer = 0;
    }

    @FXML
    public void player2(){
        selectedPlayer = 1;
    }

    @FXML
    public void handleCardClick(javafx.scene.input.MouseEvent event){

        Region clickedButton = (Region) event.getSource();
        System.out.println(clickedButton);
//        // Get the ID of the clicked button.
//        String clickedButtonId = clickedButton.getId();
//        String buttonId = clickedButtonId.replace("card", "");
//        int col = Integer.parseInt(buttonId.substring(0,1));
//        int line = Integer.parseInt(buttonId.substring(1,2));
//        selectedCard = col*4+line;

    }

    @FXML
    public void onButtonClicked(ActionEvent event) {
        // Get the button that was clicked.
        Button clickedButton = (Button) event.getSource();

        // Get the ID of the clicked button.
        String clickedButtonId = clickedButton.getId();
        String buttonId = clickedButtonId.replace("button", "");
        int col = Integer.parseInt(buttonId.substring(0,1));
        int line = Integer.parseInt(buttonId.substring(1,2));
        // Now you can use the buttonId as needed.
        if(selectedPlayer ==-1){
                helperText.setText("Choisissez un joueur avant de faire des modifications sur le plateau");
        }
        else if (selectedCard == -1){
            helperText.setText("Veuillez choisir la carte que vous avez trouvé");
        }
        else if (game.highlighted == -1) {
            game.highlighted = col*4+line;
            game.updatePlateau();
        }
        else if (!(game.plateau[col * 4 + line] == Color.WHITE && game.plateau[game.highlighted] == Color.WHITE)){

            game.movePawn(game.highlighted, col * 4 + line, selectedPlayer);
            game.highlighted = -1;
            game.updatePlateau();
            selectedPlayer = -1;
        }
    }


    public void rmCard(){
        VBox selected = (VBox) cardHor.lookup("#cardCol"+selectedCard/4);
        VBox selectedDeep = (VBox) selected.lookup("#card"+selectedCard);
        cardsDone[selectedCard] = true;
        selectedDeep.getStyleClass().remove("highlightedCard");
        selectedDeep.getStyleClass().add("doneCard");
        selectedCard = -1;

    }

    public void setStage(Stage stage) throws IOException {
        this.stage = stage;


        for (int i = 0; i < 12; i++) {
            FXMLLoader cardLoader = new FXMLLoader(getClass().getResource("card.fxml"));
            VBox cardRegion = cardLoader.load();
            cardRegion.setId("card"+i);
            cardRegion.setOnMouseClicked(event -> {

                if(cardsDone[Integer.parseInt(cardRegion.getId().replace("card", ""))]){
                    helperText.setText("Cette carte a deja été trouvée, elle ne peut plus être jouée");
                }
                else if(selectedCard != -1){
                    VBox selected = (VBox) cardHor.lookup("#cardCol"+selectedCard/4);
                    VBox selectedDeep = (VBox) selected.lookup("#card"+selectedCard);
                    selectedDeep.getStyleClass().remove("highlightedCard");
                }
                selectedCard = Integer.parseInt(cardRegion.getId().replace("card", ""));
                cardRegion.getStyleClass().add("highlightedCard");
            });
            int[] toFill = {0,2,5};
            Color[] usedColors= new Color[3];
            for (int j = 0; j < 3; j++) {
                Region reg = (Region) cardRegion.lookup("#"+toFill[j]);
                Color rngColor = Color.getRandomColor();
                if((j ==2) && (usedColors[0] == usedColors[1]) && (rngColor == usedColors[0])){
                    while(usedColors[0] == rngColor ){
                        rngColor = Color.getRandomColor();
                    }
                }
                reg.getStyleClass().add(rngColor.toCSS());
                usedColors[j] = rngColor;
            }
            String colStr = "cardCol"+i/4;
            VBox col = (VBox) cardHor.lookup("#"+colStr);
            col.getChildren().add(cardRegion);
        }

    }

    public void setHelper(String txt){
        helperText.setText(txt);
    }

    public void showRules(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Règles du TrioVision");
        alert.setHeaderText("Comment Jouer ?");
        alert.setContentText("Principe du jeu\n" +
                "On pose au milieu de la table le plateau de jeu et les 8 pions. \n"+
                " On répartit 12 cartes autour du plateau. Tous les joueurs jouent en même temps. \n"+
                "Il s'agit d'être le premier à voir comment on peut reproduire la figure d'une des 12 cartes en\n"+
                " bougeant un seul pion sur le plateau. Qui sera le premier à découvrir avec quel pion c'est possible?");

        // Add "OK" button
        ButtonType buttonTypeOk = new ButtonType("OK");
        alert.getButtonTypes().setAll(buttonTypeOk);

        // Show the popup and wait for the user's response
        alert.showAndWait().ifPresent(buttonType -> {
        });
    }
}