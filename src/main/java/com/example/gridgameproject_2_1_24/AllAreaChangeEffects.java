package com.example.gridgameproject_2_1_24;

public final class AllAreaChangeEffects extends GeneralAllContainer<AreaChangeEffect, AreaChangeEffectContainer<AreaChangeEffect>, AllAreaEffects>{
    public AllAreaChangeEffects(){super(AreaChangeEffectContainer.class, new AllAreaEffects());}
    @Override public void updateElementsHelper(AreaChangeEffect element, AreaChangeEffectContainer<AreaChangeEffect> container, AllGameInformation allGameInformation) {
        if(element.performExplosion(allGameInformation) == 'E'){container.remove(element, allGameInformation);}
        else{element.updateEffect(allGameInformation);}
    }
}
