package com.example.gridgameproject_2_1_24;

public enum ExplodingEffect {
    DESTROY{@Override public void explosionEffect(byte xPosition, byte yPosition, double distance, byte lastingTime, ExplodingAnts ant, AllGameInformation allGameInformation) {allGameInformation.getAllAreaChangeEffects().addElement(new Explosion(xPosition, yPosition, lastingTime, (byte)(Math.floor(distance))));}},
    CONVERT{@Override public void explosionEffect(byte xPosition, byte yPosition, double distance, byte lastingTime, ExplodingAnts ant, AllGameInformation allGameInformation) {allGameInformation.getAllAreaChangeEffects().addElement(new ChangePheromoneGas(xPosition, yPosition, lastingTime, (byte)(Math.floor(distance)), ant.getTeamNumber()));}};
    public abstract void explosionEffect(byte xPosition, byte yPosition, double distance, byte lastingTime, ExplodingAnts ant, AllGameInformation allGameInformation);
}
