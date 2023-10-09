package eu.deltasource.internship.alivingecosystem.repository.herbivoreGroupRepository;

import eu.deltasource.internship.alivingecosystem.model.animalgroup.HerbivoreGroup;
import eu.deltasource.internship.alivingecosystem.model.animals.Herbivore;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

public class HerbivoreGroupRepositoryImpl implements HerbivoreGroupRepository {

	List<HerbivoreGroup> allHerbivoreGroupsList = new ArrayList<>();

	static HerbivoreGroupRepositoryImpl instance = null;

	public static HerbivoreGroupRepositoryImpl getInstance() {
		if (instance == null) {
			instance = new HerbivoreGroupRepositoryImpl();
		}

		return instance;
	}

	private HerbivoreGroupRepositoryImpl() {}

	@Override
	public List<HerbivoreGroup> getAllHerbivoreGroupsList() {
		return unmodifiableList(allHerbivoreGroupsList);
	}

	@Override
	public void addHerbivoreGroup(HerbivoreGroup herbivoreGroup) {
		allHerbivoreGroupsList.add(herbivoreGroup);
	}

	@Override
	public HerbivoreGroup findHerbivoreGroupForHerbivore(Herbivore herbivore) {
		return allHerbivoreGroupsList.stream()
			.filter(group -> group.getId() == herbivore.getGroupId())
			.findFirst()
			.orElse(null);
	}
}
