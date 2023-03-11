package eu.deltasource.internship.alivingecosystem.repository.carnivoreGroupRepository;

import eu.deltasource.internship.alivingecosystem.model.animalgroup.CarnivoreGroup;
import eu.deltasource.internship.alivingecosystem.model.animals.Carnivore;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarnivoreGroupRepositoryImpl implements CarnivoreGroupRepository {

	List<CarnivoreGroup> allCarnivoreGroupsList = new ArrayList<>();

	private static CarnivoreGroupRepositoryImpl instance = null;

	public static CarnivoreGroupRepositoryImpl getInstance() {
		if (instance == null) {
			instance = new CarnivoreGroupRepositoryImpl();
		}

		return instance;
	}

	private CarnivoreGroupRepositoryImpl() {}

	@Override
	public void addCarnivoreGroupList(CarnivoreGroup carnivoreGroups) {
		allCarnivoreGroupsList.add(carnivoreGroups);
	}


	@Override
	public Optional<CarnivoreGroup> findCarnivoreGroupForCarnivore(Carnivore carnivore) {
		return allCarnivoreGroupsList.stream()
			.filter(group -> group.getId() == carnivore.getGroupId())
			.findFirst();
	}
}
