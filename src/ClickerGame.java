
import java.awt.*;
import java.awt.event.*;
import java.io.EOFException;
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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
// Save function
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

class SaveStaticVariables implements Serializable{
    public void saveGame(DataStorage data) {
        try (FileOutputStream fos = new FileOutputStream("src\\save.ser");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class LoadStaticVariables implements Serializable{
    public static DataStorage load() {
        DataStorage data = new DataStorage();
        try (FileInputStream fis = new FileInputStream("src\\save.ser");
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            data = (DataStorage) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }
}

class ClickerBuilding extends Building {
    public ClickerBuilding() {
        super("Clicker", 400, 25, 10, 0.1, 50000, 0, 2000000, 1, "src\\ClickerBuilding.jpg");
    }
}

class CatLover extends Building {
    public CatLover() {
        super("Cat Lover", 400, 125, 100, 10,2000000, 0, 2000000, 10, "src\\catLover.jpg");
    }
}

class CatFactory extends Building {
    public CatFactory() {
        super("Cat Factory", 400, 225, 1500,200, 1000000, 0, 10000000, 30, "src\\catFactory.jpg");
    }
}

class CatTower extends Building {
    public CatTower() {
        super("Cat Tower", 400, 325, 10000, 5000,40000000, 0, 15000000, 120, "src\\catTower.jpg");
    }
}

class CatCity extends Building {
    public CatCity() {
        super("Cat City", 750, 25, 200000, 75000, 50000000L, 0, 20000000, 60, "src\\catCity.jpg");
    }
}

class CatKingdom extends Building {
    public CatKingdom() {
        super("Cat Kingdom", 750, 125, 10000000, 2000000,10000000000L, 0, 30000000, 100, "src\\catKingdom.jpg");
    }
}

class CatEmpire extends Building {
    public CatEmpire() {
        super("Cat Empire", 750, 225, 2500000000L, 10000000,100000000000L, 0, 40000000, 120, "src\\catEmpire.jpg");
    }
}

class CatUniverse extends Building {
    public CatUniverse() {
        super("Cat Universe", 750, 325, 100000000000L, 1000000000, 6000000000000l, 0, 50000000, 70, "src\\catUniverse.jpg");
    }
}
public class ClickerGame extends JFrame implements Serializable 
{
    public static DataStorage data = LoadStaticVariables.load();
    static double score;
    private JLabel scoreLabel;
    private boolean toogle = true;
    JButton clickButton = new JButton(new ImageIcon(("src\\download.jpg")));
    JPanel panel = new JPanel(null);
    private static int buyMode = 1;
    public static ArrayList<Building> buildings = new ArrayList<Building>();
    public static double prestigeMultiplier = data.getPrestigeMultiplier();
    private static double prestigeBaseCost = 1000000;
    private static double prestigeCost = data.getPrestigeCost();
    private static double normalClickerUpgrade = 1;
    public static double godUpgrade = data.getGodUpgrade();
    private static double godUpgradeCost = 10000000000L;
    static JFrame mainWindow = new JFrame();

    
    public static double getPrestigeCost()
    {
        return prestigeCost;
    }
    public static double getPrestigeMultiplier()
    {
        return prestigeMultiplier;
    }
    public static double getGodUpgrade()
    {
        return godUpgrade;
    }

    

    //UpdateScoreLabel
    NumberFormat formatter = NumberFormat.getCompactNumberInstance(Locale.US, NumberFormat.Style.SHORT);
    DecimalFormat df = new DecimalFormat("0.0");
    private void updateScoreLabel() {
        prestigeCost = prestigeBaseCost * Math.pow(1.5, (Math.abs(prestigeMultiplier-1))*10);
        if (score >= 1000) {
            formatter.setMaximumFractionDigits(2);
            scoreLabel.setText("Cat Food: " + formatter.format(score));
        } 
        else 
        {
            scoreLabel.setText("Cat Food: " + df.format(score));
        }
    }

    //Prestige System
    public void prestigeSystem()
    {
        prestigeCost = prestigeBaseCost * Math.pow(1.5, (Math.abs(prestigeMultiplier-1))*10);
        prestigeMultiplier+= 0.1;
        score = 0;
        for (int i = 0; i < buildings.size(); i++)
        {
            buildings.get(i).setLevel(0);
        }
    }
    public static int getBuyMode()
    {
        return buyMode;
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
    public static void playPopCatSound() 
    {
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
        score = data.getScore();
        
        

        //ClickButton
        clickButton = new JButton(new ImageIcon(("src\\download.jpg")));
        clickButton.setBounds(10, 10, 200, 200);
        clickButton.setBorder(new EmptyBorder(0, 0, 0, 0));
        clickButton.setVisible(true);
        clickButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) 
        {
            score+=1 * prestigeMultiplier * normalClickerUpgrade * godUpgrade;
            playPopCatSound();
            catAnimation(clickButton);
            }
        });
        
        //Spinning Rat
        Icon imgIcon = new ImageIcon("src\\rat-spinning.gif");
        JLabel spinningRat = new JLabel(imgIcon);
        spinningRat.setBounds(0, 500, 3030, 200);
        spinningRat.setVisible(true);
        // Update Game every 100ms
        Timer scoreUpdaterTimer = new Timer(100, new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) {
                updateScoreLabel();
                if (spinningRat.getWidth() < 3030)
                {
                    spinningRat.setSize(spinningRat.getWidth()+1, 200);
                }
                else
                {
                    spinningRat.setBounds(0, 500, 200, 200);
                }
                
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
                    + "<p align=\"center\">You currently have " + (ClickerGame.prestigeMultiplier - 1)*10+ " Prestige Gem.</p>"
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
            public void actionPerformed(ActionEvent e) {
            JFrame upgradeWindow = new JFrame("Upgrade Window");

            JPanel upgradePanel = new JPanel();
            upgradePanel.setLayout(null);
            upgradePanel.setPreferredSize(new Dimension(400, panel.getComponentCount() * 50 + 100));
            
            Timer statsUpdateTimer = new Timer(10, new ActionListener() {
                public void actionPerformed(ActionEvent e) 
                {
                    upgradePanel.removeAll();
                    for (Building tower : buildings) 
                    {
                        JButton towerUpgradeButton = new JButton();
                        if (tower.getName() == "Clicker Building")
                        {
                            towerUpgradeButton.setText("<html> Upgrade your Clicker Building and your mouse's efficiency by 2x<br>Current Upgrade: " + tower.getUpgrade() + "<br>Upgrade Cost: " + tower.getUpgradeCost() + "</html>");
                        }
                        else
                        {
                            towerUpgradeButton.setText("<html> Upgrade your "+tower.getName()+"s efficiency by 2x<br>Current Upgrade: " + tower.getUpgrade() + "<br>Upgrade Cost: " + tower.getUpgradeCost() + "</html>");
                        }
                        towerUpgradeButton.addActionListener(new ActionListener() 
                        {
                            public void actionPerformed(ActionEvent e) 
                            {
                                if (tower.getName() == "Clicker Building")
                                {
                                    if (score >= tower.getUpgradeCost())
                                    {
                                        normalClickerUpgrade *= 2;
                                        tower.setUpgrade(tower.getUpgrade() * 2);
                                        tower.setUpgradeCost(Math.pow(tower.getUpgradeCost(), 1.5));
                                    }
                                    else
                                    {
                                        JOptionPane.showMessageDialog(null, "Not enough money to purchase the upgrade. You need " + (tower.getUpgradeCost() -  Math.floor(score * 100) / 100) + " more to purchase the upgrade.");
                                    }
                                }
                                else
                                {
                                    if (score >= tower.getUpgradeCost())
                                    {
                                        tower.setUpgrade(tower.getUpgrade() * 2);
                                        tower.setUpgradeCost(Math.pow(tower.getUpgradeCost(), 1.5));
                                    }
                                    else
                                    {
                                        JOptionPane.showMessageDialog(null, "Not enough money to purchase the upgrade. You need " + (tower.getUpgradeCost() -  Math.floor(score * 100) / 100) + " more to purchase the upgrade.");
                                    }
                                }
                            }
                        });
                        towerUpgradeButton.setBounds(10, upgradePanel.getComponentCount() * 90, 400, 80);
                        upgradePanel.add(towerUpgradeButton);
                    }
                    JButton godUpgradeButton = new JButton();
                    godUpgradeButton.setBounds(10, upgradePanel.getComponentCount() * 90, 400, 80);
                    godUpgradeButton.setText("<html> Exchange " + godUpgradeCost + " cat food with the Cat God for 10x efficiency on all towers and your mouse<br>Current Upgrade: " + godUpgrade + "<br>Upgrade Cost: " + godUpgradeCost + "</html>");
                    godUpgradeButton.addActionListener(new ActionListener() 
                    {
                        public void actionPerformed(ActionEvent e) 
                        {
                            if (score >= godUpgradeCost)
                            {
                                godUpgrade *= 10;
                                score -= godUpgradeCost;
                                godUpgradeCost = Math.pow(godUpgradeCost, 5);
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(null, "Not enough money to purchase the upgrade. You need " + (godUpgradeCost -  Math.floor(score * 100) / 100) + " more to purchase the upgrade.");
                            }
                        }
                    });
                    upgradePanel.add(godUpgradeButton);
                    upgradePanel.repaint();
                }
            });
            statsUpdateTimer.setRepeats(true);
            statsUpdateTimer.start();

            JScrollPane upgradeScrollPane = new JScrollPane(upgradePanel);
            upgradeScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            upgradeScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

            upgradeWindow.add(upgradeScrollPane);
            upgradeWindow.setSize(400, 400);
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

        
        for (int val = 0; val < data.getBuildings().size(); val++)
        {
            if (data.getBuildings() != null && data.getBuildings().size() > 0) 
            {
                if (buildings.get(val).getName() == "Clicker")
                {
                    buildings.get(val).setLevel(data.getBuildings().get(0).getLevel());
                    buildings.get(val).setUpgrade(data.getBuildings().get(0).getUpgrade());
                    buildings.get(val).setUpgradeCost(Math.pow(buildings.get(val).getUpgradeCost(), Math.log(data.getBuildings().get(0).getUpgrade())) / Math.log(2));
                }
                else if (buildings.get(val).getName() == "Cat Lover")
                {
                    buildings.get(val).setLevel(data.getBuildings().get(1).getLevel());
                    buildings.get(val).setUpgrade(data.getBuildings().get(1).getUpgrade());
                    buildings.get(val).setUpgradeCost(Math.pow(buildings.get(val).getUpgradeCost(), Math.log(data.getBuildings().get(1).getUpgrade())) / Math.log(2));

                }
                else if (buildings.get(val).getName() == "Cat Factory")
                {
                    buildings.get(val).setLevel(data.getBuildings().get(2).getLevel());
                    buildings.get(val).setUpgrade(data.getBuildings().get(2).getUpgrade());
                    buildings.get(val).setUpgradeCost(Math.pow(buildings.get(val).getUpgradeCost(), Math.log(data.getBuildings().get(2).getUpgrade())) / Math.log(2));
                }
                else if (buildings.get(val).getName() == "Cat Tower")
                {
                    buildings.get(val).setLevel(data.getBuildings().get(3).getLevel());
                    buildings.get(val).setUpgrade(data.getBuildings().get(3).getUpgrade());
                    buildings.get(val).setUpgradeCost(Math.pow(buildings.get(val).getUpgradeCost(), Math.log(data.getBuildings().get(3).getUpgrade())) / Math.log(2));
                }
                else if (buildings.get(val).getName() == "Cat City")
                {
                    buildings.get(val).setLevel(data.getBuildings().get(4).getLevel());
                    buildings.get(val).setUpgrade(data.getBuildings().get(4).getUpgrade());
                    buildings.get(val).setUpgradeCost(Math.pow(buildings.get(val).getUpgradeCost(), Math.log(data.getBuildings().get(4).getUpgrade())) / Math.log(2));
                }
                else if (buildings.get(val).getName() == "Cat Kingdom")
                {
                    buildings.get(val).setLevel(data.getBuildings().get(5).getLevel());
                    buildings.get(val).setUpgrade(data.getBuildings().get(5).getUpgrade());
                    buildings.get(val).setUpgradeCost(Math.pow(buildings.get(val).getUpgradeCost(), Math.log(data.getBuildings().get(5).getUpgrade())) / Math.log(2));
                }
                else if (buildings.get(val).getName() == "Cat Empire")
                {
                    buildings.get(val).setLevel(data.getBuildings().get(6).getLevel());
                    buildings.get(val).setUpgrade(data.getBuildings().get(6).getUpgrade());
                    buildings.get(val).setUpgradeCost(Math.pow(buildings.get(val).getUpgradeCost(), Math.log(data.getBuildings().get(6).getUpgrade())) / Math.log(2));
                }
                else if (buildings.get(val).getName() == "Cat Universe")
                {
                    buildings.get(val).setLevel(data.getBuildings().get(7).getLevel());
                    buildings.get(val).setUpgrade(data.getBuildings().get(7).getUpgrade());
                    buildings.get(val).setUpgradeCost(Math.pow(buildings.get(val).getUpgradeCost(), Math.log(data.getBuildings().get(7).getUpgrade())) / Math.log(2));
                }
            }
        }
        //Income Timer (Update Income every 1ms)
        Timer incomeTimer = new Timer(1, new ActionListener() {
            public void actionPerformed(ActionEvent e) 
            {
                for (Building val : buildings)
                {
                    if (val.getIncome() > 0)
                    {
                        score += val.totalIncome() / (val.getTime() * 100) * prestigeMultiplier *godUpgrade;
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
        Timer saveTimer = new Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DataStorage data = new DataStorage();
                data.setPrestigeMultiplier(prestigeMultiplier);
                data.setPrestigeCost(prestigeCost);
                data.setScore(score);
                data.setBuildings(ClickerGame.buildings);
                data.setGodUpgrade(godUpgrade);
                SaveStaticVariables save = new SaveStaticVariables();
                save.saveGame(data);
            }
        });
        saveTimer.setRepeats(true);
        saveTimer.start();
        mainWindow.add(panel);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setSize(1280, 800);
        mainWindow.setResizable(false);
        mainWindow.setVisible(true);;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ClickerGame();
            }
        });
    }
}