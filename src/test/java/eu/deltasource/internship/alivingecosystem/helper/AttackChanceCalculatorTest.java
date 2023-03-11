package eu.deltasource.internship.alivingecosystem.helper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AttackChanceCalculatorTest {

	@Test
	public void Should_calculateChanceOfSuccessAttackOfGroupCarnivore_When_ValidData() {
		Assertions.assertEquals(6.5, AttackChanceCalculator.applyGroupHerbivoreModifier(5));
	}
}
