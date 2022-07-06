package org.allenfulmer.ptuviewer.services;

import org.allenfulmer.ptuviewer.models.Ability;
import org.allenfulmer.ptuviewer.models.Frequency;
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
public class AbilityServiceTest {

    @Autowired AbilityService abilityService;

    @Test
    public void saveAndRetrieve()
    {
        Ability a1 = new Ability("Eat", Frequency.STATIC, "Eats a delicious food buff.");
        abilityService.saveOrUpdate(a1);
        Ability dbAbility = abilityService.findByName("Eat");
        Assert.assertEquals(a1, dbAbility);
    }
}
