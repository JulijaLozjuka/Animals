package field;

import models.animal.Animal;
import models.plant.Plant;

import java.util.ArrayList;
import java.util.List;

public class Cell {
    private Position position;
    private List<Animal> animals;
    private List<Plant> plants;

    public Cell(Position position) {
        animals = new ArrayList<>();
        this.position = position;
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public List<Plant> getPlants() {
        return plants;
    }

    public void setAnimals(List<Animal> animals) {
        this.animals = animals;
    }

    public void addAnimal(Animal animal) {
        if (animal == null) return;
        animals.add(animal);
    }
    public void addPlant(Plant plant) {
        if (plant == null) return;
        plants.add(plant);
    }


    public Position getPosition() {
        return position;
    }

    public void removeAnimal(Animal animal) {
        animals.remove(animal);
    }
    public void removePlant(Plant plant) {
       plants.remove(plant);
    }


}
