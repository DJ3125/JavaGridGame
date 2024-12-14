package com.example.gridgameproject_2_1_24;

import java.util.ArrayList;

public abstract class LocatedRangeAnts extends ExplodingAnts{
    public LocatedRangeAnts(byte xPosition, byte yPosition, byte xVelocity, byte yVelocity, byte teamNumber, AllPossibleMovements behaviors, byte range, byte fuseTime, Behavior antExplosionConditions, ExplodingEffect explodingEffect){
        super(xPosition, yPosition, xVelocity, yVelocity, teamNumber, behaviors, range * Math.sqrt(2), fuseTime, explodingEffect);
        this.antExplosionConditions = antExplosionConditions.circularBehavior;
    }

    @Override public final void performDeadAction(AllGameInformation allGameInformation) {
        for(byte i = (byte)Math.max(1, this.getxPosition() - this.getRange()); i <= (byte)Math.min(allGameInformation.getBoardInfo().length() - 2, this.getxPosition() + this.getRange()); i++){
            for(byte j = (byte)Math.max(1, this.getyPosition() - this.getRange()); j <= (byte)Math.min(allGameInformation.getBoardInfo().height() - 2, this.getyPosition() + this.getRange()); j++){
                double distance = Math.sqrt(Math.pow(i - this.getxPosition(), 2) + Math.pow(j - this.getyPosition(), 2));
                if(this.antExplosionConditions.hasExploded(this, i, j, allGameInformation)){this.getExplodingEffect().explosionEffect(i, j, distance, (byte)2, this, allGameInformation);}
            }
        }
    }
    private final CircularAntExplosionConditions antExplosionConditions;


    public enum Behavior{
        STOP_AT_WALLS(CircularAntExplosionConditions.STOP_AT_WALLS),
        IGNORE_WALLS(CircularAntExplosionConditions.IGNORE_WALLS);
        Behavior(CircularAntExplosionConditions circularBehavior){this.circularBehavior = circularBehavior;}
        private final CircularAntExplosionConditions circularBehavior;
    }

    private enum CircularAntExplosionConditions {
        IGNORE_WALLS{@Override public boolean hasExploded(LocatedRangeAnts ants, byte areaToPlaceX, byte areaToPlaceY, AllGameInformation allGameInformation) {return CircularAntExplosionConditions.getDistanceBetweenElements(ants, areaToPlaceX, areaToPlaceY) < ants.getRange();}},
        STOP_AT_WALLS{
            @Override public boolean hasExploded(LocatedRangeAnts ants, byte areaToPlaceX, byte areaToPlaceY, AllGameInformation allGameInformation) {
                if(CircularAntExplosionConditions.IGNORE_WALLS.hasExploded(ants, areaToPlaceX, areaToPlaceY, allGameInformation)){
                    ArrayList<ProtectivePieces> walls = new ArrayList<>();
                    double distanceFromAntToPoint = CircularAntExplosionConditions.getDistanceBetweenElements(ants, areaToPlaceX, areaToPlaceY);
                    for(ProtectivePieceContainer<? extends ProtectivePieces> i : allGameInformation.getAllProtectivePieces().getAllElementsAsArray()){for(ProtectivePieces j : i.getElements()){if(CircularAntExplosionConditions.getDistanceBetweenElements(j, ants) <= distanceFromAntToPoint){walls.add(j);}}}
                    double[][] wallAngles = new double[walls.size()][];
                    for(byte i = 0; i < walls.size(); i++){
                        ProtectivePieces current = walls.get(i);
                        double[] angles = new double[4];
                        double wallAngle = CircularAntExplosionConditions.getAngle(ants, current.getxPosition(), current.getyPosition());
                        for(byte j = 0; j < 4; j++){
                            double[] position = CircularAntExplosionConditions.getLocationOfCorner(current.getxPosition(), current.getyPosition(), j);
                            angles[j] = CircularAntExplosionConditions.getAngle(ants, position[0], position[1]) - wallAngle;
                            if(Math.abs(angles[j]) > Math.PI){angles[j] -= Math.TAU;}
                        }
                        double[] minAndMax = {angles[0], angles[0]};
                        for(byte j = 0; j < 4; j++){
                            minAndMax[0] = Math.min(minAndMax[0], angles[j]);
                            minAndMax[1] = Math.max(minAndMax[1], angles[j]);
                        }
                        minAndMax[0] += wallAngle;
                        minAndMax[1] += wallAngle;
                        wallAngles[i] = minAndMax;
                    }
                    boolean[] cornersCovered = new boolean[4];
                    boolean centerCovered = false;
                    for(double[] i : wallAngles){
                        for(byte j = 0; j < 4; j++){
                            double[] position = CircularAntExplosionConditions.getLocationOfCorner(areaToPlaceX, areaToPlaceY, j);
                            if(!cornersCovered[j] && CircularAntExplosionConditions.getIfAngleIsBetween(ants, i[0], i[1], position[0], position[1])){cornersCovered[j] = true;}
                        }
                        if(!centerCovered && CircularAntExplosionConditions.getIfAngleIsBetween(ants, i[0], i[1], areaToPlaceX, areaToPlaceY)){centerCovered = true;}
                        byte counter = 0;
                        for(boolean j : cornersCovered){if(j){counter++;}}
                        if(counter > 2 || (counter == 2 && centerCovered)){return false;}
                    }
                    return true;
                }
                return false;
            }
        };

        public abstract boolean hasExploded(LocatedRangeAnts ants, byte areaToPlaceX, byte areaToPlaceY, AllGameInformation allGameInformation);
        private static double getDistanceBetweenElements(Located element, byte areaToPlaceX, byte areaToPlaceY){return Math.sqrt(Math.pow((element.getxPosition() - areaToPlaceX), 2) + Math.pow((element.getyPosition() - areaToPlaceY), 2));}
        private static double getDistanceBetweenElements(Located element, Located element2){return CircularAntExplosionConditions.getDistanceBetweenElements(element, element2.getxPosition(), element2.getyPosition());}
        private static boolean getIfAngleIsBetween(Located element, double min, double max, double xPosition, double yPosition){
            double angle = CircularAntExplosionConditions.getAngle(element, xPosition, yPosition);
            for(byte i = -1; i <= 1; i++){
                double tempAngle = angle + Math.TAU * i;
                if(min <= tempAngle && max >= tempAngle){return true;}
            }
            return false;
        }

        private static double getAngle(Located element, double xPosition, double yPosition){
            double ydist = yPosition - element.getyPosition();
            ydist *= -1;
            double xdist = xPosition - element.getxPosition();
            double angle = Math.atan((ydist)/(xdist));
            if(xdist < 0){angle += Math.PI;}
            angle = (angle + Math.TAU)%Math.TAU;
            return angle;
        }
        private static double[] getLocationOfCorner(byte xPosition, byte yPosition, byte index){return new double[]{xPosition + Math.sqrt(0.5) * Math.cos(Math.PI/4 + Math.PI/2 * index),yPosition + Math.sqrt(0.5) * Math.sin(Math.PI/4 + Math.PI/2 * index)};}
    }
}
