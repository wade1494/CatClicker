import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
public class Building
{
    private int x;
    private int y;
    private double cost;
    private double income;
    private int level;
    private int maxLevel;
    private int time;
    private String name;
    private String imageURL;
    public Building(String name, int x, int y, double cost, double income, int level, int maxLevel, int time, String imageURL)
    {
        this.imageURL = imageURL;
        this.name = name;
        this.x = x;
        this.y = y;
        this.cost = cost;
        this.income = income;
        this.level = level;
        this.maxLevel = maxLevel;
        this.time = time;
        ClickerGame.buildings.add(this);
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getCost() {
        return cost;
    }

    public double getIncome() {
        return income;
    }
    
    public double totalIncome()
    {
        return income * level * ClickerGame.prestigeMultiplier;
    }
    public int getLevel() {
        return level;
    }

    public int getMaxLevel() {
        return maxLevel;
    }
    public void setLevel(int level)
    {
        this.level = level;
    }
    public int getTime() {
        return time;
    }
    public String getName()
    {
        return name;
    }
    public String getImageURL()
    {
        return imageURL;
    }
    public void setUpButton(JPanel panel, Building building)
    {
        JButton button = new JButton();
        button.setBounds(building.getX(), building.getY(), 300, 75);
        button.setText("<html>" + building.getName() + "<br>Cost: " + building.getCost() + " Cat Food<br>Income: " + building.getIncome() + " Per "+ building.getTime()+ " second </html>");
        button.setIcon(new ImageIcon(building.getImageURL()));
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (ClickerGame.getBuyMode() != -1)
                {
                    if (ClickerGame.score >= building.getCost() * ClickerGame.getBuyMode() && building.getLevel() + ClickerGame.getBuyMode() < building.getMaxLevel()) 
                    {
                        ClickerGame.score -= cost * ClickerGame.getBuyMode();
                        building.setLevel(building.getLevel() + ClickerGame.getBuyMode());
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Not enough money to purchase the building. You need " + (cost -  Math.floor(ClickerGame.score * 100) / 100) + " more to purchase the building.");
                    }
                }
                else
                {
                    if (ClickerGame.score >= building.getCost())
                    {
                        int maxBuy = (int) (ClickerGame.score / building.getCost());
                        ClickerGame.score -= building.getCost() * maxBuy;
                        building.setLevel(building.getLevel() + maxBuy);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Not enough money to purchase the building. You need " + (building.getCost() -  Math.floor(ClickerGame.score * 100) / 100) + " more to purchase the building.");
                    }
                }
            }
        });
        Timer updateTimer = new Timer(100, new ActionListener() 
        {
            public void actionPerformed(ActionEvent e)
            {
                if (ClickerGame.getBuyMode() != -1)
                {
                    button.setText("<html>" + building.getName() + "<br>Cost: " + building.getCost()*ClickerGame.getBuyMode() + " Cat Food<br>Income: " + building.getIncome() + " Per "+ building.getTime()+ " second </html>");
                }
                else if (ClickerGame.score >= building.getCost())
                {
                    int maxBuy = (int) (ClickerGame.score / building.getCost());
                    button.setText("<html>" + building.getName() + "<br>Cost: " + building.getCost()*maxBuy + " Cat Food<br>Income: " + building.getIncome() + " Per "+ building.getTime()+ " second </html>");
                }
            }
        });
        updateTimer.start();
        updateTimer.setRepeats(true);
        panel.add(button);
    }
}