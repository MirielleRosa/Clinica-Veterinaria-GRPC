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
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.model.dao.AgendamentoDAO;
import javafx.model.dao.AnimalDAO;
import javafx.model.dao.ClienteDAO;
import javafx.model.database.Database;
import javafx.model.database.DatabaseFactory;
import javafx.model.domain.Agendamento;
import javafx.model.domain.Animal;
import javafx.model.domain.Cliente;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
public class FXMLRelatorioConsultasAgendadasController implements Initializable {

    @FXML
    private TableView<Agendamento> tableViewAgendamento;
    @FXML
    private TableColumn<Agendamento, Integer> tableColumnCliente;
    @FXML
    private TableColumn<Agendamento, String> tableColumnAnimalNome;
    @FXML
    private TableColumn<Agendamento, Integer> tableColumnqnt;
    @FXML
    private TableColumn<Agendamento, String> tableColumnEspeciealidade;
    @FXML
    private TableColumn<Agendamento, Cliente> tableColumnIdCliente;
    @FXML
    private TableColumn<Agendamento, String> tableColumnNomeCliente;
    @FXML
    private TableColumn<Agendamento, String> tableColumnClienteCpf;

    /*@FXML
    private Button buttonImprimir;*/
    private Agendamento agendamento;
    private List<Agendamento> listAgendamento;
    private ObservableList<Agendamento> observableListAgendamento;

    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final AgendamentoDAO AgendamentoDAO = new AgendamentoDAO();
    private final ClienteDAO clienteDAO = new ClienteDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void handleImprimir() throws JRException {

        URL url = getClass().getResource("/javafx/relatorios/javaFxQuantAgendamento.jasper");
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(url);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, connection);//null: caso não existam filtros
        JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);//false: não deixa fechar a aplicação principal
        jasperViewer.setVisible(true);
    }

}
