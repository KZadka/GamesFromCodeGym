package com.codegym.games.snake;

import java.util.ArrayList;
import java.util.List;

public class Snake {

    public int x;
    public int y;
    private List<GameObject> snakeParts = new ArrayList<>();

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
}
