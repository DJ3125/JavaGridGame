package com.example.gridgameproject_2_1_24;

public final class AllAnts{
    public AllAnts(){
        this.suicideAnt = new AntContainer<>(ColobopsisGenus.class);
        this.slaveMakerAnt = new AntContainer<>(RossomyrmexGenus.class);
        this.woodAnt = new AntContainer<>(FormicaRufaSpecies.class);
        this.honeyPotAnt = new AntContainer<>(MyrmecocystusGenus.class);
        this.funnelAnts = new AntContainer<>(AphaenogasterGenus.class);
        this.explosionQueen = new AntContainer<>(QueenColobopsis.class);
        this.pointQueen = new AntContainer<>(QueenMyrmecocystus.class);
    }

    public AntContainer<ColobopsisGenus> getSuicideAnt() {return this.suicideAnt;}
    public AntContainer<RossomyrmexGenus> getSlaveMakerAnt() {return this.slaveMakerAnt;}
    public AntContainer<FormicaRufaSpecies> getWoodAnt() {return this.woodAnt;}
    public AntContainer<MyrmecocystusGenus> getHoneyPotAnt() {return this.honeyPotAnt;}
    public AntContainer<AphaenogasterGenus> getFunnelAnts() {return this.funnelAnts;}
    public AntContainer<QueenMyrmecocystus> getPointQueen() {return this.pointQueen;}
    public AntContainer<QueenColobopsis> getExplosionQueen() {return this.explosionQueen;}

    private final AntContainer<ColobopsisGenus> suicideAnt;
    private final AntContainer<RossomyrmexGenus> slaveMakerAnt;
    private final AntContainer<FormicaRufaSpecies> woodAnt;
    private final AntContainer<MyrmecocystusGenus> honeyPotAnt;
    private final AntContainer<AphaenogasterGenus> funnelAnts;
    private final AntContainer<QueenColobopsis> explosionQueen;
    private final AntContainer<QueenMyrmecocystus> pointQueen;
}
