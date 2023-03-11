package eu.deltasource.internship.alivingecosystem.repository.herbivoreGroupRepository;

import eu.deltasource.internship.alivingecosystem.model.animalgroup.HerbivoreGroup;
import eu.deltasource.internship.alivingecosystem.model.animals.Herbivore;

import java.util.List;
import java.util.Optional;

public interface HerbivoreGroupRepository {

	void addHerbivoreGroup(HerbivoreGroup herbivoreGroup);

//	HerbivoreGroup findHerbivoreGroupForHerbivore(Herbivore herbivore);
	Optional<HerbivoreGroup> findHerbivoreGroupForHerbivore(Herbivore herbivore);

	List<HerbivoreGroup> getAllHerbivoreGroupsList();

}
