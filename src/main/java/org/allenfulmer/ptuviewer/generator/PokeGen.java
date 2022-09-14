package org.allenfulmer.ptuviewer.generator;

import org.allenfulmer.ptuviewer.generator.models.Nature;
import org.allenfulmer.ptuviewer.jsonLoading.PojoToDBConverter;
import org.allenfulmer.ptuviewer.models.PokemonSpecies;
import org.allenfulmer.ptuviewer.models.Stat;

import java.util.Map;

public class PokeGen {
    public static void main(String[] args) {
        abilityGenTest();
    }

    public static void abilityGenTest() {
        Map<String, PokemonSpecies> pokes = PojoToDBConverter.populatePokemonMaps();
//        Set<Ability> a1 = AbilityGenerator.pickAbilities(pokes.get("001").getBaseAbilities(), 19);
//        Set<Ability> a2 = AbilityGenerator.pickAbilities(pokes.get("002").getBaseAbilities(), 20);
//        Set<Ability> a3 = AbilityGenerator.pickAbilities(pokes.get("003").getBaseAbilities(), 21);
//        Set<Ability> a4 = AbilityGenerator.pickAbilities(pokes.get("001").getBaseAbilities(), 39);
//        Set<Ability> a5 = AbilityGenerator.pickAbilities(pokes.get("002").getBaseAbilities(), 40);
//        Set<Ability> a6 = AbilityGenerator.pickAbilities(pokes.get("003").getBaseAbilities(), 41);
//        Set<Ability> a7 = AbilityGenerator.pickAbilities(pokes.get("001").getBaseAbilities(), 100);

        Map<Stat.StatName, Stat> s1 = StatGenerator.generateStats(pokes.get("066").getBaseStats(), 1, Nature.CUDDLY);
        Map<Stat.StatName, Stat> s2 = StatGenerator.generateStats(pokes.get("066").getBaseStats(), 10, Nature.CUDDLY);
        Map<Stat.StatName, Stat> s3 = StatGenerator.generateStats(pokes.get("066").getBaseStats(), 20, Nature.CUDDLY);
        Map<Stat.StatName, Stat> s4 = StatGenerator.generateStats(pokes.get("066").getBaseStats(), 30, Nature.CUDDLY);
        Map<Stat.StatName, Stat> s5 = StatGenerator.generateStats(pokes.get("066").getBaseStats(), 40, Nature.CUDDLY);
        Map<Stat.StatName, Stat> s6 = StatGenerator.generateStats(pokes.get("066").getBaseStats(), 50, Nature.CUDDLY);
        Map<Stat.StatName, Stat> s7 = StatGenerator.generateStats(pokes.get("066").getBaseStats(), 60, Nature.CUDDLY);
        Map<Stat.StatName, Stat> s8 = StatGenerator.generateStats(pokes.get("066").getBaseStats(), 70, Nature.CUDDLY);
        Map<Stat.StatName, Stat> s9 = StatGenerator.generateStats(pokes.get("066").getBaseStats(), 80, Nature.CUDDLY);
        Map<Stat.StatName, Stat> s0 = StatGenerator.generateStats(pokes.get("066").getBaseStats(), 90, Nature.CUDDLY);
        Map<Stat.StatName, Stat> sA = StatGenerator.generateStats(pokes.get("066").getBaseStats(), 100, Nature.CUDDLY);
        Map<Stat.StatName, Stat> sX = StatGenerator.generateStats(pokes.get("150").getBaseStats(), 100, Nature.CAREFUL);
        System.out.println("Done");
    }
}
