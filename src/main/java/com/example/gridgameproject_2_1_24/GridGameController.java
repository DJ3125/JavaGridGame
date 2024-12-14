package com.example.gridgameproject_2_1_24;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.io.*;
import java.util.HashMap;
import java.util.Objects;

//update walls so they withstand explosions
//add health to all the ants

public final class GridGameController{
    @FXML private Label directTurnLabel;
    @FXML private VBox playerOptionsVBox;
    @FXML private GridPane grid;
    @FXML private BarChart<String, Short> scoringGraph;
    private void initializeOptions(){
        if(this.allGameInformation.getPlayerTurnInformation().getTurnsRemaining() > 0 ){
            this.playerOptionsVBox.getChildren().clear();
            this.directTurnLabel.setText("Its " + this.allGameInformation.getPlayerTurnInformation().getCurrentPlayerTurn().getName() + "'s turn. You Have $" + this.allGameInformation.getPlayerTurnInformation().getCurrentPlayerTurn().getMoney() + ". There Are " + this.allGameInformation.getPlayerTurnInformation().getTurnsRemaining() + " turns remaining");
            Color backgroundColor = this.allGameInformation.getPlayerTurnInformation().getCurrentPlayerTurn().getColor().interpolate(Color.WHITE, 0.5);
            this.directTurnLabel.setBackground(Background.fill(Paint.valueOf(backgroundColor.toString())));
            for(AllPossibleAntsForPurchase i  : AllPossibleAntsForPurchase.values()){
                boolean canAfford = i.getCost() <= this.allGameInformation.getPlayerTurnInformation().getCurrentPlayerTurn().getMoney();
                VBox vBox = new VBox();
                Label label = new Label(i.getName() + ":  Costs $" + i.getCost() + ((!canAfford)? " (Not Enough Money)" : ""));
                label.setAlignment(Pos.CENTER);
                vBox.getChildren().add(label);
                vBox.setBorder(Border.stroke(Paint.valueOf("black")));
                if(this.selectedAnt == i){vBox.setBackground(Background.fill(Paint.valueOf("#dce7fa")));}
                if(!canAfford){vBox.setBackground(Background.fill(Paint.valueOf("grey")));}
                vBox.setPadding(new Insets(5));
                vBox.addEventHandler(MouseEvent.MOUSE_CLICKED, (mouseEvent) ->{
                    if(this.allGameInformation.getThread().isFinishedWithTurn() && canAfford && this.selectedAnt != i){
                        System.out.println(i);
                        this.selectedAnt = i;
                        this.initializeOptions();
                    }
                });
                this.playerOptionsVBox.getChildren().add(vBox);
            }
        }else{
            this.allGameInformation.getPlayerTurnInformation().updatePlayerScores(this.allGameInformation);
            IndividualPlayer max = this.allGameInformation.getPlayerTurnInformation().getPlayers()[0];
            for(IndividualPlayer k : this.allGameInformation.getPlayerTurnInformation().getPlayers()){if(k.getTotalPoints() > max.getTotalPoints()){max = k;}}
            SceneDirector.switchScenes(Scenes.END_SCENE);
            ((WinningSceneController)(Scenes.END_SCENE.getFxmlLoader().getController())).initializeWinningScene(max);
        }
    }
    
    private void initializeAllInformation(IndividualPlayer... templates){
        try{
            GridUpdatingThread thread = new GridUpdatingThread(this, GridGameController.class.getMethod("updateGrid"), GridGameController.class.getMethod("runFlashingBorderThread", int.class), GridGameController.class.getMethod("onTurnFinished"));
            GridBoard boardInfo = new GridBoard((byte)20, (byte)20);
            PlayerTurnInformation playerTurnInformation = new PlayerTurnInformation(templates);
            this.allGameInformation = new AllGameInformation(boardInfo, thread, playerTurnInformation);
            this.grid.setGridLinesVisible(false);
            for(byte i = 0; i < (boardInfo.length() - 2 + boardInfo.height() - 2)/0.75; i++){this.allGameInformation.getAllProtectivePieces().addElement(new Walls((byte) Math.floor(1 + Math.random() * (boardInfo.length() - 2)), (byte) Math.floor(1 + Math.random() * (boardInfo.height() - 2))));}
//            this.allGameInformation.getAllProtectivePieces().addElement(new Walls((byte) 2, (byte) 3));
//            this.allGameInformation.getAllProtectivePieces().addElement(new Walls((byte) 3, (byte) 3));
            this.imageViewGrid = new ImageView[boardInfo.length()][boardInfo.height()];
            this.initializeImageHashMaps();
            this.initializeOptions();
            this.dullBorderImage = new WritableImage(1, 1);
            this.dullBorderImage.getPixelWriter().setColor(0, 0, Color.gray(0.5));
        }catch (NoSuchMethodException error){error.printStackTrace();}
    }

