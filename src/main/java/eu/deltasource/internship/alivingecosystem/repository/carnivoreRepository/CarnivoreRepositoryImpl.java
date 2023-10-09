package eu.deltasource.internship.alivingecosystem.repository.carnivoreRepository;

import eu.deltasource.internship.alivingecosystem.enums.LivingType;
import eu.deltasource.internship.alivingecosystem.model.animals.Carnivore;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

public class CarnivoreRepositoryImpl implements CarnivoreRepository {

    List<Carnivore> animalGroupList = new ArrayList<>();

    List<Carnivore> animals = new ArrayList<>();

	static CarnivoreRepositoryImpl instance = null;

    public static CarnivoreRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new CarnivoreRepositoryImpl();
        }

        return instance;
    }

    private CarnivoreRepositoryImpl() {}

	@Override
	public List<Carnivore> getAllCarnivores() {
		return unmodifiableList(animals);
	}

	@Override
	public void addCarnivore(Carnivore carnivore) {
		animals.add(carnivore);
	}

	@Override
	public void addAllToCarnivore(List<Carnivore> carnivores) {
		animals.addAll(carnivores);
	}

	@Override
	public void removeCarnivore(Carnivore carnivore) {
		animals.remove(carnivore);
	}

    @Override
    public void addCarnivoreGroupType(Carnivore carnivore) {
        if(carnivore.getLivingType().equals(LivingType.GROUP)) {
            animalGroupList.add(carnivore);
        }
    }

}
