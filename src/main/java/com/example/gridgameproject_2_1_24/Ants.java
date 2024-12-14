package com.example.gridgameproject_2_1_24;

abstract public class Ants extends Located{
    public Ants(byte xPosition, byte yPosition, byte xVelocity, byte yVelocity, byte teamNumber, AllPossibleMovements behaviors, boolean deletable){
        super(xPosition, yPosition, xVelocity, yVelocity, behaviors);
        this.teamNumber = teamNumber;
        this.deletable = deletable;
    }

    public final byte getTeamNumber() {return this.teamNumber;}
    public final void setTeamNumber(byte newTeamNumber){this.teamNumber = newTeamNumber;}

    public abstract void performAliveAction(AllGameInformation allGameInformation);
    public abstract void performOnStopAction(AllGameInformation allGameInformation);
    public abstract void performDeadAction(AllGameInformation allGameInformation);
    public abstract String getImage();
    public final void updateElement(AllGameInformation allGameInformation){
        super.updateElement(allGameInformation);
        this.performAliveAction(allGameInformation);
    }
    public final boolean isDeletable() {return this.deletable;}
    @Override public final void stopElement(AllGameInformation allGameInformation){
        super.stopElement(allGameInformation);
        this.performOnStopAction(allGameInformation);
    }
    private byte teamNumber;
    private final boolean deletable;
}