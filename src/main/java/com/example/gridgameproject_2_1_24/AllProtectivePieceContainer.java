package com.example.gridgameproject_2_1_24;

public final class AllProtectivePieceContainer extends GeneralAllContainer<ProtectivePieces, ProtectivePieceContainer<ProtectivePieces>, AllProtectors>{
    public AllProtectivePieceContainer(){super(ProtectivePieceContainer.class, new AllProtectors());}
    @Override public void updateElementsHelper(ProtectivePieces element, ProtectivePieceContainer<ProtectivePieces> container, AllGameInformation allGameInformation) {}
}
