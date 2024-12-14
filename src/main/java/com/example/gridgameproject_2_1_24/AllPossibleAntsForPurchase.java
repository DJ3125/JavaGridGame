package com.example.gridgameproject_2_1_24;

public enum AllPossibleAntsForPurchase {
    CONVERT(new Instantiater<>(RossomyrmexGenus.class, (Object[]) null), 30, "Converter Ant"),
    LASER(new Instantiater<>(FormicaRufaSpecies.class, (Object[]) null), 50, "Wood Ant"),
    POINTS(new Instantiater<>(MyrmecocystusGenus.class, AllPossibleMovements.NORMAL), 0, "Scoring Ant"),
    BOUNCING_POINTS(new Instantiater<>(MyrmecocystusGenus.class, AllPossibleMovements.RANDOM_BOUNCE), 10, "Bouncing Scorer"),
    SPIRAL(new Instantiater<>(AphaenogasterGenus.class), 60, "Spiral Exploding Ant"),
    EXPLODE(new Instantiater<>(ColobopsisGenus.class, AllPossibleMovements.NORMAL), 50, "Explosion Ant"),
    HOMING_EXPLODE(new Instantiater<>(ColobopsisGenus.class, AllPossibleMovements.HOMING), 70, "Homing Explosion Ant"),
    EXPLOSION_SPAWNER(new Instantiater<>(QueenColobopsis.class, (Object[]) null), 100, "Spawning Explosion"),
    POINT_SPAWNER(new Instantiater<>(QueenMyrmecocystus.class, (Object[]) null), 500, "Spawning Points");

    AllPossibleAntsForPurchase(Instantiater<? extends Ants> antInstantiater, int cost, String name){
        this.antInstantiater = antInstantiater;
        this.cost =  cost;
        this.name = name;
    }
    public Instantiater<? extends Ants> getAntInstantiater() {return this.antInstantiater;}
    public int getCost() {return this.cost;}
    public String getName() {return this.name;}

    private final Instantiater<? extends Ants> antInstantiater;
    private final String name;
    private final int cost;
}
