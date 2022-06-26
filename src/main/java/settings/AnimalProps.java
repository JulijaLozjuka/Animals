package settings;

public class AnimalProps {

    private double weight;
    private int amountOfSteps;
    private double maxHungerLevel;
    private int maxAmountOnCell;
    private int maxAmountOnSpawn;
    private double chanceOnSpawn;

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getAmountOfSteps() {
        return amountOfSteps;
    }

    public void setAmountOfSteps(int amountOfSteps) {
        this.amountOfSteps = amountOfSteps;
    }

    public double getMaxHungerLevel() {
        return maxHungerLevel;
    }

    public void setMaxHungerLevel(double maxHungerLevel) {
        this.maxHungerLevel = maxHungerLevel;
    }

    public int getMaxAmountOnCell() {
        return maxAmountOnCell;
    }

    public void setMaxAmountOnCell(int maxAmountOnCell) {
        this.maxAmountOnCell = maxAmountOnCell;
    }

    public double getChanceOnSpawn() {
        return chanceOnSpawn;
    }

    public void setChanceOnSpawn(double chanceOnSpawn) {
        this.chanceOnSpawn = chanceOnSpawn;
    }

    public int getMaxAmountOnSpawn() {
        return maxAmountOnSpawn;
    }

    public void setMaxAmountOnSpawn(int maxAmountOnSpawn) {
        this.maxAmountOnSpawn = maxAmountOnSpawn;
    }

    @Override
    public String toString() {
        return "AnimalProps{" +
                "weight=" + weight +
                ", amountOfSteps=" + amountOfSteps +
                ", hungerFactor=" + maxHungerLevel +
                ", maxAmountOnCell=" + maxAmountOnCell +
                ", maxAmountOnSpawn=" + maxAmountOnSpawn +
                ", chanceOnSpawn=" + chanceOnSpawn +
                '}';
    }
}
