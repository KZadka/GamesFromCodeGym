package com.codegym.games.snake;

import com.codegym.engine.cell.*;

public class SnakeGame extends Game {

    public static final int WIDTH = 15;
    public static final int HEIGHT = 15;
    private Snake snake;
    private Apple apple;
    private int turnDelay;
    @Override
    public void initialize() {
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }

    @Override
    public void onTurn(int x) {
        snake.move(apple);
        if (!apple.isAlive) {
            createNewApple();
        }
        drawScene();
    }

    @Override
    public void onKeyPress(Key key) {
        switch (key) {
            case LEFT:
                snake.setDirection(Direction.LEFT);
                break;
            case RIGHT:
                snake.setDirection(Direction.RIGHT);
                break;
            case UP:
                snake.setDirection(Direction.UP);
                break;
            case DOWN:
                snake.setDirection(Direction.DOWN);
                break;
        }
    }

    private void createNewApple() {
        int appleX = getRandomNumber(WIDTH);
        int appleY = getRandomNumber(HEIGHT);
        apple = new Apple(appleX, appleY);
    }
    private void createGame() {
        snake = new Snake(WIDTH / 2, HEIGHT / 2);
        createNewApple();
        turnDelay = 300;
        setTurnTimer(turnDelay);
        drawScene();
    }

    private void drawScene() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                setCellValueEx(x, y, Color.DIMGREY, "");
            }
        }
        snake.draw(this);
        apple.draw(this);
    }

}
