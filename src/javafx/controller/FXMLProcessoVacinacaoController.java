/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.model.dao.ClienteDAO;
import javafx.model.dao.VacinaDAO;
import javafx.model.dao.VacinacaoDAO;
import javafx.model.dao.VeterinarioDAO;
import javafx.model.database.Database;
import javafx.model.database.DatabaseFactory;
import javafx.model.domain.Animal;
import javafx.model.domain.Cliente;
import javafx.model.domain.Vacina;
import javafx.model.domain.Vacinacao;
import javafx.model.domain.Veterinario;
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

public class FXMLProcessoVacinacaoController implements Initializable {

    @FXML
    private TableView<Vacinacao> tableViewVacinacao;
    @FXML
    private TableColumn<Vacinacao, Integer> tableColumnId;
    @FXML
    private TableColumn<Vacinacao, Cliente> tableColumnDono;
    @FXML
    private TableColumn<Vacinacao, Animal> tableColumnAnimal;
    @FXML
    private TableColumn<Vacinacao, Vacina> tableColumnVacina;
    @FXML
    private Label labelCodVacinacao;
    @FXML
    private Label labelAnimal;
    @FXML
    private Label labelDono;
    @FXML
    private Label labelVeterinario;
    @FXML
    private Label labelVacina;
    @FXML
    private Label labelDataAplicacao;
    @FXML
    private Label labelCodAnimal;
    @FXML
    private Button buttonConfirmar;
    @FXML
    private Button buttonRemover;

    @FXML
    private Button buttonAlterar;

    private double xOffset = 0;
    private double yOffset = 0;

    private ObservableList<Vacinacao> observableListVacinacao;
    private List<Vacinacao> listVacinacao;

    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;

    private Animal animal;
    private Cliente cliente;
    private Veterinario veterinario;
    private Vacina vacina;

    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final VacinaDAO vacinaDAO = new VacinaDAO();
    private final VeterinarioDAO veterinarioDAO = new VeterinarioDAO();
    private final VacinacaoDAO vacinacaoDAO = new VacinacaoDAO();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        clienteDAO.setConnection(connection);
        vacinaDAO.setConnection(connection);
        veterinarioDAO.setConnection(connection);
        vacinacaoDAO.setConnection(connection);
        makeStageDraggable();
        carregarTableViewVacinacao();
        
        // Limpando a exibição dos detalhes do cliente
        selecionarItemTableViewVacinacao(null);

        // Listen acionado diante de quaisquer alterações na seleção de itens do TableView
        tableViewVacinacao.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewVacinacao(newValue));

    }

    public void carregarTableViewVacinacao() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idVacinacao"));
        tableColumnDono.setCellValueFactory(new PropertyValueFactory<>("dono"));
        tableColumnAnimal.setCellValueFactory(new PropertyValueFactory<>("animal"));
        tableColumnVacina.setCellValueFactory(new PropertyValueFactory<>("vacina"));

        listVacinacao = vacinacaoDAO.listar();

        observableListVacinacao = FXCollections.observableArrayList(listVacinacao);
        tableViewVacinacao.setItems(observableListVacinacao);

    }

    public void selecionarItemTableViewVacinacao(Vacinacao vacinacao) {
        if (vacinacao != null) {
            labelCodVacinacao.setText(String.valueOf(vacinacao.getIdVacinacao()));
            labelAnimal.setText(String.valueOf(vacinacao.getAnimal().getNome()));
            labelDono.setText(String.valueOf(vacinacao.getDono().getNome()));
            labelVeterinario.setText(vacinacao.getVeterinario().getNome());
            labelVacina.setText(String.valueOf(vacinacao.getVacina().getTipo()));
            labelDataAplicacao.setText(String.valueOf(vacinacao.getDataAplicacao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
            labelCodAnimal.setText(String.valueOf(vacinacao.getAnimal().getIdAnimal()));
        } else {
            labelCodVacinacao.setText("");
            labelAnimal.setText("");
            labelDono.setText("");
            labelVeterinario.setText("");
            labelVacina.setText("");
            labelDataAplicacao.setText("");
            labelCodAnimal.setText("");
        }
    }

    @FXML
    public void handleButtonInserir() throws IOException {
        Vacinacao vacinacao = new Vacinacao();
        boolean buttonConfirmarClicked = showFXMLCadastroVacinacaoDialog(vacinacao);
        if (buttonConfirmarClicked) {
            vacinacaoDAO.inserir(vacinacao);
            carregarTableViewVacinacao();
        }
    }

    @FXML
    public void handleButtonAlterar() throws IOException {
        Vacinacao vacinacao = tableViewVacinacao.getSelectionModel().getSelectedItem();
        if (vacinacao != null) {
            boolean buttonConfirmarClicked = showFXMLCadastroVacinacaoDialogAlterar(vacinacao);
            if (buttonConfirmarClicked) {
                vacinacaoDAO.alterar(vacinacao);
                carregarTableViewVacinacao();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um agendamento na Tabela!");
            alert.show();
        }
    }

    @FXML
    public void handleButtonRemover() throws IOException {
        Vacinacao vacinacaol = tableViewVacinacao.getSelectionModel().getSelectedItem();
        if (vacinacaol != null) {
            vacinacaoDAO.remover(vacinacaol);
            carregarTableViewVacinacao();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um processo na Tabela!");
            alert.show();
        }
    }

    public boolean showFXMLCadastroVacinacaoDialog(Vacinacao vacinacao) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLProcessoVacinacaoDialogController.class.getResource("/javafx/view/FXMLProcessoVacinacaoDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de Vacinação");
        //dialogStage.initStyle(StageStyle.UNDECORATED);

        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        FXMLProcessoVacinacaoDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setVacinacao(vacinacao);

        dialogStage.showAndWait();

        return controller.isButtonConfirmarClicked();
    }

    public boolean showFXMLCadastroVacinacaoDialogAlterar(Vacinacao vacinacao) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLProcessoVacinacaoDialogControllerALTERAR.class.getResource("/javafx/view/FXMLProcessoVacinacaoDialogALTERAR.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de Vacinação");
        //dialogStage.initStyle(StageStyle.UNIFIED);

        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        FXMLProcessoVacinacaoDialogControllerALTERAR controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setVacinacao(vacinacao);

        dialogStage.showAndWait();

        return controller.isButtonConfirmarClicked();
    }

     private void makeStageDraggable() {
        tableViewVacinacao.setOnMousePressed((MouseEvent event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        tableViewVacinacao.setOnMouseDragged((MouseEvent event) -> {
            Stage stage = (Stage) tableViewVacinacao.getScene().getWindow();
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }
}
