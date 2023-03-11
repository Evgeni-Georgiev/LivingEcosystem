package eu.deltasource.internship.alivingecosystem.repository.carnivoreRepository;

import eu.deltasource.internship.alivingecosystem.model.animals.Carnivore;

import java.util.List;

public interface CarnivoreRepository {

    void addCarnivoreGroupType(Carnivore carnivore);

    List<Carnivore> getAllCarnivores();

	void addCarnivore(Carnivore carnivore);

	void addAllToCarnivore(List<Carnivore> carnivores);

	void removeCarnivore(Carnivore carnivore);

}
