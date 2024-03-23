package javafx.controller;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import grpcjavafx.EquipeRequest;
import grpcjavafx.EquipeResponse;
import grpcjavafx.EquipeServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class FXMLSobreController implements Initializable {

    @FXML
    private Label labelIntegrantes;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Crie um canal gRPC para se comunicar com o servidor
        ManagedChannel channel = ManagedChannelBuilder.forAddress("127.0.0.1", 50051).usePlaintext().build();

        // Crie um stub gRPC para chamar métodos no serviço EquipeService
        EquipeServiceGrpc.EquipeServiceBlockingStub stub = EquipeServiceGrpc.newBlockingStub(channel);

        try {
            // Chame o método desejado do serviço EquipeService
            EquipeResponse response = stub.getEquipeInfo(EquipeRequest.newBuilder().setId(2).build());

            // Use a resposta conforme necessário
            List<String> equipe = response.getEquipeList();

            // Inicialize a exibição da equipe em uma nova thread
            Thread threadExibicao = new Thread(() -> {
                while (true) {
                    for (String membro : equipe) {
                        try {
                            Platform.runLater(() -> exibirNomeEquipe(membro));
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            threadExibicao.setDaemon(true);
            threadExibicao.start();

        } catch (StatusRuntimeException e) {
            e.printStackTrace();
        } finally {
            // Feche o canal gRPC após o uso
            channel.shutdown();
        }
    }

    private void exibirNomeEquipe(String nome) {
        labelIntegrantes.setText(nome);
        System.out.println("Nome da equipe exibido: " + nome);
    }
}