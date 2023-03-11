package eu.deltasource.internship.alivingecosystem.repository.carnivoreGroupRepository;

import eu.deltasource.internship.alivingecosystem.model.animalgroup.CarnivoreGroup;
import eu.deltasource.internship.alivingecosystem.model.animals.Carnivore;

import java.util.Optional;

public interface CarnivoreGroupRepository {

	void addCarnivoreGroupList(CarnivoreGroup carnivoreGroups);

	Optional<CarnivoreGroup> findCarnivoreGroupForCarnivore(Carnivore carnivore);

}
