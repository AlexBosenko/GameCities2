package org.example.gameclases;

import org.example.utils.MoveState;
import org.example.utils.PlayerState;

import java.util.List;

public abstract class Player {
    private String name;
    private String eneteredValue;
    private int moves = 0;
    private PlayerState playerState;
    public Player(String name) {
        this.name = name;
        this.playerState = PlayerState.INGAME;
    }

    public String getName() {
        return name;
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
