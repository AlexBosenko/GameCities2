package org.example.gameclases;

import org.example.utils.MoveState;
import org.example.utils.PlayerState;

import java.util.List;

public abstract class Gamer {
    private String name;
    private boolean canMove;
    private String eneteredValue;
    private int moves = 0;
    private PlayerState playerState;
    public Gamer(String name, boolean canMove) {
        this.name = name;
        this.canMove = canMove;
        this.playerState = PlayerState.INGAME;
    }

    public String getName() {
        return name;
    }

    public boolean isCanMove() {
        return canMove;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    public String getEneteredValue() {
        return eneteredValue;
    }

    public void setEneteredValue(String eneteredValue) {
        this.eneteredValue = eneteredValue;
    }

    public int getMoves() {
        return moves;
    }

    public void increaseMoves() {
        this.moves++;
    }

    public PlayerState getPlayerState() {
        return playerState;
    }

    public void setPlayerState(PlayerState playerState) {
        this.playerState = playerState;
    }

    public abstract MoveState process(List<String> cities, String lastSymbol, String enteredValue);
    public abstract boolean isHuman();
}
