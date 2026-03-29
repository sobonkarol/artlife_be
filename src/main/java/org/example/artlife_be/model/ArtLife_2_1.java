package org.example.artlife_be.model;

import java.util.ArrayList;

// 1. Usunięto 'public' (żeby plik się kompilował) oraz 'final' (żeby React mógł to zmieniać)
class Init {
    static int SIZE_WORLD = 10;
    static int NUM_TACT = 100;
    static int VEW_NUM_TACT = 10;
    static int START_NUM_CREEPERS = 500;
    static int START_NUM_BACT = 500;
    static int CREEPER_ENERGY_PRO_LIFE = 1;
    static int CREEPER_INITIAL_ENERGY = 1;
    static int CREEPER_ENERGY_RESERVE = 4;
    static int MAX_CREEPER_NUM_BORN_PER_TACK = 5;
    static int MAX_BACT_EATEN_BY_CREEPER = 15;
    static double BACT_MULTIPLICATION_RATE = 0.8;
    static double BACT_SPREAD_RATE = 0.5;
    static int BACT_NUM_LIMIT = 1000000;

    static ArrayList<LocationModifier> initializedLocationModifiersList() {
        ArrayList<LocationModifier> locationModifiers = new ArrayList<>(4);
        locationModifiers.add(new LocationModifier(-1, 0));
        locationModifiers.add(new LocationModifier(1, 0));
        locationModifiers.add(new LocationModifier(0, -1));
        locationModifiers.add(new LocationModifier(0, 1));
        return locationModifiers;
    }
}

// 2. Przywrócono brakującą klasę LocationModifier
class LocationModifier {
    int modifyX, modifyY;

    LocationModifier(int x, int y) {
        modifyX = x;
        modifyY = y;
    }
}

// 3. Główna klasa pliku (zostawiamy metody pomocnicze, żeby oryginał się zgadzał)
public class ArtLife_2_1 {
    private static ArrayList<Integer> totallyCreepers = new ArrayList<>();
    private static ArrayList<Integer> totallyBacteria = new ArrayList<>();

    private static void bacteriaTest(World w) {
        for (int i = 0; i < Init.SIZE_WORLD; i++) {
            for (int j = 0; j < Init.SIZE_WORLD; j++)
                System.out.format("%4d", w.board[i][j].getBactNum());
            System.out.print("\n");
        }
    }

    private static void creepersTest(World w) {
        for (int i = 0; i < Init.SIZE_WORLD; i++) {
            for (int j = 0; j < Init.SIZE_WORLD; j++)
                System.out.format("%4d", w.board[i][j].getCreepersNum());
            System.out.print("\n");
        }
    }

    private static void mainTest(World w) {
        for (int i = 0; i < Init.SIZE_WORLD; i++) {
            for (int j = 0; j < Init.SIZE_WORLD; j++)
                System.out.format("%8d|%-8d", w.board[i][j].getBactNum(),
                        w.board[i][j].getCreepersNum());
            System.out.println();
        }
    }

    private static int totalNum(World w, String what) {
        int sum = 0;
        for (int i = 0; i < Init.SIZE_WORLD; i++)
            for (int j = 0; j < Init.SIZE_WORLD; j++)
                switch (what) {
                    case "BACTERIA":
                        sum += w.board[i][j].getBactNum();
                        break;
                    case "CREEPERS":
                        sum += w.board[i][j].getCreepersNum();
                }
        return sum;
    }

    private static void addNewBornOrganismsToMainWorldCellules(World mainWorld, World tempWorld) {
        for (int i = 0; i < Init.SIZE_WORLD; i++) {
            for (int j = 0; j < Init.SIZE_WORLD; j++) {
                mainWorld.board[i][j].addBactNum(tempWorld.board[i][j].getBactNum());
                mainWorld.board[i][j].creepers.addAll(tempWorld.board[i][j].creepers);
            }
        }
    }

    public static void main(String[] args) {
        // Zostawiamy puste lub oryginalne - Spring Boot i tak tego nie wywołuje,
        // korzysta bezpośrednio z SimulationService
    }
}