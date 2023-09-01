package org.example.gameclases;

import org.example.utils.MoveState;
import org.example.utils.PlayerState;

import java.util.*;

public class CityGame extends Thread {
    private List<String> cities;
    private Player human;
    private Player comp;
    private String curCity;
    private String enteredValue;
    private String lastSymbol = "";
    private Deque<Gamer> players;
    private List<String> incorrectSymbol = Arrays.asList("И", "Й", "Ї", "Ь", "-", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", " ");
    private HashMap<Gamer, PlayerState> playersProgress;

    public CityGame(Player human, Player comp, List<String> cities, Deque<Gamer> players) {
        this.cities = cities;
        this.human = human;
        this.comp = comp;
        this.players = players;
    }

    private void setCurCity(String curCity) {
        this.curCity = curCity;
    }

    public String getCurCity() {
        return curCity;
    }

    private void setLastSymbol(String cityName) {
        int index = 1;
        int length = cityName.length();
        String symbol = cityName.substring(length - index, length - index + 1).toUpperCase();
        while (incorrectSymbol.contains(symbol) && length - index >= 0) {
            index++;
            symbol = cityName.substring(length - index, length - index + 1).toUpperCase();
        }

        this.lastSymbol = symbol;
    }


    public String getLastSymbol() {
        return lastSymbol;
    }

    public String checkPlayersStatus() {
        StringBuilder summary = new StringBuilder();
//        for (Gamer player : playersProgress.keySet()) {
//            if (player.)
//            summary.append(player.getName() + ": ходи " + player.getMoves() + "\n");
//        }
        for (Map.Entry<Gamer, PlayerState> entry : playersProgress.entrySet()) {
            summary.append(entry.getKey().getName())
                    .append(": ходи ")
                    .append(entry.getKey().getMoves())
                    .append("\n");
        }
        return summary.toString();
    }
    public boolean processGame(String enteredValue) {
        Gamer curPlayer = players.getFirst();
        MoveState result = curPlayer.process(cities, lastSymbol, enteredValue);
        switch (result) {
            case CORRECT:
                setCurCity(curPlayer.getEneteredValue());
                setLastSymbol(curPlayer.getEneteredValue());
                if (gameCanGoOn() == true) {
                    playersProgress.put(curPlayer, curPlayer.getPlayerState());
                    players.pop();
                    players.addLast(curPlayer);
                    if (!players.getFirst().isHuman()) {
                        processGame("");
                    }
                    return true;
                } else {
                    curPlayer.setPlayerState(PlayerState.WIN);
                    playersProgress.put(curPlayer, curPlayer.getPlayerState());
                    while (players.size() > 0) {
                        curPlayer = players.getFirst();
                        curPlayer.setPlayerState(PlayerState.LOSE);
                    }
                }
            case UNCORRECT:
                return false;
            case NOMOVE:
                playersProgress.put(curPlayer, curPlayer.getPlayerState());
                players.pop();
                return true;
        }
        return false;
    }

    public boolean gameCanGoOn() {
        long count = cities.stream()
                .filter(str -> str.startsWith(lastSymbol))
                .count();
        return count > 0;
    }

    public long checkStatus() {
        return cities.stream()
                .filter(str -> str.startsWith(lastSymbol))
                .count();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.currentThread().sleep(1500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (!players.getFirst().isHuman()) {
                processGame("");
            }
        }
    }
}
