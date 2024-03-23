/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx.controller;

import java.net.URL;
import java.sql.Connection;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.model.dao.VeterinarioDAO;
import javafx.model.database.Database;
import javafx.model.database.DatabaseFactory;
import javafx.model.domain.Veterinario;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Mirielle
 */
public class FXMLGraficoVeterinariosMaiselegidoController implements Initializable {

    @FXML
    private PieChart pieChart;

    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final VeterinarioDAO veterinarioDAO = new VeterinarioDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        veterinarioDAO.setConnection(connection);

        atualizarGrafico();

        pieChart.getData().forEach(data -> {
            data.getNode().setOnMouseClicked(event -> {
                Veterinario veterinario = veterinarioDAO.getVeterinariosComVacinacoes().get(pieChart.getData().indexOf(data));
                String mensagem = "O veterinário " + veterinario.getNome() + " vacinou " + veterinario.getNumVacinacoes() + " vezes.";
                Label label = new Label(mensagem);
                label.setStyle("-fx-font-size: 16px;");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.getDialogPane().setContent(label);
                alert.showAndWait();
            });
        });
    }

    public void atualizarGrafico() {
        // Busca todos os veterinários e a quantidade de vacinações que cada um realizou
        List<Veterinario> veterinarios = veterinarioDAO.getVeterinariosComVacinacoes();

        // Cria uma lista de dados para o gráfico
        ObservableList<PieChart.Data> dados = FXCollections.observableArrayList();
        for (Veterinario veterinario : veterinarios) {
            dados.add(new PieChart.Data(veterinario.getNome(), veterinario.getNumVacinacoes()));
        }

        // Configura o gráfico
        pieChart.setData(dados);
        pieChart.setTitle("Quantidade de vacinações por veterinário");
        pieChart.setLegendVisible(false);
    }

}
