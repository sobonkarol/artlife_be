package org.example.artlife_be.model;

import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class SimulationService {
    private World mainWorld;
    private int tack = 0;

    public void init(Map<String, Object> config) {
        try {
            Init.BACT_MULTIPLICATION_RATE = Double.parseDouble(config.getOrDefault("bactMultiRate", "0.8").toString());
            Init.BACT_SPREAD_RATE = Double.parseDouble(config.getOrDefault("bactSpreadRate", "0.5").toString());
            Init.MAX_BACT_EATEN_BY_CREEPER = (int) Double.parseDouble(config.getOrDefault("maxBactEaten", "15").toString());
            Init.CREEPER_ENERGY_PRO_LIFE = (int) Double.parseDouble(config.getOrDefault("energyProLife", "1").toString());
            Init.CREEPER_ENERGY_RESERVE = (int) Double.parseDouble(config.getOrDefault("energyReserve", "4").toString());

            int startBact = (int) Double.parseDouble(config.getOrDefault("startBact", "500").toString());
            int startCreepers = (int) Double.parseDouble(config.getOrDefault("startCreepers", "500").toString());

            mainWorld = new World();
            mainWorld.sowBacteries(startBact);
            mainWorld.sowCreepers(startCreepers);
            this.tack = 0;
        } catch (Exception e) {
            System.out.println("Błąd wczytywania parametrów: " + e.getMessage());
        }
    }

    public WorldState processNextStep() {
        if (mainWorld == null) init(new HashMap<>());

        World tempWorld = new World();
        mainWorld.creepersAndBacteriaAction(mainWorld, tempWorld);
        addNewBornOrganismsToMainWorldCellules(mainWorld, tempWorld);
        tack++;

        return getCurrentState();
    }

    public WorldState getCurrentState() {
        if (mainWorld == null) init(new HashMap<>());
        long currentTotalBact = 0;
        int currentTotalCreep = 0;
        List<CellState> flatBoard = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Cellule cell = mainWorld.board[i][j];
                currentTotalBact += cell.getBactNum();
                currentTotalCreep += cell.creepers.size();
                flatBoard.add(new CellState(i * 10 + j, i, j, cell.getBactNum(), cell.creepers.size()));
            }
        }
        return new WorldState(flatBoard, tack, currentTotalBact, currentTotalCreep);
    }

    private void addNewBornOrganismsToMainWorldCellules(World mainWorld, World tempWorld) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                mainWorld.board[i][j].addBactNum(tempWorld.board[i][j].getBactNum());
                mainWorld.board[i][j].creepers.addAll(tempWorld.board[i][j].creepers);
            }
        }
    }

    public void infect() {
        if (mainWorld != null) {
            mainWorld.board[4][5].addBactNum(3000);
        }
    }

    public void addLek() {
        if (mainWorld != null) {
            // TRIK: Podnosimy energię na ułamek sekundy, żeby "lek" nie umarł z głodu natychmiast
            int oldEnergy = Init.CREEPER_INITIAL_ENERGY;
            Init.CREEPER_INITIAL_ENERGY = 10;

            // Wstrzykujemy 600 pełzaczy (silna dawka leku)
            for (int i = 0; i < 600; i++) {
                int x = (int)(Math.random() * 10);
                int y = (int)(Math.random() * 10);
                mainWorld.board[x][y].creepers.add(new Creeper(x, y));
            }

            Init.CREEPER_INITIAL_ENERGY = oldEnergy; // Przywracamy normę dla zwykłych narodzin
        }
    }
}