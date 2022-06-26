package game;

public class AnimalEatingStats {
    private String animalName;
    private double chanceToEat;
    private double weight;

    public AnimalEatingStats(String animalName, double chanceToEat, double weight) {
        this.animalName = animalName;
        this.chanceToEat = chanceToEat;
        this.weight = weight;
    }

    public String getAnimalName() {
        return animalName;
    }

    public void setAnimalName(String animalName) {
        this.animalName = animalName;
    }

    public double getChanceToEat() {
        return chanceToEat;
    }

    public void setChanceToEat(double chanceToEat) {
        this.chanceToEat = chanceToEat;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
