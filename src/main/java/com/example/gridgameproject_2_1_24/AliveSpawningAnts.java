package com.example.gridgameproject_2_1_24;

abstract public class AliveSpawningAnts extends SpawningAnts{
    public AliveSpawningAnts(byte xPosition, byte yPosition, byte xVelocity, byte yVelocity, byte teamNumber, byte interval, double angleDistance, AllPossibleMovements behaviors, AllPossibleAntsForPurchase antToInstantiate){
        super(xPosition, yPosition, xVelocity, yVelocity, teamNumber, behaviors, antToInstantiate);
        this.timer = new SpawnTimer(interval);
        this.angleDistance = angleDistance;
    }

    @Override public final void performDeadAction(AllGameInformation allGameInformation) {}
    @Override public final void performAliveAction(AllGameInformation allGameInformation) {
        this.timer.incrementTimer();
        if(this.timer.performBehavior()){
            double angle = Math.atan((double)-this.getyVelocity()/this.getxVelocity()) + ((this.getxVelocity() < 0) ? Math.PI : 0);
            for(byte i = -1; i <= 1; i+= 2){
                double newAngle = angle + i * this.angleDistance;
                double multiplier = Math.sqrt(2) / ((Math.round(Math.round(Math.pow(10, 4) * (newAngle % (Math.PI/2)))/Math.pow(10, 4)) == 0) ? Math.sqrt(2) : 1);
                byte newX = (byte)(Math.round(Math.round((Math.cos(newAngle) * multiplier) * Math.pow(10, 4))/Math.pow(10,4)) + this.getxPosition());
                byte newY = (byte)(Math.round(Math.round((Math.sin(newAngle) * multiplier) * Math.pow(10, 4))/Math.pow(10,4)) + this.getyPosition());
                if(this.checkIfPlaceIsSafeToSpawn(newX, newY, allGameInformation)){this.spawnAnt(newX, newY, allGameInformation);}
            }
        }
    }
    private final SpawnTimer timer;
    private final double angleDistance;
}
