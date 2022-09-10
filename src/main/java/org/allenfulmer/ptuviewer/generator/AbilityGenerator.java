package org.allenfulmer.ptuviewer.generator;

import org.allenfulmer.ptuviewer.models.Ability;
import org.allenfulmer.ptuviewer.models.BaseAbility;

import java.util.*;
import java.util.stream.Collectors;

public class AbilityGenerator {
    private static final double LOWER_TIER_CHANCE = 1;
    private static final Random RANDOM_GEN = new Random();

    private AbilityGenerator() {
    }

    public static Set<Ability> selectAbilities(Set<BaseAbility> baseAbilities, int level) {
        Set<Ability> abilities = new HashSet<>();
        boolean onlyHighest = RANDOM_GEN.nextDouble() >= LOWER_TIER_CHANCE;
        List<Ability> pickableAbilities = gatherAbilitiesWithChance(baseAbilities, abilities, level, onlyHighest);

        for (int abilityPicks = (Math.min(level, BaseAbility.AbilityType.HIGH.getLevel()) / 20) + 1;
             pickableAbilities != null && abilityPicks > 0; abilityPicks--) {
            Ability newAbility = pickableAbilities.get(RANDOM_GEN.nextInt(pickableAbilities.size()));
            abilities.add(newAbility);
            pickableAbilities = gatherAbilitiesWithChance(baseAbilities, abilities, level, onlyHighest);
        }
        return abilities;
    }

    private static List<Ability> gatherAbilitiesWithChance(Set<BaseAbility> baseAbilities, Set<Ability> picked,
                                                           int level, boolean onlyHighest) {
        List<Ability> ret = null;
        if (!onlyHighest) // If it can be any ability rank, just grab all possible ones
        {
            ret = new ArrayList<>(gatherAbilitiesToLevel(baseAbilities, level));
            ret.removeAll(picked);
            return ret;
        }

        // Otherwise, try to grab only the highest level of unpicked Abilities
        int currLevel = level;
        while ((ret == null || ret.isEmpty()) && currLevel >= 0) {
            ret = new ArrayList<>(gatherAbilitiesOfHighestType(baseAbilities, currLevel));
            ret.removeAll(picked);
            // Probably a better way to decrement the level to ensure we step down ability tiers, but this is
            //  future-proofing in case PTU changes the levels that ability tiers unlock or adds more
            currLevel -= BaseAbility.AbilityType.ADVANCED.getLevel();
        }
        return ret;
    }

    private static Set<Ability> gatherAbilitiesOfHighestType(Set<BaseAbility> baseAbilities, int level) {
        Set<Ability> ret = new HashSet<>();
        List<BaseAbility.AbilityType> types = Arrays.stream(BaseAbility.AbilityType.values())
                .filter(b -> b.getLevel() <= level).sorted(Collections.reverseOrder()).collect(Collectors.toList());
        for (BaseAbility.AbilityType curr : types) {
            ret = gatherAbilitiesOfType(baseAbilities, curr);
            if (ret != null && !ret.isEmpty())
                return ret;
        }
        return ret;
    }

    private static Set<Ability> gatherAbilitiesOfType(Set<BaseAbility> baseAbilities, BaseAbility.AbilityType type) {
        return baseAbilities.stream().filter(b -> b.getAbilityType() == type)
                .map(BaseAbility::getAbility).collect(Collectors.toSet());
    }

    private static Set<Ability> gatherAbilitiesToLevel(Set<BaseAbility> baseAbilities, int level) {
        return baseAbilities.stream().filter(b -> b.getAbilityType().getLevel() <= level)
                .map(BaseAbility::getAbility).collect(Collectors.toSet());
    }
}
