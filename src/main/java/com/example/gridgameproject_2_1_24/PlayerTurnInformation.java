package com.example.gridgameproject_2_1_24;

public final class PlayerTurnInformation {
    public PlayerTurnInformation(IndividualPlayer... players){
        this.players = players;
        this.turn = (byte)Math.floor(this.players.length * Math.random());
        this.turnsRemaining = (byte)(5 * this.players.length);
        this.maxTurnsRemaining = this.turnsRemaining;
    }
    public IndividualPlayer getCurrentPlayerTurn(){return this.players[this.turn];}
    public byte getTurn() {return this.turn;}
    public IndividualPlayer getPlayer(byte playerIndex){return this.players[playerIndex];}
    public byte getTurnsRemaining() {return this.turnsRemaining;}
    public IndividualPlayer[] getPlayers() {return this.players;}
    public void incrementTurn(){
        this.turn++;
        this.turn%= (byte) this.players.length;
        this.turnsRemaining--;
    }

    public void updatePlayerScores(AllGameInformation allGameInformation){
        short[] scores = new short[this.players.length];
        for(AllAntContainer i : allGameInformation.getAllContainers()){for(MyrmecocystusGenus j : i.getAllObject().getHoneyPotAnt().getElements()){scores[j.getTeamNumber()] += j.getPointValue();}}
        for(byte i = 0; i< this.players.length; i++){
            this.players[i].setTotalPoints(scores[i]);
            if(this.maxTurnsRemaining - this.turnsRemaining >= this.getPlayers().length && this.players[i] == this.getCurrentPlayerTurn()){this.players[i].changeBalance(scores[i] * 50 + 20);}
        }
    }

    private byte turn, turnsRemaining;
    private final IndividualPlayer[] players;
    private final byte maxTurnsRemaining;
}
