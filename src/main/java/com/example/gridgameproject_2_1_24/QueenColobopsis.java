package com.example.gridgameproject_2_1_24;

public final class QueenColobopsis extends DeadSpawningAnts{
    public QueenColobopsis(byte xPosition, byte yPosition, byte xVelocity, byte yVelocity, byte teamNumber){super(xPosition, yPosition, xVelocity, yVelocity, teamNumber, AllPossibleMovements.NORMAL, AllPossibleAntsForPurchase.EXPLODE);}
    @Override public String getImage() {return "Queen";}
}
