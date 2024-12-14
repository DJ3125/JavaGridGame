package com.example.gridgameproject_2_1_24;

public final class AllGameInformation {
    public AllGameInformation(GridBoard gameInfo, GridUpdatingThread gridUpdatingThread, PlayerTurnInformation players){
        this.boardInfo = gameInfo;
        this.thread = gridUpdatingThread;
        this.allAntContainer = new AllAntContainer();
        this.playerTurnInformation = players;
        this.allProtectivePieceContainer = new AllProtectivePieceContainer();
        this.allAreaChangeEffects = new AllAreaChangeEffects();
    }
    public AllAntContainer[] getAllContainers(){return new AllAntContainer[]{this.allAntContainer, this.thread.getAllAntContainer()};}
    public AllAntContainer getAllAntContainer() {return this.allAntContainer;}
    public GridBoard getBoardInfo() {return this.boardInfo;}
    public GridUpdatingThread getThread() {return this.thread;}
    public AllProtectivePieceContainer getAllProtectivePieces(){return this.allProtectivePieceContainer;}
    public AllAreaChangeEffects getAllAreaChangeEffects(){return this.allAreaChangeEffects;}
    public PlayerTurnInformation getPlayerTurnInformation() {return this.playerTurnInformation;}

    private final GridBoard boardInfo;
    private final GridUpdatingThread thread;
    private final AllAntContainer allAntContainer;
    private final PlayerTurnInformation playerTurnInformation;
    private final AllAreaChangeEffects allAreaChangeEffects;
    private final AllProtectivePieceContainer allProtectivePieceContainer;
}
