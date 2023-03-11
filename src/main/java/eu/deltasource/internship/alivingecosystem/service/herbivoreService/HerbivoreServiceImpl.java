package eu.deltasource.internship.alivingecosystem.service.herbivoreService;

import eu.deltasource.internship.alivingecosystem.enums.LivingType;
import eu.deltasource.internship.alivingecosystem.model.animalgroup.HerbivoreGroup;
import eu.deltasource.internship.alivingecosystem.model.animals.Herbivore;
import eu.deltasource.internship.alivingecosystem.repository.herbivoreGroupRepository.HerbivoreGroupRepository;
import eu.deltasource.internship.alivingecosystem.repository.herbivoreRepository.HerbivoreRepository;

import java.util.ArrayList;
import java.util.List;

public class HerbivoreServiceImpl implements HerbivoreService {

	private final HerbivoreRepository herbivoreRepository;

	private final HerbivoreGroupRepository herbivoreGroupRepository;

	public HerbivoreServiceImpl(HerbivoreRepository herbivoreRepository, HerbivoreGroupRepository herbivoreGroupRepository) {
		this.herbivoreRepository = herbivoreRepository;
		this.herbivoreGroupRepository = herbivoreGroupRepository;
	}

	@Override
	public void createHerbivore(String nameOfAnimal, Herbivore sampleAnimal, int numberOfAnimals) {
		if (sampleAnimal.getLivingType() == LivingType.GROUP) {
			HerbivoreGroup herbivoreGroup = new HerbivoreGroup(nameOfAnimal + " Group", sampleAnimal, numberOfAnimals);
			List<Herbivore> newListHerbivore = herbivoreGroup.getAnimals();
			int idGroup = herbivoreGroup.getId();
			// add list of animals to the existing group
			for (int i = 0; i < numberOfAnimals; i++) {
				sampleAnimal = createsHerbivoreSample(sampleAnimal);
				newListHerbivore.add(sampleAnimal);
				herbivoreGroup.getAnimals().get(i).setGroupId(idGroup);
			}
			herbivoreRepository.addHerbivore(sampleAnimal);
			herbivoreGroupRepository.addHerbivoreGroup(herbivoreGroup);
		} else if (sampleAnimal.getLivingType() == LivingType.ALONE) {
			List<Herbivore> newAloneHerbivores = new ArrayList<>();
			for (int i = 0; i < numberOfAnimals; i++) {
				sampleAnimal = createsHerbivoreSample(sampleAnimal);
				newAloneHerbivores.add(sampleAnimal);
			}
			herbivoreRepository.addAllHerbivores(newAloneHerbivores);
		}
	}

	@Override
	public void reduceHerbivoreReproductionRate() {
		int decreaseCounter = 1;

		List<Herbivore> newBornAnimalsList = new ArrayList<>();
		for (Herbivore herbivore : herbivoreRepository.getAllHerbivores()) {

			int currentReproductionRate = herbivore.getReproductionRate();
			currentReproductionRate -= decreaseCounter;
			herbivore.setReproductionRate(currentReproductionRate);

			if (herbivore.getReproductionRate() == 0) {
				if (herbivoreGroupRepository.findHerbivoreGroupForHerbivore(herbivore).isPresent()) {
					reproduceHerbivoreGroup(newBornAnimalsList, herbivore);
				} else {
					reproduceAloneHerbivores(newBornAnimalsList, herbivore);
				}
			}
		}
		for (Herbivore newBornAnimal : newBornAnimalsList) {
			herbivoreRepository.addHerbivore(newBornAnimal);
		}
		newBornAnimalsList.clear();

	}

	@Override
	public void increasesHerbivoresAgeForEveryIteration() {
		int increaseCounter = 1;

		List<Herbivore> deadAnimalsList = new ArrayList<>();
		for (var herbivore : herbivoreRepository.getAllHerbivores()) {
			if (herbivoreGroupRepository.findHerbivoreGroupForHerbivore(herbivore).isPresent()) {
				HerbivoreGroup herbivoreGroup = herbivoreGroupRepository.findHerbivoreGroupForHerbivore(herbivore).orElse(new HerbivoreGroup("Belongs to no group", herbivore, 0));
				List<Herbivore> allHerbivoresFromGroup = herbivoreGroup.getAnimals();
				for (var memberOfGroup : allHerbivoresFromGroup) {
					int currentAge = memberOfGroup.getAge();
					currentAge += increaseCounter;
					memberOfGroup.setAge(currentAge);
					if (memberOfGroup.getAge() >= memberOfGroup.getMaxAge()) {
						deadAnimalsList.add(memberOfGroup);
					}

				}
			} else {
				int currentAge = herbivore.getAge();
				currentAge += increaseCounter;
				herbivore.setAge(currentAge);
				if (herbivore.getAge() >= herbivore.getMaxAge()) {
					deadAnimalsList.add(herbivore);
				}
			}
		}

		for (Herbivore deadAnimal : deadAnimalsList) {
			herbivoreRepository.removeHerbivore(deadAnimal);
		}
		deadAnimalsList.clear();
	}

	/**
	 * Reproduces alone herbivores
	 *
	 * @param newBornAnimalsList list to hold the herbivores
	 * @param herbivore herbivore sample based on which the new herbivores will be created
	 */
	private void reproduceAloneHerbivores(List<Herbivore> newBornAnimalsList, Herbivore herbivore) {
		for (int i = 0; i < 3; i++) {
			Herbivore newHerbivore = createsHerbivoreSample(herbivore);
			newHerbivore.setReproductionRate(herbivore.getOriginalReproductionRate());
			newBornAnimalsList.add(newHerbivore);
		}
	}

	/**
	 * Reproduce new group of herbivores
	 *
	 * @param newBornAnimalsList list holding the newborn herbivore group
	 * @param herbivore herbivore sample based on which the herbivores of the new group will be reproduced
	 */
	private void reproduceHerbivoreGroup(List<Herbivore> newBornAnimalsList, Herbivore herbivore) {
		HerbivoreGroup herbivoreGroup = new HerbivoreGroup(herbivore.getName() + " Group ", herbivore, 3);
		List<Herbivore> newListHerbivore = herbivoreGroup.getAnimals();
		int idGroup = herbivoreGroup.getId();
		// add list of animals to the existing group
		for (int i = 0; i < 3; i++) {
			Herbivore newHerbivore = createsHerbivoreSample(herbivore);
			newHerbivore.setGroupId(idGroup);
			newListHerbivore.add(newHerbivore);
		}

		newBornAnimalsList.add(newListHerbivore.get(0));
		herbivoreGroupRepository.addHerbivoreGroup(herbivoreGroup);
	}

	/**
	 * Creates herbivore boilerplate for creating new herbivore
	 */
	private Herbivore createsHerbivoreSample(Herbivore sampleAnimal) {
		return new Herbivore(
			sampleAnimal.getName(),
			sampleAnimal.getHabitat(),
			1,
			sampleAnimal.getMaxAge(),
			sampleAnimal.getWeight(),
			sampleAnimal.getLivingType(),
			sampleAnimal.getReproductionRate(),
			sampleAnimal.getOriginalReproductionRate(),
			sampleAnimal.getEscapePoints());
	}

}
