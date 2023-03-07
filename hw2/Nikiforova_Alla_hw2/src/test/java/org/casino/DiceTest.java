package org.casino;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DiceTest {
    @Test
    public void correctDice() {
        Dice dice = new Dice();

        for (int i = 0; i < 500; i++) {
            assertTrue(dice.throwADice() <= 6);
            assertTrue(dice.throwADice() > 0);
        }
    }

//    @Test
//    void getDice() {
//        Dice dice = new Dice();
//
//        for (int i = 0; i < 500; i++) {
//            assertTrue(dice.throwADice() <= 6);
//            assertTrue(dice.throwADice() > 0);
//        }
//    }
}
