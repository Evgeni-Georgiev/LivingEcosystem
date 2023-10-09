package eu.deltasource.internship.alivingecosystem.model;

import eu.deltasource.internship.alivingecosystem.enums.HabitatType;
import eu.deltasource.internship.alivingecosystem.enums.LivingType;

public abstract class Animal {

    protected int id;

	protected int groupId;

    protected int age;

    protected int maxAge;

    protected int weight;

    protected int reproductionRate;

	protected int originalReproductionRate;

    protected String name;

    protected HabitatType habitat;

    protected LivingType livingType;

    public Animal(String name, HabitatType habitat, int age, int maxAge, int weight, LivingType livingType, int reproductionRate, int originalReproductionRate) {
        this.name = name;
        this.habitat = habitat;
        this.age = age;
        this.maxAge = maxAge;
        this.weight = weight;
        this.livingType = livingType;
        this.reproductionRate = reproductionRate;
		this.originalReproductionRate = originalReproductionRate;
    }

    public void setId(int id) {
        this.id = id;
    }

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getName() {
        return name;
    }

    public HabitatType getHabitat() {
        return habitat;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public int getWeight() {
        return weight;
    }

    public LivingType getLivingType() {
        return livingType;
    }

    public int getReproductionRate() {
        return reproductionRate;
    }

    public void setReproductionRate(int reproductionRate) {
		if(reproductionRate < 0) {
			this.reproductionRate = originalReproductionRate;
		}
    }

	public int getOriginalReproductionRate() {
		return originalReproductionRate;
	}

	@Override
    public String toString() {
        return String.format("""
                    %n Animal =>
                     id: %s
                     Name: %s
                     Age: %s
                     Max Age: %s
                     Weight: %s
                     Habitat: %s
                     Living Type: %s
                     Reproduction Rate: %s %n""",
            id, name, age, maxAge, weight, habitat, livingType, reproductionRate);
    }

}
