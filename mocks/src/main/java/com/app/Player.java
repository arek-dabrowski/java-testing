package com.app;

public class Player {
    private String name;
    private Color coinColor;

    private int wins = 0;
    private int loses = 0;

    public Player(String name, Color coinColor){
        this.name = name;
        this.coinColor = coinColor;
    }

    public Color getCoinColor() {
        return coinColor;
    }

    @Override
    public String toString(){
        return this.name;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLoses() {
        return loses;
    }

    public void setLoses(int loses) {
        this.loses = loses;
    }

}
