package com.example.gridgameproject_2_1_24;

abstract public class ScoringAnts extends Ants{
    public ScoringAnts(byte xPosition, byte yPosition, byte xVelocity, byte yVelocity, byte teamNumber, AllPossibleMovements movement, byte pointValue){
        super(xPosition, yPosition, xVelocity, yVelocity, teamNumber, movement, false);
        this.pointValue = pointValue;
    }
    @Override public final void performDeadAction(AllGameInformation allGameInformation){}
    @Override public final void performOnStopAction(AllGameInformation allGameInformation) {}
    public final byte getPointValue() {return this.pointValue;}
    private final byte pointValue;
}
