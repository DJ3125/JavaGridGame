package com.example.gridgameproject_2_1_24;

public final class FormicaRufaSpecies extends LinearExplodingAnts{
    public FormicaRufaSpecies(byte xPosition, byte yPosition, byte xVelocity, byte yVelocity, byte teamNumber){super(xPosition, yPosition, xVelocity, yVelocity, teamNumber, AllPossibleMovements.NORMAL, (byte)3, (byte) 3, ExplodingEffect.DESTROY, Behavior.ALL_OVER);}
    @Override public String getImage() {return "ExplosiveAnt";}
}
