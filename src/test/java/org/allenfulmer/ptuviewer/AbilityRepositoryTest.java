package org.allenfulmer.ptuviewer;

import org.allenfulmer.ptuviewer.models.Ability;
import org.allenfulmer.ptuviewer.models.Frequency;
import org.allenfulmer.ptuviewer.models.Move;
import org.allenfulmer.ptuviewer.models.Type;
import org.allenfulmer.ptuviewer.repositories.AbilityRepository;
import org.allenfulmer.ptuviewer.services.AbilityService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith( SpringJUnit4ClassRunner.class )
@EnableJpaRepositories("org.allenfulmer.ptuviewer.repositories")
public class AbilityRepositoryTest {

    @Autowired AbilityRepository abilityRepo;
    static final Move MOVE_CONNECTION = new Move("Warm",Type.FIRE, Frequency.EOT, Move.MoveClass.STATUS);
    static final Ability ABILITY_CONNECTION = new Ability("Cook", Frequency.DAILY, "Creates some food.");

    @BeforeAll
    public void setup()
    {
//        insertTestData();

        Ability a1 = new Ability("Eat", Frequency.STATIC, "Eats a delicious food buff.");
        Ability a3 = new Ability("Grow", Frequency.AT_WILL, "Grow some berries.");
        ABILITY_CONNECTION.setConnection(MOVE_CONNECTION);

        abilityRepo.save(a1);
        abilityRepo.save(ABILITY_CONNECTION);
        abilityRepo.save(a3);
    }

    @Test
    public void injectedComponentsAreNotNull(){
        Assert.assertNotNull(abilityRepo);
    }

    @Test
    public void getByConnection()
    {
        Ability connection = abilityRepo.findByConnection(MOVE_CONNECTION).orElse(null);
        Assert.assertNotNull(connection);
        Assert.assertEquals(ABILITY_CONNECTION, connection);
    }
}
