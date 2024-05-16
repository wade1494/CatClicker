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
    private int income;
    private int level;
    private int maxLevel;
    public Building(int x, int y, int cost, int income, int level, int maxLevel)
    {
        this.x = x;
        this.y = y;
        this.cost = cost;
        this.baseIncome = income;
        this.level = level;
        this.maxLevel = maxLevel;
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

    public int getIncome() {
        return baseIncome;
    }
    public int totalIncome()
    {
        return baseIncome * level;
    }
    public int getLevel() {
        return level;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public int getUpgradeCost() {
        return upgradeCost;
    }
    public drawButton(int currentMoney, String image)
    {
        JButton clickButton = new JButton(image);
        clickButton.setBorder(new EmptyBorder(0, 0, 0, 0));
        clickButton.setPreferredSize(new Dimension(200, 200));
        clickButton.setLocation(500, 500);
        clickButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) 
        {
            if (currentMoney >= cost && level < maxLevel)
            {
                currentMoney -= cost;
                level++;
            }
        }
    });
    }
}