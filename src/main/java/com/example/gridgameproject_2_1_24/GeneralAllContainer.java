package com.example.gridgameproject_2_1_24;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;

abstract public class GeneralAllContainer<X extends Located, U extends GeneralContainer<X>, Y> {
    public GeneralAllContainer(Class<? extends GeneralContainer> classObject, Y all){
        this.elementsAsArray = (U[])Array.newInstance(classObject, all.getClass().getDeclaredMethods().length);
        this.allObject = all;
        for(byte i =0; i < this.elementsAsArray.length; i++){
            try{this.elementsAsArray[i] = (U)all.getClass().getDeclaredMethods()[i].invoke(all);}
            catch (IllegalAccessException | InvocationTargetException error){error.printStackTrace();}
        }
    }

    public final boolean isElementAtLocation(byte xPosition, byte yPosition){
        for(U i : this.elementsAsArray){if(i.isElementAtLocation(xPosition, yPosition)){return true;}}
        return false;
    }
    public final void updateElements(AllGameInformation allGameInformation){for(U i : this.elementsAsArray){for(X j : i.getElements()){this.updateElementsHelper(j, i , allGameInformation);}}}
    public final void addElement(X pieceToAdd){for(U i : this.elementsAsArray){if(i.getClassObject() == pieceToAdd.getClass()){i.add(pieceToAdd);}}}
    public final U[] getAllElementsAsArray(){return this.elementsAsArray;}
    public final Y getAllObject(){return this.allObject;}
    public abstract void updateElementsHelper(X element, U container, AllGameInformation allGameInformation);
    private final U[] elementsAsArray;
    private final Y allObject;
}
