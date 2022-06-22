package edu.skku.cs.omokwithmvp;

public class Position {
    public int     y;
    public int     x;
    public int     player;
    public boolean isEmpty;

    public Position (int y, int x, int player, boolean isEmpty) {
        this.y = y;
        this.x = x;
        this.player = player;
        this.isEmpty = isEmpty;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public int getPlayer() {
        return player;
    }

    public boolean getIsEmpty() {
        return isEmpty;
    }
}