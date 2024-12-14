package com.example.gridgameproject_2_1_24;

abstract public class ExplodingAnts extends Ants{
    public ExplodingAnts(byte xPosition, byte yPosition, byte xVelocity, byte yVelocity, byte teamNumber, AllPossibleMovements behaviors, double range, byte fuse, ExplodingEffect explodingEffect){
        super(xPosition, yPosition, xVelocity, yVelocity, teamNumber, behaviors, true);
        this.range = range;
        this.fuse = new ExplosionFuse(fuse);
        this.explodingEffect = explodingEffect;
    }

    public final double getRange() {return this.range;}
    public final ExplodingEffect getExplodingEffect() {return this.explodingEffect;}
    @Override public final void performOnStopAction(AllGameInformation allGameInformation){}
    @Override public final void performAliveAction(AllGameInformation allGameInformation) {this.fuse.incrementTimer();}
    @Override public final boolean isContinuing(AllGameInformation allGameInformation) {return super.isContinuing(allGameInformation) && !this.fuse.performBehavior();}
    private final double range;
    private final TimeCounter fuse;
    private final ExplodingEffect explodingEffect;
}
