package com.codegym.games.game2048;

import com.codegym.engine.cell.Color;
import com.codegym.engine.cell.Game;
import com.codegym.engine.cell.Key;

public class Game2048 extends Game {
    private static final int SIDE = 4;
    private int[][] gameField = new int[SIDE][SIDE];

    @Override
    public void initialize() {
        setScreenSize(SIDE, SIDE);
        createGame();
        drawScene();
        for (int i = 0; i < SIDE; i++) {
            compressRow(gameField[i]);
        }
    }
    @Override
    public void onKeyPress(Key key) {
        switch (key) {
            case LEFT:
                moveLeft();
                break;
            case RIGHT:
                moveRight();
                drawScene();
                break;
            case UP:
                moveUp();
                drawScene();
                break;
            case DOWN:
                moveDown();
                drawScene();
                break;
        }
    }

    private void createGame() {
        createNewNumber();
        createNewNumber();
    }

    private void drawScene() {
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField.length; j++) {
                setCellColoredNumber(j, i, gameField[i][j]);
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

    private Color getColorByValue(int value) {
        switch (value) {
            case 0:
                return Color.ALICEBLUE;
            case 2:
                return Color.AQUAMARINE;
            case 4:
                return Color.BISQUE;
            case 8:
                return Color.BLANCHEDALMOND;
            case 16:
                return Color.CORAL;
            case 32:
                return Color.DARKORCHID;
            case 64:
                return Color.CYAN;
            case 128:
                return Color.CRIMSON;
            case 256:
                return Color.FUCHSIA;
            case 512:
                return Color.DEEPPINK;
            case 1024:
                return Color.FORESTGREEN;
            case 2048:
                return Color.MOCCASIN;
        }
        return Color.WHITE;
    }

    private void setCellColoredNumber(int x, int y, int value) {
        Color colorToSet = getColorByValue(value);
        if (value == 0) {
            setCellValueEx(x, y, colorToSet, "");
        } else {
            setCellValueEx(x, y, colorToSet, String.valueOf(value));
        }
    }

    private boolean compressRow(int[] row) {
        boolean moved = false;
        int writeIndex = 0;
        for (int readIndex = 0; readIndex < row.length; readIndex++) {
            if (row[readIndex] != 0) {
                if (readIndex != writeIndex) {
                    row[writeIndex] = row[readIndex];
                    row[readIndex] = 0;
                    moved = true;
                }
                writeIndex++;
            }
        }
        return moved;
    }
    private boolean mergeRow(int[] row) {
        boolean merged = false;

        for (int i = 0; i < row.length - 1; i++) {
            if (row[i] != 0 && row[i] == row[i + 1]) {
                row[i] *= 2;
                row[i + 1] = 0;
                merged = true;
                i++;
            }
        }
        return merged;
    }
    private void moveLeft() {
        boolean moved = false;

        for (int i = 0; i < gameField.length; i++) {
            boolean rowMoved = compressRow(gameField[i]);
            rowMoved = mergeRow(gameField[i]) || rowMoved;
            rowMoved = compressRow(gameField[i]) || rowMoved;

            if (rowMoved && !moved) {
                createNewNumber();
                moved = true;
            }
        }
        drawScene();
    }
    private void moveRight() {
        for (int i = 0; i < 4; i++) {
            rotateClockwise();
            printBoard();
            if (i == 1) {
                moveLeft();
            }
        }
//        rotateClockwise();
//        rotateClockwise();
//        moveLeft();
//        rotateClockwise();
//        rotateClockwise();
    }
    private void moveUp() {
        for (int i = 0; i < 4; i++) {
            rotateClockwise();
            if (i == 2) {
                 moveLeft();
            }
        }
    }
    private void moveDown() {
        for (int i = 0; i < 4; i++) {
            rotateClockwise();
            if (i == 0) {
                moveLeft();
            }
        }
    }
    private void rotateClockwise() {
        int[][] rotatedMatrix = new int[SIDE][SIDE];

        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                rotatedMatrix[i][j] = gameField[SIDE - j - 1][i];
            }
        }

        gameField = rotatedMatrix;
    }
    private void printBoard() {
        System.out.println("Printing the board: ");
        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                System.out.print(gameField[i][j]);
            }
            System.out.println();
        }
    }
}
