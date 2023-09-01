package org.example.gameclases;

import org.example.utils.MoveState;
import org.example.utils.PlayerState;

import java.util.*;
import java.util.stream.Collectors;

public class CityGame extends Thread {
    private List<String> cities;
    private String curCity;
    private String lastSymbol = "";
    private Deque<Player> players;
    private final List<String> INCORRECT_SYMBOL = Arrays.asList("И", "Й", "Ї", "Ь", "-", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", " ");
    private final HashMap<Player, PlayerState> PLAYERS_PROGRESS = new HashMap<>();

    public CityGame(List<String> cities, Deque<Player> players) {
        this.cities = cities;
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
        while (INCORRECT_SYMBOL.contains(symbol) && length - index >= 0) {
            index++;
            symbol = cityName.substring(length - index, length - index + 1).toUpperCase();
        }

        this.lastSymbol = symbol;
        cities.remove(cities.indexOf(cityName));
    }


    public String getLastSymbol() {
        return lastSymbol;
    }

    public String checkPlayersStatus() {
        StringBuilder summary = new StringBuilder();
        for (Map.Entry<Player, PlayerState> entry : PLAYERS_PROGRESS.entrySet()) {
            if (entry.getKey().getPlayerState().equals(PlayerState.INGAME)) {
                summary.append(entry.getKey().getName())
                        .append(": ходи ")
                        .append(entry.getKey().getMoves())
                        .append("\n");
            } else {
                summary.append(entry.getKey().getName())
                        .append(": ")
                        .append(entry.getKey().getPlayerState())
                        .append("\n");
            }
        }
        return summary.toString();
    }

    private void setPlayerLose(Player curPlayer) {
        curPlayer.setPlayerState(PlayerState.LOSE);
        PLAYERS_PROGRESS.put(curPlayer, curPlayer.getPlayerState());
        players.pop();
    }

    private void setWinnerAndLose(Player curPlayer) {
        curPlayer.setPlayerState(PlayerState.WIN);
        PLAYERS_PROGRESS.put(curPlayer, curPlayer.getPlayerState());
        List<Player> collect = players.stream()
                .filter(pl -> pl.getPlayerState() != PlayerState.WIN)
                .collect(Collectors.toList());
        for (Player player : collect) {
            setPlayerLose(player);
        }
    }

    private void backOfQueue(Player curPlayer) {
        PLAYERS_PROGRESS.put(curPlayer, curPlayer.getPlayerState());
        players.pop();
        players.addLast(curPlayer);
    }

    public boolean processGame(String enteredValue) {
        System.out.println("players.size() = " + players.size());
        Player curPlayer = players.peekFirst();
        if (enteredValue.equals(MoveState.SURRENDER.name())) {
            setPlayerLose(curPlayer);
            enteredValue = "";
            curPlayer = players.getFirst();
            if (curPlayer.isHuman()) {
                return true;
            }
        }
        if (gameCanGoOn()) {
            MoveState result = curPlayer.process(cities, lastSymbol, enteredValue);
            switch (result) {
                case CORRECT:
                    setCurCity(curPlayer.getEneteredValue());
                    setLastSymbol(curPlayer.getEneteredValue());
                    if (gameCanGoOn()) {
                        backOfQueue(curPlayer);
                        if (!players.getFirst().isHuman()) {
                            processGame("");
                        }
                    } else {
                        setWinnerAndLose(curPlayer);
                    }
                    return true;
                case UNCORRECT:
                    return false;
                case NOMOVE:
                    setPlayerLose(curPlayer);
                    return true;
            }
        } else {
            setWinnerAndLose(curPlayer);

        }
        return false;
    }

    public boolean gameCanGoOn() {
        long count = cities.stream()
                .filter(str -> str.startsWith(lastSymbol))
                .count();
        return (count > 0 && players.size() > 1);
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
