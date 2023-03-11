package eu.deltasource.internship.alivingecosystem.service.carnivoreService;

import eu.deltasource.internship.alivingecosystem.model.animals.Carnivore;
import eu.deltasource.internship.alivingecosystem.model.animals.Herbivore;

public interface CarnivoreService {

	/**
	 * Reduces reproduction rating of every carnivore and when it gets to 0 new carnivore is added to the simulation.
	 */
	void reduceCarnivoreReproductionRate();

	/**
	 * Creates carnivore based of it's living type
	 *
	 * @param nameOfAnimal name of the carnivore
	 * @param sampleAnimal object based on which the carnivore will be created
	 * @param numberOfAnimals number of carnivores to create
	 */
	void createCarnivore(String nameOfAnimal, Carnivore sampleAnimal, int numberOfAnimals);

	/**
	 * Increases carnivores age with every iteration
	 */
	void increasesCarnivoresAgeForEveryIteration();

	/**
	 * Decreases carnivore hunger level if herbivore is captured
	 *
	 * @param carnivore attacker captured herbivore
	 * @param herbivore herbivore being captured
	 */
	void hungerLevelDecrease(Carnivore carnivore, Herbivore herbivore);

	/**
	 * Increases every carnivore hunger level for every iteration.
	 * When carnivore reaches, 100 it dies.
	 */
	void hungerLevelIncreaseForEveryIteration();

	/**
	 * Increases carnivore hunger level when prey is not caught.
	 *
	 * @param carnivore the carnivore to which the hunger level will be reduced.
	 */
	void hungerLevelIncrease(Carnivore carnivore);
}
