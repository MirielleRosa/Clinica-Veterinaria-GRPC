/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx.controller;

import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.model.domain.Vacina;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLCadastrarVacinaDialogController implements Initializable {

    @FXML
    private TextField textFieldNomeVacina;
    @FXML
    private Button buttonCadastrar;
    @FXML
    private TextField textFieldFabricante;
    @FXML
    private DatePicker dateFabricacao;
    @FXML
    private DatePicker dateValidade;
    @FXML
    private TextField textFieldQuantidade;
    @FXML
    private Button buttonCadastrar1;

    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Vacina vacina;

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

    public Vacina getVacina() {
        return this.vacina;
    }

    public void setVacina(Vacina vacina) {
        this.vacina = vacina;
        this.textFieldNomeVacina.setText(vacina.getTipo());
        this.textFieldFabricante.setText(vacina.getFabricante());
        this.dateValidade.setValue(vacina.getDataValidade());
        this.dateFabricacao.setValue(vacina.getDataFabricacao());
        this.textFieldQuantidade.setText(Integer.toString(vacina.getQuantidade()));
    }

    public boolean isButtonConfirmarClicked() {
        return buttonConfirmarClicked;
    }

    @FXML
    public void handleButtonConfirmar() throws ParseException {
        if (validarEntradaDeDados()) {

            vacina.setTipo(textFieldNomeVacina.getText());
            vacina.setFabricante(textFieldFabricante.getText());
            vacina.setQuantidade(Integer.parseInt(textFieldQuantidade.getText()));
            vacina.setDataFabricacao(dateFabricacao.getValue());
            vacina.setDataValidade(dateValidade.getValue());

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

        if (textFieldNomeVacina.getText() == null || textFieldNomeVacina.getText().length() == 0) {
            errorMessage += "Nome inválido!\n";
        }
        if (textFieldFabricante.getText() == null || textFieldFabricante.getText().length() == 0) {
            errorMessage += "Favricante inválido!\n";
        }
        if (textFieldQuantidade.getText() == null || textFieldQuantidade.getText().length() == 0) {
            errorMessage += "Quantidade inválida!\n";
        }
        if (dateFabricacao.getValue() == null) {
            errorMessage += "Data de fabricação inválida!\n";
        }
        if (dateValidade.getValue() == null) {
            errorMessage += "Data de validade inválida!\n";
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
