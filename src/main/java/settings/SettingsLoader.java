package settings;

import models.animal.AnimalType;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static settings.BeingSettings.ANIMAL_SETTINGS_PATH;

public class SettingsLoader {

    public static final String PLANT_SETTINGS_PATH = "plant-settings.yaml";
    public static final String GAME_SETTINGS_PATH = "game-settings.yaml";
    public static final String ANIMAL_EMOJI_PATH = "animal-emoji.yaml";
    public static final String ANIMAL_CHANCE_TO_EAT_PATH = "animal-chance-to-eat.yaml";

    public BeingSettings loadAnimalProperties(BeingSettings beingSettings) {
        ObjectMapper mapper = new YAMLMapper();
        BeingSettings tmpBeingSettings = beingSettings != null ? beingSettings : new BeingSettings();
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(ANIMAL_SETTINGS_PATH)) {
            TypeReference<HashMap<AnimalType, AnimalProps>> typeRef = new TypeReference<>() {
            };
            Map<AnimalType, AnimalProps> animalPropsMap = mapper.readValue(inputStream, typeRef);
            tmpBeingSettings.setAnimalPropsMap(animalPropsMap);
        } catch (IOException e) {

            System.out.println(e.getMessage());
            System.exit(1);
        }
        return tmpBeingSettings;
    }

    public BeingSettings loadAnimalEmoji(BeingSettings beingSettings) {
        ObjectMapper mapper = new YAMLMapper();
        BeingSettings tmpBeingSettings = beingSettings != null ? beingSettings : new BeingSettings();
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(ANIMAL_EMOJI_PATH)) {
            TypeReference<HashMap<AnimalType, String>> typeRef = new TypeReference<>() {
            };
            Map<AnimalType, String> animalEmojiMap = mapper.readValue(inputStream, typeRef);
            tmpBeingSettings.setEmoji(animalEmojiMap);
        } catch (IOException e) {
            System.out.println("Animal emoji loading error.");
            System.exit(1);
        }
        return tmpBeingSettings;
    }

    public BeingSettings loadAnimalChanceToEat(BeingSettings beingSettings) {
        ObjectMapper mapper = new YAMLMapper();
        BeingSettings tmpBeingSettings = beingSettings != null ? beingSettings : new BeingSettings();
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(ANIMAL_CHANCE_TO_EAT_PATH)) {
            TypeReference<HashMap<String, Map<String, Integer>>> typeRef = new TypeReference<>() {
            };
            HashMap<String, Map<String, Integer>> animalChanceToEatMap = mapper.readValue(inputStream, typeRef);
            tmpBeingSettings.setChanceToEat(animalChanceToEatMap);
        } catch (IOException e) {
            System.out.println("Animal chance to eat loading error.");
            System.exit(1);
        }
        return tmpBeingSettings;

    }

    public GameSettings loadGameSettings() {
        ObjectMapper mapper = new YAMLMapper();
        GameSettings gameSettings = new GameSettings();
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(GAME_SETTINGS_PATH)) {
            gameSettings = mapper.readValue(inputStream, GameSettings.class);
        } catch (IOException e) {
            System.out.println("Game props loading error.");
            System.out.println(e.getMessage());
            System.exit(1);
        }
        return gameSettings;

    }

    public BeingSettings loadPlantSettings(BeingSettings beingSettings)  {
        ObjectMapper mapper = new YAMLMapper();
        Map<String, PlantProps> plantSettings = new HashMap<>();
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(PLANT_SETTINGS_PATH)) {
            TypeReference<HashMap<String, PlantProps>> typeRef = new TypeReference<>() {
            };
            plantSettings = mapper.readValue(inputStream, typeRef);
            beingSettings.setPlantPropsMap(plantSettings);
        } catch (IOException e) {
            System.out.println("Plant props loading error.");
            System.out.println(e.getMessage());
            System.exit(1);
        }
        return beingSettings;

    }
}
