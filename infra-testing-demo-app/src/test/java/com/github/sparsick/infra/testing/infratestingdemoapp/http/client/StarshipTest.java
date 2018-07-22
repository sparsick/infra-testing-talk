package com.github.sparsick.infra.testing.infratestingdemoapp.http.client;

import com.github.sparsick.infra.testing.infratestingdemoapp.http.client.Starship;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


class StarshipTest {

    @Test
    void convertFromJsonMap(){
        Map jsonMap = new HashMap();
        jsonMap.put("name", "Executor");
        jsonMap.put("model", "Executor-class star dreadnought");
        jsonMap.put("cost_in_credits", "1143350000");
        jsonMap.put("length", "19000");
        jsonMap.put("max_atmosphering_speed", "n/a");
        jsonMap.put("crew", "279144");
        jsonMap.put("passengers", "38000");
        jsonMap.put("cargo_capacity", "250000000");
        jsonMap.put("consumables", "6 years");
        jsonMap.put("hyperdrive_rating", "2.0");
        jsonMap.put("MGLT", "40");
        jsonMap.put("starship_class", "Star dreadnought");
        jsonMap.put("pilots", "");

        Starship starship = Starship.from(jsonMap);

        assertThat(starship).isEqualTo(expectedStarship());
    }

    private Starship expectedStarship() {
        Starship starship = new Starship();
        starship.setName("Executor");
        starship.setModel("Executor-class star dreadnought");
        starship.setCostInCredits("1143350000");
        starship.setLength(19000d);
        starship.setMaxAtmospheringSpeed("n/a");
        starship.setCrew(279144);
        starship.setPassengers(38000);
        starship.setCargoCapacity("250000000");
        starship.setConsumables("6 years");
        starship.setHyperdriveRating(2.0);
        starship.setMglt(40);
        starship.setStarshipClass("Star dreadnought");

        return starship;
    }

}