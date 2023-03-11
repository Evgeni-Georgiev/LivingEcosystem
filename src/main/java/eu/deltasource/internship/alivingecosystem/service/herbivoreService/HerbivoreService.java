package eu.deltasource.internship.alivingecosystem.service.herbivoreService;

import eu.deltasource.internship.alivingecosystem.model.animals.Herbivore;

public interface HerbivoreService {

	/**
	 * Creates Herbivore based on it's living type
	 *
	 * @param nameOfAnimal name of the herbivore
	 * @param sampleAnimal object based on which the herbivore will be created
	 * @param numberOfAnimals number of herbivores to create
	 */
	void createHerbivore(String nameOfAnimal, Herbivore sampleAnimal, int numberOfAnimals);

	/**
	 * Reduces reproduction rating of every herbivore and when it gets to 0 new herbivore is added to the simulation.
	 */
	void reduceHerbivoreReproductionRate();

	/**
	 * Increases herbivores age with every iteration
	 */
	void increasesHerbivoresAgeForEveryIteration();

}
