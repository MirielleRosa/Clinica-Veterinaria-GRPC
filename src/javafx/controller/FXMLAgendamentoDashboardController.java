package javafx.controller;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import javafx.agendamento.AgendaServiceGrpc;
import javafx.agendamento.GetAgendamentosRequest;
import javafx.agendamento.GetAgendamentosResponse;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.model.dao.AgendamentoDAO;
import javafx.model.database.Database;
import javafx.model.database.DatabaseFactory;
import javafx.model.domain.Agendamento;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.model.domain.Animal;
import javafx.model.domain.Cliente;
import javafx.scene.Scene;

public class FXMLAgendamentoDashboardController implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TableView<Agendamento> tableViewDescricao;
    @FXML
    private TableColumn<Agendamento, String> tableColumnId;
    @FXML
    private TableColumn<Agendamento, String> tableColumnAnimal;
    @FXML
    private TableColumn<Agendamento, String> tableColumnStatus;
    @FXML
    private TableColumn<Agendamento, String> tableColumnCliente;
    @FXML
    private TableColumn<Agendamento, LocalDate> tableColumnData;
    @FXML
    private TableColumn<Agendamento, LocalDate> tableColumnHora;
    @FXML
    private Button buttonStatus;
    @FXML
    private Label labelNomeCliente;
    @FXML
    private Label labelAnimal;
    @FXML
    private Label labelTipo;
    @FXML
    private Label labelData;
    @FXML
    private Label labelHora;
     
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 50051;
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();

    private final AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private List<Agendamento> listAgendamento;
    private ObservableList<Agendamento> observableListAgendamento;
   

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        agendamentoDAO.setConnection(connection);

        // Initialize the list and set up the table columns
        observableListAgendamento = FXCollections.observableArrayList();
        tableViewDescricao.setItems(observableListAgendamento);

        // Set up selection listener
        tableViewDescricao.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewAgendamento(newValue));

        // Schedule updates to the table
        scheduler.scheduleAtFixedRate(this::atualizarTabela, 0, 5, TimeUnit.SECONDS);
    }

    private void atualizarTabela() {
        Platform.runLater(() -> carregarDadosDaTabelaDoServidor());
    }

    void stop() {
        scheduler.shutdown();
    }

    private void carregarDadosDaTabelaDoServidor() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
                .usePlaintext()
                .build();

        AgendaServiceGrpc.AgendaServiceBlockingStub blockingStub = AgendaServiceGrpc.newBlockingStub(channel);

        try {
            GetAgendamentosResponse response = blockingStub.getAgendamentos(GetAgendamentosRequest.newBuilder().build());
            List<javafx.agendamento.Agendamento> agendamentos = response.getAgendamentosList();

            List<Agendamento> agendamentosConvertidos = agendamentos.stream()
                    .map(this::mapProtoToDomain)
                    .collect(Collectors.toList());

            System.out.println("Número de agendamentos recebidos: " + agendamentosConvertidos.size());


            Platform.runLater(() -> {
                observableListAgendamento.clear();
                observableListAgendamento.addAll(agendamentosConvertidos);

                // Configure as colunas da tabela usando setCellValueFactory
                tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idAgendamento"));
                tableColumnAnimal.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getAnimal().getNome()));
                tableColumnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
                tableColumnCliente.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCliente().getNome()));
                tableColumnData.setCellValueFactory(new PropertyValueFactory<>("data"));
                tableColumnHora.setCellValueFactory(new PropertyValueFactory<>("hora"));

                tableViewDescricao.refresh(); // Use o método refresh para garantir que a tabela seja atualizada
            });

        } catch (StatusRuntimeException e) {
            System.out.println("Erro gRPC no cliente: " + e.getStatus().getCode());
            e.printStackTrace();
        } finally {
            channel.shutdown();
        }
    }

 
    private Agendamento mapProtoToDomain(javafx.agendamento.Agendamento protoAgendamento) {
        Agendamento agendamento = new Agendamento();

        agendamento.setIdAgendamento(protoAgendamento.getIdAgendamento());
        agendamento.setTipo(protoAgendamento.getTipo());
        agendamento.setStatus(protoAgendamento.getStatus());

        // Assuming you have methods like setCliente, setAnimal, setData, and setHora in Agendamento class
        agendamento.setCliente(new Cliente(protoAgendamento.getIdCliente(), protoAgendamento.getNomeCliente()));
        agendamento.setAnimal(new Animal(protoAgendamento.getIdAnimal(), protoAgendamento.getNomeAnimal()));
        agendamento.setData(convertStringToLocalDate(protoAgendamento.getData()));
        agendamento.setHora(convertStringToLocalTime(protoAgendamento.getHora()));

        return agendamento;
    }


    public void selecionarItemTableViewAgendamento(Agendamento agendamento) {
        if (agendamento != null) {
            labelNomeCliente.setText(String.valueOf(agendamento.getCliente().getNome()));
            labelAnimal.setText(String.valueOf(agendamento.getAnimal().getNome()));
            labelTipo.setText(String.valueOf(agendamento.getTipo()));
            labelData.setText(String.valueOf(agendamento.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
            labelHora.setText(agendamento.getHora().format(DateTimeFormatter.ofPattern("HH:mm")));
        } else {
            labelNomeCliente.setText("");
            labelAnimal.setText("");
            labelTipo.setText("");
            labelData.setText("");
            labelHora.setText("");
        }
    }

    @FXML
    public void handleButtonInserir() throws IOException {
        Agendamento agendamento = tableViewDescricao.getSelectionModel().getSelectedItem();
        if (agendamento != null) {
            boolean buttonConfirmarClicked = showFXMLDashboardDialog(agendamento);
            if (buttonConfirmarClicked) {
                agendamentoDAO.alterarStatus(agendamento);
                carregarDadosDaTabelaDoServidor();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um agendamento na Tabela!");
            alert.show();
        }
    }

    public boolean showFXMLDashboardDialog(Agendamento agendamento) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLAgendamentoDashboardDialogController.class.getResource("/javafx/view/FXMLAgendamentoDashboardDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Dashboard");

        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        FXMLAgendamentoDashboardDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setAgendamento(agendamento);

        dialogStage.showAndWait();

        return controller.isButtonConfirmarClicked();
    }
    
    
    private LocalDate convertStringToLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, formatter);
    }


        private LocalTime convertStringToLocalTime(String time) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            return LocalTime.parse(time, formatter);
        }

        private Animal buscarAnimalPorId(int idAnimal) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
