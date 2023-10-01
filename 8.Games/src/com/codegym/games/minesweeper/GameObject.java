package com.codegym.games.minesweeper;


public class GameObject {
    
    public int x;
    public int y;
    public int countMineNeighbors;
    public boolean isMine;
    public boolean isOpen;
    public boolean isFlag;
    
    
    public GameObject(int x, int y, boolean isMine) {
        this.x = x;
        this.y = y;
        this.isMine = isMine;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o instanceof GameObject) {
            GameObject object = (GameObject) o;
            return x == object.x &&
                    y == object.y &&
                    isMine == object.isMine;
        }
        return false;
    }

}