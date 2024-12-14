package com.example.gridgameproject_2_1_24;

abstract public class SpiralExplodingAnts extends ExplodingAnts{
    public SpiralExplodingAnts(byte xPosition, byte yPosition, byte xVelocity, byte yVelocity, byte teamNumber, AllPossibleMovements behaviors, double range, byte fuse, ExplodingEffect explodingEffect, Behavior behavior){
        super(xPosition, yPosition, xVelocity, yVelocity, teamNumber, behaviors, range, fuse, explodingEffect);
        this.behavior = behavior.spiralBehavior;
    }
    @Override public final void performDeadAction(AllGameInformation allGameInformation) {this.behavior.performExplosion(this, this.getExplodingEffect(), allGameInformation);}
    private final SpiralAntBehavior behavior;

    public enum Behavior{
        CLOCKWISE(SpiralAntBehavior.CLOCKWISE),
        COUNTER_CLOCKWISE(SpiralAntBehavior.COUNTER_CLOCKWISE),
        BOTH_DIRECTIONS(SpiralAntBehavior.BOTH_DIRECTIONS);
        Behavior(SpiralAntBehavior spiralBehavior){this.spiralBehavior = spiralBehavior;}
        private final SpiralAntBehavior spiralBehavior;
    }

    private enum SpiralAntBehavior {
        CLOCKWISE{@Override public void performExplosion(SpiralExplodingAnts ant, ExplodingEffect explodingEffect, AllGameInformation allGameInformation) {SpiralAntBehavior.createExplosions(ant, explodingEffect, allGameInformation, (byte)1);}},
        COUNTER_CLOCKWISE{@Override public void performExplosion(SpiralExplodingAnts ant, ExplodingEffect explodingEffect, AllGameInformation allGameInformation) {SpiralAntBehavior.createExplosions(ant, explodingEffect, allGameInformation, (byte)-1);}},
        BOTH_DIRECTIONS{
            @Override public void performExplosion(SpiralExplodingAnts ant, ExplodingEffect explodingEffect, AllGameInformation allGameInformation) {
                SpiralAntBehavior.CLOCKWISE.performExplosion(ant, explodingEffect, allGameInformation);
                SpiralAntBehavior.COUNTER_CLOCKWISE.performExplosion(ant, explodingEffect, allGameInformation);
            }
        };
        abstract public void performExplosion(SpiralExplodingAnts ant, ExplodingEffect explodingEffect, AllGameInformation allGameInformation);
        private static void createExplosions(SpiralExplodingAnts ant, ExplodingEffect explodingEffect, AllGameInformation allGameInformation, byte direction){
            for(byte i = 1; i <= ant.getRange(); i++){
                double constant = i * ant.getRange();
                for(byte j = 0; j<=3; j++){
                    byte counter = 0;
                    boolean terminate = false;
                    double initialPoint = direction * j * Math.PI/2, angle = initialPoint;
                    byte formerXPosition = ant.getxPosition(), formerYPosition = ant.getyPosition();
                    while (Math.abs(angle) < 2 * Math.TAU + Math.abs(initialPoint) && !terminate){
                        byte newXPosition = (byte)Math.round(ant.getxPosition() + Math.cos(angle) * constant * (angle - initialPoint)), newYPosition = (byte)Math.round(ant.getyPosition() + Math.sin(angle) * constant * (angle - initialPoint));
                        if(allGameInformation.getBoardInfo().doesSquareExist(newXPosition, newYPosition) && !allGameInformation.getBoardInfo().isSquareOnBorder(newXPosition, newYPosition) && !allGameInformation.getAllProtectivePieces().isElementAtLocation(newXPosition, newYPosition)){
                            if(!(newXPosition == formerXPosition && newYPosition == formerYPosition)){
                                formerXPosition = newXPosition;
                                formerYPosition = newYPosition;
                                explodingEffect.explosionEffect(newXPosition, newYPosition, counter, (byte)8, ant, allGameInformation);
                                counter++;
                            }
                            angle += 0.01 * direction;
                        }else{terminate = true;}
                    }
                }
            }
        }
    }
}
