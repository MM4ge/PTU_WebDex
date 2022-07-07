package org.allenfulmer.ptuviewer.repositories;

import org.allenfulmer.ptuviewer.models.Ability;
import org.allenfulmer.ptuviewer.models.Frequency;
import org.allenfulmer.ptuviewer.models.Move;
import org.allenfulmer.ptuviewer.models.Type;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class AbilityRepositoryTest {
    @Autowired
    AbilityRepository abilityRepo;
    @Autowired
    MoveRepository moveRepository;

    @Test
    public void injectedComponentsAreNotNull() {
        Assert.assertNotNull(abilityRepo);
    }

    @Test
    public void getByConnection() {
        Ability a1 = new Ability("Test1", Frequency.SPECIAL, "Test");
        Move m1 = new Move("Test2", Type.FIRE, Frequency.AT_WILL, Move.MoveClass.SPECIAL);
        a1.setConnection(m1);
        moveRepository.save(m1);
        abilityRepo.save(a1);

        Ability connection = abilityRepo.findByConnection(m1).orElse(null);
        Assert.assertNotNull(connection);
        Assert.assertEquals(a1, connection);
    }
}
