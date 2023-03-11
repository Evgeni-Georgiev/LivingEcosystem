package eu.deltasource.internship.alivingecosystem.service.carnivoreService;

import eu.deltasource.internship.alivingecosystem.enums.LivingType;
import eu.deltasource.internship.alivingecosystem.model.animalgroup.CarnivoreGroup;
import eu.deltasource.internship.alivingecosystem.model.animals.Carnivore;
import eu.deltasource.internship.alivingecosystem.model.animals.Herbivore;
import eu.deltasource.internship.alivingecosystem.repository.carnivoreGroupRepository.CarnivoreGroupRepository;
import eu.deltasource.internship.alivingecosystem.repository.carnivoreRepository.CarnivoreRepository;
import eu.deltasource.internship.alivingecosystem.repository.herbivoreRepository.HerbivoreRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

import static eu.deltasource.internship.alivingecosystem.helper.DecreaseHungerLevelCalculator.decreaseHungerLevelByFormula;
import static eu.deltasource.internship.alivingecosystem.helper.DecreaseHungerLevelCalculator.validateIfHungerLevelIsLessThanZero;

public class CarnivoreServiceImpl implements CarnivoreService {

	private final CarnivoreRepository carnivoreRepository;

	private final CarnivoreGroupRepository carnivoreGroupRepository;

	public CarnivoreServiceImpl(CarnivoreRepository carnivoreRepository, CarnivoreGroupRepository carnivoreGroupRepository) {
		this.carnivoreRepository = carnivoreRepository;
		this.carnivoreGroupRepository = carnivoreGroupRepository;
	}

	@Override
	public void reduceCarnivoreReproductionRate() {
		int decreaseCounter = 1;

		List<Carnivore> newBornAnimalsList = new ArrayList<>();
		for (Carnivore carnivore : carnivoreRepository.getAllCarnivores()) {

			int currentReproductionRate = carnivore.getReproductionRate();
			currentReproductionRate -= decreaseCounter;
			carnivore.setReproductionRate(currentReproductionRate);

			if (carnivore.getReproductionRate() == 0) {
				if (carnivoreGroupRepository.findCarnivoreGroupForCarnivore(carnivore).isPresent()) {
					reproduceCarnivoreGroup(newBornAnimalsList, carnivore);
				} else {
					reproduceAloneCarnivores(newBornAnimalsList, carnivore);
				}
			}
		}
		addReproducedCarnivore(newBornAnimalsList);

	}

	@Override
	public void createCarnivore(String nameOfAnimal, Carnivore sampleAnimal, int numberOfAnimals) {
		if (sampleAnimal.getLivingType() == LivingType.GROUP) {
			CarnivoreGroup carnivoreGroup = new CarnivoreGroup(nameOfAnimal + " Group", sampleAnimal, numberOfAnimals);
			List<Carnivore> newListCarnivore = carnivoreGroup.getAnimals();
			int idGroup = carnivoreGroup.getId();
			for (int i = 0; i < numberOfAnimals; i++) {
				sampleAnimal = createsCarnivoreSample(sampleAnimal);
				newListCarnivore.add(sampleAnimal);
				carnivoreGroup.getAnimals().get(i).setGroupId(idGroup);
			}
			carnivoreRepository.addCarnivore(sampleAnimal);
			carnivoreRepository.addCarnivoreGroupType(sampleAnimal);
			carnivoreGroupRepository.addCarnivoreGroupList(carnivoreGroup);
		} else if (sampleAnimal.getLivingType() == LivingType.ALONE) {
			List<Carnivore> newAloneCarnivores = new ArrayList<>();
			for (int i = 0; i < numberOfAnimals; i++) {
				sampleAnimal = createsCarnivoreSample(sampleAnimal);
				newAloneCarnivores.add(sampleAnimal);
			}
			carnivoreRepository.addAllToCarnivore(newAloneCarnivores);
		}
	}


