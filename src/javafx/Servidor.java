package javafx;

import grpcjavafx.EquipeRequest;
import grpcjavafx.EquipeResponse;
import grpcjavafx.EquipeServiceGrpc;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import javafx.agendamento.AgendaServiceGrpc;
import javafx.agendamento.AtualizarStatusAgendamentoRequest;
import javafx.agendamento.AtualizarStatusAgendamentoResponse;
import javafx.agendamento.GetAgendamentosRequest;
import javafx.agendamento.GetAgendamentosResponse;
import javafx.agendamento.Agendamento;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.model.dao.AgendamentoDAO;
import javafx.model.database.Database;
import javafx.model.database.DatabaseFactory;

public class Servidor {
    private final int port;
    private final Server server;
    private static boolean servidorPronto = false;

    public Servidor(int port) {
        this.port = port;
        this.server = ServerBuilder.forPort(port)
                .addService(new AgendaServiceImpl())
                .addService(new EquipeServiceImpl())
                .build();
    }

    public void start() throws IOException {
        server.start();
        System.out.println("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            Servidor.this.stop();
            System.err.println("*** server shut down");
        }));
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Servidor server = new Servidor(50051);
        server.start();
        servidorPronto = true;
        server.server.awaitTermination();
    }

    public static boolean isServidorPronto() {
        return servidorPronto;
    }

    private static class AgendaServiceImpl extends AgendaServiceGrpc.AgendaServiceImplBase {
        private final Connection connection;
        private final AgendamentoDAO agendamentoDAO;

        public AgendaServiceImpl() {
            // Initialize AgendamentoDAO and set up the connection
            this.connection = DatabaseFactory.getDatabase("postgresql").conectar();
            this.agendamentoDAO = new AgendamentoDAO();
            this.agendamentoDAO.setConnection(connection);
        }

@Override
public void getAgendamentos(GetAgendamentosRequest request, StreamObserver<GetAgendamentosResponse> responseObserver) {
    try {
        // Execute the SQL query with JOIN to get additional data
        String query = "SELECT agendamento.*, animal.nome AS nomeAnimal, cliente.nome AS nomeCliente "
                + "FROM agendamento "
                + "INNER JOIN animal ON agendamento.animal = animal.idAnimal "
                + "INNER JOIN cliente ON agendamento.cliente = cliente.idCliente";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        GetAgendamentosResponse.Builder responseBuilder = GetAgendamentosResponse.newBuilder();

        // For each row in the result, build an Agendamento message and add it to the response
        while (resultSet.next()) {
            Agendamento agendamento = Agendamento.newBuilder()
                    .setIdAgendamento(resultSet.getInt("idAgendamento"))
                    .setIdCliente(resultSet.getInt("cliente"))
                    .setIdAnimal(resultSet.getInt("animal"))
                    .setTipo(resultSet.getString("tipo"))
                    .setData(resultSet.getString("data"))
                    .setHora(resultSet.getString("hora"))
                    .setStatus(resultSet.getString("status"))
                    .setNomeCliente(resultSet.getString("nomeCliente"))
                    .setNomeAnimal(resultSet.getString("nomeAnimal"))
                    
                    .build();

            responseBuilder.addAgendamentos(agendamento);
        }

        // Send the response to the gRPC client
        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    } catch (SQLException e) {
        // Log the error message on the server
        e.printStackTrace();

        // Handle database errors appropriately
        responseObserver.onError(Status.INTERNAL.withDescription("Erro interno no servidor").asRuntimeException());
    }
}

private Agendamento mapAgendamentoToProto(javafx.model.domain.Agendamento agendamento) {
    return Agendamento.newBuilder()
            .setIdAgendamento(agendamento.getIdAgendamento())
            .setIdCliente(agendamento.getCliente().getIdCliente()) // assuming getIdCliente() returns the ID of the client
            .setIdAnimal(agendamento.getAnimal().getIdAnimal()) // assuming getIdAnimal() returns the ID of the animal
            .setTipo(agendamento.getTipo())
            .setData(agendamento.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
            .setHora(agendamento.getHora().format(DateTimeFormatter.ofPattern("HH:mm:ss")))
            .setStatus(agendamento.getStatus())
            .setNomeCliente(agendamento.getCliente().getNome()) // assuming getNomeCliente() returns the name of the client
            .setNomeAnimal(agendamento.getAnimal().getNome()) // assuming getNomeAnimal() returns the name of the animal
            .build();
}


        
        private List<Agendamento> mapAgendamentosToProto(List<javafx.model.domain.Agendamento> agendamentos) {
            return agendamentos.stream()
                    .map(this::mapAgendamentoToProto)
                    .collect(Collectors.toList());
        }
    }
    
      private static class EquipeServiceImpl extends EquipeServiceGrpc.EquipeServiceImplBase{
        @Override
        public void getEquipeInfo(EquipeRequest request, io.grpc.stub.StreamObserver<EquipeResponse> responseObserver) {
            int id = request.getId();
            List<String> equipe = getEquipeById(id);

            EquipeResponse response = EquipeResponse.newBuilder().addAllEquipe(equipe).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }

        private List<String> getEquipeById(int id) {
            List<String> equipeList = new ArrayList<>();
            switch (id) {
                case 1:
                    equipeList.add("Magno Leal De Brito Junior");
                    equipeList.add("Renan Gomes Poggian");
                    equipeList.add("Vitor Miller de Toledo");
                    break;
                case 2:
                    equipeList.add("Milena Melo Linhares");
                    equipeList.add("Mirielle Rosa de Souza");
                    equipeList.add("Yuri Fabres de Jesus Figueira");
                    equipeList.add("Sofia Pereira Bachetti Sartorio");
                    break;
                case 3:
                    equipeList.add("Gabriely Machado Carrari");
                    equipeList.add("Alda Maria Norbiato Torres");
                    equipeList.add("Fernanda De Oliveira Ramos");
                    break;
                case 4:
                    equipeList.add("Marcelo Alves Santos");
                    equipeList.add("Livia Guimarães De Jesus");
                    equipeList.add("Gabriel Cardoso Girarde");
                    break;
                case 5:
                    equipeList.add("Matheus da Silva Módolo");
                    equipeList.add("Sofia Pereira Bachetti Sartorio");
                    equipeList.add("Larissa Paganini");
                    break;
                case 6:
                    equipeList.add("Silvio Lopes Ribeiro");
                    equipeList.add("Murillo Carlete Ribeiro");
                    equipeList.add("Róger Corrente Pinto");
                    equipeList.add("Milena Melo Linhares");
                    equipeList.add("Mirielle Rosa de Souza");
                    break;
                case 7:
                    equipeList.add("Douglas Altoé Stein da Silva");
                    equipeList.add("Diego Chaves Ravani");
                    equipeList.add("Carlos Eduardo de Menezes Pacheco");
                    break;
                case 8:
                    equipeList.add("Eduardo Souto Siqueira Santana");
                    equipeList.add("Rafael Savignon de Resende");
                    equipeList.add("Felipe Moreira da Paz");
                    break;
                case 9:
                    equipeList.add("Wesley Pereira da Silva");
                    equipeList.add("João Victor de Salles Mapeli Couzzi");
                    equipeList.add("João Victor Maciel Campos");
                    break;
                case 10:
                    equipeList.add("Igor Almeida da Silva");
                    equipeList.add("Raphael Pavani Manhães Bersot");
                    equipeList.add("Júlia Borges Santos");
                    break;
                default:
                    equipeList.add("Número da equipe informado está incorreto!");
            }
            return equipeList;
        }
    }

}
