package com.example.gridgameproject_2_1_24;

public final class RossomyrmexGenus extends LocatedRangeAnts{
    public RossomyrmexGenus(byte xPosition, byte yPosition, byte xVelocity, byte yVelocity, byte teamNumber){super(xPosition, yPosition, xVelocity, yVelocity, teamNumber, AllPossibleMovements.NORMAL, (byte) 2, (byte) 100, Behavior.STOP_AT_WALLS, ExplodingEffect.CONVERT);}
    @Override public String getImage() {return "ExplosiveAnt";}
}