	@Override
	public void increasesCarnivoresAgeForEveryIteration() {
		int increaseCounter = 1;

		List<Carnivore> deadAnimalsList = new ArrayList<>();
		for (var carnivore : carnivoreRepository.getAllCarnivores()) {
			if (carnivoreGroupRepository.findCarnivoreGroupForCarnivore(carnivore).isPresent()) {
				CarnivoreGroup carnivoreGroup = carnivoreGroupRepository.findCarnivoreGroupForCarnivore(carnivore).orElse(new CarnivoreGroup("Belongs to no group", carnivore, 0));
				List<Carnivore> allCarnivoresFromGroup = carnivoreGroup.getAnimals();
				for (var memberOfGroup : allCarnivoresFromGroup) {
					int currentAge = memberOfGroup.getAge();
					currentAge += increaseCounter;
					memberOfGroup.setAge(currentAge);
					if (memberOfGroup.getAge() >= memberOfGroup.getMaxAge()) {
						deadAnimalsList.add(memberOfGroup);
					}

				}
			} else {
				int currentAge = carnivore.getAge();
				currentAge += increaseCounter;
				carnivore.setAge(currentAge);
				if (carnivore.getAge() >= carnivore.getMaxAge()) {
					deadAnimalsList.add(carnivore);
				}
			}
		}
		for (Carnivore deadAnimal : deadAnimalsList) {
			carnivoreRepository.removeCarnivore(deadAnimal);
		}
		deadAnimalsList.clear();

	}

	@Override
	public void hungerLevelIncrease(Carnivore carnivore) {
		int currentHungerLevel = carnivore.getHungerLevel();
		int currentHungerRate = carnivore.getHungerRate();
		carnivore.setHungerLevel(currentHungerLevel + currentHungerRate);
		if (carnivore.getHungerLevel() >= 100) {
			System.out.println(carnivore.getName() + " has been removed from list due to hunger rate.");
			carnivoreRepository.removeCarnivore(carnivore);
		}

	}

	@Override
	public void hungerLevelDecrease(Carnivore carnivore, Herbivore herbivore) {
		int getCarnivoreHungerLevel = carnivore.getHungerLevel();
		int caughtPreyWeight = herbivore.getWeight();
		int attackerWeight = carnivore.getWeight();
		int decreaseHungerLevelFormula = decreaseHungerLevel(caughtPreyWeight, attackerWeight);

		if (carnivore.getLivingType().equals(LivingType.GROUP) && carnivoreGroupRepository.findCarnivoreGroupForCarnivore(carnivore).isPresent()) {

			CarnivoreGroup carnivoreGroup = carnivoreGroupRepository.findCarnivoreGroupForCarnivore(carnivore).orElse(new CarnivoreGroup("Belongs to no group", carnivore, 0));
			List<Carnivore> allCarnivoresFromGroup = carnivoreGroup.getAnimals();

			int carnivoreGroupCaughtPrey = allCarnivoresFromGroup.size();
			int preyWeightDistribution = decreaseHungerLevelFormula / (carnivoreGroupCaughtPrey + 1);
			for (Carnivore carnivoreGroupMember : allCarnivoresFromGroup) {
				int memberHungerLevel = carnivoreGroupMember.getHungerLevel();
				memberHungerLevel -= preyWeightDistribution;
				carnivoreGroupMember.setHungerLevel(memberHungerLevel);
			}

			int mainAttackerHungerLevel = carnivore.getHungerLevel();
			mainAttackerHungerLevel -= preyWeightDistribution;
			carnivore.setHungerLevel(mainAttackerHungerLevel);

		} else {
			carnivore.setHungerLevel(getCarnivoreHungerLevel - decreaseHungerLevelFormula);
			validateIfHungerLevelIsLessThanZero(carnivore);
		}
	}

