package org.allenfulmer.ptuviewer.fileLoading.txt;

import lombok.extern.slf4j.Slf4j;
import org.allenfulmer.ptuviewer.fileLoading.JsonToPojoLoader;
import org.allenfulmer.ptuviewer.fileLoading.PojoToDBConverter;
import org.allenfulmer.ptuviewer.fileLoading.pojo.pokemon.*;
import org.allenfulmer.ptuviewer.models.PokeConstants;
import org.allenfulmer.ptuviewer.models.PokemonSpecies;
import org.thymeleaf.util.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class PlaintextPokeParser {

    private static final List<String> EVOLUTION_CRITERIA = Collections.unmodifiableList(Arrays.asList(
            "Minimum",
            "Learn",
            "Leaf Stone",
            "Thunder Stone",
            "Electrical Conditions"
    ));
    private static final String TUTOR_HEADER = "Tutor Move List";
    private static final String EGG_HEADER = "Egg Move List";
    private static final String TM_HM_HEADER = "TM/HM Move List";
    private static final String TM_HEADER = "TM Move List";
    private final Deque<String> data;
    private final Map<String, String> dexIds;
    private final Matcher genderChances = Pattern.compile("([\\d.]+)%").matcher("");
    private final Matcher capabilitiesMatcher = Pattern.compile("([A-Z][a-zA-Z ]+[a-z])(?: ([\\d/]+|\\([\\w, ]+\\)))?").matcher("");
    private final Matcher nameAndFormsMatcher = Pattern.compile("([A-Z-'.,: ]{3,})(?:$| ([A-Z\\d][\\w'%,. ]+))").matcher("");
    private final Matcher heightMatcher = Pattern.compile("(\\d+)’ (\\d+)” \\/ ([\\d.]+)m \\((\\w+)\\)").matcher("");
    private final Matcher weightMatcher = Pattern.compile("([\\d.]+) lbs \\/ ([\\d.]+)kg \\((\\d+)\\)").matcher("");
    private PokemonSpeciesPojo poke;

    public PlaintextPokeParser() {
        this.data = Arrays.stream(TxtUtils.readAllFiles(TxtUtils.POKEMON_DIRECTORY).split("\r\n")).collect(Collectors.toCollection(ArrayDeque::new));
        this.dexIds = generateDexMap(Arrays.stream(TxtUtils.readAllFiles(TxtUtils.DEX_ID_DIRECTORY).split("\r\n")).toList());
    }

    public static void main(String[] args) {
        Map<String, PokemonSpeciesPojo> sortedPokemon = new TreeMap<>(new PlaintextPokeParser().parsePokemon());
        Map<String, PokemonSpecies> plaintextPokemon = PojoToDBConverter.pokemonMapBuilder(sortedPokemon);
        Map<String, PokemonSpecies> pokePokemon = PojoToDBConverter.pokemonMapBuilder(JsonToPojoLoader.parsePojoPokemon());
        Map<String, PokemonSpecies> mergedPokemon = new TreeMap<>(pokePokemon);
        mergedPokemon.putAll(plaintextPokemon);
        mergedPokemon.forEach((k, v) -> log.info(k + " - " + v.toString()));
    }

    private Map<String, String> generateDexMap(List<String> combinedFiles) {
        Map<String, String> ret = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        combinedFiles.forEach(s -> {
            int spaceIndex = s.indexOf(" ");
            if (ret.put(s.substring(spaceIndex + 1).trim(), s.substring(0, spaceIndex).trim()) != null)
                throw new TxtUtils.InvalidInputException("Key collision when generating dex id map!");
        });
        return ret;
    }

    public Map<String, PokemonSpeciesPojo> parsePokemon() {
        Map<String, PokemonSpeciesPojo> pokeMap = new HashMap<>();
        while (data.peek() != null) {
            this.poke = new PokemonSpeciesPojo();

            setName();
            setStats();
            setTypes();
            setAbilities();
            setEvolutions();
            setSizeInfo();
            setBreedingInfo();
            setEnvironment();
            setCapabilities();
            setSkills();
            setLevelMoves();
            setTmHmMoves();
            setEggMoves();
            setTutorMoves();

            pokeMap.put(getDexId(), poke);
        }
        return pokeMap;
    }

    private String getDexId() {
        String pokeKey = poke.getSpecies();
        if (!poke.getForm().equalsIgnoreCase(PokeConstants.NON_REGIONAL_FORM))
            pokeKey += " " + poke.getForm();
        String id = dexIds.get(pokeKey);
        if (id == null)
            throw new TxtUtils.InvalidInputException("Pokemon " + poke.getSpecies() + " not in dexMap! Full name:" + pokeKey);
        return id;
    }

    private String combineUntil(String nextHeader) {
        return combineUntil(nextHeader, "");
    }

    private String combineUntil(String nextHeader, String alternateHeader) {
        StringBuilder ret = new StringBuilder();
        while (data.peek() != null && !data.peek().equalsIgnoreCase(nextHeader) &&
                !data.peek().equalsIgnoreCase(alternateHeader)) {
            ret.append(data.poll());
            ret.append(" ");
        }
        return ret.toString();
    }

    private List<String> collectUntil(String nextHeader) {
        return collectUntil(nextHeader, "", "");
    }

    private List<String> collectUntil(String primaryHeader, String secondaryHeader, String tertiaryHeader) {
        List<String> ret = new ArrayList<>();
        while (data.peek() != null && !data.peek().equalsIgnoreCase(primaryHeader) &&
                !data.peek().equalsIgnoreCase(secondaryHeader) &&
                !data.peek().equalsIgnoreCase(tertiaryHeader)) {
            ret.add(data.poll());
        }
        return ret;
    }

    private void setName() {
        nameAndFormsMatcher.reset(data.poll());
        if (!nameAndFormsMatcher.find())
            throw new TxtUtils.InvalidInputException("No name matches found in Poke name matcher!");

        poke.setSpecies(StringUtils.capitalizeWords(nameAndFormsMatcher.group(1).toLowerCase().trim()));
        String form = nameAndFormsMatcher.group(2);
        poke.setForm((form == null) ? PokeConstants.NON_REGIONAL_FORM : StringUtils.capitalizeWords(form.toLowerCase().trim()));
    }

    private void setStats() {
        BaseStats stats = new BaseStats();

        log.info(data.pop()); // Base Stats:
        log.info(data.pop()); // HP:
        stats.setHp(Integer.parseInt(Objects.requireNonNull(data.poll())));
        log.info(data.pop()); // Attack:
        stats.setAttack(Integer.parseInt(Objects.requireNonNull(data.poll())));
        log.info(data.pop()); // Defense:
        stats.setDefense(Integer.parseInt(Objects.requireNonNull(data.poll())));
        log.info(data.pop()); // Special Attack:
        stats.setSpecialAttack(Integer.parseInt(Objects.requireNonNull(data.poll())));
        log.info(data.pop()); // Special Defense:
        stats.setSpecialDefense(Integer.parseInt(Objects.requireNonNull(data.poll())));
        log.info(data.pop()); // Speed:
        stats.setSpeed(Integer.parseInt(Objects.requireNonNull(data.poll())));

        poke.setBaseStats(stats);
    }

    private void setTypes() {
        log.info(data.pop()); // Basic Information
        // Type: Bug / Psychic
        String[] types = Objects.requireNonNull(data.poll()).replace("Type: ", "").split(" / ");
        if (types.length < 2)
            poke.setTypes(Collections.singletonList(types[0]));
        else
            poke.setTypes(new ArrayList<>(Arrays.asList(types)));
    }

    private void setAbilities() {
        // Basic Ability 1: Overgrow
        poke.setAbilities(collectUntil("Evolution:").stream().map(curr -> {
            String name = curr.substring(curr.indexOf(":") + 2).trim();
            String type;
            if (curr.startsWith("Adv"))
                type = "Advanced";
            else
                type = curr.substring(0, curr.indexOf(" ")).trim();
            return new Ability(name, type);
        }).toList());
    }

    private void setEvolutions() {
        log.info(data.pop()); // Evolution:
        // 2 - Dartrix Minimum 15
        poke.setEvolutionStages(collectUntil("Size Information").stream().map(streamStr -> {
            int stage = Integer.parseInt(String.valueOf(streamStr.charAt(0)));
            String species;
            String criteria = "";

            // Find if there's a criteria; only a certain amount of them in PTU, so we can just check each directly
            int criteriaIndex = -1;
            for (String currCriteria : EVOLUTION_CRITERIA) {
                criteriaIndex = streamStr.indexOf(currCriteria);
                if (criteriaIndex != -1)
                    break;
            }

            if (criteriaIndex != -1) {
                species = streamStr.substring(streamStr.indexOf(" - ") + 3, criteriaIndex).trim();
                criteria = streamStr.substring(criteriaIndex).trim();
            } else
                species = streamStr.substring(streamStr.indexOf(" - ") + 3).trim();
            return new EvolutionStage(stage, species, criteria);
        }).toList());
    }

    private void setSizeInfo() {
        log.info(data.pop()); // Size Information
        heightMatcher.reset(data.poll()); // Height: 1’ 0” / 0.3m (Small)
        weightMatcher.reset(data.poll()); // Weight: 11 lbs / 5kg (1)
        if (!heightMatcher.find())
            throw new TxtUtils.InvalidInputException("No matches found for Height in poke height matcher!");
        poke.setHeight(new Height(
                new MetricHeightRange(
                        new MetricHeight(Double.parseDouble(heightMatcher.group(3))),
                        new MetricHeight(Double.parseDouble(heightMatcher.group(3)))
                ),
                new ImperialHeightRange(
                        new ImperialHeight(Long.parseLong(heightMatcher.group(1)), Long.parseLong(heightMatcher.group(2))),
                        new ImperialHeight(Long.parseLong(heightMatcher.group(1)), Long.parseLong(heightMatcher.group(2)))
                ),
                new Category(heightMatcher.group(4), heightMatcher.group(4))
        ));
        if (!weightMatcher.find())
            throw new TxtUtils.InvalidInputException("No matches found for Weight in poke weight matcher!");
        poke.setWeight(new Weight(
                new MetricWeightRange(
                        new MetricWeight(Double.parseDouble(weightMatcher.group(2))),
                        new MetricWeight(Double.parseDouble(weightMatcher.group(2)))
                ),
                new ImperialWeightRange(
                        new ImperialWeight(Double.parseDouble(weightMatcher.group(1))),
                        new ImperialWeight(Double.parseDouble(weightMatcher.group(1)))
                ),
                new WeightClass(Long.parseLong(weightMatcher.group(3)), Long.parseLong(weightMatcher.group(3)))
        ));
    }

    private void setBreedingInfo() {
        log.info(data.pop()); // Breeding Information
        BreedingData breedingData = new BreedingData();

        // Gender Ratio: 87.5% M / 12.5% F
        // Gender Ratio: No Gender
        String genderStr = Objects.requireNonNull(data.poll()).replace("Gender Ratio: ", "");
        if (genderStr.startsWith("No Gender") || genderStr.startsWith("Genderless")) {
            breedingData.setHasGender(false);
        } else {
            breedingData.setHasGender(true);
            genderChances.reset(genderStr);

            if (!genderChances.find())
                throw new TxtUtils.InvalidInputException("No matches found for Male chance in poke gender matcher!");
            breedingData.setMaleChance(Double.parseDouble(genderChances.group(1)) / 100);

            if (!genderChances.find())
                throw new TxtUtils.InvalidInputException("No matches found for Female chance in poke gender matcher!");
            breedingData.setFemaleChance(Double.parseDouble(genderChances.group(1)) / 100);
        }

        // Egg Group: Field / Plant
        String eggStr = Objects.requireNonNull(data.poll()).replace("Egg Group: ", "");
        if (eggStr.contains(" / "))
            breedingData.setEggGroups(new ArrayList<>(Collections.singletonList(eggStr)));
        else
            breedingData.setEggGroups(new ArrayList<>(Arrays.asList(eggStr.split(" / "))));

        // Average Hatch Rate: 10 Days
        if (data.peek() != null && data.peek().startsWith("Average Hatch Rate: "))
            breedingData.setHatchRate(Objects.requireNonNull(data.poll()).replace("Average Hatch Rate: ", ""));

        poke.setBreedingData(breedingData);
    }

    private void setEnvironment() {
        Environment enviro = new Environment();

        // Diet: Omnivore, Terravore
        String dietStr = Objects.requireNonNull(data.poll()).replace("Diet: ", "");
        if (dietStr.contains(", "))
            enviro.setDiet(new ArrayList<>(Collections.singletonList(dietStr)));
        else
            enviro.setDiet(new ArrayList<>(Arrays.asList(dietStr.split(", "))));

        // Habitat: Forest, Grassland, Urban
        String habitatStr = Objects.requireNonNull(data.poll()).replace("Habitat: ", "");
        if (habitatStr.contains(", "))
            enviro.setHabitats(new ArrayList<>(Collections.singletonList(habitatStr)));
        else
            enviro.setHabitats(new ArrayList<>(Arrays.asList(habitatStr.split(", "))));

        poke.setEnvironment(enviro);
    }

    private void setCapabilities() {
        log.info(data.pop()); // Capability List
        List<Capability> ret = new ArrayList<>();
        // Overland 2
        capabilitiesMatcher.reset(combineUntil("Skill List"));
        while (capabilitiesMatcher.find()) {
            Capability cap = new Capability();
            cap.setCapabilityName(capabilitiesMatcher.group(1));

            String val = capabilitiesMatcher.group(2);
            cap.setValue((val == null) ? "" : val);

            ret.add(cap);
        }
        poke.setCapabilities(ret);
    }

    private void setSkills() {
        log.info(data.pop()); // Skill List
        String skillStr = combineUntil("Move List");
        // Acro 2d6+1
        poke.setSkills(Arrays.stream(skillStr.split(", ")).map(String::trim).map(s -> {
            Skill skill = new Skill();
            int nameValSplit = s.indexOf(" ");
            skill.setSkillName(s.substring(0, nameValSplit).trim());
            skill.setDiceRank(s.substring(nameValSplit).trim());
            return skill;
        }).toList());
    }

    private void setLevelMoves() {
        log.info(data.pop()); // Move List
        log.info(data.pop()); // Level Up Move List
        List<LevelUpmove> levelMoves = new ArrayList<>();
        // 1 Tackle - Normal
        for (String curr : collectUntil(TM_HM_HEADER, TM_HEADER, TUTOR_HEADER)) {
            int levelIndex = curr.indexOf(" ");
            int typeIndex = curr.indexOf(" - ");
            String levelLearnedStr = curr.substring(0, levelIndex);

            int levelLearned = (levelLearnedStr.equalsIgnoreCase("Evo")) ?
                    PokeConstants.EVO_LEVEL_MOVE_VALUE : Integer.parseInt(levelLearnedStr);
            String moveName = curr.substring(levelIndex, typeIndex).trim();
            levelMoves.add(new LevelUpmove(moveName, levelLearned, null));
        }
        poke.setLevelUpMoves(levelMoves);
    }

    private void setTmHmMoves() {
        if (data.peek() == null || !(data.peek().equalsIgnoreCase(TM_HEADER) ||
                data.peek().equalsIgnoreCase(TM_HM_HEADER))) {
            poke.setTmHmMoves(Collections.emptyList());
            return;
        }
        log.info(data.pop()); // TM Move List
        String movesStr = combineUntil(EGG_HEADER, TUTOR_HEADER);
        // 06 Toxic
        poke.setTmHmMoves(Arrays.stream(movesStr.split(", ")).filter(s -> !s.isEmpty()).map(s -> {
            int idIndex = s.indexOf(" ");
            return new TmHmmove(s.substring(idIndex).trim(), null, s.substring(0, idIndex));
        }).toList());
    }

    private void setEggMoves() {
        if (data.peek() == null || !data.peek().equalsIgnoreCase(EGG_HEADER)) {
            poke.setEggMoves(Collections.emptyList());
            return;
        }
        log.info(data.pop()); // Egg Move List
        String movesStr = combineUntil(TUTOR_HEADER);
        // Baton Pass, Confuse Ray
        poke.setEggMoves(Arrays.stream(movesStr.split(", ")).filter(s -> !s.isEmpty()).map(s ->
                new Eggmove(s.trim(), null, null)
        ).toList());
    }

    private void setTutorMoves() {
        if (data.peek() == null || !data.peek().equalsIgnoreCase(TUTOR_HEADER)) {
            poke.setTutorMoves(Collections.emptyList());
            return;
        }
        log.info(data.pop()); // Tutor Move List
        List<String> moveStrings = collectUntil("Base Stats:");
        if (data.peek() != null) // If not null, we aren't EOF and must've grabbed a Pokemon name with the collect
        {
            data.push(moveStrings.remove(moveStrings.size() - 1));
        }

        // Convert the list into a single string and split it on commas
        String movesStr = String.join(" ", moveStrings);
        // Covet, Defog
        poke.setTutorMoves(Arrays.stream(movesStr.split(", ")).filter(s -> !s.isEmpty()).map(s -> {
            boolean natural = s.endsWith(" (N)");
            return new Tutormove(s.replace(" (N)", "").trim(), null, null, natural);
        }).toList());
    }
}
