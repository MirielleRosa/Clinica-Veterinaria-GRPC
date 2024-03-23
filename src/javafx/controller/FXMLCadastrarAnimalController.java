package javafx.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.model.dao.AnimalDAO;
import javafx.model.database.Database;
import javafx.model.database.DatabaseFactory;
import javafx.model.domain.Animal;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.input.MouseEvent;

public class FXMLCadastrarAnimalController implements Initializable {

    @FXML
    private TableView<Animal> tableViewAnimal;
    @FXML
    private TableColumn<Animal, String> tableColumnAnimalNome;
    @FXML
    private TableColumn<Animal, String> tableColumnAnimalEspecie;
    @FXML
    private TableColumn<Animal, String> tableColumnAnimalDono;
    @FXML
    private Label labelAnimalCodigo;
    @FXML
    private Label labelAnimalNome;
    @FXML
    private Label labelAnimalIdade;
    @FXML
    private Label labelAnimalEspecie;
    @FXML
    private Label labelAnimalRaça;
    @FXML
    private Label labelAnimalDono;
    @FXML
    private Label labelAnimalPeso;
    @FXML
    private Label labelAnimalDatadeNascimento;
    @FXML
    private Label labelAnimalGenero;
    @FXML
    private Label labelCodDono;
    @FXML
    private Button buttonInserir;
    @FXML
    private Button buttonAlterar;
    @FXML
    private Button buttonRemover;

    private List<Animal> listAnimal;
    private ObservableList<Animal> observableListAnimal;

    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final AnimalDAO animalDAO = new AnimalDAO();

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        animalDAO.setConnection(connection);

        carregarTableViewAnimais();
        makeStageDraggable();

        // Limpando a exibição dos detalhes do cliente
        selecionarItemTableViewAnimais(null);

        // Listen acionado diante de quaisquer alterações na seleção de itens do TableView
        tableViewAnimal.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewAnimais(newValue));
    }

    public void carregarTableViewAnimais() {
        tableColumnAnimalNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnAnimalEspecie.setCellValueFactory(new PropertyValueFactory<>("especie"));
        tableColumnAnimalDono.setCellValueFactory(new PropertyValueFactory<>("dono"));

        listAnimal = animalDAO.listar();

        observableListAnimal = FXCollections.observableArrayList(listAnimal);
        tableViewAnimal.setItems(observableListAnimal);
    }

    public void selecionarItemTableViewAnimais(Animal animal) {
        if (animal != null) {
            labelAnimalCodigo.setText(String.valueOf(animal.getIdAnimal()));
            labelAnimalNome.setText(animal.getNome());
            labelAnimalIdade.setText(String.valueOf(animal.getIdade()));
            labelAnimalEspecie.setText(animal.getEspecie());
            labelAnimalRaça.setText(animal.getRaca());
            labelAnimalDono.setText(String.valueOf(animal.getDono().getNome()));
            labelAnimalPeso.setText(String.valueOf(animal.getPeso()));
            labelAnimalDatadeNascimento.setText(String.valueOf(animal.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
            labelAnimalGenero.setText(animal.getGenero());
            labelCodDono.setText(String.valueOf(animal.getDono().getIdCliente()));
        } else {
            labelAnimalCodigo.setText("");
            labelCodDono.setText("");
            labelAnimalNome.setText("");
            labelAnimalIdade.setText("");
            labelAnimalEspecie.setText("");
            labelAnimalRaça.setText("");
            labelAnimalDono.setText("");
            labelAnimalPeso.setText("");
            labelAnimalDatadeNascimento.setText("");
            labelAnimalGenero.setText("");
        }
    }

    @FXML
    public void handleButtonInserir() throws IOException {
        Animal animal = new Animal();
        boolean buttonConfirmarClicked = showFXMLCadastroAnimalDialog(animal);
        if (buttonConfirmarClicked) {
            animalDAO.inserir(animal);
            carregarTableViewAnimais();
        }
    }

    @FXML
    public void handleButtonAlterar() throws IOException {
        Animal animal = tableViewAnimal.getSelectionModel().getSelectedItem();//Obtendo cliente selecionado
        if (animal != null) {
            boolean buttonConfirmarClicked = showFXMLCadastroAnimalDialog(animal);
            if (buttonConfirmarClicked) {
                animalDAO.alterar(animal);
                carregarTableViewAnimais();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um animal na Tabela!");
            alert.show();
        }
    }

    @FXML
    public void handleButtonRemover() throws IOException {
        Animal animal = tableViewAnimal.getSelectionModel().getSelectedItem();
        if (animal != null) {
            animalDAO.remover(animal);
            carregarTableViewAnimais();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um animal na Tabela!");
            alert.show();
        }
    }

    public boolean showFXMLCadastroAnimalDialog(Animal animal) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLCadastroAnimalDialogController.class.getResource("/javafx/view/FXMLCadastroAnimalDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de Animais");
        //dialogStage.initStyle(StageStyle.UTILITY);

        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        // Setando o cliente no Controller.
        FXMLCadastroAnimalDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setAnimal(animal);     
        

        // Mostra o Dialog e espera até que o usuário o feche
        dialogStage.showAndWait();

        return controller.isButtonConfirmarClicked();
    }

    private void makeStageDraggable() {
        tableViewAnimal.setOnMousePressed((MouseEvent event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        tableViewAnimal.setOnMouseDragged((MouseEvent event) -> {
            Stage stage = (Stage) tableViewAnimal.getScene().getWindow();
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }
}
