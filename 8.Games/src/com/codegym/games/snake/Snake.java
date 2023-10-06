package com.codegym.games.snake;

import com.codegym.engine.cell.*;

import java.util.ArrayList;
import java.util.List;

public class Snake {

    private List<GameObject> snakeParts = new ArrayList<>();
    private static final String HEAD_SIGN = "\uD83D\uDC7E";
    private static final String BODY_SIGN = "\u26AB";
    private Direction direction = Direction.LEFT;
    public int x;
    public int y;
    public boolean isAlive = true;

    public Snake(int x, int y) {
        this.x = x;
        this.y = y;
        GameObject first = new GameObject(x, y);
        GameObject second = new GameObject(x + 1, y);
        GameObject third = new GameObject(x + 2, y);
        snakeParts.add(first);
        snakeParts.add(second);
        snakeParts.add(third);
    }

    public void draw(Game game) {
        Color snakeColor;
        for (int i = 0; i < snakeParts.size(); i++) {
            if (this.isAlive) {
                snakeColor = Color.GREENYELLOW;
            } else {
                snakeColor = Color.RED;
            }
            GameObject currentPart = snakeParts.get(i);
            if (i == 0) {
                game.setCellValueEx(currentPart.x, currentPart.y, Color.NONE, HEAD_SIGN, snakeColor, 75);
            } else {
                game.setCellValueEx(currentPart.x, currentPart.y, Color.NONE, BODY_SIGN, snakeColor, 75);
            }
        }
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
