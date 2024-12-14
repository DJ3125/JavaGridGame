package com.example.gridgameproject_2_1_24;

public final class PlayerCreationTemplate {
    public PlayerCreationTemplate(String color){
        this.name = "DJ";
        this.color = color;
    }
    public void setColor(String color) {this.color = color;}
    public String getColor() {return this.color;}
    public void setName(String name) {
        this.name = name;
        if(this.getName().isEmpty()){this.name = "DJ";}
    }
    public String getName() {return this.name;}

    private String name, color;
}
