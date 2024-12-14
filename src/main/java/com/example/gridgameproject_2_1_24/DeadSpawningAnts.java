package com.example.gridgameproject_2_1_24;

abstract public class DeadSpawningAnts extends SpawningAnts{
    public DeadSpawningAnts(byte xPosition, byte yPosition, byte xVelocity, byte yVelocity, byte teamNumber, AllPossibleMovements behaviors, AllPossibleAntsForPurchase antToInstantiate){super(xPosition, yPosition, xVelocity, yVelocity, teamNumber, behaviors, antToInstantiate);}
    @Override public final void performAliveAction(AllGameInformation allGameInformation) {}
    @Override public final void performDeadAction(AllGameInformation allGameInformation) {for(byte i = (byte)(this.getxPosition()-1); i <= 1 + this.getxPosition(); i++){for(byte j = (byte)(this.getyPosition()-1); j <= 1 + this.getyPosition(); j++){if(!(j == this.getyPosition() && i == this.getxPosition()) && this.checkIfPlaceIsSafeToSpawn(i , j, allGameInformation)){this.spawnAnt(i, j, allGameInformation);}}}}
}
