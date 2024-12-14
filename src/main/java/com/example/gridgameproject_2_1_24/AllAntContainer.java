package com.example.gridgameproject_2_1_24;

public final class AllAntContainer extends GeneralAllContainer<Ants, AntContainer<Ants>, AllAnts>{
    public AllAntContainer(){super(AntContainer.class, new AllAnts());}
    @Override public void updateElementsHelper(Ants element, AntContainer<Ants> container, AllGameInformation allGameInformation) {
        if(element.isContinuing(allGameInformation)){element.updateElement(allGameInformation);}
        else{container.transferAntUponStop(element, allGameInformation);}
    }
}
