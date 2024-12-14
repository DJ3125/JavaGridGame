package com.example.gridgameproject_2_1_24;

import javafx.application.Platform;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class GridUpdatingThread extends Thread {
    @Override public void run() {
        this.counter = 0;
        GridUpdatingThread threader = this;
        while(!this.isInterrupted()){
            while(!this.isInterrupted() && this.counter == (int)((System.currentTimeMillis() - this.startTime)/this.interval)){Thread.onSpinWait();}
            this.counter = (int)((System.currentTimeMillis() - this.startTime)/this.interval);
            Platform.runLater(() -> {
                if(threader.isFinishedWithTurn()){
                    if(!threader.performedUponTurnFinished){
                        threader.invokeMethod(threader.uponTurnFinished);
                        threader.performedUponTurnFinished = true;
                        threader.counter = 0;
                    }
                    threader.invokeMethod(threader.borderUpdater, threader.counter);
                }else{
                    threader.performedUponTurnFinished = false;
                    threader.invokeMethod(threader.updater);
                }
            });
        }
    }

    private void invokeMethod(Method methodToInvoke, Object... params){
        try{methodToInvoke.invoke(this.controller, params);}
        catch (IllegalAccessException | InvocationTargetException error){error.printStackTrace();}
    }

    public GridUpdatingThread(GridGameController controller, Method updater, Method borderUpdater, Method uponTurnFinished){
        this.allAntContainer = new AllAntContainer();
        this.controller = controller;
        this.updater = updater;
        this.borderUpdater = borderUpdater;
        this.startTime = System.currentTimeMillis();
        this.performedUponTurnFinished = true;
        this.uponTurnFinished = uponTurnFinished;
        this.counter = 0;
        this.start();
    }

    public boolean isFinishedWithTurn(){
        for(AntContainer<? extends Ants> i : this.allAntContainer.getAllElementsAsArray()){if(i.hasElements()){return false;}}
        for(AreaChangeEffectContainer<? extends AreaChangeEffect> i : this.controller.getAllGameInformation().getAllAreaChangeEffects().getAllElementsAsArray()){if(i.hasElements()){return false;}}
        return true;
    }

    public AllAntContainer getAllAntContainer() {return this.allAntContainer;}

    private final long startTime;
    private final AllAntContainer allAntContainer;
    private final GridGameController controller;
    private final Method updater, borderUpdater, uponTurnFinished;
    private boolean performedUponTurnFinished;
    private final int interval = 100;
    private int counter;
}
