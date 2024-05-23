import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.Flow;

import javax.sound.sampled.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Dimension;
class ClickerBuilding extends Building
{
    JButton clickerButton;

    public ClickerBuilding() {
        super(300, 200, 10, 0.1, 0, 2000000);
        ClickerGame.buildings.add(this);

    }
    
}

public class ClickerGame extends JFrame {
    static double score;
    private JLabel scoreLabel;
    private boolean toogle = true;
    static ArrayList<Building> buildings = new ArrayList<Building>();
    int upgradeScore;
    //UpdateScoreLabel
    NumberFormat formatter = NumberFormat.getCompactNumberInstance(Locale.US, NumberFormat.Style.SHORT);
    DecimalFormat df = new DecimalFormat("0.0");
    private void updateScoreLabel() {
        
        if (score >= 1000) {
            formatter.setMaximumFractionDigits(2);
            scoreLabel.setText("Cat Food: " + formatter.format(score));
        } else 
        {
            scoreLabel.setText("Cat Food: " + df.format(score));
        }
    }

    //CatAnimation
    public void catAnimation(JButton button)
    {
        button.setIcon(new ImageIcon("src\\images.jpg"));
        toogle = !toogle;
        
        updateScoreLabel();

        Timer timer = new Timer(100, new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                button.setIcon(new ImageIcon("src\\download.jpg"));
                toogle = true;
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    //PopCatSound
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
    //Game Constructor
    public ClickerGame() {
        score = 0;
        upgradeScore = 0; //Implement later if have time
        //ClickButton
        JButton clickButton = new JButton(new ImageIcon(("src\\download.jpg")));
        clickButton.setBounds(0, 0, 200, 200);
        clickButton.setBorder(new EmptyBorder(0, 0, 0, 0));
        clickButton.setVisible(true);
        
        clickButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) 
        {
            score++;
            playPopCatSound();
            catAnimation(clickButton);
            }
        });

        //Spinning Rat
        Icon imgIcon = new ImageIcon("src\\rat-spinning.gif");
        JLabel spinningRat = new JLabel(imgIcon);
        spinningRat.setBounds(400, 200, 200, 200);
        spinningRat.setVisible(false);

        //ScoreLabel
        scoreLabel = new JLabel("Cat Food: " +  score);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 15));
        scoreLabel.setBounds(clickButton.getX() + 55, clickButton.getY() + 170, 200, 100);

        

        //ClickerBuilding
        ClickerBuilding clickerBuilding = new ClickerBuilding();
        JButton clickerBuildingButton = new JButton("<html>Clicker Building<br>Cost: 10 Cat Food<br>Income: 0.1 Per Second</html>");
        clickerBuildingButton.setIcon(new ImageIcon("src\\ClickerBuilding.jpg"));
        clickerBuildingButton.setBounds(clickerBuilding.getX(), clickerBuilding.getY(), 300, 75);
        clickerBuildingButton.setVisible(true);
        clickerBuildingButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) {
                if (ClickerGame.score >= clickerBuilding.getCost())
                {
                    clickerBuilding.setLevel(clickerBuilding.getLevel() + 1);
                    ClickerGame.score -= clickerBuilding.getCost();
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Not enough money to purchase the building. You need " + (clickerBuilding.getCost() -  Math.floor(score * 100) / 100) + " more to purchase the building.");
                }
            };
        });

        //Income Timer System
        Timer time = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    for (Building building : buildings) {
                        if (building.totalIncome() > 0.0)
                        {
                            playPopCatSound();
                            catAnimation(clickButton);
                            
                        }
                        score += building.totalIncome();
                    }
                    updateScoreLabel();
            }
        });
        time.setRepeats(true);
        time.start();

        //Panel
        JPanel panel = new JPanel(null);
        panel.add(clickButton);
        panel.add(scoreLabel);
        panel.add(spinningRat);
        panel.add(clickerBuildingButton);
        
        //Frame
        add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setResizable(false);
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