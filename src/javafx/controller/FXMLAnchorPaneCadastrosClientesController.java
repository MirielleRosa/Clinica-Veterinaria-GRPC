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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.fxml.FXMLLoader;
import javafx.model.dao.ClienteDAO;
import javafx.model.database.Database;
import javafx.model.database.DatabaseFactory;
import javafx.model.domain.Cliente;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FXMLAnchorPaneCadastrosClientesController implements Initializable {

    @FXML
    private TableView<Cliente> tableViewClientes;
    @FXML
    private TableColumn<Cliente, String> tableColumnClienteNome;
    @FXML
    private TableColumn<Cliente, String> tableColumnClienteCPF;
    @FXML
    private TableColumn<Cliente, String> tableColumnClienteTelefone;
    @FXML
    private Label labelClienteCodigo;
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
    private Button buttonInserir;
    @FXML
    private Button buttonAlterar;
    @FXML
    private Button buttonRemover;
    private double xOffset = 0;
    private double yOffset = 0;
    private List<Cliente> listClientes;
    private ObservableList<Cliente> observableListClientes;

    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final ClienteDAO clienteDAO = new ClienteDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clienteDAO.setConnection(connection);
        makeStageDraggable();
        carregarTableViewClientes();

        // Limpando a exibição dos detalhes do cliente
        selecionarItemTableViewClientes(null);

        // Listen acionado diante de quaisquer alterações na seleção de itens do TableView
        tableViewClientes.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewClientes(newValue));

    }

    public void carregarTableViewClientes() {
        tableColumnClienteNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnClienteCPF.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        tableColumnClienteTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));

        listClientes = clienteDAO.listarClientes();

        observableListClientes = FXCollections.observableArrayList(listClientes);
        tableViewClientes.setItems(observableListClientes);
    }

    //Alterar ao adicionar mais atributos da Abstração no FXML
    public void selecionarItemTableViewClientes(Cliente cliente) {
        if (cliente != null) {
            labelClienteCodigo.setText(String.valueOf(cliente.getIdCliente()));
            labelClienteNome.setText(cliente.getNome());
            labelClienteCPF.setText(cliente.getCpf());
            labelClienteRg.setText(cliente.getRg());
            labelClienteTelefone.setText(cliente.getTelefone());
            labelClienteEmail.setText(cliente.getEmail());
            labelClienteDataNascimento.setText(String.valueOf(cliente.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        } else {
            labelClienteCodigo.setText("");
            labelClienteNome.setText("");
            labelClienteCPF.setText("");
            labelClienteRg.setText("");
            labelClienteTelefone.setText("");
            labelClienteEmail.setText("");
            labelClienteDataNascimento.setText("");
        }
    }

    @FXML
    public void handleButtonInserir() throws IOException {
        Cliente cliente = new Cliente();
        boolean buttonConfirmarClicked = showFXMLCadastroClienteDialog(cliente);
        if (buttonConfirmarClicked) {
            clienteDAO.inserir(cliente);
            carregarTableViewClientes();
        }
    }

    @FXML
    public void handleButtonAlterar() throws IOException {
        Cliente cliente = tableViewClientes.getSelectionModel().getSelectedItem();//Obtendo cliente selecionado
        if (cliente != null) {
            boolean buttonConfirmarClicked = showFXMLCadastroClienteDialog(cliente);
            if (buttonConfirmarClicked) {
                clienteDAO.alterar(cliente);
                carregarTableViewClientes();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um cliente na Tabela!");
            alert.show();
        }
    }

    @FXML
    public void handleButtonRemover() throws IOException {
        Cliente cliente = tableViewClientes.getSelectionModel().getSelectedItem();
        if (cliente != null) {
            clienteDAO.remover(cliente);
            carregarTableViewClientes();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um cliente na Tabela!");
            alert.show();
        }
    }

    public boolean showFXMLCadastroClienteDialog(Cliente cliente) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLCadastroClienteDialogController.class.getResource("/javafx/view/FXMLCadastroClienteDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de Clientes");

        //dialogStage.initStyle(StageStyle.UTILITY);


        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        // Setando o cliente no Controller.
        FXMLCadastroClienteDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setCliente(cliente);

        // Mostra o Dialog e espera até que o usuário o feche
        dialogStage.showAndWait();

        return controller.isButtonConfirmarClicked();

    }

    private void makeStageDraggable() {
        AnchorPane anchorPane = (AnchorPane) tableViewClientes.getParent();

        anchorPane.setOnMousePressed((MouseEvent event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        anchorPane.setOnMouseDragged((MouseEvent event) -> {
            Stage stage = (Stage) anchorPane.getScene().getWindow();
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }

}
