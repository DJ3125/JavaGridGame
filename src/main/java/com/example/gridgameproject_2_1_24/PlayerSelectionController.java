package com.example.gridgameproject_2_1_24;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Arrays;

public final class PlayerSelectionController {
    @FXML private HBox playerSelectionHBox;
    @FXML private Button proceedButton;

    private void updatePlayerSelection(){
        this.playerSelectionHBox.setAlignment(Pos.CENTER);
        this.proceedButton.setText("Proceed: (" + this.individualPlayers.size() + "/" + this.MAX_AMOUNT_OF_PLAYERS + ")");
        this.proceedButton.setDisable(this.individualPlayers.size() < 2);
        this.playerSelectionHBox.getChildren().clear();
        for(PlayerCreationTemplate i : this.individualPlayers){
            VBox vBox = new VBox();
            vBox.setAlignment(Pos.CENTER);
            TextField textField = new TextField(i.getName());
            textField.addEventHandler(KeyEvent.KEY_TYPED, keyEvent -> {
                String text = textField.getText();
                if(text.length() >=7 || (!keyEvent.getCharacter().trim().isEmpty() && !Character.isAlphabetic(keyEvent.getCharacter().charAt(0)))){keyEvent.consume();}
                else if(text.isEmpty() || this.doesNameExist(text, i)){
                    i.setName(text);
                    this.modifyInfoUntilUnique(i);
                    if(keyEvent.getCharacter().isEmpty()){keyEvent.consume();}
                    textField.setText(i.getName());
                }else{if(!keyEvent.getCharacter().isEmpty()){i.setName(text);}}
            });
            ChoiceBox<String> colors = new ChoiceBox<>();
            colors.getItems().addAll(this.colors);
            colors.getSelectionModel().select(i.getColor());
            colors.addEventHandler(ActionEvent.ACTION, actionEvent -> {
                String selectedColor = colors.getSelectionModel().getSelectedItem();
                if(i.getColor() != null){this.colors.add(i.getColor());}
                i.setColor(selectedColor);
                this.colors.remove(selectedColor);
                this.updatePlayerSelection();
            });
            Button remove = new Button("Remove");
            remove.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                this.colors.add(i.getColor());
                this.individualPlayers.remove(i);
                this.updatePlayerSelection();
            });
            vBox.getChildren().addAll(textField, colors, remove);
            this.playerSelectionHBox.getChildren().add(vBox);
        }
        if(this.individualPlayers.size() < this.MAX_AMOUNT_OF_PLAYERS){
            VBox vBox = new VBox();
            vBox.setAlignment(Pos.CENTER);
            Button createButton = new Button("Create Another Player");
            createButton.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent->{
                PlayerCreationTemplate template = new PlayerCreationTemplate(this.colors.get(0));
                this.individualPlayers.add(template);
                this.modifyInfoUntilUnique(template);
                this.colors.remove(0);
                this.updatePlayerSelection();
            });
            vBox.getChildren().addAll(createButton);
            this.playerSelectionHBox.getChildren().add(vBox);
        }
    }

    private void modifyInfoUntilUnique(PlayerCreationTemplate template){while(this.doesNameExist(template.getName(), template)){template.setName(template.getName().concat("JA"));}}

    public void initializePlayerSelection(){
        this.individualPlayers = new ArrayList<>();
        this.colors = new ArrayList<>(Arrays.asList("red", "blue", "green", "yellow"));
        this.updatePlayerSelection();
    }

    private boolean doesNameExist(String name, PlayerCreationTemplate originalTemplate){
        for(PlayerCreationTemplate i : this.individualPlayers){if(i != originalTemplate && i.getName() != null && i.getName().equals(name)){return true;}}
        return false;
    }
    @FXML public void handleProceed(){
        IndividualPlayer[] players = new IndividualPlayer[this.individualPlayers.size()];
        for(byte i =0; i< players.length; i++){players[i] = new IndividualPlayer(this.individualPlayers.get(i));}
        SceneDirector.switchScenes(Scenes.GRID_GAME_SCENE);
        ((GridGameController)(Scenes.GRID_GAME_SCENE.getFxmlLoader().getController())).initializeGridScene(players);
    }

    private ArrayList<PlayerCreationTemplate> individualPlayers;
    private final byte MAX_AMOUNT_OF_PLAYERS = 2;
    private ArrayList<String> colors;
}
