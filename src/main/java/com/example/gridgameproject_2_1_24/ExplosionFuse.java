package com.example.gridgameproject_2_1_24;

public final class ExplosionFuse extends TimeCounter{
    public ExplosionFuse(byte fuseTimer){
        super();
        this.fuseTimer = fuseTimer;
    }
    @Override public boolean performBehavior() {return this.getCurrentTime() >= this.fuseTimer;}
    private final byte fuseTimer;
}
