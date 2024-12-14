package com.example.gridgameproject_2_1_24;

abstract public class AreaChangeEffect extends Located {
    public AreaChangeEffect(byte xPosition, byte yPosition, byte lastingTime, byte delay){
        super(xPosition, yPosition);
        this.activationTimer = new ActivationTimer(delay, lastingTime);
    }
    public final void updateEffect(AllGameInformation allGameInformation){this.activationTimer.incrementTimer();}
    public final char performExplosion(AllGameInformation allGameInformation){
        if(this.isActivated()){
            for(AllAntContainer i : allGameInformation.getAllContainers()){for(AntContainer<?> j : i.getAllElementsAsArray()){for(Ants k : j.getElementsAtLocation(this.getxPosition(), this.getyPosition())){this.effectToEnforceHelper(k, j, allGameInformation);}}}
            return 'A'; //Appear
        }else if(this.activationTimer.hasFuseNotExpired()){return 'I';}//Initializing
        else{return 'E';}//Ended
    }

    private <T extends Ants> void effectToEnforceHelper(T ants, AntContainer<?> container, AllGameInformation allGameInformation){this.effectToEnforce(ants, (AntContainer<T>) container, allGameInformation);}
    abstract public <T extends Ants> void effectToEnforce(T ant, AntContainer<T> antContainer, AllGameInformation allGameInformation);
    public final boolean isActivated(){return this.activationTimer.performBehavior();}
    private final ActivationTimer activationTimer;
}
