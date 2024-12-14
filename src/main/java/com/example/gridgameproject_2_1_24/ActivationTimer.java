package com.example.gridgameproject_2_1_24;

public final class ActivationTimer extends TimeCounter{
    public ActivationTimer(byte delay, byte effectLasting){
        super();
        this.lowerBound = delay;
        this.upperBound = (byte)(delay + effectLasting);
    }
    @Override public boolean performBehavior() {return this.lowerBound < this.getCurrentTime() && this.hasFuseNotExpired();}
    public boolean hasFuseNotExpired(){return this.getCurrentTime() <= this.upperBound;}
    private final byte lowerBound, upperBound;
}
