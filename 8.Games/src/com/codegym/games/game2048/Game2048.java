package com.codegym.games.game2048;

import com.codegym.engine.cell.*;

public class Game2048 extends Game {
    private static final int SIDE = 4;
    private int[][] gameField = new int[SIDE][SIDE];
    @Override
    public void initialize() {
        setScreenSize(SIDE, SIDE);
        createGame();
        drawScene();
    }
    private void createGame() {
        createNewNumber();
        createNewNumber();
    }
    private void drawScene() {
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField.length; j++) {
                setCellColor(i, j, Color.AQUAMARINE);
            }
        }
    }
    private void createNewNumber() {
        int x = getRandomNumber(SIDE);
        int y = getRandomNumber(SIDE);
        int probability = getRandomNumber(10);
        if (gameField[x][y] != 0) {
            createNewNumber();
        }
        if (probability < 9) {
            gameField[x][y] = 2;
        } else {
            gameField[x][y] = 4;
        }
    }
}
