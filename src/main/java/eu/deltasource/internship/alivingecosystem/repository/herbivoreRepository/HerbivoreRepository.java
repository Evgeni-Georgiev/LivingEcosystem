package eu.deltasource.internship.alivingecosystem.repository.herbivoreRepository;

import eu.deltasource.internship.alivingecosystem.model.animals.Herbivore;

import java.util.List;

public interface HerbivoreRepository {

	List<Herbivore> getAllHerbivores();

	void addHerbivore(Herbivore herbivore);

	void addAllHerbivores(List<Herbivore> herbivoresList);

	void removeHerbivore(Herbivore herbivore);

}
