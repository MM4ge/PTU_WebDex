package org.allenfulmer.ptuviewer.generator;

import org.allenfulmer.ptuviewer.jsonLoading.PojoToDBConverter;
import org.allenfulmer.ptuviewer.models.Ability;
import org.allenfulmer.ptuviewer.models.PokemonSpecies;

import java.util.Map;
import java.util.Set;

public class PokeGen {
    public static void main(String[] args) {
        Map<String, PokemonSpecies> pokes = PojoToDBConverter.populatePokemonMaps();
        Set<Ability> a1 = AbilityGenerator.selectAbilities(pokes.get("001").getBaseAbilities(), 1);
        Set<Ability> a2 = AbilityGenerator.selectAbilities(pokes.get("004").getBaseAbilities(), 19);
        Set<Ability> a3 = AbilityGenerator.selectAbilities(pokes.get("007").getBaseAbilities(), 25);
        Set<Ability> a4 = AbilityGenerator.selectAbilities(pokes.get("010").getBaseAbilities(), 33);
        Set<Ability> a5 = AbilityGenerator.selectAbilities(pokes.get("025").getBaseAbilities(), 40);
        Set<Ability> a6 = AbilityGenerator.selectAbilities(pokes.get("042").getBaseAbilities(), 70);
        Set<Ability> a7 = AbilityGenerator.selectAbilities(pokes.get("150").getBaseAbilities(), 100);
        // TODO: Abilities are picked in priority order BUT you have to have a basic, then can CHOOSE adv or basic then basic, adv, or high
        System.out.println("Done");
    }
}
