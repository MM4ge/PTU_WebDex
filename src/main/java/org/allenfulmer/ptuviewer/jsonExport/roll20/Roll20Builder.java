package org.allenfulmer.ptuviewer.jsonExport.roll20;

import com.google.gson.JsonPrimitive;
import org.allenfulmer.ptuviewer.generator.Pokemon;
import org.allenfulmer.ptuviewer.generator.models.Nature;
import org.allenfulmer.ptuviewer.models.*;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Roll20Builder {
    private final StringBuilder notesBuilder = new StringBuilder("""
            General Generator / Converter Notes:
            If the Sheet has weird things (like a move having another's effect or missing entirely), make a new character sheet and re-import the same JSON from the Import page. If it still exists, tell Mage about the error and give him the JSON.
            """);

    private final StringBuilder changeNotesBuilder = new StringBuilder("Manual Changes Notes:\n");
    private boolean abilitiesAsMoves = false;
    private boolean connectionInfoInMoves = false;
    private boolean autoNumberNames = false;

    // FC: Add additional flag for adding formes abbreviations on the ends of names so this doesn't increment for
    //  different form versions (i.e. Meowth (Standard) and Meowth (Galarian) would end up as Meowth 1 and Meowth 2
    //  even if they aren't exactly the same species
    // FC: This won't work for a web-hosted version, but is fine for local-only. Change how builder works later if
    //  counting is offered to the web version as this will cause different web users' counts to interfere.
    private static Map<String, Integer> autoNumberNamesMap;
    private static final Predicate<Ability> abilityMovesPred = a -> !a.getFrequency().equals(Frequency.SCENE) && !a.getFrequency().equals(Frequency.DAILY);

    private final Pokemon poke;
    private PokemonRoll20 rollPoke;


    public Roll20Builder(org.allenfulmer.ptuviewer.generator.Pokemon poke) {
        this.poke = poke;
    }

    public Roll20Builder setAbilitiesAsMoves(boolean flag) {
        abilitiesAsMoves = flag;
        return this;
    }

    public Roll20Builder setConnectionInfoInMoves(boolean flag) {
        connectionInfoInMoves = flag;
        return this;
    }

    public Roll20Builder setAutoNumberNames(boolean flag) {
        autoNumberNames = flag;
        return this;
    }

    public PokemonRoll20 build() {
        rollPoke = new PokemonRoll20();

        PokemonSpecies species = poke.getSpecies();
        List<Type> types = species.getTypes();
        String speciesName = species.getSpeciesName();
        if (species.getForm() != null && !species.getForm().isEmpty() && !species.getForm().equalsIgnoreCase("Standard"))
            speciesName += " (" + species.getForm() + ")";
        rollPoke.setSpecies(speciesName);
        // Add counter to end of nickname if autoNumber flag is set - Meowth (1), Meowth (2), etc.
        if(autoNumberNames) {
            if(autoNumberNamesMap == null)
                autoNumberNamesMap = new HashMap<>();
            Map<String, Integer> namesMap = autoNumberNamesMap;

            int count = namesMap.getOrDefault(speciesName, 0);
            namesMap.put(speciesName, ++count);
            rollPoke.setNickname(speciesName + " (" + count + ")");
        }
        else
            rollPoke.setNickname(speciesName);

        rollPoke.setType1(types.get(0).getDisplayName());
        if (types.size() > 1)
            rollPoke.setType2(types.get(1).getDisplayName());

        rollPoke.setLevel(poke.getLevel());
        rollPoke.setGender(poke.getGender());
        Nature pokeNat = poke.getNature();
        rollPoke.setNature(pokeNat.getDisplayName());
        if (pokeNat.getRaise() != pokeNat.getLower()) { // applied (hp +1, def -2)
            notesBuilder.append("Nature changes to base stats already applied (");
            notesBuilder.append(pokeNat.getRaise().getShortName());
            notesBuilder.append(" +");
            notesBuilder.append(pokeNat.getRaiseValue());
            notesBuilder.append(", ");
            notesBuilder.append(pokeNat.getLower().getShortName());
            notesBuilder.append(" -");
            notesBuilder.append(pokeNat.getLowerValue());
            notesBuilder.append(").\n");
        }
        // FC: make weight random between the min/max instead of a range
        rollPoke.setHeight(species.getHeightCategoryMax());
        rollPoke.setWeightClass(species.getWeightClassMax());

        Map<Stat.StatName, Stat> stats = poke.getStats();
        rollPoke.setBaseHp(stats.get(Stat.StatName.HP).getBase());
        rollPoke.setBaseAtk(stats.get(Stat.StatName.ATTACK).getBase());
        rollPoke.setBaseDef(stats.get(Stat.StatName.DEFENSE).getBase());
        rollPoke.setBaseSpAtk(stats.get(Stat.StatName.SPECIAL_ATTACK).getBase());
        rollPoke.setBaseSpDef(stats.get(Stat.StatName.SPECIAL_DEFENSE).getBase());
        rollPoke.setBaseSpeed(stats.get(Stat.StatName.SPEED).getBase());

        rollPoke.setHp(stats.get(Stat.StatName.HP).getAllocated());
        rollPoke.setAtk(stats.get(Stat.StatName.ATTACK).getAllocated());
        rollPoke.setDef(stats.get(Stat.StatName.DEFENSE).getAllocated());
        rollPoke.setSpatk(stats.get(Stat.StatName.SPECIAL_ATTACK).getAllocated());
        rollPoke.setSpdef(stats.get(Stat.StatName.SPECIAL_DEFENSE).getAllocated());
        rollPoke.setSpeed(stats.get(Stat.StatName.SPEED).getAllocated());

        rollPoke.setHpBonus(stats.get(Stat.StatName.HP).getBonus());
        rollPoke.setAtkBonus(stats.get(Stat.StatName.ATTACK).getBonus());
        rollPoke.setDefBonus(stats.get(Stat.StatName.DEFENSE).getBonus());
        rollPoke.setSpAtkBonus(stats.get(Stat.StatName.SPECIAL_ATTACK).getBonus());
        rollPoke.setSpDefBonus(stats.get(Stat.StatName.SPECIAL_DEFENSE).getBonus());
        rollPoke.setSpeedBonus(stats.get(Stat.StatName.SPEED).getBonus());

        //Pokémon Hit Points = Pokémon Level + (HP x3) + 10
        rollPoke.setHitPoints(rollPoke.getLevel() + (stats.get(Stat.StatName.HP).getTotal() * 3) + 10);

        // Capabilities - Map<String, JsonPrimitive> capabilities
        rollPoke.setCapabilities(species.getBaseCapabilities().stream().collect(Collectors.toMap(b -> {
                    String ret = b.getFullName();
                    if (ret.equalsIgnoreCase("High Jump"))
                        ret = "HJ";
                    else if (ret.equalsIgnoreCase("Long Jump"))
                        ret = "LJ";
                    return ret;
                }, b -> {
                    if (b.getRank() != -1)
                        return new JsonPrimitive(b.getRank());
                    String ret = b.getCriteria();
                    return (Objects.nonNull(ret)) ? new JsonPrimitive(ret) : new JsonPrimitive(true);
                }
        )));

        // Moves -- Could use switch statement without break; to keep it going?
        convertMoves();

        // Abilities
        convertAbilities();

        // Skills
        convertSkills();

        // Combat at Expert or higher causes Struggle to lose 1 AC and gain 1 DB
        if (rollPoke.getCombat() >= 5) {
            rollPoke.setStruggleAC(3);
            rollPoke.setStruggleDB(5);
            notesBuilder.append("Struggle's -1 AC / +1 DB for having Combat at Expert or higher already applied.");
            notesBuilder.append("\n");
        }

        // Notes
        if (changeNotesBuilder.length() > 23) {
            notesBuilder.append("\n");
            notesBuilder.append(changeNotesBuilder);
        }
        notesBuilder.append("Any stat, capability, or move changes by Abilities or Moves, if present, need to be manually applied.\n");
        rollPoke.setNotes(notesBuilder.toString());

        return rollPoke;
    }

    private void convertAbilities() //Set<Ability> origAbilities)
    { // poke.getAbilities()
        List<Ability> abilities = new ArrayList<>(poke.getAbilities());
        if (!abilities.isEmpty())
            rollPoke.setAbility1(new AbilityRoll20(abilities.get(0)));
        if (abilities.size() > 1)
            rollPoke.setAbility2(new AbilityRoll20(abilities.get(1)));
        if (abilities.size() > 2)
            rollPoke.setAbility3(new AbilityRoll20(abilities.get(2)));
        if (abilities.size() > 3)
            rollPoke.setAbility4(new AbilityRoll20(abilities.get(3)));
        if (abilities.size() > 4)
            rollPoke.setAbility5(new AbilityRoll20(abilities.get(4)));
        if (abilities.size() > 5)
            rollPoke.setAbility6(new AbilityRoll20(abilities.get(5)));
        if (abilities.size() > 6)
            rollPoke.setAbility7(new AbilityRoll20(abilities.get(6)));
        if (abilities.size() > 7)
            rollPoke.setAbility8(new AbilityRoll20(abilities.get(7)));
        if (abilities.size() > 8)
            rollPoke.setAbility9(new AbilityRoll20(abilities.get(8)));
        if (abilities.size() > 9)
            rollPoke.setAbility10(new AbilityRoll20(abilities.get(9)));
        if (abilities.size() > 10)
            rollPoke.setAbility11(new AbilityRoll20(abilities.get(10)));
        if (abilities.size() > 11)
            rollPoke.setAbility12(new AbilityRoll20(abilities.get(11)));
    }

    private void convertMoves() {
        List<Move> moves = poke.getMoves();
        List<Type> types = poke.getSpecies().getTypes();

        List<MoveRoll20> convertedMoves = new ArrayList<>(moves.size());
        List<Move> stabMoves = new ArrayList<>(moves.size());
        List<Move> zeroDBStabMoves = new ArrayList<>(moves.size());
        List<Move> zeroDBAfterDBMoves = new ArrayList<>(moves.size());
        List<Move> sceneAndDailyMoves = new ArrayList<>(moves.size());
        List<Move> connectionMoves = new ArrayList<>(moves.size());

        boolean anyPrevDB = false;
        for (Move curr : moves) {
            boolean sameType = types.contains(curr.getType());
            boolean attackingMove = (curr.getMoveClass() == Move.MoveClass.PHYSICAL || curr.getMoveClass() == Move.MoveClass.SPECIAL);
            //&& !curr.getDb().contains(PokeConstants.SEE_EFFECT_DB);
            boolean connectedMove = connectionInfoInMoves && poke.getAbilities().stream().anyMatch(a -> curr.equals(a.getConnection()));
            boolean zeroDb = true;
            try {
                zeroDb = Integer.parseInt(curr.getDb()) == 0;
            } catch (NumberFormatException ignored) {
                // Left empty intentionally
            }

            if (zeroDb) {
                // Moves without a DB inherit the damage totals of the previously occurring DB total in R20
                if (anyPrevDB)
                    zeroDBAfterDBMoves.add(curr);
                // STAB can't be automatically added to 0 DB same-typed attacking moves
                if (sameType && attackingMove)
                    zeroDBStabMoves.add(curr);
            }

            // Only add STAB to same typed attacking moves - already handled 0 DB moves
            else if (attackingMove) {
                anyPrevDB = true;
                if (sameType)
                    stabMoves.add(curr);
            }

            // Moves with # of uses - R20 doesn't have an import for max uses
            if (curr.getFrequency().equals(Frequency.SCENE) || curr.getFrequency().equals(Frequency.DAILY)) {
                sceneAndDailyMoves.add(curr);
            }

            // Flag for manually adding the +2 STAB bonus since R20 doesn't have an import for that checkbox
            boolean stab = sameType && attackingMove && !zeroDb;
            if (connectedMove) {
                connectionMoves.add(curr);
                convertedMoves.add(new MoveRoll20(curr, stab, getConnectionText(curr)));
            } else
                convertedMoves.add(new MoveRoll20(curr, stab));
        }

        // Notes
        appendStabNotes(stabMoves, zeroDBStabMoves);
        appendZeroDBNotes(zeroDBAfterDBMoves);
        appendUseNotes(sceneAndDailyMoves);
        if (connectionInfoInMoves)
            appendConnectionNotes(connectionMoves);

        if (abilitiesAsMoves)
            convertAbilitiesToMoves(convertedMoves, anyPrevDB);

        if (!convertedMoves.isEmpty())
            rollPoke.setMove1(convertedMoves.get(0));
        if (convertedMoves.size() > 1)
            rollPoke.setMove2(convertedMoves.get(1));
        if (convertedMoves.size() > 2)
            rollPoke.setMove3(convertedMoves.get(2));
        if (convertedMoves.size() > 3)
            rollPoke.setMove4(convertedMoves.get(3));
        if (convertedMoves.size() > 4)
            rollPoke.setMove5(convertedMoves.get(4));
        if (convertedMoves.size() > 5)
            rollPoke.setMove6(convertedMoves.get(5));
        if (convertedMoves.size() > 6)
            rollPoke.setMove7(convertedMoves.get(6));
        if (convertedMoves.size() > 7)
            rollPoke.setMove8(convertedMoves.get(7));
        if (convertedMoves.size() > 8)
            rollPoke.setMove9(convertedMoves.get(8));
        if (convertedMoves.size() > 9)
            rollPoke.setMove10(convertedMoves.get(9));
        if (convertedMoves.size() > 10)
            rollPoke.setMove11(convertedMoves.get(10));
        if (convertedMoves.size() > 11)
            rollPoke.setMove12(convertedMoves.get(11));
        if (convertedMoves.size() > 12)
            rollPoke.setMove13(convertedMoves.get(12));
        if (convertedMoves.size() > 13)
            rollPoke.setMove14(convertedMoves.get(13));
        if (convertedMoves.size() > 14)
            rollPoke.setMove15(convertedMoves.get(14));
        if (convertedMoves.size() > 15)
            rollPoke.setMove16(convertedMoves.get(15));
        if (convertedMoves.size() > 16)
            rollPoke.setMove17(convertedMoves.get(16));
        if (convertedMoves.size() > 17)
            rollPoke.setMove18(convertedMoves.get(17));
        if (convertedMoves.size() > 18)
            rollPoke.setMove19(convertedMoves.get(18));
        if (convertedMoves.size() > 19)
            rollPoke.setMove20(convertedMoves.get(19));
        if (convertedMoves.size() > 20)
            rollPoke.setMove21(convertedMoves.get(20));
        if (convertedMoves.size() > 21)
            rollPoke.setMove22(convertedMoves.get(21));
        if (convertedMoves.size() > 22)
            rollPoke.setMove23(convertedMoves.get(22));
        if (convertedMoves.size() > 23)
            rollPoke.setMove24(convertedMoves.get(23));
        if (convertedMoves.size() > 24)
            rollPoke.setMove25(convertedMoves.get(24));
        if (convertedMoves.size() > 25)
            rollPoke.setMove26(convertedMoves.get(25));
        if (convertedMoves.size() > 26)
            rollPoke.setMove27(convertedMoves.get(26));
        if (convertedMoves.size() > 27)
            rollPoke.setMove28(convertedMoves.get(27));
        if (convertedMoves.size() > 28)
            rollPoke.setMove29(convertedMoves.get(28));
        if (convertedMoves.size() > 29)
            rollPoke.setMove30(convertedMoves.get(29));
    }

    private void convertAbilitiesToMoves(List<MoveRoll20> convertedMoves, boolean anyPrevDB) {
        Set<Ability> abilities = new HashSet<>(poke.getAbilities());
        abilities.removeIf(a -> Arrays.asList(Frequency.STATIC, Frequency.SEE_TEXT, Frequency.SPECIAL).contains(a.getFrequency()));
        convertedMoves.addAll(abilities.stream().map(MoveRoll20::new).toList());

        // Notes
        changeNotesBuilder.append((abilities.size() > 1) ? "These abilities have " : "An ability has ");
        if (anyPrevDB) {
            changeNotesBuilder.append(" inherited a previous damaging move's DB: "); // DB
            changeNotesBuilder.append(abilities.stream().map(Ability::getName)
                    .collect(Collectors.joining(", ", "", "\n")));
        }
        abilities.removeIf(abilityMovesPred);
        if (!abilities.isEmpty()) {
            changeNotesBuilder.append((abilities.size() > 1) ? "These abilities have " : "An ability has ");
            changeNotesBuilder.append("the following number of uses: "); // Freq
            changeNotesBuilder.append(abilities.stream().map(a -> a.getName() + " (" + a.getUses() + ")")
                    .collect(Collectors.joining(", ", "", "\n")));
        }
    }

    private void appendStabNotes(List<Move> normalDBMoves, List<Move> nonstandardDBMoves) {
        if (normalDBMoves.isEmpty() && nonstandardDBMoves.isEmpty())
            return;

        // Numeric STAB Moves
        if (!normalDBMoves.isEmpty()) {
            if (normalDBMoves.size() == 1)
                notesBuilder.append("STAB's +2 DB already applied to the same-typed damaging move ");
            else
                notesBuilder.append("STAB's +2 DB already applied to these same-typed damaging moves: ");

            notesBuilder.append(normalDBMoves.stream().map( // Ember (5 → 7)
                            m -> m.getName() + " (" + m.getDb() + " → " + (Integer.parseInt(m.getDb()) + PokeConstants.STAB) + ")")
                    .collect(Collectors.joining(", ", "", ".\n")));
        }

        // Non-Numerics
        if (!nonstandardDBMoves.isEmpty()) {
            if (nonstandardDBMoves.size() == 1)
                changeNotesBuilder.append("The following STAB move has a non-standard DB; STAB could not be applied automatically: ");
            else
                changeNotesBuilder.append("The following STAB moves have non-standard DBs; STAB could not be applied automatically: ");

            changeNotesBuilder.append(nonstandardDBMoves.stream().map(Move::getName).collect(Collectors.joining(", ",
                    "", ".\n")));
        }
    }

    private void appendUseNotes(List<Move> limitedUseMoves) {
        if (limitedUseMoves.isEmpty())
            return;

        if (limitedUseMoves.size() == 1)
            changeNotesBuilder.append("This limited-use move has the following number of uses: ");
        else
            changeNotesBuilder.append("These limited-use moves have the following number of uses: ");

        changeNotesBuilder.append(limitedUseMoves.stream().map(m -> m.getName() + " (" + m.getUses() + ")").collect(
                Collectors.joining(", ", "", ".\n")));
    }

    private void appendZeroDBNotes(List<Move> zeroDBMoves) {
        if (zeroDBMoves.isEmpty())
            return;

        if (zeroDBMoves.size() == 1)
            changeNotesBuilder.append("A move without a base DB has inherited a previous move's DB total when it should be 0: ");
        else
            changeNotesBuilder.append("Some moves without a base DB have inherited a previous move's DB total when it should be 0: ");

        changeNotesBuilder.append(zeroDBMoves.stream().map(Move::getName).collect(Collectors.joining(", ", "", ".\n")));

    }

    private void appendConnectionNotes(List<Move> connectionMoves) {
        if (connectionMoves.isEmpty())
            return;

        if (connectionMoves.size() == 1)
            changeNotesBuilder.append("A move has had any Connection ability effect text added to it's description: ");
        else
            changeNotesBuilder.append("Some moves have had their Connection abilities' effect text added to their descriptions: ");

        changeNotesBuilder.append(connectionMoves.stream().map(Move::getName).collect(Collectors.joining(", ", "", ".\n")));
    }

    private String getConnectionText(Move curr) {
        List<Ability> connections = new ArrayList<>(poke.getAbilities().stream().filter(a -> curr.equals(a.getConnection())).toList());
        // If Abilities are also placed in Moves, then there'll already be something in the Moves section to remind
        //  the user of the Connection ability, so no need to double up on it by adding it to Move effect text too.
        if(abilitiesAsMoves)
            connections.removeIf(abilityMovesPred.negate());

        return connections.stream().map(a -> {
                    StringBuilder ret = new StringBuilder();
                    String effect = a.getEffect().substring(a.getEffect().indexOf(". ") + 2);
                    String trigger = a.getTrigger();

                    ret.append(a.getName());
                    if (trigger != null && !trigger.isBlank() &&
                            !trigger.matches("^The user ((\\buses\\b)|(\\bhits\\b)).*(" + curr.getName() + ").?$")) {
                        ret.append("\n");
                        ret.append("Trigger: ");
                        ret.append(trigger);
                        ret.append("\n");
                        ret.append("Effect");
                    }
                    ret.append(": ");
                    ret.append(effect);
                    return ret.toString();
                }
        ).collect(Collectors.joining("\n\n", "", ""));
    }

    private void convertSkills() {
        // Skills
        // Choosing to read the following block of code is a mistake
        // This could be done with reflection or altering the SkillName enum to have consumers, but that's actually kinda worse
        Map<Skill.SkillName, Skill> skills = poke.getSpecies().getSkills().stream().collect(Collectors.toMap(
                Skill::getSkillName, s -> s));

        // Native Pokedex Skills
        if (skills.containsKey(Skill.SkillName.ACROBATICS)) {
            rollPoke.setAcrobatics(skills.get(Skill.SkillName.ACROBATICS).getRank());
            rollPoke.setAcrobaticsBonus(skills.get(Skill.SkillName.ACROBATICS).getBonus());
        }
        if (skills.containsKey(Skill.SkillName.ATHLETICS)) {
            rollPoke.setAthletics(skills.get(Skill.SkillName.ATHLETICS).getRank());
            rollPoke.setAthleticsBonus(skills.get(Skill.SkillName.ATHLETICS).getBonus());
        }
        if (skills.containsKey(Skill.SkillName.COMBAT)) {
            rollPoke.setCombat(skills.get(Skill.SkillName.COMBAT).getRank());
            rollPoke.setCombatBonus(skills.get(Skill.SkillName.COMBAT).getBonus());
        }
        if (skills.containsKey(Skill.SkillName.STEALTH)) {
            rollPoke.setStealth(skills.get(Skill.SkillName.STEALTH).getRank());
            rollPoke.setStealthBonus(skills.get(Skill.SkillName.STEALTH).getBonus());
        }
        if (skills.containsKey(Skill.SkillName.PERCEPTION)) {
            rollPoke.setPerception(skills.get(Skill.SkillName.PERCEPTION).getRank());
            rollPoke.setPerceptionBonus(skills.get(Skill.SkillName.PERCEPTION).getBonus());
        }
        if (skills.containsKey(Skill.SkillName.FOCUS)) {
            rollPoke.setFocus(skills.get(Skill.SkillName.FOCUS).getRank());
            rollPoke.setFocusBonus(skills.get(Skill.SkillName.FOCUS).getBonus());
        }
        if (skills.containsKey(Skill.SkillName.TECHNOLOGY_EDUCATION)) {
            rollPoke.setTechnologyEducation(skills.get(Skill.SkillName.TECHNOLOGY_EDUCATION).getRank());
            rollPoke.setTechnologyEducationBonus(skills.get(Skill.SkillName.TECHNOLOGY_EDUCATION).getBonus());
        }

        // Non-Native Skills
        if (skills.containsKey(Skill.SkillName.INTIMIDATE)) {
            rollPoke.setIntimidate(skills.get(Skill.SkillName.INTIMIDATE).getRank());
            rollPoke.setIntimidateBonus(skills.get(Skill.SkillName.INTIMIDATE).getBonus());
        }
        if (skills.containsKey(Skill.SkillName.SURVIVAL)) {
            rollPoke.setSurvival(skills.get(Skill.SkillName.SURVIVAL).getRank());
            rollPoke.setSurvivalBonus(skills.get(Skill.SkillName.SURVIVAL).getBonus());
        }

        if (skills.containsKey(Skill.SkillName.GENERAL_EDUCATION)) {
            rollPoke.setGeneralEducation(skills.get(Skill.SkillName.GENERAL_EDUCATION).getRank());
            rollPoke.setGeneralEducationBonus(skills.get(Skill.SkillName.GENERAL_EDUCATION).getBonus());
        }
        if (skills.containsKey(Skill.SkillName.MEDICINE_EDUCATION)) {
            rollPoke.setMedicineEducation(skills.get(Skill.SkillName.MEDICINE_EDUCATION).getRank());
            rollPoke.setMedicineEducationBonus(skills.get(Skill.SkillName.MEDICINE_EDUCATION).getBonus());
        }
        if (skills.containsKey(Skill.SkillName.OCCULT_EDUCATION)) {
            rollPoke.setOccultEducation(skills.get(Skill.SkillName.OCCULT_EDUCATION).getRank());
            rollPoke.setOccultEducationBonus(skills.get(Skill.SkillName.OCCULT_EDUCATION).getBonus());
        }
        if (skills.containsKey(Skill.SkillName.POKEMON_EDUCATION)) {
            rollPoke.setPokemonEducation(skills.get(Skill.SkillName.POKEMON_EDUCATION).getRank());
            rollPoke.setPokemonEducationBonus(skills.get(Skill.SkillName.POKEMON_EDUCATION).getBonus());
        }
        if (skills.containsKey(Skill.SkillName.GUILE)) {
            rollPoke.setGuile(skills.get(Skill.SkillName.GUILE).getRank());
            rollPoke.setGuileBonus(skills.get(Skill.SkillName.GUILE).getBonus());
        }

        if (skills.containsKey(Skill.SkillName.CHARM)) {
            rollPoke.setCharm(skills.get(Skill.SkillName.CHARM).getRank());
            rollPoke.setCharmBonus(skills.get(Skill.SkillName.CHARM).getBonus());
        }
        if (skills.containsKey(Skill.SkillName.COMMAND)) {
            rollPoke.setCommand(skills.get(Skill.SkillName.COMMAND).getRank());
            rollPoke.setCommandBonus(skills.get(Skill.SkillName.COMMAND).getBonus());
        }
        if (skills.containsKey(Skill.SkillName.INTUITION)) {
            rollPoke.setIntuition(skills.get(Skill.SkillName.INTUITION).getRank());
            rollPoke.setIntuitionBonus(skills.get(Skill.SkillName.INTUITION).getBonus());
        }
    }
}
