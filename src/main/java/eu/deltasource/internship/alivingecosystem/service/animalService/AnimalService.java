package eu.deltasource.internship.alivingecosystem.service.animalService;

import eu.deltasource.internship.alivingecosystem.model.animals.Carnivore;
import eu.deltasource.internship.alivingecosystem.model.animals.Herbivore;

public interface AnimalService {


	/**
	 * Makes the carnivore attack herbivore during the simulation
	 *
	 * @param attacker attacker
	 * @param target prey
	 */
	void simulateAttack(Carnivore attacker, Herbivore target);

	/**
	 * Adds prepared Herbivore animals to the list of the simulation.
	 *
	 * @param herbivores
	 */
	void addCreatedHerbivoresToIteration(Herbivore... herbivores);

	/**
	 * Adds prepared Carnivore animals to the list of the simulation.
	 *
	 * @param carnivores
	 */
	void addCreatedCarnivoresToIteration(Carnivore... carnivores);
}
