package com.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class FirstWindowApplication extends Application {
    private Stage firstStage;
    private static final List<String> NAMES = List.of("Хомяк", "Капибара", "Котенок", "Рыба", "Желудь", "Холодец", "Оливье", "Шкаф", "Пес", "Жвачка");

    @Override
    public void start(Stage stage) throws IOException {
        this.firstStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(FirstWindowApplication.class.getResource("first-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 500);
        stage.setTitle("Вход в игру");
        stage.setScene(scene);
        stage.show();

        FirstWindowController controller = fxmlLoader.getController();
        controller.setPrimaryStage(stage);

        Random rand = new Random();
        controller.setPromtName(NAMES.get(rand.nextInt(NAMES.size())));
    }

    public static void main(String[] args) {
        launch();
    }
}