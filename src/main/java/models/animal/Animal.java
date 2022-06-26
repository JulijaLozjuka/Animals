package models.animal;


import field.Position;

public abstract class Animal {

    private final AnimalType animalType;

    private Position position;

    private double weight;
    private int amountOfSteps;
    private double maxHungerLevel;

    private double hunger;

    public Animal(Position position, AnimalType animalType) {
        this.position = position;
        this.animalType = animalType;
    }

    public double getWeight() {
        return weight;
    }

    public int getAmountOfSteps() {
        return amountOfSteps;
    }

    public double getMaxHungerLevel() {
        return maxHungerLevel;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setAmountOfSteps(int amountOfSteps) {
        this.amountOfSteps = amountOfSteps;
    }

    public void setMaxHungerLevel(double maxHungerLevel) {
        this.maxHungerLevel = maxHungerLevel;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public double getHunger() {
        return hunger;
    }

    public void setHunger(double hunger) {
        this.hunger = hunger;
    }

    public boolean isHungry() {
        return maxHungerLevel > hunger;
    }

    public double getAmountOfEnergyForOneMove() {
        return maxHungerLevel / 3;
    }

    public void eat(Animal animal){
        this.hunger += animal.getWeight();
    }


    public void looseEnergy() {
        this.hunger -= maxHungerLevel / 3;
    }

    public boolean isStarving() {
        return this.hunger <= 0;
    }

    public AnimalType getAnimalType(){
        return animalType;
    }
}
