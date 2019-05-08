package com.app;

public class Coin {
    private Color color;

    public Coin(Color color){
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString(){
        return color.toString().substring(0, 1);
    }
}
