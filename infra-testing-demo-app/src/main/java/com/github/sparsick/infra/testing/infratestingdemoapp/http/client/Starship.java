package com.github.sparsick.infra.testing.infratestingdemoapp.http.client;

import java.util.Map;
import java.util.Objects;

public class Starship {

    private String name;
    private String model;
    private String manufacturer;
    private String costInCredits;
    private double length;
    private String maxAtmospheringSpeed;
    private int crew;
    private int passengers;
    private String cargoCapacity;
    private String consumables;
    private double hyperdriveRating;
    private int mglt;
    private String starshipClass;


    public static Starship from(Map jsonMap) {
        Starship starship = new Starship();
        starship.name = (String) jsonMap.get("name");
        starship.model = (String) jsonMap.get("model");
        starship.costInCredits = (String) jsonMap.get("cost_in_credits");
        starship.length = Double.parseDouble((String) jsonMap.get("length"));
        starship.maxAtmospheringSpeed = (String) jsonMap.get("max_atmosphering_speed");
        starship.crew = Integer.parseInt((String) jsonMap.get("crew"));
        starship.passengers = Integer.parseInt((String) jsonMap.get("passengers"));
        starship.cargoCapacity = (String) jsonMap.get("cargo_capacity");
        starship.consumables = (String) jsonMap.get("consumables");
        starship.hyperdriveRating = Double.parseDouble((String) jsonMap.get("hyperdrive_rating"));
        starship.mglt = Integer.parseInt((String) jsonMap.get("MGLT"));
        starship.starshipClass = (String) jsonMap.get("starship_class");
        return starship;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getCostInCredits() {
        return costInCredits;
    }

    public void setCostInCredits(String costInCredits) {
        this.costInCredits = costInCredits;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public String getMaxAtmospheringSpeed() {
        return maxAtmospheringSpeed;
    }

    public void setMaxAtmospheringSpeed(String maxAtmospheringSpeed) {
        this.maxAtmospheringSpeed = maxAtmospheringSpeed;
    }

    public int getCrew() {
        return crew;
    }

    public void setCrew(int crew) {
        this.crew = crew;
    }

    public int getPassengers() {
        return passengers;
    }

    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }

    public String getCargoCapacity() {
        return cargoCapacity;
    }

    public void setCargoCapacity(String cargoCapacity) {
        this.cargoCapacity = cargoCapacity;
    }

    public String getConsumables() {
        return consumables;
    }

    public void setConsumables(String consumables) {
        this.consumables = consumables;
    }

    public double getHyperdriveRating() {
        return hyperdriveRating;
    }

    public void setHyperdriveRating(double hyperdriveRating) {
        this.hyperdriveRating = hyperdriveRating;
    }

    public int getMglt() {
        return mglt;
    }

    public void setMglt(int mglt) {
        this.mglt = mglt;
    }

    public String getStarshipClass() {
        return starshipClass;
    }

    public void setStarshipClass(String starshipClass) {
        this.starshipClass = starshipClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Starship starship = (Starship) o;
        return Double.compare(starship.length, length) == 0 &&
                crew == starship.crew &&
                passengers == starship.passengers &&
                cargoCapacity == starship.cargoCapacity &&
                Double.compare(starship.hyperdriveRating, hyperdriveRating) == 0 &&
                mglt == starship.mglt &&
                Objects.equals(name, starship.name) &&
                Objects.equals(model, starship.model) &&
                Objects.equals(manufacturer, starship.manufacturer) &&
                Objects.equals(costInCredits, starship.costInCredits) &&
                Objects.equals(maxAtmospheringSpeed, starship.maxAtmospheringSpeed) &&
                Objects.equals(consumables, starship.consumables) &&
                Objects.equals(starshipClass, starship.starshipClass);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, model, manufacturer, costInCredits, length, maxAtmospheringSpeed, crew, passengers, cargoCapacity, consumables, hyperdriveRating, mglt, starshipClass);
    }

    @Override
    public String toString() {
        return "Starship{" +
                "name='" + name + '\'' +
                ", model='" + model + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", costInCredits='" + costInCredits + '\'' +
                ", length=" + length +
                ", maxAtmospheringSpeed='" + maxAtmospheringSpeed + '\'' +
                ", crew=" + crew +
                ", passengers=" + passengers +
                ", cargoCapacity=" + cargoCapacity +
                ", consumables='" + consumables + '\'' +
                ", hyperdriveRating=" + hyperdriveRating +
                ", mglt=" + mglt +
                ", starshipClass='" + starshipClass + '\'' +
                '}';
    }
}
