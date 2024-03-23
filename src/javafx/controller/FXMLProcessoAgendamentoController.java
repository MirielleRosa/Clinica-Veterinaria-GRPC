/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.model.dao.AgendamentoDAO;
import javafx.model.dao.VacinacaoDAO;
import javafx.model.database.Database;
import javafx.model.database.DatabaseFactory;
import javafx.model.domain.Agendamento;
import javafx.model.domain.Animal;
import javafx.model.domain.Cliente;
import javafx.model.domain.Vacinacao;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author linha
 */
public class FXMLProcessoAgendamentoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TableView<Agendamento> tableViewAgendamento;
    @FXML
    private TableColumn<Agendamento, Integer> tableColumnAgendamentoCodigo;
    @FXML
    private TableColumn<Agendamento, LocalDate> tableColumnAgendamentoData;
    @FXML
    private TableColumn<Agendamento, String> tableColumnAgendamentoAnimal;
    @FXML
    private Label labelAgendamentoCodigo;
    @FXML
    private Label labelAgendamentoCliente;
    @FXML
    private Label labelAgendamentoAnimal;
    @FXML
    private Label labelAgendamentoData;
    @FXML
    private Label labelAgendamentoHora;
    @FXML
    private Label labelTipoConsulta;
    @FXML
    private Button buttonInserir;
    @FXML
    private Button buttonRemover;
    @FXML
    private Button buttonAlterar;

    private double xOffset = 0;
    private double yOffset = 0;

    private List<Agendamento> listAgendamento;
    private ObservableList<Agendamento> observableListAgendamento;

    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
    @FXML
    private Label labelStatus;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        agendamentoDAO.setConnection(connection);

        carregarTableViewAgendamento();

        makeStageDraggable();
        // Limpando a exibição dos detalhes do cliente
        selecionarItemTableViewAgendamento(null);

        // Listen acionado diante de quaisquer alterações na seleção de itens do TableView
        tableViewAgendamento.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewAgendamento(newValue));
    }

    public void carregarTableViewAgendamento() {
        tableColumnAgendamentoCodigo.setCellValueFactory(new PropertyValueFactory<>("idAgendamento"));
        tableColumnAgendamentoData.setCellValueFactory(new PropertyValueFactory<>("data"));
        tableColumnAgendamentoAnimal.setCellValueFactory(new PropertyValueFactory<>("animal"));

        listAgendamento = agendamentoDAO.listar();

        observableListAgendamento = FXCollections.observableArrayList(listAgendamento);
        tableViewAgendamento.setItems(observableListAgendamento);
    }

    public void selecionarItemTableViewAgendamento(Agendamento agendamento) {
        if (agendamento != null) {
            labelAgendamentoCodigo.setText(String.valueOf(agendamento.getIdAgendamento()));
            labelAgendamentoCliente.setText(String.valueOf(agendamento.getCliente().getNome()));
            labelAgendamentoAnimal.setText(String.valueOf(agendamento.getAnimal().getNome()));
            labelAgendamentoData.setText(String.valueOf(agendamento.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
            labelAgendamentoHora.setText(agendamento.getHora().format(DateTimeFormatter.ofPattern("HH:mm")));
            labelTipoConsulta.setText(agendamento.getTipo());
            labelStatus.setText(agendamento.getStatus());
        } else {
            labelAgendamentoCodigo.setText("");
            labelAgendamentoCliente.setText("");
            labelAgendamentoAnimal.setText("");
            labelAgendamentoData.setText("");
            labelAgendamentoHora.setText("");
            labelTipoConsulta.setText("");
            labelStatus.setText("");
        }
    }

    @FXML
    public void handleButtonInserir() throws IOException {
        Agendamento agendamento = new Agendamento();
        boolean buttonConfirmarClicked = showFXMLProcessoAgendamentoDialog(agendamento);
        if (buttonConfirmarClicked) {
            agendamentoDAO.inserir(agendamento);
            carregarTableViewAgendamento();
        }
    }

    @FXML
    public void handleButtonAlterar() throws IOException {
        Agendamento agendamento = tableViewAgendamento.getSelectionModel().getSelectedItem();
        if (agendamento != null) {
            boolean buttonConfirmarClicked = showFXMLProcessoAgendamentoDialogALTER(agendamento);
            if (buttonConfirmarClicked) {
                agendamentoDAO.alterar(agendamento);
                carregarTableViewAgendamento();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um agendamento na Tabela!");
            alert.show();
        }
    }

    @FXML
    public void handleButtonRemover() throws IOException {
        Agendamento agendamento = tableViewAgendamento.getSelectionModel().getSelectedItem();
        if (agendamento != null) {
            agendamentoDAO.remover(agendamento);
            carregarTableViewAgendamento();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um funcionario na Tabela!");
            alert.show();
        }
    }

    public boolean showFXMLProcessoAgendamentoDialog(Agendamento agendamento) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLProcessoAgendamentoDialogController.class.getResource("/javafx/view/FXMLProcessoAgendamentoDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Agendamento");

        //dialogStage.initStyle(StageStyle.UNDECORATED);

        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        FXMLProcessoAgendamentoDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setAgendamento(agendamento);

        dialogStage.showAndWait();

        return controller.isButtonConfirmarClicked();
    }

    public boolean showFXMLProcessoAgendamentoDialogALTER(Agendamento agendamento) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLProcessoAgendamentoDialogControllerALTER.class.getResource("/javafx/view/FXMLProcessoAgendamentoDialogALTER.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Agendamento");
        //dialogStage.initStyle(StageStyle.UNDECORATED);

        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        FXMLProcessoAgendamentoDialogControllerALTER controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setAgendamento(agendamento);

        dialogStage.showAndWait();

        return controller.isButtonConfirmarClicked();
    }
    
    private void makeStageDraggable() {
        tableViewAgendamento.setOnMousePressed((MouseEvent event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        tableViewAgendamento.setOnMouseDragged((MouseEvent event) -> {
            Stage stage = (Stage) tableViewAgendamento.getScene().getWindow();
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }

}
