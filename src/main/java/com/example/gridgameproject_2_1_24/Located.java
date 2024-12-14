package com.example.gridgameproject_2_1_24;

abstract public class Located {
    public final byte getxPosition() {return this.movement.getxPosition();}
    public final byte getyPosition() {return this.movement.getyPosition();}
    public final byte getxVelocity(){return this.movement.getxVelocity();}
    public final byte getyVelocity(){return this.movement.getyVelocity();}
    public Located(byte xPosition, byte yPosition, byte xVelocity, byte yVelocity, AllPossibleMovements behavior){this.movement = behavior.getInstantiater().instantiate(xPosition, yPosition, xVelocity, yVelocity);}
    public Located(byte xPosition, byte yPosition){this(xPosition, yPosition, (byte)0, (byte)0, AllPossibleMovements.NONE);}
    public boolean isContinuing(AllGameInformation allGameInformation){return this.movement.isUnimpeded(allGameInformation);}
    public void stopElement(AllGameInformation allGameInformation){this.movement.stop();}
    public void updateElement(AllGameInformation allGameInformation){this.movement.move();}
    private final LocatedMovement movement;

}
