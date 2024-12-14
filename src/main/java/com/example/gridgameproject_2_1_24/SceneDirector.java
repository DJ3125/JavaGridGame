package com.example.gridgameproject_2_1_24;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public final class SceneDirector extends Application {
    @Override public void start(Stage stage){
        gameStage = stage;
        gameStage.setResizable(false);
        gameStage.setAlwaysOnTop(true);
//        SceneDirector.switchScenes(Scenes.GRID_GAME_SCENE);
//        ((GridGameController)(Scenes.GRID_GAME_SCENE.getFxmlLoader().getController())).initializeGridScene();
        SceneDirector.switchScenes(Scenes.INTRODUCTION_SCENE);
        ((IntroductionSceneController)(Scenes.INTRODUCTION_SCENE.getFxmlLoader().getController())).initializeIntroduction();
//        SceneDirector.switchScenes(Scenes.PLAYER_SELECTION_SCENE);
//        ((PlayerSelectionController)(Scenes.PLAYER_SELECTION_SCENE.getFxmlLoader().getController())).initializePlayerSelection();
        gameStage.setTitle("Dylan's Grid Game Bonanza!");
        gameStage.show();
    }

    public static void main(String[] args) {launch();}
    public static void switchScenes(Scenes sceneToSwitchTo){
        try{
            Scene scene = new Scene(sceneToSwitchTo.getFxmlLoader().load());
            gameStage.setScene(scene);
            gameStage.setX((scene.getWindow().getWidth() - scene.getWidth()));
            gameStage.setY((scene.getWindow().getHeight() - scene.getHeight()));
            for(Scenes i : Scenes.values()){if(i != sceneToSwitchTo){i.reset();}}
        }catch(IOException error){error.printStackTrace();}
    }
    private static Stage gameStage;
}