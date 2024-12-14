package com.example.gridgameproject_2_1_24;

public final class ChangePheromoneGas extends AreaChangeEffect{
    public ChangePheromoneGas(byte xPosition, byte yPosition, byte lastingTime, byte delay, byte indexToChangeTo){
        super(xPosition, yPosition, lastingTime, delay);
        this.indexToChangeTo = indexToChangeTo;
    }
    @Override public <T extends Ants> void effectToEnforce(T ant, AntContainer<T> antContainer, AllGameInformation allGameInformation) {ant.setTeamNumber(this.indexToChangeTo);}
    private final byte indexToChangeTo;
}
