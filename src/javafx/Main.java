package javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/FXMLLogin.fxml"));
        
        Scene scene = new Scene(root);
      
        stage.setScene(scene);
        stage.setTitle("Sistema de Vendas (JavaFX MVC)");
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED); //Deixa a barra superior da tela invisivel
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}