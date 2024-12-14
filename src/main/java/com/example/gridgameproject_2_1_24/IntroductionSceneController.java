package com.example.gridgameproject_2_1_24;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public final class IntroductionSceneController {
    @FXML private VBox parentVBox;
    @FXML private Canvas animationCanvas;

    public void initializeIntroduction(){
        this.ctx = this.animationCanvas.getGraphicsContext2D();
        this.introductionImages = new IntroductionImages[2];
        this.introductionImages[0] = new IntroductionImages((short)-100, (short)(this.animationCanvas.getHeight()/2), 0, "src/main/resources/Pictures/Pieces/ExplosiveAnt.jpg");
        this.introductionImages[1] = new IntroductionImages((short)(this.animationCanvas.getWidth() + 100), (short)(this.animationCanvas.getHeight()/2), Math.PI, "src/main/resources/Pictures/Pieces/HoneyPot.jpg");
        IntroductionSceneController controller = this;
        this.animationThread = new Thread("animationThread"){
            @Override public void run() {
                int counter = 0;
                int interval = 25;
                long start = System.currentTimeMillis();
                while(counter < 70){
                    while((int)((System.currentTimeMillis() - start)/interval) == counter){Thread.onSpinWait();}
                    counter = (int)((System.currentTimeMillis() - start)/interval);
                    controller.update(counter * 50/1000.0);
                }
                controller.ctx.fillText("Start", controller.animationCanvas.getWidth()/2, controller.animationCanvas.getHeight() - 140);
            }
        };
        this.animationThread.start();
    }

    private void update(double counter){
        this.ctx.clearRect(0, 0, this.animationCanvas.getWidth(), this.animationCanvas.getHeight());
        this.ctx.setTextAlign(TextAlignment.CENTER);
        this.ctx.fillText("Dylan's Grid Game Bonanza!", this.animationCanvas.getWidth()/2, -100 + counter * 1000/50.0 * 2);
        for(IntroductionImages i : this.introductionImages){
            i.updatePosition(counter);
            this.ctx.save();
            this.ctx.translate(i.getXPosition() + i.getImage().getWidth()/2, i.getYPosition() + i.getImage().getHeight()/2);
            this.ctx.scale(0.25, 0.25);
            this.ctx.drawImage(i.getImage(), -i.getImage().getWidth()/2, -i.getImage().getHeight()/2);
            this.ctx.restore();
        }
    }

    @FXML public void handleMouseClick(MouseEvent mouseEvent){
        if(!this.animationThread.isAlive() && Math.abs(mouseEvent.getX() - this.animationCanvas.getWidth()/2) < 20 && Math.abs(mouseEvent.getY() - this.animationCanvas.getHeight() + 140) < 20){
            SceneDirector.switchScenes(Scenes.PLAYER_SELECTION_SCENE);
            ((PlayerSelectionController)(Scenes.PLAYER_SELECTION_SCENE.getFxmlLoader().getController())).initializePlayerSelection();
        }
    }


    private Thread animationThread;
    private IntroductionImages[] introductionImages;
    private GraphicsContext ctx;
}
