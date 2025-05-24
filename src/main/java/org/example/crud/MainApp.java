package org.example.crud;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        // Carrega FXML do resources
        Parent root = FXMLLoader.load(
                MainApp.class.getResource("/org/example/crud/ui/MainView.fxml")
        );

        stage.setTitle("CRUD de Cursos e Alunos");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
