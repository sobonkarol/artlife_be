package org.example.artlife_be.model;
import java.util.List;

public class WorldState {
    public List<CellState> board;
    public int tack;
    public long totalBact;
    public int totalCreep;

    public WorldState(List<CellState> board, int tack, long totalBact, int totalCreep) {
        this.board = board;
        this.tack = tack;
        this.totalBact = totalBact;
        this.totalCreep = totalCreep;
    }
}
