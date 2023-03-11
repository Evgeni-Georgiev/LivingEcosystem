package eu.deltasource.internship.alivingecosystem.service.herbivoreService;

import static org.mockito.Mockito.*;

import eu.deltasource.internship.alivingecosystem.model.animalgroup.HerbivoreGroup;
import eu.deltasource.internship.alivingecosystem.model.animals.Herbivore;
import eu.deltasource.internship.alivingecosystem.enums.HabitatType;
import eu.deltasource.internship.alivingecosystem.enums.LivingType;
import eu.deltasource.internship.alivingecosystem.repository.herbivoreGroupRepository.HerbivoreGroupRepository;
import eu.deltasource.internship.alivingecosystem.repository.herbivoreRepository.HerbivoreRepository;
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
class HerbivoreServiceImplTest {

	@Mock
	HerbivoreRepository herbivoreRepository;

	@Mock
	HerbivoreGroupRepository herbivoreGroupRepository;

	@InjectMocks
	HerbivoreServiceImpl classUnderTest;

	private List<Herbivore> initTestData() {
		List<Herbivore> herbivoreList = new ArrayList<>();
		herbivoreList.add(new Herbivore("Single Hare", HabitatType.LAND, 1, 24, 5, LivingType.ALONE, 0, 3, 80.0));
		return herbivoreList;
	}

	private List<Herbivore> initTestDataGroup() {
		List<Herbivore> herbivoreList = new ArrayList<>();
		herbivoreList.add(new Herbivore("Group Zebra", HabitatType.LAND, 1, 24, 5, LivingType.GROUP, 0, 3, 80.0));
		return herbivoreList;
	}

	@Test
	public void Should_CreateGroupCarnivore_When_ValidData() {

		// Given
		Herbivore herbivore = new Herbivore("Grouped Zebra", HabitatType.LAND, 1, 50, 300, LivingType.GROUP, 10, 10, 80.0);

		// When
		classUnderTest.createHerbivore(herbivore.getName(), herbivore, 1);

		// Then
		Assertions.assertEquals("Grouped Zebra", herbivore.getName());
	}

	@Test
	public void Should_CreateAloneHerbivore_When_ValidData() {

		// Given
		Herbivore herbivore = new Herbivore("Single Hare", HabitatType.LAND, 1, 24, 5, LivingType.ALONE, 0, 3, 80.0);

		// When
		classUnderTest.createHerbivore(herbivore.getName(), herbivore, 1);

		// Then
		Assertions.assertEquals("Single Hare", herbivore.getName());
	}

	@Test
	public void Should_ReproduceAloneHerbivore_When_ValidData() {

		// Given
		// Captures the Herbivore object when passed to addHerbivore()
		ArgumentCaptor<Herbivore> herbivoreArgumentCaptor = ArgumentCaptor.forClass(Herbivore.class);

		// setup mock object for herbivoreRepository, when getAllHerbivores() is called it will return test data
		when(herbivoreRepository.getAllHerbivores()).thenReturn(initTestData());

		// When
		classUnderTest.reduceHerbivoreReproductionRate();

		// Then
		// verify that the addHerbivore() was called 3 times and captures Herbivore object passed through ArgumentCaptor
		verify(herbivoreRepository, times(3)).addHerbivore(herbivoreArgumentCaptor.capture());

		Assertions.assertEquals(3, herbivoreArgumentCaptor.getAllValues().size());
	}

	@Test
	public void Should_ReproduceGroupHerbivore_When_ValidData() {

		// Given
		Herbivore herbivore = new Herbivore("Grouped Zebra", HabitatType.LAND, 1, 50, 300, LivingType.GROUP, 10, 10, 80.0);

		// Captures the Herbivore object when passed to addHerbivore()
		ArgumentCaptor<Herbivore> herbivoreArgumentCaptor = ArgumentCaptor.forClass(Herbivore.class);

		// setup mock object for herbivoreRepository, when getAllHerbivores() is called it will return test data
		when(herbivoreRepository.getAllHerbivores()).thenReturn(initTestDataGroup());

		// setup mock object for herbivoreGroupRepository, when findHerbivoreGroupForHerbivore() is called it will return a new object HerbivoreGroup
		when(herbivoreGroupRepository.findHerbivoreGroupForHerbivore(any(Herbivore.class))).thenReturn(Optional.of(new HerbivoreGroup("testGroup", herbivore, 3)));

		// When
		classUnderTest.reduceHerbivoreReproductionRate();

		// Then
		// verify that addHerbivore() was called one time and captures Herbivore object passed through ArgumentCaptor
		verify(herbivoreRepository, times(1)).addHerbivore(herbivoreArgumentCaptor.capture());

		Assertions.assertEquals(1, herbivoreArgumentCaptor.getAllValues().size());
	}

	@Test
	void Should_increasesHerbivoresAloneAgeForEveryIteration_When_ValidData() {

		// Given
		List<Herbivore> herbivoreList = new ArrayList<>();
		herbivoreList.add(new Herbivore("Single Zebra", HabitatType.LAND, 23, 24, 5, LivingType.ALONE, 0, 3, 80.0));

		// Captures the Herbivore object when passed to removeHerbivore()
		ArgumentCaptor<Herbivore> herbivoreArgumentCaptor = ArgumentCaptor.forClass(Herbivore.class);

		// setup mock object for herbivoreRepository, when getAllHerbivores() is called it will return test data from herbivoreList
		when(herbivoreRepository.getAllHerbivores()).thenReturn(herbivoreList);

		// When
		classUnderTest.increasesHerbivoresAgeForEveryIteration();


		// Then
		// verify that removeHerbivore() is called one time and captures Herbivore object passed through ArgumentCaptor
		verify(herbivoreRepository, times(1)).removeHerbivore(herbivoreArgumentCaptor.capture());

		Assertions.assertEquals(1, herbivoreArgumentCaptor.getAllValues().size());

	}

	@Test
	void Should_increasesHerbivoresGroupAgeForEveryIteration_When_ValidData() {

		// Given
		List<Herbivore> herbivoreList = new ArrayList<>();

		herbivoreList.add(new Herbivore("Group Zebra", HabitatType.LAND, 23, 24, 5, LivingType.GROUP, 0, 3, 80.0));
		herbivoreList.get(0).setGroupId(UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d"));

		HerbivoreGroup herbivoreGroup = new HerbivoreGroup("testGroup", herbivoreList.get(0), 3);

		Herbivore herbivore = new Herbivore("Grouped Zebra", HabitatType.LAND, 49, 50, 300, LivingType.GROUP, 10, 10, 80.0);
		herbivoreGroup.getAnimals().add(herbivore);

		// Captures the Herbivore object that was passed through removeHerbivore()
		ArgumentCaptor<Herbivore> herbivoreArgumentCaptor = ArgumentCaptor.forClass(Herbivore.class);

		// setup mock object for herbivore repository, when getAllHerbivores() is called it will return test data
		when(herbivoreRepository.getAllHerbivores()).thenReturn(herbivoreList);
		when(herbivoreGroupRepository.findHerbivoreGroupForHerbivore(any(Herbivore.class))).thenReturn(Optional.of(herbivoreGroup));

		// When
		classUnderTest.increasesHerbivoresAgeForEveryIteration();

		// verify that removeHerbivore() in called one time and captures Herbivore object passed through ArgumentCaptor
		verify(herbivoreRepository, times(1)).removeHerbivore(herbivoreArgumentCaptor.capture());

		// Then
		Assertions.assertEquals(1, herbivoreArgumentCaptor.getAllValues().size());

	}

}
