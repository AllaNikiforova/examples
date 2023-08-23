package com.client;

/**
 * Класс для игрока на стороне клиента
 */
public class Player {
    private int countCharacters; // количество набранных символов
    private int countErrors; // количество ошибок
    private boolean isOver;

    public Player() {
        this.countCharacters = 0;
        this.countErrors = 0;
        this.isOver = false;
    }
    public int getCharactersTyped() {
        return countCharacters;
    }

    public void increaseCharactersTyped(int charactersTyped) {
        this.countCharacters += charactersTyped;
    }

    public int getErrorsMade() {
        return countErrors;
    }

    public void increaseErrorsMaid(int countErrors) { this.countErrors += countErrors; }

    public boolean isOver() {
        return isOver;
    }

    public void setOver(boolean over) {
        isOver = over;
    }
}

