/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx.controller;

import java.net.URL;
import java.sql.Connection;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.model.dao.AgendamentoDAO;
import javafx.model.database.Database;
import javafx.model.database.DatabaseFactory;
import javafx.model.domain.Agendamento;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;


public class FXMLAgendamentoDashboardDialogController implements Initializable {
    @FXML
    private Label labelCliente;
    @FXML
    private Label labelAnimal;
    @FXML
    private Label labelTipo;
    @FXML
    private Label labelData;
    @FXML
    private Label labelHora;
    private MenuButton menuButtonStatus;
    @FXML
    private TextArea textAreaDescricao;
    
    
    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Agendamento agendamento;

     private final AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
    @FXML
    private ComboBox<String> comboBoxStatus;
  
    private final ObservableList<String> statusList = FXCollections.observableArrayList("Agendado", "Concluído");
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         agendamentoDAO.setConnection(connection);

        comboBoxStatus.setItems(statusList);
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
    
    
    
    void handleMenuItemAgendado() {
        System.out.println("Agendado selecionado");
        menuButtonStatus.setText("Agendado");
    }

    void handleMenuItemConcluido() {
        System.out.println("Concluído selecionado");
        menuButtonStatus.setText("Concluído");
    }

  
  public void setAgendamento(Agendamento agendamento) {
    this.agendamento = agendamento;
    if (agendamento != null) {
        this.labelCliente.setText(String.valueOf(agendamento.getCliente().getNome()));
        this.labelAnimal.setText(String.valueOf(agendamento.getAnimal().getNome()));
        this.labelData.setText(String.valueOf(agendamento.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        this.labelHora.setText(agendamento.getHora().format(DateTimeFormatter.ofPattern("HH:mm")));
        this.labelTipo.setText(agendamento.getTipo());
        this.comboBoxStatus.setValue(agendamento.getStatus());
    }
}

@FXML
public void handleButtonConfirmar() {
    System.out.println(agendamento);
    if (agendamento != null) {
        agendamento.setStatus(comboBoxStatus.getValue());
        buttonConfirmarClicked = true;
        dialogStage.close();
    } else {
        System.out.println("Agendamento não inicializado!");
    }
}




    public boolean isButtonConfirmarClicked() {
        return buttonConfirmarClicked;
    }
    
}
