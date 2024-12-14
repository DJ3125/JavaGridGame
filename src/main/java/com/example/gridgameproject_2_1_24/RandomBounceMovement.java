package com.example.gridgameproject_2_1_24;

import java.util.ArrayList;

public final class RandomBounceMovement extends LocatedMovement {
    public RandomBounceMovement(byte xPosition, byte yPosition, byte xVelocity, byte yVelocity) {super(xPosition, yPosition, xVelocity, yVelocity);}
    @Override public boolean isUnimpeded(AllGameInformation allGameInformation) {
        if (!super.isUnimpeded(allGameInformation)) {
            if (this.counter > 0) {
                this.counter--;
                ArrayList<Double> angles = new ArrayList<>();
                for (byte i = 0; i < 8; i++) {if(this.checkIfViablePlace(allGameInformation, i *Math.PI/4)){angles.add(i * Math.PI/4);}}
                double selectedAngle = angles.get((int)Math.floor(Math.random() * angles.size()));
                double multiplier = (Math.abs(selectedAngle % (Math.PI / 2)) < 0.01) ? 1 : Math.sqrt(2);
                byte newX = (byte) (Math.round(multiplier * Math.cos(selectedAngle) * Math.pow(10, 4)) / Math.pow(10, 4));
                byte newY = (byte) (-Math.round(multiplier * Math.sin(selectedAngle) * Math.pow(10, 4)) / Math.pow(10, 4));
                this.setxVelocity(newX);
                this.setyVelocity(newY);
                return true;
            } else {return false;}
        } else {return true;}
    }

    private boolean checkIfViablePlace(AllGameInformation allGameInformation, double angle) {
        double multiplier = (Math.abs(angle % (Math.PI / 2)) < 0.01) ? 1 : Math.sqrt(2);
        byte newX = (byte) (Math.round(multiplier * Math.cos(angle) * Math.pow(10, 4)) / Math.pow(10, 4) + this.getxPosition());
        byte newY = (byte) (-Math.round(multiplier * Math.sin(angle) * Math.pow(10, 4)) / Math.pow(10, 4) + this.getyPosition());
        if(!allGameInformation.getBoardInfo().doesSquareExist(newX, newY) || allGameInformation.getBoardInfo().isSquareOnBorder(newX, newY)){return false;}
        for (AllAntContainer j : allGameInformation.getAllContainers()) {if (j.isElementAtLocation(newX, newY)) {return false;}}
        for(ProtectivePieceContainer<? extends ProtectivePieces> i : allGameInformation.getAllProtectivePieces().getAllElementsAsArray()){if(i.isElementAtLocation(newX, newY)){return false;}}
        return true;
    }

    private byte counter = 3;
}
