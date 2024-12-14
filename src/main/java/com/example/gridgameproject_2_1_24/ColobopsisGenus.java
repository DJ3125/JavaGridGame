package com.example.gridgameproject_2_1_24;

public final class ColobopsisGenus extends LocatedRangeAnts{
    public ColobopsisGenus(byte xPosition, byte yPosition, byte xVelocity, byte yVelocity, byte teamNumber, AllPossibleMovements movements){super(xPosition, yPosition, xVelocity, yVelocity, teamNumber, movements, (byte) 3, (byte) 100, Behavior.STOP_AT_WALLS, ExplodingEffect.DESTROY);}
    @Override public String getImage() {return "ExplosiveAnt";}
}