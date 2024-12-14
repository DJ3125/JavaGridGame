package com.example.gridgameproject_2_1_24;

public final class Explosion extends AreaChangeEffect{
    public Explosion(byte xPosition, byte yPosition, byte lastingTime, byte delay){super(xPosition, yPosition, lastingTime, delay);}
    @Override public <T extends Ants> void effectToEnforce(T ant, AntContainer<T> antContainer, AllGameInformation allGameInformation) {antContainer.remove(ant, allGameInformation);}
}
