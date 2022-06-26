package models;

import models.animal.*;
import field.Position;
import settings.AnimalProps;
import stats.StatsController;

import java.util.Map;

import static models.animal.AnimalType.*;

public class AnimalFactory {

    private Map<AnimalType, AnimalProps> animalPropsMap;
    private StatsController statsController;

    public AnimalFactory(Map<AnimalType, AnimalProps> animalPropsMap, StatsController statsController) {
        this.animalPropsMap = animalPropsMap;
        this.statsController = statsController;
    }

    public Animal createAnimal(AnimalType animalType, Position position) {
        Animal animal =
                switch (animalType) {
                    case WOLF -> new Wolf(position, WOLF);
                    case SNAKE -> new Snake(position, SNAKE);
                    case FOX -> new Fox(position, FOX);
                    case BEAR -> new Bear(position, BEAR);
                    case EAGLE -> new Eagle(position, EAGLE);
                    case HORSE -> new Horse(position, HORSE);
                    case DEER -> new Deer(position, DEER);
                    case RABBIT -> new Rabbit(position, RABBIT);
                    case MOUSE -> new Mouse(position, MOUSE);
                    case GOAT -> new Goat(position, GOAT);
                    case SHEEP -> new Sheep(position, SHEEP);
                    case BOAR -> new Boar(position, BOAR);
                    case BUFFALO -> new Buffalo(position, BUFFALO);
                    case DUCK -> new Duck(position, DUCK);
                    case CATERPILLAR -> new Caterpillar(position, CATERPILLAR);
                };

        animal.setWeight(animalPropsMap.get(animal.getAnimalType()).getWeight());
        animal.setAmountOfSteps(animalPropsMap.get(animal.getAnimalType()).getAmountOfSteps());
        animal.setMaxHungerLevel(animalPropsMap.get(animal.getAnimalType()).getMaxHungerLevel());
        statsController.incAnimalCount(animalType);
        return animal;
    }

}
