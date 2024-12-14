package com.example.gridgameproject_2_1_24;

abstract public class LinearExplodingAnts extends ExplodingAnts{
    public LinearExplodingAnts(byte xPosition, byte yPosition, byte xVelocity, byte yVelocity, byte teamNumber, AllPossibleMovements behaviors, double range, byte fuse, ExplodingEffect explodingEffect, Behavior pattern){
        super(xPosition, yPosition, xVelocity, yVelocity, teamNumber, behaviors, range, fuse, explodingEffect);
        this.pattern = pattern.linearPattern;
    }

    @Override public final void performDeadAction(AllGameInformation allGameInformation) {this.pattern.explosionPattern(this, this.getExplodingEffect(), allGameInformation);}
    private final LinearExplosionPatterns pattern;
    
    public enum Behavior{
        ALL_OVER(LinearExplosionPatterns.ALL_DIRECTIONS),
        DIAGONAL(LinearExplosionPatterns.DIAGONAL),
        PLUS(LinearExplosionPatterns.PLUS_SIGN),
        HORIZONTAL(LinearExplosionPatterns.HORIZONTAL),
        VERTICAL(LinearExplosionPatterns.VERTICAL);
        Behavior(LinearExplosionPatterns linearPattern){this.linearPattern = linearPattern;}
        private final LinearExplosionPatterns linearPattern;
    }
    private enum LinearExplosionPatterns {
        VERTICAL{@Override public void explosionPattern(LinearExplodingAnts ant, ExplodingEffect explodingEffect, AllGameInformation allGameInformation) {LinearExplosionPatterns.createHorizontalOrVerticalExplosionLine(ant, explodingEffect, allGameInformation, 0);}},
        HORIZONTAL{@Override public void explosionPattern(LinearExplodingAnts ant, ExplodingEffect explodingEffect, AllGameInformation allGameInformation) {LinearExplosionPatterns.createHorizontalOrVerticalExplosionLine(ant, explodingEffect, allGameInformation, Math.PI/2);}},
        PLUS_SIGN{
            @Override public void explosionPattern(LinearExplodingAnts ant, ExplodingEffect explodingEffect, AllGameInformation allGameInformation) {
                LinearExplosionPatterns.HORIZONTAL.explosionPattern(ant, explodingEffect, allGameInformation);
                LinearExplosionPatterns.VERTICAL.explosionPattern(ant, explodingEffect, allGameInformation);
            }
        },

        DIAGONAL{
            @Override public void explosionPattern(LinearExplodingAnts ant, ExplodingEffect explodingEffect, AllGameInformation allGameInformation){
                byte[] directions = new byte[4];
                for(byte i = 0; i < 4; i++){
                    double[] angles = LinearExplosionPatterns.getCosAndSinFromAngle(i * Math.PI/2);
                    directions[i] = LinearExplosionPatterns.getDistanceInDirection(ant, angles[0], angles[1], allGameInformation);
                }
                LinearExplosionPatterns.createExplosion(ant, 0, directions, Math.PI/4, explodingEffect, allGameInformation);
            }
        },

        ALL_DIRECTIONS{
            @Override public void explosionPattern(LinearExplodingAnts ant, ExplodingEffect explodingEffect, AllGameInformation allGameInformation) {
                LinearExplosionPatterns.DIAGONAL.explosionPattern(ant, explodingEffect, allGameInformation);
                LinearExplosionPatterns.PLUS_SIGN.explosionPattern(ant, explodingEffect, allGameInformation);
            }
        };
        abstract public void explosionPattern(LinearExplodingAnts ant, ExplodingEffect explodingEffect, AllGameInformation allGameInformation);
        private static void createHorizontalOrVerticalExplosionLine(LinearExplodingAnts ant, ExplodingEffect explodingEffect, AllGameInformation allGameInformation, double angle){
            byte[] direction = new byte[2];
            for(byte i = 0; i <= 1; i++){
                double[] angles = LinearExplosionPatterns.getCosAndSinFromAngle(angle + Math.PI * i);
                double cos = angles[0];
                double sin = angles[1];
                direction[i] = LinearExplosionPatterns.getDistanceInDirection(ant, cos, sin, allGameInformation);
            }
            LinearExplosionPatterns.createExplosion(ant, angle, direction, Math.PI/2, explodingEffect, allGameInformation);
        }

