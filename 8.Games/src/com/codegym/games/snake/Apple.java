package com.codegym.games.snake;

import com.codegym.engine.cell.*;

public class Apple extends GameObject {

    private static final String APPLE_SIGN = "\uD83C\uDF4E";
    public int x;
    public int y;

    public Apple(int x, int y) {
        super(x, y);
        this.x = x;
        this.y = y;
    }

    public void draw(Game game) {
        game.setCellValueEx(this.x, this.y, Color.NONE, APPLE_SIGN, Color.RED, 75);
    }
}
