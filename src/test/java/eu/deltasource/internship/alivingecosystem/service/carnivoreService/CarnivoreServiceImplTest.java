package eu.deltasource.internship.alivingecosystem.service.carnivoreService;

import static org.mockito.Mockito.*;

import eu.deltasource.internship.alivingecosystem.model.animalgroup.CarnivoreGroup;
import eu.deltasource.internship.alivingecosystem.model.animalgroup.HerbivoreGroup;
import eu.deltasource.internship.alivingecosystem.model.animals.Carnivore;
import eu.deltasource.internship.alivingecosystem.model.animals.Herbivore;
import eu.deltasource.internship.alivingecosystem.enums.HabitatType;
import eu.deltasource.internship.alivingecosystem.enums.LivingType;
import eu.deltasource.internship.alivingecosystem.repository.carnivoreGroupRepository.CarnivoreGroupRepository;
import eu.deltasource.internship.alivingecosystem.repository.carnivoreRepository.CarnivoreRepository;
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

@ExtendWith(MockitoExtension.class)
class CarnivoreServiceImplTest {

	@Mock
	CarnivoreRepository carnivoreRepository;

	@Mock
	CarnivoreGroupRepository carnivoreGroupRepository;

	@InjectMocks
	CarnivoreServiceImpl classUnderTest;

	private List<Carnivore> initTestData() {
		List<Carnivore> carnivoreList = new ArrayList<>();
		carnivoreList.add(new Carnivore("Single Cheetah", HabitatType.LAND, 1, 30, 60, LivingType.ALONE, 0, 3, 115.0, 0, 15));
		return carnivoreList;
	}

	private List<Carnivore> initTestDataGroup() {
		List<Carnivore> carnivoreList = new ArrayList<>();
		carnivoreList.add(new Carnivore("Grouped Tiger", HabitatType.LAND, 1, 20, 200, LivingType.ALONE, 0, 3,  23.0, 95, 18));
		return carnivoreList;
	}

	@Test
	public void Should_CreateGroupCarnivore_When_ValidData() {

		// Given
		Carnivore carnivore = new Carnivore("Grouped Tiger", HabitatType.LAND, 1, 50, 300, LivingType.GROUP, 10, 10, 80.0, 0, 15);

		// When
		classUnderTest.createCarnivore(carnivore.getName(), carnivore, 1);

		// Then
		Assertions.assertEquals("Grouped Tiger", carnivore.getName());
	}

	@Test
	public void Should_CreateAloneHerbivore_When_ValidData() {

		// Given
		Carnivore carnivore = new Carnivore("Single Cheetah", HabitatType.LAND, 1, 24, 5, LivingType.ALONE, 0, 3, 80.0, 0, 15);

		// When
		classUnderTest.createCarnivore(carnivore.getName(), carnivore, 1);

		// Then
		Assertions.assertEquals("Single Cheetah", carnivore.getName());
	}

	@Test
	public void Should_hungerLevelIncrease_When_ValidData() {

		// Given
		List<Carnivore> carnivoreList = new ArrayList<>();
		carnivoreList.add(new Carnivore("Single Cheetah", HabitatType.LAND, 1, 30, 60, LivingType.ALONE, 5, 5, 115.0, 95, 15));
		ArgumentCaptor<Carnivore> carnivoreArgumentCaptor = ArgumentCaptor.forClass(Carnivore.class);

		// When
		classUnderTest.hungerLevelIncrease(carnivoreList.get(0));

		// Then
		verify(carnivoreRepository, times(1)).removeCarnivore(carnivoreArgumentCaptor.capture());
		Assertions.assertEquals(1, carnivoreArgumentCaptor.getAllValues().size());
	}

	@Test
	public void Should_hungerLevelDecrease_When_ValidData() {

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
		CarnivoreGroup carnivoreGroup = new CarnivoreGroup("Cheetah Group", carnivoreList.get(0), 3);
		carnivoreGroup.getAnimals().add(carnivore);
		carnivoreGroup.getAnimals().add(carnivore1);
		carnivoreGroup.getAnimals().add(carnivore2);

		List<Herbivore> herbivoreList = new ArrayList<>();
		Herbivore herbivore = new Herbivore("Grouped Zebra", HabitatType.LAND, 49, 50, 5, LivingType.GROUP, 10, 10, 80.0);
		herbivoreList.add(herbivore);
		herbivoreList.get(0).setGroupId(UUID.fromString("48400000-8cf0-11bd-b23e-10b96e4ef00d"));

		HerbivoreGroup herbivoreGroup = new HerbivoreGroup("Zebra Group", herbivoreList.get(0), 3);
		herbivoreGroup.getAnimals().add(herbivore);

		when(carnivoreGroupRepository.findCarnivoreGroupForCarnivore(any(Carnivore.class))).thenReturn(Optional.of(carnivoreGroup));

		// When
		classUnderTest.hungerLevelDecrease(carnivore, herbivore);

		// Then
		Assertions.assertAll(
			() -> Assertions.assertEquals(91, carnivore.getHungerLevel()),
			() -> Assertions.assertEquals(93, carnivore1.getHungerLevel()),
			()->Assertions.assertEquals(93, carnivore2.getHungerLevel()));

	}

