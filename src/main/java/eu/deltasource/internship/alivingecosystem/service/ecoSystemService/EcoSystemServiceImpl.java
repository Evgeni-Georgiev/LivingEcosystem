package eu.deltasource.internship.alivingecosystem.service.ecoSystemService;

import eu.deltasource.internship.alivingecosystem.enums.HabitatType;
import eu.deltasource.internship.alivingecosystem.enums.LivingType;
import eu.deltasource.internship.alivingecosystem.model.animals.Carnivore;
import eu.deltasource.internship.alivingecosystem.model.animals.Herbivore;
import eu.deltasource.internship.alivingecosystem.repository.carnivoreGroupRepository.CarnivoreGroupRepository;
import eu.deltasource.internship.alivingecosystem.repository.carnivoreGroupRepository.CarnivoreGroupRepositoryImpl;
import eu.deltasource.internship.alivingecosystem.repository.carnivoreRepository.CarnivoreRepository;
import eu.deltasource.internship.alivingecosystem.repository.carnivoreRepository.CarnivoreRepositoryImpl;
import eu.deltasource.internship.alivingecosystem.repository.herbivoreGroupRepository.HerbivoreGroupRepository;
import eu.deltasource.internship.alivingecosystem.repository.herbivoreGroupRepository.HerbivoreGroupRepositoryImpl;
import eu.deltasource.internship.alivingecosystem.repository.herbivoreRepository.HerbivoreRepository;
import eu.deltasource.internship.alivingecosystem.repository.herbivoreRepository.HerbivoreRepositoryImpl;
import eu.deltasource.internship.alivingecosystem.service.animalService.AnimalService;
import eu.deltasource.internship.alivingecosystem.service.animalService.AnimalServiceImpl;
import eu.deltasource.internship.alivingecosystem.service.carnivoreService.CarnivoreService;
import eu.deltasource.internship.alivingecosystem.service.carnivoreService.CarnivoreServiceImpl;
import eu.deltasource.internship.alivingecosystem.service.herbivoreService.HerbivoreService;
import eu.deltasource.internship.alivingecosystem.service.herbivoreService.HerbivoreServiceImpl;

import java.util.List;
import java.util.Random;

public class EcoSystemServiceImpl implements EcoSystemService {

	@Override
	public void ecoSystem() throws InterruptedException {

		CarnivoreRepository carnivoreRepository = CarnivoreRepositoryImpl.getInstance();
		CarnivoreGroupRepository carnivoreGroupRepository = CarnivoreGroupRepositoryImpl.getInstance();
		HerbivoreRepository herbivoreRepository = HerbivoreRepositoryImpl.getInstance();
		HerbivoreGroupRepository herbivoreGroupRepository = HerbivoreGroupRepositoryImpl.getInstance();

		CarnivoreService carnivoreService = new CarnivoreServiceImpl(carnivoreRepository, carnivoreGroupRepository);
		HerbivoreService herbivoreService = new HerbivoreServiceImpl(herbivoreRepository, herbivoreGroupRepository);

		AnimalService animalService = new AnimalServiceImpl(carnivoreRepository, herbivoreRepository, carnivoreService, carnivoreGroupRepository, herbivoreGroupRepository, herbivoreService);

		Carnivore lion = new Carnivore("Grouped Lion", HabitatType.LAND, 1, 20, 150, LivingType.GROUP, 6, 6, 45.0, 0, 20);
		Carnivore cheetah = new Carnivore("Single Cheetah", HabitatType.LAND, 1, 30, 60, LivingType.ALONE, 5, 5, 115.0, 0, 15);
		Carnivore tiger = new Carnivore("Grouped Tiger", HabitatType.LAND, 1, 20, 200, LivingType.ALONE, 6, 6, 23.0, 0, 18);
		Carnivore hyena = new Carnivore("Grouped Hyena", HabitatType.LAND, 1, 24, 50, LivingType.GROUP, 5, 5, 33.0, 0, 14);

		Herbivore zebra = new Herbivore("Grouped Zebra", HabitatType.LAND, 1, 50, 300, LivingType.GROUP, 10, 10, 80.0);
		Herbivore hare = new Herbivore("Single Hare", HabitatType.LAND, 1, 24, 5, LivingType.ALONE, 3, 3, 80.0);
		Herbivore gazelle = new Herbivore("Grouped gazelle", HabitatType.LAND, 1, 25, 25, LivingType.GROUP, 5, 5, 80.0);
		Herbivore buffalo = new Herbivore("Grouped buffalo", HabitatType.LAND, 1, 35, 300, LivingType.GROUP, 9, 9, 40.0);


		animalService.addCreatedCarnivoresToIteration(lion, cheetah, tiger, hyena);
		animalService.addCreatedHerbivoresToIteration(zebra, gazelle, buffalo);

		List<Carnivore> carnivores = carnivoreRepository.getAllCarnivores();
		List<Herbivore> herbivores = herbivoreRepository.getAllHerbivores();

		while (true) {

			if (carnivores.size() == 0) {
				animalService.addCreatedCarnivoresToIteration(lion, cheetah, tiger, hyena);
			}

			if (herbivores.size() == 0) {
				animalService.addCreatedHerbivoresToIteration(zebra, hare, gazelle, buffalo);
			}

			Carnivore carnivoreIndex = carnivores.get(new Random().nextInt(0, carnivores.size()));
			Herbivore herbivoreIndex = herbivores.get(new Random().nextInt(0, herbivores.size()));

			animalService.simulateAttack(carnivoreIndex, herbivoreIndex);

			carnivoreService.increasesCarnivoresAgeForEveryIteration();
			herbivoreService.increasesHerbivoresAgeForEveryIteration();

			herbivoreService.reduceHerbivoreReproductionRate();
			carnivoreService.reduceCarnivoreReproductionRate();

			carnivoreService.hungerLevelIncreaseForEveryIteration();

			System.out.println("------------------------------------------");
			Thread.sleep(1000);

		}
	}
}
