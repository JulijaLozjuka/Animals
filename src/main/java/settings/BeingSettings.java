package settings;

import models.animal.AnimalType;

import java.util.HashMap;
import java.util.Map;

public class BeingSettings {

    public static final String ANIMAL_SETTINGS_PATH = "animal-settings.yaml";

    private Map<AnimalType, AnimalProps> animalPropsMap;
    private Map<String, PlantProps> plantPropsMap;
    private Map<AnimalType, String> emoji;
    private Map<String, Map<String, Integer>> chanceToEat;

    public BeingSettings() {
        this.animalPropsMap = new HashMap<>();
        this.plantPropsMap = new HashMap<>();
        this.emoji = new HashMap<>();
        this.chanceToEat = new HashMap<>();
    }

    public Map<AnimalType, AnimalProps> getAnimalPropsMap() {
        return animalPropsMap;
    }

    public void setAnimalPropsMap(Map<AnimalType, AnimalProps> animalPropsMap) {
        this.animalPropsMap = animalPropsMap;
    }

    public Map<String, PlantProps> getPlantPropsMap() {
        return plantPropsMap;
    }

    public void setPlantPropsMap(Map<String, PlantProps> plantPropsMap) {
        this.plantPropsMap = plantPropsMap;
    }

    public Map<AnimalType, String> getEmoji() {
        return emoji;
    }

    public void setEmoji(Map<AnimalType, String> emoji) {
        this.emoji = emoji;
    }

    public Map<String, Map<String, Integer>> getChanceToEat() {
        return chanceToEat;
    }

    public void setChanceToEat(Map<String, Map<String, Integer>> chanceToEat) {
        this.chanceToEat = chanceToEat;
    }
}
