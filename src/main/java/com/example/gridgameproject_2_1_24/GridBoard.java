package com.example.gridgameproject_2_1_24;


public record GridBoard(byte length, byte height) {
    public boolean doesSquareExist(byte xPos, byte yPos) {return xPos >= 0 && yPos >= 0 && xPos < this.length && yPos < this.height;}
    public boolean isSquareOnBorder(byte xPos, byte yPos) {return this.doesSquareExist(xPos, yPos) && (xPos == 0 || yPos == 0 || xPos == this.length - 1 || yPos == this.height - 1);}
    private byte[] getCoordinatesOfFutureLocation(byte xPos, byte yPos){
        if(!this.isSquareOnBorder(xPos, yPos)){return new byte[]{xPos, yPos};}
        else{
            byte newX = (byte)(xPos + ((xPos == 0) ? 1 : (xPos == this.length - 1) ? -1 : 0));
            byte newY = (byte)(yPos + ((yPos == 0) ? 1 : (yPos == this.height - 1) ? -1 : 0));
            return new byte[]{newX, newY};
        }
    }

    public boolean isBorderSegmentUnimpeded(byte xPosition, byte yPosition, AllGameInformation allGameInformation){
        if(this.isSquareOnBorder(xPosition, yPosition)){
            byte[] futureCoordinates = this.getCoordinatesOfFutureLocation(xPosition, yPosition);
            byte newX = futureCoordinates[0];
            byte newY = futureCoordinates[1];
            for(AllAntContainer i : allGameInformation.getAllContainers()){if(i.isElementAtLocation(newX, newY)){return false;}}
            return !allGameInformation.getAllProtectivePieces().isElementAtLocation(newX, newY);
        }else{return false;}
    }

}