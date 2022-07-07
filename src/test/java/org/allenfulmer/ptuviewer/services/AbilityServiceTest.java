package org.allenfulmer.ptuviewer.services;

import org.allenfulmer.ptuviewer.models.Ability;
import org.allenfulmer.ptuviewer.models.ActionType;
import org.allenfulmer.ptuviewer.models.Frequency;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class AbilityServiceTest {

    @Autowired
    AbilityService abilityService;

    @Test
    public void injectedComponentsAreNotNull() {
        Assert.assertNotNull(abilityService);
    }

    @Test
    public void saveAndRetrieve() {
        Ability a1 = new Ability("Test1");
        abilityService.saveOrUpdate(a1);
        Ability dbAbility = abilityService.findByName(a1.getName());
        Assert.assertEquals(a1, dbAbility);
    }

    @Test
    public void findWithExample() {
        Ability a2 = new Ability("Test2", Frequency.SPECIAL, 2, ActionType.FREE_ACTION,
                ActionType.Priority.INTERRUPT, "", "", "", null);
        abilityService.saveOrUpdate(a2);
        List<Ability> abilities1 = abilityService.findAbilityByExample(a2);
        Assert.assertTrue(abilities1.contains(a2));
    }
}
