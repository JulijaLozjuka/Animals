package console;

import models.animal.Animal;
import models.animal.AnimalType;
import field.Cell;
import settings.AnimalProps;
import settings.BeingSettings;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class WorldMapPrinter {

    public void printWorldMap(List<List<Cell>> worldMap, BeingSettings beingSettings) {
        for (List<Cell> cells : worldMap) {
            for (Cell cell : cells) {
                printCell(cell, beingSettings);
                System.out.print(" ");
            }
            System.out.println("");
        }
    }

    private void printCell(Cell cell, BeingSettings beingSettings) {
        Optional<AnimalType> largestPopulation = getLargestPopulation(cell, beingSettings.getAnimalPropsMap());
        if (largestPopulation != null && largestPopulation.isPresent()) {
            AnimalType largestPopulationName = largestPopulation.get();
            String emoji = beingSettings.getEmoji().get(largestPopulationName);
            System.out.print("[" + emoji + "]");
        } else {
            System.out.print("[  ]");
        }

    }

    private Optional<AnimalType> getLargestPopulation(Cell cell, Map<AnimalType, AnimalProps> animalProperties) {
        if (cell.getAnimals().isEmpty()) {
            return Optional.empty();
        }
        Set<AnimalType> allAnimalSpecies = animalProperties.keySet();
        double maxPopulationCount = Double.MIN_VALUE;
        AnimalType maxPopulationSpecies = null;
        for (AnimalType species : allAnimalSpecies) {
            double totalSpeciesWeight = getSpeciesPopulation(cell.getAnimals(), species) * animalProperties.get(species).getWeight();
            if (maxPopulationCount < totalSpeciesWeight) {
                maxPopulationCount = totalSpeciesWeight;
                maxPopulationSpecies = species;
            }
        }
        return Optional.of(maxPopulationSpecies);
    }

    private long getSpeciesPopulation(List<Animal> animals, AnimalType species) {
        return animals.stream().filter(a -> a.getAnimalType().equals(species)).count();
    }
}
