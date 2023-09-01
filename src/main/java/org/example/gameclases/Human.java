package org.example.gameclases;

import org.example.utils.MoveState;

import java.util.List;

public class Human extends Player {
    public Human(String name) {
        super(name);
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
