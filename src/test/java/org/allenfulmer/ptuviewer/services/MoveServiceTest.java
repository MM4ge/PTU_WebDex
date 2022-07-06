package org.allenfulmer.ptuviewer.services;

import org.allenfulmer.ptuviewer.models.Ability;
import org.allenfulmer.ptuviewer.models.Frequency;
import org.allenfulmer.ptuviewer.models.Move;
import org.allenfulmer.ptuviewer.models.Type;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class MoveServiceTest {

    @Autowired
    MoveService moveService;

    @Test
    public void saveAndRetrieve()
    {
        Move m1 = new Move("Buzz", Type.BUG, Frequency.EOT, Move.MoveClass.SPECIAL);
        moveService.saveOrUpdate(m1);
        Move dbMove = moveService.findByName("Buzz");
        Assert.assertEquals(m1, dbMove);
    }
}
