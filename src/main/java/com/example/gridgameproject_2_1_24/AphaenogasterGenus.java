package com.example.gridgameproject_2_1_24;

public final class AphaenogasterGenus extends SpiralExplodingAnts{
    public AphaenogasterGenus(byte xPosition, byte yPosition, byte xVelocity, byte yVelocity, byte teamNumber){super(xPosition, yPosition, xVelocity, yVelocity, teamNumber, AllPossibleMovements.NORMAL, 2, (byte)4, ExplodingEffect.DESTROY, Behavior.BOTH_DIRECTIONS);}
    @Override public String getImage() {return "ExplosiveAnt";}
}
