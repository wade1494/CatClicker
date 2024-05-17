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
    public Building(int x, int y, int cost, double income, int level, int maxLevel)
    {
        this.x = x;
        this.y = y;
        this.cost = cost;
        this.income = income;
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
    
}