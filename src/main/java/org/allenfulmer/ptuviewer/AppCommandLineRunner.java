package org.allenfulmer.ptuviewer;

import lombok.extern.slf4j.Slf4j;
import org.allenfulmer.ptuviewer.fileLoading.JsonToPojoLoader;
import org.allenfulmer.ptuviewer.fileLoading.PojoToDBConverter;
import org.allenfulmer.ptuviewer.fileLoading.pojo.ability.AbilityPojo;
import org.allenfulmer.ptuviewer.fileLoading.pojo.move.MovePojo;
import org.allenfulmer.ptuviewer.models.*;
import org.allenfulmer.ptuviewer.repositories.AbilityRepository;
import org.allenfulmer.ptuviewer.repositories.CapabilityRepository;
import org.allenfulmer.ptuviewer.repositories.MoveRepository;
import org.allenfulmer.ptuviewer.repositories.PokemonSpeciesRepository;
import org.allenfulmer.ptuviewer.views.StartupWindow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
public class AppCommandLineRunner implements CommandLineRunner {
    CapabilityRepository capabilityRepo;
    AbilityRepository abilityRepo;
    MoveRepository moveRepo;
    PokemonSpeciesRepository pokemonRepo;

    @Autowired
    public AppCommandLineRunner(CapabilityRepository capabilityRepo, AbilityRepository abilityRepo,
                                MoveRepository moveRepo, PokemonSpeciesRepository pokemonRepo) {
        this.capabilityRepo = capabilityRepo;
        this.abilityRepo = abilityRepo;
        this.moveRepo = moveRepo;
        this.pokemonRepo = pokemonRepo;
    }

    @Override
    public void run(String... args) {
        insertCapabilities();
        insertMoves();
        insertAbilities();
        insertPokemon();
        log.info("---Done with all loading---");

//        printAll();
//        tempTestCode();

        new StartupWindow();
    }

    private void tempTestCode() {
        AtomicInteger len = new AtomicInteger(-1);
        AtomicInteger sum = new AtomicInteger();
        abilityRepo.findAll().forEach(a -> {
            if (a.getName() != null && a.getName().length() > len.get()) {
                len.set(a.getName().length());
            }
            sum.addAndGet(a.getName().length());
        });
        log.info("Max Ability length: " + len);
        log.info("Total Length:" + sum.get());
        log.info("Average Length: " + ((float) sum.get()) / abilityRepo.count());
    }

    private void printAll() {
        capabilityRepo.findAll().forEach(c -> log.info(c.toString()));
        moveRepo.findAll().forEach(m -> log.info(m.toString()));
        abilityRepo.findAll().forEach(a -> log.info(a.toString()));
        pokemonRepo.findAll().forEach(p -> log.info(p.toString()));
        log.info("---Done logging DB objects---");
    }

    private void insertCapabilities() {
        Map<String, Capability> capabilities = PojoToDBConverter.getConvertedCapabilities();
        capabilityRepo.saveAll(capabilities.values());
        log.info("Done With Capabilities");
    }

    private void insertPokemon() {
        Map<String, PokemonSpecies> pokes = PojoToDBConverter.getConvertedPokemonSpeciesMap();
        pokemonRepo.saveAll(pokes.values());
        log.info("Done With Pokemon");
    }

    private void insertAbilities() {
        Map<String, AbilityPojo> pojoAbility = JsonToPojoLoader.parsePojoAbilities();
        Map<String, Ability> abilities = PojoToDBConverter.abilityMapBuilder(pojoAbility);
        abilityRepo.saveAll(abilities.values());
        log.info("Done With Abilities");
    }

    private void insertMoves() {
        Map<String, MovePojo> pojoMoves = JsonToPojoLoader.parsePojoMoves();
        Map<String, Move> moves = PojoToDBConverter.moveMapBuilder(pojoMoves);
        // Protects against duplicate value Moves
        // Individual save duplicates turn into updates, duplicates in saveAll crash
        moves.values().forEach(moveRepo::save);
        log.info("Done with Moves");
    }

