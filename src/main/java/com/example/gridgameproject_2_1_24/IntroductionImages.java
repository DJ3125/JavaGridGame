package com.example.gridgameproject_2_1_24;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public final class IntroductionImages {
    public IntroductionImages(short initialXPosition, short initialYPosition, double angle, String image){
        this.angle  = angle;
        try {
            this.image = new Image(new FileInputStream(image));
            this.xPosition = (short)(initialXPosition - this.image.getWidth()/2);
            this.yPosition = (short)(initialYPosition - this.image.getHeight()/2);
        }catch (FileNotFoundException error) {error.printStackTrace();}
    }

    public short getXPosition() {return this.xPosition;}
    public short getYPosition() {return this.yPosition;}

    public void updatePosition(double counter) {
        double counterFunction = 8/Math.pow(counter + 1.01, 1);
        this.xPosition += (short)(Math.cos(this.angle) * counterFunction);
        this.yPosition += (short)(Math.sin(this.angle) * counterFunction);
    }

    public Image getImage() {return this.image;}

    private short xPosition, yPosition;
    private Image image;
    private final double angle;
}
