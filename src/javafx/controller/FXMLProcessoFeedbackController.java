package javafx.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.model.dao.FeedbackDAO;
import javafx.model.domain.Animal;
import javafx.model.domain.Feedback;
import javafx.model.domain.Veterinario;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLProcessoFeedbackController implements Initializable {

    private ComboBox<String> comboBoxNotaAvaliacao;

    private ObservableList<String> notasAvaliacao = FXCollections.observableArrayList("1", "2", "3", "4", "5");

    private FeedbackDAO feedbackDAO; // Add this field

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       // comboBoxNotaAvaliacao.setItems(notasAvaliacao);
        //feedbackDAO = new FeedbackDAO(); // Instantiate the FeedbackDAO
    }

    /**
     * Insere um novo feedback no sistema.
     */
    public void inserirFeedback() {
    /*    String data = datePickerDataFeedback.getValue().toString();
        String animalString = textFieldAnimal.getText();
        String veterinarioString = textFieldVeterinario.getText();
        String nota = comboBoxNotaAvaliacao.getSelectionModel().getSelectedItem();
        String comentarioString = textAreaComentario.getText();

        Animal animal = new Animal();
        animal.setNome(animalString);

        Veterinario veterinario = new Veterinario();
        veterinario.setNome(veterinarioString);

        Feedback feedback = new Feedback();
        feedback.setDataFeedback(data);
        feedback.setAnimal(animal);
        feedback.setVeterinario(veterinario);
        feedback.setNotaAvaliacao(Integer.parseInt(nota));
        feedback.setComentario(comentarioString);

        feedbackDAO.inserir(feedback);*/
    }

    /**
     * Altera um feedback existente no sistema.
     */
    public void alterarFeedback() {
     /*   String data = datePickerDataFeedback.getValue().toString();
        String animalString = textFieldAnimal.getText();
        String veterinarioString = textFieldVeterinario.getText();
        String nota = comboBoxNotaAvaliacao.getSelectionModel().getSelectedItem();
        String comentarioString = textAreaComentario.getText();

        Animal animal = new Animal();
        animal.setNome(animalString);

        Veterinario veterinario = new Veterinario();
        veterinario.setNome(veterinarioString);

        Feedback feedback = new Feedback();
        feedback.setDataFeedback(data);
        feedback.setAnimal(animal);
        feedback.setVeterinario(veterinario);
        feedback.setNotaAvaliacao(Integer.parseInt(nota));
        feedback.setComentario(comentarioString);

        feedbackDAO.alterar(feedback);*/
    }

    /**
     * Remove um feedback existente no sistema.
     */
    public void removerFeedback() {
     /*   String data = datePickerDataFeedback.getValue().toString();

        Feedback feedback = new Feedback();
        feedback.setDataFeedback(data);

        feedbackDAO.remover(feedback);*/
    }

}