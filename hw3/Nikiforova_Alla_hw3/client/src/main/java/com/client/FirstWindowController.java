package com.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class FirstWindowController {
    @FXML
    private Button buttonAboutGame;

    @FXML
    private TextField textFieldPort;
    @FXML
    private TextField textFieldAddress;
    @FXML
    private TextField textFieldName;
    private Stage primaryStage;

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    public void setPromtName(String name) { textFieldName.setPromptText(name); }

    @FXML
    protected void showAlertAbout() {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("\uD83E\uDD7A");
        alert.setContentText("Автор программы - Аллочка Никифорова\nПроверяющий, не суди строго...");

        alert.getDialogPane().getButtonTypes().add(ButtonType.OK);
        alert.showAndWait();
    }

    protected void showAlertError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("\uD83D\uDE2D");
        alert.setHeaderText(null);
        alert.setContentText("К сожалению, пока что игра реализована только на одном сервере. \nВыберите адрес сервера = \"localhost\", port = 8080");
        alert.showAndWait();
    }

    @FXML
    protected void openSecondWindow() throws IOException {
        boolean flag = false;
        if (textFieldAddress.getText().equals("") && textFieldPort.getText().equals("8888") ||
                textFieldAddress.getText().equals("localhost") && textFieldPort.getText().equals("") ||
                textFieldAddress.getText().equals("localhost") && textFieldPort.getText().equals("8888") ||
                textFieldAddress.getText().equals("") && textFieldPort.getText().equals("")) {
            flag = true;
        } else {
            showAlertError();
            return;
        }

        try {
            ClientSocket clientSocket = new ClientSocket();

            String name = textFieldName.getText();
            if (name.isEmpty()) {
                name = textFieldName.getPromptText();
            }
            clientSocket.writeUTF(name);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("second-view.fxml"));
            VBox secondRoot = loader.load();
            SecondWindowController secondController = loader.getController();
            Stage secondStage = new Stage();
            secondController.startt(secondStage, clientSocket);

            System.out.println("Соединение установлено");

            secondController.setTextFieldTimerBeforeStart(clientSocket.readInt());

            Scene secondScene = new Scene(secondRoot, 600, 400);
            secondStage.setScene(secondScene);
            secondStage.show();
            primaryStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}