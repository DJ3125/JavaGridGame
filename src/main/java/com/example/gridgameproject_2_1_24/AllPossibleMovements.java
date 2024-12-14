package com.example.gridgameproject_2_1_24;

public enum AllPossibleMovements {
    RANDOM_BOUNCE(new Instantiater<>(RandomBounceMovement.class)),
    HOMING(new Instantiater<>(HomingMovement.class)),
    NORMAL(new Instantiater<>(NormalMovement.class)),
    NONE(new Instantiater<>(NoMovement.class));
    AllPossibleMovements(Instantiater<? extends LocatedMovement> movementInstantiater){this.instantiater = movementInstantiater;}
    public Instantiater<? extends LocatedMovement> getInstantiater() {return this.instantiater;}
    private final Instantiater<? extends LocatedMovement> instantiater;
}
