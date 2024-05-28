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

//Building Class
class ClickerBuilding extends Building {
    public ClickerBuilding() {
        super("Clicker", 400, 25, 10, 0.1, 0, 2000000, 1, "src\\ClickerBuilding.jpg");
    }
}

class CatLover extends Building {
    public CatLover() {
        super("Cat Lover", 400, 125, 100, 10, 0, 2000000, 10, "src\\catLover.jpg");
    }
}

class CatFactory extends Building {
    public CatFactory() {
        super("Cat Factory", 400, 225, 1500, 200, 0, 10000000, 30, "src\\catFactory.jpg");
    }
}

class CatTower extends Building {
    public CatTower() {
        super("Cat Tower", 400, 325, 10000, 5000, 0, 15000000, 120, "src\\catTower.jpg");
    }
}

class CatCity extends Building {
    public CatCity() {
        super("Cat City", 750, 25, 200000, 75000, 0, 20000000, 60, "src\\catCity.jpg");
    }
}

class CatKingdom extends Building {
    public CatKingdom() {
        super("Cat Kingdom", 750, 125, 10000000, 2000000, 0, 30000000, 100, "src\\catKingdom.jpg");
    }
}

class CatEmpire extends Building {
    public CatEmpire() {
        super("Cat Empire", 750, 225, 2500000000L, 10000000, 0, 40000000, 120, "src\\catEmpire.jpg");
    }
}

class CatUniverse extends Building {
    public CatUniverse() {
        super("Cat Universe", 750, 325, 100000000000L, 1000000000, 0, 50000000, 70, "src\\catUniverse.jpg");
    }
}
public class ClickerGame extends JFrame {
    static double score;
    private JLabel scoreLabel;
    private boolean toogle = true;
    JButton clickButton = new JButton(new ImageIcon(("src\\download.jpg")));
    JPanel panel = new JPanel(null);
    private static int buyMode = 1;
    static ArrayList<Building> buildings = new ArrayList<Building>();
    private static int prestigePoints = 0;
    public static double prestigeMultiplier = 1.5;
    private static double prestigeBaseCost = 1000000;
    private static double prestigeCost = 1000000;
    JFrame mainWindow = new JFrame();

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

