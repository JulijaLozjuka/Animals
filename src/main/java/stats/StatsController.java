package stats;

import models.animal.AnimalType;
import console.StatsPrinter;

import java.util.HashMap;
import java.util.Map;

public class StatsController {

    private StatsPrinter statsPrinter;

    private Map<AnimalType, Integer> animalsSpawnedOnStart;
    private Map<AnimalType, Integer> amountOfAnimalsInGame;

    public StatsController() {
        amountOfAnimalsInGame = new HashMap<>();
        animalsSpawnedOnStart = new HashMap<>();
        this.statsPrinter = new StatsPrinter();
    }

    public void printInitStats(){
        statsPrinter.printAnimalStats(animalsSpawnedOnStart);
    }

    public void incAnimalCount(AnimalType animalType) {
        amountOfAnimalsInGame.putIfAbsent(animalType, 1);
        amountOfAnimalsInGame.put(animalType, amountOfAnimalsInGame.get(animalType) + 1);
    }

    public void decAnimalCount(AnimalType animalType) {
        amountOfAnimalsInGame.putIfAbsent(animalType, 0);
        amountOfAnimalsInGame.put(animalType, amountOfAnimalsInGame.get(animalType) - 1);
    }

    public void incInitAnimalCount(AnimalType animalType) {
        animalsSpawnedOnStart.putIfAbsent(animalType, 1);
        animalsSpawnedOnStart.put(animalType, animalsSpawnedOnStart.get(animalType) + 1);
    }

    public Map<AnimalType, Integer> getAnimalsSpawnedOnStart() {
        return animalsSpawnedOnStart;
    }

    public Map<AnimalType, Integer> getAmountOfAnimalsInGame() {
        return amountOfAnimalsInGame;
    }
}
