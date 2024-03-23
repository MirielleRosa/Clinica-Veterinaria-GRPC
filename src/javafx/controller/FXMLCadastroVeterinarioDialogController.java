/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx.controller;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.model.dao.VeterinarioDAO;
import javafx.model.database.Database;
import javafx.model.database.DatabaseFactory;
import javafx.model.domain.Veterinario;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLCadastroVeterinarioDialogController implements Initializable {

    @FXML
    private Label labelVeterinarioNome;
    @FXML
    private Label labelVeterinarioCrmv;
    @FXML
    private Label labelVeterinarioCpf;
    @FXML
    private Label labelVeterinarioTelefone;
    @FXML
    private Label labelVeterinarioEmail;
    @FXML
    private Label labelVeterinarioEspecialidade;
    @FXML
    private Label labelVeterinarioDataNascimento;
    @FXML
    private TextField textFieldVeterinarioNome;
    @FXML
    private TextField textFieldVeterinarioCrmv;
    @FXML
    private TextField textFieldVeterinarioCpf;
    @FXML
    private TextField textFieldVeterinarioTelefone;
    @FXML
    private TextField textFieldVeterinarioEmail;
    @FXML
    private TextField textFieldVeterinarioEspecialidade;
    @FXML
    private DatePicker datePickerDataNascimento;
    @FXML
    private TextField textFieldSenha;
    @FXML
    private Button buttonConfirmar;
    @FXML
    private Button buttonCancelar;

    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Veterinario veterinario;
    private final VeterinarioDAO veterinarioDAO = new VeterinarioDAO();
    @FXML
    private Label labelVeterinarioEspecialidade1;
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        veterinarioDAO.setConnection(connection);
    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public Veterinario getCliente() {
        return this.veterinario;
    }

    public void setVeterinario(Veterinario veterinario) {
        this.veterinario = veterinario;
        this.textFieldVeterinarioNome.setText(veterinario.getNome());
        this.textFieldVeterinarioCrmv.setText(veterinario.getCrmv());
        this.textFieldVeterinarioCpf.setText(veterinario.getCpf());
        this.textFieldVeterinarioTelefone.setText(veterinario.getTelefone());
        this.textFieldVeterinarioEmail.setText(veterinario.getEmail());
        this.textFieldVeterinarioEspecialidade.setText(veterinario.getEspecialidade());
        this.datePickerDataNascimento.setValue(veterinario.getDataNascimento());
        this.textFieldSenha.setText(veterinario.getSenha());
    }

    public boolean isButtonConfirmarClicked() {
        return buttonConfirmarClicked;
    }
    

    @FXML
    public void handleButtonConfirmar() {
        if (validarEntradaDeDados()) {
            veterinario.setNome(textFieldVeterinarioNome.getText());
            veterinario.setCrmv(textFieldVeterinarioCrmv.getText());
            veterinario.setCpf(textFieldVeterinarioCpf.getText());
            veterinario.setTelefone(textFieldVeterinarioTelefone.getText());
            veterinario.setEmail(textFieldVeterinarioEmail.getText());
            veterinario.setEspecialidade(textFieldVeterinarioEspecialidade.getText());
            veterinario.setDataNascimento(datePickerDataNascimento.getValue());
            veterinario.setEmail(textFieldVeterinarioEmail.getText());
            veterinario.setSenha(textFieldSenha.getText());
            
            buttonConfirmarClicked = true;
            veterinarioDAO.inserirLogin(textFieldVeterinarioEmail.getText(), "Veterinario", textFieldSenha.getText()); // Substitua "senha" pela senha desejada
            
            dialogStage.close();
        }
    }

    @FXML
    public void handleButtonCancelar() {
        getDialogStage().close();
    }

    //Validar entrada de dados para o cadastro
    private boolean validarEntradaDeDados() {
        String errorMessage = "";

        if (textFieldVeterinarioNome.getText() == null || textFieldVeterinarioNome.getText().length() == 0) {
            errorMessage += "Nome inválido!\n";
        }
        if (textFieldVeterinarioCrmv.getText() == null || textFieldVeterinarioCrmv.getText().length() == 0) {
            errorMessage += "CRMV inválido!\n";
        }
        if (textFieldVeterinarioCpf.getText() == null || textFieldVeterinarioCpf.getText().length() == 0) {
            errorMessage += "CPF inválido!\n";
        }
        if (textFieldVeterinarioTelefone.getText() == null || textFieldVeterinarioTelefone.getText().length() == 0) {
            errorMessage += "Telefone inválido!\n";
        }
        if (textFieldVeterinarioEmail.getText() == null || textFieldVeterinarioEmail.getText().length() == 0) {
            errorMessage += "Email inválido!\n";
        }
        if (textFieldVeterinarioEspecialidade.getText() == null || textFieldVeterinarioEspecialidade.getText().length() == 0) {
            errorMessage += "Especialidade inválida!\n";
        }
        if (datePickerDataNascimento.getValue() == null) {
            errorMessage += "Data inválida!!\n";
        }
        if (textFieldSenha.getText() == null) {
            errorMessage += "Data inválida!!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Mostrando a mensagem de erro
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro no cadastro");
            alert.setHeaderText("Campos inválidos, por favor, corrija...");
            alert.setContentText(errorMessage);
            alert.show();
            return false;
        }
    }

}
