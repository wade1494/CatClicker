import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ClickerGame extends JFrame {
    private int score;
    private JLabel scoreLabel;
    private boolean toogle = true;
    private void updateScoreLabel() {
        scoreLabel.setText("Score: " + score);
    }
    public void playPopCatSound()
    {
        try 
        {
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
        } 
        catch (Exception a) 
        {
            a.printStackTrace();
        }
    }
    public ClickerGame() 
    {
        score = 0;

        JButton clickButton = new JButton(new ImageIcon("src\\download.jpg"));
        clickButton.setBorder(new EmptyBorder(0, 0, 0, 0));
        clickButton.setPreferredSize(new Dimension(200, 200));
        clickButton.setLocation(500, 500);
        clickButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) 
        {
            playPopCatSound();
            clickButton.setIcon(new ImageIcon("src\\images.jpg"));
            toogle = !toogle;
            score++;
            updateScoreLabel();

            Timer timer = new Timer(100, new ActionListener() 
            {
                public void actionPerformed(ActionEvent e) 
                {
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

        add(panel);

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