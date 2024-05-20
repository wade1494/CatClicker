import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

class ClickerBuilding extends Building {
    JButton clickerButton;

    public ClickerBuilding() {
        super(500, 1000, 10, 0.1, 0, 2000000);
        clickerButton = new JButton(new ImageIcon("src\\ClickerBuildingPicture.webp"));
        clickerButton.setHorizontalTextPosition(SwingConstants.RIGHT);
        clickerButton.setBounds(super.getX(), super.getY(), 300, 100);

        ClickerGame.buildings.add(this);

    }

    public void actionPerformed(ActionEvent e) {
        if (ClickerGame.score >= this.getCost()) {
            this.setLevel(this.getLevel() + 1);
            ClickerGame.score -= this.getCost();
        } else {
            JOptionPane.showMessageDialog(null, "Not enough money to purchase the building.");
        }
    };
}

public class ClickerGame extends JFrame {
    static double score;
    private JLabel scoreLabel;
    private boolean toogle = true;
    static ArrayList<Building> buildings = new ArrayList<Building>();

    private void updateScoreLabel() {
        scoreLabel.setText("Score: " + score);
    }

    public void playPopCatSound() {
        try {
            File soundFile = new File("src\\popcatSound.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            clip.addLineListener(new LineListener() {
                public void update(LineEvent event) {
                    if (event.getType() == LineEvent.Type.STOP) {
                        clip.close();
                        try {
                            audioInputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (Exception a) {
            a.printStackTrace();
        }
    }

    public ClickerGame() {
        score = 0;

        JButton clickButton = new JButton(new ImageIcon("src\\download.jpg"));
        clickButton.setBorder(new EmptyBorder(0, 0, 0, 0));
        clickButton.setPreferredSize(new Dimension(200, 200));
        clickButton.setLocation(500, 500);
        clickButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playPopCatSound();
                clickButton.setIcon(new ImageIcon("src\\images.jpg"));
                toogle = !toogle;
                score++;
                updateScoreLabel();

                Timer timer = new Timer(100, new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        clickButton.setIcon(new ImageIcon("src\\download.jpg"));
                        toogle = true;
                    }
                });
                timer.setRepeats(false);
                timer.start();
            }
        });

        scoreLabel = new JLabel("Score: 0");

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(clickButton);
        panel.add(scoreLabel);

        ClickerBuilding clickerBuilding = new ClickerBuilding();
        panel.add(clickerBuilding.clickerButton);
        add(panel);
        Timer time = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (Building building : buildings) {
                    score += building.totalIncome();
                }
                updateScoreLabel();
            }
        });
        time.setRepeats(true);
        time.start();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Clicker Game");
        setSize(1000, 5000);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ClickerGame();
            }
        });
    }
}