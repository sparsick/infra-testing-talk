package com.github.sparsick.infra.testing.infratestingdemoapp.http.client;

import java.util.Map;
import java.util.Objects;

public class Character {

    private String name;
    private int height;
    private String mass;
    private String hairColor;
    private String skinColor;
    private String eyeColor;
    private String birthYear;
    private String gender;


    static Character from(Map map) {
        Character character = new Character();
        character.name = (String) map.get("name");
        character.height = Integer.parseInt((String) map.get("height"));
        character.mass = (String) map.get("mass");
        character.hairColor = (String) map.get("hair_color");
        character.skinColor = (String) map.get("skin_color");
        character.eyeColor = (String) map.get("eye_color");
        character.birthYear = (String) map.get("birth_year");
        character.gender = (String) map.get("gender");
        return character;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getMass() {
        return mass;
    }

    public void setMass(String mass) {
        this.mass = mass;
    }

    public String getHairColor() {
        return hairColor;
    }

    public void setHairColor(String hairColor) {
        this.hairColor = hairColor;
    }

    public String getSkinColor() {
        return skinColor;
    }

    public void setSkinColor(String skinColor) {
        this.skinColor = skinColor;
    }

    public String getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(String eyeColor) {
        this.eyeColor = eyeColor;
    }

    public String getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Character character = (Character) o;
        return height == character.height &&
                mass == character.mass &&
                Objects.equals(name, character.name) &&
                Objects.equals(hairColor, character.hairColor) &&
                Objects.equals(skinColor, character.skinColor) &&
                Objects.equals(eyeColor, character.eyeColor) &&
                Objects.equals(birthYear, character.birthYear) &&
                Objects.equals(gender, character.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, height, mass, hairColor, skinColor, eyeColor, birthYear, gender);
    }

    @Override
    public String toString() {
        return "Character{" +
                "name='" + name + '\'' +
                ", height=" + height +
                ", mass=" + mass +
                ", hairColor='" + hairColor + '\'' +
                ", skinColor='" + skinColor + '\'' +
                ", eyeColor='" + eyeColor + '\'' +
                ", birthYear='" + birthYear + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
