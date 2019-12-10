package com.trilogyed.repository;

import com.trilogyed.model.LevelUp;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LevelUpRepoTest {

    @Autowired
    LevelUpRepo repo;

    @Before
    public void setUp() {
        repo.deleteAll();
    }

    @Test
    public void shouldGetLevelUpByCustomerId() {
        LevelUp levelUp1 = repo.save(new LevelUp(1, 50, LocalDate.of(2017, 9, 25)));
        LevelUp levelUp2 = repo.save(new LevelUp(2, 150, LocalDate.of(2016, 9, 20)));
        LevelUp levelUp3 = repo.save(new LevelUp(3, 35, LocalDate.of(2018, 3, 10)));

        assertEquals(levelUp1, repo.findByCustomerId(1));
        assertEquals(levelUp2, repo.findByCustomerId(2));
        assertEquals(levelUp3, repo.findByCustomerId(3));
    }
}