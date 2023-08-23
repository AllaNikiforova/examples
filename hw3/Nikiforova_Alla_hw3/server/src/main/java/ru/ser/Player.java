package ru.ser;

/**
 * Класс для игрока. Используется во время подсчетов результатов
 */
public class Player implements Comparable<Player> {
    private String playerName;
    private int countCharacters;
    private int process;
    private int countErrors;
    private boolean errorFlag;
    private int finishTime;
    private int typingTime;
    private ClientSocket clientSocket;

    public Player(ClientSocket clientSocket) {
        this.countCharacters = 0;
        this.countErrors = 0;
        this.errorFlag = false;
        this.clientSocket = clientSocket;
        this.process = 0;
        this.finishTime = 0;
        this.typingTime = 0;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getCharactersTyped() {
        return countCharacters;
    }

    public void setCharactersTyped(int charactersTyped) {
        this.countCharacters = charactersTyped;
    }
    public int getErrorsMade() {
        return countErrors;
    }

    public void setErrorsMade(int countErrors) {
        this.countErrors = countErrors;
    }

    public boolean isErrorFlag() {
        return errorFlag;
    }

    public void setErrorFlag(boolean errorFlag) {
        this.errorFlag = errorFlag;
    }

    public int getProcess() {
        return process;
    }

    public void setProcess(int process) {
        this.process = process;
    }

    @Override
    public int compareTo(Player otherPlayer) {
        if (this.getCharactersTyped() != otherPlayer.getCharactersTyped()) {
            return otherPlayer.getCharactersTyped() - this.getCharactersTyped();
        }

        if (this.getErrorsMade() != otherPlayer.getErrorsMade()) {
            return this.getErrorsMade() - otherPlayer.getErrorsMade();
        }

        if (this.getTypingTime() != otherPlayer.getTypingTime()) {
            return Long.compare(this.getTypingTime(), otherPlayer.getTypingTime());
        }

        return 0;
    }

    public int getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }

    public int getTypingTime() {
        return typingTime;
    }

    public void setTypingTime(int time) {
        this.typingTime = time;
    }
}

