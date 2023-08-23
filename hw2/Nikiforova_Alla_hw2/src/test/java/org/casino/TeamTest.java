package org.casino;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;


class TeamTest {
    private RandomName randomName;
    private Team team;

    @BeforeEach
    public void setUp() {
        randomName = new RandomName();
        team = new Team("TestTeamName", randomName);
    }

    @Test
    public void testGetTeamName() {
        assertEquals("TestTeamName", team.getTeamName());
    }

    @Test
    public void testGetPlayers() {
        List<Player> players = team.getPlayers();
        assertEquals(3, players.size());
    }

    @Test
    public void testSetAndGetScore() {
        assertEquals(0, team.getScore());
        team.setScore(100);
        assertEquals(100, team.getScore());
    }

    @Test
    public void testThrowADice() {
        int score = team.throwADice();
        assertTrue(score >= 1 && score <= 6 * 6);
        assertEquals(score, team.getScore());
    }

    @Test
    public void testSetPrizeAndGetPrize() {
        team.setPrize(100);
        assertEquals(100f, team.getPrize());
    }

    @Test
    public void testSetStop() {
        team.setStop();
        assertEquals(true, team.isStopped());
        team.continueWork();
        assertEquals(false, team.isStopped());
    }
}