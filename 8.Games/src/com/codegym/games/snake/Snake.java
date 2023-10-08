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
        if (direction == Direction.UP && this.direction == Direction.DOWN ||
            direction == Direction.DOWN && this.direction == Direction.UP ||
            direction == Direction.LEFT && this.direction == Direction.RIGHT ||
            direction == Direction.RIGHT && this.direction == Direction.LEFT) {
            return;
        }
        this.direction = direction;
    }

    public GameObject createNewHead() {
        GameObject currentHead = snakeParts.get(0);
        switch (this.direction) {
            case UP:
                return new GameObject(currentHead.x, currentHead.y - 1);
            case DOWN:
                return new GameObject(currentHead.x, currentHead.y + 1);
            case LEFT:
                return new GameObject(currentHead.x - 1, currentHead.y);
            case RIGHT:
                return new GameObject(currentHead.x + 1, currentHead.y);
        }
        return currentHead;
    }
    public void removeTail() {
        snakeParts.remove(snakeParts.size() - 1);
    }
    public void move(Apple apple) {
        GameObject newHead = createNewHead();
        if (newHead.x < 0 || newHead.x >= SnakeGame.WIDTH ||
            newHead.y < 0 || newHead.y >= SnakeGame.HEIGHT) {
            isAlive = false;
            return;
        }
        if (newHead.x == apple.x && newHead.y == apple.y) {
            apple.isAlive = false;
            snakeParts.add(0, newHead);
            return;
        }
        snakeParts.add(0, newHead);
        removeTail();
    }
}
