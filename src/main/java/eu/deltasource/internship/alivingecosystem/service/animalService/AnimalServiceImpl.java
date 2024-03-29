package eu.deltasource.internship.alivingecosystem.service.animalService;

import eu.deltasource.internship.alivingecosystem.helper.AttackChanceCalculator;
import eu.deltasource.internship.alivingecosystem.model.animals.Carnivore;
import eu.deltasource.internship.alivingecosystem.model.animals.Herbivore;
import eu.deltasource.internship.alivingecosystem.repository.carnivoreGroupRepository.CarnivoreGroupRepository;
import eu.deltasource.internship.alivingecosystem.repository.carnivoreRepository.CarnivoreRepository;
import eu.deltasource.internship.alivingecosystem.repository.herbivoreGroupRepository.HerbivoreGroupRepository;
import eu.deltasource.internship.alivingecosystem.repository.herbivoreRepository.HerbivoreRepository;
import eu.deltasource.internship.alivingecosystem.service.carnivoreService.CarnivoreService;
import eu.deltasource.internship.alivingecosystem.service.herbivoreService.HerbivoreService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AnimalServiceImpl implements AnimalService {

	private final CarnivoreRepository carnivoreRepository;

	private final CarnivoreGroupRepository carnivoreGroupRepository;

	private final HerbivoreGroupRepository herbivoreGroupRepository;

	private final HerbivoreRepository herbivoreRepository;

	private final CarnivoreService carnivoreService;

	private final HerbivoreService herbivoreService;

	public AnimalServiceImpl(CarnivoreRepository carnivoreRepository, HerbivoreRepository herbivoreRepository, CarnivoreService carnivoreService, CarnivoreGroupRepository carnivoreGroupRepository, HerbivoreGroupRepository herbivoreGroupRepository, HerbivoreService herbivoreService) {
		this.carnivoreRepository = carnivoreRepository;
		this.herbivoreRepository = herbivoreRepository;
		this.carnivoreService = carnivoreService;
		this.carnivoreGroupRepository = carnivoreGroupRepository;
		this.herbivoreGroupRepository = herbivoreGroupRepository;
		this.herbivoreService = herbivoreService;
	}

	@Override
	public void addCreatedHerbivoresToIteration(Herbivore... herbivores) {
		for (Herbivore herbivore : herbivores) {
			herbivoreService.createHerbivore(herbivore.getName(), herbivore, 3);
		}
	}

	@Override
	public void addCreatedCarnivoresToIteration(Carnivore... carnivores) {
		for (Carnivore carnivore : carnivores) {
			carnivoreService.createCarnivore(carnivore.getName(), carnivore, 3);
		}
	}

	@Override
	public void simulateAttack(Carnivore attacker, Herbivore target) {
		double originalAttackOfAnimal = attacker.getAttackPoints();
		double originalEscapeOfAnimal = target.getEscapePoints();
		double attackPoint = originalAttackOfAnimal;
		double escapePoint = originalEscapeOfAnimal;
		attackPoint = getsAttackPoints(attacker, attackPoint);
		attacker.setAttackPoints(attackPoint);
		escapePoint = getsEscapePoints(target, escapePoint);
		target.setEscapePoints(escapePoint);
		double chanceForSuccess = AttackChanceCalculator.chanceForSuccessOfAttack(attacker, target);
		int randomCoefficient = new Random().nextInt(0, 80);
		List<Herbivore> attackedHerbivores = new ArrayList<>();
		if (chanceForSuccess < randomCoefficient) {
			carnivoreService.hungerLevelIncrease(attacker);
			System.out.println(target.getName() + " escapes from " + attacker.getName());
		} else {
			// The herbivore did not escape
			carnivoreService.hungerLevelDecrease(attacker, target);
			if (carnivoreGroupRepository.findCarnivoreGroupForCarnivore(attacker) != null) {
				System.out.println(target.getName() + " is killed by " + carnivoreGroupRepository.findCarnivoreGroupForCarnivore(attacker).getNameGroup());
			} else {
				System.out.println(target.getName() + " is killed by " + attacker.getName());
			}
			attackedHerbivores.add(target);
		}

		for (Herbivore deadAnimal : attackedHerbivores) {
			herbivoreRepository.removeHerbivore(deadAnimal);
		}
		attackedHerbivores.clear();

		attacker.setAttackPoints(originalAttackOfAnimal);
		target.setEscapePoints(originalEscapeOfAnimal);

	}

	/**
	 * Gets escape points of herbivore
	 *
	 * @param target herbivore
	 * @param escapePoints current escape points
	 * @return return escape points based if the herbivore is group or alone
	 */
	private double getsEscapePoints(Herbivore target, double escapePoints) {
		if (herbivoreGroupRepository.findHerbivoreGroupForHerbivore(target) != null) {
			var sizeOfHerbivoresGroupConsistsOf = herbivoreGroupRepository.findHerbivoreGroupForHerbivore(target).getAnimals().size();
			escapePoints = herbivoreGroupRepository.findHerbivoreGroupForHerbivore(target).getSampleAnimal().getEscapePoints() * sizeOfHerbivoresGroupConsistsOf;
		} else if (herbivoreGroupRepository.findHerbivoreGroupForHerbivore(target) == null) {
			escapePoints = target.getEscapePoints();
		}
		return escapePoints;
	}

	/**
	 * Gets attack points of herbivore
	 *
	 * @param attacker carnivore
	 * @param attackPoints current attack points
	 * @return attack points based if the carnivore is group or alone
	 */
	private double getsAttackPoints(Carnivore attacker, double attackPoints) {
		if (carnivoreGroupRepository.findCarnivoreGroupForCarnivore(attacker) != null) {
			var sizeOfCarnivoresGroupConsistsOf = carnivoreGroupRepository.findCarnivoreGroupForCarnivore(attacker).getAnimals().size();
			attackPoints = carnivoreGroupRepository.findCarnivoreGroupForCarnivore(attacker).getSampleAnimal().getAttackPoints() * sizeOfCarnivoresGroupConsistsOf;
		} else if (carnivoreGroupRepository.findCarnivoreGroupForCarnivore(attacker) == null) {
			attackPoints = attacker.getAttackPoints();
		}
		return attackPoints;
	}

}
