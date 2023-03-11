package eu.deltasource.internship.alivingecosystem.service.ecoSystemService;

import eu.deltasource.internship.alivingecosystem.model.animals.Carnivore;
import eu.deltasource.internship.alivingecosystem.model.animals.Herbivore;
import eu.deltasource.internship.alivingecosystem.service.animalService.AnimalService;
import eu.deltasource.internship.alivingecosystem.service.carnivoreService.CarnivoreService;
import eu.deltasource.internship.alivingecosystem.service.herbivoreService.HerbivoreService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EcoSystemServiceImplTest {

	@Mock
	private AnimalService animalService;

	@Mock
	private CarnivoreService carnivoreService;

	@Mock
	private HerbivoreService herbivoreService;

	@Mock
	private List<Carnivore> carnivores;

	@Mock
	private List<Herbivore> herbivores;

	@InjectMocks
	EcoSystemServiceImpl classUnderTest;

	@Test
	public void Should_SimulateEcoSystem_When_ValidData() throws Exception {

		// Given
		// Set up the mock behavior
		when(carnivores.size()).thenReturn(1);
		when(herbivores.size()).thenReturn(1);

		// When
		// Call the method once
		classUnderTest.ecoSystem();

		// Then
		// Verify that the expected methods were called exactly once
		verify(animalService).addCreatedCarnivoresToIteration(any(), any(), any(), any());
		verify(animalService).addCreatedHerbivoresToIteration(any(), any(), any(), any());
		verify(animalService).simulateAttack(any(), any());
		verify(carnivoreService).increasesCarnivoresAgeForEveryIteration();
		verify(herbivoreService).increasesHerbivoresAgeForEveryIteration();
		verify(herbivoreService).reduceHerbivoreReproductionRate();
		verify(carnivoreService).reduceCarnivoreReproductionRate();
		verify(carnivoreService).hungerLevelIncreaseForEveryIteration();

		// Verify that the simulateIteration method returns after one iteration
		verifyNoMoreInteractions(animalService, carnivoreService, herbivoreService, carnivores, herbivores);
	}


}
