package com.example.gridgameproject_2_1_24;

public final class MyrmecocystusGenus extends ScoringAnts{
    public MyrmecocystusGenus(byte xPosition, byte yPosition, byte xVelocity, byte yVelocity, byte teamNumber, AllPossibleMovements movement){super(xPosition, yPosition, xVelocity, yVelocity, teamNumber, movement, (byte) 1);}
    @Override public void performAliveAction(AllGameInformation allGameInformation) {}
    @Override public String getImage() {return "HoneyPot";}
}
