package org.allenfulmer.ptuviewer.generator;

import org.allenfulmer.ptuviewer.jsonLoading.PojoToDBConverter;
import org.allenfulmer.ptuviewer.models.Ability;
import org.allenfulmer.ptuviewer.models.PokemonSpecies;

import java.util.Map;
import java.util.Set;

public class PokeGen {
    public static void main(String[] args) {
        Map<String, PokemonSpecies> pokes = PojoToDBConverter.populatePokemonMaps();
        Set<Ability> a1 = AbilityGenerator.pickAbilities(pokes.get("001").getBaseAbilities(), 19);
        Set<Ability> a2 = AbilityGenerator.pickAbilities(pokes.get("002").getBaseAbilities(), 20);
        Set<Ability> a3 = AbilityGenerator.pickAbilities(pokes.get("003").getBaseAbilities(), 21);
        Set<Ability> a4 = AbilityGenerator.pickAbilities(pokes.get("001").getBaseAbilities(), 39);
        Set<Ability> a5 = AbilityGenerator.pickAbilities(pokes.get("002").getBaseAbilities(), 40);
        Set<Ability> a6 = AbilityGenerator.pickAbilities(pokes.get("003").getBaseAbilities(), 41);
        Set<Ability> a7 = AbilityGenerator.pickAbilities(pokes.get("001").getBaseAbilities(), 100);
        System.out.println("Done");
    }
}
