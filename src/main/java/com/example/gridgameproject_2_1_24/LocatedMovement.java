package com.example.gridgameproject_2_1_24;

abstract public class LocatedMovement{
    public LocatedMovement(byte xPosition, byte yPosition, byte xVelocity, byte yVelocity){
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
    }
    public final byte getxPosition() {return xPosition;}
    public final byte getxVelocity() {return xVelocity;}
    public final byte getyPosition() {return yPosition;}
    public final byte getyVelocity() {return yVelocity;}
    public final void setxPosition(byte xPosition) {this.xPosition = xPosition;}
    public final void setxVelocity(byte xVelocity) {this.xVelocity = xVelocity;}
    public final void setyPosition(byte yPosition) {this.yPosition = yPosition;}
    public final void setyVelocity(byte yVelocity) {this.yVelocity = yVelocity;}
    public final void increaseXPosition(byte xPositionToIncreaseBy){this.xPosition += xPositionToIncreaseBy;}
    public final void increaseYPosition(byte yPositionToIncreaseBy){this.yPosition += yPositionToIncreaseBy;}
    public final byte getFutureXPosition(){return (byte)(this.getxPosition() + this.xVelocity);}
    public final byte getFutureYPosition(){return (byte)(this.getyPosition() + this.yVelocity);}
    public boolean isUnimpeded(AllGameInformation allGameInformation){
        if(this.xVelocity == 0 && this.yVelocity == 0){return true;}
        else if(allGameInformation.getBoardInfo().doesSquareExist(this.getFutureXPosition(), this.getFutureYPosition()) && !allGameInformation.getBoardInfo().isSquareOnBorder(this.getFutureXPosition(), this.getFutureYPosition())){
            for(ProtectivePieceContainer<? extends ProtectivePieces> i : allGameInformation.getAllProtectivePieces().getAllElementsAsArray()){if(i.isElementAtLocation(this.getFutureXPosition(), this.getFutureYPosition())){return false;}}
            for(AllAntContainer k : allGameInformation.getAllContainers()){for(AntContainer<?> i : k.getAllElementsAsArray()){if(i.isElementAtLocation(this.getFutureXPosition(), this.getFutureYPosition())){return false;}}}
            return true;
        }else{return false;}
    }
    public final void stop(){this.xVelocity = this.yVelocity = 0;}
    public void move(){this.increaseXPosition(this.getxVelocity());this.increaseYPosition(this.getyVelocity());}
    private byte xPosition, yPosition, xVelocity, yVelocity;
}
