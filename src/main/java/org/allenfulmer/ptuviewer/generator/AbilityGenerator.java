package org.allenfulmer.ptuviewer.generator;

import org.allenfulmer.ptuviewer.models.Ability;
import org.allenfulmer.ptuviewer.models.BaseAbility;

import java.util.*;
import java.util.stream.Collectors;

public class AbilityGenerator {
    private static final double LOWER_TIER_CHANCE = 0.5;
    private static final Random RANDOM_GEN = new Random();

    private AbilityGenerator() {
    }

    public static Set<Ability> pickAbilities(Set<BaseAbility> baseAbilities, int level) {
        Set<Ability> abilities = new HashSet<>();
        boolean onlyHighest = RANDOM_GEN.nextDouble() >= LOWER_TIER_CHANCE;

        Arrays.stream(BaseAbility.AbilityType.values()).filter(a -> a.getLevel() <= level).sorted().forEach(curr -> {
            List<Ability> possibleAbilities = pickPossibleAbilities(baseAbilities, abilities, curr, onlyHighest);
            if (!(possibleAbilities == null || possibleAbilities.isEmpty()))
                abilities.add(possibleAbilities.get(RANDOM_GEN.nextInt(possibleAbilities.size())));
        });
        return abilities;
    }

    private static List<Ability> pickPossibleAbilities(Set<BaseAbility> baseAbilities, Set<Ability> picked,
                                                       BaseAbility.AbilityType highestType, boolean onlyHighest) {
        List<Ability> ret = null;
        if (!onlyHighest) // If it can be any ability rank, just grab all possible ones
        {
            ret = gatherAbilitiesToType(baseAbilities, highestType);
            ret.removeAll(picked);
            return ret;
        }

        // Otherwise, try to grab only the highest level of unpicked Abilities
        for (BaseAbility.AbilityType currType = highestType; (ret == null || ret.isEmpty()) && currType != null;
             currType = currType.getPrevType()) {
            ret = gatherAbilitiesOfType(baseAbilities, currType);
            ret.removeAll(picked);
        }
        return ret;
    }

    private static List<Ability> gatherAbilitiesOfType(Set<BaseAbility> baseAbilities, BaseAbility.AbilityType type) {
        return baseAbilities.stream().filter(b -> b.getAbilityType() == type)
                .map(BaseAbility::getAbility).collect(Collectors.toList());
    }

    private static List<Ability> gatherAbilitiesToType(Set<BaseAbility> baseAbilities, BaseAbility.AbilityType type) {
        return baseAbilities.stream().filter(b -> b.getAbilityType().getLevel() <= type.getLevel())
                .map(BaseAbility::getAbility).collect(Collectors.toList());
    }
}
