/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javafx.model.dao.VacinaDAO;
import javafx.model.database.Database;
import javafx.model.database.DatabaseFactory;
import javafx.model.domain.Vacina;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FXMLCadastrarVacinaController implements Initializable {

    @FXML
    private TableView<Vacina> tableViewVacina;
    @FXML
    private TableColumn<Vacina, String> tableColumnCod;
    @FXML
    private TableColumn<Vacina, String> tableColumnNome;
    @FXML
    private Label labelVacinaNome;
    @FXML
    private Label labelVacinaFabricante;
    @FXML
    private Label labelVacinaValidade;
    @FXML
    private Label labelVacinaCodigo;
    @FXML
    private Label labelVacinaQuantidade;
    @FXML
    private Label dateFabricacao;
    @FXML
    private Button buttonRemoverVacina;
    @FXML
    private Button buttonAlterarVacina;
    @FXML
    private Button buttonCadastrarVacina;

    private List<Vacina> listVacina;
    private ObservableList<Vacina> observableListVacina;

    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final VacinaDAO vacinaDAO = new VacinaDAO();
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        vacinaDAO.setConnection(connection);

        carregarTableViewVacina();
        makeStageDraggable();

        // Limpando a exibição dos detalhes do cliente
        selecionarItemTableViewVacina(null);

        // Listen acionado diante de quaisquer alterações na seleção de itens do TableView
        tableViewVacina.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewVacina(newValue));

    }

    public void carregarTableViewVacina() {
        tableColumnCod.setCellValueFactory(new PropertyValueFactory<>("idVacina"));
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        listVacina = vacinaDAO.listar();

        observableListVacina = FXCollections.observableArrayList(listVacina);
        tableViewVacina.setItems(observableListVacina);
    }

    public void selecionarItemTableViewVacina(Vacina vacina) {
        if (vacina != null) {
            labelVacinaValidade.setText(String.valueOf(vacina.getDataValidade().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
            dateFabricacao.setText(String.valueOf(vacina.getDataFabricacao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
            labelVacinaCodigo.setText(String.valueOf(vacina.getIdVacina()));
            labelVacinaNome.setText(vacina.getTipo());
            labelVacinaFabricante.setText(vacina.getFabricante());
            labelVacinaQuantidade.setText(String.valueOf(vacina.getQuantidade()));
        } else {
            labelVacinaCodigo.setText("");
            labelVacinaNome.setText("");
            labelVacinaValidade.setText("");
            labelVacinaFabricante.setText("");
            labelVacinaQuantidade.setText("");
            dateFabricacao.setText("");
        }
    }

    @FXML
    public void handleButtonInserir() throws IOException {
        Vacina vacina = new Vacina();
        boolean buttonConfirmarClicked = showFXMLCadastrarVacinaDialog(vacina);
        if (buttonConfirmarClicked) {
            vacinaDAO.inserir(vacina);
            carregarTableViewVacina();
        }
    }

    @FXML
    public void handleButtonAlterar() throws IOException {
        Vacina vacina = tableViewVacina.getSelectionModel().getSelectedItem();//Obtendo cliente selecionado
        if (vacina != null) {
            boolean buttonConfirmarClicked = showFXMLCadastrarVacinaDialog(vacina);
            if (buttonConfirmarClicked) {
                vacinaDAO.alterar(vacina);
                carregarTableViewVacina();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha uma vacina na Tabela!");
            alert.show();
        }
    }

    @FXML
    public void handleButtonRemover() throws IOException {
        Vacina vacina = tableViewVacina.getSelectionModel().getSelectedItem();
        if (vacina != null) {
            vacinaDAO.remover(vacina);
            carregarTableViewVacina();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha uma vacina na Tabela!");
            alert.show();
        }
    }

    public boolean showFXMLCadastrarVacinaDialog(Vacina vacina) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLCadastrarVacinaDialogController.class.getResource("/javafx/view/FXMLCadastrarVacinaDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de Vacina");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        //dialogStage.initStyle(StageStyle.UNDECORATED);
        // Setando o cliente no Controller.
        FXMLCadastrarVacinaDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setVacina(vacina);

        // Mostra o Dialog e espera até que o usuário o feche
        dialogStage.showAndWait();

        return controller.isButtonConfirmarClicked();

    }

    private void makeStageDraggable() {
        tableViewVacina.setOnMousePressed((MouseEvent event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        tableViewVacina.setOnMouseDragged((MouseEvent event) -> {
            Stage stage = (Stage) tableViewVacina.getScene().getWindow();
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }

}
