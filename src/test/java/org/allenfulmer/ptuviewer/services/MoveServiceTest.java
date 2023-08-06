package org.allenfulmer.ptuviewer.services;

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

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class MoveServiceTest {

    @Autowired
    MoveService moveService;

    @Test
    public void injectedComponentsAreNotNull() {
        Assert.assertNotNull(moveService);
    }

    @Test
    // Save the move, check if it was saved, grab it and compare equality to the original
    public void saveExistsAndRetrieve() {
        Move m1 = new Move("Test1");
        moveService.saveOrUpdate(m1);
        Assert.assertTrue(moveService.doesMoveExist(m1.getName()));
        Move dbMove = moveService.findByName("Test1");
        Assert.assertEquals(m1, dbMove);
    }

    @Test
    public void saveAndDelete() {
        Move m2 = new Move("Test2");
        moveService.saveOrUpdate(m2);
        Assert.assertTrue(moveService.doesMoveExist(m2.getName()));
        moveService.deleteByName(m2.getName());
        Assert.assertFalse(moveService.doesMoveExist(m2.getName()));
    }

    @Test
    public void findAllAndSorted() {
        Move m3 = new Move("Test3");
        Move m4 = new Move("Test4");
        moveService.saveOrUpdate(m3);
        moveService.saveOrUpdate(m4);
        Assert.assertTrue(moveService.doesMoveExist(m3.getName()));
        Assert.assertTrue(moveService.doesMoveExist(m4.getName()));

        List<Move> moves1 = moveService.findAll();
        List<Move> moves2 = moveService.findAllSorted();
        Assert.assertTrue(moves1.containsAll(Arrays.asList(m3, m4)));
        Assert.assertTrue(moves2.containsAll(Arrays.asList(m3, m4)));
    }

    @Test
    public void findWithExample() {
        Move m5 = new Move("Test5", Type.FIRE, Frequency.SPECIAL, 2, "", "",
                Move.MoveClass.SPECIAL, "", "");
        moveService.saveOrUpdate(m5);
        Assert.assertTrue(moveService.doesMoveExist(m5.getName()));
        List<Move> moves3 = moveService.findMoveByExample(m5);
        Assert.assertTrue(moves3.contains(m5));
    }
}
