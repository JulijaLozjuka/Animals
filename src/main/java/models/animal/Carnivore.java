package models.animal;

import field.Position;

public abstract class Carnivore extends Animal {

    public Carnivore(Position position, AnimalType animalType) {
        super(position, animalType);
    }
}