        private static byte getDistanceInDirection(LinearExplodingAnts ant, double cos, double sin, AllGameInformation allGameInformation){
            byte counter = 1;
            byte xPosition = (byte)(ant.getxPosition() + cos * counter);
            byte yPosition = (byte)(ant.getyPosition() + sin * counter);
            while(counter < ant.getRange() && allGameInformation.getBoardInfo().doesSquareExist(xPosition, yPosition) && !allGameInformation.getBoardInfo().isSquareOnBorder(xPosition, yPosition) && !allGameInformation.getAllProtectivePieces().isElementAtLocation(xPosition, yPosition)){
                counter++;
                xPosition = (byte)(ant.getxPosition() + cos * counter);
                yPosition = (byte)(ant.getyPosition() + sin * counter);
            }
            return (byte)(counter - 1);
        }

        private static double[] getCosAndSinFromAngle(double angle){
            byte howManyTens = 4;
            return new double[]{Math.round(Math.cos(angle) * Math.pow(10, howManyTens))/Math.pow(10, howManyTens), Math.round(Math.sin(angle) * Math.pow(10, howManyTens))/Math.pow(10, howManyTens)};
        }

        private static void createExplosionsInLine(LinearExplodingAnts ant, double baseAngle, double angleDistance, byte distanceFromCenter, byte baseXPosition, byte baseYPosition, ExplodingEffect explodingEffect, AllGameInformation allGameInformation){
            for(byte k = -1; k <= 1; k+=2){
                byte counter = 1;
                byte cosChange, sinChange;
                {
                    double[] modifiedAngles = LinearExplosionPatterns.getCosAndSinFromAngle(baseAngle + angleDistance * k);
                    byte howManyZeros = 4;
                    double multiplier = (Math.round((angleDistance/Math.PI*4) * Math.pow(10, howManyZeros))/Math.pow(10, howManyZeros) == 1) ? Math.sqrt(2) : 1;
                    cosChange = (byte)(Math.round(multiplier * modifiedAngles[0] * Math.pow(10, howManyZeros))/Math.pow(10, howManyZeros));
                    sinChange = (byte)(Math.round(multiplier * modifiedAngles[1] * Math.pow(10, howManyZeros))/Math.pow(10, howManyZeros));
                }
                byte xPosition = (byte)(baseXPosition + counter * cosChange);
                byte yPosition = (byte)(baseYPosition + counter * sinChange);
                while(allGameInformation.getBoardInfo().doesSquareExist(xPosition, yPosition) && !allGameInformation.getBoardInfo().isSquareOnBorder(xPosition, yPosition) && !allGameInformation.getAllProtectivePieces().isElementAtLocation(xPosition, yPosition)){
                    explodingEffect.explosionEffect(xPosition, yPosition, distanceFromCenter + counter, (byte)2, ant, allGameInformation);
                    counter++;
                    xPosition = (byte)(baseXPosition + counter * cosChange);
                    yPosition = (byte)(baseYPosition + counter * sinChange);
                }
            }
        }

        private static void createExplosion(LinearExplodingAnts ant, double baseAngle, byte[] directions, double angleDistance, ExplodingEffect explodingEffect, AllGameInformation allGameInformation){
            for(byte i = 0; i < 2; i++){LinearExplosionPatterns.createExplosionsInLine(ant,i * Math.PI + baseAngle, angleDistance, (byte)0, ant.getxPosition(), ant.getyPosition(), explodingEffect, allGameInformation);}
            for(byte i = 0; i < directions.length; i++){
                double newBaseAngle = i * Math.PI/directions.length * 2 + baseAngle;
                double[] angles = LinearExplosionPatterns.getCosAndSinFromAngle(newBaseAngle);
                for(byte j = 1; j <= directions[i]; j++){
                    byte baseXPosition = (byte)(angles[0] * j + ant.getxPosition());
                    byte baseYPosition = (byte)(angles[1] * j + ant.getyPosition());
                    explodingEffect.explosionEffect(baseXPosition, baseYPosition, j, (byte)2, ant, allGameInformation);
                    LinearExplosionPatterns.createExplosionsInLine(ant, newBaseAngle, angleDistance, j, baseXPosition, baseYPosition, explodingEffect, allGameInformation);
                }
            }
        }
    }
}
