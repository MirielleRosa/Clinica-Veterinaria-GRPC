package javafx.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FXMLMenuControllerVeterinario implements Initializable {

    @FXML
    private Menu menuListagem;
    @FXML
    private MenuItem menuItemListagemAnimais;
    @FXML
    private MenuItem menuItemAgen;
    @FXML
    private MenuItem menuItemVetRelatorio;
    @FXML
    private MenuItem graficoVacinacao;
    @FXML
    private MenuItem menuItemVet;
    @FXML
    private AnchorPane anchorPaneCarregar;
    @FXML
    private Button menuVacinacao;
    @FXML
    private Button menuPaciente;
    @FXML
    private ImageView logo;
    @FXML
    private Button buttonSobre;
    @FXML
    private Button buttonFechar;
    @FXML
    private Button menuDashboard;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            handleMenuItemLimparAnchorPane();
        } catch (IOException ex) {
            Logger.getLogger(FXMLMenuControllerVeterinario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void handleMenuItemCadastrosClientes() throws IOException {
        AnchorPane a = FXMLLoader.load(getClass().getResource("/javafx/view/FXMLAnchorPaneCadastrosClientes.fxml"));
        anchorPaneCarregar.getChildren().setAll(a);
    }
    
    @FXML
    private void handleMenuItemLimparAnchorPane() throws IOException {
        AnchorPane a = FXMLLoader.load(getClass().getResource("/javafx/view/FXMLMenuInicial.fxml"));
        anchorPaneCarregar.getChildren().setAll(a);
    }

    public void handleMenuItemCadastroAnimal() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafx/view/FXMLCadastrarAnimal.fxml"));
        anchorPaneCarregar.getChildren().setAll(a);
    }
    
    @FXML
    public void handleMenuItemSobre() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafx/view/FXMLSobre.fxml"));
        anchorPaneCarregar.getChildren().setAll(a);
    }

//    public void handleMenuItemCadastroVetrinario() throws IOException {
//        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafx/view/FXMLCadastrarVeterinario.fxml"));
//        anchorPaneCarregar.getChildren().setAll(a);
//    }
//
//    public void handleMenuItemCadastroFuncionario() throws IOException {
//        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafx/view/FXMLCadastroFuncionario.fxml"));
//        anchorPaneCarregar.getChildren().setAll(a);
//    }
//
//    public void handleMenuItemCadastroVacina() throws IOException {
//        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafx/view/FXMLCadastrarVacina.fxml"));
//        anchorPaneCarregar.getChildren().setAll(a);
//    }

    @FXML
    public void handleMenuItemProcessoVacinacao() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafx/view/FXMLProcessoVacinacao.fxml"));
        anchorPaneCarregar.getChildren().setAll(a);
    }

    public void handleMenuItemDashboard() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafx/view/FXMLAgendamentoDashboard.fxml"));
        anchorPaneCarregar.getChildren().setAll(a);
    }

    @FXML
    public void FXMLGraficoVacinacaoPorMes() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafx/view/FXMLGraficoVacinacaoPorMes.fxml"));
        anchorPaneCarregar.getChildren().setAll(a);
    }

    @FXML
    public void FXMLGraficoVeterinarioElegido() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafx/view/FXMLGraficoVeterinariosMaiselegido.fxml"));
        anchorPaneCarregar.getChildren().setAll(a);
    }

    @FXML
    public void handleMenuItemRelatorioAnimais() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafx/view/FXMLRelatorioAnimais.fxml"));
        anchorPaneCarregar.getChildren().setAll(a);
    }

    @FXML
    public void handleMenuItemRelatorioAgen() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafx/view/FXMLRelatorioConsultasAgendadas.fxml"));
        anchorPaneCarregar.getChildren().setAll(a);
    }

    @FXML
    public void handleMenuItemRelatorioVet() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafx/view/FXMLRelatorioVacinaPorVeterinario.fxml"));
        anchorPaneCarregar.getChildren().setAll(a);
    }
    
    @FXML
    public void handleMenuItemFeedback() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafx/view/FXMLProcessoFeedback.fxml"));
        anchorPaneCarregar.getChildren().setAll(a);
    }
    
    @FXML
    public void buttonFecharPrograma(ActionEvent event) {
        // Obtém a referência à janela atual
        Stage stage = (Stage) buttonFechar.getScene().getWindow();

        // Fecha a janela
        stage.close();
    }
}
