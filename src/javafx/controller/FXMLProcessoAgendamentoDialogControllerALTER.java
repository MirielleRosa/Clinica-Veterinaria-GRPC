/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx.controller;

import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.model.dao.AgendamentoDAO;
import javafx.model.domain.Agendamento;
import javafx.model.domain.Animal;
import javafx.model.domain.Cliente;
import javafx.model.domain.Vacina;
import javafx.model.domain.Vacinacao;
import javafx.model.domain.Veterinario;
import javafx.model.dao.AnimalDAO;
import javafx.model.dao.ClienteDAO;
import javafx.model.database.Database;
import javafx.model.database.DatabaseFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author linha
 */
public class FXMLProcessoAgendamentoDialogControllerALTER implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ComboBox<String> comboBoxAgendamentoHora;
    @FXML
    private ComboBox<Cliente> comboBoxAgendamentoCliente;
    @FXML
    private ComboBox<Animal> comboBoxAgendamentoAnimal;
    @FXML
    private ComboBox<String> comboBoxTipoConsulta;
    @FXML
    private DatePicker DatePickerAgendamentoData;
    @FXML
    private Button buttonConfirmar;
    @FXML
    private Button buttonCancelar;

    private List<Cliente> listCliente;
    private List<Animal> listAnimal;
    private List<Agendamento> listVacinacao;
    private List<String> horarios = Arrays.asList("09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00");
    private List<String> tipo = Arrays.asList("Urgente", "Check Up");
    private ObservableList<Cliente> observableListClientes;
    private ObservableList<Animal> observableListAnimal;
    private ObservableList<String> observableListHorario;
    private ObservableList<String> observableListTipo;

    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private Animal animal;
    private Cliente cliente;
    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final AnimalDAO animalDAO = new AnimalDAO();
    private final AgendamentoDAO agendamentoDAO = new AgendamentoDAO();

    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Agendamento agendamento;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clienteDAO.setConnection(connection);
        animalDAO.setConnection(connection);
        agendamentoDAO.setConnection(connection);

        listCliente = clienteDAO.listar();
        listAnimal = animalDAO.listar();
        carregarComboBoxClientes();
        carregarComboBoxHora();
        carregarComboTipoConsulta();
    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public Agendamento getAgendamento() {
        return agendamento;
    }

    @FXML
    public void handleButtonCancelar() {
        dialogStage.close();
    }

    public void setAgendamento(Agendamento agendamento) {
        this.agendamento = agendamento;
        this.DatePickerAgendamentoData.setValue(agendamento.getData());
        this.comboBoxAgendamentoHora.setValue(String.valueOf(agendamento.getHora()));
        this.comboBoxAgendamentoCliente.setValue(agendamento.getCliente());
        //this.comboBoxAgendamentoAnimal.setValue(agendamento.getAnimal());
        this.comboBoxTipoConsulta.setValue(agendamento.getTipo());
    }

   public void carregarComboBoxClientes() {
    listCliente = clienteDAO.listar();
    observableListClientes = FXCollections.observableArrayList(listCliente);
    comboBoxAgendamentoCliente.setItems(observableListClientes);
    comboBoxAgendamentoCliente.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue != null) {
            listAnimal = animalDAO.listarAnimalPorCliente(newValue);
            observableListAnimal = FXCollections.observableArrayList(listAnimal);
            comboBoxAgendamentoAnimal.setItems(observableListAnimal);
        }
    });
}


 @FXML
public void handleButtonConfirmar() {
    if (validarEntradaDeDados()) {
        LocalDate data = DatePickerAgendamentoData.getValue();
        LocalTime novoHorario = LocalTime.parse(comboBoxAgendamentoHora.getValue());

        // Verifica se o animal já possui uma consulta agendada para o dia e horário selecionados
//        boolean consultaExistente = agendamentoDAO.verificarConsultaExistente(agendamento.getAnimal(), data, novoHorario);
//
//        if (consultaExistente) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Erro");
//            alert.setHeaderText("Animal já possui uma consulta agendada para o horário selecionado");
//            alert.setContentText("O animal já possui uma consulta agendada para o horário selecionado. Por favor, selecione outro horário.");
//            alert.showAndWait();
//            return;
//        }

        agendamento.setData(data);
        agendamento.setHora(novoHorario);
        agendamento.setCliente(comboBoxAgendamentoCliente.getValue());
        agendamento.setAnimal(comboBoxAgendamentoAnimal.getValue());
        agendamento.setTipo(comboBoxTipoConsulta.getValue());

        // Atualiza o agendamento no banco de dados
        agendamentoDAO.alterar(agendamento);

        buttonConfirmarClicked = true;
        dialogStage.close();
    }
}


    public boolean isButtonConfirmarClicked() {
        return buttonConfirmarClicked;
    }

    public void carregarComboBoxHora() {
        observableListHorario = FXCollections.observableArrayList(horarios);
        comboBoxAgendamentoHora.setItems(observableListHorario);
    }

    public void carregarComboTipoConsulta() {
        observableListTipo = FXCollections.observableArrayList(tipo);
        comboBoxTipoConsulta.setItems(observableListTipo);
    }

    private boolean validarEntradaDeDados() {
        String errorMessage = "";

        if (comboBoxAgendamentoHora.getValue() == null) {
            errorMessage += "Hora inválida!\n";
        }

        if (comboBoxAgendamentoCliente.getValue() == null) {
            errorMessage += "Cliente inválido!\n";
        }

        if (comboBoxAgendamentoAnimal.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Animal inválido!\n";
        }

        if (DatePickerAgendamentoData.getValue() == null) {
            errorMessage += "Data de vacinação inválida!\n";
        }
        if (comboBoxTipoConsulta.getValue() == null) {
            errorMessage += "Data de vacinação inválida!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Mostra a mensagem de erro.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Campos Inválidos");
            alert.setHeaderText("Por favor, corrija os campos inválidos.");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }

}
