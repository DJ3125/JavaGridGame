package com.example.gridgameproject_2_1_24;

import java.util.ArrayList;
import java.util.Arrays;

public final class HomingMovement extends LocatedMovement{
    public HomingMovement(byte xPosition, byte yPosition, byte xVelocity, byte yVelocity){
        super(xPosition, yPosition, xVelocity, yVelocity);
        this.targetXPosition = this.targetYPosition = 10;
        this.hasTargetBeenSet = false;
        this.hasNoTarget = false;
        this.angle = Math.atan((double)-yVelocity/xVelocity) + ((xVelocity< 0)? Math.PI : 0);
        this.angle = (this.angle + Math.TAU)%Math.TAU;
    }

    @Override public boolean isUnimpeded(AllGameInformation allGameInformation) {
        this.setTarget(allGameInformation);
        boolean value;
        if(!this.hasTargetBeenSet){value = true;}else{value = !(this.targetXPosition == this.getxPosition() && this.targetYPosition == this.getyPosition());}
        System.out.println(this.hasTargetBeenSet + "," + value);
        return super.isUnimpeded(allGameInformation) && value;
    }

    @Override public void move() {
        super.move();
        if(this.hasTargetBeenSet){
            this.distanceToTurn();
            while(this.angle < 0){this.angle += Math.TAU;}
            this.angle %= Math.TAU;
            this.updateVelocitiesBasedOnAngle();
        }
    }

    private void setTarget(AllGameInformation allGameInformation){
        if(!this.hasTargetBeenSet && !this.hasNoTarget){
            ArrayList<Ants> allAnts = new ArrayList<>();
            for(AllAntContainer i : allGameInformation.getAllContainers()){for(AntContainer<? extends Ants> j : i.getAllElementsAsArray()){allAnts.addAll(Arrays.asList(j.getElements()));}}
            byte counter = 0;
            while (counter < allAnts.size()){if(allAnts.get(counter).getxPosition() == this.getxPosition() && allAnts.get(counter).getyPosition() == this.getyPosition()){allAnts.remove(counter);}else{counter++;}}
            if(allAnts.isEmpty()){this.hasNoTarget = true;}
            else{
                Ants selectedAnt = allAnts.get((int)Math.floor(Math.random() * allAnts.size()));
                this.targetXPosition = selectedAnt.getxPosition();
                this.targetYPosition = selectedAnt.getyPosition();
                this.hasTargetBeenSet = true;
            }
        }
    }

    private void updateVelocitiesBasedOnAngle(){
        byte howManyZeros = 4;
        this.setxVelocity((byte)Math.round(Math.round(Math.cos(this.angle) * Math.pow(10, howManyZeros))/Math.pow(10, howManyZeros)));
        this.setyVelocity((byte)Math.round(Math.round(-Math.sin(this.angle) * Math.pow(10, howManyZeros))/Math.pow(10, howManyZeros)));
    }

    private double getAngleFromTarget(){
        double ydist = this.targetYPosition - this.getyPosition();
        ydist *= -1;
        double xdist = this.targetXPosition - this.getxPosition();
        double angle = Math.atan(ydist/xdist);
        if(xdist < 0){angle += Math.PI;}
        angle = (angle + Math.TAU)%Math.TAU;
        return angle;
    }

    private void distanceToTurn(){
        double angleDist = this.getAngleFromTarget() - this.angle;
        double minAngle = angleDist - Math.TAU;
        for(byte i = -2; i <= 2; i++){if(Math.abs(minAngle) > Math.abs(angleDist + i * Math.TAU)){minAngle = angleDist + i * Math.TAU;}}
        if(Math.abs(minAngle) > this.turningTime){
            if(minAngle > 0){this.angle += this.turningTime;}
            else if (minAngle < 0) {this.angle -= this.turningTime;}
        }else{this.angle += minAngle;}
    }

    private double angle;
    private byte targetXPosition, targetYPosition;
    private boolean hasTargetBeenSet, hasNoTarget;
    private final double turningTime = .2;
}
