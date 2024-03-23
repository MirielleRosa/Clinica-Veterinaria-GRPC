package javafx.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.model.dao.VeterinarioDAO;
import javafx.model.database.Database;
import javafx.model.database.DatabaseFactory;
import javafx.model.domain.Veterinario;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FXMLCadastrarVeterinarioController implements Initializable {

    @FXML
    private TableView<Veterinario> tableViewVeterinario;
    @FXML
    private TableColumn<Veterinario, String> tableColumnVeterinarioNome;
    @FXML
    private TableColumn<Veterinario, String> tableColumnVeterinarioCrmv;
    @FXML
    private TableColumn<Veterinario, String> tableColumnVeterinarioEspecialidade;
    @FXML
    private Label labelVeterinarioCodigo;
    @FXML
    private Label labelVeterinarioNome;
    @FXML
    private Label labelVeterinarioCrmv;
    @FXML
    private Label labelVeterinarioTelefone;
    @FXML
    private Label labelVeterinarioCpf;
    @FXML
    private Label labelVeterinarioEmail;
    @FXML
    private Label labelVeterinarioEspecialidade;
    @FXML
    private Label labelVeterinarioDataNacimento;
    @FXML
    private Button buttonInserir;
    @FXML
    private Button buttonAlterar;
    @FXML
    private Button buttonRemover;

    private double xOffset = 0;
    private double yOffset = 0;

    private List<Veterinario> listVeterinario;
    private ObservableList<Veterinario> observableListVeterinario;

    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final VeterinarioDAO veterinarioDAO = new VeterinarioDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        veterinarioDAO.setConnection(connection);

        carregarTableViewVeterinarios();
        makeStageDraggable();
        // Limpando a exibição dos detalhes do cliente
        selecionarItemTableViewVeterinarios(null);

        // Listen acionado diante de quaisquer alterações na seleção de itens do TableView
        tableViewVeterinario.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewVeterinarios(newValue));
    }

    public void carregarTableViewVeterinarios() {
        tableColumnVeterinarioNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnVeterinarioCrmv.setCellValueFactory(new PropertyValueFactory<>("crmv"));
        tableColumnVeterinarioEspecialidade.setCellValueFactory(new PropertyValueFactory<>("especialidade"));

        listVeterinario = veterinarioDAO.listar();

        observableListVeterinario = FXCollections.observableArrayList(listVeterinario);
        tableViewVeterinario.setItems(observableListVeterinario);
    }

    public void selecionarItemTableViewVeterinarios(Veterinario veterinario) {
        if (veterinario != null) {
            labelVeterinarioCodigo.setText(String.valueOf(veterinario.getIdVet()));
            labelVeterinarioNome.setText(veterinario.getNome());
            labelVeterinarioCrmv.setText(veterinario.getCrmv());
            labelVeterinarioTelefone.setText(veterinario.getTelefone());
            labelVeterinarioCpf.setText(veterinario.getCpf());
            labelVeterinarioEmail.setText(veterinario.getEmail());
            labelVeterinarioEspecialidade.setText(veterinario.getEspecialidade());
            labelVeterinarioDataNacimento.setText(String.valueOf(veterinario.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        } else {
            labelVeterinarioCodigo.setText("");
            labelVeterinarioNome.setText("");
            labelVeterinarioCrmv.setText("");
            labelVeterinarioTelefone.setText("");
            labelVeterinarioCpf.setText("");
            labelVeterinarioEmail.setText("");
            labelVeterinarioEspecialidade.setText("");
            labelVeterinarioDataNacimento.setText("");
        }
    }

    @FXML
    public void handleButtonInserir() throws IOException {
        Veterinario veterinario = new Veterinario();
        boolean buttonConfirmarClicked = showFXMLCadastroVeterinarioDialog(veterinario);
        if (buttonConfirmarClicked) {
            veterinarioDAO.inserir(veterinario);
            carregarTableViewVeterinarios();
        }
    }

    @FXML
    public void handleButtonAlterar() throws IOException {
        Veterinario veterinario = tableViewVeterinario.getSelectionModel().getSelectedItem();
        if (veterinario != null) {
            boolean buttonConfirmarClicked = showFXMLCadastroVeterinarioDialog(veterinario);
            if (buttonConfirmarClicked) {
                veterinarioDAO.alterar(veterinario);
                carregarTableViewVeterinarios();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um cliente na Tabela!");
            alert.show();
        }
    }

    private boolean mostrarConfirmacaoRemocao() {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirmação de Remoção");
    alert.setHeaderText("Você tem certeza que deseja remover o veterinário?");
    alert.setContentText("Esta ação também removerá o veterinário da tabela login.");

    Optional<ButtonType> result = alert.showAndWait();
    return result.isPresent() && result.get() == ButtonType.OK;
}
    
    @FXML
    public void handleButtonRemover() throws IOException {
        Veterinario veterinario = tableViewVeterinario.getSelectionModel().getSelectedItem();
        if (veterinario != null) {
            boolean confirmacao = mostrarConfirmacaoRemocao();
            if (confirmacao) {
                // Remover da tabela login
                veterinarioDAO.removerLogin(veterinario);

                // Remover da tabela veterinario
                veterinarioDAO.remover(veterinario);

                carregarTableViewVeterinarios();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um cliente na Tabela!");
            alert.show();
        }
    }


    public boolean showFXMLCadastroVeterinarioDialog(Veterinario veterinario) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLCadastroVeterinarioDialogController.class.getResource("/javafx/view/FXMLCadastroVeterinarioDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de Veterinarios");

        //dialogStage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        // Setando o cliente no Controller.
        FXMLCadastroVeterinarioDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setVeterinario(veterinario);

        // Mostra o Dialog e espera até que o usuário o feche
        dialogStage.showAndWait();

        return controller.isButtonConfirmarClicked();

    }

    private void makeStageDraggable() {
        tableViewVeterinario.setOnMousePressed((MouseEvent event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        tableViewVeterinario.setOnMouseDragged((MouseEvent event) -> {
            Stage stage = (Stage) tableViewVeterinario.getScene().getWindow();
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }

}
