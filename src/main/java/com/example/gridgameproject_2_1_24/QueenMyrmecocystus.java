package com.example.gridgameproject_2_1_24;

public class QueenMyrmecocystus extends AliveSpawningAnts{
    public QueenMyrmecocystus(byte xPosition, byte yPosition, byte xVelocity, byte yVelocity, byte teamNumber){super(xPosition, yPosition, xVelocity, yVelocity, teamNumber, (byte)4, Math.PI/2, AllPossibleMovements.NORMAL, AllPossibleAntsForPurchase.POINTS);}
    @Override public String getImage() {return "Queen";}
}
