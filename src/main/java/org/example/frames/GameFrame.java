package org.example.frames;

import org.example.gameclases.CityGame;
import org.example.gameclases.Player;
import org.example.utils.CitiesOfUkraine;
import org.example.utils.GameMode;
import org.example.utils.MoveState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameFrame extends JFrame implements ActionListener {
    static JTextField tfInputCity;
    static JButton btEnterCity;
    static JButton btStart;
    static JButton btSurrender;
    static JLabel lbComment;
    static JLabel lbComp;
    static JLabel lbStatus;
    static JTextArea taSummary;
    final Font font = new Font(Font.DIALOG, Font.BOLD, 15);
    private static boolean gameStarted = false;
    private List<String> cities;
    private static CityGame cityGame;
    private static ScheduledExecutorService executorService;
    private static GameMode gameMode;
    private static Deque<Player> players;

    public GameFrame(GameMode gameMode, Deque<Player> players) {
        super("Успіхів!");
        this.gameMode = gameMode;
        this.players = players;
        drawFrameElements();
        configureFrame();
    }

    private void drawFrameElements() {
        tfInputCity = new JTextField();
        tfInputCity.setBounds(30, 30, 150, 30);
        tfInputCity.setFont(font);

        btEnterCity = new JButton("Зробити хід");
        btEnterCity.setBounds(30, 70, 150, 30);
        btEnterCity.setFont(font);
        btEnterCity.setEnabled(false);
        btEnterCity.addActionListener(this);

        btStart = new JButton("Старт!");
        btStart.setBounds(30, 110, 100, 30);
        btStart.setFont(font);
        btStart.addActionListener(this);

        btSurrender = new JButton("Здаюся!");
        btSurrender.setBounds(130, 110, 100, 30);
        btSurrender.setFont(font);
        btSurrender.addActionListener(this);

        lbComment = new JLabel("Введіть назву міста");
        lbComment.setBounds(200, 30, 250, 30);
        lbComment.setFont(font);

        lbComp = new JLabel("Комп'ютер:");
        lbComp.setBounds(200, 70, 250, 30);
        lbComp.setFont(font);

        //lbStatus = new JLabel("Місто введене не вірно");
        lbStatus = new JLabel("");
        lbStatus.setBounds(240, 110, 200, 30);

        taSummary = new JTextArea();
        taSummary.setBounds(410, 30, 160, 100);

        add(tfInputCity);
        add(btEnterCity);
        add(btStart);
        add(btSurrender);
        add(lbComment);
        add(lbComp);
        add(lbStatus);
        add(taSummary);
    }

    private void configureFrame() {
        setSize(600, 190);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void startGame() throws IOException {
        if (gameMode.equals(GameMode.ONLINE)) {
            cities = CitiesOfUkraine.getCityNames();
        } else {
            cities = CitiesOfUkraine.getCityNames();
        }

        cityGame = new CityGame(cities, players);
        //cityGame.start();

        setComponentValues(true, "Стоп");

        executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(GameFrame::checkStatusGame, 0, 1, TimeUnit.SECONDS);
    }

    private static void stopGame() {
        executorService.shutdown();
        //cityGame.interrupt();
        setComponentValues(false, "Старт!");
        setLabelDefaultText();
        System.out.println("Stop game");
    }

    private static void checkStatusGame() {
        Player curPlayer = players.getFirst();
        taSummary.setText(cityGame.checkPlayersStatus());
        lbComp.setText(curPlayer.isHuman() ? "Комп'ютер: " + cityGame.getCurCity() : "Комп'ютер: ");
        lbComment.setText(curPlayer.getName() + " вам на " + "\"" + cityGame.getLastSymbol() + "\"");
        if (!cityGame.gameCanGoOn()) {
            stopGame();
        }
//        String playerName = "";
//        if (human.isCanMove()) {
//            playerName = human.getName();
//            lbComp.setText("Комп'ютер: " + cityGame.getCurCity());
//        }
//        if (comp.isCanMove()) {
//            playerName = comp.getName();
//        }
//        lbComment.setText(playerName + " вам на " + "\"" + cityGame.getLastSymbol() + "\"");
//        if (!cityGame.getLastSymbol().isEmpty()) {
//            lbStatus.setText("На \"" + cityGame.getLastSymbol() + "\" ще " + cityGame.checkStatus() + " міст");
//        }
//
//        if (cityGame.checkStatus() == 0) {
//            if (human.isCanMove()) {
//                lbStatus.setText("Переміг " + comp.getName());
//            }
//            if (human.isCanMove()) {
//                lbStatus.setText("Переміг " + comp.getName());
//            }
//            stopGame();
//        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btEnterCity) {
            if (players.getFirst().isHuman()) {
                boolean result = cityGame.processGame(tfInputCity.getText());
                if (result) {
                    tfInputCity.setText("");
                    lbStatus.setText("");
                } else {

                }
                lbStatus.setText("Місто введене не вірно");
            }
//            if (human.isCanMove()) {
//                checkEnteredValue();
//            }
        } else if (e.getSource() == btSurrender) {
            cityGame.processGame(MoveState.SURRENDER.name());
        } else if (e.getSource() == btStart) {
            if (!gameStarted) {
                try {
                    startGame();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                stopGame();
            }
        }
    }

    private static void setComponentValues(boolean action, String text) {
        gameStarted = action;
        btStart.setText(text);
        btEnterCity.setEnabled(action);
    }

    private static void setLabelDefaultText() {
        lbComment.setText("Введіть назву міста");
        lbComp.setText("Комп'ютер:");
        //lbStatus.setText("");
    }
}
