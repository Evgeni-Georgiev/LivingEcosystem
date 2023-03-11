package eu.deltasource.internship.alivingecosystem.enums;

public enum HabitatType {

    LAND("Land"),

    WATER("Water"),

    AIR("Air");

    public final String habitat;

    HabitatType(String habitat) {
        this.habitat = habitat;
    }
}