	@Test
	public void Should_ReproduceAloneCarnivore_When_ValidData() {

		// Given
		ArgumentCaptor<Carnivore> carnivoreArgumentCaptor = ArgumentCaptor.forClass(Carnivore.class);
		when(carnivoreRepository.getAllCarnivores()).thenReturn(initTestData());

		// When
		classUnderTest.reduceCarnivoreReproductionRate();

		// Then
		verify(carnivoreRepository, times(3)).addCarnivore(carnivoreArgumentCaptor.capture());
		Assertions.assertEquals(3, carnivoreArgumentCaptor.getAllValues().size());
	}

	@Test
	public void Should_ReproduceGroupCarnivore_When_ValidData() {

		// Given
		Carnivore carnivore = new Carnivore("Grouped Tiger", HabitatType.LAND, 1, 20, 200, LivingType.GROUP, 10, 10,  23.0, 0, 18);
		ArgumentCaptor<Carnivore> carnivoreArgumentCaptor = ArgumentCaptor.forClass(Carnivore.class);
		when(carnivoreRepository.getAllCarnivores()).thenReturn(initTestDataGroup());
		when(carnivoreGroupRepository.findCarnivoreGroupForCarnivore(any(Carnivore.class))).thenReturn(Optional.of(new CarnivoreGroup("testGroup", carnivore, 3)));

		// When
		classUnderTest.reduceCarnivoreReproductionRate();

		// Then
		verify(carnivoreRepository, times(1)).addCarnivore(carnivoreArgumentCaptor.capture());
		Assertions.assertEquals(1, carnivoreArgumentCaptor.getAllValues().size());
	}

	@Test
	void Should_increasesCarnivoresAloneAgeForEveryIteration_When_ValidData() {
		// Given
		List<Carnivore> carnivoreList = new ArrayList<>();
		carnivoreList.add(new Carnivore("Single Cheetah", HabitatType.LAND, 1, 2, 60, LivingType.ALONE, 0, 3, 115.0, 0, 15));
		ArgumentCaptor<Carnivore> carnivoreArgumentCaptor = ArgumentCaptor.forClass(Carnivore.class);
		when(carnivoreRepository.getAllCarnivores()).thenReturn(carnivoreList);

		// When
		classUnderTest.increasesCarnivoresAgeForEveryIteration();

		// Then
		verify(carnivoreRepository, times(1)).removeCarnivore(carnivoreArgumentCaptor.capture());
		Assertions.assertEquals(1, carnivoreArgumentCaptor.getAllValues().size());

	}

	@Test
	void Should_increasesCarnivoresGroupAgeForEveryIteration_When_ValidData() {

		// Given
		List<Carnivore> carnivoreList = new ArrayList<>();
		carnivoreList.add(new Carnivore("Group Tiger", HabitatType.LAND, 23, 24, 5, LivingType.GROUP, 0, 3, 80.0, 0, 15));
		carnivoreList.get(0).setGroupId(UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d"));

		CarnivoreGroup carnivoreGroup = new CarnivoreGroup("testGroup", carnivoreList.get(0), 3);
		Carnivore carnivore = new Carnivore("Grouped Tiger", HabitatType.LAND, 49, 50, 300, LivingType.GROUP, 10, 10, 80.0, 0, 15);
		carnivoreGroup.getAnimals().add(carnivore);

		ArgumentCaptor<Carnivore> carnivoreArgumentCaptor = ArgumentCaptor.forClass(Carnivore.class);
		when(carnivoreRepository.getAllCarnivores()).thenReturn(carnivoreList);
		when(carnivoreGroupRepository.findCarnivoreGroupForCarnivore(any(Carnivore.class))).thenReturn(Optional.of(carnivoreGroup));

		// When
		classUnderTest.increasesCarnivoresAgeForEveryIteration();

		// Then
		verify(carnivoreRepository, times(1)).removeCarnivore(carnivoreArgumentCaptor.capture());
		Assertions.assertEquals(1, carnivoreArgumentCaptor.getAllValues().size());

	}

	@Test
	void Should_increasesCarnivoresAloneHungerLevelForEveryIteration_When_ValidData() {

		// Given
		List<Carnivore> carnivoreList = new ArrayList<>();
		carnivoreList.add(new Carnivore("Single Cheetah", HabitatType.LAND, 6, 24, 5, LivingType.ALONE, 0, 3, 80.0, 100, 15));
		ArgumentCaptor<Carnivore> carnivoreArgumentCaptor = ArgumentCaptor.forClass(Carnivore.class);
		when(carnivoreRepository.getAllCarnivores()).thenReturn(carnivoreList);

		// When
		classUnderTest.hungerLevelIncreaseForEveryIteration();

		// Then
		verify(carnivoreRepository, times(1)).removeCarnivore(carnivoreArgumentCaptor.capture());
		Assertions.assertEquals(1, carnivoreArgumentCaptor.getAllValues().size());

	}

}
