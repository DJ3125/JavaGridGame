package com.example.gridgameproject_2_1_24;

public final class AllProtectors {
    public AllProtectors(){this.wallContainers = new ProtectivePieceContainer<>(Walls.class);}
    public ProtectivePieceContainer<Walls> getWallContainers() {return this.wallContainers;}
    private final ProtectivePieceContainer<Walls> wallContainers;
}
