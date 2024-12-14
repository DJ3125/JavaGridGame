package com.example.gridgameproject_2_1_24;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class WinningSceneController {
    @FXML private Label winningLabel;
    @FXML private Button continueButton;
    public void initializeWinningScene(IndividualPlayer winner){this.winningLabel.setText("Congrats " + winner.getName() + "! You Have Won With " + winner.getTotalPoints() + " points");}

    @FXML public void handleContinue(){
        SceneDirector.switchScenes(Scenes.INTRODUCTION_SCENE);
        ((IntroductionSceneController)(Scenes.INTRODUCTION_SCENE.getFxmlLoader().getController())).initializeIntroduction();
    }
}
