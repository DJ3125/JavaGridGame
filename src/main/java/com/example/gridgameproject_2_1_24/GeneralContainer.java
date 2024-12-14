package com.example.gridgameproject_2_1_24;

import java.lang.reflect.Array;
import java.util.ArrayList;

abstract public class GeneralContainer<T extends Located>{
    public GeneralContainer(Class<T> classObject){
        this.elements = new ArrayList<>();
        this.classObject = classObject;
    }
    public final T[] getElements() {
        T[] preCastedElements = (T[]) Array.newInstance(this.classObject, this.elements.size());
        for(short i = 0; i< this.elements.size(); i++){preCastedElements[i] = this.elements.get(i);}
        return preCastedElements;
    }
    public final boolean hasElements(){return !this.elements.isEmpty();}
    public void remove(T element, AllGameInformation allGameInformation){this.elements.remove(element);}
    public final void add(T element){this.elements.add(element);}
    public final ArrayList<T> getElementsAtLocation(byte xPosition, byte yPosition){
        ArrayList<T> elementsAtLocation = new ArrayList<>();
        for(T i : this.elements){if(i.getxPosition() == xPosition && i.getyPosition() == yPosition){elementsAtLocation.add(i);}}
        return elementsAtLocation;
    }

    public final boolean isElementAtLocation(byte xPosition, byte yPosition){
        for(T i : this.elements){if(i.getxPosition() == xPosition && i.getyPosition() == yPosition){return true;}}
        return false;
    }

    public final Class<T> getClassObject() {return this.classObject;}
    private final ArrayList<T> elements;
    private final Class<T> classObject;
}
