package org.allenfulmer.ptuviewer.services;

import org.allenfulmer.ptuviewer.models.PokemonSpecies;
import org.allenfulmer.ptuviewer.models.Type;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class PokemonServiceTest {

    @Autowired
    PokemonSpeciesService pokemonService;

    @Test
    public void injectedComponentsAreNotNull() {
        Assert.assertNotNull(pokemonService);
    }

    @Test
    public void saveAndRetrieve() {
        PokemonSpecies p1 = new PokemonSpecies("2001", "TestMon1", "Test1");
        pokemonService.saveOrUpdate(p1);
        PokemonSpecies dbPoke = pokemonService.findByID(p1.getPokedexID());
        Assert.assertEquals(p1, dbPoke);
    }

    @Test
    public void findAll() {
        PokemonSpecies p2 = new PokemonSpecies("2002", "TestMon2", "Test2");
        PokemonSpecies p3 = new PokemonSpecies("2003", "TestMon3", "Test3");
        pokemonService.saveOrUpdate(p2);
        pokemonService.saveOrUpdate(p3);

        List<PokemonSpecies> pokes1 = pokemonService.getAllSpecies();
        Assert.assertTrue(pokes1.containsAll(Arrays.asList(p2, p3)));
    }

    @Test
    public void findWithExample() {
        PokemonSpecies p4 = new PokemonSpecies("2004", "TestMon4", "Test4");
        p4.setTypesFromList(Arrays.asList(Type.FIRE, Type.FIGHTING));
        p4.setEvolutions("1 - TestMon4");
        pokemonService.saveOrUpdate(p4);
        List<PokemonSpecies> pokes2 = pokemonService.findPokemonByExample(p4);
        Assert.assertTrue(pokes2.contains(p4));
    }
}
