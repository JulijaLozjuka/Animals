package models;

import field.Position;
import models.plant.Plant;
import settings.PlantProps;

import java.util.Map;


public class PlantFactory {


    private Map<String, PlantProps> plantProps;

    public PlantFactory(Map<String, PlantProps> plantPropsMap) {
        this.plantProps = plantPropsMap;
    }

    public Plant createPlant(Position position) {
        Plant plant = new Plant();

        plant.setWeight(plantProps.get("PLANT").getWeight());
//        statsController.incAnimalCount(animalType);
        return plant;
    }
}
