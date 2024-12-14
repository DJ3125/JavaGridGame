package com.example.gridgameproject_2_1_24;

import javafx.fxml.FXMLLoader;

public enum Scenes{
    INTRODUCTION_SCENE("intro-scene.fxml"),
    PLAYER_SELECTION_SCENE("player-selection-scene.fxml"),
    END_SCENE("winning-scene.fxml"),
    GRID_GAME_SCENE("grid-game-scene.fxml");
    Scenes(String fxmlLoaderName){this.fxmlLoader = new FXMLLoader(SceneDirector.class.getResource(fxmlLoaderName));}
    public FXMLLoader getFxmlLoader() {return this.fxmlLoader;}
    public void reset(){
        this.fxmlLoader.setController(null);
        this.fxmlLoader.setRoot(null);
    }
    private final FXMLLoader fxmlLoader;
}
