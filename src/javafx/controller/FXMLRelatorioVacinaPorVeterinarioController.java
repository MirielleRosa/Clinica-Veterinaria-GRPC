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
import javafx.model.dao.VeterinarioDAO;
import javafx.model.database.Database;
import javafx.model.database.DatabaseFactory;
import javafx.model.domain.Animal;
import javafx.model.domain.Cliente;
import javafx.model.domain.Veterinario;
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

public class FXMLRelatorioVacinaPorVeterinarioController implements Initializable {

    @FXML
    private TableView<Veterinario> tableViewRelatorioVet;
    @FXML
    private TableColumn<Veterinario, Integer> tableColumnIdVet;
    @FXML
    private TableColumn<Veterinario, String> tableColumnNomeVet;
    @FXML
    private TableColumn<Veterinario, String> tableColumnNomeAnimal;
    @FXML
    private TableColumn<Veterinario, String> tableColumnNomeCliente;
    @FXML
    private TableColumn<Veterinario, String> tableColumnNomeVacina;
    @FXML
    private TableColumn<Veterinario, Integer> tableColumnQnt;
    @FXML
    private Button buttonImprimir;
    
    private double xOffset = 0;
    private double yOffset = 0;

    private Cliente cliente;
    private List<Veterinario> listVet;
    private ObservableList<Veterinario> observableListVet;

    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final VeterinarioDAO veterinarioDAO = new VeterinarioDAO();
    private final AnimalDAO animalDAO = new AnimalDAO();
    private final ClienteDAO clienteDAO = new ClienteDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        animalDAO.setConnection(connection);
        clienteDAO.setConnection(connection);
        veterinarioDAO.setConnection(connection);
        makeStageDraggable();
        carregarTableViewAnimais();
    }

    public void carregarTableViewAnimais() {
    tableColumnIdVet.setCellValueFactory(new PropertyValueFactory<>("idVet"));
    tableColumnNomeVet.setCellValueFactory(new PropertyValueFactory<>("nome"));
    tableColumnNomeVacina.setCellValueFactory(new PropertyValueFactory<>("vacina"));
    tableColumnQnt.setCellValueFactory(new PropertyValueFactory<>("numVacinacoes"));
    tableColumnNomeAnimal.setCellValueFactory(new PropertyValueFactory<>("animal"));
    tableColumnNomeCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));

    listVet = veterinarioDAO.getVeterinariosVacinacoes();
    
    // Criar uma nova lista observável
    observableListVet = FXCollections.observableArrayList(listVet);
    tableViewRelatorioVet.setItems(observableListVet);

}

    public void handleImprimir() throws JRException {

        URL url = getClass().getResource("/javafx/relatorios/javaFxRelatorioVacinacaoVeterinario.jasper");
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(url);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, connection);//null: caso não existam filtros
        JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);//false: não deixa fechar a aplicação principal
        jasperViewer.setVisible(true);
    }
    
    private void makeStageDraggable() {
        tableViewRelatorioVet.setOnMousePressed((MouseEvent event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        tableViewRelatorioVet.setOnMouseDragged((MouseEvent event) -> {
            Stage stage = (Stage) tableViewRelatorioVet.getScene().getWindow();
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }

}
