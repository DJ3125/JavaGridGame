package com.example.gridgameproject_2_1_24;

abstract public class SpawningAnts extends Ants{
    public SpawningAnts(byte xPosition, byte yPosition, byte xVelocity, byte yVelocity, byte teamNumber, AllPossibleMovements behaviors, AllPossibleAntsForPurchase antToInstantiate){
        super(xPosition, yPosition, xVelocity, yVelocity, teamNumber, behaviors, true);
        this.antToInstantiate = antToInstantiate;
    }

    @Override public final void performOnStopAction(AllGameInformation allGameInformation) {}
    public final boolean checkIfPlaceIsSafeToSpawn(byte newX, byte newY, AllGameInformation allGameInformation){
        if(!allGameInformation.getBoardInfo().doesSquareExist(newX, newY) || allGameInformation.getBoardInfo().isSquareOnBorder(newX, newY)){return false;}
        for (AllAntContainer j : allGameInformation.getAllContainers()) {if (j.isElementAtLocation(newX, newY)) {return false;}}
        for(ProtectivePieceContainer<? extends ProtectivePieces> i : allGameInformation.getAllProtectivePieces().getAllElementsAsArray()){if(i.isElementAtLocation(newX, newY)){return false;}}
        return true;
    }
    public final void spawnAnt(byte xPosition, byte yPosition, AllGameInformation allGameInformation){
        System.out.println("spawn");
        allGameInformation.getThread().getAllAntContainer().addElement(this.antToInstantiate.getAntInstantiater().instantiate(xPosition, yPosition, (byte)(xPosition - this.getxPosition()), (byte)(yPosition - this.getyPosition()), this.getTeamNumber()));}

    private final AllPossibleAntsForPurchase antToInstantiate;
}
