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
    private int cost;
    private double income;
    private int level;
    private int maxLevel;
    private int time;
    public Building(int x, int y, int cost, double income, int level, int maxLevel, int time)
    {
        this.x = x;
        this.y = y;
        this.cost = cost;
        this.income = income;
        this.level = level;
        this.maxLevel = maxLevel;
        this.time = time;
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getCost() {
        return cost;
    }

    public double getIncome() {
        return income;
    }
    
    public double totalIncome()
    {
        return income * level;
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
    public void setUpButton(JPanel panel, Building building, String imageURL)
    {
        JButton button = new JButton();
        button.setBounds(building.getX(), building.getY(), 300, 75);
        button.setText("<html>Clicker Building<br>Cost: " + building.getCost() + " Cat Food<br>Income: " + building.getIncome() + " Per "+ building.getTime()+ " second </html>");
        button.setIcon(new ImageIcon(imageURL));
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (ClickerGame.score >= cost && level < maxLevel) {
                    ClickerGame.score -= cost;
                    building.setLevel(level + 1);
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Not enough money to purchase the building. You need " + (building.getCost() -  Math.floor(ClickerGame.score * 100) / 100) + " more to purchase the building.");
                }
            }
        });
        panel.add(button);
    }
}