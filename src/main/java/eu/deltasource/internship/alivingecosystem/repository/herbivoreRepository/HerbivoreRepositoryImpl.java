package eu.deltasource.internship.alivingecosystem.repository.herbivoreRepository;

import eu.deltasource.internship.alivingecosystem.model.animals.Herbivore;

import java.util.ArrayList;
import java.util.List;


public class HerbivoreRepositoryImpl implements HerbivoreRepository {

	List<Herbivore> herbivores = new ArrayList<>();

	private static HerbivoreRepositoryImpl instance = null;

	public static HerbivoreRepositoryImpl getInstance() {
		if (instance == null) {
			instance = new HerbivoreRepositoryImpl();
		}
		return instance;
	}

	private HerbivoreRepositoryImpl() {}

	@Override
	public List<Herbivore> getAllHerbivores() {
		return herbivores;
	}

	@Override
	public void addHerbivore(Herbivore herbivore) {
		herbivores.add(herbivore);
	}

	@Override
	public void addAllHerbivores(List<Herbivore> herbivoresList) {
		herbivores.addAll(herbivoresList);
	}

	@Override
	public void removeHerbivore(Herbivore herbivore) {
		herbivores.remove(herbivore);
	}

}
