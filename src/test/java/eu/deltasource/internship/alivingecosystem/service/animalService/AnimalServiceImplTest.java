package eu.deltasource.internship.alivingecosystem.service.animalService;

import eu.deltasource.internship.alivingecosystem.enums.HabitatType;
import eu.deltasource.internship.alivingecosystem.enums.LivingType;
import eu.deltasource.internship.alivingecosystem.model.animalgroup.CarnivoreGroup;
import eu.deltasource.internship.alivingecosystem.model.animalgroup.HerbivoreGroup;
import eu.deltasource.internship.alivingecosystem.model.animals.Carnivore;
import eu.deltasource.internship.alivingecosystem.model.animals.Herbivore;
import eu.deltasource.internship.alivingecosystem.repository.carnivoreGroupRepository.CarnivoreGroupRepository;
import eu.deltasource.internship.alivingecosystem.repository.herbivoreGroupRepository.HerbivoreGroupRepository;
import eu.deltasource.internship.alivingecosystem.repository.herbivoreRepository.HerbivoreRepository;
import eu.deltasource.internship.alivingecosystem.service.carnivoreService.CarnivoreServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnimalServiceImplTest {

	@Mock
	CarnivoreGroupRepository carnivoreGroupRepository;

	@Mock
	HerbivoreGroupRepository herbivoreGroupRepository;

	@Mock
	HerbivoreRepository herbivoreRepository;

	@Mock
	CarnivoreServiceImpl carnivoreService;

	@InjectMocks
	AnimalServiceImpl classUnderTest;

	@Test
	public void Should_SimulateAttackGroupCarnivore_When_ValidData() {

		// Given
		Carnivore carnivore = new Carnivore("Group Cheetah", HabitatType.LAND, 1, 30, 60, LivingType.GROUP, 5, 5, 115.0, 95, 15);
		Carnivore carnivore1 = new Carnivore("Group Cheetah", HabitatType.LAND, 1, 30, 60, LivingType.GROUP, 5, 5, 115.0, 95, 15);
		Carnivore carnivore2 = new Carnivore("Group Cheetah", HabitatType.LAND, 1, 30, 60, LivingType.GROUP, 5, 5, 115.0, 95, 15);
		List<Carnivore> carnivoreList = new ArrayList<>();
		carnivoreList.add(carnivore);
		carnivoreList.add(carnivore1);
		carnivoreList.add(carnivore2);
		carnivoreList.get(0).setGroupId(UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d"));
		carnivoreList.get(1).setGroupId(UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d"));
		carnivoreList.get(2).setGroupId(UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d"));
		CarnivoreGroup carnivoreGroup = new CarnivoreGroup("Grouped Cheetah", carnivoreList.get(0), 3);
		carnivoreGroup.getAnimals().add(carnivore);
		carnivoreGroup.getAnimals().add(carnivore1);
		carnivoreGroup.getAnimals().add(carnivore2);

		List<Herbivore> herbivoreList = new ArrayList<>();
		Herbivore herbivore = new Herbivore("Grouped Zebra", HabitatType.LAND, 49, 50, 5, LivingType.GROUP, 10, 10, 80.0);
		herbivoreList.add(herbivore);
		herbivoreList.get(0).setGroupId(UUID.fromString("48400000-8cf0-11bd-b23e-10b96e4ef00d"));

		HerbivoreGroup herbivoreGroup = new HerbivoreGroup("Grouped Zebra", herbivoreList.get(0), 3);
		herbivoreGroup.getAnimals().add(herbivore);

		ArgumentCaptor<Herbivore> herbivoreArgumentCaptor = ArgumentCaptor.forClass(Herbivore.class);
		when(carnivoreGroupRepository.findCarnivoreGroupForCarnivore(any(Carnivore.class))).thenReturn(Optional.of(carnivoreGroup));
		when(herbivoreGroupRepository.findHerbivoreGroupForHerbivore(any(Herbivore.class))).thenReturn(Optional.of(herbivoreGroup));
		carnivoreService.hungerLevelDecrease(carnivore, herbivore);

		// When
		classUnderTest.simulateAttack(carnivore, herbivore);

		// Then
		verify(herbivoreRepository, times(1)).removeHerbivore(herbivoreArgumentCaptor.capture());
		Assertions.assertEquals("Group Cheetah", carnivore.getName());

	}

	@Test
	public void Should_SimulateAttackAloneCarnivore_When_ValidData() {

		// Given
		Carnivore carnivore = new Carnivore("Alone Cheetah", HabitatType.LAND, 1, 30, 60, LivingType.ALONE, 5, 5, 115.0, 95, 15);
		Herbivore herbivore = new Herbivore("Alone Zebra", HabitatType.LAND, 49, 50, 5, LivingType.ALONE, 10, 10, 80.0);

		carnivoreService.hungerLevelDecrease(carnivore, herbivore);

		// When
		classUnderTest.simulateAttack(carnivore, herbivore);

		// Then
		Assertions.assertEquals("Alone Cheetah", carnivore.getName());

	}



}
