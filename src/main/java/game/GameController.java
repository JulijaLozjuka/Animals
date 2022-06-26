package game;

import models.PlantFactory;
import models.animal.Animal;
import models.AnimalFactory;
import models.animal.AnimalType;
import console.WorldMapPrinter;
import field.Cell;
import field.GameBoard;
import models.plant.Plant;
import settings.*;
import stats.StatsController;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class GameController {
    private SettingsLoader settingsLoader;
    private AnimalFactory animalFactory;
    private PlantFactory plantFactory;
    private GameBoard gameBoard;

    public GameController() {
        settingsLoader = new SettingsLoader();
        gameBoard = new GameBoard();
    }

    public void start() {
        BeingSettings beingSettings = loadAnimalSettings();
        StatsController statsController = new StatsController();
        animalFactory = new AnimalFactory(beingSettings.getAnimalPropsMap(), statsController);
        plantFactory = new PlantFactory(beingSettings.getPlantPropsMap());
        GameSettings gameSettings = loadGameSettings();
        gameBoard.initWorldMap(gameSettings.getRows(), gameSettings.getColumns());
        populateMap(beingSettings.getAnimalPropsMap(), beingSettings.getPlantPropsMap(), statsController);
        initConsolePrinter(beingSettings);
        ActionController actionController = new ActionController(
                gameBoard,
                beingSettings.getAnimalPropsMap(),
                beingSettings.getChanceToEat(),
                animalFactory,
                plantFactory,
                statsController,
                gameSettings.getGameTurns());
        actionController.start();
    }

    private void initConsolePrinter(BeingSettings beingSettings) {
        WorldMapPrinter worldMapPrinter = new WorldMapPrinter();
        worldMapPrinter.printWorldMap(gameBoard.getWorldMap(), beingSettings);
    }

    private void populateMap(Map<AnimalType, AnimalProps> animalPropsMap, Map<String, PlantProps> plantMap, StatsController statsController) {
        List<List<Cell>> worldMap = gameBoard.getWorldMap();
        for (int i = 0; i < worldMap.size(); i++) {
            for (int j = 0; j < worldMap.get(i).size(); j++) {
                populateCell(worldMap.get(i).get(j), animalPropsMap, plantMap, statsController);
            }
        }
    }

    private void populateCell(Cell cell, Map<AnimalType, AnimalProps> animalPropsMap, Map<String, PlantProps> plantMap, StatsController statsController) {
        for (Map.Entry<AnimalType, AnimalProps> animalPropsEntry : animalPropsMap.entrySet()) {
            int amountOfAnimalsToSpawn = ThreadLocalRandom.current().nextInt(1, animalPropsEntry.getValue().getMaxAmountOnSpawn());
            double currentChanceOnSpawn = ThreadLocalRandom.current().nextDouble(0.0, 100.0);
            double chanceOnSpawn = animalPropsEntry.getValue().getChanceOnSpawn();
            if (chanceOnSpawn >= currentChanceOnSpawn) {
                for (int i = 0; i < amountOfAnimalsToSpawn; i++) {
                    Animal animal = animalFactory.createAnimal(animalPropsEntry.getKey(), cell.getPosition());
                    cell.addAnimal(animal);
                    statsController.incInitAnimalCount(animal.getAnimalType());
                }
            }
        }
        int amountOfPlantsToSpawn = ThreadLocalRandom.current().nextInt(1, 10);
        double currentChanceOnSpawn = ThreadLocalRandom.current().nextDouble(0.0, 100.0);
        double chanceOnSpawn = 50.0;
        if (chanceOnSpawn >= currentChanceOnSpawn) {
            for (int i = 0; i < amountOfPlantsToSpawn; i++) {
                Plant plant = plantFactory.createPlant(cell.getPosition());
                cell.addPlant(plant);
            }
        }
    }

    private BeingSettings loadAnimalSettings() {

        BeingSettings beingSettings = settingsLoader.loadAnimalProperties(null);
        beingSettings = settingsLoader.loadAnimalEmoji(beingSettings);
        beingSettings = settingsLoader.loadPlantSettings(beingSettings);
        beingSettings = settingsLoader.loadAnimalChanceToEat(beingSettings);
        return beingSettings;
    }

    private GameSettings loadGameSettings() {
        return settingsLoader.loadGameSettings();
    }
}
