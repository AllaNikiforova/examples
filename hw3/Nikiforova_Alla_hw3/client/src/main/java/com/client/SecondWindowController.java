package com.client;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SecondWindowController {
    private int timeSecondsBeforeStart;

    private int timeSecondsAfterStart; // TIMESECONDSAFTERSTART

    private List<String> textList;
    private String text;

    private ClientSocket clientSocket;
    private Player player;
    private Stage secondStage;
    private int iForText = 0;

    @FXML
    private TextArea textAreaText;

    @FXML
    private TextArea textAreaTimer;

    @FXML
    private TextArea textAreaPlayers;

    @FXML
    private TextField textFieldInput;

    @FXML
    private TextFlow textFlowInput;


    public void startt(Stage stage, ClientSocket clientSocket) {
        player = new Player();
        this.secondStage = stage;
        this.clientSocket = clientSocket;
        secondStage.setOnCloseRequest((WindowEvent event) -> {
            // Вызываем метод в контроллере для обработки закрытия окна
            try {
                this.handleClose();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        ChangeListener<String> inputChangeListener = (observable, oldValue, newValue) -> {
            if (iForText < textList.size()) {
                player.increaseErrorsMaid(countErrors(newValue, textList.get(iForText), oldValue));
                System.out.println("countErrors(newValue, textList.get(iForText), oldValue): " + countErrors(newValue, textList.get(iForText), oldValue));
                System.out.println("all errors = " + player.getErrorsMade());

                if (countErrors(newValue, textList.get(iForText)) > 0) {
                    Platform.runLater(() -> {
                        textFieldInput.setStyle("-fx-border-color: red;");
                    });
                } else {
                    Platform.runLater(() -> {
                        textFieldInput.setStyle("-fx-border-color: green;");
                    });
                }
            }
            if (newValue.length() > 0 && newValue.substring(newValue.length() - 1).equals(" ")) {
                System.out.println(newValue + " " + textList.get(iForText) + "\n");

                String word = textList.get(iForText);
                if (word.equals(newValue.substring(0, newValue.length() - 1))
                    || (word.substring(0, word.length() - 1).equals(newValue.substring(0, newValue.length() - 1)) && word.substring(newValue.length() - 1).equals("\n"))) {
                    Platform.runLater(() -> {
                        textFieldInput.clear();
                    });
                    iForText++;
                    System.out.println(iForText);

                    // что-то про добавление

                    player.increaseCharactersTyped(word.length());
                    System.out.println("Количество введенных символов: " + player.getCharactersTyped());
                }

                if (iForText == textList.size()) {
                    System.out.println(iForText + " " + textList.size());
                    System.out.println("player.setOver(true)");
                    player.setOver(true);
                    Platform.runLater(() -> {
                        textFieldInput.clear();
                        textFieldInput.setStyle("");
                    });
                    textFieldInput.setEditable(false);
                }
            }
        };

        textFieldInput.textProperty().addListener(inputChangeListener);
    }

    @FXML
    private void handleClose() throws IOException {
        clientSocket.close();
        secondStage.close();
        System.exit(0);
    }

    public void setTextFieldTimerBeforeStart(int timeSecondsBeforeStart) {
        this.timeSecondsBeforeStart = timeSecondsBeforeStart;

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            int tBeforeStart = timeSecondsBeforeStart;
            int tAfterStart;

            boolean flagFinish = false;

            @Override
            public void run() {
                if (clientSocket.isClosed()) {
                    timer.cancel();
                }

                if (tBeforeStart > 0) {
                    tBeforeStart--;
                    textAreaTimer.setText("До начала игры " + tBeforeStart + " секунд");

                    try {
                        textAreaPlayers.setText(clientSocket.readUTF());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    if (tBeforeStart == 5) {
                        try {
                            textAreaText.setText(clientSocket.readUTF());
                            tAfterStart = clientSocket.readInt();
                            textList = List.of(textAreaText.getText().split(" "));
                            text = textAreaText.getText();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                } else if (tAfterStart > 0) {
                    if (tBeforeStart == 0) {
                        tBeforeStart--;
                        textFieldInput.setEditable(true);
                    }

                    if (player.isOver()) {
                        textAreaTimer.setText("Вы достигли финала!\n\uD83C\uDFC6\uD83C\uDF8A\uD83E\uDD73");
                        flagFinish = true;
                        textFieldInput.setEditable(false);
                    } else {
                        textAreaTimer.setText("До конца игры " + tAfterStart + " секунд");
                    }

                    try {
                        clientSocket.writeInt(player.getCharactersTyped());
                        clientSocket.writeInt(player.getErrorsMade());
                        textAreaPlayers.setText(clientSocket.readUTF());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    tAfterStart--;
                } else {
                    if (tAfterStart <= 0) {
                        textAreaTimer.setText("Время вышло");
                        textFieldInput.setEditable(false);
                        timer.cancel();
                        textFieldInput.clear();
                        textFieldInput.setStyle("");
                    }

                    try {
                        textAreaPlayers.setText(clientSocket.readUTF());
                    } catch (IOException e) {
                        timer.cancel();
                    }
                }
            }
        };

        timer.scheduleAtFixedRate(task, 0, 1000);
    }
    public static int countErrors(String newValue, String str, String oldValue) {
        if (newValue.length() > oldValue.length()) {
            return countErrors(newValue, str) - countErrors(oldValue, str);
        }
        return 0;
    }

    public static int countErrors(String newValue, String str) {
        int errorCount = 0;
        int currentIndex = 0;

        for (char inputChar : newValue.toCharArray()) {
            if (currentIndex < str.length() && inputChar != str.charAt(currentIndex)) {
                errorCount++;
                currentIndex++;
            } else if (currentIndex < str.length()) {
                currentIndex++;
            } else {
                errorCount++;
            }
        }

        if (str.contains("\n") && errorCount > 0
                && newValue.endsWith(" ")
                && str.substring(0, str.length() - 1).equals(newValue.substring(0, newValue.length() - 1))) {
            errorCount--;
        }

        if (errorCount > 0 && (newValue.length() - str.length() == 1) && newValue.endsWith(" ")) {
            errorCount--;
        }
        return errorCount;
    }
}