    public void initializeGridScene(IndividualPlayer... templates){
        this.initializeAllInformation(templates);
        this.grid.setHgap(0);
        this.grid.setVgap(0);
        for(byte i = 0; i < this.allGameInformation.getBoardInfo().length(); i++){
            for(byte j = 0; j < this.allGameInformation.getBoardInfo().height(); j++){
                ImageView imageView = new ImageView(this.imagesForGrid.get("newGrass"));
                byte xPosition = j;
                byte yPosition = i;
                imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, (mouseEvent) ->{
                    if(this.selectedAnt != null && this.allGameInformation.getThread().isFinishedWithTurn() && this.allGameInformation.getBoardInfo().isBorderSegmentUnimpeded(xPosition, yPosition, this.allGameInformation) && this.allGameInformation.getPlayerTurnInformation().getCurrentPlayerTurn().buyAnt(this.selectedAnt)){
                        byte xVelocity = (byte)((xPosition == 0) ? 1 : (xPosition == this.allGameInformation.getBoardInfo().length() - 1) ? -1 : 0);
                        byte yVelocity = (byte)((yPosition == 0) ? 1 : (yPosition == this.allGameInformation.getBoardInfo().height() - 1) ? -1 : 0);
                        this.allGameInformation.getThread().getAllAntContainer().addElement(this.selectedAnt.getAntInstantiater().instantiate(xPosition, yPosition, xVelocity, yVelocity, this.allGameInformation.getPlayerTurnInformation().getTurn()));
                        this.displayAnts();
                        this.initializeOptions();
                    }
                });
                HBox hBox = new HBox(imageView);
                this.imageViewGrid[i][j] = imageView;
                double width = this.grid.getPrefWidth()/this.allGameInformation.getBoardInfo().length();
                double height = this.grid.getPrefHeight()/this.allGameInformation.getBoardInfo().height();
                imageView.setFitHeight(height);
                imageView.setFitWidth(width);
                hBox.setMinHeight(height);
                hBox.setMaxHeight(height);
                hBox.setMaxWidth(width);
                hBox.setMinWidth(width);
                hBox.setAlignment(Pos.CENTER);
                this.grid.add(hBox, j, i);
            }
        }
        this.updateScoreGraph();
        this.displayAnts();
        this.grid.setGridLinesVisible(true);
    }

    public void updateGrid(){
        GridUpdatingThread thread = this.allGameInformation.getThread();
        thread.getAllAntContainer().updateElements(this.allGameInformation);
        this.allGameInformation.getAllAreaChangeEffects().updateElements(this.allGameInformation);
        this.displayAnts();
    }

    private void displayAnts(){
        for(byte i = 1; i < this.imageViewGrid[0].length - 1; i++) {for (byte j = 1; j < this.imageViewGrid.length - 1; j++) {this.setImagesForGrid(i, j, "newGrass");}}
        GridBoard board = this.allGameInformation.getBoardInfo();
        for(byte i = 0; i < board.length(); i++){for(byte j = 0; j <= 1; j++){this.imageViewGrid[j * (board.height() - 1)][i].setImage(this.dullBorderImage);}}
        for(byte i = 1; i < board.height() - 1; i++){for(byte j = 0; j <= 1; j++){this.imageViewGrid[i][j * (board.length() - 1)].setImage(this.dullBorderImage);}}
        for(AllAntContainer k : this.allGameInformation.getAllContainers()){for(AntContainer<?> i : k.getAllElementsAsArray()){for(Ants j : i.getElements()){this.setPiecesForGrid(j);}}}
        for(ProtectivePieceContainer<? extends ProtectivePieces> i : this.allGameInformation.getAllProtectivePieces().getAllElementsAsArray()){for(ProtectivePieces j : i.getElements()){this.setImagesForGrid(j.getxPosition(), j.getyPosition(), "Wall");}}
        for(AreaChangeEffectContainer<? extends AreaChangeEffect> i : this.allGameInformation.getAllAreaChangeEffects().getAllElementsAsArray()){for(AreaChangeEffect j : i.getElements()){if(j.isActivated()){this.setImagesForGrid(j.getxPosition(), j.getyPosition(), "download");}}}
    }

    public void onTurnFinished(){
        this.allGameInformation.getPlayerTurnInformation().incrementTurn();
        this.allGameInformation.getPlayerTurnInformation().updatePlayerScores(this.allGameInformation);
        this.selectedAnt = null;
        this.initializeOptions();
        this.updateScoreGraph();
    }

    private void updateScoreGraph(){
        this.scoringGraph.getData().clear();
        XYChart.Series<String, Short> data = new XYChart.Series<>();
        data.getData().clear();
        for(IndividualPlayer i : this.allGameInformation.getPlayerTurnInformation().getPlayers()){data.getData().add(new XYChart.Data<>(i.getName(), i.getTotalPoints()));}
        this.scoringGraph.getData().add(data);
    }

    private void initializeImageHashMaps(){
        this.imagesForGrid = this.createHashMapForDirectory(new File("src/main/resources/Pictures/GridSceneImages"));
        File piecesDirectory = new File("src/main/resources/Pictures/Pieces");
        this.pieces = new HashMap<>();
        for(IndividualPlayer i : this.allGameInformation.getPlayerTurnInformation().getPlayers()){
            HashMap<String, Image> hashMap = new HashMap<>();
            for(File j : piecesDirectory.listFiles()){
                try{
                    Image pieceImage = new Image(new FileInputStream(j));
                    WritableImage writableImage = new WritableImage((int)pieceImage.getWidth(), (int)pieceImage.getHeight());
                    for(int k = 0; k < (int)pieceImage.getWidth(); k++){
                        for(int l = 0; l < (int) pieceImage.getHeight(); l++){
                            Color originalImageColor = pieceImage.getPixelReader().getColor(k, l);
                            writableImage.getPixelWriter().setColor(k, l, originalImageColor.grayscale().interpolate(i.getColor(), 0.2));
                        }
                    }
                    hashMap.put(j.getName().substring(0, j.getName().indexOf('.')), writableImage);
                }catch(FileNotFoundException error){error.printStackTrace();}
            }
            this.pieces.put(i, hashMap);
        }
    }

    private HashMap<String, Image> createHashMapForDirectory(File file){
        if(file.isDirectory()){
            HashMap<String, Image> hashMap = new HashMap<>();
            for(File i : Objects.requireNonNull(file.listFiles())){
                try{hashMap.put(i.getName().substring(0, i.getName().indexOf('.')), new Image(i.getCanonicalPath()));}
                catch (IOException error){error.printStackTrace();}
            }
            return hashMap;
        }else{return null;}
    }

    public void runFlashingBorderThread(int counter){
        GridBoard board = this.allGameInformation.getBoardInfo();
        WritableImage image = new WritableImage(1 , 1);
        image.getPixelWriter().setColor(0, 0, Color.gray(0.05 * Math.sin(Math.TAU/10 * counter) + 0.7));
        for(byte i = 0; i < board.length(); i++){
            for(byte j = 0; j <= 1; j++){
                byte yLocation = (byte)(j * (board.height() - 1));
                if(this.allGameInformation.getBoardInfo().isBorderSegmentUnimpeded(i, yLocation, this.allGameInformation)){this.imageViewGrid[yLocation][i].setImage(image);}
            }
        }
        for(byte i = 1; i < board.height() - 1; i++){
            for(byte j = 0; j <= 1; j++){
                byte xLocation = (byte)(j * (board.length() - 1));
                if(this.allGameInformation.getBoardInfo().isBorderSegmentUnimpeded(xLocation, i, this.allGameInformation)){this.imageViewGrid[i][xLocation].setImage(image);}
            }
        }
    }

    public AllGameInformation getAllGameInformation() {return this.allGameInformation;}

    private void setImagesForGrid(byte xPos, byte yPos, String url){this.imageViewGrid[yPos][xPos].setImage(this.imagesForGrid.get(url));}
    private void setPiecesForGrid(Ants ants){this.imageViewGrid[ants.getyPosition()][ants.getxPosition()].setImage(this.pieces.get(this.allGameInformation.getPlayerTurnInformation().getPlayer(ants.getTeamNumber())).get(ants.getImage()));}

    private ImageView[][] imageViewGrid;
    private HashMap<String, Image> imagesForGrid;
    private HashMap<IndividualPlayer, HashMap<String, Image>> pieces;
    private AllGameInformation allGameInformation;
    private AllPossibleAntsForPurchase selectedAnt;
    private WritableImage dullBorderImage;
}