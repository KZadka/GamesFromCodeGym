package com.codegym.games.minesweeper;

import com.codegym.engine.cell.Color;
import com.codegym.engine.cell.Game;

import java.util.ArrayList;
import java.util.List;

public class MinesweeperGame extends Game {
    private static final int SIDE = 9;
    private static final String MINE = "\uD83D\uDCA3";
    private static final String FLAG = "\uD83D\uDEA9";
    private final GameObject[][] gameField = new GameObject[SIDE][SIDE];
    private int countMinesOnField;
    private int countFlags;
    private int countClosedTiles = SIDE * SIDE;
    private boolean isGameStopped;

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
        isGameStopped = false;
        countMineNeighbors();
        countFlags = countMinesOnField;
    }

    private void openTile(int x, int y) {
        GameObject currentElement = gameField[x][y];
        if (currentElement.isOpen || currentElement.isFlag || isGameStopped) {
            return;
        }
        if (currentElement.isMine) {
            setCellValueEx(x, y, Color.RED, MINE);
            gameOver();
        }
        currentElement.isOpen = true;
        countClosedTiles--;
        if (!currentElement.isMine && currentElement.countMineNeighbors > 0) {
            setCellColor(x, y, Color.GREEN);
            setCellNumber(x, y, currentElement.countMineNeighbors);
        }
        if (!currentElement.isMine && currentElement.countMineNeighbors == 0) {
            setCellColor(x, y, Color.GREEN);
            setCellValue(x, y, "");
            List<GameObject> fieldsList = getNeighbors(currentElement);
            for (GameObject field : fieldsList) {
                openTile(field.x, field.y);
            }
        }
        if (countClosedTiles == countMinesOnField && !currentElement.isMine) {
            win();
        }
    }

    private void markTile(int x, int y) {
        GameObject currentElement = gameField[x][y];
        if (isGameStopped || currentElement.isOpen || (countFlags == 0 && !currentElement.isFlag)) {
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

    private void win() {
        isGameStopped = true;
        showMessageDialog(Color.GRAY, "You win!", Color.GOLD, 20);
    }

    private void gameOver() {
        isGameStopped = true;
        showMessageDialog(Color.GRAY, "You loose", Color.WHITE, 20);
    }

    private void countMineNeighbors() {
        for (int x = 0; x < SIDE; x++) {
            for (int y = 0; y < SIDE; y++) {
                GameObject currentElement = gameField[x][y];
                if (!currentElement.isMine) {
                    int mines = 0;
                    for (GameObject field : getNeighbors(currentElement)) {
                        if (field.isMine) {
                            mines++;
                        }
                    }
                    currentElement.countMineNeighbors = mines;
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
                GameObject currentElement = gameField[x][y];
                if (currentElement.equals(gameObject)) {
                    continue;
                }
                result.add(currentElement);
            }
        }
        return result;
    }
}