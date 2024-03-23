package javafx.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.model.dao.FuncionarioDAO;
import javafx.model.database.Database;
import javafx.model.database.DatabaseFactory;
import javafx.model.domain.Funcionario;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FXMLCadastroFuncionarioController implements Initializable {

    @FXML
    private TableView<Funcionario> tableViewFuncionario;
    @FXML
    private TableColumn<Funcionario, String> tableColumnNome;
    @FXML
    private TableColumn<Funcionario, String> tableColumnCPF;
    @FXML
    private Label labelCodigo;
    @FXML
    private Label labelNome;
    @FXML
    private Label labelCPF;
    @FXML
    private Label labelTelefone;
    @FXML
    private Label labelEmail;
    @FXML
    private Label labelCargo;
    @FXML
    private Label labelSalario;
    @FXML
    private Button buttonInserir;
    @FXML
    private Button buttonAlterar;
    @FXML
    private Button buttonRemover;
    
    private double xOffset = 0;
    private double yOffset = 0;
    

    private List<Funcionario> listFuncionario;
    private ObservableList<Funcionario> observableListFuncionario;

    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        funcionarioDAO.setConnection(connection);

        carregarTableViewFuncionarios();
        makeStageDraggable();
        // Limpando a exibição dos detalhes do cliente
        selecionarItemTableViewFuncionarios(null);

        // Listen acionado diante de quaisquer alterações na seleção de itens do TableView
        tableViewFuncionario.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewFuncionarios(newValue));
    }

    public void carregarTableViewFuncionarios() {
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnCPF.setCellValueFactory(new PropertyValueFactory<>("cpf"));

        listFuncionario = funcionarioDAO.listar();

        observableListFuncionario = FXCollections.observableArrayList(listFuncionario);
        tableViewFuncionario.setItems(observableListFuncionario);
    }

    public void selecionarItemTableViewFuncionarios(Funcionario funcionario) {
        if (funcionario != null) {
            labelCodigo.setText(String.valueOf(funcionario.getIdFunc()));
            labelNome.setText(funcionario.getNome());
            labelCPF.setText(funcionario.getCpf());
            labelTelefone.setText(funcionario.getTelefone());
            labelEmail.setText(funcionario.getEmail());
            labelCargo.setText(funcionario.getCargo());
            labelSalario.setText(String.valueOf(funcionario.getSalario()));
        } else {
            labelCodigo.setText("");
            labelNome.setText("");
            labelCPF.setText("");
            labelTelefone.setText("");
            labelEmail.setText("");
            labelCargo.setText("");
            labelSalario.setText("");
        }
    }

    @FXML
    public void handleButtonInserir() throws IOException {
        Funcionario funcionario = new Funcionario();
        boolean buttonConfirmarClicked = showFXMLCadastroFuncionarioDialog(funcionario);
        if (buttonConfirmarClicked) {
            funcionarioDAO.inserir(funcionario);
            carregarTableViewFuncionarios();
        }
    }

    public void handleButtonAlterar() throws IOException {
        Funcionario funcionario = tableViewFuncionario.getSelectionModel().getSelectedItem();
        if (funcionario != null) {
            boolean buttonConfirmarClicked = showFXMLCadastroFuncionarioDialog(funcionario);
            if (buttonConfirmarClicked) {
                funcionarioDAO.alterar(funcionario);
                carregarTableViewFuncionarios();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um funcionario na Tabela!");
            alert.show();
        }
    }

    @FXML
    public void handleButtonRemover() throws IOException {
        Funcionario funcionario = tableViewFuncionario.getSelectionModel().getSelectedItem();
        if (funcionario != null) {
            funcionarioDAO.remover(funcionario);
            carregarTableViewFuncionarios();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um funcionario na Tabela!");
            alert.show();
        }
    }

    public boolean showFXMLCadastroFuncionarioDialog(Funcionario funcionario) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLCadastroFuncionarioDialogController.class.getResource("/javafx/view/FXMLCadastroFuncionarioDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de Funcionarios");
         //dialogStage.initStyle(StageStyle.UNDECORATED);

        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        // Setando o cliente no Controller.
        FXMLCadastroFuncionarioDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setFuncionario(funcionario);

        // Mostra o Dialog e espera até que o usuário o feche
        dialogStage.showAndWait();

        return controller.isButtonConfirmarClicked();

    }
    
    private void makeStageDraggable() {
        tableViewFuncionario.setOnMousePressed((MouseEvent event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        tableViewFuncionario.setOnMouseDragged((MouseEvent event) -> {
            Stage stage = (Stage) tableViewFuncionario.getScene().getWindow();
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }
    
}
