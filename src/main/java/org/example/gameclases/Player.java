package org.example.gameclases;

public class Player {
    private boolean canMove;
    private String name;

    public Player(String name, boolean canMove) {
        this.name = name;
        this.canMove = canMove;
    }

    public boolean isCanMove() {
        return canMove;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
