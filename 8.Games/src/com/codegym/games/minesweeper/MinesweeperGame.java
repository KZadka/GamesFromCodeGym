package com.codegym.games.minesweeper;

import com.codegym.engine.cell.Color;
import com.codegym.engine.cell.Game;

import java.util.ArrayList;
import java.util.List;

public class MinesweeperGame extends Game {
    private static final int SIDE = 9;
    private static final String MINE = "\uD83D\uDCA3";
    private static final String FLAG = "\uD83D\uDEA9";
    private GameObject[][] gameField = new GameObject[SIDE][SIDE];
    private int countMinesOnField;
    private int countFlags;

    @Override
    public void initialize() {
        setScreenSize(SIDE, SIDE);
        createGame();
    }

    @Override
    public void onMouseLeftClick(int x, int y) {
        openTile(x, y);
    }

    @Override
    public void onMouseRightClick(int x, int y) {
        markTile(x, y);
    }

    private void createGame() {
        for (int x = 0; x < SIDE; x++) {
            for (int y = 0; y < SIDE; y++) {
                boolean isMine = getRandomNumber(10) < 1;
                if (isMine) {
                    countMinesOnField++;
                }
                gameField[x][y] = new GameObject(x, y, isMine);
                setCellColor(x, y, Color.AZURE);
            }
        }
        countMineNeighbors();
        countFlags = countMinesOnField;
    }

    private void openTile(int x, int y) {
        if (gameField[x][y].isOpen) {
            return;
        }
        gameField[x][y].isOpen = true;
        if (gameField[x][y].isMine) {
            setCellValue(x, y, MINE);
        }
        if (!gameField[x][y].isMine && gameField[x][y].countMineNeighbors > 0) {
            setCellColor(x, y, Color.GREEN);
            setCellNumber(x, y, gameField[x][y].countMineNeighbors);
        }
        if (!gameField[x][y].isMine && gameField[x][y].countMineNeighbors == 0) {
            setCellColor(x, y, Color.GREEN);
            setCellValue(x, y, "");
            List<GameObject> fieldsList = getNeighbors(gameField[x][y]);
            for (GameObject field : fieldsList) {
                openTile(field.x, field.y);
            }
        }
    }

    private void markTile(int x, int y) {
        GameObject currentElement = gameField[x][y];
        if (currentElement.isOpen || (countFlags == 0 && !currentElement.isFlag)) {
            return;
        }
        if (!currentElement.isFlag) {
            currentElement.isFlag = true;
            countFlags -= 1;
            setCellValue(x, y, FLAG);
            setCellColor(x, y, Color.AQUA);
        } else {
            currentElement.isFlag = false;
            countFlags++;
            setCellValue(x, y, "");
            setCellColor(x, y, Color.AZURE);
        }
    }

    private void countMineNeighbors() {
        for (int x = 0; x < SIDE; x++) {
            for (int y = 0; y < SIDE; y++) {
                if (!(gameField[x][y].isMine)) {
                    int mines = 0;
                    for (GameObject field : getNeighbors(gameField[x][y])) {
                        if (field.isMine) {
                            mines++;
                        }
                    }
                    gameField[x][y].countMineNeighbors = mines;
                }
            }
        }
    }

    private List<GameObject> getNeighbors(GameObject gameObject) {
        List<GameObject> result = new ArrayList<>();
        for (int x = gameObject.x - 1; x <= gameObject.x + 1; x++) {
            for (int y = gameObject.y - 1; y <= gameObject.y + 1; y++) {
                if (x < 0 || x >= SIDE) {
                    continue;
                }
                if (y < 0 || y >= SIDE) {
                    continue;
                }
                if (gameField[x][y].equals(gameObject)) {
                    continue;
                }
                result.add(gameField[x][y]);
            }
        }
        return result;
    }
}