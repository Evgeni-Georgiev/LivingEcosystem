package eu.deltasource.internship.alivingecosystem.service.animalService;

import eu.deltasource.internship.alivingecosystem.enums.HabitatType;
import eu.deltasource.internship.alivingecosystem.enums.LivingType;
import eu.deltasource.internship.alivingecosystem.model.animalgroup.CarnivoreGroup;
import eu.deltasource.internship.alivingecosystem.model.animalgroup.HerbivoreGroup;
import eu.deltasource.internship.alivingecosystem.model.animals.Carnivore;
import eu.deltasource.internship.alivingecosystem.model.animals.Herbivore;
import eu.deltasource.internship.alivingecosystem.repository.carnivoreGroupRepository.CarnivoreGroupRepository;
import eu.deltasource.internship.alivingecosystem.repository.herbivoreGroupRepository.HerbivoreGroupRepository;
import eu.deltasource.internship.alivingecosystem.service.carnivoreService.CarnivoreServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AnimalServiceImplTest {

	@Mock
	CarnivoreGroupRepository carnivoreGroupRepository;

	@Mock
	HerbivoreGroupRepository herbivoreGroupRepository;

	@Mock
	CarnivoreServiceImpl carnivoreService;

	@InjectMocks
	AnimalServiceImpl classUnderTest;

//	@Test
//	public void Should_SimulateAttackGroupCarnivore_When_ValidData() {
//
//		// Given
//		Carnivore carnivore = new Carnivore("Group Cheetah", HabitatType.LAND, 1, 30, 60, LivingType.GROUP, 5, 5, 115.0, 95, 15);
//		Carnivore carnivore1 = new Carnivore("Group Cheetah", HabitatType.LAND, 1, 30, 60, LivingType.GROUP, 5, 5, 115.0, 95, 15);
//		Carnivore carnivore2 = new Carnivore("Group Cheetah", HabitatType.LAND, 1, 30, 60, LivingType.GROUP, 5, 5, 115.0, 95, 15);
//		List<Carnivore> carnivoreList = new ArrayList<>();
//		carnivoreList.add(carnivore);
//		carnivoreList.add(carnivore1);
//		carnivoreList.add(carnivore2);
//		carnivoreList.get(0).setGroupId(2);
//		carnivoreList.get(1).setGroupId(2);
//		carnivoreList.get(2).setGroupId(2);
//		CarnivoreGroup carnivoreGroup = new CarnivoreGroup("Grouped Cheetah", carnivoreList.get(0), 3);
//		carnivoreGroup.getAnimals().add(carnivore);
//		carnivoreGroup.getAnimals().add(carnivore1);
//		carnivoreGroup.getAnimals().add(carnivore2);
//
//		List<Herbivore> herbivoreList = new ArrayList<>();
//		Herbivore herbivore = new Herbivore("Grouped Zebra", HabitatType.LAND, 49, 50, 5, LivingType.GROUP, 10, 10, 80.0);
//		herbivoreList.add(herbivore);
//		herbivoreList.get(0).setGroupId(2);
//
//		HerbivoreGroup herbivoreGroup = new HerbivoreGroup("Grouped Zebra", herbivoreList.get(0), 3);
//		herbivoreGroup.getAnimals().add(herbivore);
//
//		when(carnivoreGroupRepository.findCarnivoreGroupForCarnivore(any(Carnivore.class))).thenReturn(carnivoreGroup);
//		when(herbivoreGroupRepository.findHerbivoreGroupForHerbivore(any(Herbivore.class))).thenReturn(herbivoreGroup);
//		carnivoreService.hungerLevelDecrease(carnivore, herbivore);
//
//		// When
//		classUnderTest.simulateAttack(carnivore, herbivore);
//
//		// Then
//		Assertions.assertEquals("Group Cheetah", carnivore.getName());
//
//	}

//	@Test
//	public void Should_SimulateAttackAloneCarnivore_When_ValidData() {
//
//		// Given
//		Carnivore carnivore = new Carnivore("Alone Cheetah", HabitatType.LAND, 1, 30, 60, LivingType.ALONE, 5, 5, 115.0, 95, 15);
//		Herbivore herbivore = new Herbivore("Alone Zebra", HabitatType.LAND, 49, 50, 5, LivingType.ALONE, 10, 10, 80.0);
//
//		carnivoreService.hungerLevelDecrease(carnivore, herbivore);
//
//		// When
//		classUnderTest.simulateAttack(carnivore, herbivore);
//
//		// Then
//		Assertions.assertEquals("Alone Cheetah", carnivore.getName());
//
//	}



}
