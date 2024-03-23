package javafx.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.model.domain.Cliente;
import javafx.model.domain.Veterinario;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLCadastroClienteDialogController implements Initializable {

    @FXML
    private Label labelClienteNome;
    @FXML
    private Label labelClienteCPF;
    @FXML
    private Label labelClienteRg;
    @FXML
    private Label labelClienteTelefone;
    @FXML
    private Label labelClienteEmail;
    @FXML
    private Label labelClienteDataNascimento;
    @FXML
    private TextField textFieldClienteNome;
    @FXML
    private TextField textFieldClienteCPF;
    @FXML
    private TextField textFieldClienteRg;
    @FXML
    private TextField textFieldClienteTelefone;
    @FXML
    private TextField textFieldClienteEmail;
    @FXML
    private DatePicker datePickerDataNascimento;
    @FXML
    private Button buttonConfirmar;
    @FXML
    private Button buttonCancelar;

    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Cliente cliente;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
        this.textFieldClienteNome.setText(cliente.getNome());
        this.textFieldClienteCPF.setText(cliente.getCpf());
        this.textFieldClienteRg.setText(cliente.getRg());
        this.textFieldClienteTelefone.setText(cliente.getTelefone());
        this.textFieldClienteEmail.setText(cliente.getEmail());
        this.datePickerDataNascimento.setValue(cliente.getDataNascimento());
    }

    public boolean isButtonConfirmarClicked() {
        return buttonConfirmarClicked;
    }

    @FXML
    public void handleButtonConfirmar() {
        if (validarEntradaDeDados()) {
            cliente.setNome(textFieldClienteNome.getText());
            cliente.setCpf(textFieldClienteCPF.getText());
            cliente.setRg(textFieldClienteRg.getText());
            cliente.setTelefone(textFieldClienteTelefone.getText());
            cliente.setEmail(textFieldClienteEmail.getText());
            cliente.setDataNascimento(datePickerDataNascimento.getValue());
            buttonConfirmarClicked = true;
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

        if (textFieldClienteNome.getText() == null || textFieldClienteNome.getText().length() == 0) {
            errorMessage += "Nome inválido!\n";
        }
        if (textFieldClienteCPF.getText() == null || textFieldClienteCPF.getText().length() == 0) {
            errorMessage += "CPF inválido!\n";
        }
        if (textFieldClienteRg.getText() == null || textFieldClienteTelefone.getText().length() == 0) {
            errorMessage += "Rg inválido!\n";
        }
        if (textFieldClienteTelefone.getText() == null || textFieldClienteTelefone.getText().length() == 0) {
            errorMessage += "Telefone inválido!\n";
        }
        if (textFieldClienteEmail.getText() == null || textFieldClienteTelefone.getText().length() == 0) {
            errorMessage += "Email inválido!\n";
        }
        if (datePickerDataNascimento.getValue() == null) {
            errorMessage += "Data inválida!\n";
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
