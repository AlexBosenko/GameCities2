package org.example.gameclases;

import org.example.utils.MoveState;
import org.example.utils.PlayerState;

import java.util.List;
import java.util.stream.Collectors;

public class Human extends Gamer {
    public Human(String name, boolean canMove) {
        super(name, canMove);
    }

    @Override
    public MoveState process(List<String> cities, String lastSymbol, String enteredValue) {
        String firstSymbol = enteredValue.substring(0, 1);
        if (cities.contains(enteredValue)
                && (firstSymbol.equals(lastSymbol) || lastSymbol.isEmpty())) {
            setEneteredValue(enteredValue);
            increaseMoves();
            return MoveState.CORRECT;
        }
        return MoveState.UNCORRECT;
    }

    @Override
    public boolean isHuman() {
        return true;
    }
}
