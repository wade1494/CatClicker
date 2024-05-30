import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public  class DataStorage implements Serializable {
    private static final long serialVersionUID = 1L;
    private double prestigeMultiplier = 1;
    private double prestigeCost = 1000000;
    private double score = 0;
    private ArrayList<Building> buildings = new ArrayList<Building>();
    private double godUpgrade = 1;


    public double getGodUpgrade() {
        return godUpgrade;
    }
    public void setGodUpgrade(double godUpgrade) {
        this.godUpgrade = godUpgrade;
    }
    public double getPrestigeCost() {
        return prestigeCost;
    }
    public double getPrestigeMultiplier() {
        return prestigeMultiplier;
    }
    public void setPrestigeMultiplier(double prestigeMultiplier) {
        this.prestigeMultiplier = prestigeMultiplier;
    }
    public void setPrestigeCost(double prestigeCost) {
        this.prestigeCost = prestigeCost;
    }
    public double getScore() {
        return score;
    }
    public ArrayList<Building> getBuildings() {
        return buildings;
    }
    public void setScore(double score) {
        this.score = score;
    }
    public void setBuildings(ArrayList<Building> buildings) {
        this.buildings = buildings;
    }
    // Override writeObject and readObject methods to handle static variables
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeDouble(prestigeMultiplier);
        out.writeDouble(prestigeCost);
        out.writeDouble(score);
        out.writeObject(buildings);
        out.writeDouble(godUpgrade);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        setPrestigeMultiplier(in.readDouble());
        setPrestigeCost(in.readDouble());
        setScore(in.readDouble());
        setBuildings((ArrayList<Building>) in.readObject());
        setGodUpgrade(in.readDouble());
    }
}
