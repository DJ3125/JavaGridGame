package com.example.gridgameproject_2_1_24;

public final class AntContainer<T extends Ants> extends GeneralContainer<T>{
    @Override public void remove(T element, AllGameInformation allGameInformation){
        if(this.containsAnt(element)){
            element.performDeadAction(allGameInformation);
            super.remove(element, allGameInformation);
        }
    }

    public void transferAntUponStop(T element, AllGameInformation allGameInformation){
        if(this.containsAnt(element)){
            element.stopElement(allGameInformation);
            if(!element.isDeletable()){allGameInformation.getAllAntContainer().addElement(element);}
            this.remove(element, allGameInformation);
        }
    }
    private boolean containsAnt(T ant){
        for(T i : this.getElements()){if(i == ant){return true;}}
        return false;
    }
    public AntContainer(Class<T> classObject){super(classObject);}
}
