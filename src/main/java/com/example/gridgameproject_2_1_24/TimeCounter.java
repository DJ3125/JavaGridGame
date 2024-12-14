package com.example.gridgameproject_2_1_24;

abstract public class TimeCounter {
    public TimeCounter(){this.currentTime = 0;}
    public final byte getCurrentTime() {return this.currentTime;}
    abstract public boolean performBehavior();
    public final void incrementTimer(){this.currentTime++;}
    private byte currentTime;
}
