package com.example.gridgameproject_2_1_24;

public final class NoMovement extends LocatedMovement{
    public NoMovement(byte xPosition, byte yPosition){super(xPosition, yPosition, (byte)0, (byte)0);}
    public NoMovement(byte xPosition, byte yPosition, byte xVelocity, byte yVelocity){this(xPosition, yPosition);}
    @Override public boolean isUnimpeded(AllGameInformation allGameInformation) {return true;}
    @Override public void move() {}
}
