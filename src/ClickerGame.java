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
    public ClickerBuilding() 
    {
        super(300, 25, 10, 0.1, 0, 2000000, 1);
        ClickerGame.buildings.add(this);
    }
}
class CatLover extends Building
{
    public CatLover() 
    {
        super(300, 125, 100, 100, 0, 2000000,10);
        ClickerGame.buildings.add(this);
    }
}
public class ClickerGame extends JFrame {
    static double score;
    private JLabel scoreLabel;
    private boolean toogle = true;
    JButton clickButton = new JButton(new ImageIcon(("src\\download.jpg")));
    JPanel panel = new JPanel(null);
    static ArrayList<Building> buildings = new ArrayList<Building>();
    static int scoreMultiplier;
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
    public void setUpIncomeTimer(Building building)
    {
        Timer timer = new Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent e) 
            {
                for (Building val : buildings)
                {
                    if (building == val)
                    {
                    score += building.totalIncome() / (building.getTime() * 10);
                    }
                }
            }
        });
        timer.setRepeats(true);
        timer.start();
    }
    //CatAnimation
    public void catAnimation(JButton button)
    {
        button.setIcon(new ImageIcon("src\\images.jpg"));
        toogle = !toogle;
        

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
    public static void playPopCatSound() {
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
        scoreMultiplier = 0; //Implement later if have time

        //ClickButton
        clickButton = new JButton(new ImageIcon(("src\\download.jpg")));
        clickButton.setBounds(10, 10, 200, 200);
        clickButton.setBorder(new EmptyBorder(0, 0, 0, 0));
        clickButton.setVisible(true);
        clickButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) 
        {
            score+=100;
            playPopCatSound();
            catAnimation(clickButton);
            }
        });

        //Spinning Rat
        Icon imgIcon = new ImageIcon("src\\rat-spinning.gif");
        JLabel spinningRat = new JLabel(imgIcon);
        spinningRat.setBounds(400, 200, 200, 200);
        spinningRat.setVisible(false);

        // Update Score every 100ms
        Timer timer = new Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateScoreLabel();
                if (score >= 1000000) 
                {
                    spinningRat.setVisible(true);
                }
            }
        });
        timer.setRepeats(true);
        timer.start();
        

        //ScoreLabel
        scoreLabel = new JLabel("Cat Food: " +  score);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 15));
        scoreLabel.setBounds(clickButton.getX() + 55, clickButton.getY() + 170, 200, 100);

        

        //ClickerBuilding
        ClickerBuilding clickerBuilding = new ClickerBuilding();
        clickerBuilding.setUpButton(panel, clickerBuilding, "src\\ClickerBuilding.jpg");
        setUpIncomeTimer(clickerBuilding);

        //CatLover
        CatLover catLover = new CatLover();
        catLover.setUpButton(panel, catLover, "src\\catLover.jpg");
        setUpIncomeTimer(catLover);
        //Panel
        
        panel.add(clickButton);
        panel.add(scoreLabel);
        panel.add(spinningRat);
        
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