package com.example.gridgameproject_2_1_24;

import javafx.scene.paint.Color;

public final class IndividualPlayer {

    public IndividualPlayer(String name, String color){
        this.name = name;
        this.numberOfWins = 0;
        this.color = Color.valueOf(color);
        this.money = 100;
    }
    public IndividualPlayer(PlayerCreationTemplate template){this(template.getName(), template.getColor());}
    public byte getNumberOfWins() {return this.numberOfWins;}
    public void incrementNumberOfWins(){this.numberOfWins++;}
    public String getName() {return this.name;}
    public int getMoney() {return this.money;}
    public void changeBalance(int changeInBalance){
        this.money += changeInBalance;
        if(this.money < 0){this.money = 0;}
    }
    public boolean buyAnt(AllPossibleAntsForPurchase ants){
        if(this.money >= ants.getCost()){
            this.changeBalance(-ants.getCost());
            return true;
        }else{return false;}
    }

    public Color getColor() {return this.color;}
    public short getTotalPoints() {return this.totalPoints;}
    public void setTotalPoints(short totalPoints) {this.totalPoints = totalPoints;}

    private final String name;
    private int money;
    private final Color color;
    private byte numberOfWins;
    private short totalPoints;
}
