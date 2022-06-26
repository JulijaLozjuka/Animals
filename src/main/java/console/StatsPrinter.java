package console;

import models.animal.AnimalType;

import java.util.Map;

public class StatsPrinter {

    public void printAnimalStats(Map<AnimalType, Integer> animalStats){
        System.out.println("On init there is.");
        for (Map.Entry<AnimalType, Integer> animalTypeIntegerEntry : animalStats.entrySet()) {
            System.out.println("" + animalTypeIntegerEntry.getKey() + " : " + animalTypeIntegerEntry.getValue());
        }
    }
}
