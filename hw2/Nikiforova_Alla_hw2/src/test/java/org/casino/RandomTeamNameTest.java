package org.casino;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class RandomTeamNameTest {
    @Test
    public void testGetFreeTeamName() {
        RandomTeamName randomTeamName = new RandomTeamName();
        int count = randomTeamName.getCountOfFreeTeamNames();
        String teamName = randomTeamName.getFreeTeamName();
        assertNotNull(teamName);
        assertTrue(teamName.length() > 0);
        assertEquals(count - 1, randomTeamName.getCountOfFreeTeamNames());
    }

    @Test
    public void testGetCountOfFreeTeamNames() {
        RandomTeamName randomTeamName = new RandomTeamName();
        int count = randomTeamName.getCountOfFreeTeamNames();

        for (int i = 0; i < count; i++) {
            String teamName = randomTeamName.getFreeTeamName();
        }

        assertEquals(0, randomTeamName.getCountOfFreeTeamNames());
    }
}
