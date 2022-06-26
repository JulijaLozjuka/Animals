package game;

import models.AnimalFactory;
import models.PlantFactory;
import models.animal.Animal;
import models.animal.AnimalType;
import field.Cell;
import field.GameBoard;
import field.Position;
import settings.AnimalProps;
import stats.StatsController;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class ActionController {

    private final int gameTurns;

    private List<Animal> performedAction;
    private GameBoard gameBoard;

    private StatsController statsController;

    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);
    private Map<String, Map<String, Integer>> animalChanceToEatMap;
    private Map<AnimalType, AnimalProps> animalPropMap;

    private AnimalFactory animalFactory;
    private PlantFactory plantFactory;

    public ActionController(GameBoard gameBoard,
                            Map<AnimalType, AnimalProps> animalPropMap,
                            Map<String, Map<String, Integer>> animalChanceToEatMap,
                            AnimalFactory animalFactory,
                            PlantFactory plantFactory,
                            StatsController statsController,
                            int gameTurns) {
        this.gameBoard = gameBoard;
        this.animalChanceToEatMap = animalChanceToEatMap;
        this.gameTurns = gameTurns;
        this.animalPropMap = animalPropMap;
        this.animalFactory = animalFactory;
        this.plantFactory = plantFactory;
        this.statsController = statsController;
        performedAction = new ArrayList<>();
    }

    public void start() {
        statsController.printInitStats();
        scheduledExecutorService.schedule(
                () -> {
                    for (int i = 0; i < gameTurns; i++) {
                        refreshActions();
                        for (List<Cell> cells : gameBoard.getWorldMap()) {
                            for (Cell cell : cells) {
                                for (int j = cell.getAnimals().size() - 1; j >= 0; j--) {
                                    performAction(cell.getAnimals().get(j));
                                }
                            }
                        }
                    }
                }, 100,
                TimeUnit.MILLISECONDS);
        scheduledExecutorService.shutdown();
    }

    private void refreshActions() {
        performedAction.clear();
    }

    private void performAction(Animal animal) {
        List<Animal> animals = getAnimalsOnAnimalCell(animal);
        if (animal.isStarving()) {
            die(animal, animals);
            return;
        }
        List<AnimalEatingStats> cellAnimalEatingStats = getCellAnimalEatingStats(animal, animals);
        if (animal.isHungry() && cellAnimalEatingStats.size() > 0) {
            eat(animal, cellAnimalEatingStats, animals);
        } else if (canReproduce(animal, animals)) {
            reproduce(animal, animals);
        } else {
            move(animal);
        }
        animal.looseEnergy();
        performedAction.add(animal);
    }


    private void reproduce(Animal animal, List<Animal> animals) {
        List<Animal> sameClassAnimals = animals.stream()
                .filter(a -> isSameClassName(a.getClass().getSimpleName(), animal))
                .filter(a -> a != animal)
                .toList();
        if (sameClassAnimals.size() == 0) {
            System.out.println("Animals can't reproduce alone.");
            return;
        }
        performedAction.add(sameClassAnimals.get(0));
        performedAction.add(animal);
        animals.add(animalFactory.createAnimal(animal.getAnimalType(), animal.getPosition()));
    }

    private void eat(Animal animal, List<AnimalEatingStats> animalEatingStats, List<Animal> animalsOnCell) {
        List<AnimalEatingStats> suitableAnimalsToEat = animalEatingStats.stream()
                .filter(stat -> stat.getWeight() >= animal.getAmountOfEnergyForOneMove())
                .sorted(Comparator.comparing(AnimalEatingStats::getChanceToEat))
                .toList();
        AnimalEatingStats prayStat = null;
        if (suitableAnimalsToEat.size() > 0) {
            prayStat = suitableAnimalsToEat.get(0);
        } else {
            prayStat = animalEatingStats.stream().sorted(Comparator.comparing(AnimalEatingStats::getWeight)).toList().get(0);
        }
        for (Animal pray : animalsOnCell) {
            if (!isSameClassName(prayStat.getAnimalName(), pray)) {
                continue;
            }
            if (ThreadLocalRandom.current().nextDouble(0.0, 100.1) < prayStat.getChanceToEat()) {
                animal.eat(pray);
                die(pray, animalsOnCell);
            }
            break;
        }
    }

    private void move(Animal animal) {
        Cell currentAnimalLocation = gameBoard.getCell(animal.getPosition());
        for (int i = 0; i < animal.getAmountOfSteps(); i++) {
            int direction = ThreadLocalRandom.current().nextInt(0, 4);
            Position position = currentAnimalLocation.getPosition();
            Position newPosition = null;
            switch (direction) {
                case 0:
                    newPosition = new Position(position.getRow(), position.getColumn() - 1);
                    break;
                case 1:
                    newPosition = new Position(position.getRow(), position.getColumn() + 1);
                    break;
                case 2:
                    newPosition = new Position(position.getRow() - 1, position.getColumn());
                    break;
                case 3:
                    newPosition = new Position(position.getRow() + 1, position.getColumn());
                    break;
                default:
                    System.out.println("Unknown direction to move");
            }

            if (gameBoard.getWorldMap().size() <= newPosition.getColumn()
                    || newPosition.getColumn() < 0
                    || newPosition.getRow() < 0
                    || gameBoard.getWorldMap().get(0).size() <= newPosition.getRow()) {
                return;
            }

            Cell newLocation = gameBoard.getCell(newPosition);
            newLocation.addAnimal(animal);
            currentAnimalLocation.removeAnimal(animal);
        }
    }

    private List<Animal> getAnimalsOnAnimalCell(Animal animal) {
        return gameBoard.getCell(animal.getPosition()).getAnimals();
    }

    private boolean isSameClassName(String a, Animal animal) {
        return a.equals(animal.getClass().getSimpleName());
    }

    private List<AnimalEatingStats> getCellAnimalEatingStats(Animal animal, List<Animal> animals) {
        String hunterName = animal.getClass().getSimpleName();
        List<AnimalEatingStats> animalEatingStats = new ArrayList<>();
        Set<Animal> uniquePray = new HashSet<>(animals);
        for (Animal pray : uniquePray) {
            String prayName = pray.getClass().getSimpleName();
            Integer chanceToEat = animalChanceToEatMap.get(hunterName).get(prayName);
            if (chanceToEat == 0) continue;
            animalEatingStats.add(
                    new AnimalEatingStats(
                            prayName,
                            chanceToEat,
                            animalPropMap.get(prayName).getWeight()));
        }
        return animalEatingStats;
    }


    private boolean canReproduce(Animal animal, List<Animal> animals) {
        return !performedAction.contains(animal)
                && animals
                .stream()
                .filter(a -> isSameClassName(a.getClass().getSimpleName(), animal))
                .count() > 1;
    }

    private void die(Animal animal, List<Animal> animalsOnCell) {
        if (animalsOnCell == null) {
            System.out.println("Animal can't die no animal list provided");
        }
        animalsOnCell.remove(animal);
        statsController.decAnimalCount(animal.getAnimalType());
        animal = null;
    }
}
