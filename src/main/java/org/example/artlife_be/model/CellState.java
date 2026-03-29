package org.example.artlife_be.model;

public class CellState {
    public int id;
    public int x;
    public int y;
    public int bactNum;
    public int creepersNum;

    public CellState(int id, int x, int y, int bactNum, int creepersNum) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.bactNum = bactNum;
        this.creepersNum = creepersNum;
    }
}
