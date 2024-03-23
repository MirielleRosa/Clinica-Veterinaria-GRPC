package javafx.controller;

import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.model.dao.AnimalDAO;
import javafx.model.dao.ClienteDAO;
import javafx.model.database.Database;
import javafx.model.database.DatabaseFactory;
import javafx.model.domain.Animal;
import javafx.model.domain.Cliente;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 */
public class FXMLCadastroAnimalDialogController implements Initializable {

    @FXML
    private Label labelAnimalNome;
    @FXML
    private Label labelAnimalIdade;
    @FXML
    private Label labelAnimalEspecie;
    @FXML
    private Label labelAnimalRaca;
    @FXML
    private Label labelAnimalDono;
    @FXML
    private Label labelAnimalPeso;
    @FXML
    private DatePicker datePickerDataNascimento;
    @FXML
    private TextField textFieldAnimalNome;
    @FXML
    private TextField textFieldAnimalIdade;
    @FXML
    private TextField textFieldAnimalEspecie;
    @FXML
    private TextField textFieldAnimalRaca;
    @FXML
    private TextField textFieldAnimalPeso;
    @FXML
    private TextField textFieldAnimalGenero;
    @FXML
    private ComboBox<Cliente> comboBoxAnimalDono;
    @FXML
    private Button buttonConfirmar;
    @FXML
    private Button buttonCancelar;

    private ObservableList<Cliente> observableListCliente;
    private List<Cliente> listCliente;

    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;

    private Animal animal;
    private Cliente cliente;

    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final AnimalDAO animalDAO = new AnimalDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clienteDAO.setConnection(connection);

        listCliente = clienteDAO.listar();

        observableListCliente = FXCollections.observableArrayList(listCliente);

        comboBoxAnimalDono.setItems(observableListCliente);

    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
        this.textFieldAnimalNome.setText(animal.getNome());
        this.textFieldAnimalIdade.setText(String.valueOf(animal.getIdade()));
        this.textFieldAnimalEspecie.setText(animal.getEspecie());
        this.textFieldAnimalRaca.setText(animal.getRaca());
        this.comboBoxAnimalDono.setValue(animal.getDono());
        this.textFieldAnimalPeso.setText(String.valueOf(animal.getPeso()));
        this.textFieldAnimalGenero.setText(animal.getGenero());
        this.datePickerDataNascimento.setValue(animal.getDataNascimento());
    }

    public boolean isButtonConfirmarClicked() {
        return buttonConfirmarClicked;
    }

    @FXML
    public void handleButtonConfirmar() {
        if (validarEntradaDeDados()) {
            animal.setNome(textFieldAnimalNome.getText());
            try {
                int idade = Integer.parseInt(textFieldAnimalIdade.getText());
                animal.setIdade(idade);
            } catch (NumberFormatException e) {

            }
            animal.setEspecie(textFieldAnimalEspecie.getText());
            animal.setRaca(textFieldAnimalRaca.getText());
            animal.setDono(comboBoxAnimalDono.getValue());
            animal.setPeso(Double.parseDouble(textFieldAnimalPeso.getText()));
            animal.setGenero(textFieldAnimalGenero.getText());
            animal.setDataNascimento(datePickerDataNascimento.getValue());

            buttonConfirmarClicked = true;
            dialogStage.close();
        }
    }

    public void handleButtonCancelar() {
        dialogStage.close();
    }

    // Validate input data for registration
    private boolean validarEntradaDeDados() {
        String errorMessage = "";

        if (textFieldAnimalNome.getText() == null || textFieldAnimalNome.getText().isEmpty()) {
            errorMessage += "Nome inválido!\n";
        }
        if (textFieldAnimalIdade.getText() == null || textFieldAnimalIdade.getText().isEmpty()) {
            errorMessage += "Idade inválida!\n";
        }
        if (textFieldAnimalEspecie.getText() == null || textFieldAnimalEspecie.getText().isEmpty()) {
            errorMessage += "Espécie inválida!\n";
        }
        if (textFieldAnimalRaca.getText() == null || textFieldAnimalRaca.getText().isEmpty()) {
            errorMessage += "Raça inválida!\n";
        }
        if (comboBoxAnimalDono.getValue() == null) {
            errorMessage += "Dono inválido!\n";
        }
        if (textFieldAnimalPeso.getText() == null || textFieldAnimalPeso.getText().isEmpty()) {
            errorMessage += "Peso inválido!\n";
        }
        if (textFieldAnimalGenero.getText() == null || textFieldAnimalGenero.getText().isEmpty()) {
            errorMessage += "Gênero inválido!\n";
        }

        if (datePickerDataNascimento.getValue() == null) {
            errorMessage += "Data inválida!\n";
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            // Show error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro no cadastro");
            alert.setHeaderText("Campos inválidos, por favor, corrija...");
            alert.setContentText(errorMessage);
            alert.show();
            return false;
        }
    }
}
