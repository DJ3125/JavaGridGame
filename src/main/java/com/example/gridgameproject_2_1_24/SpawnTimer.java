package com.example.gridgameproject_2_1_24;

public final class SpawnTimer extends TimeCounter{
    public SpawnTimer(byte spawnRate){
        super();
        this.spawnRate = spawnRate;
    }
    @Override public boolean performBehavior() {return this.getCurrentTime() % this.spawnRate == 0 && this.getCurrentTime() > 0;}
    private final byte spawnRate;
}