    //Prestige System
    public void prestigeSystem()
    {
        prestigeCost = prestigeBaseCost * Math.pow(1.5, prestigePoints);
        prestigePoints++;
        prestigeMultiplier = 1 + prestigePoints / 10;
        score = 0;
    }
    public static int getBuyMode()
    {
        return buyMode;
    }
    public static int getPresigePoint()
    {
        return prestigePoints;
    }
    public static double getPresigeMultiplier()
    {
        return prestigeMultiplier;
    }
    public static void buyMultipleTime(int x, Building building)
    {
        if (ClickerGame.score >= building.getCost()*x)
        {
            score-=building.getCost()*x;
        }
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
        JLabel background = new JLabel(new ImageIcon("src\\background.jpg"));
        background.setBounds(0, 0, 1280, 800);
        background.setVisible(true);
        score = 0;

        //ClickButton
        clickButton = new JButton(new ImageIcon(("src\\download.jpg")));
        clickButton.setBounds(10, 10, 200, 200);
        clickButton.setBorder(new EmptyBorder(0, 0, 0, 0));
        clickButton.setVisible(true);
        clickButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) 
        {
            score+=100 * prestigeMultiplier;
            playPopCatSound();
            catAnimation(clickButton);
            }
        });
        
        //Spinning Rat
        Icon imgIcon = new ImageIcon("src\\rat-spinning.gif");
        JLabel spinningRat = new JLabel(imgIcon);
        spinningRat.setBounds(400, 200, 200, 200);
        spinningRat.setVisible(false);

        // Update Game every 100ms
        Timer scoreUpdaterTimer = new Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateScoreLabel();
                
                spinningRat.setVisible(score >= 1000000);
            }
        });
        scoreUpdaterTimer.setRepeats(true);
        scoreUpdaterTimer.start();

        //Stats Button (Finish)
        JButton statsButton = new JButton("Building Stats");
        statsButton.setBounds(215, 35, 85, 20);
        statsButton.setToolTipText("Building Stats");
        statsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            JFrame statsWindow = new JFrame("Building Stats Window");

            JPanel statsPanel = new JPanel();
            statsPanel.setLayout(null);
            statsPanel.setPreferredSize(new Dimension(400, panel.getComponentCount() * 50 + 100));

            Timer statsUpdateTimer = new Timer(10, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                statsPanel.removeAll();
                double totalIncomePerSecond = 0;
                for (Building tower : buildings) {
                    JLabel towerLabel = new JLabel("<html>Tower Name: " + tower.getName() +
                    "<br>Income per Second: " + tower.totalIncome()/tower.getTime() +
                    "<br>Level: " + tower.getLevel() +
                    "<br><br></html>");
                    towerLabel.setIcon(new ImageIcon(tower.getImageURL()));
                    towerLabel.setBounds(10, statsPanel.getComponentCount() * 90, 400, 80);
                    statsPanel.add(towerLabel);
                    totalIncomePerSecond += tower.totalIncome()/tower.getTime();
                }
                JLabel totalIncomeLabel = new JLabel("Total Income per Second: " + totalIncomePerSecond);
                totalIncomeLabel.setBounds(10, statsPanel.getComponentCount() * 90, 400, 80);
                statsPanel.add(totalIncomeLabel);
                statsPanel.repaint();
                }
            });
            statsUpdateTimer.setRepeats(true);
            statsUpdateTimer.start();

            JScrollPane statsScrollPane = new JScrollPane(statsPanel);
            statsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            statsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

            statsWindow.add(statsScrollPane);
            statsWindow.setSize(400, 400);
            statsWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            statsWindow.setLocationRelativeTo(null);
            statsWindow.setVisible(true);
            statsWindow.setResizable(false);
            }
        });
        //PrestigeButton (FINISH)
        JButton prestigeButton = new JButton("Prestige");
        prestigeButton.setBounds(215, 10, 85, 20);
        prestigeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                formatter.setMaximumFractionDigits(2);
                JFrame newWindow = new JFrame("Prestige Window");

                JLabel catGod1 = new JLabel(new ImageIcon("src\\catPrestigePicture.jpg"));
                JLabel catGod2 = new JLabel(new ImageIcon("src\\catPrestigePicture.jpg"));

                catGod1.setBounds(0, 10, 91, 200);
                catGod2.setBounds(300, 10, 91, 200);

                JLabel prestigeLabel = new JLabel("<html>"
                    + "<p align=\"center\"><strong>Are you ready to Prestige?</strong></p>"
                    + "<p align=\"center\">Prestiging will reset your progress in exchange for Prestige Points and Prestige Gem.</p>"
                    + "<p align=\"center\">Each Prestige Gem gives you a permanent 10% boost to your cookie production.</p>"
                    + "<p align=\"center\">You can use Prestige Points to buy buildings upgrade.</p>"
                    + "<p align=\"center\">You currently have " + (ClickerGame.getPresigeMultiplier() - 1)*10+ " Prestige Gem.</p>"
                    + "<p align=\"center\">The cost of prestige is " + formatter.format(prestigeCost) + " Cat Food.</p>"
                    + "</html>");
                prestigeLabel.setBounds(100, 0, 200, 200);
                prestigeLabel.setFont(new Font("Arial", Font.BOLD, 11));
                prestigeLabel.setHorizontalAlignment(SwingConstants.CENTER);
                prestigeLabel.setVerticalAlignment(SwingConstants.CENTER);
 
                JButton prestigeConfirmButton = new JButton("PRESTIGE!!!");
                prestigeConfirmButton.setBounds(119, 200, 150, 50);
                prestigeConfirmButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (score >= prestigeCost)
                            prestigeSystem();
                            newWindow.dispose();
                    }
                });

                // Repaint every 10ms
                Timer repaintTimer = new Timer(10, new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        newWindow.repaint();
                    }
                });
                repaintTimer.setRepeats(true);
                repaintTimer.start();
                newWindow.add(catGod1);
                newWindow.add(catGod2);
                newWindow.setSize(400, 300);
                newWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                newWindow.setLocationRelativeTo(null);
                newWindow.setLayout(null);
                newWindow.add(prestigeConfirmButton);
                newWindow.add(prestigeLabel);
                newWindow.setVisible(true);
                newWindow.setResizable(false);
            }
        });

        //UpgradeWindowButton (WIP)
        JButton upgradeButton = new JButton("Upgrade");
        upgradeButton.setBounds(215, 60, 85, 20);
        upgradeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
            JFrame upgradeWindow = new JFrame("Upgrade Window");

            JButton upgradeClicker = new JButton();
            JButton upgradeCatLover = new JButton();

            upgradeWindow.setSize(400, 300);
            upgradeWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            upgradeWindow.setLocationRelativeTo(null);
            upgradeWindow.setVisible(true);
            upgradeWindow.setResizable(false);
            }
        });

        //BuyModeButton
        JButton x1Button = new JButton("1x");
        x1Button.setForeground(Color.YELLOW);
        JButton x10Button = new JButton("10x");
        JButton x100Button = new JButton("100x");
        JButton maxButton = new JButton("MAX");



        x1Button.setBounds(0, 250, 70, 20);
        x1Button.setFont(new Font(" DIALOG", Font.BOLD, 15));
        x1Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                x1Button.setForeground(Color.YELLOW);
                x10Button.setForeground(Color.BLACK);
                x100Button.setForeground(Color.BLACK);
                maxButton.setForeground(Color.BLACK);
                buyMode = 1;
            }
        });

        x10Button.setBounds(80, 250, 70, 20);
        x10Button.setFont(new Font("Arial", Font.BOLD, 15));
        x10Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                x1Button.setForeground(Color.BLACK);
                x10Button.setForeground(Color.YELLOW);
                x100Button.setForeground(Color.BLACK);
                maxButton.setForeground(Color.BLACK);
                buyMode = 10;
            }
        });

        x100Button.setBounds(160, 250, 70, 20);
        x100Button.setFont(new Font("Arial", Font.BOLD, 15));
        x100Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                x1Button.setForeground(Color.BLACK);
                x10Button.setForeground(Color.BLACK);
                x100Button.setForeground(Color.YELLOW);
                maxButton.setForeground(Color.BLACK);
                buyMode = 100;
            }
        });

        maxButton.setBounds(240, 250, 70, 20);
        maxButton.setFont(new Font("Arial", Font.BOLD, 15));
        maxButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                x1Button.setForeground(Color.BLACK);
                x10Button.setForeground(Color.BLACK);
                x100Button.setForeground(Color.BLACK);
                maxButton.setForeground(Color.YELLOW);
                buyMode = -1;
            }
        });

        //ScoreLabel
        scoreLabel = new JLabel("Cat Food: " +  score);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 15));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setBounds(clickButton.getX() + 55, clickButton.getY() + 170, 200, 100);

        

        //ClickerBuilding
        ClickerBuilding clickerBuilding = new ClickerBuilding();
        clickerBuilding.setUpButton(panel, clickerBuilding);

        //CatLover
        CatLover catLover = new CatLover();
        catLover.setUpButton(panel, catLover);

        //CatFactory
        CatFactory catFactory = new CatFactory();
        catFactory.setUpButton(panel, catFactory);

        //CatTower
        CatTower catTower = new CatTower();
        catTower.setUpButton(panel, catTower);

        //CatCity
        CatCity catCity = new CatCity();
        catCity.setUpButton(panel, catCity);

        //CatKingdom
        CatKingdom catKingdom = new CatKingdom();
        catKingdom.setUpButton(panel, catKingdom);

        //CatEmpire
        CatEmpire catEmpire = new CatEmpire();
        catEmpire.setUpButton(panel, catEmpire);

        //CatUniverse
        CatUniverse catUniverse = new CatUniverse();
        catUniverse.setUpButton(panel, catUniverse);

        //Income Timer (Update Income every 1ms)
        Timer incomeTimer = new Timer(1, new ActionListener() {
            public void actionPerformed(ActionEvent e) 
            {
                for (Building val : buildings)
                {
                    if (val.getIncome() > 0)
                    {
                        score += val.totalIncome() / (val.getTime() * 100) * prestigeMultiplier;
                    }
                }
            }
        });
        incomeTimer.setRepeats(true);
        incomeTimer.start();

        //Panel
        panel.add(clickButton);
        panel.add(scoreLabel);
        panel.add(spinningRat);
        panel.add(statsButton);
        panel.add(prestigeButton);
        panel.add(upgradeButton);
        panel.add(x1Button);
        panel.add(x10Button);
        panel.add(x100Button);
        panel.add(maxButton);
        panel.add(background);
        //Frame
        
        mainWindow.add(panel);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setSize(1280, 800);
        mainWindow.setResizable(false);
        mainWindow.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ClickerGame();
            }
        });
    }
}