package org.casino;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class RandomNameTest {
    @Test
    public void testGetFreeName() {
        RandomName randomName = new RandomName();
        int count = randomName.getCountOfFreeNames();
        String name = randomName.getFreeName();
        assertNotNull(name);
        assertTrue(name.length() > 0);
        name = randomName.getFreeName();
        assertEquals(count - 2, randomName.getCountOfFreeNames());
    }
}
