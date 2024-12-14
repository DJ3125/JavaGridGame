package com.example.gridgameproject_2_1_24;

public final class AllAreaEffects {
    public AllAreaEffects(){
        this.explosions = new AreaChangeEffectContainer<>(Explosion.class);
        this.changingGas = new AreaChangeEffectContainer<>(ChangePheromoneGas.class);
    }

    public AreaChangeEffectContainer<ChangePheromoneGas> getChangingGas() {return this.changingGas;}
    public AreaChangeEffectContainer<Explosion> getExplosions() {return this.explosions;}

    private final AreaChangeEffectContainer<Explosion> explosions;
    private final AreaChangeEffectContainer<ChangePheromoneGas> changingGas;
}