	@Override
	public void hungerLevelIncreaseForEveryIteration() {
		List<Carnivore> increasedHungerRateListOfCarnivores = new ArrayList<>();
		for (var carnivore : carnivoreRepository.getAllCarnivores()) {
			increaseHungerLevel(carnivore);
			validateIfHungerLevelIsLessThanZero(carnivore);
			if (carnivore.getHungerLevel() >= 100) {
				System.out.println("Hunger rate of " + carnivore.getName() + " has reached 100.");
				increasedHungerRateListOfCarnivores.add(carnivore);
			}
		}
		for (Carnivore deadAnimal : increasedHungerRateListOfCarnivores) {
			System.out.println(deadAnimal.getName() + " has been removed from list due to hunger level increase during iteration.");
			carnivoreRepository.removeCarnivore(deadAnimal);
		}
		increasedHungerRateListOfCarnivores.clear();
	}

	/**
	 * Creates carnivore boilerplate for creating new carnivore.
	 */
	private Carnivore createsCarnivoreSample(Carnivore sampleAnimal) {
		return new Carnivore(
			sampleAnimal.getName(),
			sampleAnimal.getHabitat(),
			1,
			sampleAnimal.getMaxAge(),
			sampleAnimal.getWeight(),
			sampleAnimal.getLivingType(),
			sampleAnimal.getReproductionRate(),
			sampleAnimal.getOriginalReproductionRate(),
			sampleAnimal.getAttackPoints(),
			sampleAnimal.getHungerLevel(),
			sampleAnimal.getHungerRate()
		);
	}

	private static int decreaseHungerLevel(int caughtPreyWeight, int attackerWeight) {
		return decreaseHungerLevelByFormula(caughtPreyWeight, attackerWeight);
	}


	/**
	 * Increases hunger level of carnivore.
	 *
	 * @param carnivore this carnivore's hunger level will increase
	 */
	private void increaseHungerLevel(Carnivore carnivore) {
		int carnivoreHungerLevel = carnivore.getHungerLevel();
		int carnivoreHungerRate = carnivore.getHungerRate();
		carnivoreHungerLevel += carnivoreHungerRate;
		carnivore.setHungerLevel(carnivoreHungerLevel);
	}

	/**
	 * Add reproduced carnivore to simulation.
	 */
	private void addReproducedCarnivore(List<Carnivore> newBornAnimalsList) {
		for (Carnivore newBornAnimal : newBornAnimalsList) {
			System.out.println("A new carnivore(s) has been reproduced " + newBornAnimal.getName() + " " + newBornAnimal.getReproductionRate() + " " + newBornAnimal.getOriginalReproductionRate());
			carnivoreRepository.addCarnivore(newBornAnimal);
		}
		newBornAnimalsList.clear();
	}

	/**
	 * Reproduce alone animal and reset its reproduction rate
	 *
	 */
	private void reproduceAloneCarnivores(List<Carnivore> newBornAnimalsList, Carnivore carnivore) {
		carnivore.setReproductionRate(carnivore.getOriginalReproductionRate());
		for (int i = 0; i < 3; i++) {
			Carnivore newCarnivore = createsCarnivoreSample(carnivore);
			newBornAnimalsList.add(newCarnivore);
			newCarnivore.setReproductionRate(carnivore.getOriginalReproductionRate());
		}
	}

	private void reproduceCarnivoreGroup(List<Carnivore> newBornAnimalsList, Carnivore carnivore) {
		carnivore.setReproductionRate(carnivore.getOriginalReproductionRate());
		CarnivoreGroup carnivoreGroup = new CarnivoreGroup(carnivore.getName() + " Group", carnivore, 3);
		List<Carnivore> newListCarnivore = carnivoreGroup.getAnimals();
		int idGroup = carnivoreGroup.getId();
		// add list of animals to the existing group
		for (int i = 0; i < 3; i++) {
			Carnivore newCarnivore = createsCarnivoreSample(carnivore);
			newCarnivore.setReproductionRate(carnivore.getOriginalReproductionRate());
			newCarnivore.setGroupId(idGroup);
			newListCarnivore.add(newCarnivore);
		}
		newBornAnimalsList.add(newListCarnivore.get(0));
		carnivoreRepository.addCarnivoreGroupType(carnivore);
		carnivoreGroupRepository.addCarnivoreGroupList(carnivoreGroup);

	}


}