    private void insertTestData() {
        PokemonSpecies p1 = new PokemonSpecies("1001", "TestMonA", "Standard");
        PokemonSpecies p2 = new PokemonSpecies("1002", "TestMonB", "Alolan");
        PokemonSpecies p3 = new PokemonSpecies("1003", "TestMonC", "Galar");
        p1.setTypesFromList(Arrays.asList(Type.BUG, Type.DARK));
        p2.setTypesFromList(Arrays.asList(Type.ICE));
        p3.setTypesFromList(Arrays.asList(Type.FIRE, Type.FIGHTING));
        p1.setBaseStatsFromInts(1, 2, 3, 4, 5, 6);
        p2.setBaseStatsFromInts(5, 5, 5, 5, 5, 5);
        p3.setBaseStatsFromInts(3, 1, 4, 1, 5, 9);

        Ability a1 = new Ability("Eat", Frequency.STATIC, "Eats a delicious food buff.");
        Ability a2 = new Ability("Cook", Frequency.DAILY, "Creates some food.");
        Ability a3 = new Ability("Grow", Frequency.AT_WILL, "Grow some berries.");

        Move m1 = new Move("Buzz", Type.BUG, Frequency.EOT, Move.MoveClass.SPECIAL);
        Move m2 = new Move("Warm", Type.FIRE, Frequency.EOT, Move.MoveClass.STATUS);
        Move m3 = new Move("Punch", Type.FIGHTING, Frequency.AT_WILL, Move.MoveClass.PHYSICAL);
        Move m4 = new Move("Pollinate", Type.BUG, Frequency.AT_WILL, Move.MoveClass.PHYSICAL);
        Move m5 = new Move("Attach", Type.BUG, Frequency.AT_WILL, Move.MoveClass.STATUS);
        m1.setEffect("Buzz loudly to talk to other bugs.");
        m2.setEffect("Warm something up with some heat.");
        m3.setEffect("Punch something to cause it damage.");
        m4.setEffect("Pollinate a plant.");
        m5.setEffect("Attach onto an object. This may be done by grappling it with your limbs, stinging it and sticking" +
                " to it, shooting some webs at it, etc.");

        moveRepo.save(m1);
        moveRepo.save(m2);
        moveRepo.save(m3);
        moveRepo.save(m4);
        moveRepo.save(m5);

        a2.setConnection(m2);

        abilityRepo.save(a1);
        abilityRepo.save(a2);
        abilityRepo.save(a3);

        p1.addLevelMove(1, m1);
        p1.addLevelMove(12, m2);
        p1.addLevelMove(16, m1);
        p1.addLevelMove(28, m3);

        p2.addLevelMove(1, m3);
        p2.addLevelMove(1, m2);
        p2.addLevelMove(11, m3);
        p2.addLevelMove(21, m3);
        p2.addLevelMove(31, m3);
        p2.addLevelMove(41, m5);

        p3.addLevelMove(1, m1);
        p3.addLevelMove(1, m5);
        p3.addLevelMove(16, m4);

        p1.addBaseAbility(BaseAbility.AbilityType.BASIC, a1);
        p1.addBaseAbility(BaseAbility.AbilityType.ADVANCED, a2);
        p1.addBaseAbility(BaseAbility.AbilityType.HIGH, a3);

        p2.addBaseAbility(BaseAbility.AbilityType.BASIC, a1);
        p2.addBaseAbility(BaseAbility.AbilityType.BASIC, a1);
        p2.addBaseAbility(BaseAbility.AbilityType.ADVANCED, a3);
        p2.addBaseAbility(BaseAbility.AbilityType.HIGH, a2);

        p3.addBaseAbility(BaseAbility.AbilityType.BASIC, a3);

        pokemonRepo.save(p1);
        pokemonRepo.save(p2);
        pokemonRepo.save(p3);
    }
}
