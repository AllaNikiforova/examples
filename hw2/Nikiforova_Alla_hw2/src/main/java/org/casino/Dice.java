package org.casino;

/**
 * Класс для кубика
 */
public class Dice {
    /**
     * Бросаем кубик
     * @return значение от 1 до 6, которое выпало на кости.
     */
    public static int throwADice() { return 1 + (int) (Math.random() * 6);
    }
}
