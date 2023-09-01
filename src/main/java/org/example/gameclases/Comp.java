package org.example.gameclases;

import org.example.utils.MoveState;
import org.example.utils.PlayerState;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Comp extends Player {
    public Comp() {
        super("Comp");
    }

    @Override
    public MoveState process(List<String> cities, String lastSymbol, String enteredValue) {
        List<String> collect = cities;
        if (!lastSymbol.isEmpty()) {
            collect = cities.stream()
                    .filter(s -> s.toUpperCase().startsWith(lastSymbol.toUpperCase()))
                    .collect(Collectors.toList());
        }

        int size = collect.size();
        if (size > 0) {
            Random rnd = new Random();
            int index = rnd.nextInt(size);
            String randomCity = collect.get(index);
            setEneteredValue(randomCity);
            increaseMoves();
            return MoveState.CORRECT;
        } else {
            setPlayerState(PlayerState.LOSE);
            return MoveState.NOMOVE;
        }
    }

    @Override
    public boolean isHuman() {
        return false;
    }
}
