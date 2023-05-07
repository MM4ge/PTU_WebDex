package org.allenfulmer.ptuviewer.jsonExport;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.allenfulmer.ptuviewer.fileLoading.PojoToDBConverter;
import org.allenfulmer.ptuviewer.generator.Pokemon;
import org.allenfulmer.ptuviewer.generator.models.Nature;
import org.allenfulmer.ptuviewer.jsonExport.exodus.PokemonExodus;
import org.allenfulmer.ptuviewer.jsonExport.exodus.StatExodus;
import org.allenfulmer.ptuviewer.jsonExport.roll20.Roll20Builder;
import org.allenfulmer.ptuviewer.models.PokemonSpecies;
import org.allenfulmer.ptuviewer.models.Stat;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

@Slf4j
public class ExodusConverter {

    private static final Gson gson = new GsonBuilder().serializeNulls().disableHtmlEscaping().setLenient().create();

    private ExodusConverter(){}

    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        log.info("Input the exodusJSON (un-beautified - as one line), then hit Enter:");
        String exodusJSON = input.nextLine();
        Pokemon p1 = convertFromExodus(exodusJSON);
        log.info(gson.toJson(new Roll20Builder(p1).build()));
    }

    public static Pokemon convertFromExodus(String exodusJSON)
    {
        PokemonExodus e1 = gson.fromJson(exodusJSON, PokemonExodus.class);
        Pokemon p1 = new Pokemon();

        //TODO: Catch for meowstic (and nidorans?) one has the bonus on name, the other on form

        Map<String, PokemonSpecies> allPokes = PojoToDBConverter.getConvertedPokemonSpeciesMap();
        List<PokemonSpecies> speciesMatches = allPokes.values().stream()
                .filter(p -> p.getSpeciesName().equalsIgnoreCase(e1.getPokedexEntry().getSpecies()))
                .filter(p -> e1.getPokedexEntry().getForm().toUpperCase().startsWith(p.getForm().toUpperCase())).toList();

        if(speciesMatches.isEmpty())
            throw new RuntimeException("Exodus Pokemon matched no species!");
        else if (speciesMatches.size() > 1)
            throw new RuntimeException("Exodus Pokemon matched more than one DB Species!");
        p1.setSpecies(speciesMatches.get(0));

        Map<Stat.StatName, StatExodus> exodusStats = new EnumMap<>(Stat.StatName.class);
        exodusStats.put(Stat.StatName.HP, e1.getHp());
        exodusStats.put(Stat.StatName.ATTACK, e1.getAtk());
        exodusStats.put(Stat.StatName.DEFENSE, e1.getDef());
        exodusStats.put(Stat.StatName.SPECIAL_ATTACK, e1.getSpatk());
        exodusStats.put(Stat.StatName.SPECIAL_DEFENSE, e1.getSpdef());
        exodusStats.put(Stat.StatName.SPEED, e1.getSpd());
        p1.setStats(exodusStats.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey,
                e -> new Stat(e.getKey(), e.getValue().getBase(), 0, e.getValue().getLvlUp(), e.getValue().getAdd())
        )));

        p1.setNatureAndStats(Nature.getNature(e1.getNature()));
        p1.setGender(e1.getGender());
        p1.setLevel(e1.getLevel());
        p1.setMoves(e1.getMoves().stream().map(m -> PojoToDBConverter.getMove(m.getName())).toList());
        p1.setAbilities(e1.getAbilities().stream().map(a -> PojoToDBConverter.getAbility(a.getName())).collect(Collectors.toSet()));

        return p1;
    }
}
