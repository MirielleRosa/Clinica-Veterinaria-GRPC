package javafx.controller;

import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.model.dao.AnimalDAO;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class FXMLProcessoVacinacaoDialogController implements Initializable {

    @FXML
    private Button buttonAdicionar;
    @FXML
    private ComboBox<Vacina> comboBoxVacina;
    @FXML
    private DatePicker dataVacinacao;
    @FXML
    private ComboBox<Cliente> comboBoxCliente;
    @FXML
    private ComboBox<Veterinario> comboBoxVeterinario;
    @FXML
    private Button buttonConfirmar;
    @FXML
    private Button buttonCancelar;
    @FXML
    private TableView<Animal> tableViewAnimal;
    @FXML
    private TableColumn<Animal, Integer> tableColumnAnimalIDAnimal;
    @FXML
    private TableColumn<Animal, String> tableColumnAnimalNomeAnimal;

    private List<Cliente> listClientes;
    private List<Animal> listAnimal;
    private List<Vacina> listVacina;
    private List<Veterinario> listVeterinario;
    private List<Vacinacao> listVacinacao;
    private ObservableList<Cliente> observableListClientes;
    private ObservableList<Animal> observableListAnimal;
    private ObservableList<Vacina> observableListVacina;
    private ObservableList<Vacinacao> observableListVacinacao;
    private ObservableList<Veterinario> observableListVeterinario;

    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final AnimalDAO animalDAO = new AnimalDAO();
    private final VacinaDAO vacinaDAO = new VacinaDAO();
    private final VacinacaoDAO vacinacaoDAO = new VacinacaoDAO();
    private final VeterinarioDAO veterinarioDAO = new VeterinarioDAO();

    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Vacina vacina;
    private Vacinacao vacinacao;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clienteDAO.setConnection(connection);
        animalDAO.setConnection(connection);
        vacinaDAO.setConnection(connection);
        veterinarioDAO.setConnection(connection);
        vacinacaoDAO.setConnection(connection);

        carregarComboBoxClientes();
        carregarComboBoxVeterinario();
        carregarComboBoxVacina();

    }

    public void carregarComboBoxClientes() {
        //caregar os animais do cliente//
        listClientes = clienteDAO.listar();
        observableListClientes = FXCollections.observableArrayList(listClientes);
        comboBoxCliente.setItems(observableListClientes);
        comboBoxCliente.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
         
            // carrega lista de animais do cliente selecionado
            tableColumnAnimalIDAnimal.setCellValueFactory(new PropertyValueFactory<>("idAnimal"));
            tableColumnAnimalNomeAnimal.setCellValueFactory(new PropertyValueFactory<>("nome"));
            listAnimal = animalDAO.listarAnimalPorCliente(newValue); // usar objeto AnimalDAO
            observableListAnimal = FXCollections.observableArrayList(listAnimal);
            tableViewAnimal.setItems(observableListAnimal);
        });
    }

    public void carregarComboBoxVacina() {
        listVacina = vacinaDAO.listar();
        observableListVacina = FXCollections.observableArrayList(listVacina);
        comboBoxVacina.setItems(observableListVacina);
    }

    public void carregarComboBoxVeterinario() {
        listVeterinario = veterinarioDAO.listar();
        observableListVeterinario = FXCollections.observableArrayList(listVeterinario);
        comboBoxVeterinario.setItems(observableListVeterinario);
    }

    public boolean isButtonConfirmarClicked() {
        return buttonConfirmarClicked;
    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public Vacinacao getVacinacao() {
        return vacinacao;
    }

    public void setVacinacao(Vacinacao vacinacao) {
        this.vacinacao = vacinacao;
        this.comboBoxVacina.setValue(vacinacao.getVacina());
        this.comboBoxCliente.setValue(vacinacao.getDono());
        this.comboBoxVeterinario.setValue(vacinacao.getVeterinario());
        this.dataVacinacao.setValue(vacinacao.getDataAplicacao());
        
    }
    
    @FXML
    public void buttonInserir(ActionEvent event) {
        if (tableViewAnimal.getSelectionModel().getSelectedItem() != null
                && comboBoxVacina.getValue() != null
                && comboBoxCliente.getValue() != null
                && comboBoxVeterinario.getValue() != null
                && dataVacinacao.getValue() != null) {

            Animal animalSelecionado = (Animal) tableViewAnimal.getSelectionModel().getSelectedItem();

            // Verifica se o animal já foi vacinado duas vezes
            if (vacinacao.GetlistVacinacao().stream()
                    .filter(v -> v.getAnimal().equals(animalSelecionado))
                    .count() >= 2) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText("Animal já vacinado duas vezes");
                alert.setContentText("Não é possível vacinar o mesmo animal mais de duas vezes");
                alert.showAndWait();
                return;
            }

            // Cria uma nova instância de vacinacao para cada vacinação a ser adicionada
            Vacinacao vacina = new Vacinacao();
            vacina.setVacina((Vacina) comboBoxVacina.getValue());
            vacina.setAnimal(animalSelecionado);
            vacina.setDono((Cliente) comboBoxCliente.getValue());
            vacina.setVeterinario((Veterinario) comboBoxVeterinario.getValue());
            vacina.setDataAplicacao(dataVacinacao.getValue());

            // Adiciona a vacinação na lista de vacinações
            vacinacao.GetlistVacinacao().add(vacina);

            // Carrega a tabela de visualização com a lista de vacinações atualizada
            observableListVacinacao = FXCollections.observableArrayList(vacinacao.GetlistVacinacao());
           // tableViewVacinacao.setItems(observableListVacinacao);
        }
    }
    
    public void handleButtonConfirmar() {
    if (validarEntradaDeDados()) {
        Animal animalSelecionado = tableViewAnimal.getSelectionModel().getSelectedItem();
        LocalDate dataSelecionada = dataVacinacao.getValue();

        // Verifica se o animal já foi vacinado mais de duas vezes na mesma data
        int numeroVacinacoes = vacinacaoDAO.contarVacinacoesPorAnimalEData(animalSelecionado, dataSelecionada);
        if (numeroVacinacoes >= 2) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Animal já vacinado duas vezes na mesma data");
            alert.setContentText("Não é possível vacinar o mesmo animal mais de duas vezes na mesma data");
            alert.showAndWait();
            return;
        }

        vacinacao.setDono((Cliente) comboBoxCliente.getSelectionModel().getSelectedItem());
        vacinacao.setVeterinario((Veterinario) comboBoxVeterinario.getSelectionModel().getSelectedItem());
        vacinacao.setVacina((Vacina) comboBoxVacina.getSelectionModel().getSelectedItem());
        vacinacao.setAnimal(animalSelecionado);
        vacinacao.setDataAplicacao(dataSelecionada);

        buttonConfirmarClicked = true;
        dialogStage.close();
    }
}



    @FXML
    public void handleButtonCancelar() {
        getDialogStage().close();
    }

    private boolean validarEntradaDeDados() {
        String errorMessage = "";

        if (comboBoxVacina.getValue() == null) {
            errorMessage += "Vacina inválida!\n";
        }

        if (comboBoxCliente.getValue() == null) {
            errorMessage += "Cliente inválido!\n";
        }

        if (tableViewAnimal.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Animal inválido!\n";
        }

        if (dataVacinacao.getValue() == null ) {
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

 
