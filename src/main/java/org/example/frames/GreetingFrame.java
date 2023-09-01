package org.example.frames;

import org.example.gameclases.Comp;
import org.example.gameclases.Gamer;
import org.example.gameclases.Human;
import org.example.gameclases.Player;
import org.example.utils.GameMode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Queue;

public class GreetingFrame extends JFrame implements ActionListener {
    JMenuBar menuBar;
    JMenu mainMenu;
    JMenuItem settingsMenu;
    JLabel lb;
    JButton b;
    final Font font = new Font(Font.DIALOG, Font.BOLD, 15);
    private GameMode gameMode = GameMode.OFFLINE;
    private Deque<Gamer> players = new ArrayDeque<>();

    public GreetingFrame() {
        super("Вітаємо!");
        drawElements();
        configureFrame();
        setDefaultPlayers();
    }

    private void drawElements() {
        menuBar = new JMenuBar();
        mainMenu = new JMenu("Налаштування");
        settingsMenu = new JMenuItem("Варіант гри");
        mainMenu.add(settingsMenu);
        menuBar.add(mainMenu);
        settingsMenu.addActionListener(this);

        lb = new JLabel("Вітаємо вас у грі дитинства і всіх розумників!");
        lb.setBounds(30, 30, 350, 30);
        lb.setFont(font);

        b = new JButton("OK");
        b.setBounds(380, 30, 60, 30);
        b.setFont(font);
        b.addActionListener(this);

        setJMenuBar(menuBar);
        add(lb);
        add(b);
    }

    public void configureFrame() {
        setSize(480, 150);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void setDefaultPlayers() {
        players.add(new Human("Alex", true));
        players.add(new Comp());
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b) {
            System.out.println("gameMode = " + gameMode);
            dispose();
            new GameFrame(gameMode, players);
        } else if (e.getSource() == settingsMenu) {
            new SettingsFrame(this);
        }
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
//        String hello = "Вітаємо вас у грі дитинства і всіх розумників!";
//        String helloConvert = new String(hello.getBytes("windows-1251"), "UTF-8");
        new GreetingFrame();
    }
}
