package com.github.sparsick.infra.testing.infratestingdemoapp.http.client;

import org.apache.commons.collections4.map.HashedMap;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class CharacterTest {

    @Test
    void testConvertJsonMapToCharacter(){
        Map jsonMap = new HashedMap<>();
        jsonMap.put("name", "Luke Skywalker");
        jsonMap.put("height", "172");
        jsonMap.put("mass", "77");
        jsonMap.put("hair_color","blond" );
        jsonMap.put("skin_color","fair" );
        jsonMap.put("eye_color","blue" );
        jsonMap.put("birth_year","19BBY" );
        jsonMap.put("gender", "male");

        Character character = Character.from(jsonMap);

        assertThat(character).isEqualTo(expectedCharacter());
    }

    private Character expectedCharacter() {
        Character character = new Character();
        character.setName("Luke Skywalker");
        character.setHeight(172);
        character.setMass(77);
        character.setHairColor("blond");
        character.setSkinColor("fair");
        character.setEyeColor("blue");
        character.setBirthYear("19BBY");
        character.setGender("male");
        return character;
    }
}