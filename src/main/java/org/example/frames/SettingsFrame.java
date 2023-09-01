package org.example.frames;

import org.example.utils.GameMode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsFrame extends JFrame implements ActionListener {
    JRadioButton r1;
    JRadioButton r2;
    JLabel lb;
    JButton b;
    private GameMode gameMode;
    final Font font = new Font(Font.DIALOG, Font.BOLD, 15);
    GreetingFrame greetingFrame;

    public SettingsFrame(GreetingFrame greetingFrame) {
        super("Налаштування гри");
        this.greetingFrame = greetingFrame;
        drawElements();
        configureFrame();
    }

    private void drawElements() {
        r1 = new JRadioButton("Online");
        r1.setBounds(30,20,100,30);
        r1.setFont(font);
        r1.addActionListener(this);
        r1.setSelected(true);

        r2 = new JRadioButton("Offline");
        r2.setBounds(30,50,100,30);
        r2.setFont(font);
        r2.addActionListener(this);

        ButtonGroup bg = new ButtonGroup();
        bg.add(r1);
        bg.add(r2);

        b = new JButton("Застосувати");
        b.setBounds(150, 30, 60, 30);
        b.setFont(font);
        b.addActionListener(this);

        add(r1);
        add(r2);
        add(b);
    }

    private void configureFrame() {
        setSize(250, 150);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b) {
            if (r1.isSelected()){
                greetingFrame.setGameMode(GameMode.ONLINE);
            }
            if (r2.isSelected()){
                greetingFrame.setGameMode(GameMode.OFFLINE);
            }
            dispose();
        }
    }
}
