package eu.deltasource.internship.alivingecosystem.repository.carnivoreGroupRepository;

import eu.deltasource.internship.alivingecosystem.model.animalgroup.CarnivoreGroup;
import eu.deltasource.internship.alivingecosystem.model.animals.Carnivore;

public interface CarnivoreGroupRepository {

	void addCarnivoreGroupList(CarnivoreGroup carnivoreGroups);

	CarnivoreGroup findCarnivoreGroupForCarnivore(Carnivore carnivore);

}
