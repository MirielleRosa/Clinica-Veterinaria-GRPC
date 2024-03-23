package javafx.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.model.dao.LoginDAO;
import javafx.model.database.Database;
import javafx.model.database.DatabaseFactory;
import javafx.model.domain.Login;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.input.MouseEvent;

public class FXMLLoginController implements Initializable {

    @FXML
    private ImageView imageViewLogin01;
    @FXML
    private ImageView imageViewLogin02;
    @FXML
    private ImageView imageViewLogin03;
    @FXML
    private ImageView imageViewLogin04;
    @FXML
    private AnchorPane anchorPane02Login;
    @FXML
    private Button buttonLogin;
    @FXML
    private TextField textFieldUsuário;
    @FXML
    private Label btnLabelForgetPswd;
    @FXML
    private PasswordField passwordFieldPw;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button buttonClose;

    private double xOffset = 0;
    private double yOffset = 0;

    LoginDAO loginDAO = new LoginDAO();
     private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        makeStageDraggable();
        loginDAO.setConnection(connection);
        loginDAO.listar();
    }

    private void makeStageDraggable() {
        anchorPane.setOnMousePressed((MouseEvent event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        anchorPane.setOnMouseDragged((MouseEvent event) -> {
            Stage stage = (Stage) anchorPane.getScene().getWindow();
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }

    @FXML
    private void clicarBotaoLogin() throws IOException {
        String usuario = textFieldUsuário.getText();
        String senha = passwordFieldPw.getText();

        // Assuming you have a method to check if the email and password are valid
        Login login = isValidLogin(usuario, senha);

        System.out.println(login); // Adicione esta linha para verificar se login é null ou não

        if (login != null) {
            String cargo = login.getCargo(); // Obter o cargo do usuário

            if ("funcionario".equals(cargo)) {
                openDefaultMenu();
            } else {
                // Handle other roles or unknown roles
                System.out.println("Unsupported role: " + cargo);
            }
        } else {
            showLoginErrorAlert();
        }
    }

    private Login isValidLogin(String usuario, String senha) {
        return loginDAO.buscarPorEmailESenha(usuario, senha);
    }


    private String getRoleForUser(String usuario) {
        // Convertendo para minúsculas para tornar a comparação insensível a maiúsculas e minúsculas
        String lowerCaseUsuario = usuario.toLowerCase();

        // Perform logic to get the user's role from your data source (e.g., database)
        // Replace this with your actual logic
        if ("admin".equals(lowerCaseUsuario)) {
            return "admin";
        } else if ("veterinario".equals(lowerCaseUsuario)) {
            return "veterinario";
        } else {
            return "funcionario";
        }
    }

        
    private void openVeterinarioMenu() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafx/view/FXMLMenuVeterinario.fxml"));
        anchorPane.getChildren().setAll(a);
    }

    private void openDefaultMenu() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafx/view/FXMLMenu.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    
    private void showLoginErrorAlert() {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Erro de login");
    alert.setHeaderText(null);
    alert.setContentText("Usuário ou senha inválidos. Tente novamente");
    alert.showAndWait();
}


    @FXML
    public void handleMenu() throws IOException {
        String usuario = textFieldUsuário.getText();
        String senha = passwordFieldPw.getText();

        Login login = isValidLogin(usuario, senha);

        if (login != null) {
            String cargo = login.getCargo();

            if ("Funcionario".equals(cargo)) {
                openDefaultMenu();
            } else if ("Veterinario".equals(cargo)) {
                // Abrir um menu específico para o admin, se necessário
                openVeterinarioMenu();
            } else if ("admin".equals(cargo)) {
                // Abrir um menu específico para o admin, se necessário
                openAdminMenu();
            } else {
                // Tratar outros cargos, se necessário
                System.out.println("Unsupported role: " + cargo);
            }
        } else {
            // Credenciais inválidas
            showLoginErrorAlert();
        }
    }

    private void openAdminMenu() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafx/view/FXMLMenu.fxml"));
        anchorPane.getChildren().setAll(a);
    }


    @FXML
    public void buttonFecharPrograma() {
        Stage stage = (Stage) buttonClose.getScene().getWindow();
        stage.close();
        Platform.exit();
    }
}