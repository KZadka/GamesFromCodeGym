package com.codegym.games.snake;

import com.codegym.engine.cell.*;

public class SnakeGame extends Game {

    public static final int WIDTH = 15;
    public static final int HEIGHT = 15;
    private static final int GOAL = 28;
    private Snake snake;
    private Apple apple;
    private int turnDelay;
    private int score;
    private boolean isGameStopped;

    @Override
    public void initialize() {
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }

    @Override
    public void onTurn(int x) {
        snake.move(apple);
        if (snake.getLength() > GOAL) {
            win();
        }
        if (!snake.isAlive) {
            gameOver();
        }
        if (!apple.isAlive) {
            setScore(score += 5);
            setTurnTimer(turnDelay -= 10);
            createNewApple();
        }
        drawScene();
    }

    @Override
    public void onKeyPress(Key key) {
        if (key == Key.SPACE && isGameStopped) {
            createGame();
        }
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
        Apple appleToBeCreated = new Apple(appleX, appleY);
        if (snake.checkCollision(appleToBeCreated)) {
            createNewApple();
        } else {
            apple = appleToBeCreated;
        }
    }

    private void createGame() {
        score = 0;
        snake = new Snake(WIDTH / 2, HEIGHT / 2);
        turnDelay = 300;
        isGameStopped = false;
        createNewApple();
        setTurnTimer(turnDelay);
        setScore(score);
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

    private void gameOver() {
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.GRAY, "GAME OVER", Color.RED, 75);
    }

    private void win() {
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.GRAY, "YOU WIN", Color.GREEN, 75);
    }

}
