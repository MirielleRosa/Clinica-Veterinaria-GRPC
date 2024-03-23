/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 * FXML Controller class
 *
 * @author linha
 */
public class FXMLRelatorioAnimaisController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TableView<Cliente> tableViewAnimais;
    @FXML
    private TableColumn<Cliente, Cliente> tableColumnIdCliente;
    @FXML
    private TableColumn<Cliente, String> tableColumnNomeCliente;
    @FXML
    private TableColumn<Cliente, Integer> tableColumnClienteCpf;
    @FXML
    private TableColumn<Cliente, String> tableColumnClienteTelefone;
    @FXML
    private TableColumn<Cliente, Integer> tableColumnQuantidade;

    @FXML
    private Button buttonImprimir;

    private double xOffset = 0;
    private double yOffset = 0;

    private Cliente cliente;
    private List<Cliente> listAnimal;
    private ObservableList<Cliente> observableListAnimal;

    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final AnimalDAO animalDAO = new AnimalDAO();
    private final ClienteDAO clienteDAO = new ClienteDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        animalDAO.setConnection(connection);
        clienteDAO.setConnection(connection);
        makeStageDraggable();
        carregarTableViewAnimais();
    }

    public void carregarTableViewAnimais() {
        tableColumnIdCliente.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
        tableColumnNomeCliente.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnClienteCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        tableColumnClienteTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        tableColumnQuantidade.setCellValueFactory(new PropertyValueFactory<>("numAnimais"));

        listAnimal = clienteDAO.listarPorCliente();

        observableListAnimal = FXCollections.observableArrayList(listAnimal);
        tableViewAnimais.setItems(observableListAnimal);
    }

    @FXML
    public void handleImprimir() throws JRException {

        URL url = getClass().getResource("/javafx/relatorios/JavaFxRelatorioAnimais.jasper");
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(url);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, connection);//null: caso não existam filtros
        JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);//false: não deixa fechar a aplicação principal
        jasperViewer.setVisible(true);
    }

    private void makeStageDraggable() {
        tableViewAnimais.setOnMousePressed((MouseEvent event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        tableViewAnimais.setOnMouseDragged((MouseEvent event) -> {
            Stage stage = (Stage) tableViewAnimais.getScene().getWindow();
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }

}
